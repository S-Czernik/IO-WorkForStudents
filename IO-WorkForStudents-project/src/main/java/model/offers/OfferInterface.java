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
			PreparedStatement preparedStatement;
			ResultSet results;

			if (!offerTitle.startsWith("_")) {
				if (type == 0) {
					query = "SELECT * FROM OFFERS WHERE id_offer <= ? AND title = ? ORDER BY id_offer DESC LIMIT 10";
				}
				else {
					query = "SELECT * FROM (SELECT * FROM OFFERS WHERE id_offer > ? AND title = ? LIMIT 10) AS subquery ORDER BY id_offer DESC";
				}

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, last);
				preparedStatement.setString(2, offerTitle);

				results = preparedStatement.executeQuery();

			}
			else {
				offerTitle = offerTitle.substring(1);
				String[] parts = offerTitle.split(",");

				if (type == 0) {
					query = "SELECT * FROM OFFERS WHERE id_offer <= ? AND (";
				}
				else {
					query = "SELECT * FROM (SELECT * FROM OFFERS WHERE id_offer > ? AND (";
				}

				for (int i = 0; i < parts.length; i++) {
					if (i > 0) {
						query += " OR ";
					}
					query += "tags LIKE ?";
				}

				query += ") ORDER BY id_offer DESC LIMIT 10";

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, last);

				for (int i = 0; i < parts.length; i++) {
					preparedStatement.setString(i + 2, "%" + parts[i] + "%");
				}

				results = preparedStatement.executeQuery();
			}

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				int salary = results.getInt("salary");
				String tags = results.getString("tags");

				Offer offer = new Offer(id_offer, id_empl, title, content, salary, tags);
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
			String sortBy = "";
			String sortOrder = "";

			switch (type) {
				case 0, 1 ->
					sortBy = "id_offer";
				case 2, 3 ->
					sortBy = "percentage";
				case 4, 5 ->
					sortBy = "title";
				case 6, 7 ->
					sortBy = "salary";
				case 8, 9 ->
					sortBy = "id_offer";
			}
			switch (type) {
				case 0, 2, 4, 6, 8 ->
					sortOrder = "ASC";
				case 1, 3, 5, 7, 9 ->
					sortOrder = "DESC";
			}

			String query = "SELECT * FROM OFFERS";

			if (sortBy.equals("percentage")) {
				query += " JOIN student_calendar_comparisons AS scc ON OFFERS.id_offer = scc.id_offer";
			}

			boolean was = false;
			String[] parts = new String[0];
			if (!searched.equals("")) {
				if (!searched.startsWith("_")) {
					query += " WHERE title = ?";
				}
				else {
					searched = searched.substring(1);
					parts = searched.split(",");

					query += " WHERE ";

					for (int i = 0; i < parts.length; i++) {
						if (i > 0) {
							query += " OR ";
						}
						query += "tags LIKE ?";
					}
				}
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

			if (sortBy.equals("percentage")) {
				query += " ORDER BY scc.value " + sortOrder;
			}
			else {
				query += " ORDER BY " + sortBy + " " + sortOrder;
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int parameterIndex = 1;
			if (was) {
				if (!searched.startsWith("_")) {
					preparedStatement.setString(parameterIndex++, searched);
				}
				else {
					for (int i = 0; i < parts.length; i++) {
						preparedStatement.setString(i + 2, "%" + parts[i] + "%");
						parameterIndex++;
					}
				}
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
						String tags = results.getString("tags");
						Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
								String tags = results.getString("tags");
								Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
							String tags = results.getString("tags");
							Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
							String tags = results.getString("tags");
							Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
							String tags = results.getString("tags");
							Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
						String tags = results.getString("tags");
						Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
								String tags = results.getString("tags");
								Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
							String tags = results.getString("tags");
							Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
							String tags = results.getString("tags");
							Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
							String tags = results.getString("tags");
							Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
			PreparedStatement preparedStatement;
			ResultSet results;

			if (!offerTitle.startsWith("_")) {
				if (type == 0) {
					query = """
						SELECT sp.*, u.description, AVG(sr.number) AS average_rating
						FROM STUDENT_PROFILES sp
						JOIN users u ON sp.id_stud = u.id_user
						LEFT JOIN student_ratings sr ON sp.id_stud = sr.stud_id
						WHERE sp.id_stud <= ? AND sp.title = ?
						GROUP BY sp.id_stud
						ORDER BY sp.id_stud DESC
						LIMIT 10;""";
				}
				else {
					query = """
						SELECT subquery.*, u.description, AVG(sr.number) AS average_rating
						FROM (
						    SELECT *
						    FROM STUDENT_PROFILES
						    WHERE id_stud > ? AND sp.title = ?
						    ORDER BY id_stud DESC
						    LIMIT 10
						) AS subquery
						JOIN users u ON subquery.id_stud = u.id_user
						LEFT JOIN student_ratings sr ON subquery.id_stud = sr.stud_id
                        GROUP BY subquery.id_stud;""";
				}
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, last);
				preparedStatement.setString(2, offerTitle);

				results = preparedStatement.executeQuery();
			}
			else {
				offerTitle = offerTitle.substring(1);
				String[] parts = offerTitle.split(",");

				if (type == 0) {
					query = "SELECT sp.*, u.description, AVG(sr.number) AS average_rating "
							+ "FROM STUDENT_PROFILES sp "
							+ "JOIN users u ON sp.id_stud = u.id_user "
							+ "LEFT JOIN student_ratings sr ON sp.id_stud = sr.stud_id "
							+ "WHERE id_stud <= ? AND (";
				}
				else {
					query = "SELECT subquery.*, u.description, AVG(sr.number) AS average_rating "
							+ "FROM (SELECT * FROM STUDENT_PROFILES WHERE id_stud > ? AND (";
				}

				for (int i = 0; i < parts.length; i++) {
					if (i > 0) {
						query += " OR ";
					}
					query += "tags LIKE ?";
				}
				
				if (type == 0) {
					query += ") GROUP BY sp.id_stud ORDER BY id_stud DESC LIMIT 10";
				}
				else {
					query += ") AS subquery JOIN users u ON subquery.id_stud = u.id_user "
							+ "LEFT JOIN student_ratings sr ON subquery.id_stud = sr.stud_id "
							+ "GROUP BY subquery.id_stud "
							+ "ORDER BY id_stud DESC LIMIT 10";
				}

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, last);

				for (int i = 0; i < parts.length; i++) {
					preparedStatement.setString(i + 2, "%" + parts[i] + "%");
				}

				results = preparedStatement.executeQuery();
			}

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("description");
				int rating = results.getInt("rating");
				String tags = results.getString("tags");
				Offer offer = new Offer(id_person, title, content, rating, tags);
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

			query = "SELECT sp.*, u.description" +
"						FROM STUDENT_PROFILES sp" +
"						JOIN users u ON sp.id_stud = u.id_user" +
"						WHERE sp.id_stud = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userID);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("description");
				int rating = results.getInt("rating");
				String tags = results.getString("tags");
				Offer offer = new Offer(id_person, title, content, rating, tags);
				searchedoffers.add(offer);
			}
		}
		catch (SQLException exp) {
			System.out.println(exp);
		}
		return searchedoffers;
	}

	public ArrayList<Offer> getEmployerOffer(int userID) {
		ArrayList<Offer> searchedoffers = new ArrayList<>();

		try {
			String query;

			query = "SELECT * FROM  OFFERS WHERE id_offer = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userID);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_offer = results.getInt("id_offer");
				int id_empl = results.getInt("id_empl");
				String title = results.getString("title");
				String content = results.getString("content");
				int salary = results.getInt("salary");
				String tags = results.getString("tags");
				Offer offer = new Offer(id_offer, id_empl, title, content, salary, tags);
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
			String sortBy = "";
			String sortOrder = "";

			switch (type) {
				case 0, 1 ->
					sortBy = "id_stud";
				case 2, 3 ->
					sortBy = "percentage";
				case 4, 5 ->
					sortBy = "title";
				case 6, 7 ->
					sortBy = "salary";
				case 8, 9 ->
					sortBy = "id_stud";
			}
			switch (type) {
				case 0, 2, 4, 6, 8 ->
					sortOrder = "ASC";
				case 1, 3, 5, 7, 9 ->
					sortOrder = "DESC";
			}

			String query = "SELECT sp.*, u.description, AVG(sr.number) AS average_rating FROM STUDENT_PROFILES sp JOIN users u ON sp.id_stud = u.id_user LEFT JOIN student_ratings sr ON sp.id_stud = sr.stud_id";

			if (sortBy.equals("percentage")) {
				query += " JOIN offer_calendar_comparisons AS occ ON sp.id_stud = occ.id_stud";
			}

			boolean was = false;
			String[] parts = new String[0];
			if (!searched.equals("")) {
				if (!searched.startsWith("_")) {
					query += " WHERE title = ?";
				}
				else {
					searched = searched.substring(1);
					parts = searched.split(",");

					query += " WHERE ";

					for (int i = 0; i < parts.length; i++) {
						if (i > 0) {
							query += " OR ";
						}
						query += "sp.tags LIKE ?";
					}
				}
				was = true;
			}

			if (max >= min && max >= 0 && min >= 0) {
				if (was) {
					query += " AND sp.rating BETWEEN ? AND ?";
				}
				else {
					query += " WHERE sp.rating BETWEEN ? AND ?";
				}
			}

			if (sortBy.equals("percentage")) {
				query += " GROUP BY sp.id_stud ORDER BY occ.value " + sortOrder;
			}
			else {
				query += " GROUP BY sp.id_stud ORDER BY " + sortBy + " " + sortOrder;
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int parameterIndex = 1;
			if (was) {
				if (!searched.startsWith("_")) {
					preparedStatement.setString(parameterIndex++, searched);
				}
				else {
					for (int i = 0; i < parts.length; i++) {
						preparedStatement.setString(i + 2, "%" + parts[i] + "%");
					}
				}
			}

			if (max >= min && max >= 0 && min >= 0) {
				preparedStatement.setInt(parameterIndex++, min);
				preparedStatement.setInt(parameterIndex, max);
			}

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("description");
				int rating = results.getInt("average_rating");
				String tags = results.getString("tags");
				Offer offer = new Offer(id_person, title, content, rating, tags);
				searchedoffers.add(offer);
			}

			boolean wasNum = false;
			int count = 0;
			switch (type2) {
				case 0 -> {
					while (results.next() && count < 10) {
						int id_person = results.getInt("id_stud");
						String title = results.getString("title");
						String content = results.getString("description");
						int rating = results.getInt("average_rating");
						String tags = results.getString("tags");
						Offer offer = new Offer(id_person, title, content, rating, tags);
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
								String content = results.getString("description");
								int rating = results.getInt("average_rating");
								String tags = results.getString("tags");
								Offer offer = new Offer(id_person, title, content, rating, tags);
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
							String content = results.getString("description");
							int rating = results.getInt("average_rating");
							String tags = results.getString("tags");
							Offer offer = new Offer(id_person, title, content, rating, tags);
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
							String content = results.getString("description");
							int rating = results.getInt("average_rating");
							String tags = results.getString("tags");
							Offer offer = new Offer(id_person, title, content, rating, tags);
							searchedoffers.add(0, offer);
							count++;
						}
					}
					if (count == 0) {
						while (results.next() && count < 10) {
							int id_person = results.getInt("id_stud");
							String title = results.getString("title");
							String content = results.getString("description");
							int rating = results.getInt("average_rating");
							String tags = results.getString("tags");
							Offer offer = new Offer(id_person, title, content, rating, tags);
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
				String tags = results.getString("tags");
				Offer offer = new Offer(id_offer, id_person, title, content, salary, tags);
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
				query = """
						SELECT sp.*, u.description, AVG(sr.number) AS average_rating
                        FROM STUDENT_PROFILES sp
                        JOIN users u ON sp.id_stud = u.id_user
                        LEFT JOIN student_ratings sr ON sp.id_stud = sr.stud_id
                        WHERE sp.id_stud <= ?
                        GROUP BY sp.id_stud
                        ORDER BY sp.id_stud DESC
                        LIMIT 10;""";
			}
			else {
				query = """
						SELECT subquery.*, u.description, AVG(sr.number) AS average_rating
                        FROM (
                            SELECT *
                            FROM STUDENT_PROFILES
                            WHERE id_stud > ?
                            ORDER BY id_stud DESC
                            LIMIT 10
                        ) AS subquery
                        JOIN users u ON subquery.id_stud = u.id_user
                        LEFT JOIN student_ratings sr ON subquery.id_stud = sr.stud_id
                        GROUP BY subquery.id_stud;""";
			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, last);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int id_person = results.getInt("id_stud");
				String title = results.getString("title");
				String content = results.getString("description");
				int rating = results.getInt("average_rating");
				String tags = results.getString("tags");
				Offer offer = new Offer(id_person, title, content, rating, tags);
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
				String tags = results.getString("tags");
				Offer offer = new Offer(id_offer, id_empl, title, content, salary, tags);
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

	public boolean addOffer(int id_empl, String title, String content, String salary, String tags) {
		try {
			int newMax = getLastOfferId() + 1;
			String dotSalary = salary;
			String insertQuery = "INSERT INTO offers (id_offer, id_empl, title, content, salary, tags) VALUES (?, ?, ?, ?, ?, ?)";
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
			insertStatement.setString(6, tags);

			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean editOffer(int id_offer, String title, String content, String salary, String tags) {
		try {
			String dotSalary;
			String insertQuery = "UPDATE offers SET title = ?, content = ?, salary = ?, tags = ? WHERE (id_offer = ?)";

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
			insertStatement.setString(4, tags);
			insertStatement.setInt(5, id_offer);

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
				String tags = results.getString("tags");
				return new Offer(id_offer, id_person, title, content, salary, tags);
			}
		}
		catch (Exception exp) {
			System.out.println(exp);
		}
		return new Offer(-2, -2, "Skipped try/catch", "Skipped try/catch", 0, "tag");
	}
}
