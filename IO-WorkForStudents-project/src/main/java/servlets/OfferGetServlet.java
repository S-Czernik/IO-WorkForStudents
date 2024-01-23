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
import model.offers.Offer;

/**
 *
 * @author adamk
 */
@WebServlet(name = "OfferGetServlet", urlPatterns = {"/OfferGetServlet"})
public class OfferGetServlet extends HttpServlet {

	Model model;

	public OfferGetServlet() {
		model = Model.getModel();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
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
		try (PrintWriter out = response.getWriter()) {
			String id_offer = request.getParameter("arg1");
			Offer offer = model.offerInterface.getOffer(id_offer);

			if (offer != null) {
				// Offer found, construct JSON response
				StringBuilder jsonOffers = new StringBuilder("[");
				jsonOffers.append("{")
						.append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
						.append("\"id_person\": \"").append(offer.getIdPerson()).append("\",")
						.append("\"title\": \"").append(offer.getTitle()).append("\",")
						.append("\"content\": \"").append(offer.getContent()).append("\",")
						.append("\"info\": \"").append(offer.getInfo()).append("\",")
						.append("\"salary\": \"").append(offer.getSalary()).append("\"")
						.append("}");
				jsonOffers.append("]");
				out.println(jsonOffers.toString());
			} else {
				// Offer not found, return an empty response or an error message
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(""); // Empty response or a specific error message
			}
		} catch (Exception exp) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			System.out.println(exp);
		}
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