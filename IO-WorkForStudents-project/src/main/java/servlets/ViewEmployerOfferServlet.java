
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
import servlets.helper.Helper;


@WebServlet(name = "ViewEmployerOfferServlet", urlPatterns = {"/ViewEmployerOfferServlet"})
public class ViewEmployerOfferServlet extends HttpServlet {

	Model model;

	public ViewEmployerOfferServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			int arg = Helper.getIntValueOf(request.getParameter("arg1"));
			ArrayList<Offer> offers = new ArrayList<>();
			
			offers = model.offerInterface.getEmployerOffer(arg);

			StringBuilder jsonOffers = new StringBuilder("[");
			if (!offers.isEmpty()) {
				for (int i = 0; i < offers.size(); i++) {
					Offer offer = offers.get(i);

					jsonOffers.append("{")
							.append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
							.append("\"id_person\": \"").append(offer.getIdPerson()).append("\",")
							.append("\"title\": \"").append(offer.getTitle()).append("\",")
							.append("\"content\": \"").append(offer.getContent()).append("\",")
							.append("\"salary\": \"").append(offer.getSalary()).append("\"")
							.append("}");
					if (i < offers.size() - 1) {
						jsonOffers.append(",");
					}
				}
                        }
			jsonOffers.append("]");

			out.println(jsonOffers.toString());
		} catch (Exception exp) {
			System.out.println(exp);
		}
	}
}
