package strongbox.login;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
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
	protected JLabel labelPassword;
	protected JLabel msg;
	protected JLabel image;
	protected ImageIcon pict_small;
	/**
	 * Constructor sets up the default GUI
	 */
	public PasswordView() {

		frame = new JFrame();
		
		passwordField = new JPasswordField();
		button = new JButton("Login");
		labelPassword = new JLabel("Password: ");
		msg = new JLabel("Welcome to StrongBox: ");
		
		URL url = PasswordView.class.getResource("/box.png");
		ImageIcon pict = new ImageIcon(url);
		
		url = PasswordView.class.getResource("/box_small.png");
		pict_small = new ImageIcon(url);
		
		image = new JLabel(pict);
		
		setLayout(null);
		
		frame.add(this);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
