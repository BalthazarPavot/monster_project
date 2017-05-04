package monster_project ;

import java.sql.* ;

public class DatabaseHandler {
	
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver" ;
	private static final String DB_HOST = "localhost" ;
	private static final String DB_NAME = "monster_project" ;
	private static final String DB_PORT = "3306" ;
	private static final String DB_USER = "root" ;
	private static final String DB_PASSWORD = ":p" ;
	private static final String SERVER_URL = "jdbc:mysql://" + DB_HOST + ":" +
											 DB_PORT + "/" + DB_NAME ;


	private Connection conn ;

	public DatabaseHandler () {	
		System.out.println("Connecting to MySQL server ...") ;
		try {
			Class.forName (DRIVER_CLASS) ;
			this.conn = DriverManager.getConnection (SERVER_URL,
													DB_USER, DB_PASSWORD) ;
		} catch (SQLException e) {
			e.printStackTrace() ;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace() ;
		}
	}
	
	public void printAllRecord (String query) throws SQLException {
		Statement stmt = this.conn.createStatement() ;
		ResultSet rs = stmt.executeQuery (query) ;
		ResultSetMetaData rsmd = rs.getMetaData() ;
		int columnNumber = rsmd.getColumnCount() ;
		while ( rs.next() ) {
			for (int i=1 ; i < columnNumber ; i++ ) {
				System.out.print(rs.getString(i) + " ") ;
			}
			System.out.println() ;
		}
		rs.close() ;
		stmt.close() ;
	}
	
	public void closeConnection () throws SQLException {
			this.conn.close() ;
	}
}

