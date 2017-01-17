package strongbox.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

import strongbox.util.PasswordSafe;


public class PropertiesModel {

	
/** Directory to store user data for this application. */
	
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".strongbox");
	
	private File file;
	private BasicTextEncryptor stringEncryptor;
	private EncryptableProperties prop;

	public PropertiesModel() {
		stringEncryptor = new BasicTextEncryptor();
		prop = new EncryptableProperties(stringEncryptor);
	}

	/**
	 * checks if masterkey exists
	 * 
	 * @return boolean keyExist
	 */
	public boolean checkMasterKeyExists() {
		boolean keyExist = false;
		file = new File(DATA_STORE_DIR + "/config.properties");
		try {
			Scanner input = new Scanner(file);
			input.nextLine();
			String key = input.findInLine("masterkey");
			if (key.equals("masterkey")) {
				keyExist = true;
			}
		} catch (NoSuchElementException ex) {
			keyExist = false;
		} catch (FileNotFoundException e) {
			keyExist = false;
		}
		return keyExist;
	}

	/**
	 * writes masterkey to properties file
	 * 
	 * @param userkey
	 *            setup password
	 */
	public void saveMasterKey(String masterpasswd) {

		OutputStream output = null;
		stringEncryptor.setPassword(masterpasswd);

		try {
			output = new FileOutputStream(DATA_STORE_DIR+"/config.properties");
			String encryptPasswd = stringEncryptor.encrypt(masterpasswd);
			prop.setProperty("masterkey", encryptPasswd);
			prop.setProperty("passphrase",
					stringEncryptor.encrypt(PasswordSafe.generatePassphrase((32))));
			prop.store(output, null);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * read property from file config.properties and checks if user input is the
	 * right key
	 * 
	 * @param userInputpasswd
	 *            the user password
	 * @return login gives true when passwords match
	 */

	public boolean checkLogin(String userInputpasswd) {
		BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
		boolean login = false;
		stringEncryptor.setPassword(userInputpasswd);
		InputStream input;
		try {
			input = new FileInputStream(file);
			prop.load(input);
			if (prop.get("masterkey") != null) {
				String s = prop.getProperty("masterkey");
				String d = stringEncryptor.decrypt(s);
				if (d.equals(userInputpasswd)) {
					login = true;
				}
			}

		} catch (EncryptionOperationNotPossibleException e) {
			// System.out.println("Wrong Login... Terminating");
			login = false;

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return login;

	}

}
