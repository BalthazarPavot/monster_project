package metadata;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import model.Document;
import model.User;
import network.Client;

public class Context {

	static public Context singleton = new Context();

	private String serverIP = "127.0.0.1";
	private Integer serverPort = 8520;
	private String serverAdress = String.format("%s:%d", serverIP, serverPort);
	private String clientIP = "127.0.0.1";
	private Integer clientPort = 8521;
	private String clientAdress = String.format("%s:%d", clientIP, clientPort);
	private Boolean confLoaded = false;
	private boolean running = true;
	private Dimension screenDimensions = new Dimension(640, 480);
	private String defaultConfigPath = "/config.conf";

	public User user = new User();
	public Document document = new Document();
	public Client client = new Client();
	final public ErrorManager errorManager = ErrorManager.singleton;
	public ModelManager modelManager = null ;

	private Context() {
		modelManager = new ModelManager () ;
		loadConf();
	}

	/**
	 * Load the default configuration file.
	 */
	public void loadConf() {
		loadConf(defaultConfigPath);
	}

	/**
	 * Load the default configuration file.
	 */
	public void loadConf(String confFilePath) {
		loadConf(confFilePath, false);
	}

	public void loadConf(String confFilePath, boolean extarnal) {
		File confFile = null;

		if (confLoaded == false) {
			confFile = new File(confFilePath);
			try {
				loadConf(confFile, extarnal);
			} catch (IOException | IllegalArgumentException e) {
			}
			confFile = null;
		}
	}

	public void setError(Exception error) {
		try {
			setError(error, false);
		} catch (Exception e) {
			// should never ocure
			e.printStackTrace();
			System.out.println("An error occurend in the error handler!");
		}
	}

	public void setSilencedError(Exception error) {
		errorManager.setError(error);
		errorManager.silenceError();
	}

	public void setError(Exception error, Boolean raise) throws Exception {
		this.errorManager.setError(error);
		if (raise) {
			setRunning(false);
			errorManager.throwError();
		} else
			errorManager.silenceError();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	public void loadConfiguration(String[] args) {
		HashMap<String, String> options = null;

		if (args.length > 1) {
			options = parseArgs(args);
			if (options.containsKey("config")) {
				confLoaded = false;
				loadConf(options.get("config"), true);
			}
			loadOptions(options);
		}
	}

	/**
	 * Inspirated from
	 * https://stackoverflow.com/questions/7341683/parsing-arguments-to-a-java-command-line-program
	 * 
	 * @param args
	 */
	private HashMap<String, String> parseArgs(String[] args) {
		HashMap<String, String> options = new HashMap<>();

		for (int i = 0; i < args.length; i++) {
			switch (args[i].charAt(0)) {
			case '-':
				if (args[i].length() < 2)
					throw new IllegalArgumentException("Not a valid argument: " + args[i]);
				if (args[i].charAt(1) == '-') {
					if (args[i].length() < 3)
						throw new IllegalArgumentException("Not a valid argument: " + args[i]);
					options.put(args[i].substring(2, args[i].length()), "true");
				} else {
					if (args.length - 1 == i)
						throw new IllegalArgumentException("Expected arg after: " + args[i]);
					options.put(args[i].substring(1, args[i].length()), args[i + 1]);
					i += 1;
				}
				break;
			default:
				break;
			}
		}
		return options;
	}

	private void loadOptions(HashMap<String, String> options) {
		String value = null;
		for (String option : options.keySet()) {
			value = options.get(option);
			if (option.equals("server_port"))
				setServerPort(Integer.parseInt(value));
			else if (option.equals("client_port"))
				setClientPort(Integer.parseInt(value));
			else if (option.equals("server_ip"))
				setServerIP(value);
			else if (option.equals("client_ip"))
				setClientIP(value);
			else if (option.equals("config")) {
				errorManager.info("Ignoring already loaded option: %s=%s", option, value);
				continue;
			} else
				throw new IllegalArgumentException("Unknown option: " + option);
			errorManager.info("Loaded option: %s=%s", option, value);
		}
	}

	public Dimension getDimension() {
		return screenDimensions;
	}

	public String getServerAdress() {
		return serverAdress;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
		serverAdress = String.format("%s:%d", serverIP, serverPort);
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
		serverAdress = String.format("%s:%d", serverIP, serverPort);
	}

	public String getClientAdress() {
		return clientAdress;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
		clientAdress = String.format("%s:%d", clientIP, clientPort);
	}

	public Integer getClientPort() {
		return clientPort;
	}

	public void setClientPort(Integer clientPort) {
		this.clientPort = clientPort;
		clientAdress = String.format("%s:%d", clientIP, clientPort);
	}

	/**
	 * Open the configuration file and load the configuration.
	 * 
	 * @param confFile
	 */
	private void loadConf(File confFile, boolean external) throws IOException, IllegalArgumentException {
		Properties confProperties = null;

		confProperties = new Properties();
		errorManager.info("Loading configuration file at %s", confFile.getAbsolutePath());
		if (external)
			confProperties.load(new FileReader(confFile));
		else
			confProperties.load(this.getClass().getResourceAsStream(confFile.getAbsolutePath()));
		loadConf(confProperties);
		confProperties = null;
		confLoaded = true;
	}

	/**
	 * Read the properties of the configuration file and extract the
	 * configuration.
	 * 
	 * @param confProperties
	 */
	private void loadConf(Properties confProperties) throws IOException, IllegalArgumentException {
		Integer clientPort = null;
		Integer serverPort = null;
		String clientIP = confProperties.getProperty("client_ip");
		String serverIP = confProperties.getProperty("server_ip");
		try {
			if (confProperties.getProperty("client_port") != null)
				clientPort = Integer.parseInt(confProperties.getProperty("client_port"));
		} catch (NumberFormatException e) {
			errorManager.setSilentError(e);
		}
		try {
			if (confProperties.getProperty("server_port") != null)
				serverPort = Integer.parseInt(confProperties.getProperty("server_port"));
		} catch (NumberFormatException e) {
			errorManager.setSilentError(e);
		}
		if (clientPort != null) {

			setClientPort(clientPort);
		}
		if (serverPort != null)
			setServerPort(serverPort);
		if (clientIP != null)
			setClientIP(clientIP);
		if (serverIP != null)
			setServerIP(serverIP);
	}

}
