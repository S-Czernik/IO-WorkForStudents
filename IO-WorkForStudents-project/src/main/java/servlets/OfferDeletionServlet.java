/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;

/**
 *
 * @author adamk
 */
@WebServlet(name = "OfferDeletionServlet", urlPatterns = {"/OfferDeletionServlet"})
public class OfferDeletionServlet extends HttpServlet {

    Model model;

    public OfferDeletionServlet() {
        model = Model.getModel();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
    try {
        String id_offer = request.getParameter("arg1");
        boolean deleted = model.offerInterface.deleteOffer(id_offer);

        // Send a response to the client
        response.getWriter().write(deleted ? "true" : "false");
    } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
