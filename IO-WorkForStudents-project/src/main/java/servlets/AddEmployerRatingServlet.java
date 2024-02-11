package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import model.Model;
import model.notifications.Notification;
import servlets.helper.Helper;

/**
 *
 * @author adamk
 */
@WebServlet(name = "AddEmployerRatingServlet", urlPatterns = {"/AddEmployerRatingServlet"})
public class AddEmployerRatingServlet extends HttpServlet {

	Model model;

	public AddEmployerRatingServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		int userID = Helper.getIntValueOf(request.getParameter("arg1"));
		String content = request.getParameter("arg2");
		int stars = Helper.getIntValueOf(request.getParameter("arg3"));

		boolean created = model.ratingInterface.addEmployerRating(userID, content, stars);

	}


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
