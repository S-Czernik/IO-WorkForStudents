package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import model.Model;
import servlets.helper.Helper;

@WebServlet(name = "ProfileEmployerServlet", urlPatterns = {"/ProfileEmployerServlet"})
public class ProfileEmployerServlet extends HttpServlet {

	Model model;

	public ProfileEmployerServlet() {
		model = Model.getModel();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		int userID = Helper.getIntValueOf(request.getParameter("arg1"));
		try (PrintWriter out = response.getWriter()) {
			byte[] picture = model.accountInterface.getProfilePicture(userID);
			String base64Image = Base64.getEncoder().encodeToString(picture);
			StringBuilder jsonProfileInfo = new StringBuilder("[");
			jsonProfileInfo.append("{")
					.append("\"login\": \"").append(model.accountInterface.getLogin(userID)).append("\",")
					.append("\"company_name\": \"").append(model.accountInterface.getCompanyName(userID)).append("\",")
					.append("\"email\": \"").append(model.accountInterface.getEmail(userID)).append("\",")
					.append("\"city\": \"").append(model.accountInterface.getCity(userID)).append("\",")
					.append("\"NIP\": \"").append(model.accountInterface.getNIP(userID)).append("\",")
					.append("\"description\": \"").append(model.accountInterface.getDescription(userID)).append("\",")
					.append("\"picture\": \"").append(base64Image).append("\"")
					.append("}");
			jsonProfileInfo.append("]");
			out.println(jsonProfileInfo.toString());
		}
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
	}
}
