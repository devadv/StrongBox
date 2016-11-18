package strongbox.model;

	import java.io.ObjectOutputStream;
	import java.io.FileOutputStream;
	import java.io.ObjectInputStream;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;

	/**
	 * Reading and writing a binary file with
	 * input and output streams.
	 * 
	 * This class DOES require the Record class to implement 
	 * interface Serializable!
	 * 
	 * @author Thomas Timmermans
	 * @version 17-11-2016
	 */
	public class BinaryFileIO {

	    // instance variables
	    String file;
	    ObjectOutputStream output;
	    ObjectInputStream input;
	    Record record; // test record

	    /**
	     * Constructor
	     */
	    public BinaryFileIO() {
	        // initialise instance variables
	        file = "res/data.bin";
	        record = new Record("a", "b", "c", "d", "e", "f", "g");
	    }

	    /**
	     * Write to file.
	     */
	    public void writeFile() {
	        try {
	            output = new ObjectOutputStream(new FileOutputStream(file));
	            output.writeObject(record); // write object to file
	            output.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Read from file.
	     */
	    public void readFile() {
	        try {
	            input = new ObjectInputStream(new FileInputStream(file));
	            record = (Record)input.readObject(); // read object from file
	            input.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	}
