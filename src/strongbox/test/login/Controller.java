package strongbox.test.login;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Controller {

		

	public static void main(String[] args) {

		ConfigProp cProp = new ConfigProp();

		if (cProp.checkMasterKeyExists()) {

			new Login(cProp);
		}

	}

}
