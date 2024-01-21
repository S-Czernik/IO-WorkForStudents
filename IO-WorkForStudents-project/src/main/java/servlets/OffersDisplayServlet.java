package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Random;
import model.Model;
import model.offers.Offer;
import jakarta.servlet.http.Cookie;

@WebServlet(name = "OffersDisplayServlet", urlPatterns = {"/offersdisplay"})
public class OffersDisplayServlet extends HttpServlet {

	Model model;
	int start, begin, end;

	public OffersDisplayServlet() {
		model = Model.getModel();

		Random random = new Random();
		start = random.nextInt(model.offerInterface.getOfferCount() - 1);
		random();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			String arg1 = request.getParameter("arg1");
			ArrayList<Offer> offers = new ArrayList<>();

			boolean exists = false;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					switch (cookie.getName()) {
						case "startO":
							start = Integer.parseInt(cookie.getValue());
							exists = true;
							break;
						case "beginO":
							begin = Integer.parseInt(cookie.getValue());
							break;
						case "endO":
							end = Integer.parseInt(cookie.getValue());
							break;
					}
				}
			}

			int count = model.offerInterface.getOfferCount() - 1;
			if (arg1.equals("-1")) {
				begin -= 10;
				if (begin + 10 == start) {
					begin += 10;
				}
				else if (begin < 0) {
					end = begin + 9;
					begin = count + begin + 1;
				}
				else if (begin >= start) {
					end = begin + 9;
				}
				else if (begin < start && end > start) {
					end = begin + 9;
					begin = start + 1;
				}
				else {
					end = begin + 9;
				}
			}
			else if (arg1.equals("1")) {
				end += 10;
				if (end >= start && (begin < start || begin > end)) {
					if ((begin = end - 9) == start) {
						begin--;
					}
					end = start - 1;
				}
				else if (end > count) {
					begin = end - 9;
					end = Math.abs(count - end) - 1;
				}
				else if (end < start) {
					begin = end - 9;
				}
				else {
					begin += 10;
				}
			}
			else if (arg1.equals("2")) {
				random();
				Cookie searchCookie = new Cookie("searchedO", "");
				response.addCookie(searchCookie);
			}
			else if (arg1.equals("3")) {
				Random random = new Random();
				start = random.nextInt(model.offerInterface.getOfferCount() - 1);
				random();
				Cookie startCookie = new Cookie("startO", String.valueOf(start));
				response.addCookie(startCookie);
			}
			else if (!exists) {
				Cookie startCookie = new Cookie("startO", String.valueOf(start));
				response.addCookie(startCookie);
			}

			offers = model.offerInterface.getOffers(begin, end);

			Cookie beginCookie = new Cookie("beginO", String.valueOf(begin));
			Cookie endCookie = new Cookie("endO", String.valueOf(end));
			response.addCookie(beginCookie);
			response.addCookie(endCookie);

			StringBuilder jsonOffers = new StringBuilder("[");
			for (int i = 0; i < offers.size(); i++) {
				Offer offer = offers.get(i);

				jsonOffers.append("{")
						.append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
						.append("\"id_person\": \"").append(offer.getIdPerson()).append("\",")
						.append("\"title\": \"").append(offer.getTitle()).append("\",")
						.append("\"content\": \"").append(offer.getContent()).append("\",")
						.append("\"info\": \"").append(offer.getInfo()).append("\",")
                                                .append("\"salary\": \"").append(offer.getSalary()).append("\"")
						.append("}");
				if (i < offers.size() - 1) {
					jsonOffers.append(",");
				}
			}
			jsonOffers.append("]");

			out.println(jsonOffers.toString());
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
	}

	public void random() {
		begin = start;
		end = begin + 9;
		int count = model.offerInterface.getOfferCount() - 1;
		if (end > count) {
			end = Math.abs(count - end) - 1;
		}
	}
}
