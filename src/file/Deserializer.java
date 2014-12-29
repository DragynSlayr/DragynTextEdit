package file;

import gui.GUI;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Deserializer {

	private BufferedReader reader;

	public Deserializer() {
		createReader();
	}

	/**
	 * Creates a bufferedReader
	 */
	private void createReader() {
		try {
			reader = new BufferedReader(
					new FileReader(new File("settings.cfg")));
		} catch (Exception e) {
			System.out.println("Could not create deserializer");
		}
	}

	/**
	 * Gets information back from a file
	 */
	public void deserialize() {
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(":");
				String identifier = split[0].split(" ")[0];
				String name = split[0].split(" ")[1];
				switch (identifier) {
				case "color":
					String[] rgb = split[1].split("_");
					Color color = getColor(rgb);
					if (name.equalsIgnoreCase("correct")) {
						GUI.textField.setCorrectColor(color);
					} else {
						GUI.textField.setIncorrectColor(color);
					}
					break;
				case "font":
					String[] fontInfo = split[1].split("_");
					Font font = getFont(fontInfo);
					GUI.textField.setFont(font);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Could not deserialize");
		}
	}

	/**
	 * Gets a color from a String array of red, green and blue
	 * 
	 * @param rgb
	 *            The String array of red, green and blue
	 * @return The color represented by the String array
	 */
	private Color getColor(String[] rgb) {
		int r = Integer.parseInt(rgb[0]);
		int g = Integer.parseInt(rgb[1]);
		int b = Integer.parseInt(rgb[2]);
		return new Color(r, g, b);
	}

	/**
	 * Gets a font from a String array of name, style and size
	 * 
	 * @param fontInfo
	 *            The String array of name, style and size
	 * @return The font represented by the String array
	 */
	private Font getFont(String[] fontInfo) {
		String fontName = fontInfo[0];
		int fontStyle = Integer.parseInt(fontInfo[1]);
		int fontSize = Integer.parseInt(fontInfo[2]);
		return new Font(fontName, fontStyle, fontSize);
	}
}
