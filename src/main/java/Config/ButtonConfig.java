package Config;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import view.OverrideComponent.CustomButton;
import view.Style;

public class ButtonConfig {

  public static final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);
  public static final Color BUTTON_COLOR = new Color(41, 128, 185);

  public static void setButtonIcon(String url, JButton that, int gap) {
    ImageIcon iconButton = new ImageIcon(url);
    Image image = iconButton.getImage();
    Dimension buttonSize = that.getPreferredSize();
    Image resizedImage =
        image.getScaledInstance(
            buttonSize.height - gap,
            buttonSize.height - gap,
            java.awt.Image.SCALE_SMOOTH); // Resize
    that.setIcon(new ImageIcon(resizedImage));
  }

  public static void setButtonIcon(ImageIcon icon, JButton that, int gap) {
    Image image = icon.getImage();
    Dimension buttonSize = that.getPreferredSize();

    int imageWidth = image.getWidth(null);
    int imageHeight = image.getHeight(null);

    int btWidth = buttonSize.width - gap;
    int btHeight = buttonSize.height - gap;

    double widthRatio = (double) btWidth / imageWidth;
    double heightRatio = (double) btHeight / imageHeight;
    double scale = Math.min(widthRatio, heightRatio);

    int newWidth = (int) (imageWidth * scale);
    int newHeight = (int) (imageHeight * scale);

    Image resizedImage = image.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
    that.setIcon(new ImageIcon(resizedImage));
  }

  public static void setStyleButton(
      JButton that,
      Font font,
      Color textColor,
      Color backgroundColor,
      int textPosition,
      Dimension size) {
    that.setBackground(backgroundColor);
    that.setFont(font);
    that.setVerticalTextPosition(SwingConstants.BOTTOM);
    that.setHorizontalTextPosition(textPosition);
    that.setBorderPainted(false);
    that.setForeground(textColor);
    that.setFocusable(false);
    that.setPreferredSize(size);
  }

  public static JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setForeground(Color.WHITE);
    button.setBackground(BUTTON_COLOR);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
  }

  public static void setIcon(String url, JButton that) {
    ImageIcon iconButton = new ImageIcon(url);
    Image image = iconButton.getImage();
    Dimension buttonSize = that.getPreferredSize();
    Image resizedImage =
        image.getScaledInstance(
            buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
    that.setIcon(new ImageIcon(resizedImage));
  }

  public static JSeparator createVerticalSeparator(int w, int h) {
    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
    separator.setPreferredSize(new Dimension(w, h));
    return separator;
  }

  public static JSeparator createVerticalSeparator() {
    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
    separator.setPreferredSize(new Dimension(2, 70));
    return separator;
  }

  public static void addButtonHoverEffect(JButton button, Color hover, Color defaultColor) {
    button.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            button.setBackground(hover);
          }

          @Override
          public void mouseExited(MouseEvent e) {
            button.setBackground(defaultColor);
          }
        });
  }

  public static JButton createEditFieldButton(JTextField textField) {
    JButton editBt = new JButton();
    editBt.setIcon(new ImageIcon("src/main/java/Icon/penIcon1.png"));
    editBt.setPreferredSize(new Dimension(45, 40));

    Color hoverBackground = new Color(130, 180, 230); // Màu sáng hơn khi hover

    editBt.setBackground(Style.LIGHT_BlUE);
    editBt.setFocusPainted(false);
    editBt.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
    editBt.setContentAreaFilled(true);

    editBt.addMouseListener(
        new MouseAdapter() {
          public void mouseEntered(MouseEvent evt) {
            editBt.setBackground(hoverBackground);
          }

          public void mouseExited(MouseEvent evt) {
            editBt.setBackground(Style.LIGHT_BlUE);
          }
        });

    editBt.addMouseListener(
        new MouseAdapter() {
          public void mousePressed(MouseEvent evt) {
            editBt.setBackground(hoverBackground.darker());
          }

          public void mouseReleased(MouseEvent evt) {
            editBt.setBackground(hoverBackground);
          }
        });

    editBt.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            textField.setEditable(!textField.isEditable());
            textField.setForeground(textField.isEditable() ? Color.BLACK : Color.GRAY);
          }
        });

    return editBt;
  }

  public static JButton createShowPasswdButton(JPasswordField passwordField) {
    JButton toggleButton = new JButton();
    toggleButton.setBackground(Style.LIGHT_BlUE);
    toggleButton.setFocusPainted(false);
    toggleButton.setFocusable(false);
    toggleButton.setBorder(
        BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
    ImageIcon showPasswd = new ImageIcon("src/main/java/Icon/showPasswd_Icon.png");
    ImageIcon hidePasswd = new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png");

    toggleButton.setIcon(showPasswd);
    toggleButton.addMouseListener(
        new MouseAdapter() {
          public void mouseEntered(MouseEvent evt) {
            toggleButton.setBackground(new Color(130, 180, 230));
          }

          public void mouseExited(MouseEvent evt) {
            toggleButton.setBackground(Style.LIGHT_BlUE);
          }
        });

    toggleButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (passwordField.getEchoChar() != '\u0000') {
              passwordField.setEchoChar('\u0000');
              toggleButton.setIcon(hidePasswd);
            } else {
              passwordField.setEchoChar('*');
              toggleButton.setIcon(showPasswd);
            }
          }
        });
    return toggleButton;
  }

  public static CustomButton createCustomButton(String text) {
    CustomButton button = new CustomButton(text);
    FontMetrics metrics = button.getFontMetrics(button.getFont());
    int textWidth = metrics.stringWidth(text);
    int padding = 40;
    button.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GRAY);
    button.setBackgroundColor(Style.LIGHT_BlUE);
    button.setBorderRadius(20);
    button.setPreferredSize(new Dimension(textWidth + padding, 50));

    return button;
  }

  public static CustomButton createCustomButton(
      String title,
      Font font,
      Color textColor,
      Color backgroundColor,
      Color hoverColor,
      int radius,
      int textPosition,
      Dimension size) {
    CustomButton button = new CustomButton(title);
    button.setFont(font);
    button.setTextColor(textColor);
    button.setBackgroundColor(backgroundColor);
    button.setHoverColor(hoverColor);
    button.setHorizontalAlignment(textPosition);
    button.setBorderRadius(radius);
    button.setDrawBorder(false);
    button.setPreferredSize(size);
    return button;
  }

  public static CustomButton createCustomButton(
      String title,
      Font font,
      Color textColor,
      Color backgroundColor,
      Color hoverColor,
      Color borderColor,
      int thickness,
      int radius,
      int textPosition,
      Dimension size) {
    CustomButton bt = new CustomButton(title);
    bt.setFont(font);
    bt.setTextColor(textColor);
    bt.setBackgroundColor(backgroundColor);
    bt.setHoverColor(hoverColor);
    bt.setBorderColor(borderColor);
    bt.setBorderThickness(thickness);
    bt.setBorderRadius(radius);
    bt.setHorizontalAlignment(textPosition);
    bt.setPreferredSize(size);
    return bt;
  }

  public static CustomButton createCustomButtonGradientBorder(
      String title,
      Font font,
      Color textColor,
      Color backgroundColor,
      Color startColor,
      Color endColor,
      int thickness,
      int radius,
      Dimension size) {
    CustomButton gradientButton = new CustomButton(title);
    gradientButton.setFont(font);
    gradientButton.setForeground(textColor);
    gradientButton.setGradientColors(startColor, endColor);
    gradientButton.setBackgroundColor(backgroundColor);
    gradientButton.setPreferredSize(size);
    gradientButton.setBorderRadius(radius);
    gradientButton.setBorderThickness(thickness);
    return gradientButton;
  }
}
