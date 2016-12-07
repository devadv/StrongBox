package strongbox.test.encryption;

import org.jasypt.util.text.StrongTextEncryptor;

public class Encryption {
	
	private static String password;
	
	public Encryption(String password) {
		this.password = password;
	}
	
	public static String encrypt(String text) {
		StrongTextEncryptor sTextEncryptor = new StrongTextEncryptor();
		sTextEncryptor.setPassword(password);
		String encryptedText = sTextEncryptor.encrypt(text);
		return encryptedText;
	}
	public static String decrypt(String encryptedtext){
		StrongTextEncryptor sTextEncryptor = new StrongTextEncryptor();
		sTextEncryptor.setPassword(password);
		String decryptedText =sTextEncryptor.decrypt(encryptedtext);
		return decryptedText;
		
	}

}
