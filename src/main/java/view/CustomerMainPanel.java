package view;

import Model.Product;
import controller.ProductController;
import view.OtherComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class CustomerMainPanel extends JPanel {
    JPanel containerPanel;
    OldLoginFrame loginFrame;
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
    public CustomerMainPanel() {
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

            welcomeLabel = new JLabel("Welcome Customer:)", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
            welcomeLabel.setForeground(Style.BACKGROUND_COLOR);

            JButton bt = new JButton("Let's get started  -->");
            setStyleButton(bt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(300, 50));
            bt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showPanel(PRODUCT_CATALOG_CONSTRAINT);
                }
            });
            add(bt, BorderLayout.SOUTH);
            add(welcomeLabel, BorderLayout.CENTER);
        }
    }

    class ProductCatalogPanel extends JPanel {
        ToolPanel toolPanel;
        DisplayProductPanel displayProductPanel;
        JButton cartBt, searchBt;
        JLabel shopName;
        JTextField searchTextField;
        private static ProductController productController = new ProductController();
        private static ArrayList<Product> productsAll = productController.getAll();

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
                setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(68, 40));
                setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                gbc.gridx = 2;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.insets = new Insets(10, 0, 0, 0);
                add(searchBt, gbc);

                cartBt = new JButton("Cart");
                setStyleButton(cartBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(80, 80));
                setIconBigButton("src/main/java/Icon/cartIcon.png", cartBt);
                cartBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                cartBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                cartBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        displayProductPanel.showProduct("oneProduct");
                    }
                });
                gbc.gridx = 3;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.LINE_END;
                gbc.insets = new Insets(5, 15, 0, 10);
                add(cartBt, gbc);
            }
        }

        class DisplayProductPanel extends JPanel {
            JScrollPane scrollPane;
            JPanel product1;
            CardLayout cardLayoutDisplayProduct;
            DisplayProductList displayProductList = new DisplayProductList();
            DisplaySingleProductPn displaySingleProductPn = new DisplaySingleProductPn();

            DisplayProductPanel() {
                cardLayoutDisplayProduct = new CardLayout();
                setLayout(cardLayoutDisplayProduct);

                add(displayProductList, "list");
                add(displaySingleProductPn, "oneProduct");

                cardLayoutDisplayProduct.show(this, "list");

            }

            public void showProduct(String title) {
                cardLayoutDisplayProduct.show(this, title);
            }


            class DisplayProductList extends JPanel {
                DisplayProductList() {
                    //panel chính chứa các panel khác
                    setLayout(new BorderLayout());
                    containerPanel = new JPanel(new GridBagLayout());

                    String[] filePaths = {"src/main/java/Icon/laptopAsus1.jpg", "src/main/java/img/MacBook_Air_M2_2023.jpg", "src/main/java/img/Acer_Predator_Helios_300.jpg"};
                    String[] filePaths1 = {"src/main/java/Icon/laptopAsus1.jpg", "src/main/java/img/MacBook_Air_M2_2023.jpg", "src/main/java/img/Acer_Predator_Helios_300.jpg", "src/main/java/img/Asus_VivoBook_S15.jpg"};
                    Product product1 = new Product(1, "Asus Ultra Vip Pro", 30, 8888, "Apple M2", "Apple", "Apple", "Apple M2", "512GB SSD", "8GB", "China", "in stock");
                    JPanel p1 = createNewPanel(filePaths, product1);
                    addNewPanelToContainer(p1);

                    JPanel[] panels = new JPanel[10];


                    for (int i = 0; i < panels.length; i++) {
                        panels[i] = createNewPanel(filePaths1, productsAll.get(i));
                        addNewPanelToContainer(panels[i]);
                    }


                    scrollPane = new JScrollPane(containerPanel);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    add(scrollPane, BorderLayout.CENTER);

                }
            }
            class DisplaySingleProductPn extends JPanel {
                JLabel image, productName, price, status;
                JButton addToCart, backBt, previousBt, nextBt;
                Product product;

                DisplaySingleProductPn() {
//                    this.product =product;
                    setLayout(new GridLayout(1, 3));
                    JPanel imagePn = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);

                    backBt = new JButton("Back");// button back to catalog screen
                    backBt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showProduct("list");
                        }
                    });
                    JPanel backPn = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    backPn.add(backBt);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 1;
                    imagePn.add(backPn, gbc);

                    image = new JLabel(createImageForProduct("src/main/java/Icon/laptopAsus1.jpg", 400, 400));// create image of product
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 4;
                    imagePn.add(image, gbc);

                    // 2 button switching between product image
                    previousBt = new JButton("Previous");
                    setStyleButton(previousBt, Style.FONT_BUTTON_CUSTOMER, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, SwingConstants.CENTER, new Dimension(150, 30));
                    nextBt = new JButton("Next");
                    setStyleButton(nextBt, Style.FONT_BUTTON_CUSTOMER, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, SwingConstants.CENTER, new Dimension(150, 30));
                    JPanel switchPn = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    switchPn.add(previousBt);
                    switchPn.add(nextBt);
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    gbc.gridwidth = 4;
                    imagePn.add(switchPn, gbc);
                    add(imagePn);

                    // display more details of product
                    JPanel productDetailsPn = new JPanel(new GridLayout(8, 1));
                    productDetailsPn.setPreferredSize(new Dimension(200, 500));

                    JLabel spaceLabel = new JLabel("");
                    spaceLabel.setPreferredSize(new Dimension(200, 60));
                    productDetailsPn.add(spaceLabel);

                    String[] attributes = {"Brand: ", "Operating System: ", "CPU: ", "Memory: ", "RAM: ", "Made In: ", "Genre: "};

                    productName = new JLabel("<html>" + " Asus Ultra Vip pro super hehe :O " + "<html>");
                    productName.setFont(Style.FONT_BUTTON_LOGIN_FRAME);
                    productDetailsPn.add(productName);

                    for (int i = 0; i < attributes.length; i++) {
                        JLabel lb = new JLabel(attributes[i] + ": " + "unknown");
                        lb.setFont(Style.FONT_TEXT_LOGIN_FRAME);
                        productDetailsPn.add(lb);
                    }
                    add(productDetailsPn);
                    //payment pn
                    JPanel paymentPn = new JPanel(new BoxLayout(this,BoxLayout.Y_AXIS));

                    price = new JLabel("$ "+" 100000000");
                    price.setFont(Style.FONT_BUTTON_LOGIN_FRAME);
                    price.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                    paymentPn.add(price);

                    status = new JLabel("<html>Status: <br>"+"in stock"+"<html>");
                    status.setFont(Style.FONT_BUTTON_LOGIN_FRAME);

                    paymentPn.add(status);


                }
            }

            class AddToCartPn extends JPanel {
                JButton backBt;


                AddToCartPn() {


                }
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

    // duy
    private static void setStyleButton(JButton that, Font font, Color textColor, Color backgroundColor, int textPosition, Dimension size) {
        that.setBackground(backgroundColor);
        that.setFont(font);
        that.setHorizontalAlignment(textPosition);
        that.setBorderPainted(false);
        that.setForeground(textColor);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }

    // tạo ảnh cho sản phẩm
    private static ImageIcon createImageForProduct(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage(); // Lấy Image từ ImageIcon
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        return new ImageIcon(scaledImg);
    }

    public void addNewPanelToContainer(JPanel panel) {
        panel.setPreferredSize(new Dimension(320, 550));
        panel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = this.containerPanel.getComponentCount() % 3;
        gbc.gridy = this.containerPanel.getComponentCount() / 3;
        gbc.insets = new Insets(2, 2, 2, 2);

        this.containerPanel.add(panel, gbc);
        this.containerPanel.revalidate();
        this.containerPanel.repaint();
    }

    // panel chứa thông tin từng sản phẩm
    public JPanel createNewPanel(String[] filePaths, Product product) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 10, 2, 10);

        ImageIcon[] images = new ImageIcon[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            images[i] = createImageForProduct(filePaths[i], 300, 300);
        }

        JLabel imageLabel = new JLabel(images[0]); // Hiển thị hình ảnh đầu tiên
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Chiếm toàn bộ chiều rộng
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(imageLabel, gbc);

        JButton previousButton = new JButton("<Previous");
        setStyleButton(previousButton, Style.FONT_SIZE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, SwingConstants.CENTER, new Dimension(60, 20));
        JButton nextButton = new JButton("Next>");
        setStyleButton(nextButton, Style.FONT_SIZE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, SwingConstants.CENTER, new Dimension(60, 20));

        // Biến để theo dõi chỉ số hình ảnh hiện tại
        int[] currentIndex = {0};

        // Thêm ActionListener cho nút "Previous"
        previousButton.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + images.length) % images.length; // Cập nhật chỉ số
            imageLabel.setIcon(images[currentIndex[0]]); // Cập nhật hình ảnh
        });

        // Thêm ActionListener cho nút "Next"
        nextButton.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % images.length; // Cập nhật chỉ số
            imageLabel.setIcon(images[currentIndex[0]]); // Cập nhật hình ảnh
        });
        JPanel switchPn = new JPanel(new GridLayout(1, 2));
        switchPn.add(previousButton);
        switchPn.add(nextButton);

        // Thêm nút vào panel
        gbc.gridy++;
        gbc.gridwidth = 2; // Chiếm toàn bộ chiều rộng
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(switchPn, gbc);

        // Từ hàng thứ hai: thông tin sản phẩm (căn giữa)
        JLabel productName = new JLabel("<html>" + product.getName() + "<html>");// product Name
        productName.setFont(Style.FONT_TITLE_PRODUCT);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy++;
        panel.add(productName, gbc);

        // Thêm thông tin sản phẩm khác
        gbc.gridy++;
        panel.add(new JLabel("Brand: " + product.getBrand()), gbc);
        gbc.gridy++;
        panel.add(new JLabel("CPU: " + product.getCpu()), gbc);
        gbc.gridy++;
        panel.add(new JLabel("RAM: " + product.getRam()), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Storage capacity: " + product.getMemory()), gbc);

        JLabel productPrice = new JLabel("$ " + product.getPrice());
        productPrice.setFont(new Font("Arial", Font.BOLD, 25));
        productPrice.setForeground(Style.DELETE_BUTTON_COLOR_RED);
        gbc.gridy++;
        gbc.gridwidth = 1; // Chỉ chiếm một cột
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(productPrice, gbc);

        JButton detailBt = new JButton("More details");
        setStyleButton(detailBt, Style.FONT_HEADER_ROW_TABLE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(80, 30));
        detailBt.addActionListener(e -> {
            this.productCatalogPanel.displayProductPanel.showProduct("oneProduct");
        });
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(detailBt, gbc);


        JButton addToCartBt = new JButton("Add to Cart");
        setStyleButton(addToCartBt, Style.FONT_HEADER_ROW_TABLE, Color.white, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.CENTER, new Dimension(80, 30));
        addToCartBt.addActionListener(e -> {
            ToastNotification.showToast("Product added to Cart!", 3000);
        });
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(addToCartBt, gbc);

        return panel;
    }


}
