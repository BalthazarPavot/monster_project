package screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import metadata.Context;
import model.User;
import network.HTTPResponse;

public class LoginForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = 5934372618920115955L;
	private Context context;
	private JPasswordField passwordField;
	private JTextField loginField;
	private String login;
	private String password;
	private JFrame parent;
	private MainScreen parentScreen;

	public LoginForm(MainScreen screen) {
		super(Screen.mainFrame, "Login", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel loginLabel = new JLabel("Login: ");
		loginField = new JTextField(10);
		passwordField = new JPasswordField(10);

		setBounds(0, 0, 300, 125);
		loginField.setActionCommand("OK");
		loginField.addActionListener(this);
		passwordField.setActionCommand("OK");
		passwordField.addActionListener(this);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		passwordLabel.setLabelFor(passwordField);
		loginLabel.setLabelFor(loginField);

		panel.add(loginLabel);
		panel.add(loginField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			login();
			passwordField.selectAll();
			passwordField.requestFocusInWindow();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	private void login() {
		HTTPResponse response;
		User mappedUser = null;
		login = new String(loginField.getText());
		password = new String(passwordField.getPassword());
		response = context.client.sendServerLoginRequest(login, password);
		if (response.getErrorCode() == 200) {
			mappedUser = context.modelManager.mapUser(response.getContent());
			context.user.setConnected(mappedUser);
			parentScreen.chatManager.launchChatServer();
			dispose();
		} else {
			JOptionPane.showMessageDialog(parent, "Invalid password. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}