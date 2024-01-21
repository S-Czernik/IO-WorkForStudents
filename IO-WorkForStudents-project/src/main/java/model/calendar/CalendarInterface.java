package model.calendar;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import model.Interface;

public class CalendarInterface extends Interface {

	Map<String, Integer> daysDictionary;

	public CalendarInterface() {
		daysDictionary = new HashMap<>();
		daysDictionary.put("mon", 0);
		daysDictionary.put("tue", 1);
		daysDictionary.put("wen", 2);
		daysDictionary.put("thu", 3);
		daysDictionary.put("fri", 4);
		daysDictionary.put("sat", 5);
		daysDictionary.put("sun", 6);
	}

	public String getStudentCalendarCsv(String userLogin) {
		Kalyndarz ret = getStudentCalendar(userLogin);
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

	public String getStudentCalendarHtml(String userLogin) {
		Kalyndarz ret = getStudentCalendar(userLogin);
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

	public void saveStudentCalendarToDatabase(String userID, String csv) {
		try {
			String query = "SELECT * FROM hours";
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void saveOfferCalendarToDatabase(int offerID, String csv) {
		try {
			String query = "SELECT * FROM hours";
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public float compareCalendars(String userID, int offerID) {
		Kalyndarz stud = getStudentCalendar(userID);
		Kalyndarz offer = getOfferCalendar(offerID);

		if (stud != null && offer != null) {
			return stud.compare(offer);
		}
		return 0.0f;
	}

	Kalyndarz getStudentCalendar(String userID) {
		try {
			Kalyndarz k = new Kalyndarz();

			String query = "SELECT * FROM student_hours WHERE id_stud = '" + userID + "'";
			ResultSet results = statement.executeQuery(query);

			while (results.next()) {
				int begin = results.getInt("begin");
				int end = results.getInt("end");
				int day = daysDictionary.get(results.getString("day"));

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
			String query = "SELECT * FROM hours";
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
