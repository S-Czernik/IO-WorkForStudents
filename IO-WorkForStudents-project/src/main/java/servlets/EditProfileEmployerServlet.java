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

@WebServlet(name = "EditProfileEmployerServlet", urlPatterns = {"/EditProfileEmployerServlet"})
@MultipartConfig
public class EditProfileEmployerServlet extends HttpServlet {

    Model model;

    public EditProfileEmployerServlet() {
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
                    .append("\"NIP\": \"").append(model.accountInterface.getNIP(userID)).append("\",")
                    .append("\"email\": \"").append(model.accountInterface.getEmail(userID)).append("\",")
                    .append("\"city\": \"").append(model.accountInterface.getCity(userID)).append("\",")
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int userID = Helper.getIntValueOf(request.getParameter("userID"));
            String company_name = request.getParameter("company_name");
            String NIP = request.getParameter("NIP");
            String city = request.getParameter("city");
            String description = request.getParameter("description");
            Part filePart = request.getPart("picture");
            InputStream fileInputStream = filePart.getInputStream();
            byte[] profilePicture = fileInputStream.readAllBytes();
            System.out.println(city);
            model.accountInterface.saveCompanyName(userID, company_name);
            model.accountInterface.saveNIP(userID, NIP);
            model.accountInterface.saveCity(userID, city);
            model.accountInterface.saveDescription(userID, description);
            if (profilePicture.length > 100) {
                model.accountInterface.savePicture(userID, profilePicture);
            }
            out.println("Changes saved successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            out.close();
        }
    }
}
