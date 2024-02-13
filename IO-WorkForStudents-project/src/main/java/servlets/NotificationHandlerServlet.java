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
                    Notification notif = null;
		try {
			String action = request.getParameter("arg1");

			int notif_id = Helper.getIntValueOf(request.getParameter("arg2"));
                        
			int user_id = Helper.getIntValueOf(request.getParameter("arg3"));
			String userType = model.accountInterface.getUserType(user_id);
                        if(action.equals("newOffer") || action.equals("application")){
                            int offer_id = notif_id;
                            notif = new Notification(0,user_id,offer_id,action);        
                        }
                        else {
                            notif = model.notificationInterface.getNotification(notif_id, userType);
                        }
			String recieverType = null;
                        
                        if(action.equals("newOffer")|| action.equals("application")){
                            //skip switching
                        }
                        else {
                            recieverType = switch (userType) {
				case "student" ->
					"employer";
				case "employer" ->
					"student";
				default ->
					"unknown";
                            };
                        }
                        

			//need to delete notification from users list ASAP
			switch (action) {
				case "accept" -> {
					model.notificationInterface.createNotification(notif, recieverType, action);
					model.notificationInterface.deleteNotification(notif_id, userType);
                                        if(userType.equals("student")){
                                            model.notificationInterface.createNotification(notif, userType, "contactEmployer");
                                        }
                                        else if(userType.equals("employer")){
                                            model.notificationInterface.createNotification(notif, userType, "contactStudent");
                                        }
                                        else {
                                            // unnknown type
                                        }
				}
				case "reject" ->
					model.notificationInterface.deleteNotification(notif_id, userType);
				case "delete" ->
					model.notificationInterface.deleteNotification(notif_id, userType);
                                case "newOffer" ->
                                        model.notificationInterface.createNotification(notif, "student", action);
                                case "application" ->    
                                        model.notificationInterface.createNotification(notif, "employer", action);
			}

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
