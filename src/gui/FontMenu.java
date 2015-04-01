package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;

import spelling.SpellChecker;
import file.SettingsSaver;

/**
 *
 * @author Inderpreet
 */
public class FontMenu {

	public static Color correctColor = Color.BLACK;
	public static Color incorrectColor = Color.RED;

	public static int fontSize;
	public static int fontStyle;
	public static String fontType;

	private JPanel cardsPanel;
	private SpellChecker spellChecker;
	private DefaultStyledDocument defualtDocument;
	private JTextPane exampleTextPane;
	private final String FONT_COLOR = "Font Colors";
	private final String FONT_SIZE = "Font Size";
	private final String FONT_STYLE = "Font Style";
	private final String FONT_TYPE = "Font Type";

	private Font createdFont;

	private SettingsSaver settingsSaver;

	private static boolean saveSettings;

	public FontMenu(String frameTitle, int frameWidth, int frameHeight, Font currentFont) {
		//Get the current font values
		fontSize = currentFont.getSize();
		fontStyle = currentFont.getStyle();
		fontType = currentFont.getName();
		
		saveSettings = false;
		
		// Create a JFrame to hold the panel
		final JFrame frame = new JFrame(frameTitle);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Create a settingsSaver
		settingsSaver = new SettingsSaver();

		// Create a defualtDocument
		defualtDocument = new DefaultStyledDocument();

		// Create an editable JTextField to show changes in font
		exampleTextPane = new JTextPane(defualtDocument);
		exampleTextPane.setText("Example Tetx");

		// Initialize spellChecker
		spellChecker = new SpellChecker(new TextField(correctColor, incorrectColor,
				exampleTextPane));

		spellChecker.getTextField().setCorrectColor(correctColor);
		spellChecker.getTextField().setIncorrectColor(incorrectColor);
		updateTextArea();

		// Add custom keyListener to JTextPane
		exampleTextPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					spellChecker.checkLastWord(exampleTextPane.getCaretPosition());
					break;
				}
			}
		});

		// Set alignment of the exampleTextPane
		StyleConstants.setAlignment(spellChecker.getTextField().getAttributeSet(),
				StyleConstants.ALIGN_CENTER);
		defualtDocument.setParagraphAttributes(0, defualtDocument.getLength(), spellChecker
				.getTextField().getAttributeSet(), false);

		createdFont = new Font(fontType, fontStyle, fontSize);

		setFont();

		// Initialize cardsPanel JPanel, set layout to new CardLayout
		cardsPanel = new JPanel(new CardLayout());

		// Create a container to house all components
		Container panel = frame.getContentPane();
		panel.setLayout(new GridLayout(4, 1));

		// Create a cell render to modify the horizontal alignment of some
		// components
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

		// A list of items that will be present in a JComboBox
		String[] items = { FONT_TYPE, FONT_SIZE, FONT_STYLE, FONT_COLOR };

		// Create a new JComboBox with the items
		final JComboBox<String> choices = new JComboBox<String>(items);

		// Assign a custom renderer to the JComboBox
		choices.setRenderer(renderer);

		// Set default value of JComboBox
		choices.setSelectedIndex(0);

		// Add an actionListener to the JComboBox
		choices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String a = choices.getSelectedItem().toString();
				CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
				switch (a) {
				case FONT_TYPE:
					cardLayout.show(cardsPanel, FONT_TYPE);
					break;
				case FONT_SIZE:
					cardLayout.show(cardsPanel, FONT_SIZE);
					break;
				case FONT_STYLE:
					cardLayout.show(cardsPanel, FONT_STYLE);
					break;
				case FONT_COLOR:
					cardLayout.show(cardsPanel, FONT_COLOR);
					break;
				}
			}
		});

		// Add JComboBox to panel
		panel.add(choices);

		// Create a JPanel for fontType with GridLayout
		JPanel typePanel = new JPanel(new GridLayout(1, 1));

		// Create a SystemFonts object
		final SystemFonts sysFonts = new SystemFonts();

		// An array of possible font types
		String[] fonts = sysFonts.getAvailableFonts();
		int fontTypeIndex = Arrays.binarySearch(fonts, fontType);

		// Create a JComboBox for fontTypes with fonts
		JComboBox<String> fontTypes = new JComboBox<String>(fonts);

		// Set default selection for fontTypes comboBox
		fontTypes.setSelectedIndex(fontTypeIndex);

		// Set renderer to custom cellRenderer
		fontTypes.setRenderer(renderer);

		fontTypes.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
				fontType = sysFonts.getAvailableFonts()[comboBox
						.getSelectedIndex()];
				createdFont = new Font(fontType, fontStyle, fontSize);
				setFont();
			}
		});
		// Add the combo box to the panel
		typePanel.add(fontTypes);

		// Add the typePanel to the cardsPanel panel with FONT_TYPE as a name
		cardsPanel.add(typePanel, FONT_TYPE);

		// Create a size JPanel with GridLayout
		JPanel sizePanel = new JPanel(new GridLayout(2, 1));

		// Create a JLabel to show the createdFont font size
		final JLabel sizeLabel = new JLabel("Font Size: " + fontSize);

		// Set Horizontal alignment of the sizeLabel
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Create a custom ChangeListener for the JSlider
		ChangeListener sliderListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				fontSize = slider.getValue();
				sizeLabel.setText("Font Size: " + fontSize);
				createdFont = new Font(fontType, fontStyle, fontSize);
				setFont();
			}
		};

		// Create a size JSlider
		JSlider sizeSlider = createSlider(10, 100, fontSize, 10, 5, true, true,
				sliderListener);

		// Add sizeLabel and sizeSlider to sizePanel
		sizePanel.add(sizeLabel);
		sizePanel.add(sizeSlider);

		// Add sizePanel to cardsPanel panel with FONT_SIZE as name
		cardsPanel.add(sizePanel, FONT_SIZE);

		// Create a JPanel for a font styles with GridLayout
		JPanel stylePanel = new JPanel(new GridLayout(1, 1));

		// An array of possible font types
		String[] styleItems = { "Normal", "Bold", "Italic", "Bold & Italic" };

		// An array of integers that represents font types
		final int[] fontStylesInt = { Font.PLAIN, Font.BOLD, Font.ITALIC,
				Font.BOLD + Font.ITALIC };

		// Get the index of the createdFont style
		int fontStyleIndex = slowSearch(fontStyle, fontStylesInt);

		// Create a JComboBox for fontStyles with fontItems
		JComboBox<String> fontStyles = new JComboBox<String>(styleItems);

		// Set default selection for fontTypes comboBox
		fontStyles.setSelectedIndex(fontStyleIndex);

		// Set renderer to custom cellRenderer
		fontStyles.setRenderer(renderer);

		fontStyles.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
				fontStyle = fontStylesInt[comboBox.getSelectedIndex()];
				createdFont = new Font(fontType, fontStyle, fontSize);
				setFont();
			}
		});

		// Add fontTypes to typePanel
		stylePanel.add(fontStyles);

		// Add typeJPanel to cardsPanel with
		cardsPanel.add(stylePanel, FONT_STYLE);

		// Create color JPanel with GridLayout
		final JPanel colorPanel = new JPanel(new GridLayout(1, 2));

		JButton correctColorButton = new JButton("Choose Correct Color");
		correctColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				correctColor = JColorChooser.showDialog(colorPanel,
						"Choose Correct Color", correctColor);
				if (correctColor != null) {
					spellChecker.getTextField().setCorrectColor(correctColor);
					updateTextArea();
				}
			}
		});

		JButton incorrectColorButton = new JButton("Choose Incorrect Color");
		incorrectColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				incorrectColor = JColorChooser.showDialog(colorPanel,
						"Choose Incorrect Color", incorrectColor);
				if (incorrectColor != null) {
					spellChecker.getTextField().setIncorrectColor(incorrectColor);
					updateTextArea();
				}
			}
		});

		// Add buttons to the color panel
		colorPanel.add(correctColorButton);
		colorPanel.add(incorrectColorButton);

		// Add color panel to cardsPanel as FONT_COLOR
		cardsPanel.add(colorPanel, FONT_COLOR);

		// Add cardsPanel to panel
		panel.add(cardsPanel);

		// Add exampleTextPane to panel
		panel.add(exampleTextPane);

		// Create a panel to hold the save and discard button
		JPanel savePanel = new JPanel(new GridLayout(1, 2));

		// The button to save settings
		JButton saveButton = new JButton("Save font settings");

		// Adding a button listener that notifies the program to save the
		// current settings
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettings = true;
				frame.dispose();
			}
		});

		// The button to discard settings
		JButton discardButton = new JButton("Discard font settings");

		// Adding a listener to just close the window
		discardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettings = false;
				frame.dispose();
			}
		});

		// Add the buttons to the save panel
		savePanel.add(saveButton);
		savePanel.add(discardButton);

		// Add the save panel to the main panel
		panel.add(savePanel);

		// Add custom windowListener to frame
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (saveSettings) {
					GUI.textField.setCurrentFont(new Font(fontType, fontStyle,
							fontSize));
					setColors();
					saveOptions();
				}
			}
		});
	}

	/**
	 * Creates a JSlider
	 * 
	 * @param minimumValue
	 *            The minimum value
	 * @param maximumValue
	 *            The maximum value
	 * @param currentValue
	 *            The starting value
	 * @param majorTickSpacing
	 *            The space between big ticks
	 * @param minorTickSpacing
	 *            The space between small ticks
	 * @param paintTicks
	 *            Whether or not to show ticks
	 * @param paintLabels
	 *            Whether or not to show labels
	 * @param changeListener
	 *            A ChangeListener that controls the JSlider
	 * @return A new JSlider with createdFont attributes
	 */
	private JSlider createSlider(int minimumValue, int maximumValue, int currentValue,
			int majorTickSpacing, int minorTickSpacing, boolean paintTicks,
			boolean paintLabels, ChangeListener changeListener) {
		JSlider slider = new JSlider(minimumValue, maximumValue, currentValue);
		slider.setMajorTickSpacing(majorTickSpacing);
		slider.setMinorTickSpacing(minorTickSpacing);
		slider.setPaintTicks(paintTicks);
		slider.setPaintLabels(paintLabels);
		slider.addChangeListener(changeListener);
		return slider;
	}

	/**
	 * Saves important options to a file
	 */
	private void saveOptions() {
		settingsSaver.formatOptions(SettingsSaver.FONT_TYPE, fontType, true);
		settingsSaver.formatOptions(SettingsSaver.FONT_SIZE, fontSize, true);
		settingsSaver.formatOptions(SettingsSaver.FONT_STYLE, fontStyle, true);
		settingsSaver.formatOptions(SettingsSaver.CORRECT_COLOR, correctColor.getRed()
				+ SettingsSaver.SEPARATOR + correctColor.getGreen()
				+ SettingsSaver.SEPARATOR + correctColor.getBlue(), true);
		settingsSaver.formatOptions(SettingsSaver.INCORRECT_COLOR,
				incorrectColor.getRed() + SettingsSaver.SEPARATOR
						+ incorrectColor.getGreen() + SettingsSaver.SEPARATOR
						+ incorrectColor.getBlue(), false);
	}

	/**
	 * Sets the colors to be used in the editor
	 */
	private void setColors() {
		GUI.spellChecker.getTextField().setCorrectColor(correctColor);
		GUI.spellChecker.getTextField().setIncorrectColor(incorrectColor);
		GUI.spellChecker.checkTextArea();
	}

	/**
	 * Set the font of the exampleTextPane
	 */
	private void setFont() {
		exampleTextPane.setFont(createdFont);
	}

	/**
	 * Gets the index of the key in the array
	 * 
	 * @param key
	 *            The value to search for
	 * @param array
	 *            The array to search in
	 * @return Index if found, -1 otherwise
	 */
	private int slowSearch(int key, int[] array) {
		int index = -1;
		for (int i = 0; i < array.length; i++) {
			if (key == array[i]) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Updates the components of the exampleTextPane
	 */
	private void updateTextArea() {
		spellChecker.checkTextArea();
	}

	/**
	 * Sets the correctColor
	 * 
	 * @param correctColor
	 *            The correct color
	 */
	public static void setCorrectColor(Color correctColor) {
		GUI.spellChecker.getTextField().setCorrectColor(correctColor);
	}

	/**
	 * Sets the incorrect color
	 * 
	 * @param incorrectColor
	 *            The incorrect color
	 */
	public static void setIncorrectColor(Color incorrectColor) {
		GUI.spellChecker.getTextField().setIncorrectColor(incorrectColor);
	}

}
