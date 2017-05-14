package strongbox.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import strongbox.login.view.LoginView;
import strongbox.login.view.SetupView;

/**
 * Controller for login
 * 
 * @author ben
 *
 */

public class LoginController {

	private PropertiesModel model;
	private boolean access = false;
	private String password;
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".strongbox");
	private String pathMaster = DATA_STORE_DIR + "/config.properties";
	private String pathPassphrase = DATA_STORE_DIR
			+ "/config.passphrase.properties";

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
						// System.out.println(LoginController.this.password);
						view.getFrame().repaint();
						view.getFrame().dispose();
						LoginController.this.access = true;

					} else {
						// set the title of the frame
						view.getFrame().setTitle("Wrong password try again!");
						counter++;
						if (counter > 2) {
							view.getFrame().dispose();
							System.exit(1);
						}
						System.out.println("Wrong password try again");
						view.getPasswordField().setText("");
					}

				}
			});

		} else {
			final SetupView view = new SetupView();
			view.addActionListenerButtonAndField(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(" SetupMasterKey");
					String masterpasswd = view.getPassword();
					// create config.properties and set drive connection
					model.saveProperties(masterpasswd, pathMaster, "masterkey",
							view.getGoogleCheck());
					System.out.println(" SetupPassphrase");
					if (!view.getGoogleCheck()) {
						// create passphrase properties
						model.saveProperties(masterpasswd, pathPassphrase,
								"passphrase", false);
					}
					view.getFrame().dispose();
					login();
				}
			});
		}

	}

	/**
	 * get the user password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * true if the user has access
	 * 
	 * @return boolean access
	 */
	public boolean hasAccess() {
		return access;
	}
}
