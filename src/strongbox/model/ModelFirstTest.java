package strongbox.model;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to read lines of text from a file (the records) and to write them
 * to that file. There are also methods provided to manage the box of records;
 * add new ones, edit them, delete records and so on.
 * 
 * @author Thomas Timmermans
 * @version 15-11-2016
 */

public class ModelFirstTest {

    File file;
    Charset charset;
    ArrayList<String> lineList;
    HashMap<String, String> records;

    /**
     * Constructor for objects of class ReadWrite.
     */
    public ModelFirstTest()	{
        file = new File("res/test.csv");
        charset = Charset.forName("US-ASCII");
        lineList = new ArrayList<>();
        records = new HashMap<>();
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

    /**
     * Print the list to the console.
     */
    public void printList() {
        for (String line: lineList) {
            System.out.println(line);
        }
    }

    /**
     * Create the records in a hashmap.
     */
    public void makeRecords() {
        for (String line: lineList) {
            splitLine(line);
        }
    }

    /**
     * Split the line at the comma, add the first part to records-map as 
     * the name of a site or account and add the second part as the 
     * corresponding password for it.
     */
    public void splitLine(String line) {
        String[] pair = line.split(",");
        records.put(pair[0], pair[1]);
    }

    /**
     * Print the records (the hasmap's entries) to the console.
     */
    public void printRecords() {
        for (Map.Entry<String, String> entry: records.entrySet()) {
            System.out.println(entry);
        }
    }

    /**
     * Add a line of text to the list.
     * @param newLine  The string to be added to the list as a new line.
     */
    public void addLine(String newLine) {
        lineList.add(newLine);
    }

    /**
     * Remove a line from the list.
     * @param index  The indexnumber of the line to remove.
     */
    public void removeLine(int index) {
        lineList.remove(index);
    }

}
