package model;

import java.sql.Connection;

public abstract class Interface {

	public static Model model;
	public Connection connection;

	public Interface() {
		connection = model.connection;
	}
}
