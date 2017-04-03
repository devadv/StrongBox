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
import java.util.HashSet;

import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

import strongbox.test.encryption.RecordBC;
import strongbox.test.login.mvc.PropertiesModel;

/**
 * A model for managing the application and the handling of records.
 */

public class Model implements iModel {

	private ArrayList<Record> records;
	private String masterpassword;
	private String passphrase;

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
			String password, String folder, String note) {
		addRecord(new Record(title, address, userName, password, folder, note));
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
	 * Get a record based on it's title.
	 * 
	 * @param title  The record's title.
	 * @return The corresponding record.
	 */
	@Override
	public Record getRecord(String title) {
		Record rec = null;
		for (Record record : records) {
			if (record.getTitle().equals(title)) {
				rec = record;
			}
		}
		return rec;
	}

	/**
	 * Get a list of record titles containing (parts of) the keyword. To create 
	 * the list searching is done not only by title but address, note and folder
	 * attributes are also being searched.
	 * 
	 * @param keyword  The keyword to search for.
	 * @return A list of matching record titles.
	 */
	@Override
	public ArrayList<String> search(String keyword) {
		ArrayList<String> titlesByKeyword = new ArrayList<>();
		for (Record record : records) {
			if (record.getTitle().contains(keyword)
					|| record.getAddress().contains(keyword)
					|| record.getNote().contains(keyword)
					|| record.getFolder().contains(keyword)) {
				titlesByKeyword.add(record.getTitle());
			}
		}
		return titlesByKeyword;
	}

	/**
	 * Returns a list of record titles associated with the specified folder 
	 * name.
	 * @param folder  The folder's name.
	 * @return The list of record titles.
	 */
	@Override
	public ArrayList<String> getTitlesByFolder(String folder) {
		ArrayList<String> titlesByFolder = new ArrayList<>();
		for (Record record : records) {
			if (record.getFolder().equals(folder)) {
				titlesByFolder.add(record.getTitle());
			}
		}
		return titlesByFolder;
	}

	/**
	 * Get a set with the folder names (it's a set so duplicate names are not to
	 * be found here).
	 * 
	 * @return The set with folder names.
	 */
	@Override
	public HashSet<String> getFolders() {
		HashSet<String> folderNames = new HashSet<>();
		for (Record record : records) {
			folderNames.add(record.getFolder());
		}
		return folderNames;
	}

    /**
     * Create and return an array of strings representing the values of the 
     * fields from a record.
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
		readFile("res/data.csv");
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
		records = new ArrayList<>();

		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {

				// separator
				String[] item = line.split(cvsSplitBy);
				Record record = new Record(item[0], item[1], item[2], item[3],
						item[4], item[5]);
				records.add(record);
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
	}
	public void writeRecordsToFile(ArrayList<Record> records) {
		this.records = records;
		writeRecordsToFile();
	}

	/**
	 * Save the records to a file.
	 */
	@Override
	public void writeRecordsToFile() {
		FileWriter writer = null;
		
			try {
				writer = new FileWriter("res/data.csv");
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
		System.out.println("File saved");
	}
	/**
	 * private method
	 * Converts one object Record to one line of file and write it.
	 * @param writer  FileWriter
	 * @param record  The record to write as a line.
	 */
	private static void writeLine(Writer writer, Record record) {
		// Title Address UserName Password Info Folder Note
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
		s += "\n";
		try {
			writer.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readProperties() {

		File file = new File("res/config.properties");

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
}