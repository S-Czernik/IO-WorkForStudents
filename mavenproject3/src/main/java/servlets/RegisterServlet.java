package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/registerServlet"})
public class RegisterServlet extends HttpServlet {

	Model model;

	public RegisterServlet() {
		model = Model.getModel();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plaintext;charset=UTF-8");

		String login = request.getParameter("arg1");
		String passwd = request.getParameter("arg2");
		String email = request.getParameter("arg3");
		String type = request.getParameter("arg4");

		boolean registered = model.register(login, passwd, email, type);

		PrintWriter out = response.getWriter();
		out.print(registered);
		out.close();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}