package view.OtherComponent;

import view.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel containerPanel;
    private JScrollPane scrollPane;

    public MainFrame() {
        setTitle("Giao diện với Panel chứa thanh cuộn");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Chỉ set layout một lần
        containerPanel = new JPanel(new GridBagLayout());
        mainPanel = new JPanel(new BorderLayout());

        scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);






        JButton addButton = new JButton("Thêm Panel Mới");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel pn = createNewPanel("src/main/java/Icon/laptopAsus1.jpg",250,250,"Laptop vip pro",9999.9);
                addNewPanel(pn);
            }
        });

        JPanel pn = createNewPanel("src/main/java/Icon/laptopAsus1.jpg",250,250,"Laptop Asus vip pro",9999.9);
        addNewPanel(pn);
        JButton delBt = new JButton("Delete");
        delBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removePanelByTitle("Laptop vip pro");
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(addButton, BorderLayout.SOUTH);
        mainPanel.add(delBt, BorderLayout.NORTH);

        add(mainPanel);
    }

    // Phương thức thêm panel mới mà không thay đổi layout
    private void addNewPanel(JPanel panel) {

        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = containerPanel.getComponentCount() % 3;
        gbc.gridy = containerPanel.getComponentCount() / 3;
        gbc.insets = new Insets(5, 5, 5, 5);

        containerPanel.add(panel, gbc);
        containerPanel.revalidate();
        containerPanel.repaint();
    }
    private JPanel createNewPanel(String filePath, int width, int height, String title, double price) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5); // Khoảng cách giữa các thành phần

        // Hàng đầu tiên: Hình ảnh sản phẩm (căn giữa)
        JLabel image = createImageForProduct(filePath, width, height);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Chiếm toàn bộ chiều rộng
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(image, gbc);

        // Hàng thứ hai: Tên sản phẩm (căn giữa)
        JLabel productName = new JLabel(title);
        productName.setFont(Style.FONT_TEXT_CUSTOMER);
        gbc.gridy = 1;
        panel.add(productName, gbc);

        // Hàng thứ ba: Giá sản phẩm (căn trái)
        JLabel productPrice = new JLabel("$" + price);
        productPrice.setFont(new Font("Arial", Font.BOLD, 25));
        productPrice.setForeground(new Color(53, 159, 31));
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Chỉ chiếm một cột
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(productPrice, gbc);

        // Hàng thứ tư: Nút "Add to Cart" (căn trái)
        JButton addToCart = new JButton("Add to Cart");
        gbc.gridy = 3;
        panel.add(addToCart, gbc);

        // Hàng thứ năm: Nút "More Information" (căn trái)
        JButton moreInfo = new JButton("More Information");
        gbc.gridy = 4;
        panel.add(moreInfo, gbc);

        return panel;
    }
    private void removePanelByTitle(String title) {
        for (Component comp : containerPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JLabel) {
                        JLabel label = (JLabel) innerComp;
                        String labelText = label.getText(); // Lấy văn bản của JLabel
                        if (labelText != null && labelText.equals(title)) { // Kiểm tra null trước khi so sánh
                            containerPanel.remove(panel); // Xóa panel
                            containerPanel.revalidate();
                            containerPanel.repaint();
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Không tìm thấy panel có tiêu đề: " + title);
    }


    private static JLabel createImageForProduct(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage(); // Lấy Image từ ImageIcon
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel lbImage = new JLabel(scaledIcon);
        lbImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

