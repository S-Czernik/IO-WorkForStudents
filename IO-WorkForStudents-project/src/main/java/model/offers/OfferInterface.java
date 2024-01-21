package model.offers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Interface;

public class OfferInterface extends Interface {

	public ArrayList<Offer> getSortedAndFilteredOffers(int min, int max, int type, String searched) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String sortBy = "title";
			String sortOrder = "ASC";

			if (type % 2 == 0) {
				if (type == 2) {
					sortBy = "content";
				}
				else if (type == 4) {
					sortBy = "percentage";
				}
			}
			else {
				if (type == 3) {
					sortBy = "content";
				}
				else if (type == 5) {
					sortBy = "percentage";
				}
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM OFFERS";

			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = " + searched;
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND content BETWEEN " + min + " AND " + max;
				}
				else {
					query += " WHERE content BETWEEN " + min + " AND " + max;
				}
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
                                String salary = results.getString("salary");

				Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
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
		}
		catch (Exception exp) {
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
				if (type == 2) {
					sortBy = "content";
				}
				else if (type == 4) {
					sortBy = "percentage";
				}
			}
			else {
				if (type == 3) {
					sortBy = "content";
				}
				else if (type == 5) {
					sortBy = "percentage";
				}
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM STUDENT_PROFILES";

			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = " + searched;
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND content BETWEEN " + min + " AND " + max;
				}
				else {
					query += " WHERE content BETWEEN " + min + " AND " + max;
				}
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
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getOffers(int begin, int end) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			if (end >= begin) {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? AND id_offer <= ?";
			}
			else {
				query = "SELECT * FROM OFFERS WHERE id_offer >= ? OR id_offer <= ?";
			}

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
                                String salary = results.getString("salary");

				Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return offers;
	}
        
        public Offer getOffer(String id) {
            Offer offer;
            try {
            String query = "SELECT * FROM OFFERS WHERE id_offer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                    int id_offer = results.getInt("id_offer");
                    int id_person = results.getInt("id_empl");
                    String title = results.getString("title");
                    String content = results.getString("content");
                    String info = results.getString("info");
                    String salary = results.getString("salary");
                    return offer = new Offer(id_offer, id_person, title, content, info, salary);
            }
            }
            catch (Exception exp) {
		System.out.println(exp);
                offer = new Offer(-1, -1, "", "SQLerror", "SQLerror", "0");
            }
            return offer = new Offer(-2, -2, "Skipped try/catch", "Skipped try/catch", "error", "0");
        }

	public ArrayList<Offer> getProfiles(int begin, int end) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			if (end >= begin) {
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud >= ? AND id_stud <= ?";
			}
			else {
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud >= ? OR id_stud <= ?";
			}

			PreparedStatement preparedStatement = model.connection.prepareStatement(query);
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
				String info = results.getString("info");
                                String salary = results.getString("salary");

				Offer offer = new Offer(id_offer, id_empl, title, content, info, salary);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return offers;
	}

	public boolean deleteOffer(String id_offer) {
		try {

			String insertQuery = "DELETE FROM offers WHERE id_offer= ?";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(id_offer));
			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	int getLastOfferId() {
		try {
			String query = "SELECT MAX(id_offer) AS last_offer FROM offers";
			ResultSet results = statement.executeQuery(query);

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

	public int getOfferCount() {
		try {
			String query = "SELECT COUNT(*) AS count FROM OFFERS";
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

	public ArrayList<Offer> getSearchedOffers(String offerTitle) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query = "SELECT * FROM OFFERS WHERE title = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");
                                String salary = results.getString("salary");

				Offer offer = new Offer(id_offer, id_empl, title, content, info, salary);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}

		return searchedoffers;
	}

	public boolean addOffer(String id_empl, String title, String content, String info, String salary) {
		try {
			int newMax = getLastOfferId() + 1;
			String dotSalary = salary;
			String insertQuery = "INSERT INTO offers (id_offer, id_empl, title, content, info, salary) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(newMax));
			insertStatement.setString(2, id_empl);
			insertStatement.setString(3, title);
			insertStatement.setString(4, content);
			insertStatement.setString(5, info);
			if (salary.contains(",")) {
				dotSalary = salary.replace(",", ".");
			}
			else {
				dotSalary = salary;
			}
			insertStatement.setString(6, dotSalary);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean editOffer(String id_offer, String title, String content, String info, String salary) {
		try {
			String dotSalary;
			String insertQuery = "UPDATE offers SET title = ?, content = ?, info = ?, salary = ? WHERE (id_offer = ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, title);
			insertStatement.setString(2, content);
			insertStatement.setString(3, info);
			if (salary.contains(",")) {
				dotSalary = salary.replace(",", ".");
			}
			else {
				dotSalary = salary;
			}
			insertStatement.setString(4, dotSalary);
			insertStatement.setString(5, String.valueOf(id_offer));

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
