package model;

public class Offer {
    private int id_offer, id_empl;
    private String title;
    private String content;
    private String info;

    public Offer(int id_offer, int id_empl, String title, String content, String info) {
        this.id_offer = id_offer;
        this.id_empl = id_empl;
        this.title = title;
        this.content = content;
        this.info = info;
    }

    public int getIdOffer() {return this.id_offer;}
    public int getIdEmpl() {return this.id_empl;}
    public String getTitle() {return this.title;}
    public String getContent() {return this.content;}
    public String getInfo() {return this.info;}
}
