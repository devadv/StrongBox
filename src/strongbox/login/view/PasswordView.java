package strongbox.login.view;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

	private JTextField passwordField;
	private JFrame frame;
	protected JButton button;
	protected JLabel labelPassword;
	protected JLabel msg;
	protected JLabel image;
	protected ImageIcon pict_small;
	protected JCheckBox googleCheck;
	/**
	 * Constructor sets up the default GUI
	 */
	public PasswordView() {

		setFrame(new JFrame());
		
		setPasswordField(new JPasswordField());
		button = new JButton("Login");
		labelPassword = new JLabel("Password: ");
		msg = new JLabel("Welcome to StrongBox: ");
		
		URL url = PasswordView.class.getResource("/box.png");
		ImageIcon pict = new ImageIcon(url);
		
		url = PasswordView.class.getResource("/box_small.png");
		pict_small = new ImageIcon(url);
		
		image = new JLabel(pict);
		
		setLayout(null);
		
		getFrame().add(this);		
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getFrame().setLocationRelativeTo(null);
        getFrame().setResizable(false);
		getFrame().setVisible(true);

	}
	/**
	 * add actionlistener for Button and PasswordField to connect with Controller
	 * @param listenForAction ActionListener to add
	 */
	public void addActionListenerButtonAndField(ActionListener listenForAction) {
		button.addActionListener(listenForAction);
		getPasswordField().addActionListener(listenForAction);
		
	}
	/**
	 * get the userpassword at login 
	 * @return String userpassword
	 */
	public String getPassword() {
		return getPasswordField().getText();
	}

	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public JTextField getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(JTextField passwordField) {
		this.passwordField = passwordField;
	}
	public boolean getGoogleCheck(){
		
		if(googleCheck.isSelected()){
			return true;
		}else {
			return false;
		}
		
		
	}
}
