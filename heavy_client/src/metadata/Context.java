package metadata;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import model.Document;
import model.User;
import network.Client;

/**
 * This class contains the global context in which the whole program is
 * executed. It also contains the configuration and the parameters.
 * 
 * @author Balthazar Pavot
 *
 */
public class Context {

	static public Context singleton = new Context();

	final private static String TEST_SERVER_NAME = "simulateServer.py";
	final private static String TEST_SERVER_DYIRECTORY = "test";
	private String serverIP = "127.0.0.1";
	private Integer serverPort = 8520;
	private String serverAdress = String.format("%s:%d", serverIP, serverPort);
	private String clientIP = "127.0.0.1";
	private Integer clientPort = 8521;
	private String clientAdress = String.format("%s:%d", clientIP, clientPort);
	private Boolean confLoaded = false;
	private Dimension screenDimensions = new Dimension(640, 480);
	private String defaultConfigPath = "/config.conf";

	public User user = new User();
	public Document document = new Document();
	public Client client = new Client();
	final public ErrorManager errorManager = ErrorManager.singleton;
	public ModelManager modelManager = null;
	private Process serverProcess = null;

	/**
	 * 
	 */
	private Context() {
		modelManager = new ModelManager();
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

	/**
	 * Set the current error.
	 * 
	 * @param error
	 */
	public void setError(Exception error) {
		try {
			setError(error, false);
		} catch (Exception e) {
			// should never ocure
			e.printStackTrace();
			System.out.println("An error occurend in the error handler!");
		}
	}

	/**
	 * Set the curent error and warns the user it has been silenced.
	 * 
	 * @param error
	 */
	public void setSilencedError(Exception error) {
		errorManager.setError(error);
		errorManager.silenceError();
	}

	/**
	 * Set the curent error and throws the exception if "raise" parameter's
	 * value is true
	 * 
	 * @param error
	 * @param raise
	 * @throws Exception
	 */
	public void setError(Exception error, Boolean raise) throws Exception {
		this.errorManager.setError(error);
		if (raise) {
			errorManager.throwError();
		} else
			errorManager.silenceError();
	}

	/**
	 * Loads the configuration using the command line inputs.
	 * 
	 * @param args
	 */
	public void loadConfiguration(String[] args) {
		HashMap<String, String> options = null;

		if (args.length > 0) {
			options = parseArgs(args);
			if (options.containsKey("config")) {
				confLoaded = false;
				loadConf(options.get("config"), true);
			}
			loadOptions(options);
		}
	}

	/**
	 * 
	 * Parses the command line. Store options in a hash. Inspirated from
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

	/**
	 * Executes the functions triggered by the options and apply the other
	 * options.
	 * 
	 * @param options
	 */
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
			else if (option.equals("run_test_server")) {
				runTestServer(value);
			} else if (option.equals("config")) {
				errorManager.info("Ignoring already loaded option: %s=%s", option, value);
				continue;
			} else
				throw new IllegalArgumentException("Unknown option: " + option);
			errorManager.info("Loaded option: %s=%s", option, value);
		}
	}

	/**
	 * Runs the python server (simulateServer.py). Redirects its outputs to this
	 * program's outputs.
	 * (called if --run_test_server is given)
	 * 
	 * @param option
	 */
	private void runTestServer(String option) {
		ProcessBuilder pb = null;
		errorManager.info("Launching the test server");
		pb = new ProcessBuilder("python", TEST_SERVER_NAME);
		if (option.equals("true"))
			pb.directory(new File(TEST_SERVER_DYIRECTORY));
		else
			pb.directory(new File(option));
		try {
			serverProcess = pb.start();
			inheritIO(serverProcess.getInputStream(), System.err);
			inheritIO(serverProcess.getErrorStream(), System.err);
			if (serverPort == null)
				errorManager.info("Not launched...");
			else
				errorManager.info("Launched.");
			serverProcess.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			errorManager.setError(e);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Dimension getDimension() {
		return screenDimensions;
	}

	/**
	 * 
	 * @return
	 */
	public String getServerAdress() {
		return serverAdress;
	}

    /**
     * 
     * @return
     */
	public String getServerIP() {
		return serverIP;
	}

    /**
     * 
     * @param serverIP
     */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
		serverAdress = String.format("%s:%d", serverIP, serverPort);
	}

    /**
     * 
     * @return
     */
	public Integer getServerPort() {
		return serverPort;
	}

    /**
     * 
     * @param serverPort
     */
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
		serverAdress = String.format("%s:%d", serverIP, serverPort);
	}

    /**
     * 
     * @return
     */
	public String getClientAdress() {
		return clientAdress;
	}

    /**
     * 
     * @return
     */
	public String getClientIP() {
		return clientIP;
	}

    /**
     * 
     * @param clientIP
     */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
		clientAdress = String.format("%s:%d", clientIP, clientPort);
	}

    /**
     * 
     * @return
     */
	public Integer getClientPort() {
		return clientPort;
	}

    /**
     * 
     * @param clientPort
     */
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

    /**
     * Destroy the python server if it has been launched.
     */
	public void finish() {
		if (serverProcess != null) {
			errorManager.info("Destroying test server...");
			serverProcess.destroy();
			errorManager.info("Destroyed.");
		}
	}

	/**
	 * Make a pipe from src to dest (taken from stackoerflow)
	 * 
	 * @param src
	 * @param dest
	 */
	private static void inheritIO(final InputStream src, final PrintStream dest) {
		new Thread(new Runnable() {
			public void run() {
				Scanner sc = new Scanner(src);
				while (sc.hasNextLine()) {
					dest.println(sc.nextLine());
				}
				sc.close () ; // never reached
			}
		}).start();
	}
}
