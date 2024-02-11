package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import servlets.helper.Helper;

@WebServlet(name = "OfferAddingServlet", urlPatterns = {"/offerAddingServlet"})
public class OfferAddingServlet extends HttpServlet {

	Model model;

	public OfferAddingServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		int userID = Helper.getIntValueOf(request.getParameter("arg1"));
		String title = request.getParameter("arg2");
		String content = request.getParameter("arg3");
		String salary = request.getParameter("arg4");
		String tags = request.getParameter("arg5");

		boolean created = model.offerInterface.addOffer(userID, title, content, salary, tags);

		PrintWriter out = response.getWriter();
		out.print(created);
		out.close();
	}
}
