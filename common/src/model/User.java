package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

/**
 * This class maps the user table.
 * @author Balthazar Pavot
 *
 */
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

    /**
     * 
     * @return
     */
	public boolean isConnected() {
		return connected;
	}

    /**
     * Set the user connected with the given user's login and id.
     * @param user
     */
	public void setConnected(model.User user) {
		this.login = user.getLogin();
		this.id = user.getId() ;
		connected = login.equals(ANONYMOUS) == false;
	}

    /**
     * Set the user connected with the given login
     * @param login
     */
	public void setConnected(String login) {
		this.login = login;
		connected = login.equals(ANONYMOUS) == false;
	}

    /**
     * 
     */
	public void logout() {
		setConnected(ANONYMOUS);
	}

    /**
     * 
     * @return
     */
    public String getId() {
		return id;
	}

    /**
     * 
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

    /**
     * 
     * @return
     */
	public String getLogin() {
		return login;
	}

    /**
     * 
     * @param login
     */
	public void setLogin(String login) {
		this.login = login;
	}

    /**
     * 
     * @return
     */
	public String getEmail() {
		return email;
	}

    /**
     * 
     * @param email
     */
	public void setEmail(String email) {
		this.email = email;
	}

    /**
     * 
     * @return
     */
	public String getPassword() {
		return password;
	}

    /**
     * 
     * @param password
     */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * 
     * @return
     */
	public model.Client getClient() {
		return client;
	}

    /**
     * 
     * @param client
     */
	public void setClient(model.Client client) {
		this.client = client;
	}

    /**
     * 
     * @return
     */
	public ArrayList<Group> getGroups() {
		return groups;
	}

    /**
     * 
     * @param groups
     */
	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

    /**
     * Return the group given its name owned by the user, if exists.
     * @param groupName
     * @return
     */
	public Group getGroup(String groupName) {
		for (Group group:groups) {
			if (group.hasName(groupName))
				return group ;
		}
		return null ;
	}

    /**
     * 
     */
	@Override
	public String toString() {
		return "User [client=" + client + ", id=" + id + ", login=" + login + ", email=" + email + ", password="
				+ password + "]";
	}

}
