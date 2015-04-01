package gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;

public class SystemFonts {

	private String[] availableFonts;

	public SystemFonts() {
		availableFonts = getSystemFonts();
		availableFonts = cleanFonts(availableFonts);
		Arrays.sort(availableFonts);
	}

	/**
	 * Removes duplicates from a list
	 * 
	 * @param fontNames
	 *            The array to clean
	 * @return A cleaned array
	 */
	private String[] cleanFonts(String[] fontNames) {
		ArrayList<String> cleanFonts = new ArrayList<String>();
		for (String s : fontNames) {
			if (!cleanFonts.contains(s)) {
				cleanFonts.add(s);
			}
		}
		return listToArray(cleanFonts);
	}

	/**
	 * Gets availableFonts
	 * 
	 * @return Allowed availableFonts
	 */
	public String[] getAvailableFonts() {
		return availableFonts;
	}

	/**
	 * Gets all availableFonts on the system
	 * 
	 * @return Fonts on the system
	 */
	private String[] getSystemFonts() {
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		ArrayList<String> fontFamilies = new ArrayList<String>();
		for (Font f : fonts) {
			if (f.getNumGlyphs() > 227) {
				fontFamilies.add(f.getFamily());
			}
		}
		return listToArray(fontFamilies);
	}

	/**
	 * Converts list to array
	 * 
	 * @param arrayList
	 *            The list to convert
	 * @return An array
	 */
	private String[] listToArray(ArrayList<String> arrayList) {
		String[] array = new String[arrayList.size()];
		arrayList.toArray(array);
		return array;
	}
}
