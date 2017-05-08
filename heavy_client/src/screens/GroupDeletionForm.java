package screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import metadata.Context;
import network.HTTPResponse;

public class GroupDeletionForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -790662379848118627L;
	private Context context;
	private MainScreen parentScreen;
	private JFrame parent;

	public GroupDeletionForm(MainScreen screen) {
		super(Screen.mainFrame, "Login", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	public void run() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");

		setBounds(0, 0, 300, 125);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");


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
		response = new HTTPResponse();
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
