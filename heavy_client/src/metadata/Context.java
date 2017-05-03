package metadata;

import java.awt.Dimension;

import network.Client;
import user.Project;
import user.User;

public class Context {

	public String server_ip = "127.0.0.1";
	public Integer server_port = 8520;
	public String server_adress = String.format ("%s:%d", server_ip, server_port);

	static public Context singleton = new Context();

	private ErrorManager errorManager = null;
	private Boolean confLoaded = false;
	private boolean running = true;
	private Dimension screenDimensions = new Dimension(640, 480);
	private String defaultConfigPath = "/config.conf";

	public User user = new User();
	public Project project = new Project();

	public Client client = new Client();

	private Context() {
		this(null);
	}

	private Context(ErrorManager errorManager) {
		client = new Client();
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
