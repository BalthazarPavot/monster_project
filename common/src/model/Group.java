package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Group {

    @XmlAttribute
	private String id ;
    @XmlAttribute
    private String name ;
    @XmlAttribute
    private String owner_id ;
}
