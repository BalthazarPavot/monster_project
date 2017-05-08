package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Document {
	private Permission permission = null ;
	private String content = null ;

    @XmlAttribute
    private String id ;
    @XmlAttribute
    private String owner_id;
    @XmlAttribute
    private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
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
		return "Document [permission=" + permission + ", content=" + content + ", id=" + id + ", owner_id=" + owner_id
				+ ", name=" + name + "]";
	}
}
