package strongbox.login;

/**
 * view for login window extends the PasswordView
 * 
 * @author ben
 *
 */

public class LoginView extends PasswordView {
	
	public LoginView() {
		
		image.setSize(30, 40);
		image.setBounds(120, 10, 50,50 );
		image.setIcon(pict_small);
		msg.setText("Login");
		msg.setBounds(20, 60, 250, 20);
		labelPassword.setBounds(20, 85, 120, 20);
		passwordField.setBounds(120, 85, 150, 20);
		button.setBounds(189,120,80, 20);
		
		frame.setTitle("StrongBox login");
		button.setText("Login");
		add(image);
		add(msg);
		add(labelPassword);
		add(passwordField);
		add(button);
		frame.setSize(300, 180);
		
		
	}

}
