package model;

import java.sql.Connection;
import java.sql.Statement;

public abstract class Interface {

	public static Model model;
	public Connection connection;


	public Interface() {
		connection = model.connection;
	}
}
