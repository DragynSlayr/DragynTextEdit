package gui;

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

	private Color correctColor, incorrectColor;
	private DefaultStyledDocument document;
	private Font font;
	private SimpleAttributeSet set;
	public JTextPane textBox;

	public TextField(Color correctColor, Color incorrectColor) {
		// Create a simple attribute set
		set = new SimpleAttributeSet();

		// Set colors
		setCorrectColor(correctColor);
		setIncorrectColor(incorrectColor);

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

	public TextField(Color correctColor, Color incorrectColor, JTextPane pane) {
		this.textBox = pane;

		// Create a simple attribute set
		set = new SimpleAttributeSet();

		// Create a document
		document = (DefaultStyledDocument) pane.getDocument();

		// Set colors
		setCorrectColor(correctColor);
		setIncorrectColor(incorrectColor);

	}

	/**
	 * Get correct color
	 * 
	 * @return The correct color
	 */
	public Color getCorrectColor() {
		return correctColor;
	}

	/**
	 * Gets the document
	 * 
	 * @return A DefaultStyledDocument
	 */
	public DefaultStyledDocument getDocument() {
		return document;
	}

	/**
	 * Get the incorrect color
	 * 
	 * @return The incorrect color
	 */
	public Color getIncorrectColor() {
		return incorrectColor;
	}

	/**
	 * Gets the set
	 * 
	 * @return A SimpleAttributeSet
	 */
	public SimpleAttributeSet getSet() {
		return set;
	}

	/**
	 * Get the TextBox
	 * 
	 * @return A JTextPane
	 */
	public JTextPane getTextBox() {
		return textBox;
	}

	/**
	 * Set the correct color
	 * 
	 * @param correctColor
	 *            The correct color
	 */
	public void setCorrectColor(Color correctColor) {
		this.correctColor = correctColor;
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

	/**
	 * Sets the incorrect color
	 * 
	 * @param incorrectColor
	 *            The incorrect color
	 */
	public void setIncorrectColor(Color incorrectColor) {
		this.incorrectColor = incorrectColor;
	}

	/**
	 * Sets the textBox's Key Listener
	 * 
	 * @param listener
	 *            The KeyListener to be set
	 */
	public void setKeyListener(KeyListener listener) {
		textBox.addKeyListener(listener);
	}
}
