package strongbox.test.login;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import strongbox.view.GUI;

public class Login extends JPanel implements ActionListener{
	
	
	
	private JTextField passwordField;
	private JFrame frame;
	private ConfigProp cProp;

	public Login(ConfigProp cProp) {
		this.cProp = cProp;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 70);
		frame.setLocation(600, 400);
		JLabel labelPassword = new JLabel("Password: ");
		passwordField = new JPasswordField(10);
		passwordField.addActionListener(this);
		JButton button = new JButton("Login");
		button.addActionListener(this);
		setLayout(null);
		labelPassword.setBounds(10,10,120,20);
		passwordField.setBounds(110,10,150,20);
		button.setBounds(180,40,80,20);
		add(labelPassword);
		add(passwordField);
		add(button);
		frame.add(this);
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(cProp.setMasterKeyAndLogin(passwordField.getText())){
			System.out.println("Login successful");
			frame.dispose();
			new GUI();
		}else{
			frame.dispose();
		}
		
		
	}

}
