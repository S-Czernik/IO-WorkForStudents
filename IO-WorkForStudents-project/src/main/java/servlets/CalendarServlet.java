package servlets;

import model.calendar.Calendar;
import model.calendar.CalendarHTMLBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
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
				case "cmpstud" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					float result = model.calendarInterface.compareToStudent(userID, offerID);
					out.print(result != Float.NEGATIVE_INFINITY ? String.valueOf(result * 100) : "no");
				}
				case "cmpoffer" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					float result = model.calendarInterface.compareToOffer(userID, offerID);
					out.print(result != Float.NEGATIVE_INFINITY ? String.valueOf(result * 100) : "no");
				}
			}
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

		String csv = request.getReader().lines().collect(Collectors.joining(""));

		PrintWriter out = response.getWriter();
		switch (requestType) {
			case "setuser" -> {
				model.calendarInterface.saveStudentCalendarToDatabase(userID, csv);
			}
			case "setoffer" -> {
				model.calendarInterface.saveOfferCalendarToDatabase(offerID, csv);
			}
			case "gethtml" -> {
				Calendar k = new Calendar();
				k.loadCSV(csv);
				out.print(CalendarHTMLBuilder.get(k));
			}

		}
		out.close();
	}
}
