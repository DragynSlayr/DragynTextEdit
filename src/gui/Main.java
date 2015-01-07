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
				SplashScreen screen = new SplashScreen();
				screen.display();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				screen.closeFrame();
				new GUI();
			}
		});
	}
}
