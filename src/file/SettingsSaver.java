package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SettingsSaver {

	public static final String ASSIGNER = " = ";
	public static final String CORRECT_COLOR = "Correct Color";
	public static final String FONT_SIZE = "Font Size";
	public static final String FONT_STYLE = "Font Style";
	public static final String FONT_TYPE = "Font Type";
	public static final String INCORRECT_COLOR = "Incorrect Color";
	public static final String SEPARATOR = " ";
	private BufferedWriter writer;
	private final String FILENAME = "DragynTextEdit.cfg";

	public SettingsSaver() {
		createWriter();
	}

	/**
	 * Closes the writer
	 */
	public void close() {
		try {
			writer.close();
		} catch (Exception e) {
			System.out.println("Could not close saver");
		}
	};

	/**
	 * Open a bufferedWriter
	 */
	private void createWriter() {
		try {
			writer = new BufferedWriter(
					new FileWriter(new File(FILENAME)));
		} catch (Exception e) {
			System.out.println("Could not create saver");
		}
	}

	/**
	 * Output formatted data to a file
	 * 
	 * @param name
	 *            The name of the data
	 * @param value
	 *            The value of the data
	 * @param newLine
	 *            Whether to add a new line
	 */
	public void format(String name, Object value, boolean newLine) {
		try {
			writer.append(name + ASSIGNER + value);
			if (newLine) {
				writer.newLine();
			}
		} catch (Exception e) {
			System.out.println("Could not save");
		} finally {
			try {
				writer.flush();
			} catch (Exception e) {
				System.out.println("Could not flush");
			}
		}
	}
}