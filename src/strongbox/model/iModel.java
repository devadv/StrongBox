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
		
    // CRUD: create and update 
	/**
	 * Create a new record and add it to the list.
	 */
	void createNewRecord(String title, String address, String userName,
			String password, String folder, String note);
	
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
	 * Get a record based on it's title.
	 * @param title  The record's title.
	 * @return  The corresponding record.
	 */
	Record getRecord(String title);
	
	/**
	 * Get a list of records containing (parts of) the keyword.
	 * To create this list searching is done not only by title but address, 
	 * note and folder attributes are also being searched.
	 * @param keyword  The keyword to search for.
	 * @return  A list of matching records.
	 */
	ArrayList<Record> search(String keyword);

	/**
	 * Returns a list of record titles associated with the specified folder name.
	 * @param folder   The folder's name.
	 * @return The list of record titles.
	 */
	ArrayList<String> getTitlesByFolder(String folder);
	
	/**
	 * Get a set with the folder names (it's a set so no duplicate names are
	 * to be found here).
	 * @return  The set with folder names.
	 */
	HashSet<String> getFolders();
	
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