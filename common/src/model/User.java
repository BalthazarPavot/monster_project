package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	public static final String ANONYMOUS = "anonymous";

	private model.Client client = new Client () ;

    @XmlAttribute
	private String id = "-1";
	@XmlAttribute
	private String login = new String (ANONYMOUS);
    @XmlAttribute
	private String email = "";
    @XmlAttribute
	private String password = "";

	private ArrayList<Group> groups = new ArrayList<> () ;
	private boolean connected = false;

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

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public model.Client getClient() {
		return client;
	}

	public void setClient(model.Client client) {
		this.client = client;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public Group getGroup(String groupName) {
		for (Group group:groups) {
			if (group.hasName(groupName))
				return group ;
		}
		return null ;
	}

	@Override
	public String toString() {
		return "User [client=" + client + ", id=" + id + ", login=" + login + ", email=" + email + ", password="
				+ password + "]";
	}

}
