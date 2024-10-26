package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

// create a logo, avatar in circle
public class CircularImage extends JLabel {
	private BufferedImage circularImage;
	private int width, height;

	public CircularImage(String imagePath, int width, int height) {
		this.width = width;
		this.height = height;
		try {
			BufferedImage originalImage = ImageIO.read(new File(imagePath));
			circularImage = createCircularImage(originalImage, width, height);
			setIcon(new ImageIcon(circularImage)); // Đặt hình ảnh tròn làm Icon
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedImage createCircularImage(BufferedImage image, int width, int height) {
		int diameter = Math.min(width, height);
		BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mask.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Tạo mặt nạ hình tròn
		g2d.fill(new Ellipse2D.Double(0, 0, diameter, diameter));
		g2d.dispose();

		// Áp dụng mặt nạ để tạo ảnh tròn
		BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		g2d = circularImage.createGraphics();
		g2d.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));
		g2d.drawImage(image, 0, 0, diameter, diameter, null);
		g2d.dispose();

		return circularImage;
	}
}