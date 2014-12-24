package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 * 
 * @author Inderpreet
 *
 */
public class TextField {

	private Font font;
	public JTextPane textBox;
	private DefaultStyledDocument document;
	private SimpleAttributeSet set;
	private Color correctColor, incorrectColor;

	public TextField(Color correctColor, Color incorrectColor, JTextPane pane) {
		this.textBox = pane;

		set = new SimpleAttributeSet();

		document = (DefaultStyledDocument) pane.getDocument();

		setColors(correctColor, incorrectColor);
	}

	public TextField(Color correctColor, Color incorrectColor) {
		// Create a simple attribute set
		set = new SimpleAttributeSet();

		// Set colors
		setColors(correctColor, incorrectColor);

		// Create a font
		font = new Font(Font.SERIF, Font.PLAIN, 20);

		// Create a document
		document = new DefaultStyledDocument();

		// Create the JTextArea
		textBox = new JTextPane(document);
		textBox.setFont(font);

		// Set the starting color
		textBox.setForeground(this.correctColor);
	}

	public void setKeyListener(KeyListener listener) {
		textBox.addKeyListener(listener);
	}

	/**
	 * Sets the colors to be used for the text
	 * 
	 * @param correctColor
	 *            The color for correct words
	 * @param incorrectColor
	 *            The color for incorrect words
	 */
	public void setColors(Color correctColor, Color incorrectColor) {
		this.correctColor = correctColor;
		this.incorrectColor = incorrectColor;
	}

	public JTextPane getTextBox() {
		return textBox;
	}

	public SimpleAttributeSet getSet() {
		return set;
	}

	public DefaultStyledDocument getDocument() {
		return document;
	}

	/**
	 * Gets a color
	 * 
	 * @param name
	 *            "correct" or "incorrect" are valid
	 * @return a color
	 */
	public Color getColor(String name) {
		if (name.equalsIgnoreCase("correct")) {
			return correctColor;
		} else if (name.equalsIgnoreCase("incorrect")) {
			return incorrectColor;
		} else {
			return Color.GRAY;
		}
	}

	/**
	 * Sets the font used for the JTextPane
	 * 
	 * @param f
	 *            the desired font
	 */
	public void setFont(Font f) {
		font = f;
		textBox.setFont(font);
	}
}
