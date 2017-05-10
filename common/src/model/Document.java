package model;

import javax.xml.bind.annotation.*;

/**
 * This class maps the Document table.
 * 
 * @author Balthazar Pavot
 *
 */
@XmlRootElement(name = "document")
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

    /**
     * 
     * @return
     */
	public String getId() {
		return id;
	}

    /**
     * 
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

    /**
     * 
     * @return
     */
	public String getOwnerid() {
		return ownerId;
	}

    /**
     * 
     * @param ownerId
     */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

    /**
     * 
     * @return
     */
	public String getName() {
		return name;
	}

    /**
     * 
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * 
     * @return
     */
	public Permission getPermission() {
		return permission;
	}

    /**
     * 
     * @param permission
     */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

    /**
     * 
     * @return
     */
	public String getContent() {
		return content;
	}

    /**
     * 
     * @param content
     */
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * 
     */
	@Override
	public String toString() {
		return "Document [permission=" + permission + ", content=" + content + ", id=" + id + ", ownerId=" + ownerId
				+ ", name=" + name + "]";
	}

    /**
     * 
     * @return
     */
	public Boolean isLoaded() {
		return loaded;
	}

    /**
     * 
     */
	public void setLoaded() {
		loaded = true;
	}
}
