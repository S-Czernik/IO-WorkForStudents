package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import model.Model;
import servlets.helper.Helper;

@WebServlet(name = "OfferEditServlet", urlPatterns = {"/OfferEditServlet"})
public class OfferEditServlet extends HttpServlet {

	Model model;

	public OfferEditServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		int id_offer = Helper.getIntValueOf(request.getParameter("arg1"));
		String title = request.getParameter("arg2");
		String content = request.getParameter("arg3");
		String salary = request.getParameter("arg5");
		String tags = request.getParameter("arg6");

		boolean edited = model.offerInterface.editOffer(id_offer, title, content, salary, tags);

		try (PrintWriter out = response.getWriter()) {
			out.print(edited);
		}
	}
}
