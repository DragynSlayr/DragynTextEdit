package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Inderpreet
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel panel;
	public static JTextPane textBox;
	private static DefaultStyledDocument document;
	private static SimpleAttributeSet set;
	private static final Dictionary dict = new Dictionary();
	private static ArrayList<String> dictionary;
	private static Font font;
	private static final FileOperations fileOps = new FileOperations();
	private static Color correctColor, incorrectColor;

	public GUI() {
		// Set the basics of the frame
		super("Dragyn TextEdit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);

		// Create a font
		font = new Font(Font.SERIF, Font.PLAIN, 20);

		// Create a set
		set = new SimpleAttributeSet();

		// Set colors
		correctColor = Color.BLACK;
		incorrectColor = Color.RED;

		// Creates dictionary
		dictionary = dict.getDictionary();

		setupUI();
	}

	/**
	 * Sets up the components of the GUI
	 */
	private void setupUI() {
		// Create the JPanel
		panel = new JPanel(new BorderLayout(), true);
		add(panel);

		// Create a JMenuBar
		JMenuBar menuBar = new JMenuBar();

		// Create a file JMenu
		JMenu fileMenu = createJMenu("File", KeyEvent.VK_F);

		// Create an exit JMenuItem
		JMenuItem exitItem = createJMenuItem("Exit", KeyEvent.VK_E,
				"Exit Application",
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Create a save JMenuItem
		JMenuItem saveItem = createJMenuItem("Save", KeyEvent.VK_S,
				"Save text to a file",
				KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(System
						.getProperty("user.home") + "//Desktop");
				fileChooser.setDialogTitle("Save");
				int userSelection = fileChooser.showSaveDialog(panel);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File toSave = fileChooser.getSelectedFile();
					fileOps.write(toSave, textBox.getText());
					JOptionPane.showMessageDialog(panel, "File has been saved",
							"File Saved", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		// Create a load JMenuItem
		JMenuItem loadItem = createJMenuItem("Load", KeyEvent.VK_L,
				"Load text from a file",
				KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(System
						.getProperty("user.home") + "//Desktop");
				fileChooser.setDialogTitle("Load");
				int userSelection = fileChooser.showOpenDialog(panel);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					for (int i = 0; i < 2; i++) {
						File toLoad = fileChooser.getSelectedFile();
						String loaded = fileOps.read(toLoad);
						textBox.setText(loaded);
					}
					updateTextArea();
				}
			}
		});

		// Create a edit JMenu
		JMenu editMenu = createJMenu("Editing", KeyEvent.VK_E);

		// Create a font JMenuItem
		JMenuItem fontMenuItem = new FontMenu(getWidth(), getHeight(), "Font",
				KeyEvent.VK_F, "Change font settings", null).getMenu();

		// Create a spell check JMenuItem
		JMenuItem spellCheckItem = createJMenuItem("Check Spelling",
				KeyEvent.VK_S, "Review Spelling",
				KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		spellCheckItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTextArea();
			}
		});

		// Create a help JMenu
		JMenu helpMenu = createJMenu("Help", KeyEvent.VK_H);

		// Create a help JMenuItem
		JMenuItem helpItem = createJMenuItem("Help", KeyEvent.VK_L, "Help",
				null);
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								panel,
								"Type as you would normally and the program will find and"
										+ "\n"
										+ " mark incorrectly spelled words upon pressing \"Space\"",
								"Help Message", JOptionPane.PLAIN_MESSAGE);
			}
		});

		// Add JMenuItems to JMenu
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(exitItem);
		editMenu.add(fontMenuItem);
		editMenu.add(spellCheckItem);
		helpMenu.add(helpItem);

		// Add Menus to JMenuBar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(helpMenu);

		// Set JMenuBar
		setJMenuBar(menuBar);

		// Create a document
		document = new DefaultStyledDocument();

		// Create the JTextArea
		textBox = new JTextPane(document);
		textBox.setFont(font);
		textBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					updateLastWord();
					break;
				}
			}
		});

		// Add the JTextArea
		panel.add(textBox);

		// Create the JScrollPane
		JScrollPane scrollPane = new JScrollPane(textBox);
		panel.add(scrollPane);

		// Sets content
		setContentPane(panel);
	}

	/**
	 * Creates a new JMenu
	 *
	 * @param name
	 *            The name of the new menu
	 * @param mnemonic
	 *            The shortcut key
	 * @return New JMenu
	 */
	private static JMenu createJMenu(String name, int mnemonic) {
		JMenu menu = new JMenu(name);
		menu.setMnemonic(mnemonic);
		return menu;
	}

	/**
	 * Creates a new JMenuItem
	 *
	 * @param name
	 *            The name of the item
	 * @param mnemonic
	 *            The shortcut key
	 * @param tooltip
	 *            The tool tip text
	 * @param keyStroke
	 *            The accelerated key combo
	 * @return New JMenuItem
	 */
	private JMenuItem createJMenuItem(String name, int mnemonic,
			String tooltip, KeyStroke keyStroke) {
		JMenuItem menuItem = new JMenuItem(name, mnemonic);
		menuItem.setToolTipText(tooltip);
		menuItem.setAccelerator(keyStroke);
		return menuItem;
	}

	/**
	 * Updates the main text area
	 */
	private static void updateTextArea() {
		try {
			String[] split = textBox.getText().split(" ");
			ArrayList<Word> words = new ArrayList<Word>();
			for (String s : split) {
				words.add(new Word(s));
			}
			textBox.setText("");
			for (Word word : words) {
				if (word.isWord(dictionary)) {
					StyleConstants.setForeground(set, correctColor);
					document.insertString(document.getLength(), word.getWord()
							+ " ", set);
				} else {
					StyleConstants.setForeground(set, incorrectColor);
					document.insertString(document.getLength(), word.getWord()
							+ " ", set);
				}
			}
		} catch (BadLocationException ble) {
			System.out.println("Couldn't insert string");
		}
	}

	/**
	 * Checks the spelling of the last term
	 */
	private static void updateLastWord() {
		if (textBox.getCaretPosition() == document.getLength()) {
			try {
				String[] split = textBox.getText().split(" ");
				Word word = new Word(split[split.length - 1]);
				int docLength;
				int length = split[split.length - 1].length() - 1;
				docLength = document.getLength() - 1;
				StyleConstants.setForeground(set, Color.BLACK);
				if (word.isWord(dictionary)) {
					StyleConstants.setForeground(set, Color.BLACK);
					document.replace(docLength - length, word.getWord()
							.length(), word.getWord(), set);
				} else {
					StyleConstants.setForeground(set, Color.RED);
					document.replace(docLength - length, word.getWord()
							.length(), word.getWord(), set);
				}
			} catch (BadLocationException ble) {
				System.out.println("Couldn't replace string");
			}
		}
	}

}
