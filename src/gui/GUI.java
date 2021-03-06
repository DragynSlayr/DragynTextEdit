package gui;

import images.ImageHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;

import spelling.SpellChecker;
import file.FileLoader;
import file.FileSaver;
import file.SettingsLoader;

/**
 *
 * @author Inderpreet
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static SpellChecker spellChecker;
	private String lastSavedText = "";
	public static TextField textField;

	public static Color correctColor = Color.BLACK, incorrectColor = Color.RED;
	private JPanel mainPanel;
	private boolean savedOnce = false, loadedFile = false;

	private File toSave = null;

	private FileLoader fileLoader;

	private int initialCursorPostion, endCursorPosition;

	public GUI() {
		resetCursorPositions();

		// Get the size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Declare the icons that are used for the window
		ArrayList<Image> icons = ImageHandler.getAllIconsAsImages();

		// Set the basics of the frame
		setTitle("Dragyn TextEdit");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setSize(screenSize.width / 2, screenSize.height / 2);
		setVisible(true);
		setLocationRelativeTo(null);
		setIconImages(icons);

		// Declare new textField and spellChecker
		textField = new TextField(correctColor, incorrectColor);

		// Initialize spellChecker
		spellChecker = new SpellChecker(textField);

		// Set the keyListener of the textField
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_TAB:
				case KeyEvent.VK_CAPS_LOCK:
				case KeyEvent.VK_SHIFT:
				case KeyEvent.VK_CONTROL:
				case KeyEvent.VK_ALT:
				case KeyEvent.VK_NUM_LOCK:
				case KeyEvent.VK_PAUSE:
				case KeyEvent.VK_PRINTSCREEN:
				case KeyEvent.VK_DELETE:
				case KeyEvent.VK_HOME:
				case KeyEvent.VK_PAGE_UP:
				case KeyEvent.VK_PAGE_DOWN:
				case KeyEvent.VK_END:
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_ESCAPE:
				case KeyEvent.VK_SCROLL_LOCK:
				case KeyEvent.VK_F1:
				case KeyEvent.VK_F2:
				case KeyEvent.VK_F3:
				case KeyEvent.VK_F4:
				case KeyEvent.VK_F5:
				case KeyEvent.VK_F6:
				case KeyEvent.VK_F7:
				case KeyEvent.VK_F8:
				case KeyEvent.VK_F9:
				case KeyEvent.VK_F10:
				case KeyEvent.VK_F11:
				case KeyEvent.VK_F12:
					break;
				case KeyEvent.VK_BACK_SPACE:
					endCursorPosition--;
					break;
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_SPACE:
					int temp1 = initialCursorPostion,
					temp2 = endCursorPosition;
					spellChecker.checkLastWord(textField.getTextPane()
							.getCaretPosition());
					initialCursorPostion = temp1;
					endCursorPosition = temp2;
				default:
					break;
				}
			}
		});

		// Set the caretListener of the textField
		textField.addCaretListener(event -> {
			int dot = event.getDot();
			if (dot < initialCursorPostion) {
				initialCursorPostion = dot;
			}
			if (dot > endCursorPosition) {
				endCursorPosition = dot;
			}
		});

		// Sets up the user interface
		setupUI();

		// Create a deserializer
		SettingsLoader settingsLoader = new SettingsLoader();
		settingsLoader.loadSettings();

		// Adds a custom window listener
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkBeforeClosing();
			}
		});
	}

	/**
	 * Saves a file
	 * 
	 * @param showDialog
	 *            Whether to show a confirmation or not
	 */
	private void saveFile(boolean showDialog) {
		FileSaver fileSaver = new FileSaver(toSave);

		String textFieldText = textField.getTextPane().getText();

		String toWrite = textFieldText.substring(initialCursorPostion,
				endCursorPosition);

		fileSaver.save(toWrite, initialCursorPostion);
		if (showDialog) {
			JOptionPane.showMessageDialog(mainPanel, "File has been saved",
					"File Saved", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Sets the cursor tracking integers to their default values
	 */
	private void resetCursorPositions() {
		initialCursorPostion = Integer.MAX_VALUE;
		endCursorPosition = Integer.MIN_VALUE;
	}

	/**
	 * checks if the user wants to save before exiting
	 */
	private void checkBeforeClosing() {
		if (spellChecker.getTextField().getDefaultDocument().getLength() > 0
				&& !lastSavedText.equals(textField.getTextPane().getText())) {
			int selection = JOptionPane.showConfirmDialog(mainPanel,
					"Save file before closing?", "Exit Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			switch (selection) {
			case JOptionPane.YES_OPTION:
				JFileChooser fileChooser = new JFileChooser(
						System.getProperty("user.home") + "//Desktop");
				fileChooser.setDialogTitle("Save");
				int userSelection = fileChooser.showSaveDialog(mainPanel);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					toSave = fileChooser.getSelectedFile();
					saveFile(false);
				}
			case JOptionPane.NO_OPTION:
				System.exit(0);
				break;
			default:
				break;
			}
		} else {
			System.exit(0);
		}
	}

	/**
	 * Sets up the components of the GUI
	 */
	private void setupUI() {
		// Create the JPanel
		mainPanel = new JPanel(new BorderLayout(), true);
		add(mainPanel);

		// Create a JMenuBar
		JMenuBar menuBar = new JMenuBar();

		// Create a file JMenu
		JMenu fileMenu = GUICreator.createJMenu("File", KeyEvent.VK_F);

		// Create an exit JMenuItem
		JMenuItem exitItem = GUICreator.createJMenuItem("Exit", KeyEvent.VK_E,
				"Exit Application",
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK),
				event -> checkBeforeClosing());

		// Create a save JMenuItem
		JMenuItem saveItem = GUICreator.createJMenuItem(
				"Save",
				KeyEvent.VK_S,
				"Save lastSavedText to a file",
				KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK),
				event -> {
					if (!savedOnce) {
						JFileChooser fileChooser = new JFileChooser(System
								.getProperty("user.home") + "//Desktop");
						fileChooser.setDialogTitle("Save");
						int userSelection = fileChooser
								.showSaveDialog(mainPanel);
						if (userSelection == JFileChooser.APPROVE_OPTION) {
							toSave = fileChooser.getSelectedFile();
							saveFile(true);
							savedOnce = true;
							loadedFile = false;
						}
					} else {
						saveFile(true);
					}
					lastSavedText = textField.getTextPane().getText();
				});

		// Create a load JMenuItem
		JMenuItem loadItem = GUICreator.createJMenuItem(
				"Load",
				KeyEvent.VK_L,
				"Load lastSavedText from a file",
				KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK),
				event -> {
					JFileChooser fileChooser = new JFileChooser(System
							.getProperty("user.home") + "//Desktop");
					fileChooser.setDialogTitle("Load");
					int userSelection = fileChooser.showOpenDialog(mainPanel);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File toLoad = fileChooser.getSelectedFile();
						fileLoader = new FileLoader(toLoad);
						String loaded = fileLoader.readChunk(fileLoader
								.checkDesiredLength(getWidth() * 19));
						textField.getTextPane().setText(loaded);
						spellChecker.checkTextArea();
						textField.getTextPane().setCaretPosition(0);
						loadedFile = true;
						resetCursorPositions();
					}
				});

		// Create a edit JMenu
		JMenu editMenu = GUICreator.createJMenu("Editing", KeyEvent.VK_E);

		// Create a font JMenuItem
		JMenuItem fontMenuItem = GUICreator.createJMenuItem("Font",
				KeyEvent.VK_F, "Change font settings", null, event -> {
					new FontMenu("Font Options", getWidth(), getHeight(),
							textField.getCurrentFont());
				});

		// Create a spell check JMenuItem
		JMenuItem spellCheckItem = GUICreator.createJMenuItem("Check Spelling",
				KeyEvent.VK_S, "Review Spelling",
				KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK),
				event -> {
					spellChecker.checkTextArea();
					showSpellCheckNotification();
				});

		// Create a help JMenu
		JMenu helpMenu = GUICreator.createJMenu("Help", KeyEvent.VK_H);

		// Create a help JMenuItem
		JMenuItem helpItem = GUICreator
				.createJMenuItem(
						"Help",
						KeyEvent.VK_L,
						"Help",
						null,
						event -> {
							JOptionPane
									.showMessageDialog(
											mainPanel,
											"Type as you would normally and the program will find and"
													+ "\n"
													+ " mark incorrectly spelled words upon pressing \"Space\"",
											"Help Message",
											JOptionPane.INFORMATION_MESSAGE);

						});

		// Create an about JMenuItem
		JMenuItem aboutItem = GUICreator.createJMenuItem("About",
				KeyEvent.VK_A, "About", null, event -> {
					JOptionPane.showMessageDialog(mainPanel,
							"Made by Inderpreet Dhillon", "About",
							JOptionPane.INFORMATION_MESSAGE);
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
		mainPanel.add(textField.getTextPane());

		// Create the JScrollPane
		JScrollPane scrollPane = GUICreator.createJScrollPane(textField
				.getTextPane());

		// Get the scroll bars from the scroll pane
		final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

		verticalScrollBar.setUnitIncrement(10);
		verticalScrollBar.setBlockIncrement(10);

		// Add listener to vertical scroll bar
		verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (loadedFile) {
					if (e.getSource() == verticalScrollBar
							&& verticalScrollBar.getValue() >= Math
									.abs(verticalScrollBar.getMaximum()
											- getHeight())) {
						try {
							textField.getDefaultDocument().insertString(
									textField.getDefaultDocument().getLength(),
									fileLoader.readChunk(fileLoader
											.checkDesiredLength(96)),
									textField.getAttributeSet());
						} catch (BadLocationException ble) {
							System.out.println(ble.getMessage());
						}
					}
				}
			}
		});

		verticalScrollBar.setValue(0);

		// Add scroll pane to mainPanel
		mainPanel.add(scrollPane);

		// Sets content
		setContentPane(mainPanel);
	}

	/**
	 * Displays a dialog with number of errors found
	 */
	private void showSpellCheckNotification() {
		int errorsFound = spellChecker.getErrorsFound();
		textField.getTextPane().setCaretPosition(0);
		String errorString = "Found " + spellChecker.getErrorsFound()
				+ " errors";
		if (errorsFound == 1) {
			errorString = "Found " + spellChecker.getErrorsFound() + " error";
		}
		JOptionPane.showMessageDialog(mainPanel, errorString,
				"Spell Check Complete", JOptionPane.INFORMATION_MESSAGE);
	}
}
