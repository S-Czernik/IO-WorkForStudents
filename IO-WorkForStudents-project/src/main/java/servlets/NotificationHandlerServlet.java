package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import model.notifications.Notification;
import servlets.helper.Helper;

@WebServlet(name = "NotificationHandlerServlet", urlPatterns = {"/NotificationHandlerServlet"})
public class NotificationHandlerServlet extends HttpServlet {

	Model model;

	public NotificationHandlerServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			String action = request.getParameter("arg1");

			int notif_id = Helper.getIntValueOf(request.getParameter("arg2"));
			int user_id = Helper.getIntValueOf(request.getParameter("arg3"));
			String userType = model.accountInterface.getUserType(user_id);
			Notification notif = model.notificationInterface.getNotification(notif_id, userType);
			String recieverType;

			if (userType.equals("student")) {
				recieverType = "employer";
			}
			else if (userType.equals("employer")) {
				recieverType = "student";
			}
			else {
				recieverType = "unknown";
			}

			//need to delete notification from users list ASAP 
			if (action.equals("accept")) {
				model.notificationInterface.createNotification(notif, recieverType, action);
				model.notificationInterface.deleteNotification(notif_id, userType);
			}
			else if (action.equals("reject")) {
				model.notificationInterface.deleteNotification(notif_id, userType);
			}
			else if (action.equals("delete")) {
				model.notificationInterface.deleteNotification(notif_id, userType);
			}
			else {
				// Something else
			}

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}