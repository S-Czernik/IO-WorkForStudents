/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.rating;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Interface;

/**
 *
 * @author adamk
 */
public class RatingInterface extends Interface {
    
    public int getLastStudentRatingId() {
		try {
			String query = "SELECT MAX(id_stud_rating) AS last_rating FROM student_ratings";
			ResultSet results = connection.createStatement().executeQuery(query);

			if (results.next()) {
				return results.getInt("last_rating");
			}
			return 0;
		}
		catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}
    
    public boolean addStudentRating(int userID, String content, int stars) {
		try {
			int newMax = getLastStudentRatingId() + 1;
			
			String insertQuery = "INSERT INTO student_ratings (id_stud_rating, number, comment, stud_id) VALUES (?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, String.valueOf(newMax));
			insertStatement.setInt(2, stars);
			insertStatement.setString(3, content);
                        insertStatement.setInt(4, userID);
			
			insertStatement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
    
    public ArrayList<Rating> getStudentRating(int stud_id) {
        ArrayList<Rating> ratings = new ArrayList<>();
        try {
            
                    String query = "SELECT * FROM student_ratings WHERE stud_id = ?";

                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, stud_id);
                            ResultSet results = preparedStatement.executeQuery();

                            while (results.next()) {
                                    int ratingID = results.getInt("id_stud_rating");
                                    int number = results.getInt("number");
                                    String comment = results.getString("comment");

                                    Rating rating = new Rating(ratingID, number, comment, stud_id);
                                    ratings.add(rating);
                            }
            }
            catch (Exception e) {
                    System.out.println(e);
                    
            }
        return ratings;
    }
}
