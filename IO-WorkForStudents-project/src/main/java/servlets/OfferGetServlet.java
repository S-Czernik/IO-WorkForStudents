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

@WebServlet(name = "OfferGetServlet", urlPatterns = {"/OfferGetServlet"})
public class OfferGetServlet extends HttpServlet {

	Model model;

	public OfferGetServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			response.setContentType("text/html;charset=UTF-8");
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
			}
			else {
				// Offer not found, return an empty response or an error message
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(""); // Empty response or a specific error message
			}
		}
		catch (Exception exp) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			System.out.println(exp);
		}
	}
}
