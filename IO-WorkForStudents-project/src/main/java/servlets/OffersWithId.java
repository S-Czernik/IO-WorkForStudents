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
import model.offers.Offer;

@WebServlet(name = "OffersWithId", urlPatterns = {"/offersWithId"})
public class OffersWithId extends HttpServlet {

	Model model;
	int first, last, number;

	public OffersWithId() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			String id = request.getParameter("arg1");
			String arg = request.getParameter("arg2");
			ArrayList<Offer> offers;

			offers = switch (arg) {
				case "1" ->
					model.offerInterface.getOffersWithId(id, last, number, 1);
				case "-1" ->
					model.offerInterface.getOffersWithId(id, first, 0, -1);
				default ->
					model.offerInterface.getOffersWithId(id, 0, 0, 0);
			};
			number = offers.size();

			StringBuilder jsonOffers = new StringBuilder("[");
			if (!offers.isEmpty()) {
				for (int i = 0; i < offers.size(); i++) {
					Offer offer = offers.get(i);

					jsonOffers.append("{")
							.append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
							.append("\"id_empl\": \"").append(offer.getIdPerson()).append("\",")
							.append("\"title\": \"").append(offer.getTitle()).append("\",")
							.append("\"content\": \"").append(offer.getContent()).append("\",")
							.append("\"salary\": \"").append(offer.getSalary()).append("\"")
							.append("}");
					if (i < offers.size() - 1) {
						jsonOffers.append(",");
					}
					else if (i == offers.size() - 1) {
						last = offer.getIdOffer();
					}
					if (i == 0) {
						first = offer.getIdOffer();
					}
				}
			}
			else {
				jsonOffers.append("{")
						.append("\"title\": \"").append("Offer not found!").append("\"")
						.append("}");
			}
			jsonOffers.append("]");

			out.println(jsonOffers.toString());
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
	}
}
