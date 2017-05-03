package monster_project ;

import java.sql.* ;
import java.util.ArrayList ;

public class DatabaseHandler {
	
	private static String DB_HOST = "127.0.0.1" ;
	private static String DB_USER = "root" ;
	private static String DB_PASSWORD = "trouduc" ;

	private Connection con ;

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

