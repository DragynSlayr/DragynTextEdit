package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Inderpreet
 * @date Oct 8, 2014
 */
public class FileOperations {

	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	/**
	 * Creates the bufferedReader object
	 *
	 * @param file
	 *            The file for the bufferedReader
	 */
	private void createReader(File file) {
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
	}

	/**
	 * Creates the bufferedWriter object
	 *
	 * @param file
	 *            The file for the bufferedWriter
	 */
	private void createWriter(File file) {
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException ex) {
			System.out.println("File not found");
		}
	}

	/**
	 * Reads text from a file
	 *
	 * @param file
	 *            The file to read from
	 * @return The text from the file
	 */
	public String getFileContents(File file) {
		createReader(file);
		String toReturn = "";
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				toReturn += line;
			}
		} catch (IOException ex) {
			System.out.println("Could not read from file");
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException ex) {
				System.out.println("Resource not closed");
			}
		}
		return toReturn;
	}

	/**
	 * Writes text to a file
	 *
	 * @param file
	 *            The file to write to
	 * @param text
	 *            The text to write
	 */
	public void writeData(File file, String text) {
		createWriter(file);
		try {
			bufferedWriter.write(text);
		} catch (IOException ex) {
			System.out.println("Could not write text");
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException ex) {
				System.out.println("Resource not closed");
			}

		}
	}
}
