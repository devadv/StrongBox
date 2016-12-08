package strongbox.login;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** 
 * abstract class for default login view
 * @author ben
 *
 */

public abstract class PasswordView extends JPanel {

	protected JTextField passwordField;
	protected JFrame frame;
	protected JButton button;
	/**
	 * Constructor sets up the default GUI
	 */
	public PasswordView() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 70);
		frame.setLocation(600, 400);
		JLabel labelPassword = new JLabel("Password: ");
		passwordField = new JPasswordField(10);
		button = new JButton("Login");
		setLayout(null);
		labelPassword.setBounds(10, 10, 120, 20);
		passwordField.setBounds(110, 10, 150, 20);
		button.setBounds(180, 40, 80, 20);
		add(labelPassword);
		add(passwordField);
		add(button);
		frame.add(this);
		frame.setVisible(true);

	}
	/**
	 * add actionlistener for Button and PasswordField to connect with Controller
	 * @param listenForAction ActionListener to add
	 */
	public void addActionListenerButtonAndField(ActionListener listenForAction) {
		button.addActionListener(listenForAction);
		passwordField.addActionListener(listenForAction);
	}
	/**
	 * get the userpassword at login 
	 * @return String userpassword
	 */
	public String getPassword() {
		return passwordField.getText();
	}

}
