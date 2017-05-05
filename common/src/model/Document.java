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
