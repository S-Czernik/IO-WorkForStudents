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
import model.rating.Rating;
import servlets.helper.Helper;
/**
 *
 * @author adamk
 */
@WebServlet(name = "GetStudentRatingsServlet", urlPatterns = {"/GetStudentRatingsServlet"})
public class GetStudentRatingsServlet extends HttpServlet {

    Model model;
    
    public GetStudentRatingsServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		int userID = Helper.getIntValueOf(request.getParameter("arg1"));
		try (PrintWriter out = response.getWriter()) {
			ArrayList<Rating> ratings = model.ratingInterface.getStudentRating(userID);

			StringBuilder jsonNotifications = new StringBuilder("[");
			for (int i = 0; i < ratings.size(); i++) {
				Rating rating = ratings.get(i);
				jsonNotifications.append("{")
						.append("\"ratingID\": \"").append(rating.getIdRating()).append("\",")
						.append("\"stars\": \"").append(rating.getStars()).append("\",")
						.append("\"content\": \"").append(rating.getContent()).append("\",")
						.append("\"userID\": \"").append(rating.getUserID()).append("\"")
						.append("}");
				if (i < ratings.size() - 1) {
					jsonNotifications.append(",");
				}
			}
			jsonNotifications.append("]");

			out.println(jsonNotifications.toString());
		}
	}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
