package model;

import model.notifications.Notification;
import model.offers.Offer;
import model.calendar.Kalyndarz;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.*;
import model.accounts.AccountInterface;
import model.calendar.CalendarInterface;
import model.notifications.NotificationInterface;
import model.offers.OfferInterface;

public class Model {

	static Model singleton = null;
	public Connection connection;
	public Statement statement;

	public CalendarInterface calendarInterface;
	public OfferInterface offerInterface;
	public NotificationInterface notificationInterface;
	public AccountInterface accountInterface;

    public static Model getModel() {
        if (singleton == null) {
            singleton = new Model();
            Interface.model = singleton;
            singleton.calendarInterface = new CalendarInterface();
            singleton.offerInterface = new OfferInterface();
            singleton.notificationInterface = new NotificationInterface();
            singleton.accountInterface = new AccountInterface();
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ioio?useSSL=false", "root", "szyman");
			statement = connection.createStatement();
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}
}
