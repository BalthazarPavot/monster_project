package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Permission {

	@XmlAttribute
	private Boolean userWrite ;
    @XmlAttribute
	private Boolean userRead ;
    @XmlAttribute
	private Boolean groupWrite ;
    @XmlAttribute
	private Boolean groupRead ;
    @XmlAttribute
	private Boolean otherWrite ;
    @XmlAttribute
	private Boolean otherRead ;

    @Override
	public String toString() {
		return "Permission [user_write=" + userWrite + ", user_read=" + userRead + ", group_write=" + groupWrite
				+ ", group_read=" + groupRead + ", other_write=" + otherWrite + ", other_read=" + otherRead + "]";
	}
}
