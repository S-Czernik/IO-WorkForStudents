package model.offers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Interface;

public class OfferInterface extends Interface {
	
	public ArrayList<Offer> getSearchedOffers(String offerTitle, int last, int type) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query;
			
			if (type == 0)
				query = "SELECT * FROM OFFERS WHERE id_offer <= ? AND title = ? ORDER BY id_offer DESC LIMIT 10";
			else
				query = "SELECT * FROM (SELECT * FROM OFFERS WHERE id_offer > ? AND title = ? LIMIT 10) AS subquery ORDER BY id_offer DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);
			preparedStatement.setString(2, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");
				int salary = results.getInt("salary");

				Offer offer = new Offer(id_offer, id_empl, title, content, info, salary);
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

			if (type % 2 == 0) {
				if (type == 2)
					sortBy = "percentage";
				else if (type == 4)
					sortBy = "title";
				else if (type == 6)
					sortBy = "salary";
			} else {
				if (type == 3)
					sortBy = "percentage";
				else if (type == 5)
					sortBy = "title";
				else if (type == 7)
					sortBy = "salary";
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM OFFERS";
			
			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = ?";
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was)
					query += " AND salary BETWEEN ? AND ?";
				else
					query += " WHERE salary BETWEEN ? AND ?";
			}
			
			query += " ORDER BY " + sortBy + " " + sortOrder;

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int parameterIndex = 1;
			if (was)
				preparedStatement.setString(parameterIndex++, searched);

			if (max >= min && max >= 0 && min >= 0) {
				preparedStatement.setInt(parameterIndex++, min);
				preparedStatement.setInt(parameterIndex, max);
			}

			ResultSet results = preparedStatement.executeQuery();
			
			boolean wasNum = false;
			int count = 0;
			if (type2 == 0) {
				while (results.next() && count < 10) {
					int id_offer = results.getInt("id_offer");
					int id_person = results.getInt("id_empl");
					String title = results.getString("title");
					String content = results.getString("content");
					String info = results.getString("info");
					int salary = results.getInt("salary");
					Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
					searchedoffers.add(offer);
					count++;
				}
			} else if (type2 == 1) {
				if (number == 10) {
					while (results.next() && count < 10) {
						int id_offer = results.getInt("id_offer");
						if (id_offer == last) {
							wasNum = true;
						} else if (wasNum) {
							int id_person = results.getInt("id_empl");
							String title = results.getString("title");
							String content = results.getString("content");
							String info = results.getString("info");
							int salary = results.getInt("salary");
							Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
							searchedoffers.add(offer);
							count++;
						}
					}
				} else {
					results.afterLast();
					while (results.previous() && count < number) {
						int id_offer = results.getInt("id_offer");
						int id_person = results.getInt("id_empl");
						String title = results.getString("title");
						String content = results.getString("content");
						String info = results.getString("info");
						int salary = results.getInt("salary");
						Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
						searchedoffers.add(0, offer);
						count++;
					}
				}
			} else if (type2 == -1) {
				results.afterLast();
				while (results.previous() && count < 10) {
					int id_offer = results.getInt("id_offer");
					if (id_offer == last) {
						wasNum = true;
					} else if (wasNum) {
						int id_person = results.getInt("id_empl");
						String title = results.getString("title");
						String content = results.getString("content");
						String info = results.getString("info");
						int salary = results.getInt("salary");
						Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
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
						String info = results.getString("info");
						int salary = results.getInt("salary");
						Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
						searchedoffers.add(offer);
						count++;
					}
				}
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getSearchedProfiles(String offerTitle, int last, int type) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query;
			
			if (type == 0)
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud <= ? AND title = ? ORDER BY id_stud DESC LIMIT 10";
			else
				query = "SELECT * FROM (SELECT * FROM STUDENT_PROFILES WHERE id_stud > ? AND title = ? LIMIT 10) AS subquery ORDER BY id_stud DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);
			preparedStatement.setString(2, offerTitle);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, info, rating);
				searchedoffers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getSortedAndFilteredProfiles(int min, int max, int type, String searched, int last, int number, int type2) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String sortBy = "id_stud";
			String sortOrder = "ASC";

			if (type % 2 == 0) {
				if (type == 2)
					sortBy = "percentage";
				else if (type == 4)
					sortBy = "title";
				else if (type == 6)
					sortBy = "rating";
			} else {
				if (type == 3)
					sortBy = "percentage";
				else if (type == 5)
					sortBy = "title";
				else if (type == 7)
					sortBy = "rating";
				sortOrder = "DESC";
			}

			String query = "SELECT * FROM STUDENT_PROFILES";
			
			boolean was = false;
			if (!searched.equals("")) {
				query += " WHERE title = ?";
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was)
					query += " AND content BETWEEN ? AND ?";
				else
					query += " WHERE content BETWEEN ? AND ?";
			}
			
			query += " ORDER BY " + sortBy + " " + sortOrder;

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int parameterIndex = 1;
			if (was)
				preparedStatement.setString(parameterIndex++, searched);

			if (max >= min && max >= 0 && min >= 0) {
				preparedStatement.setInt(parameterIndex++, min);
				preparedStatement.setInt(parameterIndex, max);
			}

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, info, rating);
				searchedoffers.add(offer);
			}
			
			boolean wasNum = false;
			int count = 0;
			if (type2 == 0) {
				while (results.next() && count < 10) {
					int id_person = results.getInt("id_stud");
					String title = results.getString("title");
					String content = results.getString("content");
					String info = results.getString("info");
					int rating = results.getInt("rating");
					Offer offer = new Offer(id_person, title, content, info, rating);
					searchedoffers.add(offer);
					count++;
				}
			} else if (type2 == 1) {
				if (number == 10) {
					while (results.next() && count < 10) {
						int id_person = results.getInt("id_stud");
						if (id_person == last) {
							wasNum = true;
						} else if (wasNum) {
							String title = results.getString("title");
							String content = results.getString("content");
							String info = results.getString("info");
							int rating = results.getInt("rating");
							Offer offer = new Offer( id_person, title, content, info, rating);
							searchedoffers.add(offer);
							count++;
						}
					}
				} else {
					results.afterLast();
					while (results.previous() && count < number) {
						int id_person = results.getInt("id_stud");
						String title = results.getString("title");
						String content = results.getString("content");
						String info = results.getString("info");
						int rating = results.getInt("rating");
						Offer offer = new Offer( id_person, title, content, info, rating);
						searchedoffers.add(0, offer);
						count++;
					}
				}
			} else if (type2 == -1) {
				results.afterLast();
				while (results.previous() && count < 10) {
					int id_person = results.getInt("id_stud");
					if (id_person == last) {
						wasNum = true;
					} else if (wasNum) {
						String title = results.getString("title");
						String content = results.getString("content");
						String info = results.getString("info");
						int rating = results.getInt("rating");
						Offer offer = new Offer( id_person, title, content, info, rating);
						searchedoffers.add(0, offer);
						count++;
					}
				}
				if (count == 0) {
					while (results.next() && count < 10) {
						int id_person = results.getInt("id_stud");
						String title = results.getString("title");
						String content = results.getString("content");
						String info = results.getString("info");
						int rating = results.getInt("rating");
						Offer offer = new Offer( id_person, title, content, info, rating);
						searchedoffers.add(offer);
						count++;
					}
				}
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getOffers(int last, int type) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {			
			String query;
			
			if (type == 0)
				query = "SELECT * FROM OFFERS WHERE id_offer <= ? ORDER BY id_offer DESC LIMIT 10";
			else
				query = "SELECT * FROM (SELECT * FROM OFFERS WHERE id_offer > ? LIMIT 10) AS subquery ORDER BY id_offer DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);

			ResultSet results = preparedStatement.executeQuery();			

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_person = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");
				int salary = results.getInt("salary");

				Offer offer = new Offer(id_offer, id_person, title, content, info, salary);
				offers.add(offer);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return offers;
	}

	public ArrayList<Offer> getProfiles(int last, int type) {
		ArrayList<Offer> offers = new ArrayList<>();

		try {
			String query;
			
			if (type == 0)
				query = "SELECT * FROM STUDENT_PROFILES WHERE id_stud <= ? ORDER BY id_stud DESC LIMIT 10";
			else
				query = "SELECT * FROM (SELECT * FROM STUDENT_PROFILES WHERE id_stud > ? LIMIT 10) AS subquery ORDER BY id_stud DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("content");
				String info = results.getString("info");
				int rating = results.getInt("rating");

				Offer offer = new Offer(id_person, title, content, info, rating);
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
				int salary = results.getInt("salary");

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

			String insertQuery = "DELETE FROM offers WHERE offer_id = ?";
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

	public int getLastOfferId() {
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
	
	public int getLastProfileId() {
		try {
			String query = "SELECT MAX(id_stud) AS last_offer FROM STUDENT_PROFILES";
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
				int salary = results.getInt("salary");
				return offer = new Offer(id_offer, id_person, title, content, info, salary);
			}
		} catch (Exception exp) {
			System.out.println(exp);
			offer = new Offer(-1, -1, "", "SQLerror", "SQLerror", 0);
		}
		return offer = new Offer(-2, -2, "Skipped try/catch", "Skipped try/catch", "error", 0);
	}
}