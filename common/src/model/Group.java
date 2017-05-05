package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Group {
	@XmlAttribute
	private String id ;
    @XmlAttribute
    private String name ;
    @XmlAttribute
    private String owner_id ;

    @Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", owner_id=" + owner_id + "]";
	}
}
