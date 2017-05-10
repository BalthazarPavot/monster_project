package metadata;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.*;

import network.HTTPResponse;

public class ModelManager {

    /**
     * Returns all the users logged for the curent project.
     * @return
     */
	public UserList getLoggedUsers() {
		return getLoggedUsers(Context.singleton.document.getId());
	}

    /**
     * Returns all the users logged for the given project.
     * @param project_id
     * @return
     */
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

    /**
     * Creates a list of users from their xml representation.
     * @param response
     * @return
     */
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

    /**
     * Creates a user instance from its xml representation.
     * @param content
     * @return
     */
	public model.User mapUser(String content) {
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

    /**
     * Creates a Document instance from its xml representation.
     * @param content
     * @return
     */
	public model.Document mapProject(String content) {
		JAXBContext jc = null;

		try {
			jc = JAXBContext.newInstance(model.Document.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			return (model.Document) unmarshaller.unmarshal(new StringReader(content));
		} catch (JAXBException e) {
			Context.singleton.setSilencedError(e);
		}
		return null;
	}

    /**
     * unmap the codument from the context.
     * @return
     */
	public String unmapDocument() {
		return unmapDocument(Context.singleton.document);
	}

    /**
     * Takes a document as input and outputs its xml representation.
     * @param doc
     * @return
     */
	public String unmapDocument(model.Document doc) {
		JAXBContext jc = null;
		StringWriter result = new StringWriter();

		try {
			jc = JAXBContext.newInstance(model.Document.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(doc, result);
		} catch (JAXBException e) {
			Context.singleton.setSilencedError(e);
		}
		return result.toString();
	}

    /**
     * Takes a permission as inut and outputs its xml representation.
     * @param perms
     * @return
     */
	public String unmapPermission(model.Permission perms) {
		JAXBContext jc = null;
		StringWriter result = new StringWriter();

		try {
			jc = JAXBContext.newInstance(model.Permission.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(perms, result);
		} catch (JAXBException e) {
			Context.singleton.setSilencedError(e);
		}
		return result.toString();
	}

}
