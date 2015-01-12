package gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

public class SplashScreen {

	private JProgressBar bar;
	private long startTime;

	public SplashScreen(long time) {
		// Get the size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Declare the icon that is used for the window
		ImageIcon icon = loadIcon("splash.png");

		// Resize icon
		icon = resizeImageIcon(icon, screenSize.height / 2,
				screenSize.width / 3, true);

		// Create a new JLabel to display the image
		JLabel splash = makeTransparentIconLabel(icon);

		// Create a progress bar
		bar = new JProgressBar(0, (int) time);

		// Set colors of the bar
		bar.setForeground(Color.GREEN);
		bar.setBackground(Color.WHITE);

		// Create a new frame
		final JFrame frame = setupUI(
				new Dimension(icon.getIconWidth(), icon.getIconHeight() + bar.getHeight()), icon);
		
		// Add bar to frame
		frame.add(bar, BorderLayout.SOUTH);

		// Create a thread for the progress bar
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (frame.isVisible()) {
					long now = System.currentTimeMillis();
					int timeElapsed = (int) (now - startTime);
					bar.setForeground(Color.GREEN);
					bar.setBackground(Color.WHITE);
					bar.setValue(timeElapsed);
					bar.paint(bar.getGraphics());
				}
			}
		});

		// Start the thread
		t.start();

		// Display the splash
		display(time, frame, splash);
	}

	/**
	 * Closes splashScreen Frame
	 */
	private void closeFrame(JFrame frame) {
		frame.dispose();
	}

	/**
	 * Displays splash screen
	 * 
	 * @param time
	 *            Time to display screen
	 * @param frame
	 *            Frame to contain screen
	 * @param splash
	 *            Label with icon
	 */
	private void display(long time, JFrame frame, JLabel splash) {
		frame.add(splash, BorderLayout.NORTH);
		frame.paintAll(frame.getGraphics());
		pause(time);
		closeFrame(frame);
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
	 * Creates a JLabel that is transparent
	 * 
	 * @param icon
	 *            The icon to put on the label
	 * @return Transparent JLabel
	 */
	private JLabel makeTransparentIconLabel(ImageIcon icon) {
		JLabel label = new JLabel(icon);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);
		return label;
	}

	/**
	 * Pauses all operations for a time
	 * 
	 * @param time
	 *            Time to pause
	 */
	private void pause(long time) {
		startTime = System.currentTimeMillis();
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
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

	/**
	 * Sets the parameters of the JFrame
	 * 
	 * @param size
	 *            The size of the label
	 * @param icon
	 *            The icon for the frame
	 * @return A JFrame
	 */
	private JFrame setupUI(Dimension size, ImageIcon icon) {
		JFrame frame = new JFrame("Made By Inderpreet Dhillon");
		frame.setAlwaysOnTop(true);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setIconImage(icon.getImage());
		frame.setResizable(false);
		frame.setSize(size);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}
}
