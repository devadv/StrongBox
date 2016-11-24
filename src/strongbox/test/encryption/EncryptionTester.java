package strongbox.test.encryption;

import java.util.ArrayList;




public class EncryptionTester {

	public static void main(String[] args) {
		
		ArrayList<String> encryptedPasswords = new ArrayList<>();
		String [] passwords = {
				"p-gktXAbGKyzT3Dx",
				"hgcPf#&S*yy@F2jW",
				"3xJKA$z*cBEzffWx",
				"!h+xQFND5tPq",
				"HeU%-re?2Byf",
				"z87_AvTa#=FA",
				"ictacademie",
				"klaasv44k",
				"roderijst12"};
		
		for(int i= 0;i<passwords.length;i++){
			String hash = EncryptionBouncyCastle.encrypt(passwords[i]);	
			encryptedPasswords.add(hash);
		}
		
		for(String password : encryptedPasswords){
			System.out.println("hash_" +password);
		
		}
				
		RecordBC record = new RecordBC("Telfort", "telfort.nl", "ansems", "ictacademie", "Provider","wifi code" );
		RecordBC record1 = new RecordBC("Telfort", "telfort.nl", "ansems", "hash_MwVFtKMakf75SsPqS2zgGypV18X4vA//PH64enKTVXI=", "Provider","wifi code" );
		
		System.out.println(record.getPassword());
	    System.out.println(record.getEncryptionpasswd());
	    System.out.println(record);

		System.out.println(record1.getPassword());
	    System.out.println(record1.getEncryptionpasswd());
	    System.out.println(record1);		
		}
		
	}

