package strongbox.test.encryption;

import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptingTextObject {
	
	private String password;
	
	public EncryptingTextObject(String password) {
		this.password = password;
	}
	
	public String encrypt(String text) {
		StrongTextEncryptor sTextEncryptor = new StrongTextEncryptor();
		sTextEncryptor.setPassword(password);
		String encryptedText = sTextEncryptor.encrypt(text);
		return encryptedText;
	}
	public String decrypt(String encryptedtext){
		StrongTextEncryptor sTextEncryptor = new StrongTextEncryptor();
		sTextEncryptor.setPassword(password);
		String decryptedText =sTextEncryptor.decrypt(encryptedtext);
		return decryptedText;
		
	}

}
