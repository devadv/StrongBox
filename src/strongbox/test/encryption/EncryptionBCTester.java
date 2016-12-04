package strongbox.test.encryption;

public class EncryptionBCTester {

	public static void main(String[] args) {
		
		EncryptionBC enBC = new EncryptionBC("passphraseStrong@3$!");
		
		String text = "ictacademie";
		
		String encryptedText = EncryptionBC.encrypt(text);
		System.out.println(encryptedText);
		
		String decryptedText = EncryptionBC.decrypt(encryptedText);
		System.out.println(decryptedText);
		
		RecordBC  rec = new RecordBC("Telfort", "telfort.nl", "ansems", "ictacademie", "Provider","wifi code" );
		//xQp2/SIRuD1vhXwtM9nldaHKESV96dVJVflKp6R7WUE=
		RecordBC rec1 = new RecordBC("Telfort", "telfort.nl", "ansems", "hash_IOwxqeY/V1okFjYQS3joIY3CyiQH9z5tRK4LRUlFaMk=", "Provider","wifi code" );
		
		System.out.println(rec.toString());
		System.out.println(rec1.toString());

	}

}
