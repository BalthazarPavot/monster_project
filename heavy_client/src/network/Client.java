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

public class Client {

	static private String USER_AGENT = "HeavyClient1.0 - Monster Project";

	private HTTPResponse sendGetRequest(String url) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		StringBuffer result = null;
		HTTPResponse http_response = new HTTPResponse();

		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response;
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
			return null;
		}
		return http_response;
	}

	private HTTPResponse sendPostRequest(String url, HashMap<String, String> parameters)
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
		HttpResponse response;
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
			return null;
		}
		return http_response;
	}

	public HTTPResponse sendServerLoginRequest(String login, String password) {
		HashMap<String, String> parameters = new HashMap<>();

		try {
			parameters.put("login", login);
			parameters.put("password", password);
			return sendPostRequest(getLoginURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean sendServerExistLogin(String login) {
		sendGetRequest(String.format("http://%s/logins", Context.singleton.server_adress));
		return false;
	}

	public HTTPResponse sendServerRegisterRequest(String login, String password, String email) {
		HashMap<String, String> parameters = new HashMap<>();

		try {
			parameters.put("login", login);
			parameters.put("password", password);
			parameters.put("password_verif", password);
			parameters.put("email", password);
			return sendPostRequest(getRegisterURL(), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
