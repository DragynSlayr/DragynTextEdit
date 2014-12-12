package src;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Inderpreet
 */
public class FontMenu {

	JPanel cards;
	final String FONT_TYPE = "Font Type";
	final String FONT_SIZE = "Font Size";
	final String FONT_STYLE = "Font Style";
	String fontType = "Serif";
	int fontSize = 20;
	int fontStyle = Font.PLAIN;
	JTextField exampleField;

	public FontMenu(String name, int width, int height) {
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		exampleField = new JTextField("Example Text");
		exampleField.setHorizontalAlignment(JTextField.CENTER);
		setFont();

		cards = new JPanel(new CardLayout());

		Container panel = frame.getContentPane();
		panel.setLayout(new GridLayout(3, 1));

		String[] items = { FONT_TYPE, FONT_SIZE, FONT_STYLE };
		final JComboBox<String> choices = new JComboBox<String>(items);
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		choices.setRenderer(renderer);
		choices.setSelectedIndex(0);
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
				}
			}
		});
		panel.add(choices);

		JPanel typePanel = new JPanel(new GridLayout(1, 2));
		String serif = "Serif";
		String sansSerif = "Sans Serif";
		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontType = e.getActionCommand().toString();
				setFont();
			}
		};
		JRadioButton serifButton = new JRadioButton(serif, true);
		serifButton.addActionListener(buttonListener);
		serifButton.setVerticalTextPosition(JRadioButton.BOTTOM);
		serifButton.setHorizontalTextPosition(JRadioButton.CENTER);
		serifButton.setHorizontalAlignment(JRadioButton.CENTER);
		JRadioButton sansSerifButton = new JRadioButton(sansSerif, false);
		sansSerifButton.addActionListener(buttonListener);
		sansSerifButton.setVerticalTextPosition(JRadioButton.BOTTOM);
		sansSerifButton.setHorizontalTextPosition(JRadioButton.CENTER);
		sansSerifButton.setHorizontalAlignment(JRadioButton.CENTER);
		ButtonGroup group = new ButtonGroup();
		group.add(serifButton);
		group.add(sansSerifButton);
		typePanel.add(serifButton);
		typePanel.add(sansSerifButton);

		cards.add(typePanel, FONT_TYPE);

		JPanel sizePanel = new JPanel(new GridLayout(2, 1));
		final JLabel sizeLabel = new JLabel("Font Size: " + fontSize);
		sizeLabel.setHorizontalAlignment(JLabel.CENTER);
		final JSlider sizeSlider = new JSlider(10, 100, 20);
		sizeSlider.setMajorTickSpacing(10);
		sizeSlider.setMinorTickSpacing(5);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				fontSize = sizeSlider.getValue();
				sizeLabel.setText("Font Size: " + fontSize);
				setFont();
			}
		});
		sizePanel.add(sizeLabel);
		sizePanel.add(sizeSlider);

		cards.add(sizePanel, FONT_SIZE);

		JPanel typeJPanel = new JPanel(new GridLayout(1, 1));
		String[] fontItems = { "Normal", "Bold", "Italic", "Bold & Italic" };
		final int[] fontItemsInt = { Font.PLAIN, Font.BOLD, Font.ITALIC,
				Font.BOLD + Font.ITALIC };
		final JComboBox<String> fontTypes = new JComboBox<String>(fontItems);
		fontTypes.setSelectedIndex(0);
		fontTypes.setRenderer(renderer);
		fontTypes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				fontStyle = fontItemsInt[fontTypes.getSelectedIndex()];
				setFont();
			}
		});
		typeJPanel.add(fontTypes);

		cards.add(typeJPanel, FONT_STYLE);

		panel.add(cards);

		panel.add(exampleField);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GUI.textBox.setFont(new Font(fontType, fontStyle, fontSize));
			}
		});
	}

	public void setFont() {
		Font selected = new Font(fontType, fontStyle, fontSize);
		exampleField.setFont(selected);
	}
}
