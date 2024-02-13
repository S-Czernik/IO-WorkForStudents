package model.notifications;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Interface;

public class NotificationInterface extends Interface {

	/*
    Function gets user type and their ID to search in database for 
    all notifications assigned to that account
	 */
	public ArrayList<Notification> getNotifications(int userID) {
		ArrayList<Notification> notifications = new ArrayList<>();
		String userType = model.accountInterface.getUserType(userID);

		try {
			if (userType.equals("student")) {
				String query = """			
			SELECT 
                mailbox_student.id_box_stud,
                mailbox_student.id_stud,
                mailbox_student.mess_type, 
                users_login.login AS employee_login, 
                offers.title,
                offers.id_offer,
                offers.id_empl
            FROM offers
            INNER JOIN 
                mailbox_student ON offers.id_offer = mailbox_student.id_offer
            INNER JOIN 
                users ON mailbox_student.id_stud = users.id_user
            LEFT JOIN 
                users AS users_login ON offers.id_empl = users_login.id_user
            WHERE users.id_user = ?""";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, userID);
				ResultSet results = preparedStatement.executeQuery();

				while (results.next()) {
					int notificationID = results.getInt("id_box_stud");
					String messageType = results.getString("mess_type");
					String employeeLogin = results.getString("employee_login");
					String offerTitle = results.getString("title");
					int IDOffer = results.getInt("id_offer");
					int IDEmployer = results.getInt("id_empl");

					Notification notification = new Notification(notificationID, messageType, employeeLogin, offerTitle, userID, IDOffer, IDEmployer);
					notifications.add(notification);
				}
			}
			else if (userType.equals("employer")) {
				String query = """
            SELECT
                mailbox_employer.id_box_emp,
                mailbox_employer.id_stud,
                users.login AS student_login,
                mailbox_employer.mess_type,
                offers.title,
                offers.id_offer                
            FROM users
            INNER JOIN 
                mailbox_employer ON users.id_user = mailbox_employer.id_stud
            INNER JOIN 
                offers ON mailbox_employer.id_offer = offers.id_offer
            LEFT JOIN 
                users AS users_login ON offers.id_empl = users_login.id_user
            WHERE offers.id_empl = ?""";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, userID);
				ResultSet results = preparedStatement.executeQuery();

				while (results.next()) {
					int notificationID = results.getInt("id_box_emp");
					String studentLogin = results.getString("student_login");
					String messageType = results.getString("mess_type");
					String offerTitle = results.getString("title");
					int IDOffer = results.getInt("id_offer");
                    int IDStudent = results.getInt("id_stud");

					Notification notification = new Notification(notificationID, messageType, studentLogin, offerTitle, userID, IDOffer, IDStudent);
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

	public int getLastEmpNotification() {
		try {
			String query = "SELECT MAX(id_box_emp) AS max FROM mailbox_employer";
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

	public int getLastStudNotification() {
		try {
			String query = "SELECT MAX(id_box_stud) AS max FROM mailbox_student";
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

	public Notification getNotification(int id_notif, String userType) {
		try {
			if (userType.equals("student")) {
				String query = "SELECT * FROM mailbox_student WHERE id_box_stud = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id_notif);
				ResultSet results = preparedStatement.executeQuery();

				if (results.next()) {
					int id_offer = results.getInt("id_offer");
					String mess_type = results.getString("mess_type");
					int id_stud = results.getInt("id_stud");
					Notification notif = new Notification(id_notif, id_stud, id_offer, mess_type);
					// remember to swap id_offer and id_stud when creating new Notif for employer in database
					return notif;
				}
			}
			else if (userType.equals("employer")) {
				String query = "SELECT * FROM mailbox_employer WHERE id_box_emp = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id_notif);
				ResultSet results = preparedStatement.executeQuery();

				if (results.next()) {
					int id_offer = results.getInt("id_offer");
					int id_stud = results.getInt("id_stud");
					String mess_type = results.getString("mess_type");
					Notification notif = new Notification(id_notif, id_stud, id_offer, mess_type);
					// remember to swap id_offer and id_stud when creating new Notif for student in database
					return notif;
				}

			}
		}
		catch (Exception e) {
			System.out.println(e);
			Notification notif = new Notification(-1, -1, -1, "");
			return notif;
		}
		Notification notif = new Notification(-1, -1, -1, "");
		return notif;
	}

	public void createNotification(Notification notif, String userType, String action) {
		try {
			if (userType.equals("student")) {
				int newMax = getLastStudNotification() + 1;
				String insertQuery = "INSERT INTO mailbox_student (id_box_stud, id_offer, id_stud, mess_type) VALUES (?, ?, ?, ?)";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setString(1, String.valueOf(newMax));
				insertStatement.setInt(2, notif.getOfferID());
				insertStatement.setInt(3, notif.getUserID());
				if (notif.getMessageType().equals("application")) {
					insertStatement.setString(4, "accepted");
					insertStatement.executeUpdate();
				}
                                else if (action.equals("newOffer")) {
                                        insertStatement.setString(4, action);
                                        insertStatement.executeUpdate();
                                }
				else if (action.equals("contactEmployer")) {
					insertStatement.setString(4, action);
					insertStatement.executeUpdate();
				}
			}
			else if (userType.equals("employer")) {
				int newMax = getLastEmpNotification() + 1;
				String insertQuery = "INSERT INTO mailbox_employer (id_box_emp, id_stud, id_offer, mess_type) VALUES (?, ?, ?, ?)";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setString(1, String.valueOf(newMax));
				insertStatement.setInt(2, notif.getUserID());
				insertStatement.setInt(3, notif.getOfferID());
				if (notif.getMessageType().equals("newOffer")) {
					insertStatement.setString(4, "acceptation");
					insertStatement.executeUpdate();
				}
                                else if (action.equals("application")) {
                                    insertStatement.setString(4, action);
                                    insertStatement.executeUpdate();
                                }
                                else if (action.equals("contactStudent")) {
					insertStatement.setString(4, action);
					insertStatement.executeUpdate();
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void deleteNotification(int id_notif, String userType) {
		try {
			if (userType.equals("student")) {
				String insertQuery = "DELETE FROM mailbox_student WHERE id_box_stud = ?";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setInt(1, id_notif);
				insertStatement.executeUpdate();
			}
			else if (userType.equals("employer")) {
				String insertQuery = "DELETE FROM mailbox_employer WHERE id_box_emp= ?";
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setInt(1, id_notif);
				insertStatement.executeUpdate();
			}
			else {
				// unnknown type
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}
}
