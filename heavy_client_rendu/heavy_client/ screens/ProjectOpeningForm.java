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
import model.Document;
import network.HTTPResponse;

/**
 * A project opening window.
 * @author Balthazar Pavot
 *
 */
public class ProjectOpeningForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = 3129456875961270839L;
	private Context context;
	private MainScreen parentScreen;
	private JFrame parent;
	private JTextField ownerNameField;
	private JTextField documentNameField;
	private String ownerName;
	private String documentName;

	public ProjectOpeningForm(MainScreen screen) {
		super(MainScreen.mainFrame, "Open Project", true);
		this.parent = MainScreen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JLabel ownerNameLabel = new JLabel("Owner Name: ");
		JLabel documentNameLabel = new JLabel("Project Name: ");
		ownerNameField = new JTextField(10);
		documentNameField = new JTextField(10);

		setBounds(0, 0, 300, 125);
		ownerNameField.setActionCommand("OK");
		ownerNameField.addActionListener(this);
		documentNameField.setActionCommand("OK");
		documentNameField.addActionListener(this);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		ownerNameLabel.setLabelFor(ownerNameField);
		documentNameLabel.setLabelFor(documentNameField);

		panel.add(ownerNameLabel);
		panel.add(ownerNameField);
		panel.add(documentNameLabel);
		panel.add(documentNameField);
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
		ownerName = new String(ownerNameField.getText());
		documentName = new String(documentNameField.getText());
		response = context.client.sendServerOpenProjectRequest(ownerName, documentName);
		if (response.getErrorCode() == 200) {
			context.document = context.modelManager.mapProject(response.getContent());
			if (context.document == null)
				context.document = new Document();
			else {
				context.document.setLoaded();
				parentScreen.loadProject();
			}
			dispose();
		} else {
			JOptionPane.showMessageDialog(parent, "Invalid password. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
