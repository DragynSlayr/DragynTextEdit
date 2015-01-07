package gui;

import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class SplashScreen {

	JLabel splash;
	Graphics g;
	JFrame frame;

	public SplashScreen() {
		frame = new JFrame();

		// Declare the icon that is used for the window
		ImageIcon icon = loadIcon("icon.jpg");

		// Set the basics of the frame
		frame.setTitle("Made By Inderpreet Dhillon");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(icon.getIconWidth(), icon.getIconHeight());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setIconImage(icon.getImage());

		// Create a new JLabel to display the image
		splash = new JLabel();

		// Add the image to the JLabel
		splash.setIcon(icon);

		// Get the graphics
		g = frame.getGraphics();
	}

	/**
	 * Displays splashScreen
	 */
	public void display() {
		frame.add(splash);
		frame.paintAll(g);
	}

	/**
	 * Closes splashScreen Frame
	 */
	public void closeFrame() {
		frame.dispose();
	}

	/**
	 * Loads an icon
	 * 
	 * @param icon
	 *            The path to the icon
	 * @return The icon
	 */
	private ImageIcon loadIcon(String icon) {
		try {
			return new ImageIcon(ImageIO.read(new File(icon)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
