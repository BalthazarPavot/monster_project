package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Client {

	final static String HEAVY = "heavy" ;
	final static String LIGHT = "light" ;

	@XmlAttribute
    private String type = new String (LIGHT);
    @XmlAttribute
    private String IP = "";
    @XmlAttribute
    private Integer port = 0;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isHeavy () {
		return type.equalsIgnoreCase(HEAVY) ;
	}

    @Override
	public String toString() {
		return "Client [type=" + type + ", IP=" + IP + ", port=" + port + "]";
	}
}
