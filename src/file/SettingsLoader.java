package file;

import gui.GUI;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SettingsLoader {

	private BufferedReader reader;
	private String fontType = "Times New Roman";
	private int fontSize = 20, fontStyle = Font.PLAIN;

	public SettingsLoader() {
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
			System.out.println("Could not create loader");
		}
	}

	/**
	 * Gets information back from a file
	 */
	public void load() {
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(SettingsSaver.ASSIGNER);
				String name = split[0];
				switch (name) {
				case SettingsSaver.FONT_TYPE:
					fontType = split[1];
					setFont();
					break;
				case SettingsSaver.FONT_SIZE:
					fontSize = Integer.parseInt(split[1]);
					setFont();
					break;
				case SettingsSaver.FONT_STYLE:
					fontStyle = Integer.parseInt(split[1]);
					setFont();
					break;
				case SettingsSaver.CORRECT_COLOR:
					String[] correctRGB = split[1]
							.split(SettingsSaver.SEPARATOR);
					Color correctColor = getColor(correctRGB);
					GUI.textField.setCorrectColor(correctColor);
					break;
				case SettingsSaver.INCORRECT_COLOR:
					String[] incorrectRGB = split[1]
							.split(SettingsSaver.SEPARATOR);
					Color incorrectColor = getColor(incorrectRGB);
					GUI.textField.setIncorrectColor(incorrectColor);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Could not load");
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
	 * Sets a font from what was read from the setting configuration file
	 */
	private void setFont() {
		Font font = new Font(fontType, fontStyle, fontSize);
		GUI.textField.setFont(font);
	}
}
