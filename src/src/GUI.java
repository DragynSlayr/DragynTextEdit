package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Inderpreet
 */
public class GUI extends JFrame {

    private static JPanel panel;
    private static JTextPane textBox;
    private static DefaultStyledDocument document;
    private static SimpleAttributeSet set;
    private static final Dictionary dict = new Dictionary();
    private static ArrayList dictionary;
    private static Font font;
    private static final FileOperations fileOps = new FileOperations();
    private static boolean usedEnter = false;
    private static Color correctColor, incorrectColor;
    JTextField fontExample;
    private String fontName = Font.SERIF;
    private int fontStyle = Font.PLAIN, fontSize = 20;
    private Font selected = new Font(Font.SERIF, Font.PLAIN, 20);

    public GUI() {
        //Set the basics of the frame
        super("Dragyn TextEdit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);

        //Create a font
        font = new Font(Font.SERIF, Font.PLAIN, 20);

        //Create a set
        set = new SimpleAttributeSet();

        //Set colors
        correctColor = Color.BLACK;
        incorrectColor = Color.RED;

        //Creates dictionary
        dictionary = dict.getDictionary();

        setupUI();
    }

    /**
     * Sets up the components of the GUI
     */
    private void setupUI() {
        //Create the JPanel
        panel = new JPanel(new BorderLayout(), true);
        add(panel);

        //Create a JMenuBar
        JMenuBar menuBar = new JMenuBar();

        //Create a file JMenu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //Create an exit JMenuItem
        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_E);
        exitItem.setToolTipText("Exit application");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Create a save JMenuItem
        JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveItem.setToolTipText("Save text to a file");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "//Desktop");
                fileChooser.setDialogTitle("Save");

                int userSelection = fileChooser.showSaveDialog(panel);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File toSave = fileChooser.getSelectedFile();
                    fileOps.write(toSave, textBox.getText());

                    JOptionPane.showMessageDialog(panel, "File has been saved", "File Saved", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        //Create a load JMenuItem
        JMenuItem loadItem = new JMenuItem("Load", KeyEvent.VK_L);
        loadItem.setToolTipText("Load text from a file");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "//Desktop");
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

        //Create a edit JMenu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        //Create a font JMenuItem
        JMenuItem fontMenuItem = new JMenuItem("Font", KeyEvent.VK_F);
        fontMenuItem.setToolTipText("Change font settings");
        fontMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ButtonGroup group = new ButtonGroup();

                final JFrame fontFrame = new JFrame("Font Settings");
                fontFrame.setSize(getWidth(), getHeight());
                fontFrame.setLocationRelativeTo(null);
                fontFrame.setVisible(true);

                final JPanel panel = new JPanel(new GridLayout(10, 3), true);

                fontExample = new JTextField();
                fontExample.setFont(selected);

                JLabel fontLabel = new JLabel("Font Type");
                panel.add(fontLabel);

                ActionListener buttonListener = new ActionListener() {
                    @Override
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
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        JSlider change = (JSlider) e.getSource();
                        fontSize = change.getValue();
                        selected = new Font(fontName, fontStyle, fontSize);
                        fontExample.setFont(selected);
                        sizeLabel.setText("Font Size: " + fontSize);
                    }
                });

                panel.add(sizeSlider);

                String[] fontStyles = {"Normal", "Bold", "Italic", "Bold & Italic"};
                final int[] fontStylesInt = {Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC};

                final JComboBox fontStylesBox = new JComboBox(fontStyles);
                fontStylesBox.setSelectedIndex(0);
                fontStylesBox.addItemListener(new ItemListener() {
                    @Override
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

                fontFrame.addWindowListener(new WindowListener() {

                    @Override
                    public void windowOpened(WindowEvent e) {
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("Closing");
                        selected = new Font(fontName, fontStyle, fontSize);
                        textBox.setFont(selected);
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {
                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {
                    }

                    @Override
                    public void windowActivated(WindowEvent e) {
                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {
                    }
                });

            }
        }
        );

        //Create a help JMenu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        //Create a help JMenuItem
        JMenuItem helpItem = new JMenuItem("Help", KeyEvent.VK_L);
        helpItem.setToolTipText("Help");
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel, "Type as you would normally and the program will find and" + "\n"
                        + " mark incorrectly spelled words upon pressing \"Space\"", "Help Message", JOptionPane.PLAIN_MESSAGE);
            }
        });

        //Add JMenuItems to JMenu
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        editMenu.add(fontMenuItem);
        helpMenu.add(helpItem);

        //Add Menus to JMenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);

        //Set JMenuBar
        setJMenuBar(menuBar);

        //Create a document
        document = new DefaultStyledDocument();

        //Create the JTextArea
        textBox = new JTextPane(document);
        textBox.setFont(font);
        textBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String event = KeyEvent.getKeyText(e.getKeyCode());
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        updateLastWord();
                        break;
                    case KeyEvent.VK_ENTER:
                        usedEnter = true;
                        updateLastWord();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        //Add the JTextArea
        panel.add(textBox);

        //Create the JScrollPane
        JScrollPane scrollPane = new JScrollPane(textBox);
        panel.add(scrollPane);

        //Sets content
        setContentPane(panel);
    }

    /**
     * Updates the main text area
     */
    private static void updateTextArea() {
        try {
            String[] split = textBox.getText().split(" ");
            ArrayList<Word> words = new ArrayList<>();
            for (String s : split) {
                words.add(new Word(s));
            }
            textBox.setText("");
            for (Word word : words) {
                if (word.isWord(dictionary)) {
                    StyleConstants.setForeground(set, correctColor);
                    document.insertString(document.getLength(), word.getWord() + " ", set);
                } else {
                    StyleConstants.setForeground(set, incorrectColor);
                    document.insertString(document.getLength(), word.getWord() + " ", set);
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
                if (usedEnter) {
                    docLength = document.getLength();
                } else {
                    docLength = document.getLength() - 1;
                }
                int length = split[split.length - 1].length() - 1;
                if (usedEnter) {
                    StyleConstants.setForeground(set, Color.BLACK);
                }
                if (word.isWord(dictionary)) {
                    StyleConstants.setForeground(set, Color.BLACK);
                    document.replace(docLength - length, word.getWord().length(), word.getWord(), set);
                } else {
                    StyleConstants.setForeground(set, Color.RED);
                    document.replace(docLength - length, word.getWord().length(), word.getWord(), set);
                }
            } catch (BadLocationException ble) {
                System.out.println("Couldn't replace string");
            }
        }
        usedEnter = false;
    }
}
