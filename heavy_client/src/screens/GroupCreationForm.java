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
import javax.swing.JTextField;

import metadata.Context;
import network.HTTPResponse;

public class GroupCreationForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4138179479410716458L;
	private Context context;
	private MainScreen parentScreen;
	private JFrame parent;
	JTextField nameField = null;
	String groupName = null ;

	public GroupCreationForm(MainScreen screen) {
		super(Screen.mainFrame, "New Group", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JLabel ownerNameLabel = new JLabel("Group Name: ");
		nameField = new JTextField(10);

		setBounds(0, 0, 300, 125);
		nameField.setActionCommand("OK");
		nameField.addActionListener(this);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		ownerNameLabel.setLabelFor(nameField);

		panel.add(ownerNameLabel);
		panel.add(nameField);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			createGroup();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	private void createGroup() {
		HTTPResponse response;
		groupName = new String(nameField.getText());
		response = context.client.sendServerNewGroupRequest(groupName);
		if (response.getErrorCode() == 200) {
			dispose();
		} else {
			JOptionPane.showMessageDialog(parent, "Invalid password. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
