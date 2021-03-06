package strongbox.login.view;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * view for setting up a password extends the PasswordView
 * 
 * @author ben
 *
 */

public class SetupView extends PasswordView {

	public SetupView() {
		
		
		msg.setText("Welcome to StrongBox: ");
		JLabel submsg = new JLabel("a simple password vault");
		labelPassword.setText("Give masterkey: ");
		image.setBounds(50, 20, 250, 200);
		msg.setBounds(20 , 230, 200,50);
		submsg.setBounds(20,250,200,50);
		labelPassword.setBounds(20, 300, 120, 20);
		getPasswordField().setBounds(150, 300, 150, 20);
		JLabel drive = new JLabel("Use Google Drive Storage");
		drive.setBounds(20, 330, 200, 20);
		googleCheck = new JCheckBox();
		googleCheck.setBounds(220, 330, 20, 20);
		button.setBounds(189, 370, 80, 20);
		
		add(image);
		add(msg);
		add(submsg);
		add(labelPassword);
		add(getPasswordField());
		add(drive);
		add(googleCheck);
		add(button);
		
		getFrame().setTitle("Make a StrongBox account");
		button.setText("Save");
		getFrame().setSize(350, 460);
	}

}
