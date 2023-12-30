package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public int checkLogin(String login, String password) {
        try {
            String query = "SELECT * FROM USERS WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next() && results.getString("password").equals(password))
                return results.getInt("id_user");
            else
                return -1;
            
        } catch (Exception e) {
            System.out.println(e);
            return -1;
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
    
    public ArrayList<Offer> getOffers(int begin, int end) {
        ArrayList<Offer> offers = new ArrayList<>();

        try {    
            String query = "SELECT * FROM OFFERS WHERE id_offer >= ? AND id_offer <= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, end);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_offer = results.getInt("id_offer");
                int id_empl = results.getInt("id_empl");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_offer, id_empl, title, content, info);
                offers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }

        return offers;
    }
    
    public int getOfferCount() {
        try {
            String query = "SELECT COUNT(*) AS count FROM OFFERS";
            ResultSet results = statement.executeQuery(query);

            if (results.next())
                return results.getInt("count");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
}
