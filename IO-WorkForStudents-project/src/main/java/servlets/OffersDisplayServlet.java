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
    int begin, end, start;

    public OffersDisplayServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plaintext;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String arg1 = request.getParameter("arg1");
            offers.clear();

            start = -1;
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
                for (Cookie cookie : cookies)
                    switch (cookie.getName()) {
                        case "start" -> start = Integer.parseInt(cookie.getValue());
                        case "begin" -> begin = Integer.parseInt(cookie.getValue());
                        case "end" -> end = Integer.parseInt(cookie.getValue());
                    }
            
            int count = model.getOfferCount() - 1;
            if (arg1.equals("0") && start == -1) {
                random = new Random();
                begin = random.nextInt(count - 9);
                end = begin + 9;
                start = begin;
                Cookie startCookie = new Cookie("start", String.valueOf(start));
                response.addCookie(startCookie);
            } else {
                if (arg1.equals("-1")) {
                    begin -= 10;
                    if (begin + 10 == start)
                        begin += 10;
                    else if (begin < 0) {
                        end = begin + 9;
                        begin = count + begin + 1;
                    } else if (begin >= start)
                        end = begin + 9;
                    else if (begin < start && end > start) {
                        end = begin + 9;
                        begin = start + 1;
                    } else 
                        end = begin + 9;
                } else if (arg1.equals("1")) {
                    end += 10;
                    if (end > count) {
                        begin = end - 9;
                        end = Math.abs(count - end) - 1;
                    }
                    else if (end < start)
                        begin = end - 9;
                    else if (end >= start && (begin < start || begin > end)) {
                        if ((begin = end - 9) == start) begin--;
                        end = start - 1;
                    } else
                        begin += 10;
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
        } catch (Exception exp) { 
            System.out.println(exp);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

