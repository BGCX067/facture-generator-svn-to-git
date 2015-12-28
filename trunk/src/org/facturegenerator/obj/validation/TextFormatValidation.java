package org.facturegenerator.obj.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormatValidation {

	/**
	 * Checks if string contains characters other than
	 * digit. 
	 * @param str
	 * @return <code>true</code> if string contains other characters
	 */
	public static boolean containsNumbers(String str) {
		Pattern p = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return !m.find();
	}
	
	/**
	 * Checks if string contains characters other than
	 * digit or space. 
	 * @param str
	 * @return <code>true</code> if string contains other characters
	 */
	public static boolean containsNumbersAndSpaces(String str) {
		Pattern p = Pattern.compile("[^0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return !m.find();
	}
	
	/**
	 * Checks if string is correct bank account number:
	 * xx xxxx xxxx xxxx xxxx xxxx xxxx.
	 * @param str
	 * @return <code>true</code> if string is correct bank account number
	 */
	public static boolean isBankAccountNumber(String str) {
		if(str.length() != 32) return false;
		char ch[] = str.toCharArray();
		for(int i = 0; i < ch.length; i++) {
			if(i == 2 || i == 7 || i == 12 || i == 17 ||
					i == 22 || i == 27) {
				if(ch[i] != ' ') return false;
			} else {
				if(!containsNumbers(new String(""+ch[i]))) return false;
			}
		}
		return true;
	}
	
}
