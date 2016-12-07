package strongbox.controller;

import org.jasypt.util.text.StrongTextEncryptor;

import strongbox.model.Model;
import strongbox.test.encryption.EncryptingText;
import strongbox.test.encryption.Encryption;
import strongbox.view.GUI;

public class AppController {

	public AppController(Model model, String password) {
		Encryption encryptor = new Encryption(password); 
		String encryptedPassphrase =model.getPassphrase();
		String passphrase = encryptor.decrypt(encryptedPassphrase);
				
		System.out.println(password);;
		System.out.println(passphrase);
		new GUI();
	}
}
