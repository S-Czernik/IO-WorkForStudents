package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;

@WebServlet(name = "Servlet", urlPatterns = {"/servlet"})
public class Servlet extends HttpServlet {
    Model model;

    public Servlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plaintext;charset=UTF-8");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
