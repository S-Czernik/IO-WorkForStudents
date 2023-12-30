/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author adamk
 */
public class Notification {
    
    private String ID;
    private String messageType;
    private String userLogin;
    private String offerTitle;

    public Notification(String ID, String messageType, String userLogin, String offerTitle) {
        this.ID = ID;
        this.messageType = messageType;
        this.userLogin = userLogin;
        this.offerTitle = offerTitle;
    }

    // Getters and setters if needed
    public String getID() {return this.ID;}
    public String getMessageType() {return this.messageType;}
    public String getUserLogin() {return this.userLogin;}
    public String getOfferTitle() {return this.offerTitle;}
    
}