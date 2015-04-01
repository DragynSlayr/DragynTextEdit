package spelling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Inderpreet
 */
public class Dictionary {

	private final ArrayList<String> dictionary;
	private BufferedReader bufferedReader;

	public Dictionary() {
		dictionary = new ArrayList<String>();
		initializeReader();
		readFromFile();
	}

	/**
	 * Gets the dictionary
	 *
	 * @return the dictionary
	 */
	public ArrayList<String> getDictionary() {
		return dictionary;
	}

	/**
	 * Initializes the bufferedReader variable
	 */
	private void initializeReader() {
		InputStream inputStream = getClass().getResourceAsStream("dict.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * Reads strings from a file
	 *
	 * @return ArrayList the list of words from the file
	 */
	private void readFromFile() {
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				dictionary.add(line);
			}
		} catch (IOException ex) {
			System.out.println("Could not load dictionary");
		}
	}
}
