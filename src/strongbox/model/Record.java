package strongbox.model;

import strongbox.encryption.Encryption;

/**
 * A representation of a record object.
 * 
 * @version 04-04-2017
 */

public class Record implements Comparable<Record> {

	private String title;
	private String address;
	private String userName;
	private String password;
	private String folder;
	private String note;
	private String encryptionpasswd;
	private long timestamp;

	/**
	 * Constructor for objects of class Record
	 */
	public Record(String title, String address, String userName,
			String password, String folder, String note, long timestamp) {
		
		this.title = title;
		this.address = address;
		this.userName = userName;
		this.folder = folder;
		this.note = note;
		//if password is hashed decrypt and set encryptionpasswd
		if (password.startsWith("hash_")) {
			this.encryptionpasswd = password;
			this.password = Encryption.decrypt(password.substring(5));
		}
		else {
			this.password = password;
			setEncryptionpasswd(password);
		}
		this.timestamp = timestamp;
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
	 * Return the timestamp of this record indicating when it was created. 
	 * @return the record's timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Set the record's timestamp. 
	 * @param  timestamp  The value to set the record's timestamp to.
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Return the title. Used as a string representation of the record in
	 * the view's Record-JList.
	 * @return String to display Record's title.
	 */
	public String toString() {
		return title;
	}
	
    /**
     * Implementation of compareTo() method from interface Comparable to enable 
     * the sorting of Record objects. The records should be lexicographically 
     * sorted (case insensitive) according to their title.
     */
    public int compareTo(Record record) {
      	return this.getTitle().compareToIgnoreCase(record.getTitle());
    }	

}