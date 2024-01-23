package model.notifications;

public class Notification {

	private String ID;
	private String messageType;
        private String IDUser;
	private String userLogin;
        private String IDOffer;
	private String offerTitle;

	public Notification(String ID, String messageType, String userLogin, String offerTitle, String IDUser, String IDOffer) {
		this.ID = ID;
		this.messageType = messageType;
		this.userLogin = userLogin;
		this.offerTitle = offerTitle;
                this.IDUser= IDUser;
                this.IDOffer = IDOffer;
	}
        
        public Notification(String ID,  String IDUser, String IDOffer, String messageType) {
		this.ID = ID;
		this.messageType = messageType;
                this.IDUser= IDUser;
                this.IDOffer = IDOffer;
	}

	// Getters and setters if needed
	public String getID() {
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
        
	public String getOfferID() {
		return this.IDOffer;
	}
        
	public String getUserID() {
		return this.IDUser;
	}        

}
