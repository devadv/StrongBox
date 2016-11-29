package strongbox.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A model for managing the application and the handling of records.
 */

public class Model implements iModel {
	
	private ArrayList<Record> records;

	/**
	 * Create a new record and add it to the list.
	 */
	@Override
	public void createNewRecord(String title, String address, String userName,
			String password, String folder, String note) { 
		addRecord(new Record(title, address, userName, password, folder, note));
	}
	
	/**
	 * Add a record to the list.
	 * @param record  The record to add.
	 */
	@Override
	public void addRecord(Record record) {
		records.add(record);
	}
	
	/**
	 * Delete a record from the list.
	 * @param record  The record to delete.
	 */
	@Override
	public void delete(Record record) {
		records.remove(record);	
	}

	/**
	 * Remove ALL records from the list.
	 */
	@Override
	public void deleteAll() {
		records.clear();
	}

	/**
	 * Return the list holding the record objects.
	 * @return  The record-list.
	 */
	@Override
	public ArrayList<Record> getRecordList() {
	    return records;
	}
	
	/**
	 * Get a record based on it's title.
	 * @param title  The record's title.
	 * @return  The corresponding record.
	 */
	@Override
	public Record getRecord(String title) {
		Record rec = null;
		for (Record record: records) {
			if (record.getTitle().equals(title)) {
				rec = record;
			}
		}
		if (rec == null) {
			System.out.println("Error! No record with such title exists!");
		}
		return rec;	
	}

	/**
	 * Get a list of records containing (parts of) the keyword.
	 * To create this list searching is done not only by title but address, 
	 * note and folder attributes are also being searched.
	 * @param keyword  The keyword to search for.
	 * @return  A list of matching records.
	 */
	@Override
	public ArrayList<Record> search(String keyword) {
		ArrayList<Record> recordsByKeyword = new ArrayList<>();
		for (Record record: records) {
			if (record.getTitle().contains(keyword) || 
					record.getAddress().contains(keyword) ||
					record.getNote().contains(keyword) ||
					record.getFolder().contains(keyword)) {
				recordsByKeyword.add(record);
			}
		}
		return recordsByKeyword;
	}
	
	/**
	 * Returns a list of records having the specified folder name.
	 * @param folder  The folder's name.
	 * @return  The list of records.
	 */
	@Override
	public ArrayList<Record> getRecordsByFolder(String folder) {
		ArrayList<Record> recordsByFolder = new ArrayList<>();
		for (Record record: records) {
			if (record.getFolder().equals(folder)) {
				recordsByFolder.add(record);
			}
		}
		return recordsByFolder;
	}
	
	/**
	 * Get a set with the folder names (it's a set so duplicate names are not
	 * to be found here).
	 * @return  The set with folder names.
	 */
	@Override
	public HashSet<String> getFolders() {
		HashSet<String> folderNames = new HashSet<>();
		for (Record record: records) {
			folderNames.add(record.getFolder());
		}
		return folderNames;
	}

	// --- Password settings ---	
	/**
	 * Set the master password for access and encryption.
	 * @param password  The password to set the first time using StrongBox.
	 */
	@Override
	public void setMasterPassword(String password) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Set the password phrase for encryption
	 * @param passphrase the password phrase to set
	 */
	@Override
	public void setPassPhraseEncryption(String passphrase) {
		// TODO Auto-generated method stub		
	}

    // --- Input from or output to file ---
	/**
	 * Read or retrieve the records from a file.
	 */
	@Override
	public void readRecordsFromFile() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Save the records to a file.
	 */
	@Override
	public void writeRecordsToFile() {
		// TODO Auto-generated method stub
	}

}
