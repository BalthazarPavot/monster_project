package screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import metadata.Context;
import model.Permission;
import network.HTTPResponse;

public class ProjectConfForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4379236881467436012L;
	private Context context;
	private MainScreen parentScreen;
	private JFrame parent;
	JCheckBox ownerWriteNameField = null;
	JCheckBox ownerReadNameField = null;

	public ProjectConfForm(MainScreen screen) {
		super(Screen.mainFrame, "Project Conf.", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JLabel ownerNameLabel = new JLabel("Owner Write: ");
		JLabel documentNameLabel = new JLabel("Owner Read: ");
		ownerWriteNameField = new JCheckBox();
		ownerReadNameField = new JCheckBox();

		setBounds(0, 0, 300, 125);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		ownerNameLabel.setLabelFor(ownerWriteNameField);
		documentNameLabel.setLabelFor(ownerReadNameField);

		panel.add(ownerNameLabel);
		panel.add(ownerWriteNameField);
		panel.add(documentNameLabel);
		panel.add(ownerReadNameField);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			openProject();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	private void openProject() {
		HTTPResponse response;
		model.Document mappedProject = null;
		model.Permission perms = new Permission() ;
		perms.setUserWrite(ownerWriteNameField.isSelected());
		perms.setUserRead(ownerReadNameField.isSelected());
		response = context.client.sendServerConfProjectRequest(perms);
		if (response.getErrorCode() == 200) {
			mappedProject = context.modelManager.mapProject(response.getContent());
			context.project.loadProject(mappedProject);
			parentScreen.loadProject();
			dispose();
		} else {
			JOptionPane.showMessageDialog(parent, "Invalid password. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
