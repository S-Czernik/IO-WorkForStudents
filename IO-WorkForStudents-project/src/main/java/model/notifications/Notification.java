package model.notifications;

public class Notification {

	private int ID;
	private String messageType;
	private int IDUser;
	private String userLogin;
	private int IDOffer;
	private String offerTitle;

	public Notification(int ID, String messageType, String userLogin, String offerTitle, int IDUser, int IDOffer) {
		this.ID = ID;
		this.messageType = messageType;
		this.userLogin = userLogin;
		this.offerTitle = offerTitle;
		this.IDUser = IDUser;
		this.IDOffer = IDOffer;
	}

	public Notification(int ID, int IDUser, int IDOffer, String messageType) {
		this.ID = ID;
		this.messageType = messageType;
		this.IDUser = IDUser;
		this.IDOffer = IDOffer;
	}

	// Getters and setters if needed
	public int getID() {
		return this.ID;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public String getUserLogin() {
		return this.userLogin;
	}

	public String getOfferTitle() {
		return this.offerTitle;
	}

	public int getOfferID() {
		return this.IDOffer;
	}

	public int getUserID() {
		return this.IDUser;
	}

}
