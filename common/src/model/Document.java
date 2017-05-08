package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Document {

	@XmlAttribute
	private String id = "-1";
	@XmlAttribute
	private String ownerId = "-1";
	@XmlAttribute
	private String name = "unamed";

	private Permission permission = new Permission();
	private String content = "";

	private Boolean loaded = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerid() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Document [permission=" + permission + ", content=" + content + ", id=" + id + ", ownerId=" + ownerId
				+ ", name=" + name + "]";
	}

	public Boolean isLoaded() {
		return loaded;
	}

	public void setLoaded() {
		loaded = true ;
	}
}
