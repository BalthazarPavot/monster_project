package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import metadata.Context;
import screens.ChatManager;

/**
 * The server for the chat
 * 
 * @author Balthazar Pavot
 *
 */
public class ChatServer implements Runnable {

	private int port;

	private ChatManager chatManager = null;

	private boolean serving = false;

	/**
	 * 
	 * @param chatManager
	 */
	public ChatServer(ChatManager chatManager) {
		port = Context.singleton.getClientPort();
		this.chatManager = chatManager;
	}

	/**
	 * 
	 * @param port
	 */
	public ChatServer(int port) {
		this.port = port;
	}

	/**
	 * stops the server.
	 */
	public void stop() {
		serving = false;
	}

	/**
	 * Launche the server by opening sockets and running the server.
	 */
	@Override
	public void run() {
		ServerSocket socket;

		socket = null;
		try {
			socket = new ServerSocket(port, 1);
			socket.setSoTimeout(100);
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

	/**
	 * Run the server.
	 * 
	 * @param socket
	 */
	private void serve(ServerSocket socket) {
		serving = true;
		while (serving) {
			handleConnexions(socket);
		}
	}

	/**
	 * Handle the connexions, one by one.
	 * 
	 * @param socket
	 */
	private void handleConnexions(ServerSocket socket) {
		Socket connexion;

		connexion = null;
		try {
			connexion = socket.accept();
			handleConnexion(socket, connexion);
		} catch (java.net.SocketTimeoutException e) {
			/*
			 * Also catch InterruptedException. Do nothing, because it's normal
			 * to appen. The timeout is very low to often check if we should
			 * still serve. So it will appen very often (every 100ms), so we
			 * should not use the silenced error not to be overloaded by useless
			 * silenced errors.
			 */
		} catch (IOException e) {
			Context.singleton.setSilencedError(e);
		} finally {
			try {
				if (connexion != null)
					connexion.close();
			} catch (IOException e) {
				Context.singleton.setSilencedError(e);
			}
		}
	}

	/**
	 * Handle one connexion by extracting the message and send it to the screen.
	 * 
	 * @param socket
	 * @param connexion
	 * @throws IOException
	 */
	private void handleConnexion(ServerSocket socket, Socket connexion) throws IOException {
		BufferedReader input;
		InputStreamReader inputReader;
		String message;
		String senderLogin;

		inputReader = null;
		try {
			inputReader = new InputStreamReader(connexion.getInputStream());
			input = new BufferedReader(inputReader);
			message = buildMessage(input);
			senderLogin = message.substring(0, message.indexOf(ChatManager.messageSeparator));
			message = message
					.substring(message.indexOf(ChatManager.messageSeparator) + ChatManager.messageSeparator.length());
			if (chatManager.screen.hasDiscussionTab(senderLogin) == false) {
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

	/**
	 * rebuilds the message the the socket stream
	 * 
	 * @param input
	 * @return
	 */
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
