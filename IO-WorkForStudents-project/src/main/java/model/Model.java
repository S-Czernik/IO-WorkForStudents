package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Model {

	static Model singleton = null;
	Connection connection;
	Statement statement;

	public static Model getModel() {
		if (singleton == null) {
			singleton = new Model();
		}
		return singleton;
	}

	public Model() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		connect();
	}

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "1234");
			statement = connection.createStatement();
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ArrayList<String> checkLogin(String login, String password) {
		ArrayList<String> idAndType = new ArrayList<>();

		try {
			String query = "SELECT * FROM USERS WHERE login = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, login);
			ResultSet results = preparedStatement.executeQuery();

			if (results.next() && results.getString("password").equals(password)) {
				idAndType.add(results.getString("id_user"));
				idAndType.add(results.getString("type"));
			}
			else {
				idAndType.add("-1");
				idAndType.add("-1");
			}
		}
		catch (Exception e) {
			System.out.println(e);
			idAndType.add("-1");
			idAndType.add("-1");
		}

		return idAndType;
	}

	public boolean register(String login, String password, String email, String type) {
		try {
			String query = "SELECT * FROM USERS WHERE login = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, login);
			ResultSet results = preparedStatement.executeQuery();

			if (results.next()) {
				return false;
			}

			String insertQuery = "INSERT INTO users (id, type, login, password, email) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(getUserCount()));
			insertStatement.setString(2, type);
			insertStatement.setString(3, login);
			insertStatement.setString(4, password);
			insertStatement.setString(5, email);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	int getUserCount() {
		try {
			String query = "SELECT COUNT(id) AS max FROM USERS";
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

	public ArrayList<Offer> getOffers(int begin, int end) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			if (end >= begin) {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? AND id_offer <= ?";
			}
			else {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? OR id_offer <= ?";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, begin);
			preparedStatement.setInt(2, end);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_person = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_offer, id_person, title, content, info);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return offers;
	}

	public ArrayList<Offer> getProfiles(int begin, int end) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			if (end >= begin) {
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud >= ? AND id_stud <= ?";
			}
			else {
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud >= ? OR id_stud <= ?";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, begin);
			preparedStatement.setInt(2, end);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_person, title, content, info);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return offers;
	}

	public int getOffersCount(String type) {
		try {
			String query;
			if (type.equals("offers")) {
				query = "SELECT COUNT(*) AS count FROM OFFERS";
			}
			else {
				query = "SELECT COUNT(*) AS count FROM STUDENT_PROFILES";
			}
			ResultSet results = statement.executeQuery(query);

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_offer, id_empl, title, content, info);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return offers;
	}

	public ArrayList<Offer> getOffers(int begin, int end, String empl_id) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			if (end >= begin) {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? AND id_offer <= ? AND id_empl =?";
			}
			else {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? OR id_offer <= ? AND id_empl =?";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, begin);
			preparedStatement.setInt(2, end);
			preparedStatement.setString(3, empl_id);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_offer, id_empl, title, content, info);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return offers;
	}

	public boolean deleteOffer(String id_offer) {
		try {

			String insertQuery = "DELETE FROM offers WHERE offer_id = ?";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(id_offer));
			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	int getLastOffer() {
		try {
			String query = "SELECT MAX(id_offer) AS last_offer FROM offers";
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				return results.getInt("last_offer");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public int getOfferCount() {
		try {
			String query = "SELECT COUNT(*) AS count FROM OFFERS";
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				return results.getInt("count");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public ArrayList<Offer> getSearchedOffers(String offerTitle) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query = "SELECT * FROM OFFERS WHERE title = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_offer, id_empl, title, content, info);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return searchedoffers;
	}

	/*
    Function gets user type and their ID to search in database for 
    all notifications assigned to that account
	 */
	public ArrayList<Notification> getNotifications(String userID) {
		ArrayList<Notification> notifications = new ArrayList<>();
		String userType = getUserType(userID);

		try {
			if (userType.equals("student")) {
				String query = """
                SELECT 
                    mailbox_student.id_box_stud,  
                    mailbox_student.mess_type, 
                    users_login.login AS employee_login, 
                    offers.title
                FROM offers
                INNER JOIN 
                    mailbox_student ON offers.id_offer = mailbox_student.id_offer
                INNER JOIN 
                    users ON mailbox_student.id_stud = users.id_user
                LEFT JOIN 
                    users AS users_login ON offers.id_empl = users_login.id_user
                WHERE users.id_user = ?""";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, userID);
				ResultSet results = preparedStatement.executeQuery();

				while (results.next()) {
					String notificationID = results.getString("id_box_stud");
					String messageType = results.getString("mess_type");
					String employeeLogin = results.getString("employee_login");
					String offerTitle = results.getString("title");

					Notification notification = new Notification(notificationID, messageType, employeeLogin, offerTitle);
					notifications.add(notification);
				}
			}
			else if (userType.equals("employer")) {
				String query = """
                SELECT
                    mailbox_employer.id_box_emp, 
                    users.login AS student_login,
                    mailbox_employer.mess_type,
                    offers.title
                FROM users
                INNER JOIN 
                    mailbox_employer ON users.id_user = mailbox_employer.id_stud
                INNER JOIN 
                    offers ON mailbox_employer.id_offer = offers.id_offer
                LEFT JOIN 
                    users AS users_login ON offers.id_empl = users_login.id_user
                WHERE offers.id_empl = ?""";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, userID);
				ResultSet results = preparedStatement.executeQuery();

				while (results.next()) {
					String notificationID = results.getString("id_box_emp");
					String studentLogin = results.getString("student_login");
					String messageType = results.getString("mess_type");
					String offerTitle = results.getString("title");

					Notification notification = new Notification(notificationID, messageType, studentLogin, offerTitle);
					notifications.add(notification);
				}
			}
			else {
				// Obsługa innego typu użytkownika
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return notifications;
	}

	public boolean addOffer(String id_empl, String title, String content, String info, String salary) {
		try {
			int newMax = getLastOffer() + 1;
			String dotSalary = salary;
			String insertQuery = "INSERT INTO offers (id_offer, id_empl, title, content, info, salary) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(newMax));
			insertStatement.setString(2, id_empl);
			insertStatement.setString(3, title);
			insertStatement.setString(4, content);
			insertStatement.setString(5, info);
			if (salary.contains(",")) {
				dotSalary = salary.replace(",", ".");
			}
			else {
				dotSalary = salary;
			}
			insertStatement.setString(6, dotSalary);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean editOffer(String id_offer, String title, String content, String info, String salary) {
		try {
			String dotSalary;
			String insertQuery = "UPDATE offers SET title = ?, content = ?, info = ?, salary = ? WHERE (id_offer = ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, title);
			insertStatement.setString(2, content);
			insertStatement.setString(3, info);
			if (salary.contains(",")) {
				dotSalary = salary.replace(",", ".");
			}
			else {
				dotSalary = salary;
			}
			insertStatement.setString(4, dotSalary);
			insertStatement.setString(5, String.valueOf(id_offer));

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	String getUserType(String userID) {
		try {
			String query = "SELECT type FROM users WHERE id_user = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userID);
			ResultSet results = preparedStatement.executeQuery();

			if (results.next()) {
				String type = results.getString("type");
				return type;
			}
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		return "No type assigned or no user";
	}

	public ArrayList<Offer> getSearchedProfiles(String offerTitle) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query = "SELECT * FROM STUDENT_PROFILES WHERE title = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_person, title, content, info);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getSortedAndFilteredOffers(int min, int max, int type, String searched) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String sortBy = "title";
			String sortOrder = "ASC";

			if (type % 2 == 0) {
				if (type == 2) {
					sortBy = "content";
				}
				else if (type == 4) {
					sortBy = "percentage";
				}
			}
			else {
				if (type == 3) {
					sortBy = "content";
				}
				else if (type == 5) {
					sortBy = "percentage";
				}
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM OFFERS";

			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = " + searched;
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND content BETWEEN " + min + " AND " + max;
				}
				else {
					query += " WHERE content BETWEEN " + min + " AND " + max;
				}
			}

			query += " ORDER BY " + sortBy + " " + sortOrder;

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_person = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_offer, id_person, title, content, info);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getSortedAndFilteredProfiles(int min, int max, int type, String searched) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String sortBy = "title";
			String sortOrder = "ASC";

			if (type % 2 == 0) {
				if (type == 2) {
					sortBy = "content";
				}
				else if (type == 4) {
					sortBy = "percentage";
				}
			}
			else {
				if (type == 3) {
					sortBy = "content";
				}
				else if (type == 5) {
					sortBy = "percentage";
				}
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM STUDENT_PROFILES";

			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = " + searched;
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND content BETWEEN " + min + " AND " + max;
				}
				else {
					query += " WHERE content BETWEEN " + min + " AND " + max;
				}
			}

			query += " ORDER BY " + sortBy + " " + sortOrder;

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");

				Offer offer = new Offer(id_person, title, content, info);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}
}
