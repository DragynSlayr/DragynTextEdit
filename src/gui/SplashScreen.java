package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class SplashScreen {

	JFrame frame;
	Graphics g;
	JLabel splash;

	public SplashScreen() {
		frame = new JFrame();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Declare the icon that is used for the window
		ImageIcon icon = loadIcon("splash.png");
		icon = resizeImageIcon(icon, screenSize.height / 2,
				screenSize.width / 3, true);

		// Set the basics of the frame
		frame.setTitle("Made By Inderpreet Dhillon");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(icon.getIconWidth(), icon.getIconHeight());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setIconImage(icon.getImage());
		frame.setBackground(Color.BLACK);

		// Create a new JLabel to display the image
		splash = new JLabel();

		// Add the image to the JLabel
		splash.setIcon(icon);

		// Get the graphics
		g = frame.getGraphics();
	}

	/**
	 * Closes splashScreen Frame
	 */
	public void closeFrame() {
		frame.dispose();
	}

	/**
	 * Displays splashScreen
	 */
	public void display() {
		frame.add(splash);
		frame.paintAll(g);
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
			InputStream input = getClass().getResourceAsStream(icon);
			return new ImageIcon(ImageIO.read(input));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Resizes an imageIcon
	 * 
	 * @param icon
	 *            The image icon to resize
	 * @param height
	 *            The new height
	 * @param width
	 *            The new width
	 * @param hasAlpha
	 *            Whether the icon has transparency
	 * @return Resized icon
	 */
	private ImageIcon resizeImageIcon(ImageIcon icon, int height, int width,
			boolean hasAlpha) {
		int imageType = hasAlpha ? BufferedImage.TYPE_INT_ARGB
				: BufferedImage.TYPE_INT_RGB;
		BufferedImage temp = new BufferedImage(width, height, imageType);
		Graphics2D g = temp.createGraphics();
		if (hasAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(icon.getImage(), 0, 0, width, height, null);
		g.dispose();
		return new ImageIcon(temp);
	}
}
