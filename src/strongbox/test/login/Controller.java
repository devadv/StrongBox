package strongbox.test.login;



public class Controller {

		
	public static void main(String[] args) {

		ConfigProp cProp = new ConfigProp();

		if (cProp.checkMasterKeyExists()) {

			new Login(cProp);
		}

	}

}
