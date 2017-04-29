package screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import metadata.Context;

public class MainScreen extends Screen {

	private static final long serialVersionUID = 2643713053880588518L;
	JTabbedPane discussionPannel = null;
	JPanel documentPannel = null;
	ArrayList<JComponent> discussionPannelTabs = new ArrayList<JComponent>();
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

	public MainScreen() {
		super();
		this.actionListener = new MainScreenActionManager(this);
	}

	public void prepare() {
		prepareMenuBar();
		JSplitPane documentContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, prepareTextPanel(),
				prepareDiscussionPannel());
		add(documentContainer, BorderLayout.CENTER);
		prepareStatusBar();
		add(statusBar, BorderLayout.SOUTH);
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
			newProject.setToolTipText(Screen.LOGIN_FEATURE);
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
		if (context.project.isLoaded()) {
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
			newGroup.setToolTipText(Screen.LOGIN_FEATURE);
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
			manageGroup.setToolTipText(Screen.GROUP_FEATURE);
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
			deleteGroup.setToolTipText("Disolve oune of your groups");
			deleteGroup.setEnabled(true);
		} else {
			deleteGroup.setEnabled(false);
			deleteGroup.setToolTipText(Screen.GROUP_FEATURE);
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

}

/**
 * Manage the actions of the buttons.
 * 
 * @author Balthazar Pavot
 *
 */
class MainScreenActionManager implements ActionListener {

	protected Screen screen = null;

	public MainScreenActionManager(Screen screen) {
		this.screen = screen;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("exit")) {
			screen.nextScreenID = ScreenGenerator.QUIT_SCREEN;
			screen.screenTermination();
		} else if (action.equals("connect_user")) {
			new LoginForm().run();
		} else if (action.equals("disconnect_user")) {
			Context.singleton.user.logout();
		}
		((MainScreen) screen).contextUpdatePropagation();
	}

}
