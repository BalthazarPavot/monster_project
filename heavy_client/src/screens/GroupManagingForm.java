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

/**
 * A group management window
 * 
 * @author Balthazar Pavot
 *
 */
public class GroupManagingForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7162886282230894063L;
	private Context context;
	private MainScreen parentScreen;
	private JFrame parent;

	public GroupManagingForm(MainScreen screen) {
		super(MainScreen.mainFrame, "Login", true);
		this.parent = MainScreen.mainFrame;
		context = Context.singleton;
		parentScreen = screen;
	}

	/**
	 * Run the form to open a project.
	 */
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

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			openProject();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	/**
	 * Opens the asked project
	 */
	private void openProject() {
		HTTPResponse response;
		response = new HTTPResponse();
		if (response.getErrorCode() == 200) {
			context.document = context.modelManager.mapProject(response.getContent());
			context.document.setLoaded();
			parentScreen.loadProject();
			dispose();
		} else {
			JOptionPane.showMessageDialog(parent, "Invalid password. Try again.", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
