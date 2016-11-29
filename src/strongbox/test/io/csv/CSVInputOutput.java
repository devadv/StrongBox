package strongbox.test.io.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import strongbox.model.Record;


public class CSVInputOutput {
	
	private ArrayList<Record> records;
	/**
	 * Read the lines of the file, convert to Record-objects and add them
	 * to the records-list. 
	 * @param path  The path of the file to read from and write to.
	 */
	public void readFile(String path) {

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
	/**
	 * Get the list of records
	 */
	public ArrayList<Record> getRecords() {
		return records;
	}

	/**
	 * Write the records to a file
	 * @param writer file writer
	 * @param records ArrayList of Record
	 */
	public void writeFile(Writer writer, ArrayList<Record> records) {

		for (Record record : records) {
			writeLine(writer, record);
		}
		System.out.println("File saved");
	}
	/*
	 * private method
	 * Converts one object Record to one line of file and write it
	 * @param writer
	 * @param record
	 */
	private static void writeLine(Writer writer, Record record) {
		// Title Address UserName Password Info Folder Note
		String seperator = ",";
		String s = "";
		s += record.getTitle();
		s += seperator;
		s += record.getAddress();
		s += seperator;
		s += record.getUserName();
		s += seperator;
		s += record.getEncryptionpasswd();
		s += seperator;
		s += record.getFolder();
		s += seperator;
		s += record.getNote();
		s += "\n";
		try {
			writer.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
