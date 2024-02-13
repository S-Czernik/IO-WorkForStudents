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
import model.Model;
import servlets.helper.Helper;

/**
 *
 * @author adamk
 */
@WebServlet(name = "ProfileIdToStudentIdServlet", urlPatterns = {"/ProfileIdToStudentIdServlet"})
public class ProfileIdToStudentIdServlet extends HttpServlet {

   Model model;

	public ProfileIdToStudentIdServlet() {
		model = Model.getModel();
	}

   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    
    int profileID = Helper.getIntValueOf(request.getParameter("arg1"));
    int stud_id = model.offerInterface.getStudentId(profileID);
    
    // Write the student ID to the response
    PrintWriter out = response.getWriter();
    out.print(stud_id);
    out.flush();
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
