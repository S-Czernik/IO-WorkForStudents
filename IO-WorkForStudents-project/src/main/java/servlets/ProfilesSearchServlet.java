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

@WebServlet(name = "ProfilesSearchServlet", urlPatterns = {"/searchprof"})
public class ProfilesSearchServlet extends HttpServlet {

	Model model;
	int first, last;

	public ProfilesSearchServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			String arg1 = request.getParameter("arg1");
			String arg2 = request.getParameter("arg2");
			ArrayList<Offer> offers = new ArrayList<>();

			if (arg2.equals("1")) {
				offers = model.offerInterface.getSearchedProfiles(arg1, last, 0);
			} else if (arg2.equals("-1")) {
				offers = model.offerInterface.getSearchedProfiles(arg1, first, 1);
			} else {
				first = model.offerInterface.getLastProfileId();
				offers = model.offerInterface.getSearchedProfiles(arg1, first, 0);
			}

			StringBuilder jsonOffers = new StringBuilder("[");
			if (!offers.isEmpty()) {
				for (int i = 0; i < offers.size(); i++) {
					Offer offer = offers.get(i);

					jsonOffers.append("{")
							.append("\"id_person\": \"").append(offer.getIdPerson()).append("\",")
							.append("\"title\": \"").append(offer.getTitle()).append("\",")
							.append("\"content\": \"").append(offer.getContent()).append("\",")
							.append("\"rating\": \"").append(offer.getRating()).append("\"")
							.append("}");
					if (i < offers.size() - 1) {
						jsonOffers.append(",");
					} else if (i == offers.size() - 1) {
						last = offer.getIdPerson() - 1;
					}
					if (i == 0) {
						first = offer.getIdPerson();
					}
				}
			} else {
				offers = model.offerInterface.getSearchedProfiles(arg1, first, 0);
				if (!offers.isEmpty()) {
					for (int i = 0; i < offers.size(); i++) {
						Offer offer = offers.get(i);

						jsonOffers.append("{")
								.append("\"id_person\": \"").append(offer.getIdPerson()).append("\",")
								.append("\"title\": \"").append(offer.getTitle()).append("\",")
								.append("\"content\": \"").append(offer.getContent()).append("\",")
								.append("\"rating\": \"").append(offer.getRating()).append("\"")
								.append("}");
						if (i < offers.size() - 1) {
							jsonOffers.append(",");
						} else if (i == offers.size() - 1) {
							last = offer.getIdPerson() - 1;
						}
						if (i == 0) {
							first = offer.getIdPerson();
						}
					}
				} else {
					jsonOffers.append("{")
							.append("\"title\": \"").append("Profile not found!").append("\"")
							.append("}");
				}
			}
			jsonOffers.append("]");

			out.println(jsonOffers.toString());
		} catch (Exception exp) {
			System.out.println(exp);
		}
	}
}