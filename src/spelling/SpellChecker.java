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
	 * 
	 * @param cursor
	 *            The position of the cursor
	 */
	public void checkLastWord(int cursor) {
		try {
			String[] split = textField.getTextPane().getText(0, cursor)
					.split(" ");
			Word word = new Word(split[split.length - 1]);
			int docLength = textField.getTextPane().getCaretPosition() + 1;
			int length = split[split.length - 1].length() + 1;
			StyleConstants.setForeground(textField.getAttributeSet(),
					textField.getCorrectColor());
			String replacement = word.getWord();
			if (word.isWord(dictionary)) {
				StyleConstants.setForeground(textField.getAttributeSet(),
						textField.getCorrectColor());
			} else {
				StyleConstants.setForeground(textField.getAttributeSet(),
						textField.getIncorrectColor());
			}
			textField.getDefaultDocument().replace(
					docLength - length,
					textField.getTextPane().getCaretPosition()
							- (docLength - length), replacement,
					textField.getAttributeSet());
		} catch (BadLocationException ble) {
			System.out.println("Couldn't replace string");
		}
	}

	/**
	 * Checks the spelling of the main text area
	 */
	public void checkTextArea() {
		errorsFound = 0;
		try {
			String[] split = textField.getTextPane().getText().split(" ");
			ArrayList<Word> words = new ArrayList<Word>();
			for (String s : split) {
				words.add(new Word(s));
			}
			textField.getTextPane().setText("");
			for (int i = 0; i < words.size(); i++) {
				String add;
				if (i == words.size() - 1) {
					add = "";
				} else {
					add = " ";
				}
				if (words.get(i).isWord(dictionary)) {
					StyleConstants.setForeground(textField.getAttributeSet(),
							textField.getCorrectColor());
					textField.getDefaultDocument().insertString(
							textField.getDefaultDocument().getLength(),
							words.get(i).getWord() + add, textField.getAttributeSet());
				} else {
					errorsFound++;
					StyleConstants.setForeground(textField.getAttributeSet(),
							textField.getIncorrectColor());
					textField.getDefaultDocument().insertString(
							textField.getDefaultDocument().getLength(),
							words.get(i).getWord() + add, textField.getAttributeSet());
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
