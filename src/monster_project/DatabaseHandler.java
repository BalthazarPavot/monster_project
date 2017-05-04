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
			Class.forName("com.mysql.jdbc.Driver") ;
			con = DriverManager.getConnection("jdbc:mysql://" + DB_HOST, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			System.out.println("Error : ClassNotFoundException") ;
			e.printStackTrace() ;
		}
		catch (ClassNotFoundException e) {
			System.out.println("Error : ClassNotFoundException") ;
			e.printStackTrace() ;
		}
	}
	
	public void printAllRecord (Statement st, String query, ArrayList attributeList) throws SQLException {
		ResultSet rs = st.executeQuery(query) ;
		while (rs.next()) {
			for (int i=0 ; i < attributeList.size() ; i++) {
				System.out.println(attributeList.get(i)) ;
			}
		}
	}
//
//	public Connection getCon() {
//		return con ;
//	}
//
//	public void setCon (Connection con) {
//		this.con = con ;
//	}
}

