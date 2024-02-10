
package model.rating;

public class Rating {
    private int id_user_rating;
    private int stars;
    private String content;
    private int user_id;

    public Rating(int id_user_rating, int stars, String content, int user_id) {
        this.content = content;
        this.id_user_rating = id_user_rating;
        this.stars = stars;
        this.user_id = user_id;
    }

    public int getIdRating() {return this.id_user_rating;}
    public int getStars() {return this.stars;}
    public String getContent() {return this.content;}
    public int getUserID() {return this.user_id;}

}
