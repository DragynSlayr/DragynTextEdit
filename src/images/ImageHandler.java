package images;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageHandler {
	/**
	 * Loads an icon
	 * 
	 * @param icon
	 *            The path to the icon
	 * @return The icon
	 */
	public static ImageIcon loadIcon(String icon) {
		try {

			InputStream fileInputStream = ImageHandler.class
					.getResourceAsStream(icon);
			return new ImageIcon(ImageIO.read(fileInputStream));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Resizes an imageIcon
	 * 
	 * @param imageIcon
	 *            The image icon to resize
	 * @param newHeight
	 *            The new height
	 * @param newWidth
	 *            The new width
	 * @param hasAlpha
	 *            Whether the icon has transparency
	 * @return Resized icon
	 */
	public static ImageIcon resizeImageIcon(ImageIcon imageIcon, int newHeight,
			int newWidth, boolean hasAlpha) {
		int imageType = hasAlpha ? BufferedImage.TYPE_INT_ARGB
				: BufferedImage.TYPE_INT_RGB;
		
		BufferedImage temp = new BufferedImage(newWidth, newHeight, imageType);
		
		Graphics2D g = temp.createGraphics();
		
		if (hasAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		
		g.drawImage(imageIcon.getImage(), 0, 0, newWidth, newHeight, null);
		
		g.dispose();
		
		return new ImageIcon(temp);
	}

	/**
	 * Gets all icons
	 * 
	 * @return the icons
	 */
	public static ArrayList<Image> getAllIconsAsImages() {
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(loadIcon("icon16.png").getImage());
		icons.add(loadIcon("icon32.png").getImage());
		icons.add(loadIcon("icon64.png").getImage());
		icons.add(loadIcon("icon128.png").getImage());
		return icons;
	}
}
