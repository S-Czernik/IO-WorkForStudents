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
import jakarta.servlet.http.Cookie;

@WebServlet(name = "OffersDisplayServlet", urlPatterns = {"/offersdisplay"})
public class OffersDisplayServlet extends HttpServlet {
    Model model;
    ArrayList<Offer> offers = new ArrayList<>();
    Random random;
    int begin, end;

    public OffersDisplayServlet() {
        model = Model.getModel();
        begin = 0;
        end = 9;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plaintext;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String arg1 = request.getParameter("arg1");
            offers.clear();
            
            Cookie[] cookies = request.getCookies();
            
            if (arg1.equals("0")) {
                int count = model.getOfferCount() - 10;
                random = new Random();
                begin = random.nextInt(count);
                end = begin + 9;
            } else if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("begin"))
                        begin = Integer.parseInt(cookie.getValue());
                    else if (cookie.getName().equals("end"))
                        end = Integer.parseInt(cookie.getValue());
                }
                
                if (arg1.equals("-1")) {
                    begin -= 10;
                    if (begin < 0)
                        begin = 0;
                    end = begin + 9;
                } else if (arg1.equals("1")) {
                    end += 10;
                    int count = model.getOfferCount() - 1;
                    if (end > count)
                        end = count;
                    begin = end - 9;
                }
            }

            offers = model.getOffers(begin, end);
            
            Cookie beginCookie = new Cookie("begin", String.valueOf(begin));
            Cookie endCookie = new Cookie("end", String.valueOf(end));
            response.addCookie(beginCookie);
            response.addCookie(endCookie);

            StringBuilder jsonOffers = new StringBuilder("[");
            for (int i = 0; i < offers.size(); i++) {
                Offer offer = offers.get(i);
                
                jsonOffers.append("{")
                        .append("\"id_offer\": \"").append(offer.getIdOffer()).append("\",")
                        .append("\"id_empl\": \"").append(offer.getIdEmpl()).append("\",")
                        .append("\"title\": \"").append(offer.getTitle()).append("\",")
                        .append("\"content\": \"").append(offer.getContent()).append("\",")
                        .append("\"info\": \"").append(offer.getInfo()).append("\"")
                        .append("}");
                if (i < offers.size() - 1)
                    jsonOffers.append(",");
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

