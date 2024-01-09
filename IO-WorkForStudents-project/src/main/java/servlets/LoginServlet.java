package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Model;

@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet"})
public class LoginServlet extends HttpServlet {
    Model model;
	
    public LoginServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plaintext;charset=UTF-8");

        String arg1 = request.getParameter("arg1");
        String arg2 = request.getParameter("arg2");

        ArrayList<String> idAndType = model.checkLogin(arg1, arg2);
        String args = idAndType.get(0) + "." + idAndType.get(1);

        PrintWriter out = response.getWriter();
        out.println(args);
		
        out.close();
    }
}
