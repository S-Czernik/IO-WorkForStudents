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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "root");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> checkLogin(String login, String password) {
        ArrayList<String> idAndType = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM USERS WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next() && results.getString("password").equals(password)) {
                idAndType.add(results.getString("id_user"));
                idAndType.add(results.getString("type"));
            }
            else {
                idAndType.add("-1");
                idAndType.add("-1");
            }
        } catch (Exception e) {
            System.out.println(e);
            idAndType.add("-1");
            idAndType.add("-1");
        }
        
        return idAndType;
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
            String query;
            if (end >= begin)
                query = "SELECT * FROM OFFERS WHERE id_offer >= ? AND id_offer <= ?";
            else
                query = "SELECT * FROM OFFERS WHERE id_offer >= ? OR id_offer <= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, end);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_offer = results.getInt("id_offer");
                int id_person = results.getInt("id_empl");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_offer, id_person, title, content, info);
                offers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return offers;
    }
    
    public ArrayList<Offer> getProfiles(int begin, int end) {
        ArrayList<Offer> offers = new ArrayList<>();

        try {    
            String query;
            if (end >= begin)
                query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud >= ? AND id_stud <= ?";
            else
                query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud >= ? OR id_stud <= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, end);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_person = results.getInt("id_stud");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_person, title, content, info);
                offers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return offers;
    }
    
    public int getOffersCount(String type) {
        try {
            String query;
            if (type.equals("offers"))
                query = "SELECT COUNT(*) AS count FROM OFFERS";
            else 
                query = "SELECT COUNT(*) AS count FROM STUDENT_PROFILES"; 
            ResultSet results = statement.executeQuery(query);

            if (results.next())
                return results.getInt("count");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
    
    public ArrayList<Offer> getSearchedOffers(String offerTitle) {
        ArrayList<Offer> searchedoffers = new ArrayList<>();

        try {    
            String query = "SELECT * FROM OFFERS WHERE title = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, offerTitle);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_offer = results.getInt("id_offer");
                int id_person = results.getInt("id_empl");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_offer, id_person, title, content, info);
                searchedoffers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return searchedoffers;
    }
    
    public ArrayList<Offer> getSearchedProfiles(String offerTitle) {
        ArrayList<Offer> searchedoffers = new ArrayList<>();

        try {    
            String query = "SELECT * FROM STUDENT_PROFILES WHERE title = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, offerTitle);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_person = results.getInt("id_stud");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_person, title, content, info);
                searchedoffers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return searchedoffers;
    }
    
    public ArrayList<Offer> getSortedAndFilteredOffers(int min, int max, int type, String searched) {
        ArrayList<Offer> searchedoffers = new ArrayList<>();

        try {
            String sortBy = "title";
            String sortOrder = "ASC";

            if (type % 2 == 0) {
                if (type == 2)
                    sortBy = "content";
                else if (type == 4)
                    sortBy = "percentage";
            } else {
                if (type == 3)
                    sortBy = "content";
                else if (type == 5)
                    sortBy = "percentage";
                sortOrder = "DESC";
            }

            String query = "SELECT * FROM OFFERS";
            
            boolean was = false;
            if (!searched.equals("")) {
                query += " WHERE title = " + searched;
                was = true;
            }
            
            if (max >= min && max >= 0 && min >= 0) {
                if (was)
                    query += " AND content BETWEEN " + min + " AND " + max;
                else
                    query += " WHERE content BETWEEN " + min + " AND " + max;
            }

            query += " ORDER BY " + sortBy + " " + sortOrder;
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_offer = results.getInt("id_offer");
                int id_person = results.getInt("id_empl");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_offer, id_person, title, content, info);
                searchedoffers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return searchedoffers;
    }
    
    public ArrayList<Offer> getSortedAndFilteredProfiles(int min, int max, int type, String searched) {
        ArrayList<Offer> searchedoffers = new ArrayList<>();

        try {
            String sortBy = "title";
            String sortOrder = "ASC";

            if (type % 2 == 0) {
                if (type == 2)
                    sortBy = "content";
                else if (type == 4)
                    sortBy = "percentage";
            } else {
                if (type == 3)
                    sortBy = "content";
                else if (type == 5)
                    sortBy = "percentage";
                sortOrder = "DESC";
            }

            String query = "SELECT * FROM STUDENT_PROFILES";
            
            boolean was = false;
            if (!searched.equals("")) {
                query += " WHERE title = " + searched;
                was = true;
            }
            
            if (max >= min && max >= 0 && min >= 0) {
                if (was)
                    query += " AND content BETWEEN " + min + " AND " + max;
                else
                    query += " WHERE content BETWEEN " + min + " AND " + max;
            }

            query += " ORDER BY " + sortBy + " " + sortOrder;
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id_person = results.getInt("id_stud");
                String title = results.getString("title");
                String content = results.getString("content");
                String info = results.getString("info");

                Offer offer = new Offer(id_person, title, content, info);
                searchedoffers.add(offer);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return searchedoffers;
    }
}
