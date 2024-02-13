package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Base64;
import model.Model;
import servlets.helper.Helper;

@WebServlet(name = "EditProfileStudentServlet", urlPatterns = {"/EditProfileStudentServlet"})
@MultipartConfig
public class EditProfileStudentServlet extends HttpServlet {

	Model model;

	public EditProfileStudentServlet() {
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
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			int userID = Helper.getIntValueOf(request.getParameter("userID"));
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String city = request.getParameter("city");
			String description = request.getParameter("description");
			String title = request.getParameter("title");
			Part filePart = request.getPart("picture");
			InputStream fileInputStream = filePart.getInputStream();
			byte[] profilePicture = fileInputStream.readAllBytes();
			model.accountInterface.saveName(userID, name);
			model.accountInterface.saveSurname(userID, surname);
			model.accountInterface.saveCity(userID, city);
			model.accountInterface.saveTitle(userID, title);
			model.accountInterface.saveDescription(userID, description);
			if (profilePicture.length > 100) {
				model.accountInterface.savePicture(userID, profilePicture);
			}
			out.println("Changes saved successfully.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			out.close();
		}
	}
}
