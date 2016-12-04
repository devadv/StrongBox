package strongbox.test.encryption;

import java.util.ArrayList;




public class EncryptionTester {

	public static void main(String[] args) {
		EncryptionBC  enBC = new EncryptionBC("spongbob34");
		
		ArrayList<String> encryptedPasswords = new ArrayList<>();
		String [] passwords = {
				"z87_AvTa#=FA",
				"klaasv44k",
				"roderijst12",
				"ictacademie",
				"donaldduck7",
				"Welkom01"};
		
		for(int i= 0;i<passwords.length;i++){
			//String hash = EncryptingText.encrypt(passwords[i]);
			String hash = EncryptionBC.encrypt(passwords[i]);	
			encryptedPasswords.add(hash);
		}
		
		for(String password : encryptedPasswords){
			System.out.println(password);
		
		}
				
		RecordBC record = new RecordBC("Telfort", "telfort.nl", "ansems", "ictacademie", "Provider","wifi code" );
		RecordBC record1 = new RecordBC("Telfort", "telfort.nl", "ansems", "hash_YsS/xcDqpwoU72aU2+NpEGzUTgt9WctlEe+ngdWit04=", "Provider","wifi code" );
		
		System.out.println(record.getPassword());
	    System.out.println(record.getEncryptionpasswd());
	    System.out.println(record);

		System.out.println(record1.getPassword());
	    System.out.println(record1.getEncryptionpasswd());
	    System.out.println(record1);		
		}
		
	}

