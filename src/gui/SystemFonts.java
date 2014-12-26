package gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

public class SystemFonts {

	private String[] fonts;

	public SystemFonts() {
		fonts = getFonts();
		fonts = cleanFonts(fonts);
	}

	public String[] getAvailableFonts() {
		return fonts;
	}

	private String[] getFonts() {
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		ArrayList<String> fontFamilies = new ArrayList<String>();
		for (Font f : fonts) {
			fontFamilies.add(f.getFamily());
		}
		return listToArray(fontFamilies);
	}

	private String[] cleanFonts(String[] fontNames) {
		ArrayList<String> cleanFonts = new ArrayList<String>();
		for (String s : fontNames) {
			if (!cleanFonts.contains(s)) {
				cleanFonts.add(s);
			}
		}
		return listToArray(cleanFonts);
	}

	private String[] listToArray(ArrayList<String> arrayList) {
		String[] array = new String[arrayList.size()];
		arrayList.toArray(array);
		return array;
	}
}
