package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used for control over input processing
 */
public class Model {

    Connection connection;
    Statement statement;

    public Model() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e);
        }

        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "1234");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean checkLogin(String arg1, String arg2) {
        try {
            String query = "SELECT * FROM USERS";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String login = results.getString("LOGIN");

                if (login.equals(arg1)) {
                    String password = results.getString("PASSWORD");

                    if (password.equals(arg2)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            return false;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
