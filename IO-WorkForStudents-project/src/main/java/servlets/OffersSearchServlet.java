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
import model.Offer;

@WebServlet(name = "OffersSearchServlet", urlPatterns = {"/searchoff"})
public class OffersSearchServlet extends HttpServlet {
    Model model;
    ArrayList<Offer> offers = new ArrayList<>();

    public OffersSearchServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plaintext;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String arg1 = request.getParameter("arg1");
            offers.clear();

            offers = model.getSearchedOffers(arg1);

            StringBuilder jsonOffers = new StringBuilder("[");
            if (!offers.isEmpty()) {
                for (int i = 0; i < offers.size(); i++) {
                    Offer offer = offers.get(i);

                    jsonOffers.append("{")
                            .append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
                            .append("\"id_person\": \"").append(offer.getIdPerson()).append("\",")
                            .append("\"title\": \"").append(offer.getTitle()).append("\",")
                            .append("\"content\": \"").append(offer.getContent()).append("\",")
                            .append("\"info\": \"").append(offer.getInfo()).append("\"")
                            .append("}");
                    if (i < offers.size() - 1) {
                        jsonOffers.append(",");
                    }
                }
                Cookie searchCookie = new Cookie("searchedO", String.valueOf(arg1));
                response.addCookie(searchCookie);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

