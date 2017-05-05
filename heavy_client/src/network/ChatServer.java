package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import metadata.Context;
import screens.ChatManager;

public class ChatServer implements Runnable {

	private HashMap<String, ArrayList<String>> inputMessages = new HashMap<>();
	private int port;

	public ChatServer() {
		port = Context.singleton.getClientPort();
	}

	public ChatServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocket socket;

		socket = null;
		try {
			socket = new ServerSocket(port, 1);
			serve(socket);
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				Context.singleton.setSilencedError(e);
			}
		}
	}

	private void serve(ServerSocket socket) {
		boolean serving;

		serving = true;
		while (serving) {
			handleConnexions(socket);
		}
	}

	private void handleConnexions(ServerSocket socket) {
		Socket connexion;

		connexion = null;
		try {
			connexion = socket.accept();
			handleConnexion(socket, connexion);
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
		} finally {
			try {
				connexion.close();
			} catch (IOException e1) {
				Context.singleton.setSilencedError(e1);
			}
		}
	}

	private void handleConnexion(ServerSocket socket, Socket connexion) throws IOException {
		BufferedReader input;
		InputStreamReader inputReader;
		String message;
		String senderIP;
		int senderPort;
		String senderIdentifier;

		inputReader = null;
		try {
			inputReader = new InputStreamReader(connexion.getInputStream());
			input = new BufferedReader(inputReader);
			message = buildMessage(input);
			senderIP = connexion.getInetAddress().getHostAddress();
			senderPort = connexion.getPort();
			senderIdentifier = ChatManager.formatAdress (senderIP, senderPort);
			if (inputMessages.containsKey(senderIdentifier) == false)
				inputMessages.put(senderIdentifier, new ArrayList<>());
			inputMessages.get(senderIdentifier).add(message);
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
		} finally {
			try {
				inputReader.close();
			} catch (IOException e) {
				Context.singleton.setSilencedError(e);
			}
		}
	}

	private static String buildMessage(BufferedReader input) {
		StringBuffer result = new StringBuffer();
		char[] buffer = new char[4096];
		int offset = 0;

		try {
			while (input.read(buffer, offset, 4096) != 0) {
				result.append(buffer);
				offset += 4096;
			}
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
		}
		return result.toString();
	}

}
