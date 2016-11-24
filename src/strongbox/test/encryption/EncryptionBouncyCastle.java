package strongbox.test.encryption;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptionBouncyCastle {
	

	public static String encrypt(String text) {
		
		StandardPBEStringEncryptor sStringEncrypter = new StandardPBEStringEncryptor();
		sStringEncrypter.setProvider(new BouncyCastleProvider());
		sStringEncrypter.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC");
		sStringEncrypter.setPassword("SpxJ!C=-6?gFu2Mc");
		String encryptedText= sStringEncrypter.encrypt(text);
		return encryptedText;
	}
	public static String decrypt(String encryptedtext){
		
		StandardPBEStringEncryptor sStringEncrypter = new StandardPBEStringEncryptor();
		sStringEncrypter.setProvider(new BouncyCastleProvider());
		sStringEncrypter.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC");
		sStringEncrypter.setPassword("SpxJ!C=-6?gFu2Mc");
		String decryptedText= sStringEncrypter.decrypt(encryptedtext);
		return decryptedText;
		}
	
}