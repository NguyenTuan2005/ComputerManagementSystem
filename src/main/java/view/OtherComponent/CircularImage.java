package view.OtherComponent;

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
	private boolean isAvatar;

	public CircularImage(String imagePath, int width, int height, boolean isAvatar) {
		this.width = width;
		this.height = height;
		this.isAvatar = isAvatar;
		try {
			BufferedImage originalImage = ImageIO.read(new File(imagePath));
			BufferedImage tickImage = ImageIO.read(new File("src/main/java/Icon/img_2.png")); // Tải ảnh dấu tích xanh
			circularImage = createCircularImage(originalImage, tickImage, width, height);
			setIcon(new ImageIcon(circularImage)); // Đặt hình ảnh tròn làm Icon
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private BufferedImage createCircularImage(BufferedImage image, BufferedImage tickImage, int width, int height) {
		int diameter = Math.min(width, height);

		// Tăng độ phân giải ảnh trước khi tạo hình tròn
		int highResDiameter = diameter * 2;
		BufferedImage resizedImage = new BufferedImage(highResDiameter, highResDiameter, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gResized = resizedImage.createGraphics();
		gResized.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gResized.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		gResized.drawImage(image, 0, 0, highResDiameter, highResDiameter, null);
		gResized.dispose();

		// Tạo ảnh tròn từ ảnh đã resize
		BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = circularImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));
		g2d.drawImage(resizedImage, 0, 0, diameter, diameter, null);

		// Thêm đường viền
		g2d.setClip(null);
		g2d.setStroke(new BasicStroke(3)); // Độ dày của đường viền
		g2d.setColor(Color.white); // Màu đường viền
		g2d.draw(new Ellipse2D.Double(1.5, 1.5, diameter - 3, diameter - 3)); // Vẽ đường viền

		// Chèn dấu tích xanh vào góc dưới bên phải, đè lên đường viền
		if(isAvatar) {
			int tickSize = diameter / 3; // Kích thước dấu tích
			int x = diameter-10 - tickSize / 2; // Tọa độ x
			int y = diameter-10  - tickSize / 2; // Tọa độ y
			g2d.drawImage(tickImage, x, y, tickSize, tickSize, null); // Vẽ dấu tích
			g2d.dispose();
			return circularImage;
		}
		return circularImage;
	}
}


