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

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;

import spelling.SpellChecker;
import file.Serializer;

/**
 *
 * @author Inderpreet
 */
public class FontMenu {

	private JPanel cards;
	private final String FONT_TYPE = "Font Type";
	private final String FONT_SIZE = "Font Size";
	private final String FONT_STYLE = "Font Style";
	private final String FONT_COLOR = "Font Colors";
	private String fontType = "Serif";
	private int fontSize = 20;
	private int fontStyle = Font.PLAIN;
	private Color correctColor = Color.BLACK;
	private Color incorrectColor = Color.RED;
	private JTextPane exampleField;
	private DefaultStyledDocument document;
	private SpellChecker checker;
	private Serializer serializer;
	private Font selected;

	public FontMenu(String name, int width, int height) {
		// Create a JFrame to hold the panel
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Create a serializer
		serializer = new Serializer();

		// Create a document
		document = new DefaultStyledDocument();

		// Create an editable JTextField to show changes in font
		exampleField = new JTextPane(document);
		exampleField.setText("Example Text");

		// Initialize spellChecker
		checker = new SpellChecker(new TextField(correctColor, incorrectColor,
				exampleField));

		// Add custom keyListener to JTextPane
		exampleField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					checker.checkLastWord();
					break;
				}
			}
		});

		// Set alignment of the exampleField
		StyleConstants.setAlignment(checker.getTextField().getSet(),
				StyleConstants.ALIGN_CENTER);
		document.setParagraphAttributes(0, document.getLength(), checker
				.getTextField().getSet(), false);

		selected = new Font(fontType, fontStyle, fontSize);

		setFont();

		// Initialize cards JPanel, set layout to new CardLayout
		cards = new JPanel(new CardLayout());

		// Create a container to house all components
		Container panel = frame.getContentPane();
		panel.setLayout(new GridLayout(3, 1));

		// Create a cell render to modify the horizontal alignment of some
		// components
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

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
			public void actionPerformed(ActionEvent e) {
				String a = choices.getSelectedItem().toString();
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				switch (a) {
				case FONT_TYPE:
					cardLayout.show(cards, FONT_TYPE);
					break;
				case FONT_SIZE:
					cardLayout.show(cards, FONT_SIZE);
					break;
				case FONT_STYLE:
					cardLayout.show(cards, FONT_STYLE);
					break;
				case FONT_COLOR:
					cardLayout.show(cards, FONT_COLOR);
					break;
				}
			}
		});

		// Add JComboBox to panel
		panel.add(choices);

		// Create a JPanel for fontType with GridLayout
		JPanel typePanel = new JPanel(new GridLayout(1, 2));
		String serif = "Serif";
		String sansSerif = "Sans Serif";

		// Create custom ActionListener for the buttons
		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontType = e.getActionCommand().toString();
				selected = new Font(fontType, fontStyle, fontSize);
				setFont();
			}
		};

		// Create a RadioButton to represent serif option
		JRadioButton serifButton = createJRadioButton(serif,
				fontType.equalsIgnoreCase("Serif"), buttonListener);

		// Create a RadioButton
		JRadioButton sansSerifButton = createJRadioButton(sansSerif,
				fontType.equalsIgnoreCase("Sans Serif"), buttonListener);

		// Create a ButtonGroup for the buttons
		ButtonGroup group = new ButtonGroup();

		// Add the buttons to the ButtonGroup
		group.add(serifButton);
		group.add(sansSerifButton);

		// Add the buttons to the TypePanel
		typePanel.add(serifButton);
		typePanel.add(sansSerifButton);

		// Add the typePanel to the cards panel with FONT_TYPE as a name
		cards.add(typePanel, FONT_TYPE);

		// Create a size JPanel with GridLayout
		JPanel sizePanel = new JPanel(new GridLayout(2, 1));

		// Create a JLabel to show the selected font size
		final JLabel sizeLabel = new JLabel("Font Size: " + fontSize);

		// Set Horizontal alignment of the sizeLabel
		sizeLabel.setHorizontalAlignment(JLabel.CENTER);

		// Create a custom ChangeListener for the JSlider
		ChangeListener sliderListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				fontSize = slider.getValue();
				sizeLabel.setText("Font Size: " + fontSize);
				selected = new Font(fontType, fontStyle, fontSize);
				setFont();
			}
		};

		// Create a size JSlider
		JSlider sizeSlider = createSlider(10, 100, 20, 10, 5, true, true,
				sliderListener);

		// Add sizeLabel and sizeSlider to sizePanel
		sizePanel.add(sizeLabel);
		sizePanel.add(sizeSlider);

		// Add sizePanel to cards panel with FONT_SIZE as name
		cards.add(sizePanel, FONT_SIZE);

		// Create a JPanel for a font types with GridLayout
		JPanel typeJPanel = new JPanel(new GridLayout(1, 1));

		// An array of possible font types
		String[] fontItems = { "Normal", "Bold", "Italic", "Bold & Italic" };

		// Create a JComboBox for fontTypes with fontItems
		JComboBox<String> fontTypes = new JComboBox<String>(fontItems);

		// Set default selection for fontTypes comboBox
		fontTypes.setSelectedIndex(0);

		// Set renderer to custom cellRenderer
		fontTypes.setRenderer(renderer);

		fontTypes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBox = (JComboBox<String>) e.getSource();

				// An array of ints that represents font types
				int[] fontItemsInt = { Font.PLAIN, Font.BOLD, Font.ITALIC,
						Font.BOLD + Font.ITALIC };

				fontStyle = fontItemsInt[comboBox.getSelectedIndex()];

				selected = new Font(fontType, fontStyle, fontSize);

				setFont();
			}
		});

		// Add fontTypes to typePanel
		typeJPanel.add(fontTypes);

		// Add typeJPanel to cards with
		cards.add(typeJPanel, FONT_STYLE);

		// Create color JPanel with GridLayout
		final JPanel colorPanel = new JPanel(new GridLayout(1, 2));

		JButton correctColorButton = new JButton("Choose Correct Color");
		correctColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				correctColor = JColorChooser.showDialog(colorPanel,
						"Choose Correct Color", correctColor);
				if (correctColor != null) {
					checker.getTextField().setCorrectColor(correctColor);
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
					checker.getTextField().setIncorrectColor(incorrectColor);
					updateTextArea();
				}
			}
		});

		// Add buttons to the color panel
		colorPanel.add(correctColorButton);
		colorPanel.add(incorrectColorButton);

		// Add color panel to cards as FONT_COLOR
		cards.add(colorPanel, FONT_COLOR);

		// Add cards to panel
		panel.add(cards);

		// Add exampleField to panel
		panel.add(exampleField);

		// Add custom windowListener to frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GUI.textField.setFont(new Font(fontType, fontStyle, fontSize));
				setColors();
				serializeOptions();
			}
		});
	}

	/**
	 * Set the font of the exampleField
	 */
	private void setFont() {
		exampleField.setFont(selected);
	}

	/**
	 * Creates a JRadioButton
	 * 
	 * @param text
	 *            The String to be displayed on the button
	 * @param selected
	 *            Whether the button is selected or not
	 * @param listener
	 *            An ActionListener that controls the button
	 * @return new JRadioButton with attributes
	 */
	private JRadioButton createJRadioButton(String text, Boolean selected,
			ActionListener listener) {
		JRadioButton button = new JRadioButton(text, selected);
		button.addActionListener(listener);
		button.setVerticalTextPosition(JRadioButton.BOTTOM);
		button.setHorizontalTextPosition(JRadioButton.CENTER);
		button.setHorizontalAlignment(JRadioButton.CENTER);
		return button;
	}

	/**
	 * Creates a JSlider
	 * 
	 * @param min
	 *            The minimum value
	 * @param max
	 *            The maximum value
	 * @param value
	 *            The starting value
	 * @param majorTickSpacing
	 *            The space between big ticks
	 * @param minorTickSpacing
	 *            The space between small ticks
	 * @param paintTicks
	 *            Whether or not to show ticks
	 * @param paintLabels
	 *            Whether or not to show labels
	 * @param listener
	 *            A ChangeListener that controls the JSlider
	 * @return A new JSlider with selected attributes
	 */
	private JSlider createSlider(int min, int max, int value,
			int majorTickSpacing, int minorTickSpacing, boolean paintTicks,
			boolean paintLabels, ChangeListener listener) {
		JSlider slider = new JSlider(min, max, value);
		slider.setMajorTickSpacing(majorTickSpacing);
		slider.setMinorTickSpacing(minorTickSpacing);
		slider.setPaintTicks(paintTicks);
		slider.setPaintLabels(paintLabels);
		slider.addChangeListener(listener);
		return slider;
	}

	/**
	 * Updates the components of the exampleField
	 */
	private void updateTextArea() {
		checker.checkTextArea();
	}

	/**
	 * Sets the colors to be used in the editor
	 */
	private void setColors() {
		GUI.checker.getTextField().setCorrectColor(correctColor);
		GUI.checker.getTextField().setIncorrectColor(incorrectColor);
		GUI.checker.checkTextArea();
	}

	/**
	 * Sets the correctColor
	 * 
	 * @param correctColor
	 *            The correct color
	 */
	public static void setCorrectColor(Color correctColor) {
		GUI.checker.getTextField().setCorrectColor(correctColor);
	}

	/**
	 * Sets the incorrect color
	 * 
	 * @param incorrectColor
	 *            The incorrect color
	 */
	public static void setIncorrectColor(Color incorrectColor) {
		GUI.checker.getTextField().setIncorrectColor(incorrectColor);
	}

	/**
	 * Saves important options to a file
	 */
	private void serializeOptions() {
		serializer.serialize("font", "font", fontType + " " + fontStyle + " "
				+ fontSize, true);
		serializer.serialize("color", "correct", correctColor.getRed() + " "
				+ correctColor.getGreen() + " " + correctColor.getBlue(), true);
		serializer.serialize("color", "incorrect",
				incorrectColor.getRed() + " " + incorrectColor.getGreen() + " "
						+ incorrectColor.getBlue(), false);
	}

}
