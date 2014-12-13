package src;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

public class SpellChecker {

	private static ArrayList<String> dictionary;
	private static TextField textField;

	public SpellChecker(TextField textField) {
		// Creates dictionary
		Dictionary dict = new Dictionary();
		dictionary = dict.getDictionary();

		// Set textField
		SpellChecker.textField = textField;
	}

	/**
	 * Checks the spelling of the main text area
	 */
	public void checkTextArea() {
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
							textField.getColor("correct"));
					textField.getDocument().insertString(
							textField.getDocument().getLength(),
							words.get(i).getWord() + add, textField.getSet());
				} else {
					StyleConstants.setForeground(textField.getSet(),
							textField.getColor("incorrect"));
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
	 * Checks the spelling of the last word
	 */
	public static void checkLastWord() {
		if (textField.getTextBox().getCaretPosition() == textField
				.getDocument().getLength()) {
			try {
				String[] split = textField.getTextBox().getText().split(" ");
				Word word = new Word(split[split.length - 1]);
				int docLength = textField.getDocument().getLength() + 1;
				int length = split[split.length - 1].length();
				StyleConstants.setForeground(textField.getSet(), textField.getColor("correct"));
				if (word.isWord(dictionary)) {
					StyleConstants.setForeground(textField.getSet(),
							textField.getColor("correct"));
					textField.getDocument().replace((docLength - length) - 1,
							length, word.getWord(), textField.getSet());
				} else {
					StyleConstants.setForeground(textField.getSet(),
							textField.getColor("incorrect"));
					textField.getDocument().replace((docLength - length) - 1,
							length, word.getWord(), textField.getSet());
				}
			} catch (BadLocationException ble) {
				System.out.println("Couldn't replace string");
			}
		}
	}

}
