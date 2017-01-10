package strongbox.test.login.mvc;

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
		
		JLabel labelPassword = new JLabel("Password: ");		
		passwordField = new JPasswordField();
		button = new JButton("Login");
		
		setLayout(null);
		labelPassword.setBounds(20, 20, 120, 20);
		passwordField.setBounds(120, 20, 150, 20);
		button.setBounds(189, 50, 80, 20);
		add(labelPassword);
		add(passwordField);
		add(button);

		frame.add(this);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 117);
		frame.setLocationRelativeTo(null);
        frame.setResizable(false);
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
