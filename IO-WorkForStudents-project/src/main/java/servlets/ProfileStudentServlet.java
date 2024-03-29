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

@WebServlet(name = "ProfileStudentServlet", urlPatterns = {"/ProfileStudentServlet"})
public class ProfileStudentServlet extends HttpServlet {

	Model model;

	public ProfileStudentServlet() {
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
					.append("\"name\": \"").append(model.accountInterface.getName(userID)).append("\",")
					.append("\"surname\": \"").append(model.accountInterface.getSurname(userID)).append("\",")
					.append("\"email\": \"").append(model.accountInterface.getEmail(userID)).append("\",")
					.append("\"city\": \"").append(model.accountInterface.getCity(userID)).append("\",")
					.append("\"title\": \"").append(model.accountInterface.getTitle(userID)).append("\",")
					.append("\"description\": \"").append(model.accountInterface.getDescription(userID)).append("\",")
					.append("\"picture\": \"").append(base64Image).append("\"")
					.append("}");
			jsonProfileInfo.append("]");
			out.println(jsonProfileInfo.toString());
		}
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
	}
}
