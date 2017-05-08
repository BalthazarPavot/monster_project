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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import metadata.Context;
import model.Permission;

public class Client {

	static private String USER_AGENT = "HeavyClient1.0 - Monster Project";
	static private String LOGIN_URL = "user/login";
	static private String REGISTER_URL = "user/new";
	static private String LOGGED_USERS_URL = "project/users/{{project_id}}/{{format}}";
	static private String PROJECT_CREATION_URL = "document/new";
	static private String PROJECT_OPEN_URL = "document/{{owner_name}}/{{document_name}}";
	static private String NEW_GROUP_URL = "group/new";
	
	private String protocol = "http";

	public HTTPResponse sendGetRequest(String url) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		StringBuffer result = null;
		HTTPResponse http_response = new HTTPResponse();

		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = null;
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
		return String.format("%s/%s", getBaseURL(), LOGGED_USERS_URL).replace("{{project_id}}", project_id.toString())
				.replace("{{format}}", "xml");
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
		if (Context.singleton.user.isConnected())
			return sendGetRequest(getOpenProjectURL(ownerName, projectName));
		return new HTTPResponse(401);
	}

	public HTTPResponse sendServerConfProjectRequest(Permission perms) {
		HashMap<String, String> parameters = new HashMap<>();
		String xmlPermissions ;

		if (Context.singleton.user.isConnected() && Context.singleton.project.isLoaded()) {
			xmlPermissions = Context.singleton.modelManager.unmapPermission (perms) ;
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

}
