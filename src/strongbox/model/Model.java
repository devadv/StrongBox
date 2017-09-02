package strongbox.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Observable;

//import java.util.Scanner;
//import java.util.TreeSet;


import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.text.BasicTextEncryptor;


/**
 * A model for managing the application and the handling of records.
 * 
 * @version 02-09-2017
 */

public class Model extends Observable implements iModel {

	private ArrayList<Record> records;
	private String masterpassword;
	private String passphrase;

	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".strongbox");
	private static String path = DATA_STORE_DIR + "/data.csv";


	/**
	 * Constructor for the model.
	 */
	public Model() {
		records = new ArrayList<>();
	}

	/**
	 * Create a new record and add it to the list.
	 */
	@Override
	public void createNewRecord(String title, String address, String userName,
			String password, String folder, String note, long timestamp) {
		
		validate(title, address, userName, password, folder);
		addRecord(new Record(title, address, userName, password, folder, note,
				             timestamp));
		observableChanged();
	}
	
	/**
	 * Marks this Observable object as having been changed then 
	 * notify all of it's observers that this happened.
	 */
	public void observableChanged() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Add a record to the list.
	 * 
	 * @param record  The record to add.
	 */
	@Override
	public void addRecord(Record record) {
		records.add(record);
	}
	
	/**
	 * Validate the arguments when a record is created or when editing is done.
	 */
	public void validate(String title, String address, String userName,
			String password, String folder) {

		for (Record record: records) {
			if (title.trim().toLowerCase().equals(record.getTitle().trim().toLowerCase())
					&& folder.trim().toLowerCase().equals(record.getFolder().toLowerCase())) {
				// Duplicate title within the same folder
				throw new IllegalArgumentException();
			}
		}
		emptyFieldsCheck(title, address, userName, password, folder);		
	}
	
	/**
	 * Validate the attempted creation or editing of a record by checking for 
	 * empty fields and throwing an exception if empty fields are found.
	 * 
	 * @throws IllegalArgumentException if one of the arguments is an empty string
	 */
	public void emptyFieldsCheck(String title, String address, String userName,
			String password, String folder) {
		
		if (title.trim().length() == 0 || address.trim().length() == 0 ||
				userName.trim().length() == 0 || password.trim().length() == 0
				|| folder.trim().length() == 0) {
			    // The note-field is permitted to be empty so it isn't checked
			    throw new IllegalArgumentException();
		}
	}

	/**
	 * Delete a record from the list.
	 * 
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
	 * 
	 * @return The record-list.
	 */
	@Override
	public ArrayList<Record> getRecordList() {
		return records;
	}
	
	/**
	 * 
	 * @param records
	 */
	public void setRecordList(ArrayList<Record> records){
		this.records = records;
	}

	/**
	 * Get a list of records whose title, address, note or folder attribute 
	 * contains (parts of) the keyword searched with.
	 * Sort this list before returning.
	 * 
	 * @param keyword  The keyword to search for.
	 * @return A sorted list of matching records.
	 */
	@Override
	public ArrayList<Record> search(String keyword) {
		ArrayList<Record> recordsByKeyword = new ArrayList<>();
		keyword = keyword.trim().toLowerCase();
		for (Record record : records) {
			if (record.getTitle().trim().toLowerCase().contains(keyword)
					|| record.getAddress().trim().toLowerCase().contains(keyword)
					|| record.getNote().trim().toLowerCase().contains(keyword)
					|| record.getFolder().trim().toLowerCase().contains(keyword)) {
				recordsByKeyword.add(record);
			}
		}
		Collections.sort(recordsByKeyword);
		return recordsByKeyword;
	}

	/**
	 * Make a list of records associated with the specified folder name,
	 * sort the list and then return it.
	 * @param folder  The folder's name.
	 * @return The sorted list of records.
	 */
	@Override
	public ArrayList<Record> getRecordsByFolder(String folder) {
		ArrayList<Record> recordsByFolder = new ArrayList<>();
		for (Record record : records) {
			if (record.getFolder().equals(folder)) {
				recordsByFolder.add(record);
			}
		}
		Collections.sort(recordsByFolder);
		return recordsByFolder;
	}

	/**
	 * Get a list with all unique folder names found in the records-list.
	 * Then sort this list (ignoring case differences) and return it. 
	 * 
	 * @return  The sorted list with unique folder names.
	 */
	@Override
	public ArrayList<String> getFolders() {
		
		HashSet<String> folderNameSet = new HashSet<>();
		for (Record record : records) {
			folderNameSet.add(record.getFolder());
		}
		
		ArrayList<String> folderNames = new ArrayList<>();
		for (String folderName : folderNameSet) {
			folderNames.add(folderName);
		}
		
		Collections.sort(folderNames, new Comparator<String>() {
		    @Override
		    public int compare(String s1, String s2) {              
		        return s1.compareToIgnoreCase(s2);
		    }
		});
		
		return folderNames;
	}

	/**
	 * Set the fields of a record to the values provided by an array of strings.
	 * @param record   The record whose fields will be set.
	 * @param fields   The array to set the record's fields.
	 * @throws IllegalArgumentException if user left one of the fields empty.
	 */
	public void setRecordFields(Record record, String[] fields) {
			
		// less than 5 because the note-field is permitted to be empty
		for (int i = 0; i < 5; i++) {
			if (fields[i].trim().length() == 0) {
				throw new IllegalArgumentException();
			}
		}
		
		record.setTitle(fields[0]);
		record.setAddress(fields[1]);
		record.setUserName(fields[2]);
		record.setPassword(fields[3]);
		record.setFolder(fields[4]);
		record.setNote(fields[5]);
	}
	
    /**
     * Create and return an array of strings representing the values of the 
     * fields from this record.
     * @param record   The record to get the fields from.
     * @throws NullPointerException if record is null. The possible throwing of
     *         this (unchecked) exception is absolutely necessary for the
     *         RecordListener from controller-class, which calls this method,
     *         to function correctly. 
     * @return An array with the values of the fields.
     */
    public String[] getRecordFields(Record record) throws NullPointerException {
    	return new String[] {record.getTitle(), record.getAddress(), 
    			record.getUserName(), record.getPassword(), 
    			record.getFolder(), record.getNote()};
    }
    
	// --- Password settings ---
	/**
	 * Set the master password for access and encryption.
	 * 
	 * @param password  The password to set the first time using StrongBox.
	 */
	@Override
	public void setMasterPassword(String password) {
		this.masterpassword = password;
	}
	
	/**
	 * Get the master password.
	 * @return The master password.
	 */
	public String getMasterpassword() {
		return masterpassword;
	}

	/**
	 * Set the password phrase for encryption
	 * 
	 * @param passphrase  The password phrase to set.
	 */
	@Override
	public void setPassPhraseEncryption(String passphrase) {
		this.passphrase = passphrase;
	}
	
	/**
	 * Get the password phrase.
	 * @return The password phrase.
	 */
	public String getPassphrase() {
		return passphrase;
	}

	// --- Input from or output to file ---
	/**
	 * Read or retrieve the records from a file.
	 */
	@Override
	public void readRecordsFromFile() {
		
		readFile(path);
	}

	/**
	 * Read the lines of the file, convert to Record-objects and add them to the
	 * records-list.
	 * 
	 * @param path   The path of the file to read from and write to.
	 */
	private void readFile(String path) {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		records = new ArrayList<>();  // NECESSARY ????????????????????????
		File file = new File(path);
		if(file.exists()){
			try {
				br = new BufferedReader(new FileReader(path));
				
				while ((line = br.readLine()) != null) {
					
					// separator
					String[] fields = line.split(cvsSplitBy);
					String[] item = new String[7];
					for (int i = 0; i < fields.length; i++) {
						item[i] = fields[i];
					}
					
					if (fields.length == 6) { // Note was left empty by user
						
					 /* In this case the timestamp comes one entry 'too early'
						in the 'item' array, taking the place of where note 
						should be. So the timestamp-value is assigned to the 
						next entry of the array and an empty string is assigned 
						to the entry where the timestamp-value came from (the 
						spot where note should be after all). */
						item[6] = item[5];
						item[5] = "";
					}
					
					long timestamp = Long.parseLong(item[6]);
					createNewRecord(item[0], item[1], item[2], item[3], item[4], item[5], timestamp);
				}
			}
	
			catch (FileNotFoundException e) {
				System.out.println(" File not found " + e);		
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{ 
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("File data.csv could not be created");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Save the records to a file.
	 */
	@Override
	public void writeRecordsToFile() {
		FileWriter writer = null;
		
			try {
				writer = new FileWriter(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writeFile(writer, getRecordList());
			
			
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	/**
	 * Write the records to a file.
	 * @param writer  FileWriter
	 * @param arrayList  ArrayList of Record
	 */
	private void writeFile(Writer writer, ArrayList<Record> arrayList) {

		for (Record record : arrayList) {
			writeLine(writer, record);
		}
	}
	
	/**
	 * Converts a Record to one line of text and writes it to file.
	 * @param writer  FileWriter
	 * @param record  The record to write as a line.
	 */
	private static void writeLine(Writer writer, Record record) {
		// Title Address UserName Password Info Folder Note Timestamp
		String separator = ",";
		String s = "";
		s += record.getTitle();
		s += separator;
		s += record.getAddress();
		s += separator;
		s += record.getUserName();
		s += separator;
		s += record.getEncryptionpasswd();
		s += separator;
		s += record.getFolder();
		s += separator;
		s += record.getNote();
		s += separator;
		s += Long.toString(record.getTimestamp());
		s += "\n";
		try {
			writer.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Read properties file and setPassPhrase.
	 */	 
	public void readProperties() {

		File file = new File(DATA_STORE_DIR +"/config.passphrase.properties");

		BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
		stringEncryptor.setPassword(getMasterpassword());
		EncryptableProperties prop = new EncryptableProperties(stringEncryptor);

		InputStream input;
		try {
			input = new FileInputStream(file);
			prop.load(input);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setPassPhraseEncryption(prop.getProperty("passphrase"));
	}
	
	public boolean isDrive(){
		
		File file = new File(DATA_STORE_DIR +"/config.properties");
		BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
		stringEncryptor.setPassword(getMasterpassword());
		EncryptableProperties prop = new EncryptableProperties(stringEncryptor);
		InputStream input;
		try {
			input = new FileInputStream(file);
			prop.load(input);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(prop.getProperty("setgoogledrive").equals("on")){
			return true;
		}
		else {
			System.out.println("Drive Connection is set to off");
			return false;
		}	
	}

}
