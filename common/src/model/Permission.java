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

    public Boolean getUserWrite() {
		return userWrite;
	}

	public void setUserWrite(Boolean userWrite) {
		this.userWrite = userWrite;
	}

	public Boolean getUserRead() {
		return userRead;
	}

	public void setUserRead(Boolean userRead) {
		this.userRead = userRead;
	}

	public Boolean getGroupWrite() {
		return groupWrite;
	}

	public void setGroupWrite(Boolean groupWrite) {
		this.groupWrite = groupWrite;
	}

	public Boolean getGroupRead() {
		return groupRead;
	}

	public void setGroupRead(Boolean groupRead) {
		this.groupRead = groupRead;
	}

	public Boolean getOtherWrite() {
		return otherWrite;
	}

	public void setOtherWrite(Boolean otherWrite) {
		this.otherWrite = otherWrite;
	}

	public Boolean getOtherRead() {
		return otherRead;
	}

	public void setOtherRead(Boolean otherRead) {
		this.otherRead = otherRead;
	}

    @Override
	public String toString() {
		return "Permission [user_write=" + userWrite + ", user_read=" + userRead + ", group_write=" + groupWrite
				+ ", group_read=" + groupRead + ", other_write=" + otherWrite + ", other_read=" + otherRead + "]";
	}
}
