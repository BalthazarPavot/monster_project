package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Group {
	@XmlAttribute
	private String id ="-1" ;
    @XmlAttribute
    private String name = "unamed";
    @XmlAttribute
    private String owner_id = "-1";

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", owner_id=" + owner_id + "]";
	}

    public boolean hasName (String name) {
    	return this.name.equals(name) ;
    }
}
