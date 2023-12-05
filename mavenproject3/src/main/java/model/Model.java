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

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "1234");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
