package spelling;

import java.util.ArrayList;

/**
 * @author Inderpreet
 * @date Oct 7, 2014
 */
public class Word {

	private String inputString;
	private String specialCharacters = "!\"# $%&'()*+,-./:;<=>?@[\\]^_`{}~|0123456789\t\n\r\f\b";

	public Word(String term) {
		inputString = term;
	}

	/**
	 * Gets the word
	 *
	 * @return String word
	 */
	public String getWord() {
		return inputString;
	}

	/**
	 * Returns true if word is spelled correctly
	 *
	 * @param dictionary
	 *            ArrayList o words
	 * @return boolean Whether a word is real
	 */
	public boolean isWord(ArrayList<String> dictionary) {
		String fixed = removeSpecial();
		if (fixed.equals("")) {
			return true;
		} else {
			return (dictionary.contains(fixed.toLowerCase()));
		}
	}

	/**
	 * Removes special characters from a string
	 *
	 * @return String without any special characters
	 */
	private String removeSpecial() {
		String fixed = inputString;
		fixed = fixed.trim();
		for (char c : specialCharacters.toCharArray()) {
			fixed = fixed.replace(String.valueOf(c), "");
		}
		return fixed;
	}
}
