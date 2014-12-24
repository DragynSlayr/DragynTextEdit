package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

	public TextField(Color correctColor, Color incorrectColor) {
		// Create a simple attribute set
		set = new SimpleAttributeSet();

		// Set colors
		this.correctColor = correctColor;
		this.incorrectColor = incorrectColor;

		// Create a font
		font = new Font(Font.SERIF, Font.PLAIN, 20);

		// Create a document
		document = new DefaultStyledDocument();

		// Create the JTextArea
		textBox = new JTextPane(document);
		textBox.setFont(font);
		textBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					SpellChecker.checkLastWord();
					break;
				}
			}
		});

		// Set the starting color
		textBox.setForeground(this.correctColor);
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
