package model;

import java.sql.Connection;
import java.sql.Statement;

public abstract class Interface {

	public Model model;
	public Connection connection;
	public Statement statement;


	public Interface() {
		model = Model.getModel();
		connection = model.connection;
		statement = model.statement;
	}
}
