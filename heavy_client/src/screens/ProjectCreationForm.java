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

public class ProjectCreationForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7801960644128920516L;
	private Context context;
	private MainScreen parentScreen;
	private JFrame parent;
	private JTextField nameField;
	private String name;

	public ProjectCreationForm(MainScreen screen) {
		super(Screen.mainFrame, "New Project", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JLabel nameLabel = new JLabel("Project Name: ");
		nameField = new JTextField(10);

		setBounds(0, 0, 300, 95);
		nameField.setActionCommand("OK");
		nameField.addActionListener(this);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		nameLabel.setLabelFor(nameField);

		panel.add(nameLabel);
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
			createProject();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	private void createProject() {
		HTTPResponse response;
		model.Document mappedProject = null;
		name = new String(nameField.getText());
		response = context.client.sendServerCreateProjectRequest(name);
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
