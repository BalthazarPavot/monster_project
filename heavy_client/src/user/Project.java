package user;

import model.Document;

public class Project {

	private String ID = "1";
	private String name = "untitled";
	private StringBuilder content = new StringBuilder();
	private Boolean writeUser = false;
	private Boolean writeGroup = false;
	private Boolean writeOther = false;
	private Boolean readUser = false;
	private Boolean readGroup = false;
	private Boolean readOther = false;
	private Boolean loaded = false;

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void loadProject(Document mappedProject) {
		name = mappedProject.getName();
		ID = mappedProject.getId();
		setContent(new StringBuilder(mappedProject.getContent()));
		writeUser = mappedProject.getPermission().getUserWrite();
		readUser = mappedProject.getPermission().getUserRead();
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
		return writeUser;
	}

	public void setWriteUser(Boolean writeUser) {
		this.writeUser = writeUser;
	}

	public Boolean getWriteGroup() {
		return writeGroup;
	}

	public void setWriteGroup(Boolean writeGroup) {
		this.writeGroup = writeGroup;
	}

	public Boolean getWriteOther() {
		return writeOther;
	}

	public void setWriteOther(Boolean writeOther) {
		this.writeOther = writeOther;
	}

	public Boolean getReadUser() {
		return readUser;
	}

	public void setReadUser(Boolean readUser) {
		this.readUser = readUser;
	}

	public Boolean getReadGroup() {
		return readGroup;
	}

	public void setReadGroup(Boolean readGroup) {
		this.readGroup = readGroup;
	}

	public Boolean getReadOther() {
		return readOther;
	}

	public void setReadOther(Boolean readOther) {
		this.readOther = readOther;
	}

	public StringBuilder getContent() {
		return content;
	}

	public void setContent(StringBuilder content) {
		this.content = content;
	}

}
