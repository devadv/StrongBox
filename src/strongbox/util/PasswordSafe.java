package strongbox.util;

import java.security.SecureRandom;

/**
 * This class provides a few support methods for password related tasks.
 * 1) Generate a random password with a specified length.
 * 2) A 'password meter' to give a score to a password indicating it's safety.
 * 
 * @version 09-01-2017
 */
public class PasswordSafe {
	
	/**
	 * A generator for making a random password or passphrase.
	 * @param  length
	 * @return passphrase
	 */
	
	public static String generatePassphrase(int length) {

		String passphrase = new String();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabet_lc = "abcdefghijklmnopqrstuvwxyz";
		String signs = "!@#$%^&*";
		String numbers = "0123456789";
		String totalchars = alphabet + alphabet_lc + signs + numbers;
		SecureRandom random = new SecureRandom();
		int n = totalchars.length();
		for (int i = 0; i < length; i++) {
			passphrase = passphrase + totalchars.charAt(random.nextInt(n));
		}
		return passphrase;
	}
	
	/**
     * Get the password safety score.
     * 
     * Minimum requirements for passwords: 
     *  - length of 8 characters
     * 3/4 of the following:
     *  - Uppercase Letters
     *  - Lowercase Letters
     *  - Numbers
     *  - Symbols
     *   
     * If minimum requirements are not met, additional penalties 
     * to safety score are applied.
     * 
     * Also see http://www.passwordmeter.com/
     * 
     * @param  pw     The password to get the safety score from
	 * @return score  The safety score (0 = very weak), (100 = excellent)
     */
    public static String getScore(String pw) {

        int score = 0; // password safety score (range 0-100)
        int types = 0; // the number of different types of chars (range 0-4)

        int nLowerCase = 0; // the number of lowercase letters found 
        int nUpperCase = 0; // the number of uppercase letters found 
        int nDigit = 0;     // the number of digits (numbers) found 
        int nSymbol = 0;    // the number of symbols (no letters or digits) found

        boolean lowerCaseLetterFound = false;
        boolean upperCaseLetterFound = false;
        boolean digitFound = false;
        boolean symbolFound = false;

        // Minimum requirements
        if (pw.length() < 8) {
            // pw too short
        }

        for (int i = 0; i < pw.length(); i++) {    		
            char ch = pw.charAt(i);    		
            if (Character.isLetter(ch) && Character.isLowerCase(ch)) {
                nLowerCase++;
                if (! lowerCaseLetterFound) {
                    types++;
                }
                lowerCaseLetterFound = true;
            }
            if (Character.isLetter(ch) && Character.isUpperCase(ch)) {
                nUpperCase++;
                if (! upperCaseLetterFound) {
                    types++;
                }
                upperCaseLetterFound = true;
            }
            if (Character.isDigit(ch) && digitFound == false) {
                nDigit++;
                if (! digitFound) {
                    types++;
                }
                digitFound = true;
            }
            if (pw.trim().length() != 0 && Character.isLetter(ch) == false &&
            Character.isDigit(ch) == false) {
                nSymbol++;
                if (! symbolFound) {
                    types++;
                }
                symbolFound = true;
            }
            // password should have at least three of these types
        }

        // Additions to score:
        // Number of Characters Score
        score += pw.length() * 4;

        // Lowercase Letters Score
        score += (pw.length() - nLowerCase) * 2;

        // Uppercase Letters Score
        score += (pw.length() - nUpperCase) * 2;

        // Digits Score
        score += nDigit * 3;

        // Symbols Score
        score += nSymbol * 4;

        // Middle Numbers or Symbols Score
        if (pw.length() >= 3) {
            String sub = pw.substring(1, pw.length() - 2);
            int middleNoS = 0;
            for (int i = 0; i < sub.length(); i++) {
                char ch = sub.charAt(i);
                if (Character.isDigit(ch) || 
                ( ! Character.isDigit(ch) && ! Character.isLetter(ch) ) ) {
                    middleNoS++;
                }
            }
            score += middleNoS *2;
        }

        // Requirements Score (range 0-5)
        int reqScore = 0;
        if (pw.length() >= 8) {
            reqScore++;
        }
        if (lowerCaseLetterFound) {
            reqScore++;
        }
        if (upperCaseLetterFound) {
            reqScore++;
        }
        if (digitFound) {
            reqScore++;
        }
        if (symbolFound) {
            reqScore++;
        }
        score += reqScore * 2;

        // Deductions to score:
        // Letters Only Penalty
        if (! digitFound && ! symbolFound) {
            score -= pw.length();
        }

        // Numbers Only Penalty
        if (! lowerCaseLetterFound && ! upperCaseLetterFound && ! symbolFound) {
            score -= pw.length();
        }

        // Repeat Characters Penalty (Case Insensitive)
        double nRepInc = 0;
        int nRepChar = 0; // number of times a repeat character was found
        int nUnqChar = 0; // number of times a unique character was found
        char[] pwChars = pw.toLowerCase().toCharArray();
        for (int i = 0; i < pwChars.length; i++) {
            boolean jCharExists = false;
            for (int j = 0; j < pwChars.length; j++) {
                if (pwChars[i] == pwChars[j] && i != j) { // repeat character
                    jCharExists = true;
                    /* 
                    Calculate increment deduction based on proximity to 
                    identical characters. Deduction is incremented each time a 
                    new match is discovered. Deduction amount is based on total 
                    password length divided by the difference of distance 
                    between currently selected match.
                     */
                    nRepInc += Math.abs(pwChars.length / (j - i));
                }
            }
            if (jCharExists) { 
                nRepChar++;
                nUnqChar = pwChars.length - nRepChar;
                nRepInc = (nUnqChar > 0) ? Math.ceil(nRepInc/nUnqChar) : Math.ceil(nRepInc);
            }
        }
        score -= (int)nRepInc - nRepChar + 1;

        // Consecutive Lowercase Letters, Uppercase Letters, Numbers and Symbols Penalty
        int cons = 0; // The number of times consecutive characters are found.
        pwChars = pw.toCharArray();
        for (int i = 0; i < pwChars.length; i++) {
            if (i > 0) {
                if (Character.isLetter(pwChars[i]) 
                		&& Character.isLowerCase(pwChars[i]) 
                		&& Character.isLetter(pwChars[i - 1]) 
                		&& Character.isLowerCase(pwChars[i - 1])) {
                    cons++;
                }
                if (Character.isLetter(pwChars[i]) 
                		&& Character.isUpperCase(pwChars[i]) 
                		&& Character.isLetter(pwChars[i - 1]) 
                		&& Character.isUpperCase(pwChars[i - 1])) {
                    cons++;
                }
                if (Character.isDigit(pwChars[i]) 
                		&& Character.isDigit(pwChars[i - 1])) {
                    cons++;
                }
                if (! Character.isLetter(pwChars[i]) 
                		&& ! Character.isDigit(pwChars[i]) 
                		&& ! Character.isLetter(pwChars[i - 1]) 
                		&& ! Character.isDigit(pwChars[i - 1])) {
                    cons++;
                }
            }
        }
        score -= cons * 2;

        // Sequential (3+) Letters (Case Insensitive), Numbers and Symbols Penalty
        StringBuilder sb = new StringBuilder();
        String seq1 = "abcdefghijklmnopqrstuvwxyz";
        String seq2 = sb.append(seq1).reverse().toString();

        sb = new StringBuilder();
        String seq3 = "qwertyuiop";
        String seq4 = sb.append(seq3).reverse().toString();

        sb = new StringBuilder();
        String seq5 = "asdfghjkl";
        String seq6 = sb.append(seq5).reverse().toString();

        sb = new StringBuilder();
        String seq7 = "zxcvbnm";
        String seq8 = sb.append(seq7).reverse().toString();

        sb = new StringBuilder();
        String seq9 = "01234567890";
        String seq10 = sb.append(seq9).reverse().toString();

        sb = new StringBuilder();
        String seq11 = "~!@#$%^&*()_+";
        String seq12 = sb.append(seq11).reverse().toString();

        String[] sequences = {seq1, seq2, seq3, seq4, seq5, seq6, seq7, seq8, seq9, seq10, seq11, seq12};

        String pwLowerCase = pw.toLowerCase();

        int nSequential = 0; // The number of times sequential characters are found.

        for (int i = 0; i < sequences.length; i++) {
            for (int j = 0; j < pwLowerCase.length() && j < sequences[i].length(); j++) {
                if (j > 1) {
                    String s1 = Character.toString(pwLowerCase.charAt(j));
                    String s2 = Character.toString(sequences[i].charAt(j));
                    String s3 = Character.toString(pwLowerCase.charAt(j - 1));
                    String s4 = Character.toString(sequences[i].charAt(j - 1));
                    String s5 = Character.toString(pwLowerCase.charAt(j - 2));
                    String s6 = Character.toString(sequences[i].charAt(j - 2));
                    if (s1.equals(s2) && s3.equals(s4) && s5.equals(s6)) {
                        nSequential++;
                    }
                }
            }
        }
        score -= nSequential * 4;

        // final modifications to score
        double dScore = (double)score * 0.21 * types;
        dScore = Math.ceil(dScore - 1);
        score = (int)dScore;
        
        if (pw.length() < 8) {
        score -= 8 + pw.length(); // pw too short
        }
        
        if (score < 0) {   // Lower cap
        score = 0;
        }
        if (score > 100) { // Upper cap
        score = 100;
        }
        
        String scoreString = "" + score;
        
        // FINALLY!
        return scoreString;
    }
}