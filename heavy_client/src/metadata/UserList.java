package metadata;

import java.util.List;

import javax.xml.bind.annotation.*;

import model.User;

@XmlRootElement(name = "userlist")
@XmlAccessorType (XmlAccessType.FIELD)
public class UserList {

    @XmlElement(name = "user")
    private List<User> users = null;
    
    public List<User> getUsers() {
        return users;
    }
 
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
