package database;

import java.sql.*;

public class DatabaseHandler {

	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	protected static final String DB_HOST = "localhost";
	protected static final String DB_NAME = "monster_project";
	protected static final String DB_PORT = "3306";
	protected static final String DB_USER = "root";
	protected static final String DB_PASSWORD = "eb42650";
	protected static final String SERVER_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

	private Connection conn;

	public Connection getConn() {
		return conn;
	}

	public DatabaseHandler() {
		try {
			Class.forName(DRIVER_CLASS);
			this.conn = DriverManager.getConnection(SERVER_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void printAllRecords(String query) throws SQLException {
		Statement stmt = this.conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnNumber; i++) {
				System.out.print(rs.getString(i) + " ");
			}
			System.out.println();
		}
		rs.close();
		stmt.close();
	}

	public void executeQuery(String query) throws SQLException {
		Statement stmt = this.conn.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
	}

	/**
	 * Check if there is a row in the query
	 * 
	 * @param query
	 * @return true if row exist
	 * @throws SQLException
	 */
	public boolean executeChecking(String query) {
		boolean out = false;
		Statement stmt;
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnNumber; i++) {
					out = Boolean.valueOf(rs.getString(i)) != null;
				}
			}
			rs.close();
			stmt.close();
			return out;
		} catch (SQLException e) {
			return false;
		}
	}

	public void closeConnection() throws SQLException {
		this.conn.close();
	}
}