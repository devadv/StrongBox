package strongbox.test;

import strongbox.controller.MainController;

public class MainStart {

	public static void main(String[] args) {

		final MainController mainController = new MainController();

		Thread controller1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					mainController.startController();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread controller2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					mainController.startControllerLogin();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		});

		controller1.start();
		controller2.start();
	}

}
