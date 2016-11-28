package strongbox.test.login;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel implements ActionListener{
	
	
	private JTextField usernameField;
	private JTextField passwordField;

	public LoginPanel() {

		JLabel labelUsername = new JLabel("Login: ");
		JLabel labelPassword = new JLabel("Password: ");
		usernameField = new JTextField(10);
		passwordField = new JTextField(10);
		JButton button = new JButton("Login");
		button.addActionListener(this);
		setLayout(null);
		labelUsername.setBounds(10,10 ,120,20);
		labelPassword.setBounds(10,40,120,20);
		usernameField.setBounds(110,10,150,20);
		passwordField.setBounds(110,40,150,20);
		button.setBounds(200,70,80,20);
		add(labelUsername);
		add(usernameField);
		add(labelPassword);
		add(passwordField);
		add(button);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("clicked!");
		System.out.println(usernameField.getText());
		System.out.println(passwordField.getText());
		
	}

}
