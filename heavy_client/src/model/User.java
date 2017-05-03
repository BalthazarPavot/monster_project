package model;

import javax.xml.bind.annotation.*;

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

	public static User unpackXML(String XML) {
		return null;
	}

	public String packXML() {
		return null;
	}

	public model.Client getClient() {
		return client;
	}

	public void setClient(model.Client client) {
		this.client = client;
	}
}
