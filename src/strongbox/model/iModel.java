package strongbox.model;

import java.util.ArrayList;
import java.util.HashSet;

public interface iModel {
	
	//CRUD
	
	//create and update
	/*
	 * save Record to File
	 * @param ArrayList<Records> the list of Records to save
	 */
	void writeRecordsToFile(ArrayList<Record> records);
	
	/*
	 * Add an Record to ArrayList
	 */
	void addRecord(String title, String address, String username, String password, String Info, String Folder, String Note);
	
	//read or retrieve
	
	void readRecordsFromFile();
	/*
	 * total list of Records
	 * @return ArrayList<Record>
	 */
	ArrayList<Record> getRecords();
	
	/*
	 * gets list of records with the same foldername
	 * @param String foldername the folder to search for
	 * @return Arraylist<Record>
	 */
	ArrayList<Record> getRecords(String foldername);
	
	/*
	 *  get a record 
	 *  @param String title of record
	 *  @return Record 
	 */
	Record getRecord(String title);
	
	/*
	 * get unique folders
	 * @return HashSet<String>  
	 */
	HashSet<String> getFolders();
			
	
	/*
	 * delete a record
	 * @param Record record to delete
	 */
	void delete(Record record);
	
	/*
	 * delete all the records
	 */	
	void deleteAll();
	
}
