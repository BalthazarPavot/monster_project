package model;

import javax.xml.bind.annotation.*;

/**
 * This class maps the group table
 * @author Balthazar Pavot
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Group {
	@XmlAttribute
	private String id ="-1" ;
    @XmlAttribute
    private String name = "unamed";
    @XmlAttribute
    private String owner_id = "-1";

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
	public String getOwner_id() {
		return owner_id;
	}

    /**
     * 
     * @param owner_id
     */
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

    /**
     * 
     */
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", owner_id=" + owner_id + "]";
	}

    /**
     * 
     * @param name
     * @return
     */
    public boolean hasName (String name) {
    	return this.name.equals(name) ;
    }
}
