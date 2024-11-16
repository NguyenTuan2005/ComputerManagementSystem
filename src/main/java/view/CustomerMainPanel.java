package view;

import Model.Customer;
import Model.Product;
import view.OverrideComponent.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerMainPanel extends JPanel {
    JPanel containerCatalog;
    JPanel containerCart = new JPanel(new GridBagLayout());
    JPanel notificationContainer;

    CardLayout cardLayout;
    WelcomePanel welcomePanel;
    ProductCatalogMainPanel productCatalogPanel;
    NotificationPanel notificationPanel;
    OrderHistoryPanel purchasedPanel;
    ChangeInfoPanel changeInfoPanel;

    private static final Color DARK_BLUE = new Color(0, 75, 150);
    private static final Color MEDIUM_BLUE = new Color(51, 153, 255);
    private static final Color HOVER_BLUE = new Color(30, 144, 255);
    private static final Color LIGHT_BLUE = new Color(235, 245, 255);

    static final String WELCOME_CONSTRAINT = "welcome";
    static final String PRODUCT_CATALOG_CONSTRAINT = "catalog";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String PURCHASED_CONSTRAINT = "purchased";
    static final String CHANGE_INFORMATION_CONSTRAINT = "changeInformation";

    //constructor
    public CustomerMainPanel() {
        cardLayout = new CardLayout();
        welcomePanel = new WelcomePanel();
        productCatalogPanel = new ProductCatalogMainPanel();
        purchasedPanel = new OrderHistoryPanel();
        notificationPanel = new NotificationPanel();
        changeInfoPanel = new ChangeInfoPanel();

        setLayout(cardLayout);
        add(welcomePanel, WELCOME_CONSTRAINT);
        add(productCatalogPanel, PRODUCT_CATALOG_CONSTRAINT);
        add(purchasedPanel, PURCHASED_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);
        add(changeInfoPanel, CHANGE_INFORMATION_CONSTRAINT);

        cardLayout.show(this, PRODUCT_CATALOG_CONSTRAINT);
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

            JButton bt = new JButton("Let's get started!");
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

    class ProductCatalogMainPanel extends JPanel {
        ProductCatalogInnerPn productCatalogInnerPn = new ProductCatalogInnerPn();
        CartPanel cartPanel = new CartPanel();
        ToolPanel toolPanel;
        DisplayProductPanel displayProductPanel;
        JButton searchBt;
        private CustomButton cartButton, allBt, gaming, office, pcCase, cheapest, luxury, selectedButton;
        JLabel shopName;
        JTextField searchTextField;


        CardLayout cardLayoutMain = new CardLayout();
//        private static ProductController productController = new ProductController();
//        private static ArrayList<Product> productsAll = productController.getAll();

        public ProductCatalogMainPanel() {
            setLayout(cardLayoutMain);
            add(productCatalogInnerPn, "catalog");
            add(cartPanel, "cart");

            cardLayoutMain.show(this, "catalog");
        }

        public void show(String title) {
            cardLayoutMain.show(this, title);
        }

        class ProductCatalogInnerPn extends JPanel {
            ProductCatalogInnerPn() {
                setLayout(new BorderLayout());
                toolPanel = new ToolPanel();
                displayProductPanel = new DisplayProductPanel();
                add(toolPanel, BorderLayout.NORTH);
                add(displayProductPanel, BorderLayout.CENTER);
            }

        }

        class ToolPanel extends JPanel {

            public ToolPanel() {
                setLayout(new BorderLayout());
                // search product and cart button
                JPanel searchBar = new JPanel();
                searchBar.setLayout(new GridBagLayout());
                searchBar.setBackground(Color.WHITE);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 0;

                shopName = new JLabel("Computer Shop");
                shopName.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                shopName.setFont(Style.FONT_TITLE_BOLD_55);
                gbc.gridx = 0;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 15, 0, 20);
                gbc.anchor = GridBagConstraints.LINE_START;
                searchBar.add(shopName, gbc);

                searchTextField = new JTextField("Search");
                searchTextField.setPreferredSize(new Dimension(320, 40));
                searchTextField.setFont(Style.FONT_TEXT_CUSTOMER);
                searchTextField.setForeground(Color.GRAY);
                searchTextField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
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
                searchBar.add(searchTextField, gbc);

                searchBt = new JButton();
                setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(68, 40));
                setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                gbc.gridx = 2;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.insets = new Insets(10, 0, 0, 0);
                searchBar.add(searchBt, gbc);

                cartButton = createCustomButtonGradientBorder("Cart", Style.FONT_BUTTON_CUSTOMER, Color.WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.MENU_BUTTON_COLOR_GREEN, Style.BACKGROUND_COLOR, 4, 20, new Dimension(80, 80));
                cartButton.setIcon(new ImageIcon("src/main/java/Icon/cart_Icon.png"));
                cartButton.setHorizontalTextPosition(SwingConstants.CENTER);
                cartButton.setVerticalTextPosition(SwingConstants.BOTTOM);
                cartButton.addActionListener(e -> {
                    ProductCatalogMainPanel.this.show("cart");
                });


                gbc.gridx = 3;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.LINE_END;
                gbc.insets = new Insets(5, 15, 0, 10);
                searchBar.add(cartButton, gbc);

                //tab bar to display products by order
                JPanel TabBar = new JPanel();
                TabBar.setLayout(new FlowLayout(FlowLayout.LEFT));
                TabBar.setBackground(Color.WHITE);

                allBt = createCustomButtonWithBorder("All", Style.FONT_SIZE_MENU_BUTTON, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                allBt.addActionListener(e -> updateSelectedButton(allBt));

                gaming = createCustomButtonWithBorder("Laptop Gaming", Style.FONT_SIZE_MENU_BUTTON, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(150, 25));
                gaming.addActionListener(e -> updateSelectedButton(gaming));

                office = createCustomButtonWithBorder("Laptop Office", Style.FONT_SIZE_MENU_BUTTON, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(150, 25));
                office.addActionListener(e -> updateSelectedButton(office));

                pcCase = createCustomButtonWithBorder("PC Case", Style.FONT_SIZE_MENU_BUTTON, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                pcCase.addActionListener(e -> updateSelectedButton(pcCase));

                cheapest = createCustomButtonWithBorder("Cheapest", Style.FONT_SIZE_MENU_BUTTON, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                cheapest.addActionListener(e -> updateSelectedButton(cheapest));

                luxury = createCustomButtonWithBorder("Luxury", Style.FONT_SIZE_MENU_BUTTON, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                luxury.addActionListener(e -> updateSelectedButton(luxury));

                TabBar.add(allBt);
                TabBar.add(gaming);
                TabBar.add(office);
                TabBar.add(pcCase);
                TabBar.add(luxury);
                TabBar.add(cheapest);


                add(searchBar, BorderLayout.CENTER);
                add(TabBar, BorderLayout.SOUTH);
            }
            private void updateSelectedButton(CustomButton button) {
                Color defaultColor = Color.WHITE;
                Color selectedColor = Style.BACKGROUND_COLOR;

                // Đặt lại màu cho button trước đó nếu có
                if (selectedButton != null) {
                    selectedButton.setBackgroundColor(defaultColor);
                    selectedButton.setHoverColor(Style.LIGHT_BlUE);
                }

                // Đặt màu cho button mới được chọn và cập nhật selectedButton
                button.setBackgroundColor(selectedColor);
                button.setHoverColor(selectedColor);
                selectedButton = button;
            }

        }

        class DisplayProductPanel extends JPanel {
            JScrollPane scrollPane;
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
                    containerCatalog = new JPanel(new GridBagLayout());
                    containerCatalog.setBackground(Color.WHITE);

                    String[] filePaths = {"src/main/java/Icon/laptopAsus1.jpg", "src/main/java/img/MacBook_Air_M2_2023.jpg", "src/main/java/img/Acer_Predator_Helios_300.jpg"};
                    String[] filePaths1 = {"src/main/java/Icon/laptopAsus1.jpg", "src/main/java/img/MacBook_Air_M2_2023.jpg", "src/main/java/img/Acer_Predator_Helios_300.jpg", "src/main/java/img/Asus_VivoBook_S15.jpg"};
                    Product product1 = new Product(1, "Asus Ultra Vip Pro", 30, 8888, "Apple M2", "Apple", "Apple", "Apple M2", "512GB SSD", "8GB", "China", "in stock", "demo", "demo", "demo", "demo", 1);
                    JPanel p1 = createPanelForProductInCatalog(filePaths, product1);
                    addNewPanelToCatalogContainer(p1);

                    JPanel[] panels = new JPanel[10];

                    // tạo các panel sản phầm
//                    for (int i = 0; i < panels.length; i++) {
//                        panels[i] = createPanelForProductInCatalog(filePaths1, productsAll.get(i));
//                        addNewPanelToCatalogContainer(panels[i]);
//                    }


                    scrollPane = new JScrollPane(containerCatalog);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                        @Override
                        protected void configureScrollBarColors() {
                            this.thumbColor = Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE;
                            this.trackColor = Color.WHITE;
                        }
                    });
                    add(scrollPane, BorderLayout.CENTER);

                }
            }

            class DisplaySingleProductPn extends JPanel {
                JLabel image, productName, price, status;
                JButton backBt, previousBt, nextBt;
                CustomButton addToCart, buyNowBt;
                Product product;
                ImageIcon[] images;
                String[] filePaths = {"src/main/java/Icon/laptopAsus1.jpg", "src/main/java/img/MacBook_Air_M2_2023.jpg", "src/main/java/img/Acer_Predator_Helios_300.jpg", "src/main/java/img/Asus_VivoBook_S15.jpg"};

                DisplaySingleProductPn() {
//                    this.product =product;
                    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

                    JPanel imagePn = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);

                    backBt = new JButton("Back");// button back to catalog screen
                    setStyleButton(backBt, Style.FONT_SIZE_BUTTON, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(100, 30));
                    setIconSmallButton("src/main/java/Icon/back_Icon1.png", backBt);
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


                    images = new ImageIcon[filePaths.length];
                    for (int i = 0; i < filePaths.length; i++) {
                        images[i] = createImageForProduct(filePaths[i], 420, 420);
                    }

                    image = new JLabel(createImageForProduct("src/main/java/Icon/laptopAsus1.jpg", 420, 420));// create image of product
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 4;
                    imagePn.add(image, gbc);

                    // 2 button switching between product image
                    previousBt = new JButton("Previous");
                    setStyleButton(previousBt, Style.FONT_BUTTON_CUSTOMER, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, SwingConstants.CENTER, new Dimension(150, 30));
                    nextBt = new JButton("Next");
                    setStyleButton(nextBt, Style.FONT_BUTTON_CUSTOMER, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, SwingConstants.CENTER, new Dimension(150, 30));
                    int[] currentIndex = {0};

                    // Thêm ActionListener cho nút "Previous"
                    previousBt.addActionListener(e -> {
                        currentIndex[0] = (currentIndex[0] - 1 + images.length) % images.length; // Cập nhật chỉ số
                        image.setIcon(images[currentIndex[0]]); // Cập nhật hình ảnh
                    });

                    // Thêm ActionListener cho nút "Next"
                    nextBt.addActionListener(e -> {
                        currentIndex[0] = (currentIndex[0] + 1) % images.length; // Cập nhật chỉ số
                        image.setIcon(images[currentIndex[0]]); // Cập nhật hình ảnh
                    });

                    JPanel switchPn = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    switchPn.add(previousBt);
                    switchPn.add(nextBt);
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    gbc.gridwidth = 4;
                    imagePn.add(switchPn, gbc);
                    add(imagePn);

                    // display more details of product
                    JPanel productDetailsPn = new JPanel();
                    productDetailsPn.setLayout(new BoxLayout(productDetailsPn, BoxLayout.Y_AXIS));
                    productDetailsPn.setPreferredSize(new Dimension(180, 200));

                    productName = new JLabel("<html>Asus Ultra Vip Pro Super:O</html>");
                    productName.setFont(Style.FONT_TITLE_PRODUCT_30);
                    productDetailsPn.add(productName);
                    productDetailsPn.add(Box.createVerticalStrut(10));

                    String[] attributes = {"Brand", "Operating System", "CPU", "Memory", "RAM", "Made In", "Genre"};
                    for (String attribute : attributes) {
                        JLabel lb = new JLabel("<html><b>" + attribute + "</b>" + ": unknown" + "</html>");
                        lb.setFont(Style.FONT_TEXT_CUSTOMER);
                        productDetailsPn.add(lb);
                        productDetailsPn.add(Box.createVerticalStrut(10));
                    }
                    add(productDetailsPn);

                    //payment pn
                    JPanel paymentPn = new JPanel();
                    paymentPn.setLayout(new GridBagLayout());
                    GridBagConstraints gbc2 = new GridBagConstraints();
                    gbc2.insets = new Insets(10, 5, 10, 5); // Khoảng cách giữa các thành phần
                    gbc2.fill = GridBagConstraints.HORIZONTAL; // Để các thành phần chiếm toàn bộ chiều ngang của ô

                    price = new JLabel("$ " + "100000000");
                    price.setFont(Style.FONT_TITLE_PRODUCT_30);
                    price.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                    gbc2.gridx = 0;
                    gbc2.gridy = 0;
                    gbc2.gridwidth = 2;
                    paymentPn.add(price, gbc2);

                    gbc2.insets = new Insets(5, 5, 5, 5);
                    JLabel statuslb = new JLabel("Status: ");
                    statuslb.setFont(Style.FONT_TEXT_CUSTOMER);
                    gbc2.gridy = 1;
                    gbc2.gridwidth = 1; // Chỉ chiếm 1 ô ngang
                    paymentPn.add(statuslb, gbc2);

                    status = new JLabel("In Stock");
                    status.setFont(Style.FONT_BUTTON_LOGIN_FRAME);
                    status.setForeground("In Stock".equals("In Stock") ? Style.CONFIRM_BUTTON_COLOR_GREEN : Style.DELETE_BUTTON_COLOR_RED);
                    gbc2.gridy = 2;
                    gbc2.gridwidth = 2; // Chiếm toàn bộ chiều ngang
                    paymentPn.add(status, gbc2);

                    gbc2.insets = new Insets(10, 5, 10, 5);
                    addToCart = createCustomButton("Add to Cart", Style.FONT_TITLE_PRODUCT_18, Color.white, Style.CONFIRM_BUTTON_COLOR_GREEN, new Color(162, 236, 132), SwingConstants.CENTER, new Dimension(200, 50));
                    addToCart.addActionListener(e -> ToastNotification.showToast("Product added to Cart!", 3000, 350, 60));
                    gbc2.gridy = 3;
                    paymentPn.add(addToCart, gbc2);

                    buyNowBt = createCustomButton("Buy Now", Style.FONT_TITLE_PRODUCT_18, Color.white, new Color(227, 138, 97), new Color(227, 178, 148), SwingConstants.CENTER, new Dimension(200, 50));
                    gbc2.gridy = 4;
                    paymentPn.add(buyNowBt, gbc2);

                    add(paymentPn);


                    add(paymentPn);
                }
            }

        }

        class CartPanel extends JPanel {
            JButton backBt;
            MainPaymentPanel mainPaymentPanel;
            JScrollPane scrollPane;
            private int totalItemCount = 0;
            private int totalPriceCount = 9999;

            CartPanel() {
                setLayout(new BorderLayout());
                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(Color.WHITE);
                titlePanel.setPreferredSize(new Dimension(800, 80));
                titlePanel.setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

                backBt = new JButton("Back");
                setStyleButton(backBt, Style.FONT_BUTTON_CUSTOMER, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(105, 30));
                setIconSmallButton("src/main/java/Icon/back_Icon1.png", backBt);
                backBt.addActionListener(e -> {
                    ProductCatalogMainPanel.this.show("catalog");
                });
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                titlePanel.add(backBt, gbc);

                JLabel title = new JLabel("Your Shopping Cart", SwingConstants.CENTER);
                title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                title.setFont(Style.FONT_TITLE_BOLD_40);
                gbc.gridx = 1;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.CENTER;
                titlePanel.add(title, gbc);

                add(titlePanel, BorderLayout.NORTH);

                mainPaymentPanel = new MainPaymentPanel();
                add(mainPaymentPanel, BorderLayout.CENTER);


            }

            class MainPaymentPanel extends JPanel {
                PaymentPanel paymentPanel = new PaymentPanel();
                ProductsInCartPanel productsInCartPanel = new ProductsInCartPanel();

                MainPaymentPanel() {
                    setLayout(new BorderLayout());
                    add(paymentPanel, BorderLayout.EAST);
                    add(productsInCartPanel, BorderLayout.CENTER);
                }
            }

            class PaymentPanel extends JPanel {
                private JTextField emailField, nameField, addressField;
                private JLabel totalItem, totalPrice;
                private CustomButton payBt;

                PaymentPanel() {
                    setLayout(new GridBagLayout());
                    setPreferredSize(new Dimension(320, 300));
                    setBackground(Color.WHITE);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.weightx = 1.0;

                    JLabel title = new JLabel("Delivery Information");
                    title.setFont(Style.FONT_TITLE_PRODUCT);
                    title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    add(title, gbc);

                    emailField = createTextFieldWithPlaceholder("Enter Your Email", Style.FONT_TEXT_CUSTOMER, new Dimension(300, 45));
                    gbc.gridy = 1;
                    add(emailField, gbc);

                    nameField = createTextFieldWithPlaceholder("Enter Your Name", Style.FONT_TEXT_CUSTOMER, new Dimension(300, 45));
                    gbc.gridy = 2;
                    add(nameField, gbc);


                    addressField = createTextFieldWithPlaceholder("Enter Your Address", Style.FONT_TEXT_CUSTOMER, new Dimension(300, 45));
                    gbc.gridy = 4;
                    add(addressField, gbc);

                    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                    separator.setOrientation(SwingConstants.HORIZONTAL);

                    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
                    gbc.gridy = 5;
                    add(separator, gbc);

                    JLabel cartTotal = new JLabel("Cart Total");
                    cartTotal.setFont(Style.FONT_TITLE_PRODUCT);
                    cartTotal.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                    gbc.gridy = 6;
                    add(cartTotal, gbc);


//                    totalItem = new JLabel(totalItemCount + " Items in your Cart");
                    totalItem = new JLabel("<html> <font color='green'>" + totalItemCount + "</font> Items in your Cart</html>");
                    totalItem.setFont(Style.FONT_BUTTON_PAY);
                    gbc.gridy = 7;
                    add(totalItem, gbc);


                    totalPrice = new JLabel("<html> <font color='black'>Total:  </font> <font color='red'>$" + totalPriceCount + "</font></html>");
                    totalPrice.setFont(Style.FONT_BUTTON_PAY);
                    gbc.gridy = 8;
                    add(totalPrice, gbc);

                    payBt = createCustomButtonGradientBorder("PAY", Style.FONT_TITLE_PRODUCT_30, new Color(14, 163, 204), Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN, 3, 25, new Dimension(250, 50));
                    payBt.setHorizontalAlignment(SwingConstants.CENTER);
                    payBt.setAlignmentX(Component.CENTER_ALIGNMENT);
                    payBt.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (emailField.getText().isEmpty() || emailField.getText().equals("Enter Your Email")) {
                                emailField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                emailField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                            }
                            if (nameField.getText().isEmpty() || nameField.getText().equals("Enter Your Name")) {
                                nameField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                nameField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                            }
                            if (addressField.getText().isEmpty() || addressField.getText().equals("Enter Your Address")) {
                                addressField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                addressField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                            }

                        }
                    });
                    gbc.gridy = 9;
                    add(payBt, gbc);
                }
            }

            class ProductsInCartPanel extends JPanel {

                ProductsInCartPanel() {
                    setLayout(new BorderLayout());

                    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    headerPanel.setBackground(Color.WHITE);
                    JLabel title = new JLabel("     Product", JLabel.CENTER);
                    title.setFont(Style.FONT_TITLE_PRODUCT_18);
                    headerPanel.add(title);


                    String[] filePaths = {"src/main/java/Icon/laptopAsus1.jpg", "src/main/java/img/MacBook_Air_M2_2023.jpg", "src/main/java/img/Acer_Predator_Helios_300.jpg"};
                    Product product1 = new Product(1, "Asus Ultra Vip Pro", 30, 8888, "Apple M2", "Apple", "Apple", "Apple M2", "512GB SSD", "8GB", "China", "in stock", "demo", "demo", "demo", "demo", 1);


                    addNewPanelToCartContainer(createPanelForCart(filePaths, product1));
                    addNewPanelToCartContainer(createPanelForCart(filePaths, product1));
                    addNewPanelToCartContainer(createPanelForCart(filePaths, product1));
                    addNewPanelToCartContainer(createPanelForCart(filePaths, product1));
                    addNewPanelToCartContainer(createPanelForCart(filePaths, product1));
                    addNewPanelToCartContainer(createPanelForCart(filePaths, product1));


                    scrollPane = new JScrollPane(containerCart);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    setColorScrollPane(scrollPane, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE);
                    add(headerPanel, BorderLayout.NORTH);
                    add(scrollPane, BorderLayout.CENTER);

                }
            }

        }
    }

    class OrderHistoryPanel extends JPanel {
        private TablePanel tablePanel;
        private ToolPanel toolPanel;
        private String[] columnNames ={"Serial Number","Order ID","Order Date","Product Name","Product ID","Quantity","Total Price", "Status","Shipping Address","Delivery Date"};
        private JTable table;
        private DefaultTableModel model;
        private JTableHeader header;
        private JScrollPane scrollPane;
        private JTabbedPane tabbedPane;
        private CustomButton  detailBt, viewInvoiceBt, exportExcelBt;
        private JComboBox<String> sortComboBox;

        public OrderHistoryPanel() {
            setLayout(new BorderLayout());
            tablePanel = new TablePanel();
            toolPanel = new ToolPanel();
            add(toolPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);
        }
        class ToolPanel extends JPanel{
            ToolPanel(){
                setLayout(new FlowLayout());
                detailBt =createCustomButtonWithBorder("More Detail",Style.FONT_BOLD_13,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Color.white,Style.LIGHT_BlUE,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,1,5,new Dimension(120,80));
                detailBt.setHorizontalTextPosition(SwingConstants.CENTER);
                detailBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                viewInvoiceBt =createCustomButtonWithBorder("View Invoice",Style.FONT_BOLD_13,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Color.white,Style.LIGHT_BlUE,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,1,5,new Dimension(120,80));
                viewInvoiceBt.setHorizontalTextPosition(SwingConstants.CENTER);
                viewInvoiceBt.setVerticalTextPosition(SwingConstants.BOTTOM);


                exportExcelBt =createCustomButtonWithBorder("Export Excel",Style.FONT_BOLD_13,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Color.white,Style.LIGHT_BlUE,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,1,5,new Dimension(120,80));
                exportExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
                exportExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                add(detailBt);
                add(viewInvoiceBt);
                add(exportExcelBt);
            }

        }

        class TablePanel extends JPanel {

            TablePanel(){
                setLayout(new BorderLayout());
                model = new DefaultTableModel();
                header = new JTableHeader();
                table = createTable(model,header,columnNames);
                resizeColumnWidth(table,180);
                scrollPane = new JScrollPane(table);
                setColorScrollPane(scrollPane,Style.BACKGROUND_COLOR,Color.WHITE);
                tabbedPane = createTabbedPane(scrollPane, "Order History",Style.FONT_TITLE_PRODUCT_18);
                add(tabbedPane, BorderLayout.CENTER);
            }
        }



    }

    class NotificationPanel extends JPanel {
        private NotificationMainPanel notificationMainPanel;
        private JScrollPane scrollPane;
        private JTextField searchField;
        private JButton searchButton;

        public NotificationPanel() {
            setLayout(new BorderLayout());
            JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
            title.setBackground(Color.WHITE);
            JButton addBt = new JButton("Add");
            addBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Customer customer1 = new Customer("Nguyen Thi Ngoc Huyen", "23130075@st.hcmuaf.edu.vn", "tien giang chau thanh duong diem",
                            "$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO", "src/main/java/img/cus_huyen.jpg");


                    addCustomerNotification(customer1, "ok good good ehavd hvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadm liw ehavd hvoqjbkhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .");
                }
            });
            JButton Bt = new JButton("Add");
            Bt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Customer customer1 = new Customer("Nguyen Thi Ngoc Huyen", "23130075@st.hcmuaf.edu.vn", "tien giang chau thanh duong diem",
                            "$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO", "src/main/java/img/cus_huyen.jpg");


                    addCustomerNotification(customer1, "ok good good ehavd hvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadmhvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadmhvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadmhvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadmhvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadmhvokhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .ok good good khavsd ạ,bsdm h  íadm liw ehavd hvoqjbkhsad i khouqkbohf ubas duhqokhbcoq obcahwoi .");
                }
            });
            JButton Bt1 = new JButton("Add");
            Bt1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Customer customer1 = new Customer("Nguyen Thi Ngoc Huyen", "23130075@st.hcmuaf.edu.vn", "tien giang chau thanh duong diem",
                            "$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO", "src/main/java/img/cus_huyen.jpg");


                    addCustomerNotification(customer1,"Mình làm quen nha anh! e hâm mộ anh từ lâu rồi!");
                }
            });

            searchField = new JTextField("Search Notification");
            searchField.setPreferredSize(new Dimension(320, 40));
            searchField.setFont(Style.FONT_TEXT_CUSTOMER);
            searchField.setForeground(Color.GRAY);
            // Thêm FocusListener để kiểm soát khi người dùng nhấn và rời khỏi JTextField
            searchField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                    if (searchField.getText().equals("Search Notification")) {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                    if (searchField.getText().isEmpty()) {
                        searchField.setForeground(Color.GRAY);
                        searchField.setText("Search Notification");
                    }
                }
            });
            searchButton = new JButton();
            setStyleButton(searchButton, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(50, 40));
            setIconSmallButton("src/main/java/Icon/search_Icon.png", searchButton);


            title.add(addBt);
            title.add(Bt);
            title.add(Bt1);
            title.add(searchField);
            title.add(searchButton);

            notificationMainPanel = new NotificationMainPanel();
            add(title, BorderLayout.NORTH);
            add(notificationMainPanel, BorderLayout.CENTER);


        }

        class NotificationMainPanel extends JPanel {

            NotificationMainPanel() {
                setLayout(new BorderLayout());

                notificationContainer = new JPanel();
                notificationContainer.setLayout(new BoxLayout(notificationContainer, BoxLayout.Y_AXIS));

                scrollPane = new JScrollPane(notificationContainer);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                setColorScrollPane(scrollPane, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE);

                add(scrollPane, BorderLayout.CENTER);

            }
        }
    }

    class ChangeInfoPanel extends JPanel {
        ChangeAvatarPanel changeAvatarPanel;
        ChangeInfo changeInfo;
        CustomButton updateBt, cancelBt, changeAvaBt;

        public ChangeInfoPanel() {
            setLayout(new BorderLayout());

            JPanel ChangePn = new JPanel(new GridLayout(1, 2));
            changeAvatarPanel = new ChangeAvatarPanel();
            changeInfo = new ChangeInfo();
            ChangePn.add(changeAvatarPanel);
            ChangePn.add(changeInfo);
            add(ChangePn, BorderLayout.CENTER);

            cancelBt = new CustomButton("Cancel");
            cancelBt.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GRAY);
            cancelBt.setBackgroundColor(Style.LIGHT_BlUE);
            cancelBt.setBorderRadius(20);
            cancelBt.setPreferredSize(new Dimension(200, 45));
            cancelBt.setFont(Style.FONT_TITLE_PRODUCT);

            updateBt = new CustomButton("Update");
            updateBt.setGradientColors(new Color(58, 106, 227), Color.GREEN);
            updateBt.setBackgroundColor(Style.LIGHT_BlUE);
            updateBt.setBorderRadius(20);
            updateBt.setPreferredSize(new Dimension(200, 45));
            updateBt.setFont(Style.FONT_TITLE_PRODUCT);


            JPanel updatePn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            updatePn.add(cancelBt);
            updatePn.add(updateBt);
            add(updatePn, BorderLayout.SOUTH);
        }

        class ChangeAvatarPanel extends JPanel {

            ChangeAvatarPanel() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
                CircularImage avatar = new CircularImage("src/main/java/Icon/fit_nlu_logo.jpg", 300, 300, false);
                avatar.setAlignmentX(Component.CENTER_ALIGNMENT);


                changeAvaBt = new CustomButton("Upload new image from Computer");
                changeAvaBt.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN);
                changeAvaBt.setBackgroundColor(Style.LIGHT_BlUE);
                changeAvaBt.setBorderRadius(20);
                changeAvaBt.setPreferredSize(new Dimension(400, 40));
                changeAvaBt.setFont(Style.FONT_BUTTON_CUSTOMER);
                changeAvaBt.setHorizontalAlignment(SwingConstants.CENTER);
                changeAvaBt.setAlignmentX(Component.CENTER_ALIGNMENT);

                add(Box.createVerticalGlue());
                add(avatar);
                add(Box.createRigidArea(new Dimension(0, 50)));
                add(changeAvaBt);
                add(Box.createVerticalGlue());
            }

        }

        class ChangeInfo extends JPanel {

            JTextField emailField, fullNameField, addressField;
            JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
            JButton showOldPasswd, showNewPasswd, showRetypeNewPasswd;

            ChangeInfo() {
                setLayout(new GridBagLayout());

                // Tạo layout constraints
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel title = new JLabel("Customer Information", SwingConstants.CENTER);
                title.setFont(Style.FONT_TITLE_PRODUCT);
                title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                add(title, gbc);

                JLabel emailLabel = new JLabel("Email: ");
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                add(emailLabel, gbc);

                emailField = createStyledTextField();
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                add(emailField, gbc);

//                JButton button1 = createEditFieldButton(emailField);
//                gbc.gridx = 1;
//                gbc.gridy = 2;
//                add(button1, gbc);

                // Thêm Label 2, TextField 2, và Button 2
                JLabel nameLabel = new JLabel("Name: ");
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                add(nameLabel, gbc);

                fullNameField = createStyledTextField();
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 1;
                add(fullNameField, gbc);

                JButton button2 = createEditFieldButton(fullNameField);
                gbc.gridx = 1;
                gbc.gridy = 4;
                add(button2, gbc);

                // Thêm Label 3, TextField 3, và Button 3
                JLabel addressLabel = new JLabel("Address: ");
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                add(addressLabel, gbc);

                addressField = createStyledTextField();
                gbc.gridx = 0;
                gbc.gridy = 6;
                gbc.gridwidth = 1;
                add(addressField, gbc);

                JButton button3 = createEditFieldButton(addressField);
                gbc.gridx = 1;
                gbc.gridy = 6;
                add(button3, gbc);

                JLabel oldPasswdLabel = new JLabel("Old Password: ");
                gbc.gridx = 0;
                gbc.gridy = 7;
                gbc.gridwidth = 2;
                add(oldPasswdLabel, gbc);

                oldPasswordField = createStyledJPasswordField();
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.gridwidth = 1;
                add(oldPasswordField, gbc);

                showOldPasswd = createShowPasswdButton(oldPasswordField);
                gbc.gridx = 1;
                gbc.gridy = 8;
                gbc.gridwidth = 1;
                add(showOldPasswd, gbc);

                JLabel newPasswdLabel = new JLabel("New Password: ");
                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.gridwidth = 2;
                add(newPasswdLabel, gbc);

                newPasswordField = createStyledJPasswordField();
                gbc.gridx = 0;
                gbc.gridy = 10;
                gbc.gridwidth = 1;
                add(newPasswordField, gbc);

                showNewPasswd = createShowPasswdButton(newPasswordField);
                gbc.gridx = 1;
                gbc.gridy = 10;
                gbc.gridwidth = 1;
                add(showNewPasswd, gbc);

                JLabel confirmPasswdLabel = new JLabel("Confirm Password: ");
                gbc.gridx = 0;
                gbc.gridy = 11;
                gbc.gridwidth = 2;
                add(confirmPasswdLabel, gbc);

                confirmPasswordField = createStyledJPasswordField();
                gbc.gridx = 0;
                gbc.gridy = 12;
                gbc.gridwidth = 1;
                add(confirmPasswordField, gbc);

                showRetypeNewPasswd = createShowPasswdButton(confirmPasswordField);
                gbc.gridx = 1;
                gbc.gridy = 12;
                gbc.gridwidth = 1;
                add(showRetypeNewPasswd, gbc);


            }

            private JTextField createStyledTextField() {
                JTextField field = new JTextField();
                field.setFont(Style.FONT_TEXT_CUSTOMER);
                field.setPreferredSize(new Dimension(350, 40));
                field.setEditable(false);
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(MEDIUM_BLUE),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                field.setBackground(Color.WHITE);
                field.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3));
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                    }
                });
                return field;
            }

            private JPasswordField createStyledJPasswordField() {
                JPasswordField passwdField = new JPasswordField();
                passwdField.setEchoChar('*');
                passwdField.setFont(Style.FONT_BUTTON_PAY);
                passwdField.setPreferredSize(new Dimension(350, 40));
                passwdField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(MEDIUM_BLUE),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                passwdField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        passwdField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3));
                        ;
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        passwdField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                    }
                });
                return passwdField;
            }

            private JButton createEditFieldButton(JTextField textField) {
                JButton editBt = new JButton();
                editBt.setIcon(new ImageIcon("src/main/java/Icon/penIcon1.png"));
                editBt.setPreferredSize(new Dimension(45, 40));

                Color hoverBackground = new Color(130, 180, 230); // Màu sáng hơn khi hover

                editBt.setBackground(Style.LIGHT_BlUE);
                editBt.setFocusPainted(false);
                editBt.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
                editBt.setContentAreaFilled(true); // Đảm bảo nền được vẽ

                // Thêm hiệu ứng hover
                editBt.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        editBt.setBackground(hoverBackground);
                    }

                    public void mouseExited(MouseEvent evt) {
                        editBt.setBackground(Style.LIGHT_BlUE);
                    }
                });

                // Thêm hiệu ứng click để làm sáng lên
                editBt.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        editBt.setBackground(hoverBackground.darker()); // Tối hơn khi nhấn
                    }

                    public void mouseReleased(MouseEvent evt) {
                        editBt.setBackground(hoverBackground); // Quay lại màu hover sau khi thả chuột
                    }
                });

                editBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textField.setEditable(true);
                    }
                });

                return editBt;
            }

            // nút ẩn hiện cho mật khẩu
            private JButton createShowPasswdButton(JPasswordField passwordField) {
                JButton toggleButton = new JButton();
                toggleButton.setBackground(Style.LIGHT_BlUE);
                toggleButton.setFocusPainted(false);
                toggleButton.setFocusable(false);
                toggleButton.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
                ImageIcon showPasswd = new ImageIcon("src/main/java/Icon/showPasswd_Icon.png");
                ImageIcon hidePasswd = new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png");

                toggleButton.setIcon(showPasswd);
                toggleButton.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        toggleButton.setBackground(new Color(130, 180, 230));
                    }

                    public void mouseExited(MouseEvent evt) {
                        toggleButton.setBackground(Style.LIGHT_BlUE);
                    }
                });

                toggleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (passwordField.getEchoChar() != '\u0000') {
                            passwordField.setEchoChar('\u0000');
                            toggleButton.setIcon(hidePasswd);
                        } else {
                            // Ẩn mật khẩu
                            passwordField.setEchoChar('*');
                            toggleButton.setIcon(showPasswd);
                        }
                    }
                });
                return toggleButton;
            }

        }

    }

    private void setIconSmallButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    private static void setStyleButton(JButton that, Font font, Color textColor, Color backgroundColor, int textPosition, Dimension size) {
        that.setBackground(backgroundColor);
        that.setFont(font);
        that.setHorizontalAlignment(textPosition);
        that.setBorderPainted(false);
        that.setForeground(textColor);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }

    public JTextField createTextFieldWithPlaceholder(String placeholderText, Font font, Dimension size) {
        JTextField field = new JTextField(placeholderText);
        field.setFont(font);
        field.setForeground(Color.GRAY);
        field.setPreferredSize(size);
        field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholderText);
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
        return field;
    }

    private static CustomButton createCustomButton(String title, Font font, Color textColor, Color backgroundColor, Color hoverColor, int textPosition, Dimension size) {
        CustomButton button = new CustomButton(title);
        button.setFont(font);
        button.setTextColor(textColor);
        button.setBackgroundColor(backgroundColor);
        button.setHoverColor(hoverColor);
        button.setHorizontalAlignment(textPosition);
        button.setDrawBorder(false);
        button.setPreferredSize(size);
        return button;
    }

    private static CustomButton createCustomButtonWithBorder(String title, Font font, Color textColor, Color backgroundColor, Color hoverColor, Color borderColor, int thickness, int radius, Dimension size) {
        CustomButton bt = new CustomButton(title);
        bt.setFont(font);
        bt.setTextColor(textColor);
        bt.setBackgroundColor(backgroundColor);
        bt.setHoverColor(hoverColor);
        bt.setBorderColor(borderColor);
        bt.setBorderThickness(thickness);
        bt.setBorderRadius(radius);
        bt.setPreferredSize(size);
        return bt;
    }

    private static CustomButton createCustomButtonGradientBorder(String title, Font font, Color textColor, Color backgroundColor, Color startColor, Color endColor, int thickness, int radius, Dimension size) {
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

    // tạo ảnh cho sản phẩm
    private static ImageIcon createImageForProduct(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage(); // Lấy Image từ ImageIcon
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        return new ImageIcon(scaledImg);
    }
    // chỉnh màu cho scrollbar của combobox
    public static void setComboBoxScrollBarColor(JComboBox<?> comboBox, Color thumbColorInput, Color trackColorInput) {
        Object popup = comboBox.getUI().getAccessibleChild(comboBox, 0);
        if (popup instanceof JPopupMenu) {
            JPopupMenu popupMenu = (JPopupMenu) popup;
            JScrollPane scrollPane = (JScrollPane) popupMenu.getComponent(0);
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = thumbColorInput;
                    this.trackColor = trackColorInput;
                }
            });
        }
    }
    // chỉnh màu cho scrollbar
    private static void setColorScrollPane(JScrollPane scrollPane, Color thumbColor, Color trackColor) {
        setColorScrollBar(scrollPane.getVerticalScrollBar(), thumbColor, trackColor);
        setColorScrollBar(scrollPane.getHorizontalScrollBar(), thumbColor, trackColor);
    }
    private static void setColorScrollBar(JScrollBar scrollBar, Color scrollBarColor, Color trackBackGroundColor) {
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = scrollBarColor;
                this.trackColor = trackBackGroundColor;
            }
        });
    }

    // tạo bảng để hiển thị dữ liệu
    public JTable createTable(DefaultTableModel model, JTableHeader tableHeader, String[] columnNames) {
        // Thiết lập bảng
        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setResizingAllowed(true);
        table.setFont(Style.FONT_TEXT_TABLE);
        // Thiết lập model cho bảng
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        // Thiết lập table header
        tableHeader = table.getTableHeader();
        tableHeader.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setFont(Style.FONT_HEADER_ROW_TABLE);

        return table;
    }
    // thay đổi kích thước của cột trong bảng
    public void resizeColumnWidth(JTable table, int width) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(width);
            table.getColumnModel().getColumn(0).setPreferredWidth(120);
        }
    }
    public JTabbedPane createTabbedPane(JScrollPane scrollPane, String title, Font font) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        tabbedPane.addTab(title, scrollPane);
        tabbedPane.setFocusable(false);
        return tabbedPane;
    }

    public void addNewPanelToCatalogContainer(JPanel panel) {
        panel.setPreferredSize(new Dimension(320, 550));
        panel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = this.containerCatalog.getComponentCount() % 3;
        gbc.gridy = this.containerCatalog.getComponentCount() / 3;
        gbc.insets = new Insets(2, 2, 2, 2);

        this.containerCatalog.add(panel, gbc);
        this.containerCatalog.revalidate();
        this.containerCatalog.repaint();
    }

    // panel chứa thông tin từng sản phẩm
    public JPanel createPanelForProductInCatalog(String[] filePaths, Product product) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
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
            ToastNotification.showToast("Product added to Cart!", 3000, 350, 60);
            addNewPanelToCartContainer(createPanelForCart(filePaths, product));

        });
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(addToCartBt, gbc);

        return panel;
    }

    public void addNewPanelToCartContainer(JPanel panel) {
        panel.setPreferredSize(new Dimension(700, 220));
        panel.setMaximumSize(new Dimension(700, 220));
        this.containerCart.setLayout(new BoxLayout(this.containerCart, BoxLayout.Y_AXIS));
        this.containerCart.add(panel);

        this.containerCart.revalidate();
        this.containerCart.repaint();
    }

    public JPanel createPanelForCart(String[] filePaths, Product product) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));

        JLabel imageLabel = new JLabel(createImageForProduct(filePaths[0], 200, 200));
        panel.add(imageLabel, BorderLayout.WEST);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);
        JLabel productName = new JLabel("<html>" + product.getName() + "</html>", SwingConstants.LEFT);
        productName.setVerticalAlignment(SwingConstants.CENTER);
        productName.setFont(Style.FONT_TITLE_PRODUCT_30);
        top.add(productName);


        JPanel bot = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        bot.setBackground(Color.WHITE);
        JLabel price = new JLabel("$" + product.getPrice());
        price.setFont(Style.FONT_TITLE_PRODUCT_30);
        price.setForeground(Style.CONFIRM_BUTTON_COLOR_GREEN);
        bot.add(Box.createHorizontalStrut(10));
        bot.add(price);
        bot.add(Box.createHorizontalStrut(50));
        // Hiển thị số lượng
        JLabel quantity = new JLabel("Quantity: ");
        quantity.setFont(Style.FONT_TEXT_CUSTOMER);
        bot.add(quantity);

        // Thêm JComboBox cho số lượng
        JComboBox<Integer> quantityComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        quantityComboBox.setPreferredSize(new Dimension(60, 30));
        quantityComboBox.setFont(Style.FONT_TITLE_PRODUCT_18);
        setComboBoxScrollBarColor(quantityComboBox,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Style.LIGHT_BlUE);
        bot.add(quantityComboBox);
        bot.add(Box.createHorizontalStrut(50));

        CustomButton remove = createCustomButton("Remove", Style.FONT_SIZE_MENU_BUTTON, Color.red, Color.white, Color.white, SwingConstants.CENTER, new Dimension(100, 40));
        remove.addActionListener(e -> {
            Container parentContainer = panel.getParent();
            if (parentContainer != null) {
                parentContainer.remove(panel);
                parentContainer.revalidate();
                parentContainer.repaint();
            }
            if (containerCart.getComponentCount() == 0) {
                JPanel empty = new JPanel();
                empty.setBackground(Color.BLUE);
                containerCart.add(empty);
                containerCart.revalidate();
                containerCart.repaint();
            } else {
                containerCart.setBackground(Color.WHITE);
                containerCart.revalidate();
                containerCart.repaint();
            }

        });
        bot.add(remove);

        JPanel Main = new JPanel(new GridLayout(2, 1));
        Main.add(top);
        Main.add(bot);
        panel.add(Main, BorderLayout.CENTER);


        return panel;
    }

    public void addCustomerNotification(Customer customer, String text) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel timeLabel = new JLabel("<html>" + now.format(timeFormatter) + "<br>" + now.format(dateFormatter) + "</html>");

        CircularImage avatar = new CircularImage(customer.getAvataImg(), 80, 80, true);

        JLabel customerName = new JLabel(customer.getFullName());
        customerName.setFont(Style.FONT_PLAIN_25);

        JTextArea message = new JTextArea(text);
        message.setBackground(Color.WHITE);
        message.setForeground(Color.BLACK);
        message.setFont(new Font("Arial", Font.PLAIN, 16));
        message.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, 2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE),
                BorderFactory.createEmptyBorder(3, 3, 3, 8)
        ));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        message.setOpaque(true);

        // Cố định chiều rộng và tính toán chiều cao phù hợp
        int width = 600;
        message.setSize(new Dimension(width, Short.MAX_VALUE));
        int preferredHeight = message.getPreferredSize().height;
        message.setPreferredSize(new Dimension(width, preferredHeight));

        // Bọc JTextArea trong JScrollPane nhưng tắt thanh cuộn ngang
        JScrollPane scrollPane = new JScrollPane(message);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Tắt border mặc định của JScrollPane

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
        textAreaPanel.add(scrollPane);
        textAreaPanel.setBackground(Color.WHITE);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        main.add(avatar, gbc);

        gbc.gridx = 1;
        main.add(customerName, gbc);

        gbc.gridy = 1;
        main.add(textAreaPanel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        main.add(timeLabel, gbc);

        notificationContainer.add(main);
        notificationContainer.revalidate();
        notificationContainer.repaint();
    }


}
