package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import servlets.helper.Helper;

@WebServlet(name = "OfferDeletionServlet", urlPatterns = {"/OfferDeletionServlet"})
public class OfferDeletionServlet extends HttpServlet {

	Model model;

	public OfferDeletionServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		try {
			int id_offer = Helper.getIntValueOf(request.getParameter("arg1"));
			boolean deleted = model.offerInterface.deleteOffer(id_offer);

			// Send a response to the client
			response.getWriter().write(deleted ? "true" : "false");
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}
