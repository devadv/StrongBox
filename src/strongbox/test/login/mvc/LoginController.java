package strongbox.test.login.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

	private PropertiesModel model;
	private PasswordView view;
	private boolean access = false;

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

					@Override
					public void actionPerformed(ActionEvent e) {
						if (model.checkLogin(view.getPassword())) {
							System.out.println("Login correct!");
							view.frame.dispose();
							access = true;	
						} else {
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
						System.out.println("with passwd" + view.getPassword());
						model.saveMasterKey(view.getPassword());
						view.frame.dispose();
						login();
					}
				});

		}
		
	}
}
