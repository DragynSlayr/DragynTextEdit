package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

	private File file;
	private FileInputStream inputStream;

	public FileLoader(File file) {
		this.file = file;
		openFileReader();
	}

	/**
	 * Creates file reader
	 */
	private void openFileReader() {
		try {
			inputStream = new FileInputStream(file);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	/**
	 * Reads a section of a file
	 * 
	 * @param length
	 *            The amount to read
	 * @return The read section
	 */
	public String readChunk(int length) {
		try {
			byte[] bytes = new byte[length];
			inputStream.read(bytes, 0, bytes.length);
			return new String(bytes);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return "";
	}

	/**
	 * Gets the number of lines in the file
	 * 
	 * @return Lines in the file
	 */
	public int getLines() {
		int lines = 0;
		try {
			BufferedReader buffered = new BufferedReader(new FileReader(file));
			while (buffered.readLine() != null) {
				lines++;
			}
			buffered.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return lines;
	}

	/**
	 * Sets the name of the file
	 * 
	 * @param file
	 *            The file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Closes streams
	 */
	public void close() {
		try {
			inputStream.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	/**
	 * Gets the number of bytes closest to the input
	 * 
	 * @param input
	 *            The input bytes
	 * @return The number of bytes
	 */
	public int getMinLength(int input) {
		int count = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if (count >= input) {
					reader.close();
					return count;
				} else {
					count += line.length();
				}
			}
			reader.close();
			return count;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return count;
	}
}
