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

		String login = request.getParameter("login");
		String idString = request.getParameter("offerid");
		int offerID = (idString != null ? Integer.parseInt(idString) : -1);

		PrintWriter out = response.getWriter();

		if (requestType != null) {
			switch (requestType) {
				case "getusercsv" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					out.print(model.calendarInterface.getStudentCalendarCsv(login));
				}
				case "getoffercsv" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					out.print(model.calendarInterface.getOfferCalendarCsv(offerID));
				}
				case "getuserhtml" -> {
					response.setContentType("text/html;charset=UTF-8");
					out.print(model.calendarInterface.getStudentCalendarHtml(login));
				}
				case "getofferhtml" -> {
					response.setContentType("text/html;charset=UTF-8");
					out.print(model.calendarInterface.getOfferCalendarHtml(offerID));
				}
				case "compare" -> {
					response.setContentType("text/plaintext;charset=UTF-8");
					out.print(model.calendarInterface.compareCalendars(login, offerID));
				}
			}
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			Kalyndarz test = new Kalyndarz();
			test.loadCSV("45,1380,1,450,1200,2,360,1020,3,270,840,4");
			out.print(CalendarHTMLBuilder.get(test));
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
				model.calendarInterface.saveStudentCalendarToDatabase(login, csv);
			}
			case "setoffer" -> {
				model.calendarInterface.saveOfferCalendarToDatabase(offerID, csv);
			}
		}
		out.close();
	}
}
