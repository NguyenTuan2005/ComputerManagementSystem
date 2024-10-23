package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class CustomerMainPanel extends JPanel {
    LoginFrame loginFrame;
    CardLayout cardLayout;
    WelcomePanel welcomePanel;
    ProductCatalogPanel productCatalogPanel;
    NotificationPanel notificationPanel;
    PurchasedPanel purchasedPanel;


    static final String WELCOME_CONSTRAINT = "welcome";
    static final String PRODUCT_CATALOG_CONSTRAINT = "catalog";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String PURCHASED_CONSTRAINT = "purchased";
    static final String CHANGE_INFORMATION_CONSTRAINT = "changeInformation";

    //constructor
    public CustomerMainPanel(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        cardLayout = new CardLayout();
        welcomePanel = new WelcomePanel();
        productCatalogPanel = new ProductCatalogPanel();
        notificationPanel = new NotificationPanel();
        purchasedPanel = new PurchasedPanel();
        setLayout(cardLayout);
        add(welcomePanel, WELCOME_CONSTRAINT);
        add(productCatalogPanel, PRODUCT_CATALOG_CONSTRAINT);
        add(purchasedPanel, PURCHASED_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);

        cardLayout.show(this, WELCOME_CONSTRAINT);
    }

    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
    }

    class WelcomePanel extends JPanel {
        JLabel welcomeLabel, userNameForWelcome;

        public WelcomePanel() {
            setLayout(new BorderLayout());

            welcomeLabel = new JLabel("Welcome Manager :)", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
            welcomeLabel.setForeground(Style.BACKGROUND_COLOR);

            add(welcomeLabel, BorderLayout.CENTER);
        }
    }

    class ProductCatalogPanel extends JPanel {
        ToolPanel toolPanel;
        DisplayProductPanel displayProductPanel;

        JButton cartBt, searchBt;
        JLabel shopName;
        JTextField searchTextField;

        public ProductCatalogPanel() {
            setLayout(new BorderLayout());
            toolPanel = new ToolPanel();
            displayProductPanel = new DisplayProductPanel();
            add(toolPanel, BorderLayout.NORTH);
            add(displayProductPanel, BorderLayout.CENTER);
        }

        class ToolPanel extends JPanel {
            public ToolPanel() {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 0;

                shopName = new JLabel("Computer Shop");
                shopName.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                shopName.setFont(new Font("Arial", Font.BOLD, 45));
                gbc.gridx = 0;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 15, 0, 20);
                gbc.anchor = GridBagConstraints.LINE_START;
                add(shopName, gbc);

                searchTextField = new JTextField("Search");
                searchTextField.setPreferredSize(new Dimension(320, 40));
                searchTextField.setFont(Style.FONT_TEXT_CUSTOMER);
                searchTextField.setForeground(Color.GRAY);
                // Thêm FocusListener để kiểm soát khi người dùng nhấn và rời khỏi JTextField
                searchTextField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                        if (searchTextField.getText().equals("Search")) {
                            searchTextField.setText("");
                            searchTextField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                        if (searchTextField.getText().isEmpty()) {
                            searchTextField.setForeground(Color.GRAY);
                            searchTextField.setText("Search");
                        }
                    }
                });


                gbc.gridx = 1;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(10, 0, 0, 0);
                add(searchTextField, gbc);

                searchBt = new JButton();
                setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.WORD_COLOR_WHITE,SwingConstants.LEFT, new Dimension(68, 40));
                setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                gbc.gridx = 2;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.insets = new Insets(10, 0, 0, 0);
                add(searchBt, gbc);

                cartBt = new JButton("Cart");
                setStyleButton(cartBt, Style.FONT_BUTTON_CUSTOMER, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.WORD_COLOR_WHITE,SwingConstants.LEFT, new Dimension(80, 80));
                setIconBigButton("src/main/java/Icon/cartIcon.png", cartBt);
                cartBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                cartBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                gbc.gridx = 3;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.LINE_END;
                gbc.insets = new Insets(5, 15, 0, 10);
                add(cartBt, gbc);
            }
        }

        class DisplayProductPanel extends JPanel {
            JPanel containerPanel;
            JScrollPane scrollPane;
            ArrayList<JPanel> panelList = new ArrayList<>() ;
            DisplayProductPanel() {
                setLayout(new BorderLayout());
                //panel chính chứa các panel khác
                containerPanel = new JPanel();
                containerPanel.setLayout(new GridLayout(6, 3, 5, 5));

                for (int i = 1; i <= 18; i++) {
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(300, 400)); // kích thước của 1 ô hiện sp
//                    JLabel productImage = new JLabel("Product " + i);
//                    panel.add(productImage);
                    if(i%2==0){
                        panel.setBackground(Color.GRAY);
                    }

                    panelList.add(panel);
                    containerPanel.add(panel);
                }
                // tao sp dau tien hehe:))

                ImageIcon icon = new ImageIcon("src/main/java/Icon/laptopAsus1.jpg");
                Image img = icon.getImage(); // Lấy Image từ ImageIcon
                Image scaledImg = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
                ImageIcon scaledIcon = new ImageIcon(scaledImg);

                JLabel productImage = new JLabel(scaledIcon);
                productImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel productName = new JLabel("Laptop asus vip pro max super ultra");
                productName.setFont(Style.FONT_TEXT_CUSTOMER);
                productName.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel productPrice = new JLabel("$" +2000);
                productPrice.setFont(new Font("Arial",1,25));
                productPrice.setForeground(new Color(100, 218, 77));


                panelList.get(0).setLayout(new BoxLayout(panelList.get(0), BoxLayout.Y_AXIS));
                panelList.get(0).add(productImage);
                panelList.get(0).add(productName);
                panelList.get(0).add(productPrice);



                scrollPane = new JScrollPane(containerPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                add(scrollPane, BorderLayout.CENTER);
            }

        }
    }

    class PurchasedPanel extends JPanel {

        public PurchasedPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("PurchasedPanel is Coming soonnnnnnn!"));
        }
    }

    class NotificationPanel extends JPanel {

        public NotificationPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("Cart is Coming soonnnnnnn!"));
        }
    }

    private void setIconBigButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 35, buttonSize.height - 35, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    private void setIconSmallButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 10, buttonSize.height - 10, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    private static void setStyleButton(JButton that, Font font,Color textColor, Color backgroundColor, int textPosition, Dimension size) {
        that.setBackground(backgroundColor);
        that.setFont(font);
        that.setHorizontalAlignment(textPosition);
        that.setBorderPainted(false);
        that.setForeground(textColor);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }


}
