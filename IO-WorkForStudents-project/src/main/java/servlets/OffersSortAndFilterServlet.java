package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Model;
import model.offers.Offer;

@WebServlet(name = "OffersSortAndFilterServlet", urlPatterns = {"/sortAndFilterOff"})
public class OffersSortAndFilterServlet extends HttpServlet {

	Model model;

	public OffersSortAndFilterServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			int arg1 = Integer.parseInt(request.getParameter("arg1"));
			int arg2 = Integer.parseInt(request.getParameter("arg2"));
			int arg3 = Integer.parseInt(request.getParameter("arg3"));
			ArrayList<Offer> offers = new ArrayList<>();

			String arg4 = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("searchedO")) {
						arg4 = cookie.getValue();
						break;
					}
				}
			}

			offers = model.offerInterface.getSortedAndFilteredOffers(arg1, arg2, arg3, arg4);

			StringBuilder jsonOffers = new StringBuilder("[");
			if (!offers.isEmpty()) {
				for (int i = 0; i < offers.size(); i++) {
					Offer offer = offers.get(i);

					jsonOffers.append("{")
							.append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
							.append("\"id_empl\": \"").append(offer.getIdPerson()).append("\",")
							.append("\"title\": \"").append(offer.getTitle()).append("\",")
							.append("\"content\": \"").append(offer.getContent()).append("\",")
							.append("\"info\": \"").append(offer.getInfo()).append("\"")
							.append("}");
					if (i < offers.size() - 1) {
						jsonOffers.append(",");
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
