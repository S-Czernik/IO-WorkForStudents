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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model;
import model.files.File;
import model.files.FilesInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import servlets.helper.Helper;

/**
 *
 * @author Kamil
 */
@WebServlet(name = "FilesServlet", urlPatterns = {"/FilesServlet"})
@MultipartConfig
public class FilesServlet extends HttpServlet {

    private Model model;

    public FilesServlet() {
        model = Model.getModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("arg1"));
        ArrayList<File> files = model.filesInterface.getFiles(userID);
        JSONArray filesArray = new JSONArray();
        for (File file : files) {
            JSONObject fileObj = new JSONObject();
            try {
                fileObj.put("id", file.getID());
                fileObj.put("fileName", file.getFileName());
                fileObj.put("fileData", file.getData());
                System.out.println("FILENAME:" + file.getFileName());
            } catch (JSONException ex) {
                Logger.getLogger(FilesServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            filesArray.put(fileObj);
        }
        response.setContentType("application/json;charset=UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            out.write(filesArray.toString().getBytes());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operationType = request.getParameter("operationType");
        if (operationType.equals("add")) {
            int userID = Helper.getIntValueOf(request.getParameter("userID"));
            String fileName = request.getParameter("fileName");

            Part filePart = request.getPart("fileData");
            InputStream fileInputStream = filePart.getInputStream();
            byte[] fileData = fileInputStream.readAllBytes();
            fileInputStream.close();

            System.out.println("N " + request.getParameter("userID"));
            System.out.println("KON " + userID);

            model.filesInterface.saveFile(userID, fileName, fileData);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Plik został pomyślnie zapisany na serwerze.");
        } else if (operationType.equals("delete")) {
            int fileID = Helper.getIntValueOf(request.getParameter("fileID"));
            model.filesInterface.deleteFile(fileID);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Plik został pomyślnie zapisany na serwerze.");
        }
    }
}
