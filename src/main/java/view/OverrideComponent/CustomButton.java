package view.OverrideComponent;

import java.awt.*;
import javax.swing.*;

public class CustomButton extends JButton {
  private Color borderColor = Color.BLUE;
  private Color startGradientColor = Color.CYAN;
  private Color endGradientColor = Color.BLUE;
  private Color backgroundColor = new Color(230, 245, 255);
  private Color hoverColor;
  private Color textColor = Color.BLACK;
  private int thickness = 3;
  private int borderRadius = 15;
  private boolean drawBorder = true;

  public CustomButton(String text) {
    super(text);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setForeground(textColor);
    setFont(new Font("Arial", Font.BOLD, 16));
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (getModel().isPressed()) {
      g.setColor(backgroundColor.darker());
    } else if (getModel().isRollover() && hoverColor != null) {
      g.setColor(hoverColor);
    } else {
      g.setColor(backgroundColor);
    }
    g.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
    super.paintComponent(g);
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (drawBorder) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setStroke(new BasicStroke(thickness));

      GradientPaint gradient =
          new GradientPaint(
              0, 0, startGradientColor, getWidth(), getHeight(), endGradientColor, true);
      g2d.setPaint(gradient);
      g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, borderRadius, borderRadius);
    }
  }

  public void setTextColor(Color color) {
    this.textColor = color;
    setForeground(color);
    repaint();
  }

  public void setBorderColor(Color color) {
    this.borderColor = color;
    this.startGradientColor = color;
    this.endGradientColor = color;
    repaint();
  }

  public void setBorderThickness(int thickness) {
    this.thickness = thickness;
    repaint();
  }

  public void setGradientColors(Color startColor, Color endColor) {
    this.startGradientColor = startColor;
    this.endGradientColor = endColor;
    repaint();
  }

  public void setBackgroundColor(Color color) {
    this.backgroundColor = color;
    repaint();
  }

  public void setHoverColor(Color color) {
    this.hoverColor = color;
    repaint();
  }

  public void setBorderRadius(int radius) {
    this.borderRadius = radius;
    repaint();
  }

  public void setDrawBorder(boolean drawBorder) {
    this.drawBorder = drawBorder;
    repaint();
  }
}
