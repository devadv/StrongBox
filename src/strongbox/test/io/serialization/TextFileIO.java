package strongbox.test.io.serialization;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.ArrayList;

import strongbox.model.Record;

/**
 * A class to read lines of text from a file (the records' attributes) and to 
 * create the record objects based on the info found in this file.
 * 
 * "The file" should be a text-file (.csv here actually) with
 * seven strings separated by comma's. So in a sense this is a self-defined 
 * serialization protocol requiring and providing files in this specific format.
 * 
 * Vice versa this class should be able to 'turn back' a list of record objects
 * to a list with strings (where each string is made up of seven substrings
 * separated by comma's).
 * 
 * Finally, the class should be able to write the 'lineList' containing
 * the formatted strings to the file.
 * 
 * This class does NOT require the Record class to implement 
 * interface Serializable!
 * 
 * @author Thomas Timmermans
 * @version 16-11-2016
 */

public class TextFileIO {

    private File file;
    private Charset charset;
    private ArrayList<String> lineList;
    private ArrayList<Record> records;

    /**
     * Constructor
     */
    public TextFileIO()	{
        file = new File("res/recordListSevenFields.csv");
        charset = Charset.forName("US-ASCII");
        lineList = new ArrayList<>();
        records = new ArrayList<>();
    }

    /**
     * Read the file and add the lines of text found to the linelist.
     */
    public void readFile() {
        try (BufferedReader reader = 
            Files.newBufferedReader(file.toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    
    /**
     * Make the records by processing the lines of text, then add 
     * the records to the list.
     */
    public void makeRecords() {
    	for (String line: lineList) {
    		String[] prop = line.split(",");
            Record record = new Record(prop[0], prop[1], prop[2], prop[3],
            		prop[4], prop[5]);
            records.add(record);
    	}
    }

    // A method to turn the records' attributes back into lines of text
    // does not yet exist! (18-11-2016).
    
    /**
     * Write the current contents of the list to the file (each record on 
     * a new line).
     */
    public void writeFile() {
        try  (BufferedWriter writer = 
            Files.newBufferedWriter(file.toPath(), charset)) {
            for (int i = 0; i < lineList.size(); i++) {
                String line = lineList.get(i);
                writer.write(line, 0, line.length());
                /* the if-statement below prevents creating a blank line 
                 * at the end of the file. */
                if(i < lineList.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    
    // Check size. Used for testing purposes.
    public int recordListSize() {
    	return records.size();
    }
    public ArrayList<Record> getRecordList(){
    	return records;
    }
}
