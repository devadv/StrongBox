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
			String hash = EncryptingText.encrypt(passwords[i]);	
			encryptedPasswords.add(hash);
		}
		int counter = 1;
		for(String password : encryptedPasswords){
			System.out.println(counter + "." + password);
			counter++;
		}
				
				
				
		}
		
	}

