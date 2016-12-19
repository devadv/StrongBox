package strongbox.controller;

import strongbox.model.Model;
import strongbox.model.Record;
import strongbox.encryption.Encryption;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;


public class AppController {

	public AppController(Model model) {
		model.readProperties();
		String masterpassword = model.getMasterpassword();
		System.out.println("masterpass " +masterpassword);
		Encryption enMaster = new Encryption(masterpassword);
		String encryptedpassphrase = model.getPassphrase();
		String decryptedpassphrase = enMaster.decrypt(encryptedpassphrase);
		System.out.println(encryptedpassphrase);
		System.out.println(decryptedpassphrase);
		Encryption enPassphrase = new Encryption(decryptedpassphrase);
		model.readRecordsFromFile();
		//System.out.println(model.getRecordList());
		for (Record rec :model.getRecordList()){
			
			System.out.printf("%s,%s,%s,%s,%s,%s\n" 
					,rec.getTitle() 
					,rec.getAddress()
					,rec.getUserName()
					,rec.getPassword()
					,rec.getNote()
					,rec.getFolder());
			
		}
		
		
		
		
		
		
	}
}
