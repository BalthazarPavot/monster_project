package screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import metadata.Context;
import network.HTTPResponse;

public class GroupDeletionForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -790662379848118627L;
	private Context context;
	private JFrame parent;
	private ArrayList<JCheckBox> boxes = new ArrayList<>();

	public GroupDeletionForm(MainScreen screen) {
		super(Screen.mainFrame, "Group deletion", true);
		this.parent = Screen.mainFrame;
		context = Context.singleton;
	}

	public void run(String[] groups) {
		JPanel panel = new JPanel(new GridLayout(0, 2));
		JButton okButton = new JButton("Delete");
		JButton cancelButton = new JButton("Cancel");

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		okButton.setActionCommand("OK");
		cancelButton.setActionCommand("Cancel");

		for (String group : groups) {
			JCheckBox box = new JCheckBox();
			panel.add(new JLabel(group));
			box.setActionCommand(group);
			boxes.add(box);
			panel.add(box);
		}

		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		setLocationRelativeTo(parent);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
			deleteGroups();
		} else if (cmd.equals("Cancel"))
			dispose();
	}

	private void deleteGroups() {
		HTTPResponse response;
		for (JCheckBox box : boxes) {
			if (box.isSelected()) {
				response = context.client.sendServerDeleteGroup (box.getActionCommand());
				if (response.getErrorCode() != 200) {
					JOptionPane.showMessageDialog(parent, "Could not delete the group " + box.getActionCommand(),
							"Error Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		dispose();
	}

}
