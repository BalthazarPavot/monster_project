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

public class RegisterForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = 5934372618920115955L;
	private Context context;
	private JPasswordField passwordField;
	private JPasswordField verifPasswordField;
	private JTextField loginField;
	private JTextField emailField;
	private String login;
	private String password;
	private String verifPassword;
	private String email;
	private JFrame parent;
	private MainScreen parentScreen;

	public RegisterForm(MainScreen screen) {
		super(Screen.mainFrame, "Register", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(5, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JLabel emailLabel = new JLabel("Email: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel verifPasswordLabel = new JLabel("Password (verif.): ");
		JLabel loginLabel = new JLabel("Login: ");
		loginField = new JTextField(10);
		passwordField = new JPasswordField(10);
		verifPasswordField = new JPasswordField(10);
		emailField = new JTextField(10);

		setBounds(0, 0, 300, 175);
		loginField.setActionCommand("OK");
		loginField.addActionListener(this);
		passwordField.setActionCommand("OK");
		passwordField.addActionListener(this);
		verifPasswordField.setActionCommand("OK");
		verifPasswordField.addActionListener(this);
		emailField.setActionCommand("OK");
		emailField.addActionListener(this);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		emailLabel.setLabelFor(emailField);
		verifPasswordLabel.setLabelFor(verifPasswordField);
		passwordLabel.setLabelFor(passwordField);
		loginLabel.setLabelFor(loginField);

		panel.add(loginLabel);
		panel.add(loginField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(verifPasswordLabel);
		panel.add(verifPasswordField);
		panel.add(emailLabel);
		panel.add(emailField);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			register();
			passwordField.selectAll();
			passwordField.requestFocusInWindow();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	private void register() {
		login = new String(loginField.getText());
		password = new String(passwordField.getPassword());
		verifPassword = new String(verifPasswordField.getPassword());
		email = new String(emailField.getText());
		if (password.equals(verifPassword) == false)
			JOptionPane.showMessageDialog(parent, "Password does not match", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		else {
			if (context.client.sendServerRegisterRequest(login, password, email).getErrorCode() == 200) {
				context.user.setConnected(login);
				parentScreen.chatManager.launchChatServer();
				dispose();
			} else {
				JOptionPane.showMessageDialog(parent, "Unexpected error occured", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}