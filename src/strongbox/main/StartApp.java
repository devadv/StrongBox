package strongbox.main;


import strongbox.controller.Controller;
import strongbox.login.LoginController;
import strongbox.login.PropertiesModel;
import strongbox.model.Model;

/**
 * Main controller handles login and starts main application
 * 
 * @author ben
 *
 */
//TODO checks for untouched code

public class StartApp {
	Object lock = new Object();

	public StartApp() {
		
		PropertiesModel prop = new PropertiesModel();
		final LoginController login = new LoginController(prop);

		Thread t1 = new Thread() {
			public void run() {
				synchronized (lock) {
					System.out.println("Starting Login");
					login.login();
					while (!login.hasAccess()) {
						// System.out.println("Login started ...");
						lock.notify();
					}
				}
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				synchronized (lock) {
					while (!login.hasAccess())
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					Model model = new Model();
					model.setMasterPassword(login.getPassword());
					Controller app = new Controller(model);
				}
			}
		};
		t1.start();
		t2.start();

	}

	public static void main(String[] args) {
		new StartApp();
	}

}