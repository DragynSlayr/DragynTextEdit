package gui;

import images.ImageHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class SplashScreen {

	public SplashScreen(long time) {
		// Get the size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Declare the icon that is used for the window
		ImageIcon splashIcon = ImageHandler.loadIcon("splash.png");

		// Resize icon
		splashIcon = ImageHandler.resizeImageIcon(splashIcon, screenSize.height / 2,
				screenSize.width / 3, true);

		// Create a new frame
		JFrame splashFrame = setupUI(
				new Dimension(splashIcon.getIconWidth(), splashIcon.getIconHeight()), splashIcon);

		// Create a new JLabel to display the image
		JLabel splashLabel = makeTransparentIconLabel(splashIcon);

		// Display the splash
		display(time, splashFrame, splashLabel);
	}

	/**
	 * Closes splashScreen Frame
	 */
	private void closeSplashFrame(JFrame splashFrame) {
		splashFrame.dispose();
	}

	/**
	 * Displays splash screen
	 * 
	 * @param pauseTime
	 *            Time to display screen
	 * @param splashFrame
	 *            Frame to contain screen
	 * @param splashLabel
	 *            Label with icon
	 */
	private void display(long pauseTime, JFrame splashFrame, JLabel splashLabel) {
		splashFrame.add(splashLabel);
		splashFrame.setBackground(new Color(0, 0, 0, 0));
		splashFrame.paintAll(splashFrame.getGraphics());
		pause(pauseTime);
		closeSplashFrame(splashFrame);
	}

	/**
	 * Creates a JLabel that is transparent
	 * 
	 * @param splashIcon
	 *            The icon to put on the label
	 * @return Transparent JLabel
	 */
	private JLabel makeTransparentIconLabel(ImageIcon splashIcon) {
		JLabel label = new JLabel(splashIcon);
		label.setBackground(new Color(0, 0, 0, 0));
		label.setOpaque(false);
		return label;
	}

	/**
	 * Pauses all operations for a time
	 * 
	 * @param pauseTime
	 *            Time to pause
	 */
	private void pause(long pauseTime) {
		try {
			Thread.sleep(pauseTime);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Sets the parameters of the JFrame
	 * 
	 * @param splashSize
	 *            The size of the label
	 * @param splashIcon
	 *            The icon for the frame
	 * @return A JFrame
	 */
	private JFrame setupUI(Dimension splashSize, ImageIcon splashIcon) {
		JFrame frame = new JFrame("Made By Inderpreet Dhillon");
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setIconImage(splashIcon.getImage());
		frame.setResizable(false);
		frame.setSize(splashSize);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}
}