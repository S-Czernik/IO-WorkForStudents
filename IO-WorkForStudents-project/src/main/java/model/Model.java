package model;

import java.sql.Blob;
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

    public boolean checkLogin(String login, String password) {
        try {
            String query = "SELECT * FROM users WHERE login = ?";
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
            String query = "SELECT COUNT(*) AS count FROM users";
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

    /*
    Function gets user type and their ID to search in database for 
    all notifications assigned to that account
     */
    public ArrayList<Notification> getNotifications(String userID) {
        ArrayList<Notification> notifications = new ArrayList<>();
        String userType = getUserType(userID);

        try {
            if (userType.equals("student")) {
                String query = """
                SELECT 
                    mailbox_student.id_box_stud,  
                    mailbox_student.mess_type, 
                    users_login.login AS employee_login, 
                    offers.title
                FROM offers
                INNER JOIN 
                    mailbox_student ON offers.id_offer = mailbox_student.id_offer
                INNER JOIN 
                    users ON mailbox_student.id_stud = users.id_user
                LEFT JOIN 
                    users AS users_login ON offers.id_empl = users_login.id_user
                WHERE users.id_user = ?""";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userID);
                ResultSet results = preparedStatement.executeQuery();

                while (results.next()) {
                    String notificationID = results.getString("id_box_stud");
                    String messageType = results.getString("mess_type");
                    String employeeLogin = results.getString("employee_login");
                    String offerTitle = results.getString("title");

                    Notification notification = new Notification(notificationID, messageType, employeeLogin, offerTitle);
                    notifications.add(notification);
                }
            } else if (userType.equals("employer")) {
                String query = """
                SELECT
                    mailbox_employer.id_box_emp, 
                    users.login AS student_login,
                    mailbox_employer.mess_type,
                    offers.title
                FROM users
                INNER JOIN 
                    mailbox_employer ON users.id_user = mailbox_employer.id_stud
                INNER JOIN 
                    offers ON mailbox_employer.id_offer = offers.id_offer
                LEFT JOIN 
                    users AS users_login ON offers.id_empl = users_login.id_user
                WHERE offers.id_empl = ?""";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userID);
                ResultSet results = preparedStatement.executeQuery();

                while (results.next()) {
                    String notificationID = results.getString("id_box_emp");
                    String studentLogin = results.getString("student_login");
                    String messageType = results.getString("mess_type");
                    String offerTitle = results.getString("title");

                    Notification notification = new Notification(notificationID, messageType, studentLogin, offerTitle);
                    notifications.add(notification);
                }
            } else {
                // Obsługa innego typu użytkownika
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return notifications;
    }

    public String getLogin(String userID) {
        try {
            String query = "SELECT login FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String login = results.getString("login");
                return login;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No login assigned or no user";
    }

    public String getUserType(String userID) {
        try {
            String query = "SELECT type FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String type = results.getString("type");
                return type;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No type assigned or no user";
    }

    public String getName(String userID) {

        try {
            String query = "SELECT name FROM students_profile_details WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String name = results.getString("name");
                return name;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No name in DB";
    }

    public String getSurname(String userID) {

        try {
            String query = "SELECT surname FROM students_profile_details WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String surname = results.getString("surname");
                return surname;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No surname in DB";
    }

    public String getEmail(String userID) {

        try {
            String query = "SELECT email FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String email = results.getString("email");
                return email;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No email in DB";
    }

    public String getTitle(String userID) {

        try {
            String query = "SELECT title FROM students_profile_details WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String title = results.getString("title");
                return title;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No title in DB";
    }

    public String getDescription(String userID) {

        try {
            String query = "SELECT description FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String description = results.getString("description");
                return description;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No description in DB";
    }

    public byte[] getProfilePicture(String userID) {
        byte[] blobData = null;
        try {
            String query = "SELECT picture FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                Blob picture = results.getBlob("picture");
                blobData = picture.getBytes(1, (int) picture.length());
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return blobData;
    }

    public String getCity(String userID) {
        try {
            String query = "SELECT city FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String city = results.getString("city");
                return city;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No city in DB";
    }

    public String getCompanyName(String userID) {

        try {
            String query = "SELECT company_name FROM employers_profile_details WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String company_name = results.getString("company_name");
                return company_name;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No company_name in DB";
    }

    public String getNIP(String userID) {

        try {
            String query = "SELECT NIP FROM employers_profile_details WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                String NIP = results.getString("NIP");
                return NIP;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "No NIP in DB";
    }
}

