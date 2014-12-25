package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Serializer {

	private BufferedWriter writer;

	public Serializer() {
		createWriter();
	}

	private void createWriter() {
		try {
			writer = new BufferedWriter(
					new FileWriter(new File("settings.cfg")));
		} catch (Exception e) {
			System.out.println("Could not create serializer");
		}
	};

	public void serialize(String identifier, String name, Object value) {
		try {
			writer.append(identifier + " " + name + ":" + value);
			writer.newLine();
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

	public void close() {
		try {
			writer.close();
		} catch (Exception e) {
			System.out.println("Could not close serializer");
		}
	}
}