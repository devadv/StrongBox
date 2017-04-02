package strongbox.encryption;

import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;
/**
 * Encryption class for encryption password and passphrase 
 * @author ben
 *
 */

public class Encryption {
	
	private static String password;
	
	public Encryption(String password) {
		this.password = password;
	}
	/**
	 * static method encrypts given text 
	 * @param text
	 * @return encryptedtext 
	 */
	
	public static String encrypt(String text) {
		BasicTextEncryptor sTextEncryptor = new BasicTextEncryptor();
		sTextEncryptor.setPassword(password);
		String encryptedText = sTextEncryptor.encrypt(text);
		return encryptedText;
	}
	/**
	 * static method decrypts give encryptedtext
	 * @param encryptedtext
	 * @return
	 */
	public static String decrypt(String encryptedtext){
		BasicTextEncryptor sTextEncryptor = new BasicTextEncryptor();
		sTextEncryptor.setPassword(password);
		String decryptedText =sTextEncryptor.decrypt(encryptedtext);
		return decryptedText;
		
	}

}
