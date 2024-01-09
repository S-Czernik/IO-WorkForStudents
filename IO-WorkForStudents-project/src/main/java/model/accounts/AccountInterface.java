package model.accounts;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Interface;

public class AccountInterface extends Interface {

	public ArrayList<String> checkLogin(String login, String password) {
		ArrayList<String> idAndType = new ArrayList<>();

		try {
			String query = "SELECT * FROM users WHERE login = ?";
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
		}
		catch (Exception e) {
			System.out.println(e);
			idAndType.add("-1");
			idAndType.add("-1");
		}

		return idAndType;
	}

	public boolean register(String login, String password, String email, String type) {
		try {
			String query = "SELECT * FROM users WHERE login = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, login);
			ResultSet results = preparedStatement.executeQuery();

			if (results.next()) {
				return false;
			}

			String insertQuery = "INSERT INTO users (id, type, login, password, email) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(getLastUserId() + 1));
			insertStatement.setString(2, type);
			insertStatement.setString(3, login);
			insertStatement.setString(4, password);
			insertStatement.setString(5, email);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	int getLastUserId() {
		try {
			String query = "SELECT MAX(id) AS max FROM USERS";
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				return results.getInt("max");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public int getUserCount() {
		try {
			String query = "SELECT COUNT(*) AS count FROM users";
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				return results.getInt("count");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public int getUserCount(String type) {
		try {
			String query = "SELECT COUNT(*) AS count FROM USERS WHERE type = '" + type + "'";
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				return results.getInt("count");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
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
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		return "No NIP in DB";
	}
}
