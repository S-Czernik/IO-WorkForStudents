package model.offers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Interface;

public class OfferInterface extends Interface {

	public ArrayList<Offer> getSearchedOffers(String offerTitle, int last, int type) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query;

			if (type == 0) {
				query = "SELECT * FROM OFFERS WHERE id_offer <= ? AND title = ? ORDER BY id_offer DESC LIMIT 10";
			}
			else {
				query = "SELECT * FROM (SELECT * FROM OFFERS WHERE id_offer > ? AND title = ? LIMIT 10) AS subquery ORDER BY id_offer DESC";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);
			preparedStatement.setString(2, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				int salary = results.getInt("salary");

				Offer offer = new Offer(id_offer, id_empl, title, content, salary);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return searchedoffers;
	}

	public ArrayList<Offer> getSortedAndFilteredOffers(int min, int max, int type, String searched, int last, int number, int type2) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String sortBy = "id_offer";
			String sortOrder = "ASC";

			switch (type) {
				case 2 ->
					sortBy = "percentage";
				case 4 ->
					sortBy = "title";
				case 6 ->
					sortBy = "salary";
			}
			if (type % 2 == 1) {
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM OFFERS";

			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = ?";
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND salary BETWEEN ? AND ?";
				}
				else {
					query += " WHERE salary BETWEEN ? AND ?";
				}
			}

			query += " ORDER BY " + sortBy + " " + sortOrder;

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int parameterIndex = 1;
			if (was) {
				preparedStatement.setString(parameterIndex++, searched);
			}

			if (max >= min && max >= 0 && min >= 0) {
				preparedStatement.setInt(parameterIndex++, min);
				preparedStatement.setInt(parameterIndex, max);
			}

			ResultSet results = preparedStatement.executeQuery();

			boolean wasNum = false;
			int count = 0;
			switch (type2) {
				case 0 -> {
					while (results.next() && count < 10) {
						int id_offer = results.getInt("id_offer");
						int id_person = results.getInt("id_empl");
						String title = results.getString("title");
						String content = results.getString("content");
						int salary = results.getInt("salary");
						Offer offer = new Offer(id_offer, id_person, title, content, salary);
						searchedoffers.add(offer);
						count++;
					}
				}
				case 1 -> {
					if (number == 10) {
						while (results.next() && count < 10) {
							int id_offer = results.getInt("id_offer");
							if (id_offer == last) {
								wasNum = true;
							}
							else if (wasNum) {
								int id_person = results.getInt("id_empl");
								String title = results.getString("title");
								String content = results.getString("content");
								int salary = results.getInt("salary");
								Offer offer = new Offer(id_offer, id_person, title, content, salary);
								searchedoffers.add(offer);
								count++;
							}
						}
					}
					else {
						results.afterLast();
						while (results.previous() && count < number) {
							int id_offer = results.getInt("id_offer");
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, salary);
							searchedoffers.add(0, offer);
							count++;
						}
					}
				}
				case -1 -> {
					results.afterLast();
					while (results.previous() && count < 10) {
						int id_offer = results.getInt("id_offer");
						if (id_offer == last) {
							wasNum = true;
						}
						else if (wasNum) {
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, salary);
							searchedoffers.add(0, offer);
							count++;
						}
					}
					if (count == 0) {
						while (results.next() && count < 10) {
							int id_offer = results.getInt("id_offer");
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, salary);
							searchedoffers.add(offer);
							count++;
						}
					}
				}
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getOffersWithId(String id, int last, int number, int type2) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query = "SELECT * FROM OFFERS WHERE id_empl = ? ORDER BY id_offer DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			preparedStatement.setString(1, id);

			ResultSet results = preparedStatement.executeQuery();

			boolean wasNum = false;
			int count = 0;
			switch (type2) {
				case 0 -> {
					while (results.next() && count < 10) {
						int id_offer = results.getInt("id_offer");
						int id_person = results.getInt("id_empl");
						String title = results.getString("title");
						String content = results.getString("content");
						int salary = results.getInt("salary");
						Offer offer = new Offer(id_offer, id_person, title, content, salary);
						searchedoffers.add(offer);
						count++;
					}
				}
				case 1 -> {
					if (number == 10) {
						while (results.next() && count < 10) {
							int id_offer = results.getInt("id_offer");
							if (id_offer == last) {
								wasNum = true;
							}
							else if (wasNum) {
								int id_person = results.getInt("id_empl");
								String title = results.getString("title");
								String content = results.getString("content");
								int salary = results.getInt("salary");
								Offer offer = new Offer(id_offer, id_person, title, content, salary);
								searchedoffers.add(offer);
								count++;
							}
						}
					}
					else {
						results.afterLast();
						while (results.previous() && count < number) {
							int id_offer = results.getInt("id_offer");
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, salary);
							searchedoffers.add(0, offer);
							count++;
						}
					}
				}
				case -1 -> {
					results.afterLast();
					while (results.previous() && count < 10) {
						int id_offer = results.getInt("id_offer");
						if (id_offer == last) {
							wasNum = true;
						}
						else if (wasNum) {
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, salary);
							searchedoffers.add(0, offer);
							count++;
						}
					}
					if (count == 0) {
						while (results.next() && count < 10) {
							int id_offer = results.getInt("id_offer");
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, salary);
							searchedoffers.add(offer);
							count++;
						}
					}
				}
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getSearchedProfiles(String offerTitle, int last, int type) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query;

			if (type == 0) {
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud <= ? AND title = ? ORDER BY id_stud DESC LIMIT 10";
			}
			else {
				query = "SELECT * FROM (SELECT * FROM STUDENT_PROFILES WHERE id_stud > ? AND title = ? LIMIT 10) AS subquery ORDER BY id_stud DESC";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);
			preparedStatement.setString(2, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, rating);
				searchedoffers.add(offer);
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}
        
        public ArrayList<Offer> getStudentOffer(int userID) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query;

				query = "SELECT * FROM  STUDENT_PROFILES WHERE id_stud = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userID);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, rating);
				searchedoffers.add(offer);
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}
        
        public int getStudentId(int profileID) {
		int stud_id = -1;

		try {
				String query = """
            SELECT students_profile_details.id_user
            FROM students_profile_details
            INNER JOIN 
            student_profiles ON students_profile_details.id_students_profile_details = student_profiles.id_stud
            WHERE student_profiles.id_stud = ?""";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, profileID);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				stud_id = results.getInt("id_user");
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
            return stud_id;
	}

	public ArrayList<Offer> getSortedAndFilteredProfiles(int min, int max, int type, String searched, int last, int number, int type2) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String sortBy = "id_stud";
			String sortOrder = "ASC";

			switch (type) {
				case 2 ->
					sortBy = "percentage";
				case 3 ->
					sortBy = "percentage";
				case 4 ->
					sortBy = "title";
				case 5 ->
					sortBy = "title";
				case 6 ->
					sortBy = "rating";
				case 7 ->
					sortBy = "rating";
			}
			if (type % 2 == 1) {
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM STUDENT_PROFILES";

			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = ?";
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND rating BETWEEN ? AND ?";
				}
				else {
					query += " WHERE rating BETWEEN ? AND ?";
				}
			}

			query += " ORDER BY " + sortBy + " " + sortOrder;

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int parameterIndex = 1;
			if (was) {
				preparedStatement.setString(parameterIndex++, searched);
			}

			if (max >= min && max >= 0 && min >= 0) {
				preparedStatement.setInt(parameterIndex++, min);
				preparedStatement.setInt(parameterIndex, max);
			}

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, rating);
				searchedoffers.add(offer);
			}

			boolean wasNum = false;
			int count = 0;
			switch (type2) {
				case 0 -> {
					while (results.next() && count < 10) {
						int id_person = results.getInt("id_stud");
						String title = results.getString("title");
						String content = results.getString("content");
						int rating = results.getInt("rating");
						Offer offer = new Offer(id_person, title, content, rating);
						searchedoffers.add(offer);
						count++;
					}
				}
				case 1 -> {
					if (number == 10) {
						while (results.next() && count < 10) {
							int id_person = results.getInt("id_stud");
							if (id_person == last) {
								wasNum = true;
							}
							else if (wasNum) {
								String title = results.getString("title");
								String content = results.getString("content");
								int rating = results.getInt("rating");
								Offer offer = new Offer(id_person, title, content, rating);
								searchedoffers.add(offer);
								count++;
							}
						}
					}
					else {
						results.afterLast();
						while (results.previous() && count < number) {
							int id_person = results.getInt("id_stud");
							String title = results.getString("title");
							String content = results.getString("content");
							int rating = results.getInt("rating");
							Offer offer = new Offer(id_person, title, content, rating);
							searchedoffers.add(0, offer);
							count++;
						}
					}
				}
				case -1 -> {
					results.afterLast();
					while (results.previous() && count < 10) {
						int id_person = results.getInt("id_stud");
						if (id_person == last) {
							wasNum = true;
						}
						else if (wasNum) {
							String title = results.getString("title");
							String content = results.getString("content");
							int rating = results.getInt("rating");
							Offer offer = new Offer(id_person, title, content, rating);
							searchedoffers.add(0, offer);
							count++;
						}
					}
					if (count == 0) {
						while (results.next() && count < 10) {
							int id_person = results.getInt("id_stud");
							String title = results.getString("title");
							String content = results.getString("content");
							int rating = results.getInt("rating");
							Offer offer = new Offer(id_person, title, content, rating);
							searchedoffers.add(offer);
							count++;
						}
					}
				}
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getOffers(int last, int type) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;

			if (type == 0) {
				query = "SELECT * FROM OFFERS WHERE id_offer <= ? ORDER BY id_offer DESC LIMIT 10";
			}
			else {
				query = "SELECT * FROM (SELECT * FROM OFFERS WHERE id_offer > ? LIMIT 10) AS subquery ORDER BY id_offer DESC";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_person = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				int salary = results.getInt("salary");

				Offer offer = new Offer(id_offer, id_person, title, content, salary);
				offers.add(offer);
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return offers;
	}

	public ArrayList<Offer> getProfiles(int last, int type) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;

			if (type == 0) {
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud <= ? ORDER BY id_stud DESC LIMIT 10";
			}
			else {
				query = "SELECT * FROM (SELECT * FROM STUDENT_PROFILES WHERE id_stud > ? LIMIT 10) AS subquery ORDER BY id_stud DESC";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, rating);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return offers;
	}

	public ArrayList<Offer> getOffers(int begin, int end, String empl_id) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			if (end >= begin) {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? AND id_offer <= ? AND id_empl =?";
			}
			else {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? OR id_offer <= ? AND id_empl =?";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, begin);
			preparedStatement.setInt(2, end);
			preparedStatement.setString(3, empl_id);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				int salary = results.getInt("salary");

				Offer offer = new Offer(id_offer, id_empl, title, content, salary);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return offers;
	}

	public boolean deleteOffer(int id_offer) {
		try {

			String insertQuery = "DELETE FROM offers WHERE id_offer = ?";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setInt(1, id_offer);
			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public int getLastOfferId() {
		try {
			String query = "SELECT MAX(id_offer) AS last_offer FROM offers";
			ResultSet results = connection.createStatement().executeQuery(query);

			if (results.next()) {
				return results.getInt("last_offer");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public int getLastProfileId() {
		try {
			String query = "SELECT MAX(id_stud) AS last_offer FROM STUDENT_PROFILES";
			ResultSet results = connection.createStatement().executeQuery(query);

			if (results.next()) {
				return results.getInt("last_offer");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public boolean addOffer(int id_empl, String title, String content, String salary) {
		try {
			int newMax = getLastOfferId() + 1;
			String dotSalary = salary;
			String insertQuery = "INSERT INTO offers (id_offer, id_empl, title, content, salary) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(newMax));
			insertStatement.setInt(2, id_empl);
			insertStatement.setString(3, title);
			insertStatement.setString(4, content);
			if (salary.contains(",")) {
				dotSalary = salary.replace(",", ".");
			}
			else {
				dotSalary = salary;
			}
			insertStatement.setString(5, dotSalary);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean editOffer(int id_offer, String title, String content, String salary) {
		try {
			String dotSalary;
			String insertQuery = "UPDATE offers SET title = ?, content = ?, salary = ? WHERE (id_offer = ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, title);
			insertStatement.setString(2, content);
			if (salary.contains(",")) {
				dotSalary = salary.replace(",", ".");
			}
			else {
				dotSalary = salary;
			}
			insertStatement.setString(3, dotSalary);
			insertStatement.setInt(4, id_offer);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Offer getOffer(int id) {
		try {
			String query = "SELECT * FROM OFFERS WHERE id_offer = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_person = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				int salary = results.getInt("salary");
				return new Offer(id_offer, id_person, title, content, salary);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return new Offer(-2, -2, "Skipped try/catch", "Skipped try/catch", 0);
	}
}
