package strongbox.model;


import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptingText {
	

	public static String encrypt(String text) {
		StrongTextEncryptor sTextEncryptor = new StrongTextEncryptor();
		sTextEncryptor.setPassword("SpxJ!C=-6?gFu2Mc");
		String encryptedText = sTextEncryptor.encrypt(text);
		return encryptedText;
	}
	public static String decrypt(String encryptedtext){
		StrongTextEncryptor sTextEncryptor = new StrongTextEncryptor();
		sTextEncryptor.setPassword("SpxJ!C=-6?gFu2Mc");
		String decryptedText =sTextEncryptor.decrypt(encryptedtext);
		return decryptedText;
		
	}
	
}