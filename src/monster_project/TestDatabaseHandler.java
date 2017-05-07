package monster_project;

import java.io.IOException;
import java.sql.SQLException;

public class TestDatabaseHandler {

	public static void main(String[] args) throws SQLException, IOException {
		DatabaseServer ds = new DatabaseServer();

		/*
		 * String initDb = "mysql -u " + DatabaseHandler.DB_USER +
		 * " --password=" + DatabaseHandler.DB_PASSWORD + " -e " +
		 * "\"source /home/tilito/workspace/monster_project/database_creation.sql\""
		 * ;
		 */

		// Runtime runtime = Runtime.getRuntime();
		// runtime.exec(initDb);
		// System.out.println(initDb);
		System.out.println("Début");
		System.out.println(ds.addUser("OOOOOOO", "111111111", "2222222222"));
		System.out.println(ds.addGroup("fe749668-8fc4-41b8-85ce-209ea498de7e", "grpp"));
		System.out.println("Fin");

		ds.closeConnection();
	}
}