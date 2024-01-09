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
	public ArrayList<Notification> getNotifications(String userID) {
		ArrayList<Notification> notifications = new ArrayList<>();
		String userType = model.accountInterface.getUserType(userID);

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
}
