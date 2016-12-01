package strongbox.test.login.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream.GetField;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jasypt.exceptions.AlreadyInitializedException;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

import strongbox.view.GUI;

public class PropertiesModel {

	private File file;
	private StrongTextEncryptor stringEncryptor;
	private EncryptableProperties prop;
	

	public PropertiesModel() {
		stringEncryptor = new StrongTextEncryptor();
		prop = new EncryptableProperties(stringEncryptor);
	}

	/**
	 * checks if masterkey exists
	 * 
	 * @return boolean keyExist
	 */
	public boolean checkMasterKeyExists() {
		boolean keyExist = false;
		file = new File("res/config.properties");
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
			output = new FileOutputStream("res/config.properties");
			String encryptPasswd = stringEncryptor.encrypt(masterpasswd);
			prop.setProperty("masterkey", encryptPasswd);
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
		StrongTextEncryptor stringEncryptor = new StrongTextEncryptor();
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
			//System.out.println("Wrong Login... Terminating");
			login = false;

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return login;

	}
}
