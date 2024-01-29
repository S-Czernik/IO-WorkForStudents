package model.calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Interface;

public class CalendarInterface extends Interface {

	String getDayFromIdx(int v) {
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

	int getIdxFromDay(String v) {
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
		Kalyndarz ret = getStudentCalendar(userID);
		if (ret == null) {
			return null;
		}
		return ret.getCSV();
	}

	public String getOfferCalendarCsv(int offerID) {
		Kalyndarz ret = getOfferCalendar(offerID);
		if (ret == null) {
			return null;
		}
		return ret.getCSV();
	}

	public String getStudentCalendarHtml(int userID) {
		Kalyndarz ret = getStudentCalendar(userID);
		if (ret == null) {
			return null;
		}
		return CalendarHTMLBuilder.get(ret);
	}

	public String getOfferCalendarHtml(int offerID) {
		Kalyndarz ret = getOfferCalendar(offerID);
		if (ret == null) {
			return null;
		}
		return CalendarHTMLBuilder.get(ret);
	}

	public boolean saveStudentCalendarToDatabase(int userID, String csv) {
		try {
			Kalyndarz k = new Kalyndarz();
			k.loadCSV(csv);

			statement.execute("DELETE FROM student_hours WHERE id_stud = '" + userID + "'");
			for (var i : k.intervals) {
				PreparedStatement stmt = model.connection.prepareStatement("INSERT INTO student_hours(id_hour, id_stud, begin, end, day) VALUES (?,?,?,?)");
				stmt.setInt(0, getLastStudHourId());
				stmt.setInt(1, userID);
				stmt.setInt(2, i.begin);
				stmt.setInt(3, i.end);
				stmt.setString(3, getDayFromIdx(i.day));
			}
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean saveOfferCalendarToDatabase(int offerID, String csv) {
		try {
			Kalyndarz k = new Kalyndarz();
			k.loadCSV(csv);

			statement.execute("DELETE FROM offer_hours WHERE id_stud = '" + offerID + "'");
			for (var i : k.intervals) {
				PreparedStatement stmt = model.connection.prepareStatement("INSERT INTO offer_hours(id_hour, id_offer, begin, end, day) VALUES (?,?,?,?)");
				stmt.setInt(0, getLastStudHourId());
				stmt.setInt(1, offerID);
				stmt.setInt(2, i.begin);
				stmt.setInt(3, i.end);
				stmt.setString(3, getDayFromIdx(i.day));
			}
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public float compareCalendars(int userID, int offerID) {
		Kalyndarz stud = getStudentCalendar(userID);
		Kalyndarz offer = getOfferCalendar(offerID);

		if (stud != null && offer != null) {
			return stud.compare(offer);
		}
		return 0.0f;
	}

	Kalyndarz getStudentCalendar(int userID) {
		try {
			Kalyndarz k = new Kalyndarz();

			String query = "SELECT * FROM student_hours WHERE id_stud = '" + userID + "'";
			ResultSet results = statement.executeQuery(query);

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

	Kalyndarz getOfferCalendar(int offerID) {
		try {
			Kalyndarz k = new Kalyndarz();

			String query = "SELECT * FROM offer_hours WHERE id_offer = '" + offerID + "'";
			ResultSet results = statement.executeQuery(query);

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
			ResultSet results = statement.executeQuery(query);

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
			ResultSet results = statement.executeQuery(query);

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
