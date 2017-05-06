package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import metadata.Context;
import screens.ChatManager;

public class ChatServer implements Runnable {

	private int port;

	private ChatManager chatManager = null ;

	public ChatServer(ChatManager chatManager) {
		port = Context.singleton.getClientPort();
		this.chatManager = chatManager ;
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
		String senderLogin ;

		inputReader = null;
		try {
			inputReader = new InputStreamReader(connexion.getInputStream());
			input = new BufferedReader(inputReader);
			message = buildMessage(input);
			senderLogin = message.substring(0, message.indexOf(" - ")) ;
			message = message.substring(message.indexOf(" - ")+3) ;
			if (chatManager.screen.hasDiscussionTab (senderLogin) == false) {
				chatManager.screen.addDiscussionTab(senderLogin);
			}
			chatManager.screen.addMessageToDiscussionTab(senderLogin, senderLogin, message);
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

		try {
			while (input.read(buffer, 0, 4096) != -1) {
				result.append(buffer);
			}
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
		}
		return result.toString();
	}

}
