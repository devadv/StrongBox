package strongbox.model;

import java.io.Serializable;

import strongbox.encryption.Encryption;

/**
 * A representation of a record object.
 */

public class Record implements Serializable {

	private String title;
	private String address;
	private String userName;
	private String password;
	private String folder;
	private String note;
	private String encryptionpasswd;
	// serialVersionUID is needed since Serializable interface is implemented.
	private static final long serialVersionUID = 1983L;

	/**
	 * Constructor for objects of class Record
	 */
	public Record(String title, String address, String userName,
			String password, String folder, String note) {
		this.title = title;
		this.address = address;
		this.userName = userName;
		this.password = password; // <--- REMOVE line once finished
		this.folder = folder;
		this.note = note;
		//if password is hashed decrypt and set encryptionpasswd
		// UNCOMMENT AGAIN ONCE FINISHED
		if(password.startsWith("hash_")){
			this.encryptionpasswd = password;
			this.password = Encryption.decrypt(password.substring(5));
		}else {
			this.password = password;
			setEncryptionpasswd(password);
		}
		
	}

	public String getEncryptionpasswd() {
		return encryptionpasswd;
	}

	public void setEncryptionpasswd(String passwd) {
		this.encryptionpasswd = "hash_" + Encryption.encrypt(passwd);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
		
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the folder
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * @param folder
	 *            the folder to set
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * @return String to display Record
	 */
	public String toString() {
		String s = String.format("Folder: %s" + "\nTitle: %s" + " Address: %s"
				+ " Username: %s" + " Password: %s" + " Note: %s", 
				folder, title, address, userName, getPassword(), note);

		return s;
	}

}