package strongbox.model;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.ArrayList;

/**
 * A class to read lines of text from a file (the records) and to write them
 * to that file. There are also methods provided to manage the box of records;
 * add new ones, edit them, delete records and so on.
 * 
 * @author Thomas Timmermans
 * @version 16-11-2016
 */

public class RecordManager {

    private File file;
    private Charset charset;
    private ArrayList<String> lineList;
    private ArrayList<Record> records;

    /**
     * Constructor for objects of class RecordManager.
     */
    public RecordManager()	{
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
            		prop[4], prop[5], prop[6]);
            records.add(record);
    	}
    }

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
    
    public int recordListSize() {
    	return records.size();
    }
}
