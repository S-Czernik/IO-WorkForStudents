package model.calendar;

import model.Interface;
import model.Model;

public class CalendarInterface extends Interface {

	public String loadStudentCalendarFromDatabase(String userLogin) {
		Kalyndarz ret = getStudentCalendar(userLogin);
		if (ret == null) {
			return null;
		}
		return ret.getCSV();
	}

	public String loadOfferCalendarFromDatabase(int offerID) {
		Kalyndarz ret = getOfferCalendar(offerID);
		if (ret == null) {
			return null;
		}
		return ret.getCSV();
	}

	public void saveStudentCalendarToDatabase(String login, String csv) {
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

	public float compareCalendars(String login, int offerID) {
		Kalyndarz stud = getStudentCalendar(login);
		Kalyndarz offer = getOfferCalendar(offerID);

		if (stud != null && offer != null) {
			return stud.compare(offer);
		}
		return 0.0f;
	}

	Kalyndarz getStudentCalendar(String login) {
		try {
			String query = "SELECT * FROM hours";
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return null;
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
