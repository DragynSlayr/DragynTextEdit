package file;

import gui.FontMenu;
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

	private void createReader() {
		try {
			reader = new BufferedReader(
					new FileReader(new File("settings.cfg")));
		} catch (Exception e) {
			System.out.println("Could not create deserializer");
		}
	}

	public void deserialize() {
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(":");
				String identifier = split[0].split(" ")[0];
				String name = split[0].split(" ")[1];
				switch (identifier) {
				case "color":
					String[] rgb = split[1].split(" ");
					int r = Integer.parseInt(rgb[0]);
					int g = Integer.parseInt(rgb[1]);
					int b = Integer.parseInt(rgb[2]);
					Color color = new Color(r, g, b);
					if (name.equalsIgnoreCase("correct")) {
						GUI.textField.setCorrectColor(color);
						FontMenu.setCorrectColor(color);
					} else {
						GUI.textField.setIncorrectColor(color);
						FontMenu.setIncorrectColor(color);
					}
					break;
				case "font":
					String[] fontInfo = split[1].split(" ");
					String fontName = fontInfo[0];
					int fontStyle = Integer.parseInt(fontInfo[1]);
					int fontSize = Integer.parseInt(fontInfo[2]);
					Font font = new Font(fontName, fontStyle, fontSize);
					GUI.textField.setFont(font);
					FontMenu.selected = font;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Could not deserialize");
		}
	}
}
