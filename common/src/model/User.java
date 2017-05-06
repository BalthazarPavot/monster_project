package model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	private model.Client client = null ;

    @XmlAttribute
	private String id = null;
	@XmlAttribute
	private String login = null;
    @XmlAttribute
	private String email = null;
    @XmlAttribute
	private String password = null;

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

	@Override
	public String toString() {
		return "User [client=" + client + ", id=" + id + ", login=" + login + ", email=" + email + ", password="
				+ password + "]";
	}

}
