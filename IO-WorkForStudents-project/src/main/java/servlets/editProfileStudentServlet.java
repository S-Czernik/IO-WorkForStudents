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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import model.Model;

/**
 *
 * @author Kamil
 */
@WebServlet(name = "editProfileStudentServlet", urlPatterns = {"/editProfileStudentServlet"})
@MultipartConfig
public class editProfileStudentServlet extends HttpServlet {

    Model model;

    public editProfileStudentServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String userID = request.getParameter("arg1");
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
            System.out.println(model.accountInterface.getCity(userID));
            out.println(jsonProfileInfo.toString());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Pobierz dane z żądania
            String userID = request.getParameter("userID");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String city = request.getParameter("city");
            String description = request.getParameter("description");
            String title = request.getParameter("title");
            String picture = request.getParameter("picture");
            // Pobierz plik obrazu profilowego z żądania
            //Part filePart = request.getPart("picture");
            //InputStream fileInputStream = filePart.getInputStream();
            //byte[] profilePicture = fileInputStream.toString().getBytes(StandardCharsets.UTF_8);
            // Wywołaj metody modelu do aktualizacji danych
             model.accountInterface.saveName(userID, name);
            model.accountInterface.saveSurname(userID, surname);
            model.accountInterface.saveCity(userID, city);
            model.accountInterface.saveTitle(userID, title);
            model.accountInterface.saveDescription(userID, description);
            model.accountInterface.savePicture(userID, picture);
            // Odpowiedz klientowi, że zapisano zmiany
            out.println("Changes saved successfully.");
        } catch (Exception e) {
            // Wystąpił błąd, odpowiedz klientowi z informacją o błędzie
            out.println("Error saving changes.");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
