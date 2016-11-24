package strongbox.test;


import strongbox.test.encryption.EncryptionBouncyCastle;
import strongbox.test.io.serialization.TextFileIO;
import strongbox.view.GUI;
import strongbox.model.Record;


public class MainTest{
	
	
	public static void main(String[] args) {

		/*System.out.println("StrongBox Development started");
		System.out.println("======Starting Encryption=======");
		String s = "Example text to be encrypted";
		System.out.println("encrytion text: " + s);
		String eText = EncryptingText.encrypt(s);
		System.out.println("Encrypted text will be decrypted: " + eText );
		System.out.println("======Starting Decryption=======");
		System.out.println("Decrypt: " + EncryptingText.decrypt(eText));
		*/

        TextFileIO test = new TextFileIO();
        test.readFile();
        test.makeRecords();
        for(Record r :test.getRecordList()){
        	System.out.println(r.toString());
        	System.out.println("===============================================");
        }
        System.out.println("" + test.recordListSize());
        
        GUI guiTest = new GUI();

	}
	
}
