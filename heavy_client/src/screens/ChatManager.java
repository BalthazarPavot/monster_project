package screens;

import java.awt.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import metadata.Context;
import metadata.UserList;
import model.Client;
import network.ChatClient;
import network.ChatServer;

public class ChatManager implements Runnable {

	private boolean running = true;
	public static final String messageSeparator = " - ";
	static private Integer HEART_BEAT_RATE = 500;
	private Context context = Context.singleton;
	private ChatClient chatClient = null;
	private ChatServer chatServer;
	private Thread chatServerThread;
	private int rate = HEART_BEAT_RATE;
	public HashMap<String, String> loginToAdress = new HashMap<>();
	public HashMap<String, String> adressToLogin = new HashMap<>();
	public MainScreen screen = null;

	public static String formatAdress(String ip, int port) {
		return String.format("%s - %d", ip, port);
	}

	public static String buildMessage(String sender, String message) {
		return String.format("%s" + messageSeparator + "%s", sender, message);
	}

	public ChatManager(MainScreen screen) {
		this.screen = screen;
		if (context.user.isConnected())
			this.launchChatServer();
	}

	public void launchChatServer() {
		Context.singleton.errorManager.info("Launching the chat...");
		this.chatClient = new ChatClient();
		this.chatServer = new ChatServer(this);
		chatServerThread = new Thread(chatServer);
		chatServerThread.start();
		Context.singleton.errorManager.info("Launched.");
	}

	public void stopChatServer() {
		Context.singleton.errorManager.info("Stopping the chat...");
		chatServer.stop();
		chatServerThread.interrupt();
		try {
			chatServerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Context.singleton.errorManager.info("Unacceptable error. Quitting the application.");
			System.exit(1);
		}
		Context.singleton.errorManager.info("Stopped.");
	}

	public int getRate() {
		return rate;
	}

	@Override
	public void run() {
		context.errorManager.info("The chat manager has started.");
		while (running) {
			try {
				Thread.sleep(rate);
				if (context.user.isConnected())
					addNewUsers();
			} catch (InterruptedException e) {
				context.setSilencedError(e);
				context.errorManager.info("The chat has been interrupted.");
				return;
			}

		}
	}

	private void addNewUsers() {
		UserList users = context.modelManager.getLoggedUsers();
		Set<String> loggedUsers = new HashSet<String>();
		Set<String> unloggedUsers = new HashSet<String>();

		unloggedUsers.addAll(loginToAdress.keySet());
		for (model.User user : users) {
			if (user.getClient() != null && user.getClient().isHeavy() && user.getLogin().equals(context.user.getLogin()) == false) {
				if (loginToAdress.containsKey(user.getLogin()) == false)
					addUser(user);
				loggedUsers.add(user.getLogin());
			}
		}
		unloggedUsers.removeAll(loggedUsers);
		for (String login : unloggedUsers) {
			removeUser(login);
		}
	}

	private void removeUser(String login) {
		String buttonToRemoveName = "speak_with_" + login;
		for (Component button : screen.allUsersTab.getComponents()) {
			if (button.getName().equals(buttonToRemoveName))
				screen.allUsersTab.remove(button);
		}
		adressToLogin.remove(loginToAdress.get(login));
		loginToAdress.remove(login);
	}

	private void addUser(model.User user) {
		JButton userButton = new JButton(user.getLogin());
		Client client = user.getClient();
		String clientIentifier = formatAdress(client.getIP(), client.getPort());

		loginToAdress.put(user.getLogin(), clientIentifier);
		adressToLogin.put(clientIentifier, user.getLogin());
		screen.allUsersTab.add(userButton);
		userButton.addActionListener(screen.actionListener);
		userButton.setName("speak_with_" + user.getLogin());
		userButton.setActionCommand("speak_with_" + user.getLogin());
	}

	public void end() {
		running = false;
	}

	public boolean sendMessageTo(String sender, String login, String message) {
		String adress;
		String ip;
		int port;
		if (loginToAdress.containsKey(login)) {
			adress = loginToAdress.get(login);
			ip = adress.split(" - ")[0];
			port = Integer.parseInt(adress.split(" - ")[1]);
			return chatClient.sendMessage(ip, port, ChatManager.buildMessage(sender, message));
		} else {
			return false;
		}
	}

}
