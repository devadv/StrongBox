package strongbox.controller;

import strongbox.test.login.mvc.LoginController;
import strongbox.test.login.mvc.PropertiesModel;
import strongbox.view.GUI;

public class MainController {

	private PropertiesModel model = new PropertiesModel();
	private LoginController controller = new LoginController(model);

	public void startController() throws InterruptedException {
		synchronized (controller) {

			System.out.println("Starting Controller!");
			controller.wait();
			System.out.println("Running again..");
			new GUI();// Or new Controller
		}
	}

	public void startControllerLogin() throws InterruptedException {
		synchronized (controller) {
			System.out.println("Starting LoginController !!");
			controller.login();
			while (!controller.hasAccess()) {
				controller.notify();
			}
		}
	}

}
