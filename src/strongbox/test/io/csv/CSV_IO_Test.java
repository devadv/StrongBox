package strongbox.test.io.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import strongbox.model.Record;

public class CSV_IO_Test {

	public static void main(String[] args) {
		CSVInputOutput io = new CSVInputOutput();
		io.readFile("res/data.csv");
		ArrayList<Record> recordlist = io.getRecords();
		
		for(Record record: recordlist){
			System.out.println(record);
		}
		
		//recordlist.add(new Record("Telfort", "telfort.nl" , "gebruiker" , "123456" , "Providers" , "telefoon en internet"));
		//recordlist.add(new Record("KPN", "kpn.nl" , "gebruiker" , "123456" , "Provider" , "mobiel"));
		
		/*FileWriter writer = null;
		try {
			writer = new FileWriter("res/data.csv");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		io.writeFile(writer, recordlist);
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
       
	}
	

}
