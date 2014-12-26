package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Serializer {

	private BufferedWriter writer;

	public Serializer() {
		createWriter();
	}

	/**
	 * Open a bufferedWriter
	 */
	private void createWriter() {
		try {
			writer = new BufferedWriter(
					new FileWriter(new File("settings.cfg")));
		} catch (Exception e) {
			System.out.println("Could not create serializer");
		}
	};

	/**
	 * Output formatted data to a file
	 * 
	 * @param identifier
	 *            The data type, eg. color or font
	 * @param name
	 *            The name of the data
	 * @param value
	 *            The value of the data
	 * @param newLine
	 *            Whether to add a new line
	 */
	public void serialize(String identifier, String name, Object value,
			boolean newLine) {
		try {
			writer.append(identifier + " " + name + ":" + value);
			if (newLine) {
				writer.newLine();
			}
		} catch (Exception e) {
			System.out.println("Could not serialize");
		} finally {
			try {
				writer.flush();
			} catch (Exception e) {
				System.out.println("Could not flush");
			}
		}
	}

	/**
	 * Closes the writer
	 */
	public void close() {
		try {
			writer.close();
		} catch (Exception e) {
			System.out.println("Could not close serializer");
		}
	}
}