package strongbox.test.io.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import strongbox.model.Record;

public class CSV_IO_Test {

	public static void main(String[] args) {
		
		ArrayList<Record> recordlist = CSVInputOutput.readFile("res/recordListSevenFields.csv");
		
		for(Record record: recordlist){
			System.out.println(record.toString());
		}
		
		recordlist.add(new Record("Telfort", "telfort.nl" , "gebruiker" , "123456" , "test Info", "Providers" , "telefoon en internet"));
		recordlist.add(new Record("KPN", "kpn.nl" , "gebruiker" , "123456" , "test Info", "Provider" , "mobiel"));
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("res/recordListSevenFields.csv");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CSVInputOutput.writeFile(writer, recordlist);
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	

}
