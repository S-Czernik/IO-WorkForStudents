package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
    static Model singleton = null;
    Connection connection;
    Statement statement;

    public static Model getModel() {
        if (singleton == null) {
            singleton = new Model();
        }
        return singleton;
    }

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

    public boolean checkLogin(String login, String password) {
        try {
            String query = "SELECT * FROM USERS WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet results = preparedStatement.executeQuery();

            return (results.next() && results.getString("password").equals(password));
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean register(String login, String password, String email, String type) {
        try {
            String query = "SELECT * FROM USERS WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return false;
            }

            String insertQuery = "INSERT INTO users (id, type, login, password, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, String.valueOf(getUserCount()));
            insertStatement.setString(2, type);
            insertStatement.setString(3, login);
            insertStatement.setString(4, password);
            insertStatement.setString(5, email);

            insertStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    int getUserCount() {
        try {
            String query = "SELECT COUNT(*) AS count FROM USERS";
            ResultSet results = statement.executeQuery(query);

            if (results.next()) {
                return results.getInt("count");
            }
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
}