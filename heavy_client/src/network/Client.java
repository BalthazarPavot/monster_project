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

	private String sendGetRequest(String url) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		StringBuffer result = null;

		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response;
		try {
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	private String sendPostRequest(String url, HashMap<String, String> parameters) throws UnsupportedEncodingException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		StringBuffer result = null;

		post.setHeader("User-Agent", USER_AGENT);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for (String key:parameters.keySet()) {
			urlParameters.add(new BasicNameValuePair(key, parameters.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse response;
		try {
			response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public boolean sendServerLoginRequest(String login, String password) {
		HashMap<String, String> parameters = new HashMap<>();

		try {
			parameters.put("login", login);
			parameters.put("password", password);
			sendPostRequest(String.format("http://%s/login", Context.singleton.server_adress), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean sendServerExistLogin(String login) {
		sendGetRequest(String.format("http://%s/logins", Context.singleton.server_adress));
		return true;
	}

	public boolean sendServerRegisterRequest(String login, String password, String email) {
		HashMap<String, String> parameters = new HashMap<>();

		try {
			parameters.put("login", login);
			parameters.put("password", password);
			parameters.put("password_verif", password);
			parameters.put("email", password);
			sendPostRequest(String.format("http://%s/register", Context.singleton.server_adress), parameters);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}

}
