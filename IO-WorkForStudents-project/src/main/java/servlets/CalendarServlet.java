package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;

@WebServlet(name = "CalendarServlet", urlPatterns = {"/calendar"})
public class CalendarServlet extends HttpServlet {

	Model model;

	public CalendarServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		String requestType = request.getParameter("rqtype");

		PrintWriter out = response.getWriter();
		switch (requestType) {
			case "getuser" -> {
				out.print(model.loadStudentCalendarFromDatabase(request.getParameter("login")));
			}
			case "getoffer" -> {
				out.print(model.loadOfferCalendarFromDatabase(Integer.valueOf(request.getParameter("offerid"))));
			}
			case "setuser" -> {

			}
			case "setoffer" -> {

			}
			case "compare" -> {

			}
		}
		out.close();
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
