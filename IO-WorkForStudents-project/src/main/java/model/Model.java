package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.accounts.AccountInterface;
import model.calendar.CalendarInterface;
import model.notifications.NotificationInterface;
import model.offers.OfferInterface;
import model.rating.RatingInterface;

public class Model {

	static Model singleton = null;
	public Connection connection;

	public CalendarInterface calendarInterface;
	public OfferInterface offerInterface;
	public NotificationInterface notificationInterface;
	public AccountInterface accountInterface;
        public RatingInterface ratingInterface;

    public static Model getModel() {
        if (singleton == null) {
            singleton = new Model();
            Interface.model = singleton;
            singleton.calendarInterface = new CalendarInterface();
            singleton.offerInterface = new OfferInterface();
            singleton.notificationInterface = new NotificationInterface();
            singleton.accountInterface = new AccountInterface();
            singleton.ratingInterface = new RatingInterface();
        }

        return singleton;
    }

	public Model() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e);
		}
		connect();

	}

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "1234");
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}
}
