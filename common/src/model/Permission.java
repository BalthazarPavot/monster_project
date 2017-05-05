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

    @Override
	public String toString() {
		return "Permission [user_write=" + user_write + ", user_read=" + user_read + ", group_write=" + group_write
				+ ", group_read=" + group_read + ", other_write=" + other_write + ", other_read=" + other_read + "]";
	}
}
