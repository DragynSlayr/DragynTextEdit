package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import file.FileOperations;
import spelling.SpellChecker;

/**
 *
 * @author Inderpreet
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel panel;
	private final FileOperations fileOps = new FileOperations();
	private boolean savedOnce = false;
	private File toSave = null;
	static TextField textField;
	static SpellChecker checker;
	private Color correctColor = Color.BLACK, incorrectColor = Color.RED;

	public GUI() {
		// Set the basics of the frame
		super("Dragyn TextEdit");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);

		// Declare new textField and spellChecker
		textField = new TextField(correctColor, incorrectColor);

		// Initialize checker
		checker = new SpellChecker(textField);

		// Set the keyListener of the textField
		textField.setKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					checker.checkLastWord();
					break;
				}
			}
		});

		// Sets up the user interface
		setupUI();

		// Update the components of the window
		SwingUtilities.updateComponentTreeUI(this);

		// Adds a custom window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				checkBeforeClosing();
			}
		});
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
				checkBeforeClosing();
			}
		});

		// Create a save JMenuItem
		JMenuItem saveItem = createJMenuItem("Save", KeyEvent.VK_S,
				"Save text to a file",
				KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!savedOnce) {
					JFileChooser fileChooser = new JFileChooser(System
							.getProperty("user.home") + "//Desktop");
					fileChooser.setDialogTitle("Save");
					int userSelection = fileChooser.showSaveDialog(panel);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						toSave = fileChooser.getSelectedFile();
						fileOps.write(toSave, textField.getTextBox().getText());
						JOptionPane.showMessageDialog(panel,
								"File has been saved", "File Saved",
								JOptionPane.INFORMATION_MESSAGE);
						savedOnce = true;
					}
				} else {
					fileOps.write(toSave, textField.getTextBox().getText());
					JOptionPane.showMessageDialog(panel, "File has been saved",
							"File Saved", JOptionPane.INFORMATION_MESSAGE);
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
						textField.getTextBox().setText(loaded);
					}
					checker.checkTextArea();
					showSpellCheckNotification();
				}
			}
		});

		// Create a edit JMenu
		JMenu editMenu = createJMenu("Editing", KeyEvent.VK_E);

		// Create a font JMenuItem
		JMenuItem fontMenuItem = createJMenuItem("Font", KeyEvent.VK_F,
				"Change font settings", null);
		fontMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FontMenu("Font Options", getWidth(), getHeight());
			}
		});

		// Create a spell check JMenuItem
		JMenuItem spellCheckItem = createJMenuItem("Check Spelling",
				KeyEvent.VK_S, "Review Spelling",
				KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		spellCheckItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checker.checkTextArea();
				showSpellCheckNotification();
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
								"Help Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Create an about JMenuItem
		JMenuItem aboutItem = createJMenuItem("About", KeyEvent.VK_A, "About",
				null);
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel,
						"Made by Inderpreet Dhillon", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Add JMenuItems to JMenu
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(exitItem);
		editMenu.add(fontMenuItem);
		editMenu.add(spellCheckItem);
		helpMenu.add(aboutItem);
		helpMenu.add(helpItem);

		// Add Menus to JMenuBar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(helpMenu);

		// Set JMenuBar
		setJMenuBar(menuBar);

		// Add the JTextArea
		panel.add(textField.getTextBox());

		// Create the JScrollPane
		JScrollPane scrollPane = new JScrollPane(textField.getTextBox());
		panel.add(scrollPane);

		// Sets content
		setContentPane(panel);

		// Set the theme of the program
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.out.println("Could not apply theme");
		}
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
	 * Displays a dialog with number of errors found
	 */
	private void showSpellCheckNotification() {
		int errorsFound = checker.getErrorsFound();
		String errorString = "Found " + checker.getErrorsFound() + " errors";
		if (errorsFound < 2 && errorsFound != 0) {
			errorString = "Found " + checker.getErrorsFound() + " error";
		}
		JOptionPane.showMessageDialog(panel, errorString,
				"Spell Check Complete", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * checks if the user wants to save before exiting
	 */
	private void checkBeforeClosing() {
		int selection = JOptionPane.showConfirmDialog(panel,
				"Save file before closing?", "Exit Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (selection) {
		case JOptionPane.YES_OPTION:
			JFileChooser fileChooser = new JFileChooser(
					System.getProperty("user.home") + "//Desktop");
			fileChooser.setDialogTitle("Save");
			int userSelection = fileChooser.showSaveDialog(panel);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				toSave = fileChooser.getSelectedFile();
				fileOps.write(toSave, textField.getTextBox().getText());
			}
		case JOptionPane.NO_OPTION:
			System.exit(0);
			break;
		default:
			break;
		}
	}

}
