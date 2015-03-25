package gui;

import javax.swing.SwingUtilities;

/**
 *
 * @author Inderpreet
 */
public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SplashScreen(2000);
				new GUI();
			}
		});
	}
}
