package model;

import javax.xml.bind.annotation.*;

/**
 * This class maps the Client table.
 * 
 * @author Balthazar Pavot
 *
 */
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

    /**
     * 
     * @return
     */
	public String getType() {
		return type;
	}

    /**
     * 
     * @param type
     */
	public void setType(String type) {
		this.type = type;
	}

    /**
     * 
     * @return
     */
	public String getIP() {
		return IP;
	}

    /**
     * 
     * @param iP
     */
	public void setIP(String iP) {
		IP = iP;
	}

    /**
     * 
     * @return
     */
	public Integer getPort() {
		return port;
	}

    /**
     * 
     * @param port
     */
	public void setPort(Integer port) {
		this.port = port;
	}

    /**
     * 
     * @return
     */
	public boolean isHeavy () {
		return type.equalsIgnoreCase(HEAVY) ;
	}

    /**
     * 
     */
    @Override
	public String toString() {
		return "Client [type=" + type + ", IP=" + IP + ", port=" + port + "]";
	}
}
