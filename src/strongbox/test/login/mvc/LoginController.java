package strongbox.test.login.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

	private PropertiesModel model;
	private PasswordView view;
	private boolean access = false;
	private String password;
	public boolean hasAccess() {
		return access;
	}

	public LoginController(PropertiesModel model) {
		this.model = model;

	}
	public void login() {

			if (model.checkMasterKeyExists()) {
				final LoginView view = new LoginView();
				view.addActionListenerButtonAndField(new ActionListener() {
					int counter = 0;

					@Override
					public void actionPerformed(ActionEvent e) {
						
						if (model.checkLogin(view.getPassword())) {
							
							System.out.println("Login correct!");
							LoginController.this.password = view.getPassword();
							System.out.println(LoginController.this.password);
							view.frame.dispose();
							view.frame.repaint();
							access = true;	
							
						} else {
							view.frame.setTitle("Wrong password try again!" );
							counter++;
							if(counter>2){
								view.frame.dispose();
								System.exit(1);
							}
							System.out.println("Wrong password try again");
						}
						
					}
				});
				
			} else {
				final SetupMasterKeyView view = new SetupMasterKeyView();
				view.addActionListenerButtonAndField(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(" SetupMasterKey");
						model.saveMasterKey(view.getPassword());
						view.frame.dispose();
						login();
					}
				});

		}
		
	}

	public String getPassword() {
		return password;
	}
}
