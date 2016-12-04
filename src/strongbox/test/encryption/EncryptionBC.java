package strongbox.test.encryption;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptionBC {
	
	private static String password;
	public EncryptionBC() {
		this("SpxJ!C=-6?gFu2Mc");
	}
	public EncryptionBC(String password){
		this.password = password;
	}

	public static String encrypt(String text) {
		
		StandardPBEStringEncryptor sStringEncrypter = new StandardPBEStringEncryptor();
		sStringEncrypter.setProvider(new BouncyCastleProvider());
		sStringEncrypter.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC");
		sStringEncrypter.setPassword(password);
		String encryptedText= sStringEncrypter.encrypt(text);
		return encryptedText;
	}
	public static String decrypt(String encryptedtext){
		
		StandardPBEStringEncryptor sStringEncrypter = new StandardPBEStringEncryptor();
		sStringEncrypter.setProvider(new BouncyCastleProvider());
		sStringEncrypter.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC");
		sStringEncrypter.setPassword(password);
		String decryptedText= sStringEncrypter.decrypt(encryptedtext);
		return decryptedText;
		}
	
}