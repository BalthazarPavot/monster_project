package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import metadata.Context;
import model.Document;

public class Client {

	static private String USER_AGENT = "HeavyClient1.0 - Monster Project";
	static private String LOGIN_URL = "user/login";
	static private String REGISTER_URL = "user/new";
	static private String LOGGED_USERS_URL = "document/users";
	static private String PROJECT_CREATION_URL = "document/new";
	static private String PROJECT_OPEN_URL = "document/ask?owner_name={{owner_name}}&document_name={{document_name}}";
	static private String NEW_GROUP_URL = "group/new";
	static private String DELETE_GROUP_URL = "group/{{group_id}}";
	static private String UPDATE_DOCUMENT_URL = "document/up";

	private String protocol = "http";

	public HTTPResponse sendGetRequest(String url) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		StringBuffer result = null;
		network.HTTPResponse http_response = new HTTPResponse();

		request.addHeader("User-Agent", USER_AGENT);
		org.apache.http.HttpResponse response = null;
		try {
			response = client.execute(request);
			http_response.setErrorCode(response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			http_response.setContent(result.toString());
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
			http_response.setErrorCode(0);
		}
		http_response.setOriginalResponse(response);
		return http_response;
	}

	public HTTPResponse sendPostRequest(String url, HashMap<String, String> parameters)
			throws UnsupportedEncodingException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		StringBuffer result = null;
		HTTPResponse http_response = new HTTPResponse();

		post.setHeader("User-Agent", USER_AGENT);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for (String key : parameters.keySet()) {
			urlParameters.add(new BasicNameValuePair(key, parameters.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse response = null;
		try {
			response = client.execute(post);
			http_response.setErrorCode(response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			http_response.setContent(result.toString());
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
			http_response.setErrorCode(0);
		}
		http_response.setOriginalResponse(response);
		return http_response;
	}

	public HTTPResponse sendDeleteRequest(String url) throws UnsupportedEncodingException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(url);
		StringBuffer result = null;
		HTTPResponse http_response = new HTTPResponse();

		delete.setHeader("User-Agent", USER_AGENT);
		HttpResponse response = null;
		try {
			response = client.execute(delete);
			http_response.setErrorCode(response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			http_response.setContent(result.toString());
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
			http_response.setErrorCode(0);
		}
		http_response.setOriginalResponse(response);
		;
		return http_response;
	}

	private String getBaseURL() {
		return String.format("%s://%s", protocol, Context.singleton.getServerAdress());
	}

	private String getLoginURL() {
		return String.format("%s/%s", getBaseURL(), LOGIN_URL);
	}

	private String getRegisterURL() {
		return String.format("%s/%s", getBaseURL(), REGISTER_URL);
	}

	private String getConnectedUsersURL(String project_id) {
		return String.format("%s/%s", getBaseURL(), LOGGED_USERS_URL);
	}

	private String getCreateProjectURL() {
		return String.format("%s/%s", getBaseURL(), PROJECT_CREATION_URL);
	}

	private String getOpenProjectURL(String ownerName, String projectName) {
		return String.format("%s/%s", getBaseURL(),
				PROJECT_OPEN_URL.replace("{{owner_name}}", ownerName).replace("{{document_name}}", projectName));
	}

	private String getCreateGroupURL() {
		return String.format("%s/%s", getBaseURL(), NEW_GROUP_URL);
	}

	private String getDeleteGroupURL(String groupId) {
		return String.format("%s/%s", getBaseURL(), DELETE_GROUP_URL.replace("{{group_id}}", groupId));
	}

	private String getUpdateDocumentURL() {
		return String.format("%s/%s", getBaseURL(), UPDATE_DOCUMENT_URL);
	}

	public HTTPResponse getLoggedUsers(String project_id) throws UnsupportedEncodingException {
		HashMap<String, String> parameters = new HashMap<>();

		if (Context.singleton.user.isConnected()) {
			parameters.put("id", Context.singleton.user.getId());
			parameters.put("type", "heavy");
			parameters.put("ip", Context.singleton.getClientIP());
			parameters.put("port", Context.singleton.getClientPort().toString());
			return sendPostRequest(getConnectedUsersURL(project_id), parameters);
		} else {
			return new HTTPResponse(401);
		}
	}

	public HTTPResponse sendServerLoginRequest(String login, String password) {
		HashMap<String, String> parameters = new HashMap<>();

		parameters.put("login", login);
		parameters.put("password", password);
		try {
			return sendPostRequest(getLoginURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendServerRegisterRequest(String login, String password, String email) {
		HashMap<String, String> parameters = new HashMap<>();

		parameters.put("login", login);
		parameters.put("password", password);
		parameters.put("password_verif", password);
		parameters.put("email", password);
		try {
			return sendPostRequest(getRegisterURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendServerCreateProjectRequest(String name) {
		HashMap<String, String> parameters = new HashMap<>();

		if (Context.singleton.user.isConnected()) {
			parameters.put("name", name);
			parameters.put("user_id", Context.singleton.user.getId());
			try {
				return sendPostRequest(getCreateProjectURL(), parameters);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendServerOpenProjectRequest(String ownerName, String projectName) {
		return sendGetRequest(getOpenProjectURL(ownerName, projectName));
	}

	public HTTPResponse sendServerConfProjectRequest(model.Permission perms) {
		HashMap<String, String> parameters = new HashMap<>();
		String xmlPermissions;

		if (Context.singleton.user.isConnected() && Context.singleton.document.isLoaded()) {
			xmlPermissions = Context.singleton.modelManager.unmapPermission(perms);
			parameters.put("permissions", xmlPermissions);
			try {
				return sendPostRequest(getCreateProjectURL(), parameters);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendServerNewGroupRequest(String groupName) {
		HashMap<String, String> parameters = new HashMap<>();

		if (Context.singleton.user.isConnected()) {
			parameters.put("user_id", Context.singleton.user.getId());
			parameters.put("name", groupName);
			try {
				return sendPostRequest(getCreateGroupURL(), parameters);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendServerDeleteGroup(String groupName) {
		model.User user = Context.singleton.user;
		model.Group group;

		if (user.isConnected() && (group = user.getGroup(groupName)) != null) {
			try {
				return sendDeleteRequest(getDeleteGroupURL(group.getId()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse getDocumentContent(String documentID) {
		HashMap<String, String> parameters = new HashMap<>();

		parameters.put("document_id", documentID);
		try {
			return sendPostRequest(getUpdateDocumentURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendInsertDocumentContent(Document document, int offset, int length, String content) {
		HashMap<String, String> parameters = new HashMap<>();

		parameters.put("document_id", document.getId());
		parameters.put("type", "insert");
		parameters.put("offset", Integer.toString(offset));
		parameters.put("length", Integer.toString(length));
		parameters.put("content", content);
		try {
			return sendPostRequest(getUpdateDocumentURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new HTTPResponse(401);
	}

	public HTTPResponse sendRemoveDocumentContent(Document document, int offset, int length) {
		HashMap<String, String> parameters = new HashMap<>();

		parameters.put("document_id", document.getId());
		parameters.put("type", "remove");
		parameters.put("offset", Integer.toString(offset));
		parameters.put("length", Integer.toString(length));
		try {
			return sendPostRequest(getUpdateDocumentURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new HTTPResponse(401);
	}

}
