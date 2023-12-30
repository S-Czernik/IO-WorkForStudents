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
import java.util.ArrayList;
import model.Model;
import model.Notification;


/**
 *
 * @author adamk
 */
@WebServlet(name = "NotificationServlet", urlPatterns = {"/NotificationServlet"})
public class NotificationServlet extends HttpServlet {

    Model model;
    String userType;
    ArrayList<Notification> notifications = new ArrayList<>();

    public NotificationServlet() {
        model = Model.getModel();
    }
    

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    
    String userID = request.getParameter("arg1");
    try (PrintWriter out = response.getWriter()) {
        this.notifications.clear();
        this.userType = model.getUserType(userID);
        this.notifications = model.getNotifications(userType, userID);
        
        StringBuilder jsonNotifications = new StringBuilder("[");
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            jsonNotifications.append("{")
                             .append("\"ID\": \"").append(notification.getID()).append("\",")
                             .append("\"messageType\": \"").append(notification.getMessageType()).append("\",")
                             .append("\"userLogin\": \"").append(notification.getUserLogin()).append("\",")
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
    }
}
