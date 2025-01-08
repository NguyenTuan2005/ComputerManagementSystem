package view.overrideComponent;

import java.awt.*;
import javax.swing.border.Border;

public class RoundedBorder implements Border {
  private int radius;
  private int thickness;
  private Color color;

  public RoundedBorder(int radius, int thickness, Color color) {
    this.radius = radius;
    this.thickness = thickness;
    this.color = color;
  }

  @Override
  public Insets getBorderInsets(Component c) {
    return new Insets(
        this.radius + this.thickness,
        this.radius + this.thickness,
        this.radius + this.thickness,
        this.radius + this.thickness);
  }

  @Override
  public boolean isBorderOpaque() {
    return true;
  }

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(this.color);
    g2.setStroke(new BasicStroke(this.thickness));
    g2.drawRoundRect(x, y, width - 1, height - 1, this.radius, this.radius);
  }
}
