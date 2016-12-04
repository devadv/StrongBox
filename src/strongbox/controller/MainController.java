package strongbox.controller;

import strongbox.model.Model;
import strongbox.test.login.mvc.LoginController;
import strongbox.test.login.mvc.PropertiesModel;
import strongbox.view.GUI;

public class MainController {

	private PropertiesModel propModel = new PropertiesModel();
	private LoginController controller = new LoginController(propModel);
	private String password;
	
	

	public void startController() throws InterruptedException {
		synchronized (controller) {

			System.out.println("Starting Controller!");
			controller.wait();
			System.out.println("Running again..");
			Model model = new Model();
			model.readProperties();
			password = controller.getPassword();
			System.out.println(password);
			
			AppController appController = new AppController(model, password);
			
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
