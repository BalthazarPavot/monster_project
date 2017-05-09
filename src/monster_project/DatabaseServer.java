package monster_project;

import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseServer {

	public static final int NO_ERROR = 1;
	public static final int ERR_STRING_TOO_LONG = 10;
	public static final int ERR_PSEUDO_EXISTS = 11;
	public static final int ERR_EMAIL_EXISTS = 12;
	public static final int ERR_BAD_PSEUDO_OR_PASS = 13;

	private DatabaseHandler dbh;

	public DatabaseServer() {
		this.dbh = new DatabaseHandler();
	}

	public String generateId() {
		String id = UUID.randomUUID().toString();
		return id;
	}

	/**
	 * Return true if the row doesn't exists
	 */
	public boolean checkUnique(String table, String field, String value) throws SQLException {
		String query = "SELECT COUNT(1) FROM `" + table + "` WHERE `" + field + "` = \'" + value + "\' ; ";
		return !dbh.executeChecking(query);
	}

	public boolean isInSimpleQuote(String fullString, String subString) {
		Pattern p = Pattern.compile(".*\\\'(.*)\\\'.*");
		Matcher m = p.matcher(fullString);
		while (m.find()) {
			if (subString.equals(m.group(1)))
				return true;
		}
		return false;
	}

	public int checkUser(String pseudo, String password, String email) throws SQLException {
		if (checkUnique("user", "pseudo", pseudo))
			return ERR_PSEUDO_EXISTS;
		if (checkUnique("user", "email", email))
			return ERR_EMAIL_EXISTS;
		return NO_ERROR;
	}

	public int addUser(String pseudo, String password, String email) throws SQLException {
		String query = "INSERT INTO `user` (`id`, `pseudo`, `password`, `email`) VALUES (\"" + generateId() + "\", \""
				+ pseudo + "\", \"" + password + "\", \"" + email + "\") ;";
		try {
			dbh.executeQuery(query);
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			if (isInSimpleQuote(e.getMessage(), "pseudo"))
				return ERR_PSEUDO_EXISTS;
			if (isInSimpleQuote(e.getMessage(), "email"))
				return ERR_EMAIL_EXISTS;
		} catch (SQLException e) {
			return ERR_STRING_TOO_LONG;
		}
		return NO_ERROR;
	}

	public int addUserInGroup(String idUser, String idGroup) throws SQLException {
		String query = "INSERT INTO `user_in_group` (`id_user`, `id_group`) VALUES (\"" + idUser + "\", \"" + idGroup
				+ "\") ;";
		dbh.executeQuery(query);
		return NO_ERROR;
	}

	public int addGroup(String idOwner, String name) throws SQLException {
		String idGroup = generateId();
		String query = "INSERT INTO `group` (`id`, `id_owner`, `name`) VALUES (\"" + idGroup + "\", \"" + idOwner
				+ "\", \"" + name + "\") ;";
		try {
			dbh.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return ERR_STRING_TOO_LONG;
		}
		System.out.println("Ca pass");
		return addUserInGroup(idOwner, idGroup);
	}

	public void closeConnection() throws SQLException {
		dbh.closeConnection();
	}

}