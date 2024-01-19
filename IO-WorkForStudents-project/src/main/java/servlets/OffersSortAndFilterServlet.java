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

@WebServlet(name = "OffersSortAndFilterServlet", urlPatterns = {"/sortAndFilterOff"})
public class OffersSortAndFilterServlet extends HttpServlet {

	Model model;
	int first, last, number;

	public OffersSortAndFilterServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			int min = Integer.parseInt(request.getParameter("arg1"));
			int max = Integer.parseInt(request.getParameter("arg2"));
			int sort = Integer.parseInt(request.getParameter("arg3"));
			String search = request.getParameter("arg4");
			String arg = request.getParameter("arg5");
			ArrayList<Offer> offers = new ArrayList<>();

			if (arg.equals("1")) {
				offers = model.offerInterface.getSortedAndFilteredOffers(min, max, sort, search, last, number, 1);
			} else if (arg.equals("-1")) {
				offers = model.offerInterface.getSortedAndFilteredOffers(min, max, sort, search, first, 0, -1);
			} else {
				offers = model.offerInterface.getSortedAndFilteredOffers(min, max, sort, search, 0, 0, 0);
			}
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
							.append("\"info\": \"").append(offer.getInfo()).append("\",")
							.append("\"salary\": \"").append(offer.getSalary()).append("\"")
							.append("}");
					if (i < offers.size() - 1) {
						jsonOffers.append(",");
					} else if (i == offers.size() - 1) {
						last = offer.getIdOffer();
					}
					if (i == 0) {
						first = offer.getIdOffer();
					}
				}
			} else {
				jsonOffers.append("{")
						.append("\"title\": \"").append("Offer not found!").append("\"")
						.append("}");
			}
			jsonOffers.append("]");

			out.println(jsonOffers.toString());
		} catch (Exception exp) {
			System.out.println(exp);
		}
	}
}