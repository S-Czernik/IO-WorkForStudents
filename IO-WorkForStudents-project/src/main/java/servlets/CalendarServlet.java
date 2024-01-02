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

		String login = request.getParameter("login");
		int offerID = Integer.parseInt(request.getParameter("offerid"));

		PrintWriter out = response.getWriter();
		switch (requestType) {
			case "getuser" -> {
				out.print(model.loadStudentCalendarFromDatabase(login).getCSV());
			}
			case "getoffer" -> {
				out.print(model.loadOfferCalendarFromDatabase(offerID).getCSV());
			}
			case "setuser" -> {
				model.saveStudentCalendarToDatabase(login,);
			}
			case "setoffer" -> {

			}
			case "compare" -> {

			}
		}
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plaintext;charset=UTF-8");

		String requestType = request.getHeader("rqtype");

		String login = request.getHeader("login");
		int offerID = Integer.parseInt(request.getHeader("offerid"));
		String csv = request.getHeader("csv");

		PrintWriter out = response.getWriter();
		switch (requestType) {
			case "setuser" -> {
				model.saveStudentCalendarToDatabase(login, csv);
			}
			case "setoffer" -> {
				model.saveOfferCalendarToDatabase(offerID, csv);
			}
		}
		out.close();
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
