package strongbox.model;

/**
 * Interface for the Model
 * 
 * @author devadv 
 * 
 */

import java.util.ArrayList;
import java.util.HashSet;

public interface iModel {
	
	
	
//CRUD
	
	//create and update
	/**
	 *save Record to File
	 *@param records the list of Records to save
	 */
	void writeRecordsToFile(ArrayList<Record> records);
	
	/**
	 * Add an Record to ArrayList
	 */
	void addRecord(String title, String address, String username, String password, String Info, String Folder, String Note);
	
	//read or retrieve
	
	void readRecordsFromFile();
	/**
	 *total list of Records
	 *@return ArrayList
	 */
	ArrayList<Record> getRecords();
	
	/**
	 *gets list of records with the same foldername
	 *@param foldername the foldername to get
	 *@return Arraylist
	 */
	ArrayList<String> getRecords(String foldername);
	
	/**
	 *get a record 
	 *@param title search for title
	 *@return String[]
	 */
	String[] getRecord(String title);
	
	/**
	 *get unique folders
	 *@return HashSet
	 */
	HashSet<String> getFolders();
			
	
	/**
	 *delete a record
	 *@param record to delete
	 */
	void delete(Record record);
	
	/**
	 *delete all the records
	 */	
	void deleteAll();
	
// Settings
	
	/**
	 *set the masterpassword for access and encryption
	 *@param password password to set the first of using StrongBox 
	 */
	void setMasterPassword(String password);
	
	/**
	 *set the passfrase for encryption
	 *@param passfrase the passfrase to set
	 */
	void setPassfraseEncryption(String passfrase);
	
	
// Utils
	
	/**
	 *get a records that contains the keyword
	 *@param keyword the keyword to search for
	 *@return ArrayList
	 */
	ArrayList<Record> search(String keyword);
	
	
}
