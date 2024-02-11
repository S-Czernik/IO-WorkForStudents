package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.Model;
import model.notifications.Notification;
import servlets.helper.Helper;

@WebServlet(name = "NotificationServlet", urlPatterns = {"/NotificationServlet"})
public class NotificationServlet extends HttpServlet {

	Model model;

	public NotificationServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		int userID = Helper.getIntValueOf(request.getParameter("arg1"));
		try (PrintWriter out = response.getWriter()) {
			ArrayList<Notification> notifications = model.notificationInterface.getNotifications(userID);

			StringBuilder jsonNotifications = new StringBuilder("[");
			for (int i = 0; i < notifications.size(); i++) {
				Notification notification = notifications.get(i);
				jsonNotifications.append("{")
						.append("\"ID\": \"").append(notification.getID()).append("\",")
						.append("\"messageType\": \"").append(notification.getMessageType()).append("\",")
						.append("\"userLogin\": \"").append(notification.getUserLogin()).append("\",")
						.append("\"offerID\": \"").append(notification.getOfferID()).append("\",")
						.append("\"userID\": \"").append(notification.getUserID()).append("\",")
						.append("\"studentID\": \"").append(notification.getStudentID()).append("\",")
						.append("\"offerTitle\": \"").append(notification.getOfferTitle()).append("\"")
						.append("}");
				if (i < notifications.size() - 1) {
					jsonNotifications.append(",");
				}
			}
			jsonNotifications.append("]");

			out.println(jsonNotifications.toString());
		}
	}
}
