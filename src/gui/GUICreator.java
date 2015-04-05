package gui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class GUICreator {

	/**
	 * Creates a new JMenu
	 *
	 * @param name
	 *            The name of the new menu
	 * @param mnemonic
	 *            The shortcut key
	 * @return New JMenu
	 */
	public static JMenu createJMenu(String name, int mnemonic) {
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
	 *            The tool tip lastSavedText
	 * @param keyStroke
	 *            The accelerated key combo
	 * @return New JMenuItem
	 */
	public static JMenuItem createJMenuItem(String name, int mnemonic,
			String tooltip, KeyStroke keyStroke, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(name, mnemonic);
		menuItem.setToolTipText(tooltip);
		menuItem.setAccelerator(keyStroke);
		menuItem.addActionListener(listener);
		return menuItem;
	}

	public static JScrollPane createJScrollPane(Component view) {
		JScrollPane scrollPane = new JScrollPane(view,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(10);
		verticalScrollBar.setBlockIncrement(10);
		return scrollPane;
	}
}
