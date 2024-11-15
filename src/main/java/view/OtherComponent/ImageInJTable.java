package view.OtherComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ImageInJTable {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTable with Images");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Tạo model cho bảng
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Image"}, 0);
        JTable table = new JTable(model);

        // Tạo một vài hình ảnh mẫu với kích thước được điều chỉnh
        ImageIcon icon1 = resizeImageIcon("src/main/java/img/james.JPG", 50, 50); // Kích thước 50x50
        ImageIcon icon2 = resizeImageIcon("src/main/java/img/james_avata.jpg", 50, 50);

        // Thêm dữ liệu vào model, bao gồm cả hình ảnh
        model.addRow(new Object[]{1, "User 1", icon1});
        model.addRow(new Object[]{2, "User 2", icon2});

        // Thiết lập renderer cho cột ảnh
        table.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
        table.setRowHeight(100);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    // Phương thức để thay đổi kích thước ImageIcon
    public static ImageIcon resizeImageIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Custom renderer để hiển thị hình ảnh trong ô
    public static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
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

