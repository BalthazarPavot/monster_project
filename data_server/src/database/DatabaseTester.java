package database;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseTester {
	
	public static void main(String[] args) throws SQLException, IOException {
		DatabaseServer dbs = new DatabaseServer();
		System.out.println("Début");
		System.out.println("Fin");

		dbs.closeConnection();
	}
}