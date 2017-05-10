package screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import metadata.Context;

public class MainScreen extends JPanel {


	public static final String LOGIN_FEATURE = "You must be logged to use this feature. Use our website or press 'alt-R' to create an account.";

	public static final String GROUP_FEATURE = "You must be into a group to use this feature";

	protected static JFrame mainFrame = null;

	protected Context context = null;
	protected boolean screenHasFinished = false;
	protected ActionListener actionListener = null;
	protected ArrayList<Component> widgetList = new ArrayList<Component>();
	private static final long serialVersionUID = 2643713053880588518L;
	JTabbedPane discussionPannel = null;
	JPanel documentPannel = null;
	StringBuilder documentText = null;
	JTextArea documentTextArea = null;
	HashMap<String, JPanel> discussionPannelTabs = new HashMap<String, JPanel>();
	HashMap<String, JComponent> statusBarComponents = new HashMap<>();
	JPanel allUsersTab = new JPanel(false);
	JPanel statusBar = new JPanel(new GridLayout());
	JMenuItem newProject = null;
	JMenuItem openProject = null;
	JMenuItem projectRights = null;
	JMenuItem exit = null;
	JMenuItem connect = null;
	JMenuItem disconnect = null;
	JMenuItem createUser = null;
	JMenuItem newGroup = null;
	JMenuItem manageGroup = null;
	JMenuItem deleteGroup = null;
	public ChatManager chatManager = null;
	public Thread chatManagerThread = null;
	private HashMap<String, JTextArea> discussionPannelText = new HashMap<String, JTextArea>();

	public MainScreen() {
		super();
		context = Context.singleton;
		if (MainScreen.mainFrame == null) {
			MainScreen.mainFrame = new JFrame();
		}
		this.initScreen();
		this.actionListener = new MainScreenActionManager(this);
	}

	/**
	 * Initialize the screen with a new frame, a layout and trigger the first
	 * display.
	 */
	protected void initScreen() {
		setLayout(new BorderLayout());
		MainScreen.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainScreen.mainFrame.getContentPane().add(this);
		MainScreen.mainFrame.setBounds(0, 0, context.getDimension().width, context.getDimension().height);
		this.screenHasFinished = false;
	}

	/**
	 * Make the screen to run.
	 * 
	 * @return The id of the next game screen.
	 */
	public void run() throws IllegalStateException {
		if (actionListener == null)
			throw new IllegalStateException();
		MainScreen.mainFrame.pack();
		MainScreen.mainFrame.setVisible(true);
		while (!this.screenHasFinished) {
			this.repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				this.screenTermination();
			}
		}
		screenTermination();
	}

	public void screenTermination() {
		for (Component widget : widgetList)
			this.remove(widget);
		widgetList = new ArrayList<>();
		this.removeAll();
		mainFrame.remove(this);
		screenHasFinished = true;
	}


	public void prepare() {
		prepareMenuBar();
		JSplitPane documentContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, prepareTextPanel(),
				prepareDiscussionPannel());
		add(documentContainer, BorderLayout.CENTER);
		prepareStatusBar();
		add(statusBar, BorderLayout.SOUTH);
		chatManager = new ChatManager(this);
		chatManagerThread = new Thread(chatManager);
		chatManagerThread.start();
	}

	private JTabbedPane prepareDiscussionPannel() {
		Dimension discussionPannelDimensions = new Dimension();
		discussionPannel = new JTabbedPane();

		discussionPannelDimensions.setSize(context.getDimension().getWidth() / 4, context.getDimension().getHeight());
		discussionPannel.setMinimumSize(discussionPannelDimensions);
		discussionPannel.addTab("Connected users", allUsersTab);
		return discussionPannel;
	}

	private JPanel prepareTextPanel() {
		Dimension documentPannelDimensions = new Dimension();
		documentPannel = new JPanel();

		documentPannelDimensions.setSize(context.getDimension().getWidth() / 4 * 3, context.getDimension().getHeight());
		documentPannel.setPreferredSize(documentPannelDimensions);
		return documentPannel;
	}

	private void prepareMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		prepareMenuBarFile(menuBar);
		add(menuBar, BorderLayout.NORTH);

	}

	private void prepareMenuBarFile(JMenuBar menuBar) {
		JMenu file = new JMenu("Projects");
		JMenu user = new JMenu("User");
		JMenu group = new JMenu("Group");
		file.setMnemonic(KeyEvent.VK_P);
		user.setMnemonic(KeyEvent.VK_U);
		group.setMnemonic(KeyEvent.VK_G);
		prepareMenuBarFileItem(file);
		prepareMenuBarUserItem(user);
		prepareMenuBarGroupItem(group);
		menuBar.add(file);
		menuBar.add(user);
		menuBar.add(group);
	}

	private void prepareMenuBarFileItem(JMenu file) {
		buildNewProjectItem(file);
		buildOpenProjectItem(file);
		buildProjectRightsItem(file);
		buildExitItem(file);
	}

	private void buildNewProjectItem(JMenu file) {
		newProject = new JMenuItem("New project", KeyEvent.VK_N);
		setNewProjectItemProperties();
		newProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		newProject.setActionCommand("create_project");
		newProject.addActionListener(actionListener);
		file.add(newProject);
	}

	private void setNewProjectItemProperties() {
		if (context.user.isConnected()) {
			newProject.setToolTipText("Create a new document");
			newProject.setEnabled(true);
		} else {
			newProject.setEnabled(false);
			newProject.setToolTipText(MainScreen.LOGIN_FEATURE);
		}
	}

	private void buildOpenProjectItem(JMenu file) {
		openProject = new JMenuItem("Open project", KeyEvent.VK_O);
		openProject.getAccessibleContext().setAccessibleDescription("Open an existing document");
		openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		openProject.setActionCommand("open_project");
		openProject.addActionListener(actionListener);
		file.add(openProject);
	}

	private void buildProjectRightsItem(JMenu file) {
		projectRights = new JMenuItem("Project Config.", KeyEvent.VK_C);
		setProjectRightsItemProperties();
		projectRights.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		projectRights.setActionCommand("project_conf");
		projectRights.addActionListener(actionListener);
		file.add(projectRights);
	}

	public void setProjectRightsItemProperties() {
		if (context.document.isLoaded()) {
			projectRights.setToolTipText("Configure project's r/w rights");
			projectRights.setEnabled(true);
		} else {
			projectRights.setEnabled(false);
			projectRights.setToolTipText("No project loaded yet");
		}
	}

	private void buildExitItem(JMenu file) {
		exit = new JMenuItem("Exit", KeyEvent.VK_X);
		exit.getAccessibleContext().setAccessibleDescription("Exit the application");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		exit.addActionListener(actionListener);
		exit.setActionCommand("exit");
		file.add(exit);
	}

	private void prepareMenuBarUserItem(JMenu user) {
		buildConnectUserItem(user);
		buildDisconnectUserItem(user);
		buildCreateUserItem(user);
	}

	private void buildConnectUserItem(JMenu user) {
		connect = new JMenuItem("Connect", KeyEvent.VK_C);
		setConnectUserItemProperties();
		connect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		connect.setActionCommand("connect_user");
		connect.addActionListener(actionListener);
		user.add(connect);
	}

	private void setConnectUserItemProperties() {
		if (context.user.isConnected()) {
			connect.setEnabled(false);
			connect.setToolTipText("You're already connected");
		} else {
			connect.setToolTipText("Connect to your account");
			connect.setEnabled(true);
		}
	}

	private void buildDisconnectUserItem(JMenu user) {
		disconnect = new JMenuItem("Disconnect", KeyEvent.VK_D);
		setDisconnectUserItemProperties();
		disconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
		disconnect.setActionCommand("disconnect_user");
		disconnect.addActionListener(actionListener);
		user.add(disconnect);
	}

	private void setDisconnectUserItemProperties() {
		if (context.user.isConnected()) {
			disconnect.setToolTipText("Disconnects you");
			disconnect.setEnabled(true);
		} else {
			disconnect.setEnabled(false);
			disconnect.setToolTipText("You're not connected");
		}
	}

	private void buildCreateUserItem(JMenu user) {
		createUser = new JMenuItem("New User", KeyEvent.VK_R);
		createUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		createUser.setActionCommand("register_user");
		createUser.addActionListener(actionListener);
		createUser.setToolTipText("Create an account");
		user.add(createUser);
	}

	private void prepareMenuBarGroupItem(JMenu group) {
		buildNewGroupItem(group);
		buildManageGroupItem(group);
		buildDeleteGroupItem(group);
	}

	private void buildNewGroupItem(JMenu group) {
		newGroup = new JMenuItem("New Group", KeyEvent.VK_N);
		setNewGroupItemProperties();
		newGroup.setActionCommand("new_group");
		newGroup.addActionListener(actionListener);
		group.add(newGroup);
	}

	private void setNewGroupItemProperties() {
		if (context.user.isConnected()) {
			newGroup.setToolTipText("Create a new group");
			newGroup.setEnabled(true);
		} else {
			newGroup.setEnabled(false);
			newGroup.setToolTipText(MainScreen.LOGIN_FEATURE);
		}
	}

	private void buildManageGroupItem(JMenu group) {
		manageGroup = new JMenuItem("Manage Group", KeyEvent.VK_M);
		setManageGroupItemProperties();
		manageGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
		manageGroup.setActionCommand("manage_group");
		manageGroup.addActionListener(actionListener);
		group.add(manageGroup);
	}

	private void setManageGroupItemProperties() {
		if (context.user.isConnected()) {
			manageGroup.setToolTipText("Add users to this group");
			manageGroup.setEnabled(true);
		} else {
			manageGroup.setEnabled(false);
			manageGroup.setToolTipText(MainScreen.GROUP_FEATURE);
		}
	}

	private void buildDeleteGroupItem(JMenu group) {
		deleteGroup = new JMenuItem("Delete Group", KeyEvent.VK_D);
		setDeleteGroupItemProperties();
		deleteGroup.setActionCommand("delete_group");
		deleteGroup.addActionListener(actionListener);
		group.add(deleteGroup);
	}

	private void setDeleteGroupItemProperties() {
		if (context.user.isConnected()) {
			deleteGroup.setToolTipText("Disolve one of your groups");
			deleteGroup.setEnabled(true);
		} else {
			deleteGroup.setEnabled(false);
			deleteGroup.setToolTipText(MainScreen.GROUP_FEATURE);
		}
	}

	private void prepareStatusBar() {
		JLabel connectedLabel = new JLabel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBarComponents.put("connected", connectedLabel);
		statusBar.add(connectedLabel, BorderLayout.CENTER);
		updateStatusBar();
	}

	private void updateStatusBar() {
		((JLabel) statusBarComponents.get("connected")).setText(context.user.isConnected()
				? String.format("Connected as %s", context.user.getLogin()) : "Not Connected");
	}

	public void contextUpdatePropagation() {
		setNewProjectItemProperties();
		setProjectRightsItemProperties();
		setConnectUserItemProperties();
		setDisconnectUserItemProperties();
		setNewGroupItemProperties();
		setManageGroupItemProperties();
		setDeleteGroupItemProperties();
		updateStatusBar();
	}

	public void addDiscussionTab(String userName) {
		JPanel discussionPanel = new JPanel();
		JTextArea wholeTextArea = new JTextArea();
		JTextArea textArea = new JTextArea();
		if (hasDiscussionTab(userName) == false) {
			discussionPannel.addTab(userName, discussionPanel);
			discussionPannelTabs.put(userName, discussionPanel);
			discussionPannelText.put(userName, wholeTextArea);
			discussionPanel.setLayout(new BorderLayout());
			discussionPanel.add(textArea, BorderLayout.SOUTH);
			discussionPanel.add(new JScrollPane(wholeTextArea), BorderLayout.CENTER);
			wholeTextArea.setEditable(false);
			wholeTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
			wholeTextArea.setWrapStyleWord(true);
			wholeTextArea.setLineWrap(true);
			textArea.setFont(new Font("Serif", Font.PLAIN, 15));
			textArea.addKeyListener(new TextAreaListener(wholeTextArea, textArea, this, userName));
		}
	}

	public boolean hasDiscussionTab(String userName) {
		return discussionPannelTabs.containsKey(userName);
	}

	public void addMessageToDiscussionTab(String tabName, String sender, String message) {
		if (discussionPannelText.containsKey(tabName))
			discussionPannelText.get(tabName).append(String.format("<%s>: %s\n", sender, message));
		else
			System.err.println("Unknown tab: " + tabName);
	}

	public void loadProject() {
		for (Component component: documentPannel.getComponents()) {
			documentPannel.remove(component) ;
		}
		if (context.document.isLoaded()) {
			documentText = new StringBuilder(context.document.getContent()) ;
			documentTextArea = new JTextArea() ;
			documentTextArea.setEditable(true);
			documentTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
			documentTextArea.setWrapStyleWord(true);
			documentTextArea.setLineWrap(true);
			documentPannel.add(documentTextArea);
			documentTextArea.setText(documentText.toString());
		}
	}

}

/**
 * Manage the actions of the buttons.
 * 
 * @author Balthazar Pavot
 *
 */
class MainScreenActionManager implements ActionListener {

	protected MainScreen screen = null;

	public MainScreenActionManager(MainScreen screen) {
		this.screen = screen;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("exit")) {
			screen.chatManager.end();
			while (screen.chatManagerThread.isAlive()) {
				try {
					screen.chatManagerThread.join(screen.chatManager.getRate());
				} catch (InterruptedException e1) {
					Context.singleton.setSilencedError(e1);
				}
				try {
					Thread.sleep(screen.chatManager.getRate());
				} catch (InterruptedException e2) {
					Context.singleton.setSilencedError(e2);
				}
			}
			screen.screenTermination();
		} else if (action.equals("connect_user")) {
			new LoginForm(screen).run();
		} else if (action.equals("register_user")) {
			new RegisterForm(screen).run();
		} else if (action.equals("disconnect_user")) {
			Context.singleton.user.logout();
			screen.chatManager.stopChatServer();
		} else if (action.matches("^speak_with_.*$")) {
			screen.addDiscussionTab(action.substring(11, action.length()));
		} else if (action.equals("create_project")) {
			new ProjectCreationForm(screen).run();
		} else if (action.equals("open_project")) {
			new ProjectOpeningForm(screen).run();
		} else if (action.equals("project_conf")) {
			new ProjectConfForm(screen).run();
		} else if (action.equals("new_group")) {
			new GroupCreationForm(screen).run();
		} else if (action.equals("manage_group")) {
			new GroupManagingForm(screen).run();
		} else if (action.equals("delete_group")) {
			new GroupDeletionForm(screen).run(new String[]{"test", "test2", "test3"});
		}
		((MainScreen) screen).contextUpdatePropagation();
	}

}

class TextAreaListener implements KeyListener {

	private JTextArea area;
	private JTextArea input;
	private MainScreen screen;
	private String target;

	TextAreaListener(JTextArea area, JTextArea input, MainScreen scren, String target) {
		this.area = area;
		this.input = input;
		this.screen = scren;
		this.target = target;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			String login = Context.singleton.user.getLogin();
			String message = input.getText();
			if (screen.chatManager.sendMessageTo(login, target, message)) {
				((MainScreen) screen).addMessageToDiscussionTab(target, login, message);
			} else {
				area.append(target + " is not connected anymore.\n");
			}
			input.setText("");
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}
