package strongbox.test;

import java.util.Arrays;

import org.jasypt.util.text.StrongTextEncryptor;

import strongbox.model.Model;
import strongbox.test.encryption.Encryption;

public class TestModel {

	public static void main(String[] args) {
		
		Encryption encryption = new Encryption("hx8&2RlYz2rqn&N^oiyKZG#35&P1RMkQ");
		StrongTextEncryptor enc = new StrongTextEncryptor();
		enc.setPassword("12345");
		
		Model model = new Model();
		model.setMasterPassword("12345");
		model.readRecordsFromFile();
		//model.writeRecordsToFile();
		model.readProperties();
		//Password and Passphrase
		System.out.println("---------------Password and PassPhrase-----------------------");
		System.out.println(model.getMasterpassword());
		System.out.println(model.getPassphrase());
		System.out.println(enc.decrypt(model.getPassphrase()));
		//Folders
		System.out.println("--------------------Folders----------------------------------");
		System.out.println(model.getFolders());
		//getTitlesByFolder
		System.out.println("--------------------get Title By Folder----------------------");
		System.out.println(model.getTitlesByFolder("Provider"));
		//getTitlesByFolder
		System.out.println("--------------------get Title By Folder----------------------");
		System.out.println(model.getTitlesByFolder("Online Winkelen"));
		//search
		System.out.println("---------------------search keyword--------------------------");
		System.out.println(model.search("geld"));
		//getRecord
		System.out.println("--------------------get a Record form title------------------");
		System.out.println(model.getRecord("Sting"));
		//getRecordList
		System.out.println("------------------------getRecordList------------------------");
		System.out.println(model.getRecordList());
		System.out.println("--------------------add Record Bol---------------------------");
		model.createNewRecord("Bol" , "bol.com", "devries", "12345678", "Online Winkelen", "Boeken");
		//getRecord
		System.out.println("--------------------get a Record form title------------------");
		System.out.println(model.getRecord("Bol"));
	}

}
