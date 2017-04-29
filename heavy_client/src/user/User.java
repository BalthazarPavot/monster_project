package user;

public class User {

	public static final String ANONYMOUS = "anonymous";
	private boolean connected = false;
	private String login = ANONYMOUS;

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(String login) {
		this.login = login;
		connected = login.equals(ANONYMOUS) == false;
	}

	public void logout() {
		setConnected(ANONYMOUS);
	}

	public String getLogin() {
		return login;
	}

}
