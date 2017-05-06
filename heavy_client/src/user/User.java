package user;

public class User {

	public static final String ANONYMOUS = "anonymous";
	private boolean connected = false;
	private String login = ANONYMOUS;
	private String id ;

	public boolean isConnected() {
		return connected;
	}


	public void setConnected(model.User user) {
		this.login = user.getLogin();
		this.id = user.getId() ;
		connected = login.equals(ANONYMOUS) == false;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
