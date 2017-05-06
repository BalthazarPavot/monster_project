package user;

public class Project {

	private String ID = "1";
	private String name = "untitled";
	private Boolean WriteUser = false;
	private Boolean WriteGroup = false;
	private Boolean WriteOther = false;
	private Boolean ReadUser = false;
	private Boolean ReadGroup = false;
	private Boolean ReadOther = false;
	private Boolean loaded = false;

	public void setID (String ID) {
		this.ID = ID ;
	}
	public String getID () {
		return ID ;
	}

	public void loadProject() {
		loaded = true;
	}

	public Boolean isLoaded() {
		return loaded;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getWriteUser() {
		return WriteUser;
	}

	public void setWriteUser(Boolean writeUser) {
		WriteUser = writeUser;
	}

	public Boolean getWriteGroup() {
		return WriteGroup;
	}

	public void setWriteGroup(Boolean writeGroup) {
		WriteGroup = writeGroup;
	}

	public Boolean getWriteOther() {
		return WriteOther;
	}

	public void setWriteOther(Boolean writeOther) {
		WriteOther = writeOther;
	}

	public Boolean getReadUser() {
		return ReadUser;
	}

	public void setReadUser(Boolean readUser) {
		ReadUser = readUser;
	}

	public Boolean getReadGroup() {
		return ReadGroup;
	}

	public void setReadGroup(Boolean readGroup) {
		ReadGroup = readGroup;
	}

	public Boolean getReadOther() {
		return ReadOther;
	}

	public void setReadOther(Boolean readOther) {
		ReadOther = readOther;
	}

}
