package strongbox.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for login
 * 
 * @author ben
 *
 */

public class LoginController {

	private PropertiesModel model;
	private PasswordView view;
	private boolean access = false;
	private String password;

	/**
	 * Constructor sets the properties model
	 * 
	 * @param model
	 */

	public LoginController(PropertiesModel model) {
		this.model = model;

	}

	/**
	 * the method asks for the user password otherwise it asks to setup a
	 * masterkey
	 * 
	 */
	public void login() {

		if (model.checkMasterKeyExists()) {
			final LoginView view = new LoginView();
			view.addActionListenerButtonAndField(new ActionListener() {
				// counter is number of attempts to login
				int counter = 0;

				@Override
				public void actionPerformed(ActionEvent e) {

					if (model.checkLogin(view.getPassword())) {

						System.out.println("Login correct!");
						LoginController.this.password = view.getPassword();
						//System.out.println(LoginController.this.password);
						view.frame.repaint();
						view.frame.dispose();
						LoginController.this.access = true;
						
					} else {
						//set the title of the frame
						view.frame.setTitle("Wrong password try again!");
						counter++;
						if (counter > 2) {
							view.frame.dispose();
							System.exit(1);
						}
						System.out.println("Wrong password try again");
						view.passwordField.setText("");
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
	/**
	 * get the user password
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * true if the user has access 
	 * @return boolean access
	 */
	public boolean hasAccess() {
		return access;
	}
}
