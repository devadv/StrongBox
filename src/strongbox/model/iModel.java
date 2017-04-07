package strongbox.model;

/**
 * Interface for the Model
 * 
 * @author devadv 
 * 
 */

import java.util.ArrayList;
import java.util.TreeSet;

public interface iModel {
		
    // CRUD: create and update 
	/**
	 * Create a new record and add it to the list.
	 */
	void createNewRecord(String title, String address, String userName,
			String password, String folder, String note, long timestamp);
	
	/**
	 * Add a record to the list.
	 * @param record  The record to add.
	 */
	void addRecord(Record record);
	
	/**
	 * Delete a record from the list.
	 * @param record  The record to delete.
	 */
	void delete(Record record);
	
	/**
	 * Remove ALL records from the list.
	 */	
	void deleteAll();
	
	/**
	 * Return the list holding the record objects.
	 * @return  The record-list.
	 */
	ArrayList<Record> getRecordList();
	
	/**
	 * Get a list of records whose title, address, note or folder attribute 
	 * contains (parts of) the keyword searched with.
	 * Sort this list before returning.
	 * 
	 * @param keyword  The keyword to search for.
	 * @return A sorted list of matching records.
	 */
	ArrayList<Record> search(String keyword);

	/**
	 * Make a list of records associated with the specified folder name,
	 * sort the list and then return it.
	 * @param folder  The folder's name.
	 * @return The sorted list of records.
	 */
	ArrayList<Record> getRecordsByFolder(String folder);
	
	/**
	 * Get a set with the folder names (it's a set so no duplicate names are
	 * to be found here).
	 * @return  The set with folder names.
	 */
	TreeSet<String> getFolders();
	
    // --- Password settings ---
	/**
	 * Set the master password for access and encryption.
	 * @param password  The password to set the first time using StrongBox.
	 */
	void setMasterPassword(String password);
	
	/**
	 * Set the password phrase for encryption
	 * @param passphrase the password phrase to set
	 */
	void setPassPhraseEncryption(String passphrase);
	
    // --- Input from or output to file ---
	/**
	 * Read or retrieve the records from a file.
	 */
	void readRecordsFromFile();
	
	/**
	 * Save the records to a file.
	 */
	void writeRecordsToFile();

	
	
}