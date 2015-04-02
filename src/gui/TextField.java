package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JTextPane;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 * 
 * @author Inderpreet
 *
 */
public class TextField {

	private Color correctColor, incorrectColor;
	private DefaultStyledDocument defaultDocument;
	private Font currentFont;
	private SimpleAttributeSet attributeSet;
	public JTextPane textPane;

	public TextField(Color correctColor, Color incorrectColor) {
		// Create a simple attribute attributeSet
		attributeSet = new SimpleAttributeSet();

		// Set colors
		setCorrectColor(correctColor);
		setIncorrectColor(incorrectColor);

		// Create a currentFont
		currentFont = new Font(Font.SERIF, Font.PLAIN, 20);

		// Create a defaultDocument
		defaultDocument = new DefaultStyledDocument();

		// Create the JTextArea
		textPane = new JTextPane(defaultDocument);
		textPane.setFont(currentFont);

		// Set the starting color
		textPane.setForeground(this.correctColor);
	}

	public TextField(Color correctColor, Color incorrectColor, JTextPane pane) {
		this.textPane = pane;

		// Create a simple attribute attributeSet
		attributeSet = new SimpleAttributeSet();

		// Create a defaultDocument
		defaultDocument = (DefaultStyledDocument) pane.getDocument();

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
	 * Gets the defaultDocument
	 * 
	 * @return A DefaultStyledDocument
	 */
	public DefaultStyledDocument getDefaultDocument() {
		return defaultDocument;
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
	 * Gets the attributeSet
	 * 
	 * @return A SimpleAttributeSet
	 */
	public SimpleAttributeSet getAttributeSet() {
		return attributeSet;
	}

	/**
	 * Get the TextBox
	 * 
	 * @return A JTextPane
	 */
	public JTextPane getTextPane() {
		return textPane;
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
	 * Sets the currentFont used for the JTextPane
	 * 
	 * @param desiredFont
	 *            the desired currentFont
	 */
	public void setCurrentFont(Font desiredFont) {
		currentFont = desiredFont;
		textPane.setFont(currentFont);
	}

	/**
	 * Gets the current currentFont
	 * 
	 * @return The current currentFont
	 */
	public Font getCurrentFont() {
		return textPane.getFont();
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
	 * Add a Key Listener to the textPane
	 * 
	 * @param keyListener
	 *            The KeyListener to be added
	 */
	public void addKeyListener(KeyListener keyListener) {
		textPane.addKeyListener(keyListener);
	}

	/**
	 * Add a Caret Listener to the textPane
	 * 
	 * @param caretListener
	 *            The CaretListener to be added
	 */
	public void addCaretListener(CaretListener caretListener) {
		textPane.addCaretListener(caretListener);
	}
}
