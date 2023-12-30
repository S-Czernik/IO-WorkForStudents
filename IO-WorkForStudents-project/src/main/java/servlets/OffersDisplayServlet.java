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
import model.Offer;

@WebServlet(name = "OffersDisplayServlet", urlPatterns = {"/offersdisplay"})
public class OffersDisplayServlet extends HttpServlet {
    Model model;
    ArrayList<Offer> offers = new ArrayList<>();

    public OffersDisplayServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plaintext;charset=UTF-8");

        int x = 0;
        try (PrintWriter out = response.getWriter()) {
            offers.clear();
            
            int count = model.getOfferCount() - 1;
            Random random = new Random();
            int begin = random.nextInt(count);
            int end = begin + 9;

            offers = model.getOffers(begin, end);

            StringBuilder jsonOffers = new StringBuilder("[");
            for (int i = 0; i < offers.size(); i++) {
                Offer offer = offers.get(i);
                
                jsonOffers.append("{")
                        .append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
                        .append("\"id_empl\": \"").append(offer.getIdEmpl()).append("\",")
                        .append("\"title\": \"").append(offer.getTitle()).append("\",")
                        .append("\"content\": \"").append(offer.getContent()).append("\"")
                        .append("\"info\": \"").append(offer.getInfo()).append("\"")
                        .append("}");
                if (i < offers.size() - 1) {
                    jsonOffers.append(",");
                }
            }
            jsonOffers.append("]");

            out.println(jsonOffers.toString());
        } catch (Exception exp) { }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

