package metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.*;

import model.User;

/**
 * Maps a list of users
 * @author Balthazar Pavot
 *
 */
@XmlRootElement(name = "userlist")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserList implements Collection<model.User> {

	@XmlElement(name = "user")
	private List<model.User> users = null;

    /**
     * 
     */
	public UserList () {
		users = new ArrayList<>() ;
	}

    /**
     * 
     * @return
     */
	public List<User> getUsers() {
		return users;
	}

    /**
     * 
     * @param users
     */
	public void setUsers(List<User> users) {
		this.users = users;
	}

    /**
     * 
     */
	@Override
	public boolean add(User e) {
		return users.add(e);
	}

    /**
     * 
     */
	@Override
	public boolean addAll(Collection<? extends User> c) {
		return users.addAll(c);
	}

    /**
     * 
     */
	@Override
	public void clear() {
		users.clear();
	}

    /**
     * 
     */
	@Override
	public boolean contains(Object o) {
		return users.contains(o);
	}

    /**
     * 
     */
	@Override
	public boolean containsAll(Collection<?> c) {
		return users.containsAll(c);
	}

    /**
     * 
     */
	@Override
	public boolean isEmpty() {
		return users.isEmpty();
	}

    /**
     * 
     */
	@Override
	public Iterator<User> iterator() {
		return users.iterator();
	}

    /**
     * 
     */
	@Override
	public boolean remove(Object o) {
		return users.remove(o);
	}

    /**
     * 
     */
	@Override
	public boolean removeAll(Collection<?> c) {
		return users.removeAll(c);
	}

    /**
     * 
     */
	@Override
	public boolean retainAll(Collection<?> c) {
		return users.retainAll(c);
	}

    /**
     * 
     */
	@Override
	public int size() {
		return users.size();
	}

    /**
     * 
     */
	@Override
	public Object[] toArray() {
		return users.toArray();
	}

    /**
     * 
     */
	@Override
	public <T> T[] toArray(T[] a) {
		return users.toArray(a) ;
	}

}
