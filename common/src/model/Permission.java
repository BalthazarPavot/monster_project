package model;

import javax.xml.bind.annotation.*;

/**
 * This class maps the permission table.
 * 
 * @author Balthazar Pavot
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Permission {

	@XmlAttribute
	private Boolean userWrite = true;
	@XmlAttribute
	private Boolean userRead = true;
	@XmlAttribute
	private Boolean groupWrite = true;
	@XmlAttribute
	private Boolean groupRead = true;
	@XmlAttribute
	private Boolean otherWrite = false;
	@XmlAttribute
	private Boolean otherRead = false;

    /**
     * 
     * @return
     */
	public Boolean getUserWrite() {
		return userWrite;
	}

    /**
     * 
     * @param userWrite
     */
	public void setUserWrite(Boolean userWrite) {
		this.userWrite = userWrite;
	}

    /**
     * 
     * @return
     */
	public Boolean getUserRead() {
		return userRead;
	}

    /**
     * 
     * @param userRead
     */
	public void setUserRead(Boolean userRead) {
		this.userRead = userRead;
	}

    /**
     * 
     * @return
     */
	public Boolean getGroupWrite() {
		return groupWrite;
	}

    /**
     * 
     * @param groupWrite
     */
	public void setGroupWrite(Boolean groupWrite) {
		this.groupWrite = groupWrite;
	}

    /**
     * 
     * @return
     */
	public Boolean getGroupRead() {
		return groupRead;
	}

    /**
     * 
     * @param groupRead
     */
	public void setGroupRead(Boolean groupRead) {
		this.groupRead = groupRead;
	}

    /**
     * 
     * @return
     */
	public Boolean getOtherWrite() {
		return otherWrite;
	}

    /**
     * 
     * @param otherWrite
     */
	public void setOtherWrite(Boolean otherWrite) {
		this.otherWrite = otherWrite;
	}

    /**
     * 
     * @return
     */
	public Boolean getOtherRead() {
		return otherRead;
	}

    /**
     * 
     * @param otherRead
     */
	public void setOtherRead(Boolean otherRead) {
		this.otherRead = otherRead;
	}

    /**
     * 
     */
	@Override
	public String toString() {
		return "Permission [user_write=" + userWrite + ", user_read=" + userRead + ", group_write=" + groupWrite
				+ ", group_read=" + groupRead + ", other_write=" + otherWrite + ", other_read=" + otherRead + "]";
	}
}
