package view;

import Model.Product;
import controller.ProductController;
import view.OtherComponent.CircularImage;
import view.OtherComponent.RoundedButton;
import view.OtherComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


import static org.jfree.chart.ChartColor.LIGHT_BLUE;

public class CustomerMainPanel extends JPanel {
    JPanel containerCatalog;
    JPanel containerCart;
    OldLoginFrame loginFrame;
    CardLayout cardLayout;
    WelcomePanel welcomePanel;
    ProductCatalogMainPanel productCatalogPanel;
    NotificationPanel notificationPanel;
    PurchasedPanel purchasedPanel;
    ChangeInfoPanel changeInfoPanel;

    private static final Color MEDIUM_BLUE = new Color(51, 153, 255);
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
        productCatalogPanel = new ProductCatalogMainPanel();
        purchasedPanel = new PurchasedPanel();
        notificationPanel = new NotificationPanel();
        changeInfoPanel = new ChangeInfoPanel();

        setLayout(cardLayout);
        add(welcomePanel, WELCOME_CONSTRAINT);
        add(productCatalogPanel, PRODUCT_CATALOG_CONSTRAINT);
        add(purchasedPanel, PURCHASED_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);
        add(changeInfoPanel, CHANGE_INFORMATION_CONSTRAINT);

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
        JButton cartBt, searchBt;
        RoundedButton allBt, gaming, office, pcCase, cheapest, luxury;
        private RoundedButton selectedButton;
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
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 0;

                shopName = new JLabel("Computer Shop");
                shopName.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                shopName.setFont(new Font("Arial", Font.BOLD, 55));
                gbc.gridx = 0;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 15, 0, 20);
                gbc.anchor = GridBagConstraints.LINE_START;
                searchBar.add(shopName, gbc);

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
                searchBar.add(searchTextField, gbc);

                searchBt = new JButton();
                setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(68, 40));
                setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                gbc.gridx = 2;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.insets = new Insets(10, 0, 0, 0);
                searchBar.add(searchBt, gbc);

                cartBt = new JButton("Cart");
                setStyleButton(cartBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(80, 80));
                setIconBigButton("src/main/java/Icon/cartIcon.png", cartBt);
                cartBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                cartBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                cartBt.addActionListener(e -> {
                    ProductCatalogMainPanel.this.show("cart");
                });
                gbc.gridx = 3;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.LINE_END;
                gbc.insets = new Insets(5, 15, 0, 10);
                searchBar.add(cartBt, gbc);

                //tab bar to display products by order
                JPanel TabBar = new JPanel();
                TabBar.setLayout(new FlowLayout(FlowLayout.LEFT));


                allBt = createRoundedButtonWithBorder("All", Color.BLACK, Color.white, Style.BACKGROUND_COLOR, 3, 30, new Dimension(120, 25));
                allBt.addActionListener(e -> updateSelectedButton(allBt));

                gaming = createRoundedButtonWithBorder("Laptop Gaming", Color.BLACK, Color.white, Style.BACKGROUND_COLOR, 3, 30, new Dimension(150, 25));
                gaming.addActionListener(e -> updateSelectedButton(gaming));

                office = createRoundedButtonWithBorder("Laptop Office", Color.BLACK, Color.white, Style.BACKGROUND_COLOR, 3, 30, new Dimension(150, 25));
                office.addActionListener(e -> updateSelectedButton(office));

                pcCase = createRoundedButtonWithBorder("PC Case", Color.BLACK, Color.white, Style.BACKGROUND_COLOR, 3, 30, new Dimension(120, 25));
                pcCase.addActionListener(e -> updateSelectedButton(pcCase));

                cheapest = createRoundedButtonWithBorder("Cheapest", Color.BLACK, Color.white, Style.BACKGROUND_COLOR, 3, 30, new Dimension(120, 25));
                cheapest.addActionListener(e -> updateSelectedButton(cheapest));

                luxury = createRoundedButtonWithBorder("Luxury", Color.BLACK, Color.white, Style.BACKGROUND_COLOR, 3, 30, new Dimension(120, 25));
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

            private void updateSelectedButton(RoundedButton button) {
                Color defaultColor = Color.WHITE;
                Color selectedColor = Style.BACKGROUND_COLOR;

                // Đặt lại màu cho button trước đó nếu có
                if (selectedButton != null) {
                    selectedButton.setBackgroundColor(defaultColor);
                }

                // Đặt màu cho button mới được chọn và cập nhật selectedButton
                button.setBackgroundColor(selectedColor);
                selectedButton = button;
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
                    containerCatalog = new JPanel(new GridBagLayout());

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
                    add(scrollPane, BorderLayout.CENTER);

                }
            }

            class DisplaySingleProductPn extends JPanel {
                JLabel image, productName, price, status;
                JButton backBt, previousBt, nextBt;
                RoundedButton addToCart, buyNowBt;
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
                    addToCart = new RoundedButton("Add To Cart      ");
                    addToCart.setBackgroundColor(Style.CONFIRM_BUTTON_COLOR_GREEN);
                    addToCart.setTextColor(Color.WHITE);
                    addToCart.setHoverColor(new Color(162, 236, 132));

                    addToCart.addActionListener(e -> ToastNotification.showToast("Product added to Cart!", 3000,350,60));
                    gbc2.gridy = 3;
                    paymentPn.add(addToCart, gbc2);

                    buyNowBt = new RoundedButton("Buy Now       ");
                    buyNowBt.setBackgroundColor(new Color(211, 127, 87));
                    buyNowBt.setTextColor(Color.BLACK);
                    buyNowBt.setHoverColor(new Color(227, 178, 148));
                    gbc2.gridy = 4;
                    paymentPn.add(buyNowBt, gbc2);

                    add(paymentPn);


                    add(paymentPn);
                }
            }

        }

        class CartPanel extends JPanel {
            JButton backBt;
            MainPaymentPanel mainPaymentPanel = new MainPaymentPanel();
            JScrollPane scrollPane;

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
                title.setFont(Style.FONT_TITLE_PRODUCT_30);
                gbc.gridx = 1;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.CENTER;
                titlePanel.add(title, gbc);

                add(titlePanel, BorderLayout.NORTH);
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
                private JTextField emailField, nameField, phoneNumberField, addressField;
                private JLabel totalItem, totalPrice;
                private RoundedButton pay;

                PaymentPanel() {
                    setLayout(new GridBagLayout());
                    setBackground(Color.WHITE);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.weightx = 1.0;

                    JLabel title = new JLabel("Delivery information");
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    add(title, gbc);

                    emailField = createTextFieldWithPlaceholder("Email", Style.FONT_TEXT_CUSTOMER, new Dimension(250, 40));
                    gbc.gridy = 1;
                    add(emailField, gbc);

                    nameField = createTextFieldWithPlaceholder("Your Name", Style.FONT_TEXT_CUSTOMER, new Dimension(250, 40));
                    gbc.gridy = 2;
                    add(nameField, gbc);

                    phoneNumberField = createTextFieldWithPlaceholder("Phone Number", Style.FONT_TEXT_CUSTOMER, new Dimension(250, 40));
                    gbc.gridy = 3;
                    add(phoneNumberField, gbc);

                    addressField = createTextFieldWithPlaceholder("Address", Style.FONT_TEXT_CUSTOMER, new Dimension(250, 40));
                    gbc.gridy = 4;
                    add(addressField, gbc);

                    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                    separator.setOrientation(SwingConstants.HORIZONTAL);

                    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
                    gbc.gridy = 5;
                    add(separator, gbc);

                    JLabel cartTotal = new JLabel("Cart Total");
                    gbc.gridy = 6;
                    add(cartTotal, gbc);

                    totalItem = new JLabel(36 + " Items in your Cart");
                    gbc.gridy = 7;
                    add(totalItem, gbc);

                    totalPrice = new JLabel("<html> <font color='black'>Total Price:  </font> <font color='red'>$" + 9999 + "</font></html>");

                    gbc.gridy = 8;
                    add(totalPrice, gbc);

                    pay = createRoundedButton("PAY", Style.FONT_BUTTON_PAY, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(100, 215, 196), SwingConstants.CENTER, new Dimension(250, 40));

                    gbc.gridy = 9;
                    add(pay, gbc);

                }

            }

            class ProductsInCartPanel extends JPanel {

                ProductsInCartPanel() {
                    setLayout(new BorderLayout());

                    JPanel headerPanel = new JPanel(new BorderLayout());
                    headerPanel.setBackground(Color.WHITE);
                    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    leftPanel.setBackground(Color.WHITE);
                    leftPanel.add(new JLabel("     Product", JLabel.CENTER));
                    headerPanel.add(leftPanel, BorderLayout.WEST);

                    JPanel rightPanel = new JPanel(new FlowLayout());
                    rightPanel.setBackground(Color.WHITE);

                    JLabel price = new JLabel("Price", JLabel.CENTER);
                    price.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 190));

                    rightPanel.add(price);
                    rightPanel.add(price);
                    headerPanel.add(rightPanel, BorderLayout.EAST);

                    containerCart = new JPanel(new GridBagLayout());

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
                    add(headerPanel, BorderLayout.NORTH);
                    add(scrollPane, BorderLayout.CENTER);

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

    class ChangeInfoPanel extends JPanel {
        ChangeAvatarPanel changeAvatarPanel = new ChangeAvatarPanel();
        ChangeInfo changeInfo = new ChangeInfo();
        JButton update;

        public ChangeInfoPanel() {
            setLayout(new BorderLayout());

            JPanel ChangePn = new JPanel(new GridLayout(1, 2));
            ChangePn.add(changeAvatarPanel);
            ChangePn.add(changeInfo);
            add(ChangePn, BorderLayout.CENTER);

            update = new JButton("Update");
            setStyleButton(update,Style.FONT_BUTTON_CUSTOMER, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(200, 40));
            JPanel updatePn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            updatePn.add(update);
            add(updatePn, BorderLayout.SOUTH);


        }

        class ChangeAvatarPanel extends JPanel {
            JButton changeAvaBt;

            ChangeAvatarPanel() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
                CircularImage avatar = new CircularImage("src/main/java/Icon/fit_nlu_logo.jpg", 300, 300, false);
                avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
                changeAvaBt = new JButton("Upload new image from Computer");
                setStyleButton(changeAvaBt, Style.FONT_BUTTON_CUSTOMER, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(300, 50));
                changeAvaBt.setAlignmentX(Component.CENTER_ALIGNMENT);


                add(Box.createVerticalGlue());
                add(avatar);
                add(Box.createRigidArea(new Dimension(0, 50)));
                add(changeAvaBt);
                add(Box.createVerticalGlue());

            }

        }

        class ChangeInfo extends JPanel {
            JTextField emailField, fullNameField, addressField, oldPasswd, newPasswd, retypeNewPasswd;

            ChangeInfo() {
                setLayout(new GridBagLayout());

                // Tạo layout constraints
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Thêm Label 1, TextField 1, và Button 1
                JLabel emailLabel = new JLabel("Email: ");
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                add(emailLabel, gbc);

                emailField = createStyledTextField();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                add(emailField, gbc);

                JButton button1 = new JButton("Edit");
                gbc.gridx = 1;
                gbc.gridy = 1;
                add(button1, gbc);

                // Thêm Label 2, TextField 2, và Button 2
                JLabel nameLabel = new JLabel("Name: ");
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                add(nameLabel, gbc);

                fullNameField = createStyledTextField();
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 1;
                add(fullNameField, gbc);

                JButton button2 = new JButton("Edit");
                gbc.gridx = 1;
                gbc.gridy = 3;
                add(button2, gbc);

                // Thêm Label 3, TextField 3, và Button 3
                JLabel addressLabel = new JLabel("Address");
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 2;
                add(addressLabel, gbc);

                JTextField addressField = createStyledTextField();
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 1;
                add(addressField, gbc);

                JButton button3 = new JButton("Edit");
                gbc.gridx = 1;
                gbc.gridy = 5;
                add(button3, gbc);


            }
            private JTextField createStyledTextField() {
                JTextField field = new JTextField();
                field.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                field.setPreferredSize(new Dimension(300, 40));
                field.setEditable(false);
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(MEDIUM_BLUE),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                field.setBackground(Color.WHITE);
                return field;
            }

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
        Image resizedImage = image.getScaledInstance(buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
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

    public JTextField createTextFieldWithPlaceholder(String placeholderText, Font font, Dimension size) {
        JTextField field = new JTextField(placeholderText);
        field.setFont(font);
        field.setForeground(Color.GRAY);
        field.setPreferredSize(size);
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholderText);
                }
            }
        });
        return field;
    }

    private static RoundedButton createRoundedButton(String title, Font font, Color textColor, Color backgroundColor, Color hoverColor, int textPosition, Dimension size) {
        RoundedButton button = new RoundedButton(title);
        button.setCustomFont(font);
        button.setTextColor(textColor);
        button.setBackgroundColor(backgroundColor);
        button.setHoverColor(hoverColor);
        button.setHorizontalAlignment(textPosition);
        button.setBorderPainted(false);
        button.setButtonSize(size);
        return button;
    }

    private static RoundedButton createRoundedButtonWithBorder(String title, Color textColor, Color backgroundColor, Color borderColor, int thickness, int radius, Dimension size) {
        RoundedButton bt = new RoundedButton(title);
        bt.setTextColor(textColor);
        bt.setBackgroundColor(backgroundColor);
        bt.createLineBorder(borderColor, thickness);
        bt.setBorderRadius(radius);
        bt.setButtonSize(size);
        return bt;
    }

    // tạo ảnh cho sản phẩm
    private static ImageIcon createImageForProduct(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage(); // Lấy Image từ ImageIcon
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        return new ImageIcon(scaledImg);
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
            ToastNotification.showToast("Product added to Cart!", 3000,350,60);
            addNewPanelToCartContainer(createPanelForCart(filePaths, product));

        });
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(addToCartBt, gbc);

        return panel;
    }

    public void addNewPanelToCartContainer(JPanel panel) {
        panel.setPreferredSize(new Dimension(750, 220));
        this.containerCart.setLayout(new BoxLayout(this.containerCart, BoxLayout.Y_AXIS));

        this.containerCart.add(panel);

        // Cập nhật giao diện
        this.containerCart.revalidate();
        this.containerCart.repaint();
    }

    public JPanel createPanelForCart(String[] filePaths, Product product) {
        // Sử dụng số lượng từ đối tượng product để khởi tạo số lượng trong panel

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Hiển thị hình ảnh sản phẩm
        JLabel imageLabel = new JLabel(createImageForProduct(filePaths[0], 200, 200));
        panel.add(imageLabel, gbc);

        // Hiển thị tên sản phẩm
        JLabel productName = new JLabel("<html>" + product.getName() + "</html>");
        productName.setFont(Style.FONT_TEXT_CUSTOMER);
        productName.setPreferredSize(new Dimension(170, 100));
        gbc.gridx = 1;
        panel.add(productName, gbc);


        JLabel price = new JLabel("$" + product.getPrice());
        price.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 3;
        panel.add(price, gbc);

        RoundedButton remove = createRoundedButton("Remove", Style.FONT_SIZE_MENU_BUTTON, Color.red, Color.white, Color.white, SwingConstants.CENTER, new Dimension(100, 40));
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parentContainer = panel.getParent();
                if (parentContainer != null) {
                    parentContainer.remove(panel);
                    parentContainer.revalidate();
                    parentContainer.repaint();
                }
            }
        });

        gbc.gridx = 4;
        panel.add(remove, gbc);

        return panel;
    }


}
