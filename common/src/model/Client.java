package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Client {
	@XmlAttribute
    private String type ;
    @XmlAttribute
    private String IP ;
    @XmlAttribute
    private Integer port ;

    @Override
	public String toString() {
		return "Client [type=" + type + ", IP=" + IP + ", port=" + port + "]";
	}
}
