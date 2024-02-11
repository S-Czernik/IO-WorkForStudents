package model.calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Interface;

public class CalendarInterface extends Interface {

	public static String getDayFromIdx(int v) {
		switch (v) {
			case 0 -> {
				return "mon";
			}
			case 1 -> {
				return "tue";
			}
			case 2 -> {
				return "wed";
			}
			case 3 -> {
				return "thu";
			}
			case 4 -> {
				return "fri";
			}
			case 5 -> {
				return "sat";
			}
			case 6 -> {
				return "sun";
			}
		}
		return "";
	}

	public static int getIdxFromDay(String v) {
		switch (v) {
			case "mon" -> {
				return 0;
			}
			case "tue" -> {
				return 1;
			}
			case "wed" -> {
				return 2;
			}
			case "thu" -> {
				return 3;
			}
			case "fri" -> {
				return 4;
			}
			case "sat" -> {
				return 5;
			}
			case "sun" -> {
				return 6;
			}
		}
		return 0;
	}

	public String getStudentCalendarCsv(int userID) {
		Calendar ret = getStudentCalendar(userID);
		if (ret == null) {
			return null;
		}
		return ret.getCSV();
	}

	public String getOfferCalendarCsv(int offerID) {
		Calendar ret = getOfferCalendar(offerID);
		if (ret == null) {
			return null;
		}
		return ret.getCSV();
	}

	public String getStudentCalendarHtml(int userID) {
		Calendar ret = getStudentCalendar(userID);
		if (ret == null) {
			return null;
		}
		return CalendarHTMLBuilder.get(ret);
	}

	public String getOfferCalendarHtml(int offerID) {
		Calendar ret = getOfferCalendar(offerID);
		if (ret == null) {
			return null;
		}
		return CalendarHTMLBuilder.get(ret);
	}

	public float getCompareToStudent(int userID, int offerID) {
		try {
			ResultSet valueSet = connection.createStatement().executeQuery("SELECT value FROM offer_calendar_comparisons WHERE id_stud = '" + userID + "' AND id_offer = '" + offerID + "'");
			if (valueSet.next()) {
				return valueSet.getFloat("value");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return 0f;
	}

	public float getCompareToOffer(int userID, int offerID) {
		try {
			ResultSet valueSet = connection.createStatement().executeQuery("SELECT value FROM student_calendar_comparisons WHERE id_stud = '" + userID + "' AND id_offer = '" + offerID + "'");
			if (valueSet.next()) {
				return valueSet.getFloat("value");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return 0f;
	}

	public boolean saveStudentCalendarToDatabase(int userID, String csv) {
		try {
			Calendar k = new Calendar();
			k.loadCSV(csv);

			connection.createStatement().execute("DELETE FROM student_hours WHERE id_stud = '" + userID + "'");
			PreparedStatement hourStatement = model.connection.prepareStatement("INSERT INTO student_hours(id_hour, id_stud, begin, end, day) VALUES (?,?,?,?,?)");
			for (var i : k.intervals) {
				hourStatement.setInt(1, getLastStudHourId() + 1);
				hourStatement.setInt(2, userID);
				hourStatement.setInt(3, i.begin);
				hourStatement.setInt(4, i.end);
				hourStatement.setString(5, getDayFromIdx(i.day));

				hourStatement.execute();
			}

			connection.createStatement().execute("DELETE FROM student_calendar_comparisons WHERE id_stud = '" + userID + "'");
			connection.createStatement().execute("DELETE FROM offer_calendar_comparisons WHERE id_stud = '" + userID + "'");
			PreparedStatement studCCsStatement = model.connection.prepareStatement("INSERT INTO student_calendar_comparisons(id_stud, id_offer, value) VALUES (?,?,?)");
			PreparedStatement offerCCsStatement = model.connection.prepareStatement("INSERT INTO offer_calendar_comparisons(id_stud, id_offer, value) VALUES (?,?,?)");
			int offerCount = model.offerInterface.getLastOfferId();
			for (var offerID = 1; offerID <= offerCount; offerID++) {
				Calendar cmp = getOfferCalendar(offerID);

				float value = k.compare(cmp);
				studCCsStatement.setInt(1, userID);
				studCCsStatement.setInt(2, offerID);
				studCCsStatement.setFloat(3, value);
				studCCsStatement.execute();

				value = cmp.compare(k);
				offerCCsStatement.setInt(1, userID);
				offerCCsStatement.setInt(2, offerID);
				offerCCsStatement.setFloat(3, value);
				offerCCsStatement.execute();
			}

			return true;
		}
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean saveOfferCalendarToDatabase(int offerID, String csv) {
		try {
			Calendar k = new Calendar();
			k.loadCSV(csv);

			connection.createStatement().execute("DELETE FROM offer_hours WHERE id_offer = '" + offerID + "'");
			PreparedStatement hourStatement = model.connection.prepareStatement("INSERT INTO offer_hours(id_hour, id_offer, begin, end, day) VALUES (?,?,?,?,?)");
			for (var i : k.intervals) {
				hourStatement.setInt(1, getLastStudHourId() + 1);
				hourStatement.setInt(2, offerID);
				hourStatement.setInt(3, i.begin);
				hourStatement.setInt(4, i.end);
				hourStatement.setString(5, getDayFromIdx(i.day));

				hourStatement.execute();
			}

			connection.createStatement().execute("DELETE FROM student_calendar_comparisons WHERE id_stud = '" + offerID + "'");
			connection.createStatement().execute("DELETE FROM offer_calendar_comparisons WHERE id_stud = '" + offerID + "'");
			PreparedStatement studCCsStatement = model.connection.prepareStatement("INSERT INTO student_calendar_comparisons(id_stud, id_offer, value) VALUES (?,?,?)");
			PreparedStatement offerCCsStatement = model.connection.prepareStatement("INSERT INTO offer_calendar_comparisons(id_stud, id_offer, value) VALUES (?,?,?)");
			int offerCount = model.offerInterface.getLastOfferId();
			for (var userID = 1; userID <= offerCount; userID++) {
				Calendar cmp = getStudentCalendar(userID);

				float value = k.compare(cmp);
				offerCCsStatement.setInt(1, userID);
				offerCCsStatement.setInt(2, offerID);
				offerCCsStatement.setFloat(3, value);
				offerCCsStatement.execute();

				value = cmp.compare(k);
				studCCsStatement.setInt(1, userID);
				studCCsStatement.setInt(2, offerID);
				studCCsStatement.setFloat(3, value);
				studCCsStatement.execute();
			}

			return true;
		}
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	Calendar getStudentCalendar(int userID) {
		try {
			Calendar k = new Calendar();

			String query = "SELECT * FROM student_hours WHERE id_stud = '" + userID + "'";
			ResultSet results = connection.createStatement().executeQuery(query);

			while (results.next()) {
				int begin = results.getInt("begin");
				int end = results.getInt("end");
				int day = getIdxFromDay(results.getString("day"));

				k.intervals.add(new Interval(begin, end, day));
			}

			return k;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	Calendar getOfferCalendar(int offerID) {
		try {
			Calendar k = new Calendar();

			String query = "SELECT * FROM offer_hours WHERE id_offer = '" + offerID + "'";
			ResultSet results = connection.createStatement().executeQuery(query);

			while (results.next()) {
				int begin = results.getInt("begin");
				int end = results.getInt("end");
				int day = getIdxFromDay(results.getString("day"));

				k.intervals.add(new Interval(begin, end, day));
			}

			return k;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	int getLastStudHourId() {
		try {
			String query = "SELECT MAX(id_hour) AS max FROM student_hours";
			ResultSet results = connection.createStatement().executeQuery(query);

			if (results.next()) {
				return results.getInt("max");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	int getLastOfferHourId() {
		try {
			String query = "SELECT MAX(id_hour) AS max FROM offer_hours";
			ResultSet results = connection.createStatement().executeQuery(query);

			if (results.next()) {
				return results.getInt("max");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}
}
