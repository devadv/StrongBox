package strongbox.test.io.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import strongbox.test.encryption.EncryptionBC;
import strongbox.test.encryption.RecordBC;


public class CSV_IO_Test {

	public static void main(String[] args) {
		
		String passphrase = "PeGsd0T2d1*"
				+ "vaUCMcCaw%jt0B%FxkRUdA"
				+ "2H9nmpg@fRPNTqsssGqTpoU"
				+ "E$BN95bEu32z1HXB12fw*q26P"
				+ "pJ9K6&Uje84d4K0JyyV*$MNnBp"
				+ "L7iJl$GuFeE$Ll#CKpo8&";
		System.out.println(passphrase);
		EncryptionBC enBC = new EncryptionBC(passphrase);
		CSVInputOutput io = new CSVInputOutput();
		io.readFile("res/data.csv");
		ArrayList<RecordBC> recordlist = io.getRecords();
		
		for(RecordBC record: recordlist){
			System.out.println(record);
		}
		
		recordlist.add(new RecordBC("Telfort", "telfort.nl" , "gebruiker" , "123456" , "Providers" , "telefoon en internet"));
		recordlist.add(new RecordBC("KPN", "kpn.nl" , "gebruiker" , "123456" , "Provider" , "mobiel"));
	
		FileWriter writer = null;
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
		}
       
	}
	

}
