package gui;

import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Inderpreet
 */
public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				manageLookAndFeel();

				new SplashScreen(2000);
				new GUI();
			}
		});
	}

	/**
	 * Set the theme of the program
	 */
	private static void manageLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException
				| InstantiationException | IllegalAccessException e) {
			System.out.println("Could not apply theme");
		}
	}
}
