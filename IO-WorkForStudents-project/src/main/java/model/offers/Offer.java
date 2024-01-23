package model.offers;

public class Offer {
    private int id_offer, id_person, rating;
    private String title;
    private String content;
    private String info;
	private int salary;

    public Offer(int id_offer, int id_empl, String title, String content, String info, int salary) {
        this.id_offer = id_offer;
        this.id_person = id_empl;
        this.title = title;
        this.content = content;
        this.info = info;
		this.salary = salary;
    }
    
    public Offer(int id_stud, String title, String content, String info, int rating) {
        this.id_person = id_stud;
        this.title = title;
        this.content = content;
        this.info = info;
		this.rating = rating;
    }

    public int getIdOffer() {return this.id_offer;}
    public int getIdPerson() {return this.id_person;}
    public String getTitle() {return this.title;}
    public String getContent() {return this.content;}
    public String getInfo() {return this.info;}
	public int getSalary() {return this.salary;}
	public int getRating() {return this.rating;}
}
