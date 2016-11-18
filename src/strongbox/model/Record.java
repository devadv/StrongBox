package strongbox.model;

import java.io.Serializable;

/**
 * A model for record objects.
 * 
 * @author ttimmermans
 */

public class Record implements Serializable {
	
	private String title;
    private String address;
    private String userName;
    private String password;
    private String info;
    private String folder;
    private String note;
    // serialVersionUID is needed since Serializable interface is implemented.
	private static final long serialVersionUID = 1983L;

    /**
     * Constructor for objects of class Record
     */
    public Record(String title, String address, String userName, 
    		String password, String info, String folder, String note) {
        this.title = title;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.info = info;
        this.folder = folder;
        this.note = note;
    }

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
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
	 * @param address the address to set
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
	 * @param userName the userName to set
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return the folder
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * @param folder the folder to set
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
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
    /**
     * @return String to display Record
     */
	public String toString(){
		String s =
		String.format("Folder: %s"
				+ "\nTitle: %s"
				+ " Address: %s"
				+ " Username: %s"
				+ " Password: %s"
				+ "\nInfo: %s"
				+ " Note: %s" 
				, folder,title,address,userName,password,info,note	);
		
		return s ;
	}
    

}
