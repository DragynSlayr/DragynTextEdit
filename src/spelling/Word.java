package spelling;

import java.util.ArrayList;

/**
 * @author Inderpreet
 * @date Oct 7, 2014
 */
public class Word {

	private String input;
	private String toRemove = "!\"# $%&'()*+,-./:;<=>?@[\\]^_`{}~|0123456789\t\n\r\f\b";

	public Word(String term) {
		input = term;
	}

	/**
	 * Gets the word
	 *
	 * @return String word
	 */
	public String getWord() {
		return input;
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
		String fixed = input;
		fixed = fixed.trim();
		for (char c : toRemove.toCharArray()) {
			fixed = fixed.replace(String.valueOf(c), "");
		}
		return fixed;
	}
}
