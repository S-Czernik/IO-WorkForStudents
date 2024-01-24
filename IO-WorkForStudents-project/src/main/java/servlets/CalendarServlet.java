package servlets;

import model.calendar.Kalyndarz;
import model.calendar.CalendarHTMLBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import servlets.helper.Helper;

@WebServlet(name = "CalendarServlet", urlPatterns = {"/calendar"})
public class CalendarServlet extends HttpServlet {

	Model model;

	public CalendarServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestType = request.getParameter("rqtype");

		int userID = Helper.getIntValueOf(request.getParameter("userid"));
		int offerID = Helper.getIntValueOf(request.getParameter("offerid"));

		PrintWriter out = response.getWriter();

		if (requestType != null) {
			switch (requestType) {
				case "getusercsv" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					out.print(model.calendarInterface.getStudentCalendarCsv(userID));
				}
				case "getoffercsv" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					out.print(model.calendarInterface.getOfferCalendarCsv(offerID));
				}
				case "getuserhtml" -> {
					response.setContentType("text/html;charset=UTF-8");
					out.print(model.calendarInterface.getStudentCalendarHtml(userID));
				}
				case "getofferhtml" -> {
					response.setContentType("text/html;charset=UTF-8");
					out.print(model.calendarInterface.getOfferCalendarHtml(offerID));
				}
				case "compare" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					out.print(model.calendarInterface.compareCalendars(userID, offerID));
				}
			}
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			Kalyndarz test = new Kalyndarz();
			test.loadCSV("762,863,1");
			out.print(CalendarHTMLBuilder.get(test));
		}
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plaintext;charset=UTF-8");

		String requestType = request.getHeader("rqtype");

		int userID = Helper.getIntValueOf(request.getHeader("userid"));
		int offerID = Helper.getIntValueOf(request.getHeader("offerid"));
		String csv = request.getHeader("csv");

		PrintWriter out = response.getWriter();
		switch (requestType) {
			case "setuser" -> {
				model.calendarInterface.saveStudentCalendarToDatabase(userID, csv);
			}
			case "setoffer" -> {
				model.calendarInterface.saveOfferCalendarToDatabase(offerID, csv);
			}
		}
		out.close();
	}
}
