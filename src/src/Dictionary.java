package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 *
 * @author Inderpreet
 */
public class Dictionary {

	private final ArrayList<String> dictionary;
	private BufferedReader reader;

	public Dictionary() {
		dictionary = new ArrayList<String>();
		initializeReader();
		readFromFile();
	}

	/**
	 * Reads strings from a file
	 *
	 * @return ArrayList the list of words from the file
	 */
	private void readFromFile() {
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				dictionary.add(line);
			}
		} catch (IOException ex) {
			System.out.println("Could not load dictionary");
		}
	}

	/**
	 * Initializes the reader variable
	 */
	private void initializeReader() {
		try {
			reader = new BufferedReader(new FileReader(new File(getClass()
					.getResource("dict.txt").toURI())));
		} catch (FileNotFoundException ex) {
			System.out.println("Could not find dict.txt");
		} catch (URISyntaxException urise) {
			System.out.println(urise.getMessage());
		}
	}

	/**
	 * Gets the dictionary
	 *
	 * @return the dictionary
	 */
	public ArrayList<String> getDictionary() {
		return dictionary;
	}
}
