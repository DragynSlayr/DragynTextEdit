package spelling;

import gui.TextField;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

/**
 * 
 * @author Inderpreet
 *
 */
public class SpellChecker {

	private ArrayList<String> dictionary;
	private int errorsFound = 0;
	private TextField textField;

	public SpellChecker(TextField textField) {
		// Creates dictionary
		Dictionary dict = new Dictionary();
		dictionary = dict.getDictionary();

		// Set textField
		this.textField = textField;
	}

	/**
	 * Checks the spelling of the last word
	 */
	public void checkLastWord() {
		if (textField.getTextBox().getCaretPosition() == textField
				.getDocument().getLength()
				&& !textField.getTextBox().getText().endsWith(" ")) {
			try {
				String[] split = textField.getTextBox().getText().split(" ");
				Word word = new Word(split[split.length - 1]);
				int docLength = textField.getDocument().getLength() + 1;
				int length = split[split.length - 1].length() + 1;
				StyleConstants.setForeground(textField.getSet(),
						textField.getCorrectColor());
				if (word.isWord(dictionary)) {
					StyleConstants.setForeground(textField.getSet(),
							textField.getCorrectColor());
					textField.getDocument().replace(
							docLength - length,
							textField.getTextBox().getCaretPosition()
									- (docLength - length),
							word.getWord() + " ", textField.getSet());
				} else {
					StyleConstants.setForeground(textField.getSet(),
							textField.getIncorrectColor());
					textField.getDocument().replace(
							docLength - length,
							textField.getTextBox().getCaretPosition()
									- (docLength - length),
							word.getWord() + " ", textField.getSet());
				}
			} catch (BadLocationException ble) {
				System.out.println("Couldn't replace string");
			}
		}
	}

	/**
	 * Checks the spelling of the main text area
	 */
	public void checkTextArea() {
		errorsFound = 0;
		try {
			String[] split = textField.getTextBox().getText().split(" ");
			ArrayList<Word> words = new ArrayList<Word>();
			for (String s : split) {
				words.add(new Word(s));
			}
			textField.getTextBox().setText("");
			for (int i = 0; i < words.size(); i++) {
				String add;
				if (i == words.size() - 1) {
					add = "";
				} else {
					add = " ";
				}
				if (words.get(i).isWord(dictionary)) {
					StyleConstants.setForeground(textField.getSet(),
							textField.getCorrectColor());
					textField.getDocument().insertString(
							textField.getDocument().getLength(),
							words.get(i).getWord() + add, textField.getSet());
				} else {
					errorsFound++;
					StyleConstants.setForeground(textField.getSet(),
							textField.getIncorrectColor());
					textField.getDocument().insertString(
							textField.getDocument().getLength(),
							words.get(i).getWord() + add, textField.getSet());
				}
			}
		} catch (BadLocationException ble) {
			System.out.println("Couldn't insert string");
		}
	}

	/**
	 * Returns the number of errors found
	 * 
	 * @return number of errors
	 */
	public int getErrorsFound() {
		return errorsFound;
	}

	/**
	 * Gets textField
	 * 
	 * @return The text field
	 */
	public TextField getTextField() {
		return textField;
	}

}
