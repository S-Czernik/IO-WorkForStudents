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

	static Model singleton = null; // >:3

	public static Model getModel() {
		if (singleton == null) {
			singleton = new Model();
		}
		return singleton;
	}

	Connection connection;
	Statement statement;

	public Model() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (Exception e) {
			System.out.println(e);
		}

		connect();
	}

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "1234");
			statement = connection.createStatement();
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}

	public boolean checkLogin(String login, String password) {
		try {
			String query = "SELECT * FROM USERS WHERE login = " + login;
			ResultSet results = statement.executeQuery(query);

			return (results.next() && results.getString("password").equals(password));
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean register(String login, String password, String email, String type) {
		try {
			String query = "SELECT * FROM USERS WHERE login = " + login;
			ResultSet results = statement.executeQuery(query);

			// Login already exists
			if (results.next()) {
				return false;
			}

			query = "INSERT INTO users (id, type, login, password, email) VALUES (" + String.valueOf(getUserCount()) + "," + type + "," + login + "," + password + "," + email + ")";

			statement.execute(query);
			return true;
		}
		catch (Exception e) {
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
		}
		catch (Exception e) {
			System.out.println(e);
			return -69;
		}
	}
}
