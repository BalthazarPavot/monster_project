package metadata;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.*;

import model.User;
import network.HTTPResponse;

public class ModelManager {

	public UserList getLoggedUsers() {
		return getLoggedUsers(Context.singleton.project.getID());
	}

	public UserList getLoggedUsers(String project_id) {
		UserList users = new UserList();
		HTTPResponse response = null;

		try {
			response = Context.singleton.client.getLoggedUsers(project_id);
			if (response.hasErrorCode(200)) {
				users = extractUserList(response);
			} else {
				System.err.println("Bad http error code while connected users gathering");
			}
		} catch (UnsupportedEncodingException e) {
			Context.singleton.setSilencedError(e);
		}
		return users;
	}

	private UserList extractUserList(HTTPResponse response) {
		JAXBContext jc = null;

		try {
			jc = JAXBContext.newInstance(UserList.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			return (UserList) unmarshaller.unmarshal(new StringReader(response.getContent()));
		} catch (JAXBException e) {
			Context.singleton.setSilencedError(e);
		}
		return null;
	}

	public User mapUser(String content) {
		JAXBContext jc = null;

		try {
			jc = JAXBContext.newInstance(model.User.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			return (model.User) unmarshaller.unmarshal(new StringReader(content));
		} catch (JAXBException e) {
			Context.singleton.setSilencedError(e);
		}
		return null;
	}

}
