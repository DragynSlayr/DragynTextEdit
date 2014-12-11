package src;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Inderpreet
 */
public class FontMenu {

	private final int frameWidth, frameHeight;
	private final JMenuItem fontMenuItem;
	private JTextField fontExample;
	private JFrame fontFrame;
	private String fontName = Font.SERIF;
	private int fontStyle = Font.PLAIN, fontSize = 20;
	private Font selected = new Font(Font.SERIF, Font.PLAIN, 20);

	public FontMenu(int frameWidth, int frameHeight, String name, int mnemonic,
			String tooltip, KeyStroke keyStroke) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		fontMenuItem = new JMenuItem(name, mnemonic);
		fontMenuItem.setToolTipText(tooltip);
		fontMenuItem.setAccelerator(keyStroke);
		fontMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFontMenu();
			}
		});
	}

	public JMenuItem getMenu() {
		return fontMenuItem;
	}

	private void createFontMenu() {
		final ButtonGroup group = new ButtonGroup();
		fontFrame = new JFrame("Font Settings");
		fontFrame.setSize(frameWidth, frameHeight);
		fontFrame.setLocationRelativeTo(null);
		fontFrame.setVisible(true);
		final JPanel panel = new JPanel(new GridLayout(10, 3), true);
		fontExample = new JTextField();
		fontExample.setFont(selected);
		JLabel fontLabel = new JLabel("Font Type");
		panel.add(fontLabel);
		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton pressed = (JRadioButton) e.getSource();
				if (pressed.getActionCommand().equalsIgnoreCase("serif")) {
					fontName = Font.SERIF;
				} else {
					fontName = Font.SANS_SERIF;
				}
				selected = new Font(fontName, fontStyle, fontSize);
				fontExample.setFont(selected);
			}
		};
		JRadioButton serifButton = new JRadioButton("Serif", true);
		serifButton.addActionListener(buttonListener);
		JRadioButton sansSerifButton = new JRadioButton("Sans Serif", false);
		sansSerifButton.addActionListener(buttonListener);
		panel.add(serifButton);
		panel.add(sansSerifButton);
		group.add(serifButton);
		group.add(sansSerifButton);
		final JLabel sizeLabel = new JLabel("Font Size: " + fontSize);
		panel.add(sizeLabel);
		JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, fontSize);
		sizeSlider.setMajorTickSpacing(10);
		sizeSlider.setMinorTickSpacing(5);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider change = (JSlider) e.getSource();
				fontSize = change.getValue();
				selected = new Font(fontName, fontStyle, fontSize);
				fontExample.setFont(selected);
				sizeLabel.setText("Font Size: " + fontSize);
			}
		});
		panel.add(sizeSlider);
		String[] fontStyles = { "Normal", "Bold", "Italic", "Bold & Italic" };
		final int[] fontStylesInt = { Font.PLAIN, Font.BOLD, Font.ITALIC,
				Font.BOLD + Font.ITALIC };
		final JComboBox<String> fontStylesBox = new JComboBox<String>(
				fontStyles);
		fontStylesBox.setSelectedIndex(0);
		fontStylesBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fontStyle = fontStylesInt[fontStylesBox.getSelectedIndex()];
					selected = new Font(fontName, fontStyle, fontSize);
					fontExample.setFont(selected);
				}
			}
		});
		panel.add(fontStylesBox);
		fontExample.setText("Example Text");
		panel.add(fontExample);
		fontFrame.setContentPane(panel);
		fontFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUI.textBox.setFont(new Font(fontName, fontStyle, fontSize));
			}
		});
	}
}
