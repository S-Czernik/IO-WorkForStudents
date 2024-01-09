/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author Kamil
 */
@WebServlet(name = "ProfileStudentServlet", urlPatterns = {"/ProfileStudentServlet"})
public class ProfileStudentServlet extends HttpServlet {

    private Model model;

    public ProfileStudentServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        String userID = "5";
        try (PrintWriter out = response.getWriter()) {
            
            byte[] picture = model.getProfilePicture(userID);
            String base64Image = Base64.getEncoder().encodeToString(picture);
            
            StringBuilder jsonProfileInfo = new StringBuilder("[");
            jsonProfileInfo.append("{")
                    .append("\"login\": \"").append(model.getLogin(userID)).append("\",")
                    .append("\"name\": \"").append(model.getName(userID)).append("\",")
                    .append("\"surname\": \"").append(model.getSurname(userID)).append("\",")
                    .append("\"email\": \"").append(model.getEmail(userID)).append("\",")
                    .append("\"city\": \"").append(model.getCity(userID)).append("\",")
                    .append("\"title\": \"").append(model.getTitle(userID)).append("\",")
                    .append("\"description\": \"").append(model.getDescription(userID)).append("\",")
                    .append("\"picture\": \"").append(base64Image).append("\"")
                    .append("}");
            jsonProfileInfo.append("]");
            System.out.println(model.getCity(userID));
            out.println(jsonProfileInfo.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
