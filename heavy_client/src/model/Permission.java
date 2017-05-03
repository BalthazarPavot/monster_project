package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Permission {

    @XmlAttribute
	private Boolean user_write ;
    @XmlAttribute
	private Boolean user_read ;
    @XmlAttribute
	private Boolean group_write ;
    @XmlAttribute
	private Boolean group_read ;
    @XmlAttribute
	private Boolean other_write ;
    @XmlAttribute
	private Boolean other_read ;
}
