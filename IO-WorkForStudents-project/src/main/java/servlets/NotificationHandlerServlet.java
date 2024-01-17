/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import model.notifications.Notification;

/**
 *
 * @author adamk
 */
@WebServlet(name = "NotificationHandlerServlet", urlPatterns = {"/NotificationHandlerServlet"})
public class NotificationHandlerServlet extends HttpServlet {

   Model model;

	public NotificationHandlerServlet() {
		model = Model.getModel();
	}
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            String action = request.getParameter("arg1");
            String notif_id = request.getParameter("arg2");
            String user_id = request.getParameter("arg3");
            String userType = model.accountInterface.getUserType(user_id);
            Notification notif = model.notificationInterface.getNotification(notif_id,userType);
            String recieverType;
            
            if(userType.equals("student"))
                recieverType = "employer";
            else if(userType.equals("employer"))
                recieverType = "student";
            else
                recieverType = "unknown";
            
            //need to delete notification from users list ASAP 
            if (action.equals("accept")) {
                model.notificationInterface.createNotification(notif,recieverType,action);
                model.notificationInterface.deleteNotification(notif_id, userType);
            } else if (action.equals("reject")) {
                model.notificationInterface.deleteNotification(notif_id, userType);
            } else if (action.equals("delete")) {
                model.notificationInterface.deleteNotification(notif_id, userType);
            } else {
                // Something else
            }
           
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
