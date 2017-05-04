package monster_project;

import java.sql.SQLException;

public class TestDatabaseHandler {

	public static void main(String[] args) throws SQLException {
		DatabaseHandler dbh = new DatabaseHandler() ;
		
		dbh.printAllRecord ("SELECT * FROM user ; ") ;
	}
}