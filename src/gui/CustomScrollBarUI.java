package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI {
	private static Image thumb;
	private static Image track;

	public CustomScrollBarUI() {
		loadImages("images/thumb.png", "images/track.png");
	}

	public static ComponentUI createUI(JComponent c) {
		return new CustomScrollBarUI();
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle bounds) {
		g.translate(bounds.x, bounds.y);
		AffineTransform transform = AffineTransform.getScaleInstance(
				(double) bounds.width / track.getWidth(null),
				(double) bounds.height / track.getHeight(null));
		((Graphics2D) g).drawImage(track, transform, null);
		g.translate(-bounds.x, -bounds.y);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle bounds) {
		g.translate(bounds.x, bounds.y);
		AffineTransform transform = AffineTransform.getScaleInstance(
				(double) bounds.width / thumb.getWidth(null),
				(double) bounds.height / thumb.getHeight(null));
		((Graphics2D) g).drawImage(thumb, transform, null);
		g.translate(-bounds.x, -bounds.y);
	}

	/**
	 * Loads the track and thumb images
	 * 
	 * @param thumbString
	 *            The location of the thumb
	 * @param trackString
	 *            The location of the track
	 */
	private static void loadImages(String thumbString, String trackString) {
		try {
			thumb = ImageIO.read(new File(thumbString));
			track = ImageIO.read(new File(trackString));
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
}
