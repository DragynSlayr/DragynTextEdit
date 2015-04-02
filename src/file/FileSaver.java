package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaver {

	private File startingFile;

	public FileSaver(File file) {
		this.startingFile = file;
		try {
			startingFile.delete();
			startingFile.createNewFile();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public void save(String text, int offset) {
		try {
			File tempFile = new File("temp");

			// Open streams
			FileInputStream startingFileInputStream = new FileInputStream(
					startingFile);
			FileOutputStream tempFileOutputStream = new FileOutputStream(
					tempFile);

			// Write the beginning portion of the starting startingFile to the
			// temp startingFile
			for (int i = 0; i < offset; i++) {
				tempFileOutputStream.write(startingFileInputStream.read());
			}

			// Write the text to the temp startingFile
			tempFileOutputStream.write(text.getBytes());

			// Write the end part of the starting startingFile to the temp
			// startingFile
			for (int j = startingFileInputStream.available(); j > 0; j--) {
				tempFileOutputStream.write(startingFileInputStream.read());
			}

			// Close and flush streams
			startingFileInputStream.close();
			tempFileOutputStream.flush();
			tempFileOutputStream.close();

			// Open streams
			FileInputStream tempFileInputStream = new FileInputStream(tempFile);
			FileOutputStream finalFileOutputStream = new FileOutputStream(
					startingFile);

			int availableBytes = tempFileInputStream.available();

			// Write all of the temp startingFile to the starting startingFile
			for (int c = 0; c < availableBytes; c++) {
				finalFileOutputStream.write(tempFileInputStream.read());
			}

			// Close and flush streams
			tempFileInputStream.close();
			finalFileOutputStream.flush();
			finalFileOutputStream.close();

			// Delete the temp startingFile when the program stops
			tempFile.deleteOnExit();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
}
