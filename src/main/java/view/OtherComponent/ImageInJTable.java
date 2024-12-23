package view.OtherComponent;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageInJTable {

  // Phương thức để thay đổi kích thước ImageIcon
  public static ImageIcon resizeImageIcon(String path, int width, int height) {
    ImageIcon originalIcon = new ImageIcon(path);
    Image resizedImage =
        originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
  }

  // Custom renderer để hiển thị hình ảnh trong ô
  public static class ImageRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (value instanceof ImageIcon) {
        JLabel label = new JLabel();
        label.setIcon((ImageIcon) value);
        return label;
      } else {
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      }
    }
  }
}
