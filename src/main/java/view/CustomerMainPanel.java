package view;

import Config.*;
import Model.Customer;
import Model.Product;
import Verifier.EmailVerifier;
import Verifier.NotNullVerifier;
import Verifier.UserNameAccountVerifier;
import com.toedter.calendar.JCalendar;
import controller.CustomerController;
import controller.OrderController;
import controller.OrderDetailController;
import controller.ProductController;
import dto.CustomerOrderDTO;
import dto.CustomerOrderDetailDTO;
import dto.KeyOrderDTO;
import view.OtherComponent.ChangePasswordFrame;
import view.OtherComponent.NotFoundItemPanel;
import view.OverrideComponent.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import Enum.*;
import java.util.stream.Collectors;


public class CustomerMainPanel extends JPanel {
    JPanel notificationContainer = new JPanel();
    JPanel catalogContainer = new JPanel();
    JPanel cartContainer = new JPanel(new GridLayout(0, 1));
    JPanel productDetailContainer = new JPanel(new BorderLayout());
    JPanel ordersContainer = new JPanel();

    JPanel emptyCartPn = createEmptyPanel();
    CardLayout cardLayout;
    ProductCatalogMainPanel productCatalogPanel;
    NotificationPanel notificationPanel;
    OrderHistoryPanel purchasedPanel;
    ChangeInformationPanel changeInfoPanel;

    static final String PRODUCT_CATALOG_CONSTRAINT = "catalog";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String PURCHASED_CONSTRAINT = "purchased";
    static final String CHANGE_INFORMATION_CONSTRAINT = "changeInformation";
    static final String STATUS_ORDER = "Received the application";

    private static Set<ProductOrderConfig> productOrders = new LinkedHashSet<>();
    private static ArrayList<CustomerOrderDTO> bills = new ArrayList<>();
    private static OrderController orderController = new OrderController();
    private static OrderDetailController orderDetailController = new OrderDetailController();
    private static CustomerController customerController = new CustomerController();

    private JTextField emailField, nameField, addressField;
    private static int totalItems = 0, totalPrice = 0;
    private static int MANAGER_ID=0;
    private static int CUSTOMER_ID=0;
    private static String ADDRESS="";

    public static DecimalFormat formatCurrency = new DecimalFormat("#,###");


    //constructor
    public CustomerMainPanel() throws SQLException {
        cardLayout = new CardLayout();
        productCatalogPanel = new ProductCatalogMainPanel();
        purchasedPanel = new OrderHistoryPanel();
        notificationPanel = new NotificationPanel();
        changeInfoPanel = new ChangeInformationPanel();

        setLayout(cardLayout);
        add(productCatalogPanel, PRODUCT_CATALOG_CONSTRAINT);
        add(purchasedPanel, PURCHASED_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);
        add(changeInfoPanel, CHANGE_INFORMATION_CONSTRAINT);

        cardLayout.show(this, PRODUCT_CATALOG_CONSTRAINT);
    }

    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
    }

    class ProductCatalogMainPanel extends JPanel {
        ProductCatalogInnerPn productCatalogInnerPn;
        CartPanel cartPanel;
        ToolPanel toolPanel;
        DisplayProductPanel displayProductPanel;
        JButton searchBt;
        private CustomButton cartButton, allBt, gaming, office, pcCase, cheapest, luxury, selectedButton;
        JLabel shopName;
        JTextField searchTextField;
        private static ProductController productController = new ProductController();
        private static ArrayList<Product> productsAll = productController.getAll();
        CardLayout cardLayoutMain = new CardLayout();

        public ProductCatalogMainPanel() {
            setLayout(cardLayoutMain);
            productCatalogInnerPn = new ProductCatalogInnerPn();
            add(productCatalogInnerPn, "catalog");

            cartPanel = new CartPanel();
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
                shopName.setFont(Style.FONT_BOLD_55);
                gbc.gridx = 0;
                gbc.weightx = 1.0;
                gbc.insets = new Insets(5, 15, 0, 20);
                gbc.anchor = GridBagConstraints.LINE_START;
                searchBar.add(shopName, gbc);

                searchTextField = TextFieldConfig.createTextField("Search", Style.FONT_PLAIN_18, Color.GRAY, new Dimension(320, 40));
                searchTextField.addActionListener(e -> searchBt.doClick());
                gbc.gridx = 1;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(10, 0, 0, 0);
                searchBar.add(searchTextField, gbc);

                searchBt = ButtonConfig.createCustomButton("", Style.FONT_PLAIN_20, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 0, SwingConstants.CENTER, new Dimension(50, 40));
                ButtonConfig.setButtonIcon("src/main/java/Icon/search_Icon.png", searchBt,5);
                searchBt.addActionListener(e -> {
                    catalogContainer.removeAll();
                    ProductController productController = new ProductController();
                    ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts()
                            .stream()
                            .filter(p -> p.getName().toLowerCase().contains(searchTextField.getText().toLowerCase()))
                            .collect(Collectors.toList());
                    for (int i = 0; i < products.size(); i++) {
                        JPanel p1 = createPanelForProductInCatalog(products.get(i));
                        addNewPanelToCatalogContainer(p1);
                    }

                });


                gbc.gridx = 2;
                gbc.anchor = GridBagConstraints.EAST;
                searchBar.add(searchBt, gbc);

                cartButton = ButtonConfig.createCustomButtonGradientBorder("Cart", Style.FONT_BOLD_16, Color.WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.MENU_BUTTON_COLOR_GREEN, Style.BACKGROUND_COLOR, 4, 20, new Dimension(80, 80));
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

                allBt = ButtonConfig.createCustomButton("All", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, SwingConstants.CENTER, new Dimension(120, 25));
                allBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(allBt);
                        catalogContainer.removeAll();
                        ProductController productController = new ProductController();
                        ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts();
                        for (int i = 0; i < products.size(); i++) {
                            JPanel p1 = createPanelForProductInCatalog(products.get(i));
                            addNewPanelToCatalogContainer(p1);
                        }

                    }
                });
                gaming = ButtonConfig.createCustomButton("Laptop Gaming", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, SwingConstants.CENTER, new Dimension(150, 25));
                gaming.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(gaming);
                        catalogContainer.removeAll();
                        ProductController productController = new ProductController();
                        ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts()
                                .stream()
                                .filter(p -> p.getGenre().toLowerCase().contains("gaming"))
                                .collect(Collectors.toList());
                        for (int i = 0; i < products.size(); i++) {
                            JPanel p1 = createPanelForProductInCatalog(products.get(i));
                            addNewPanelToCatalogContainer(p1);
                        }

                    }
                });
                office = ButtonConfig.createCustomButton("Laptop Office", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, SwingConstants.CENTER, new Dimension(150, 25));
                office.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(office);
                        catalogContainer.removeAll();
                        ProductController productController = new ProductController();
                        ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts()
                                .stream()
                                .filter(p -> p.getGenre().toLowerCase().contains("Ultrabook"))
                                .collect(Collectors.toList());
                        for (int i = 0; i < products.size(); i++) {
                            JPanel p1 = createPanelForProductInCatalog(products.get(i));
                            addNewPanelToCatalogContainer(p1);
                        }

                    }
                });
                pcCase = ButtonConfig.createCustomButton("PC Case", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, SwingConstants.CENTER, new Dimension(120, 25));
                pcCase.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(pcCase);
                        catalogContainer.removeAll();
                        ProductController productController = new ProductController();
                        ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts()
                                .stream()
                                .filter(p -> p.getGenre().toLowerCase().contains("pc"))
                                .collect(Collectors.toList());
                        if ( products.isEmpty()) {
                            catalogContainer.add(new NotFoundItemPanel(" not found !!! "));
                            return;
                        }
                        for (int i = 0; i < products.size(); i++) {
                            JPanel p1 = createPanelForProductInCatalog(products.get(i));
                            addNewPanelToCatalogContainer(p1);
                        }
                    }
                });
                cheapest = ButtonConfig.createCustomButton("Cheapest", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, SwingConstants.CENTER, new Dimension(120, 25));
                cheapest.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(cheapest);
                        catalogContainer.removeAll();
                        ProductController productController = new ProductController();
                        ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts()
                                .stream()
                                .sorted((p1,p2) -> p1.getPrice() - p2.getPrice())
                              .collect(Collectors.toList());
                        if ( products.isEmpty()) {
                            catalogContainer.add(new NotFoundItemPanel(" not found !!! "));
                            return;
                        }
                        for (int i = 0; i < products.size(); i++) {
                            JPanel p1 = createPanelForProductInCatalog(products.get(i));
                            addNewPanelToCatalogContainer(p1);
                        }

                    }
                });

                luxury = ButtonConfig.createCustomButton("Luxury", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, SwingConstants.CENTER, new Dimension(120, 25));
                luxury.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(luxury);
                        catalogContainer.removeAll();
                        ProductController productController = new ProductController();
                        ArrayList<Product> products = (ArrayList<Product>) productController.getEagerProducts()
                                .stream()
                                .sorted((p1,p2) -> p2.getPrice() - p1.getPrice())
                                .collect(Collectors.toList());
                        if ( products.isEmpty()) {
                            catalogContainer.add(new NotFoundItemPanel(" not found !!! "));
                            return;
                        }
                        for (int i = 0; i < products.size(); i++) {
                            JPanel p1 = createPanelForProductInCatalog(products.get(i));
                            addNewPanelToCatalogContainer(p1);
                        }

                    }
                });

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
                    catalogContainer.setLayout(new GridBagLayout());
                    catalogContainer.setBackground(Color.WHITE);
                    ProductController productController = new ProductController();
                    ArrayList<Product> products = productController.getEagerProducts();
                    for (int i = 0; i < products.size(); i++) {
                        JPanel p1 = createPanelForProductInCatalog(products.get(i));
                        addNewPanelToCatalogContainer(p1);
                    }

                    scrollPane = new JScrollPane(catalogContainer);
                    setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Color.WHITE);
                    add(scrollPane, BorderLayout.CENTER);
                }
            }

            class DisplaySingleProductPn extends JPanel {

                DisplaySingleProductPn() {
                    setLayout(new BorderLayout());
                    add(productDetailContainer, BorderLayout.CENTER);
                }
            }

        }

        class CartPanel extends JPanel {
            JLabel totalItemsLabel, totalItemsTitle, totalPriceLabel;

            CustomButton backBt, orderBt;
            ProductsInCartPanel productsInCartPanel;
            SummaryPanel summaryPanel;
            JScrollPane scrollPane;

            CartPanel() {
                setLayout(new BorderLayout());
                productsInCartPanel = new ProductsInCartPanel();
                summaryPanel = new SummaryPanel();

                add(productsInCartPanel, BorderLayout.CENTER);
                add(summaryPanel, BorderLayout.EAST);
            }

            class ProductsInCartPanel extends JPanel {
                ProductsInCartPanel() {

                    setLayout(new BorderLayout());

                    JPanel titlePnTop = new JPanel(new BorderLayout());
                    titlePnTop.setBackground(Color.WHITE);
                    titlePnTop.setPreferredSize(new Dimension(800, 100));
                    JLabel title = LabelConfig.createLabel("    Shopping Cart", Style.FONT_TITLE_BOLD_40,
                            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, SwingConstants.CENTER);
                    titlePnTop.add(title, BorderLayout.WEST);

                    totalItemsTitle = LabelConfig.createLabel(totalItems + " items     ", Style.FONT_BOLD_20, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.RIGHT, SwingConstants.CENTER);

                    titlePnTop.add(totalItemsTitle, BorderLayout.EAST);

                    add(titlePnTop, BorderLayout.NORTH);

                    // container contain all the
                    cartContainer.setLayout(new GridLayout(0, 1));
                    cartContainer.add(emptyCartPn);
                    scrollPane = new JScrollPane(cartContainer);
                    scrollPane.setBackground(Color.WHITE);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setBorder(new MatteBorder(1, 0, 1, 0, Color.GRAY));
                    setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Style.LIGHT_BlUE);
                    add(scrollPane, BorderLayout.CENTER);

                    // panel contain back button
                    JPanel BottomPn = new JPanel(new BorderLayout());
                    BottomPn.setBackground(Color.WHITE);
                    BottomPn.setPreferredSize(new Dimension(800, 80));
                    JPanel backBtPn = new JPanel(new GridBagLayout());
                    backBtPn.setBackground(Color.WHITE);
                    backBt = ButtonConfig.createCustomButton("Back to shop", Style.FONT_BOLD_16, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                            Style.MEDIUM_BLUE, 8, SwingConstants.CENTER, new Dimension(180, 35));
                    ButtonConfig.setButtonIcon("src/main/java/Icon/back_Icon1.png", backBt, 5);
                    backBt.addActionListener(e -> {
                        ProductCatalogMainPanel.this.show("catalog");
                    });
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 30, 5, 5);
                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    backBtPn.add(backBt, gbc);

                    BottomPn.add(backBtPn, BorderLayout.WEST);
                    add(BottomPn, BorderLayout.SOUTH);
                }
            }

            // thông tin người đặt mua và tính tiền
            class SummaryPanel extends JPanel {

                SummaryPanel() {
                    setLayout(new BorderLayout());
                    setPreferredSize(new Dimension(320, 100));
                    setBackground(Color.WHITE);

                    JPanel titlePn = new JPanel(new BorderLayout());
                    titlePn.setPreferredSize(new Dimension(320, 100));
                    titlePn.setBackground(Style.WHITE_BLUE_COLOR);
                    JLabel title = LabelConfig.createLabel("     Summary", Style.FONT_BOLD_30, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT);
                    titlePn.add(title, BorderLayout.CENTER);
                    add(titlePn, BorderLayout.NORTH);

                    JPanel summaryDetailsPn = new JPanel(new GridBagLayout());

                    summaryDetailsPn.setBackground(Style.WHITE_BLUE_COLOR);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(0, 10, 10, 10);
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;

                    JSeparator separatorTop = new JSeparator(SwingConstants.HORIZONTAL);
                    separatorTop.setPreferredSize(new Dimension(270, 5));
                    summaryDetailsPn.add(separatorTop, gbc);
                    JLabel emailLabel = LabelConfig.createLabel("EMAIL", Style.FONT_BOLD_20, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, SwingConstants.CENTER);
                    gbc.insets = new Insets(10, 10, 10, 10);
                    gbc.gridy++;
                    summaryDetailsPn.add(emailLabel, gbc);
                    emailField = TextFieldConfig.createTextField(CurrentUser.CURRENT_CUSTOMER.getEmail(), Style.FONT_PLAIN_18,
                            Color.BLACK, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(270, 40), true);

                    gbc.gridy++;
                    summaryDetailsPn.add(emailField, gbc);
                    JLabel nameLabel = LabelConfig.createLabel("NAME", Style.FONT_BOLD_20, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, SwingConstants.CENTER);
                    nameField = TextFieldConfig.createTextField(CurrentUser.CURRENT_CUSTOMER.getFullName(), Style.FONT_PLAIN_18,
                            Color.BLACK, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(270, 40), true);


                    gbc.gridy++;
                    summaryDetailsPn.add(nameLabel, gbc);
                    gbc.gridy++;
                    summaryDetailsPn.add(nameField, gbc);

                    JLabel addressLabel = LabelConfig.createLabel("SHIPPING ADDRESS", Style.FONT_BOLD_20,
                            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, SwingConstants.CENTER);

                    addressField = TextFieldConfig.createTextField(CurrentUser.CURRENT_CUSTOMER.getAddress(),
                            Style.FONT_PLAIN_18, Color.BLACK, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                            new Dimension(270, 40), true);

                    gbc.gridy++;
                    summaryDetailsPn.add(addressLabel, gbc);
                    gbc.gridy++;
                    summaryDetailsPn.add(addressField, gbc);

                    JSeparator separatorMid = new JSeparator(SwingConstants.HORIZONTAL);
                    separatorMid.setPreferredSize(new Dimension(270, 5));
                    gbc.gridy++;
                    summaryDetailsPn.add(separatorMid, gbc);

                    totalItemsLabel = new JLabel("<html>ITEMS <font color='green'>" + totalItems + "</font></html>");
                    totalItemsLabel.setFont(Style.FONT_BOLD_20);
                    gbc.gridy++;
                    summaryDetailsPn.add(totalItemsLabel, gbc);

                    totalPriceLabel = new JLabel("<html>TOTAL PRICE <font color='green'>" + formatCurrency.format(totalPrice) + "₫ </font></html>");
                    totalPriceLabel.setFont(Style.FONT_BOLD_20);
                    gbc.gridy++;
                    summaryDetailsPn.add(totalPriceLabel, gbc);

                    orderBt = ButtonConfig.createCustomButtonGradientBorder("Order", Style.FONT_BOLD_30, new Color(14, 163, 204),
                            Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN, 3, 15, new Dimension(260, 50));
                    orderBt.addActionListener(new ActionListener() {
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

                            int i = JOptionPane.showConfirmDialog(null, "Would you like to confirm your order?", "Order Confirmation", JOptionPane.YES_NO_OPTION);
                            boolean saved = (i == 0);
                            if (saved && !productOrders.isEmpty()) {
                                CUSTOMER_ID = CurrentUser.CURRENT_CUSTOMER.getId();
                                MANAGER_ID= CurrentUser.CURRENT_MANAGER.getManagerId();
                                ADDRESS = addressField.getText();
                                int orderId = orderController.save(CUSTOMER_ID, MANAGER_ID, ADDRESS, STATUS_ORDER);
//                                productOrders.forEach(System.out::println);
                                System.out.println("saved");
                                orderDetailController.saves(orderId, ProductOrderConfig.getUnqueProductOrder(productOrders));

                                productOrders.clear();

                                var c = new Customer();
                                c.setAvataImg("src/main/java/img/837020177Screenshot 2024-10-20 134127.png");
                                bills = customerController.findCustomerOrderById(CUSTOMER_ID);
                                addCustomerNotification(c, new BillConfig(bills).getBillCurrent());
                                ToastNotification.showToast("Successful purchase!", 2500, 50, -1, -1);

                                cartContainer.removeAll();
                                cartContainer.add(emptyCartPn);
                                cartContainer.revalidate();
                                cartContainer.repaint();

                                ordersContainer.removeAll();
                                ordersContainer.revalidate();
                                ordersContainer.repaint();

                                upLoadOrderHistory();

                            } else {
                                ToastNotification.showToast("Cancel order!", 2500, 50, -1, -1);
                            }
                        }
                    });
                    gbc.gridy++;
                    gbc.anchor = GridBagConstraints.CENTER;
                    summaryDetailsPn.add(orderBt, gbc);
                    add(summaryDetailsPn, BorderLayout.CENTER);
                }
            }
        }
    }

    class OrderHistoryPanel extends JPanel {
        private OrdersPanel ordersPanel;
        private ToolPanel toolPanel;
        private CustomButton feedbackBt, searchBt, calendarBt;
        private JTextField searchField;
        private Date selectedDate;

        public OrderHistoryPanel() throws SQLException {
            setLayout(new BorderLayout());
            ordersPanel = new OrdersPanel();
            toolPanel = new ToolPanel();
            add(toolPanel, BorderLayout.NORTH);
            add(ordersPanel, BorderLayout.CENTER);
        }

        class ToolPanel extends JPanel {
            ToolPanel() {
                setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
                setBackground(Color.WHITE);




                calendarBt = ButtonConfig.createCustomButton("", Style.FONT_PLAIN_20, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 0, SwingConstants.CENTER, new Dimension(50, 50));
                ButtonConfig.setButtonIcon("src/main/java/Icon/calendarIcon.png", calendarBt, 10);

                // Tạo JDialog chứa JCalendar
                JDialog calendarDialog = new JDialog((Frame) null, "Select Date", true);
                calendarDialog.setSize(400, 400);
                calendarDialog.setLayout(new BorderLayout());
                calendarDialog.setLocation(700, 200);
                JCalendar calendar = new JCalendar();
                calendar.setBackground(Color.WHITE);
                calendar.setFont(Style.FONT_BOLD_15);
                calendarBt.addActionListener(e -> calendarDialog.setVisible(true));
                calendarDialog.add(calendar, BorderLayout.CENTER);

                CustomButton selectBt = ButtonConfig.createCustomButton("Select", Style.FONT_BOLD_18, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 5, SwingConstants.CENTER, new Dimension(300, 35));

                    calendarDialog.add(selectBt, BorderLayout.SOUTH);

                selectBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedDate = new Date(calendar.getDate().getTime());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        searchField.setText(dateFormat.format(selectedDate));
                        searchField.setForeground(Color.BLACK);
                        calendarDialog.setVisible(false);
                    }
                });


                searchField = TextFieldConfig.createTextField("Search Order", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(350, 50));
                searchField.addActionListener(e -> searchBt.doClick());

                searchBt = ButtonConfig.createCustomButton("", Style.FONT_PLAIN_20, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 0, SwingConstants.CENTER, new Dimension(60, 50));
                ButtonConfig.setButtonIcon("src/main/java/Icon/search_Icon.png", searchBt, 5);


                feedbackBt = ButtonConfig.createCustomButton("FeedBack", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 8, SwingConstants.CENTER, new Dimension(160, 55));
                ButtonConfig.setButtonIcon("src/main/java/Icon/feedback_Icon.png", feedbackBt, 12);
                feedbackBt.addActionListener(e-> new OpenEmailConfig());
                add(calendarBt);
                add(searchField);
                add(searchBt);
                add(Box.createRigidArea(new Dimension(50, 0)));
                add(feedbackBt);
            }

        }

        class OrdersPanel extends JPanel {
            JScrollPane ordersScrollPane;

            OrdersPanel() throws SQLException {
                setLayout(new BorderLayout());

                ordersContainer.setLayout(new BoxLayout(ordersContainer, BoxLayout.Y_AXIS));

                upLoadOrderHistory();

                ordersScrollPane = new JScrollPane(ordersContainer);
                setColorScrollPane(ordersScrollPane, Style.BACKGROUND_COLOR, Style.LIGHT_BlUE);
                ordersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                ordersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


                add(ordersScrollPane, BorderLayout.CENTER);
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
            int customerId = CurrentUser.CURRENT_CUSTOMER.getId();
            int managerId = CurrentUser.CURRENT_MANAGER.getManagerId();

            bills = customerController.findCustomerOrderById(customerId);
            var c = new Customer();
            c.setAvataImg("src/main/java/img/837020177Screenshot 2024-10-20 134127.png");
            showFullBills(new BillConfig(bills).getMetadataMap(), c);


            searchField = TextFieldConfig.createTextField("Search Notification", Style.FONT_PLAIN_18, Color.GRAY, new Dimension(320, 40));


            searchButton = ButtonConfig.createCustomButton("", Style.FONT_PLAIN_20, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 0, SwingConstants.CENTER, new Dimension(50, 40));
            ButtonConfig.setButtonIcon("src/main/java/Icon/search_Icon.png", searchButton, 5);

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
                notificationContainer.setBackground(Color.WHITE);
                notificationContainer.setLayout(new BoxLayout(notificationContainer, BoxLayout.Y_AXIS));

                scrollPane = new JScrollPane(notificationContainer);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                setColorScrollPane(scrollPane, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE);

                add(scrollPane, BorderLayout.CENTER);

            }
        }
    }

    class ChangeInformationPanel extends JPanel {
        JTextField emailField, fullNameField, addressField;
        CircularImage avatar;
        ChangeAvatarPanel changeAvatarPanel = new ChangeAvatarPanel();
        ChangeInfo changeInfo = new ChangeInfo();
        CustomButton updateBt, cancelBt, changePassBt, changeAvaBt;

        CustomerController customerController = new CustomerController();

        public ChangeInformationPanel() {
            setLayout(new BorderLayout());

            JPanel ChangePn = new JPanel(new GridLayout(1, 2));
            ChangePn.add(changeAvatarPanel);
            ChangePn.add(changeInfo);
            add(ChangePn, BorderLayout.CENTER);

            cancelBt = ButtonConfig.createCustomButton("Cancel");
            cancelBt.addActionListener(e -> cancelHandle());
            updateBt = ButtonConfig.createCustomButton("Update");
            updateBt.addActionListener(e -> updateHandle());
            changePassBt = ButtonConfig.createCustomButton("Change Password");
            changePassBt.addActionListener(e -> new ChangePasswordFrame("Customer").showVisible());

            JPanel updatePn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            updatePn.add(cancelBt);
            updatePn.add(updateBt);
            updatePn.add(changePassBt);
            add(updatePn, BorderLayout.SOUTH);
            reloadData();
        }

        class ChangeAvatarPanel extends JPanel {

            public ChangeAvatarPanel() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
                avatar = new CircularImage(CurrentUser.URL, 300, 300, false);
                avatar.setAlignmentX(Component.CENTER_ALIGNMENT);


                changeAvaBt = new CustomButton("Upload new image from Computer");
                changeAvaBt.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN);
                changeAvaBt.setBackgroundColor(Style.LIGHT_BlUE);
                changeAvaBt.setBorderRadius(20);
                changeAvaBt.setPreferredSize(new Dimension(450, 40));
                changeAvaBt.setFont(Style.FONT_BOLD_16);
                changeAvaBt.setHorizontalAlignment(SwingConstants.CENTER);
                changeAvaBt.setAlignmentX(Component.CENTER_ALIGNMENT);
                changeAvaBt.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select an Image");

                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        if (selectedFile != null && isImageFile(selectedFile)) {
                            avatar.setImage(selectedFile.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "Avatar updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a valid image file!", "Invalid File", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });

                add(Box.createVerticalGlue());
                add(avatar);
                add(Box.createRigidArea(new Dimension(0, 50)));
                add(changeAvaBt);
                add(Box.createVerticalGlue());
            }

            private boolean isImageFile(File file) {
                String[] allowedExtensions = {"jpg", "jpeg", "png", "gif"};
                String fileName = file.getName().toLowerCase();
                for (String ext : allowedExtensions) {
                    if (fileName.endsWith("." + ext)) {
                        return true;
                    }
                }
                return false;
            }
        }

        class ChangeInfo extends JPanel {
            private final String[] labels = {
                    "Email",
                    "Name",
                    "Address"
            };
            private final JTextField[] editableFields = new JTextField[labels.length];
            JButton[] editButtons = new JButton[labels.length];

            public ChangeInfo() {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                addTitle(gbc);
                initializeFields();

                for (int i = 0; i < labels.length; i++) {
                    gbc.gridx = 0;
                    gbc.gridy = 2 * i + 1;
                    gbc.gridwidth = 2;
                    add(new JLabel(labels[i] + ": "), gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 2 * i + 2;
                    gbc.gridwidth = 1;
                    add(editableFields[i], gbc);

                    if (i != 0) {
                        gbc.gridx = 1;
                        JButton editButton = ButtonConfig.createEditFieldButton(editableFields[i]);
                        editButtons[i] = editButton;
                        add(editButton, gbc);
                    }
                }
            }

            private void initializeFields() {
                editableFields[0] = emailField = TextFieldConfig.createUneditableTextField(labels[0]);
                editableFields[1] = fullNameField = TextFieldConfig.createUneditableTextField(labels[1]);
                editableFields[2] = addressField = TextFieldConfig.createUneditableTextField(labels[2]);
            }

            private void addTitle(GridBagConstraints gbc) {
                JLabel title = new JLabel("Change Your Information", SwingConstants.CENTER);
                title.setFont(Style.FONT_BOLD_24);
                title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                add(title, gbc);
            }
        }

        private void reloadData() {
            try {
                emailField.setText(CurrentUser.CURRENT_CUSTOMER.getEmail());
                fullNameField.setText(CurrentUser.CURRENT_CUSTOMER.getFullName());
                addressField.setText(CurrentUser.CURRENT_CUSTOMER.getAddress());
                avatar.setImage(CurrentUser.URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean hasNotChanged() {
            return emailField.getText().trim().equals(CurrentUser.CURRENT_CUSTOMER.getEmail()) &&
                    fullNameField.getText().trim().equals(CurrentUser.CURRENT_CUSTOMER.getFullName()) &&
                    addressField.getText().trim().equals(CurrentUser.CURRENT_CUSTOMER.getAddress()) &&
                    avatar.equals(new CircularImage(CurrentUser.URL, avatar.getWidth(), avatar.getHeight(), false));
        }

        private void cancelHandle() {
            if (!hasNotChanged()) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "You have unsaved changes. Are you sure you want to cancel?",
                        "Confirm Cancel",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    reloadData();
                    JOptionPane.showMessageDialog(null, "Changes have been canceled.", "Action Canceled", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Continue editing your changes.", "Action Resumed", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No changes to cancel.", "Action Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private void updateHandle() {
            if (hasNotChanged()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No changes detected. Please modify your information before updating.",
                        "No Updates Made",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            Map<JTextField, InputVerifier[]> fieldVerifierMap = new HashMap<>();
            fieldVerifierMap.put(emailField, new InputVerifier[]{new NotNullVerifier(), new EmailVerifier()});
            fieldVerifierMap.put(fullNameField, new InputVerifier[]{new NotNullVerifier(), new UserNameAccountVerifier()});
            fieldVerifierMap.put(addressField, new InputVerifier[]{new NotNullVerifier()});

            for (Map.Entry<JTextField, InputVerifier[]> entry : fieldVerifierMap.entrySet()) {
                JTextField field = entry.getKey();
                InputVerifier[] verifiers = entry.getValue();

                for (InputVerifier verifier : verifiers) {
                    if (!verifier.verify(field)) {
                        field.requestFocus();
                        return;
                    }
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "All fields are valid. Proceeding with update...",
                    "Validation Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            performUpdate();
        }

        private void performUpdate() {
            CurrentUser.CURRENT_CUSTOMER.setEmail(emailField.getText().trim());
            CurrentUser.CURRENT_CUSTOMER.setFullName(fullNameField.getText().trim());
            CurrentUser.CURRENT_CUSTOMER.setAddress(addressField.getText().trim());
            customerController.update(CurrentUser.CURRENT_CUSTOMER);
            ToastNotification.showToast("Your information has been successfully updated.", 2500, 50, -1, -1);
        }
    }

    // tạo ảnh cho sản phẩm
    public static ImageIcon createImageForProduct(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
//----------------------------------------------------------------------------------------------------------------------
    // thêm sản phầm vào panel catalog container
    public void addNewPanelToCatalogContainer(JPanel panel) {
        panel.setPreferredSize(new Dimension(330, 620));
        panel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = this.catalogContainer.getComponentCount() % 4;
        gbc.gridy = this.catalogContainer.getComponentCount() / 4;
        gbc.insets = new Insets(2, 2, 2, 2);

        this.catalogContainer.add(panel, gbc);
        this.catalogContainer.revalidate();
        this.catalogContainer.repaint();
    }

    // panel của 1 sản phầm trong catalog
    public JPanel createPanelForProductInCatalog(Product product) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
// Ảnh sản phẩm
        ArrayList<Model.Image> urls = product.getImages();
        ImageIcon[] images = new ImageIcon[urls.size()];
        ImageIcon defaultImage = new ImageIcon();
        for (int i = 0; i < images.length; i++) {
            images[i] = createImageForProduct(urls.get(i).getUrl(), 300, 300);
        }


        defaultImage = images.length != 0 ? images[0] : createImageForProduct("src/main/java/img/not-found-image.png", 300, 300);
        ;


        JLabel imageLabel = new JLabel(defaultImage);// Hiển thị hình ảnh đầu tiên
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(250,250));
        JPanel imagePn = new JPanel();
        imagePn.setBackground(Color.WHITE);
        imagePn.add(imageLabel);
        mainPanel.add(imagePn);


// Nút chuyển ảnh
        CustomButton previousBt = ButtonConfig.createCustomButton("Previous", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, SwingConstants.CENTER, new Dimension(140, 30));
        CustomButton nextBt = ButtonConfig.createCustomButton("Next", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, SwingConstants.CENTER, new Dimension(140, 30));
        int[] currentIndex = {0};
        previousBt.addActionListener(e -> {
            try {
                currentIndex[0] = (currentIndex[0] - 1 + images.length) % images.length;
                imageLabel.setIcon(images[currentIndex[0]]);
            } catch (ArithmeticException exception) {
                System.out.println(exception.getMessage());
            }
        });
        nextBt.addActionListener(e -> {
            try {
                currentIndex[0] = (currentIndex[0] + 1) % images.length;
                imageLabel.setIcon(images[currentIndex[0]]);
            } catch (ArithmeticException exception) {
                System.out.println(exception.getMessage());
            }
        });


        JPanel switchPn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        switchPn.setBackground(Color.WHITE);
        switchPn.setPreferredSize(new Dimension(300, 40));
        switchPn.add(previousBt);
        switchPn.add(nextBt);
        mainPanel.add(switchPn);


// Tên sản phẩm
        JLabel productName = new JLabel("<html><div style='text-align: center;'>" + product.getName() + "</div></html>", SwingConstants.CENTER);
        productName.setPreferredSize(new Dimension(310, 60));
        productName.setFont(Style.FONT_BOLD_24);

        JPanel productNamePn = new JPanel(new BorderLayout());
        productNamePn.setBackground(Color.WHITE);
        productNamePn.add(productName, BorderLayout.CENTER);
        mainPanel.add(productNamePn);

// Chi tiết sản phẩm
        String[][] displayFields = {
                {"Product ID", String.valueOf(product.getId())},
                {"Brand", product.getBrand()},
                {"CPU", product.getCpu()},
                {"RAM", product.getRam()},
                {"Storage", product.getMemory()}
        };
        JPanel detailsPn = new JPanel(new GridLayout(displayFields.length, 2));
        detailsPn.setBackground(Color.WHITE);

        for (String[] field : displayFields) {
            JLabel label = new JLabel(" " + field[0] + ":");
            label.setFont(Style.FONT_BOLD_18);
            detailsPn.add(label);

            JLabel valueLabel = new JLabel(field[1]);
            valueLabel.setFont(Style.FONT_PLAIN_18);
            detailsPn.add(valueLabel);
        }
        mainPanel.add(detailsPn);


// Giá sản phẩm
        String price = formatCurrency.format(product.getPrice());
        JLabel productPrice = new JLabel("  " + price + "₫");
        productPrice.setFont(new Font("Arial", Font.BOLD, 25));
        productPrice.setForeground(Style.CONFIRM_BUTTON_COLOR_GREEN);
        productPrice.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái giá

        JPanel pricePn = new JPanel(new BorderLayout());
        pricePn.setBackground(Color.WHITE);
        pricePn.add(productPrice, BorderLayout.WEST); // Đặt giá về phía trái
        mainPanel.add(pricePn);

// Nút thao tác
        JPanel detailCartPn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        detailCartPn.setBackground(Color.WHITE);
        CustomButton detailBt = ButtonConfig.createCustomButton("More details", Style.FONT_BOLD_16, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 10, SwingConstants.CENTER, new Dimension(140, 30));
        detailBt.addActionListener(e -> {
            this.productCatalogPanel.displayProductPanel.showProduct("oneProduct");
            addProductToSingleProduct(product);
        });
        CustomButton addToCartBt = ButtonConfig.createCustomButton("Add to Cart", Style.FONT_BOLD_16, Color.white, Style.CONFIRM_BUTTON_COLOR_GREEN, Style.LIGHT_GREEN, 10, SwingConstants.CENTER, new Dimension(140, 30));
        addToCartBt.addActionListener(e -> {
            //add
            if (productOrders.add(new ProductOrderConfig(product, 1))) {
                addNewPanelToCartContainer(createPanelForCart(product));
                ToastNotification.showToast("Product added to Cart!", 3000, 50, -1, -1);
//                                productOrders.remove(new ProductOrderConfig(product));
                totalItems += 1;
                totalPrice += product.getPrice();
                updatePriceQuantityInCart(totalItems, totalPrice);
            }
        });
        detailCartPn.add(detailBt);
        detailCartPn.add(addToCartBt);
        detailCartPn.setPreferredSize(new Dimension(330, 40));
        mainPanel.add(detailCartPn);

        return mainPanel;
    }

    // panel hiển thị toàn bộ thông tin 1 sản phẩm
    public void addProductToSingleProduct(Product product) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());

        //back button
        JPanel backPn = new JPanel(new FlowLayout(FlowLayout.LEFT,20,5));
        backPn.setBackground(Color.WHITE);
        CustomButton backBt = ButtonConfig.createCustomButton("Back to shop", Style.FONT_BOLD_16, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Style.MEDIUM_BLUE, 10, SwingConstants.CENTER, new Dimension(170, 35));
        ButtonConfig.setButtonIcon("src/main/java/Icon/back_Icon1.png", backBt, 5);
        backBt.addActionListener(e -> productCatalogPanel.displayProductPanel.showProduct("list"));
        backPn.add(backBt);
        wrapperPanel.add(backPn, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // image panel
        JPanel imagePn = new JPanel(new GridBagLayout());
        imagePn.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        ArrayList<Model.Image> urls = product.getImages();
        ImageIcon[] images = new ImageIcon[urls.size()];
        for (int i = 0; i < images.length; i++) {
            images[i] = createImageForProduct(urls.get(i).getUrl(), 350, 350);
        }
        JLabel imageLabel = new JLabel(images[0]);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        imagePn.add(imageLabel, gbc);

        // 2 button switching between product image
        CustomButton previousBt = ButtonConfig.createCustomButton("Previous", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, SwingConstants.CENTER, new Dimension(150, 30));
        CustomButton nextBt = ButtonConfig.createCustomButton("Next", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, SwingConstants.CENTER, new Dimension(150, 30));
        final int[] currentIndex = {0};
        // Thêm ActionListener cho nút "Previous"
        previousBt.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + images.length) % images.length;
            imageLabel.setIcon(images[currentIndex[0]]);
        });
        // Thêm ActionListener cho nút "Next"
        nextBt.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % images.length;
            imageLabel.setIcon(images[currentIndex[0]]);
        });


        JPanel switchPn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        switchPn.setBackground(Color.WHITE);
        switchPn.add(previousBt);
        switchPn.add(nextBt);
        gbc.gridy++;
        imagePn.add(switchPn, gbc);


        // panel mô ta đầy đủ thông tin sản phẩm
        JPanel productDetailsPn = new JPanel();
        productDetailsPn.setLayout(new BorderLayout());
        productDetailsPn.setPreferredSize(new Dimension(180, 200));

        JPanel productNamePn = new JPanel();
        productNamePn.setPreferredSize(new Dimension(180, 50));
        productNamePn.setBackground(Color.WHITE);
        productNamePn.setBackground(Color.WHITE);
        JLabel productName = new JLabel("<html>" + product.getName() + "</html>", SwingConstants.LEFT);
        productName.setFont(Style.FONT_BOLD_35);
        productNamePn.add(productName);
        productDetailsPn.add(productNamePn, BorderLayout.NORTH);

        String[][] displayFields = {{"Product ID", String.valueOf(product.getId())}
                , {"Brand", product.getBrand()}
                , {"Genre", product.getGenre()}
                , {"Operating System", product.getOperatingSystem()}
                , {"CPU", product.getCpu()}
                , {"Memory", product.getMemory()}
                , {"RAM", product.getRam()}
                , {"Made In", product.getMadeIn()}
                , {"Disk", product.getDisk()}
                , {"Screen Size", product.getMonitor()}
                , {"Weight", product.getWeight()}
                , {"Graphics Card", product.getCard()}};
        JPanel detailsPn = new JPanel(new GridLayout(displayFields.length, displayFields[0].length, 0, 0));
        detailsPn.setBackground(Color.WHITE);
        for (String[] field : displayFields) {
            JLabel label = new JLabel(field[0] + ":");
            label.setFont(Style.FONT_BOLD_18);
            detailsPn.add(label);
            JLabel valueLabel = new JLabel("<html>" + field[1] + "</html>");
            valueLabel.setFont(Style.FONT_PLAIN_18);
            detailsPn.add(valueLabel);
        }
        productDetailsPn.add(detailsPn, BorderLayout.CENTER);

        //   payment pn
        JPanel paymentPn = new JPanel();
        paymentPn.setBackground(Color.WHITE);
        paymentPn.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 5, 10, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        JLabel price = new JLabel("$ " + "100000000");
        price.setFont(Style.FONT_BOLD_30);
        price.setForeground(Style.CONFIRM_BUTTON_COLOR_GREEN);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.gridwidth = 2;
        paymentPn.add(price, gbc2);

        gbc2.insets = new Insets(5, 5, 5, 5);
        JLabel statuslb = new JLabel("Status: ");
        statuslb.setFont(Style.FONT_PLAIN_18);
        gbc2.gridy = 1;
        gbc2.gridwidth = 1;
        paymentPn.add(statuslb, gbc2);

        JLabel status = new JLabel(product.getStatus());
        status.setFont(Style.FONT_BOLD_24);
        status.setForeground(product.getStatus().equals("Available") ? Style.CONFIRM_BUTTON_COLOR_GREEN : Style.DELETE_BUTTON_COLOR_RED);
        gbc2.gridy = 2;
        gbc2.gridwidth = 2;
        paymentPn.add(status, gbc2);

        gbc2.insets = new Insets(10, 5, 10, 5);
        CustomButton addToCart = ButtonConfig.createCustomButton("Add to Cart", Style.FONT_BOLD_18, Color.white, Style.CONFIRM_BUTTON_COLOR_GREEN, Style.LIGHT_GREEN, 15, SwingConstants.CENTER, new Dimension(200, 50));
        addToCart.addActionListener(e -> ToastNotification.showToast("Product added to Cart!", 3000, 50, -1, -1));
        gbc2.gridy = 3;
        paymentPn.add(addToCart, gbc2);


        mainPanel.add(imagePn, BorderLayout.WEST);
        mainPanel.add(productDetailsPn, BorderLayout.CENTER);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

// xóa sửa
        productDetailContainer.add(wrapperPanel);
        this.productDetailContainer.revalidate();
        this.productDetailContainer.repaint();
    }

//----------------------------------------------------------------------------------------------------------------------
    // add new panel contain product to CART Panel
    public void addNewPanelToCartContainer(JPanel panel) {
        if (cartContainer.getComponent(0).equals(emptyCartPn)) {
            cartContainer.remove(emptyCartPn);
        }

        this.cartContainer.add(panel, 0);
        ordersContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        this.cartContainer.revalidate();
        this.cartContainer.repaint();
    }

    public JPanel createPanelForCart(Product product) {
        ProductOrderConfig productOrderConfig = new ProductOrderConfig(product, 1);

        //panel bọc mainPanel lại để tránh mainPanel giãn nở ra -> vỡ giao diện
        JPanel wrapperPanel = new JPanel(new FlowLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setPreferredSize(new Dimension(920, 180));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        wrapperPanel.add(mainPanel);
        // Panel cho ảnh và thông tin sản phẩm (ở CENTER)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(610, 180));

        JLabel imageLabel = new JLabel(createImageForProduct(product.getImages().get(0).getUrl(), 180, 180));
        centerPanel.add(imageLabel);

        // Thêm thông tin sản phẩm
        JPanel detailsPn = new JPanel(new GridBagLayout());
        detailsPn.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel productName = LabelConfig.createLabel("<html>" + product.getName() + "</html>", Style.FONT_BOLD_25, Color.BLACK, SwingConstants.LEFT);
        productName.setPreferredSize(new Dimension(300, 50));
        gbc.gridy++;
        detailsPn.add(productName, gbc);

        JLabel productID = LabelConfig.createLabel("Product ID: " + product.getId(), Style.FONT_PLAIN_15, Color.BLACK, SwingConstants.LEFT);
        gbc.gridy++;
        gbc.insets = new Insets(3, 5, 3, 5);
        detailsPn.add(productID, gbc);

        JLabel brand = LabelConfig.createLabel("Brand: " + product.getBrand(), Style.FONT_PLAIN_15, Color.BLACK, SwingConstants.LEFT);
        JLabel type = LabelConfig.createLabel("Type: " + product.getGenre(), Style.FONT_PLAIN_15, Color.BLACK, SwingConstants.LEFT);

        JLabel technicalDetails = LabelConfig.createLabel("CPU " + product.getCpu()+" / RAM "+product.getRam()+" / "+product.getMemory(), Style.FONT_PLAIN_15, Color.BLACK, SwingConstants.LEFT);

        gbc.gridy++;
        detailsPn.add(brand, gbc);
        gbc.gridy++;
        detailsPn.add(type, gbc);
        gbc.gridy++;
        detailsPn.add(technicalDetails, gbc);

        centerPanel.add(detailsPn);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        //panel for price, quantity and remove, on the right side.
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 30));
        rightPanel.setBackground(Color.WHITE);


        // price
        JPanel priceQuantityPn = new JPanel(new GridLayout(2, 1, 20, 20));
        priceQuantityPn.setBackground(Color.WHITE);
        JLabel price = LabelConfig.createLabel(formatCurrency.format(product.getPrice()) + "₫", Style.FONT_BOLD_25, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.CENTER);
        priceQuantityPn.add(price);

        final int[] countItem = {1};// quantity tracking variable.

        JPanel quantityPn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        quantityPn.setBackground(Color.WHITE);

        JTextField quantityField = TextFieldConfig.createTextField("" + countItem[0], Style.FONT_BOLD_20,
                Color.BLACK, Color.white, Color.GRAY, new Dimension(75, 43), false);
        quantityField.setHorizontalAlignment(SwingConstants.CENTER);

        CustomButton subBt = ButtonConfig.createCustomButton("-", Style.FONT_BOLD_22, Style.DELETE_BUTTON_COLOR_RED, Color.white,
                Style.LIGHT_BlUE, Color.GRAY, 1, 5, SwingConstants.CENTER, new Dimension(45, 45));
        subBt.addActionListener(e -> {
            if (countItem[0] > 1) {
                countItem[0]--;
                quantityField.setText(countItem[0] + "");
                totalItems--;
                totalPrice -= product.getPrice();
                updatePriceQuantityInCart(totalItems, totalPrice);

                productOrderConfig.setQuatity(countItem[0]);// đặt số lượng sản phẩm cho đơn hàng
                this.productOrders.add(productOrderConfig);
            }
        });

        CustomButton addBt = ButtonConfig.createCustomButton("+", Style.FONT_BOLD_20, Style.CONFIRM_BUTTON_COLOR_GREEN, Color.white,
                Style.LIGHT_BlUE, Color.GRAY, 1, 5, SwingConstants.CENTER, new Dimension(45, 45));
        addBt.addActionListener(e -> {
            totalItems -= countItem[0];
            countItem[0]++;
            quantityField.setText(countItem[0] + "");
            totalItems += countItem[0];
            totalPrice += product.getPrice();
            updatePriceQuantityInCart(totalItems, totalPrice);

            productOrderConfig.setQuatity(countItem[0]);
            this.productOrders.add(productOrderConfig);
        });
        quantityPn.add(subBt);
        quantityPn.add(quantityField);
        quantityPn.add(addBt);

        priceQuantityPn.add(quantityPn);
        rightPanel.add(priceQuantityPn);

        // Nút Remove
        CustomButton removeBt = ButtonConfig.createCustomButton("", Style.FONT_TITLE_BOLD_45, Color.BLACK, Color.WHITE, Style.LIGHT_BlUE, 10, SwingConstants.CENTER, new Dimension(60, 50));
        ButtonConfig.setButtonIcon("src/main/java/Icon/remove_Icon.png", removeBt, 10);
        removeBt.addActionListener(e -> {
            // tính lại giá và số lượng sản phẩm
            totalItems -= countItem[0];
            totalPrice -= countItem[0] * product.getPrice();
            updatePriceQuantityInCart(totalItems, totalPrice);
            //remove panel
            this.productOrders.remove(productOrderConfig);
            removePanelFromCartContainer(wrapperPanel);
        });
        rightPanel.add(removeBt);

        productOrderConfig.setQuatity(countItem[0]);// đặt số lượng sản phẩm cho đơn hàng
        this.productOrders.add(productOrderConfig);

        mainPanel.add(rightPanel, BorderLayout.EAST);
        return wrapperPanel;
    }

    private void updatePriceQuantityInCart(int items, int price) {
        productCatalogPanel.cartPanel.totalItemsTitle.setText(items + " items    ");
        productCatalogPanel.cartPanel.totalItemsLabel.setText("<html>ITEMS <font color='green'>" + items + "</font></html>");
        productCatalogPanel.cartPanel.totalPriceLabel.setText("<html>TOTAL PRICE <font color='green'>" + formatCurrency.format(price) + "₫ </font></html>");
    }

    private void showFullBills(Map<Integer, List<CustomerOrderDTO>> map, Customer customer) {
        for (Map.Entry<Integer, List<CustomerOrderDTO>> data : map.entrySet()) {
            addCustomerNotification(customer, BillConfig.generateBill((ArrayList<CustomerOrderDTO>) data.getValue()));
        }
    }

    private JPanel createEmptyPanel() {
        productOrders.clear();
        JPanel emptyPn = new JPanel(new GridLayout(1, 2));
        emptyPn.setBackground(Color.WHITE);

        JLabel emptyImg = new JLabel();
        emptyImg.setIcon(createImageForProduct("src/main/java/img/emptyCart.png", 400, 400));
        emptyPn.add(emptyImg);

        JPanel textPn = new JPanel(new GridBagLayout());
        textPn.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 2, 10, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel emptyLb = new JLabel("Your Cart is empty!");
        emptyLb.setFont(Style.FONT_BOLD_30);
        textPn.add(emptyLb, gbc);

        gbc.gridy++;
        CustomButton goShopping = ButtonConfig.createCustomButton("Browse our products now", Style.FONT_PLAIN_18, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, SwingConstants.CENTER, new Dimension(250, 40));
        goShopping.addActionListener(e -> {
            productCatalogPanel.show("catalog");
        });
        textPn.add(goShopping, gbc);

        emptyPn.add(textPn);

        return emptyPn;
    }

    // method delete a panel in cartContainer
    public void removePanelFromCartContainer(JPanel panel) {
        this.cartContainer.remove(panel);

        if (this.cartContainer.getComponentCount() == 0) {
            this.cartContainer.add(emptyCartPn);
        }

        this.cartContainer.revalidate();
        this.cartContainer.repaint();
    }

//---------------------------------------------------------------------------------------

    public void addCustomerNotification(Customer customer, String text) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel timeLabel = new JLabel("<html>" + now.format(timeFormatter) + "<br>" + now.format(dateFormatter) + "</html>");

        CircularImage avatar = new CircularImage(customer.getAvataImg(), 80, 80, false);

        JLabel customerName = new JLabel(customer.getFullName());
        customerName.setFont(Style.FONT_PLAIN_25);

        JTextArea message = new JTextArea(text);
        message.setBackground(Color.WHITE);
        message.setForeground(Color.BLACK);
        message.setFont(new Font("Arial", Font.PLAIN, 10));
        message.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, 2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE),
                BorderFactory.createEmptyBorder(3, 3, 3, 8)
        ));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        message.setOpaque(true);

        // Cố định chiều rộng và tính toán chiều cao phù hợp
        int width = 800;
        message.setSize(new Dimension(width, Short.MAX_VALUE));
        int preferredHeight = message.getPreferredSize().height;
        message.setPreferredSize(new Dimension(width, preferredHeight));

        // Bọc JTextArea trong JScrollPane nhưng tắt thanh cuộn ngang
        JScrollPane scrollPane = new JScrollPane(message);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

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

        notificationContainer.add(main, 0);
        notificationContainer.revalidate();
        notificationContainer.repaint();
    }
//---------------------------------------------------------------------------------------

    public void addOrderToContainer(JPanel panel) {
        this.ordersContainer.add(panel);
        this.ordersContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        this.ordersContainer.revalidate();
        this.ordersContainer.repaint();
    }

    public JPanel createOrderPn(int orderId ,ArrayList<CustomerOrderDetailDTO> customerOrderDTOs) {
        JPanel main = new JPanel(new BorderLayout());

        JPanel titlePn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePn.setPreferredSize(new Dimension(100, 50));
        titlePn.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        JLabel status = new JLabel("Order awaiting shipment");
        status.setFont(Style.FONT_BOLD_30);
        status.setForeground(Color.WHITE);
        titlePn.add(status);

        JPanel datePn = new JPanel(new BorderLayout());
        datePn.setBackground(Color.WHITE);
        JLabel orderDate = LabelConfig.createLabel("    Order Date: " + customerOrderDTOs.get(0).customerOrderDTO().getOrderDate(), Style.FONT_BOLD_18, Color.BLACK, SwingConstants.LEFT);
        datePn.add(orderDate, BorderLayout.WEST);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(titlePn);
        top.add(datePn);
        main.add(top, BorderLayout.NORTH);


        JPanel mid = new JPanel(new GridLayout(0, 2));
        mid.setBackground(Color.WHITE);
        int totalPrice = 0;
        for (var item : customerOrderDTOs) {
            totalPrice += item.customerOrderDTO().getQuantity() * item.customerOrderDTO().getUnitPrice();
            mid.add(productOrderPn(item));
        }

        main.add(mid, BorderLayout.CENTER);

        JPanel bottomPn = new JPanel(new BorderLayout());
        bottomPn.setBackground(Color.WHITE);
        bottomPn.setPreferredSize(new Dimension(100, 60));
        MatteBorder botBorder = new MatteBorder(1, 0, 0, 0, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        EmptyBorder margin = new EmptyBorder(5, 0, 0, 0);

        Border combinedBorder = new CompoundBorder(margin, botBorder);
        bottomPn.setBorder(combinedBorder);

        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeft.setBackground(Color.WHITE);
        CustomButton viewBill = ButtonConfig.createCustomButton(" View Bill", Style.FONT_BOLD_15, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 10, SwingConstants.CENTER, new Dimension(150, 40));
        ButtonConfig.setButtonIcon("src/main/java/Icon/bill_Icon.png", viewBill, 15);
        bottomLeft.add(viewBill);


        CustomButton cancelOrder = ButtonConfig.createCustomButton(" Cancel Order", Style.FONT_BOLD_15, Style.DELETE_BUTTON_COLOR_RED,
                Color.white, Style.LIGHT_BlUE, Style.DELETE_BUTTON_COLOR_RED, 1, 10, SwingConstants.CENTER, new Dimension(180, 40));


        CustomButton repurchase = ButtonConfig.createCustomButton(" Repurchase", Style.FONT_BOLD_15, Style.CONFIRM_BUTTON_COLOR_GREEN,
                Color.white, Style.LIGHT_BlUE, Style.CONFIRM_BUTTON_COLOR_GREEN, 1, 10, SwingConstants.CENTER, new Dimension(180, 40));
        ButtonConfig.setButtonIcon("src/main/java/Icon/repurchase_Icon.png", repurchase, 15);
        bottomLeft.add(repurchase);


        // check order date
        var date = customerOrderDTOs.get(0).customerOrderDTO().getOrderDate();

        if(DateConfig.cancelOrderLimit(date,3)){
            ButtonConfig.setButtonIcon("src/main/java/Icon/cancelOrder_Icon.png", cancelOrder, 15);
            bottomLeft.add(cancelOrder);
        }
        bottomLeft.add(repurchase);


        cancelOrder.addActionListener(e->{
            OrderController controller= new OrderController();
            if ( JOptionPane.showConfirmDialog(null,"Cancel order") == 0 && controller.updateStatusOrder(OrderType.UN_ACTIVE,orderId)){
                ToastNotification.showToast("Cancel order",3000,30,-1,-1);
                cancelOrder.setEnabled(false);
            }
        });

        repurchase.addActionListener(e->{
            // mua lai thi phai reload cho hien len
         if(JOptionPane.showConfirmDialog(null,"chua code anh oi  chua có logic mua hàng") == 0) {
//             addOrderToContainer(createOrderPn(orderId, customerOrderDTOs));
             int reOrderId = orderController.save(CurrentUser.CURRENT_CUSTOMER.getId(), CurrentUser.CURRENT_MANAGER.getManagerId(), ADDRESS, STATUS_ORDER);
             System.out.println(reOrderId);
//             productOrders.forEach(System.out::println);
             System.out.println("saved");
             orderDetailController.saves(reOrderId, customerOrderDTOs);
             productOrders.clear();
             ToastNotification.showToast("Successful purchase!", 2500, 50, -1, -1);


             var c = new Customer();
             c.setAvataImg("src/main/java/img/837020177Screenshot 2024-10-20 134127.png");
             bills = customerController.findCustomerOrderById(CurrentUser.CURRENT_CUSTOMER.getId());

             addCustomerNotification(c, new BillConfig(bills).getBillCurrent());

             ordersContainer.removeAll();
             ordersContainer.revalidate();
             ordersContainer.repaint();
             upLoadOrderHistory();
         }
        });






        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRight.setBackground(Color.WHITE);
        JLabel items = LabelConfig.createLabel(customerOrderDTOs.size() + " items:  ", Style.FONT_BOLD_25, Color.BLACK, SwingConstants.RIGHT);
        String priceFormated = formatCurrency.format(totalPrice);
        JLabel totalPriceLabel = LabelConfig.createLabel(priceFormated + "₫", Style.FONT_BOLD_25, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.LEFT);

        bottomRight.add(items);
        bottomRight.add(totalPriceLabel);


        bottomPn.add(bottomLeft, BorderLayout.CENTER);
        bottomPn.add(bottomRight, BorderLayout.EAST);
        main.add(bottomPn, BorderLayout.SOUTH);


        return main;
    }

    public JPanel productOrderPn(CustomerOrderDetailDTO customerOrderDTO) {
        // Tạo panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(400, 160));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Panel chứa ảnh sản phẩm
        JPanel imgPn = new JPanel();
        imgPn.setBackground(Color.WHITE);
        JLabel proImg = new JLabel(createImageForProduct(customerOrderDTO.images().get(0).getUrl(), 150, 150));
        imgPn.add(proImg);
        mainPanel.add(imgPn, BorderLayout.WEST);

        // Panel chứa thông tin sản phẩm sử dụng GridBagLayout
        JPanel proDetails = new JPanel(new GridBagLayout());
        proDetails.setBackground(Color.WHITE);
        proDetails.setPreferredSize(new Dimension(500, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.4;
        JLabel proName = LabelConfig.createLabel(customerOrderDTO.customerOrderDTO().getProductName(), Style.FONT_BOLD_18, Color.BLACK, SwingConstants.LEFT);
        proDetails.add(proName, gbc);

        gbc.gridy++;
        gbc.weighty = 0.1;
        JLabel proID = LabelConfig.createLabel("Product ID:  " + customerOrderDTO.customerOrderDTO().getProductId(), Style.FONT_PLAIN_13, Color.BLACK, SwingConstants.LEFT);
        proDetails.add(proID, gbc);
        gbc.gridy++;
        JLabel proBrand = LabelConfig.createLabel("Brand: " + customerOrderDTO.customerOrderDTO().getProductBrand(), Style.FONT_PLAIN_13, Color.BLACK, SwingConstants.LEFT);
        proDetails.add(proBrand, gbc);

        gbc.gridy++;
        JLabel proTechnicalDetail = LabelConfig.createLabel("CPU " + customerOrderDTO.customerOrderDTO().getCpu() + " / " +
                        "RAM " + customerOrderDTO.customerOrderDTO().getRam() + " / " +
                        "Storage " + customerOrderDTO.customerOrderDTO().getMemory(),
                Style.FONT_PLAIN_13, Color.BLACK, SwingConstants.LEFT);
        proDetails.add(proTechnicalDetail, gbc);

        gbc.gridy++;
        JPanel bottomPn = new JPanel(new GridLayout(1, 2));

        JPanel pricePn = new JPanel(new FlowLayout(FlowLayout.LEFT));// panel xem giá của 1 sản phẩm
        pricePn.setBackground(Color.WHITE);
        String unitPrice = formatCurrency.format(customerOrderDTO.customerOrderDTO().getUnitPrice());
        JLabel proPrice = LabelConfig.createLabel(unitPrice + "₫", Style.FONT_BOLD_18, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.LEFT);
        pricePn.add(proPrice);

        JPanel quantityPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));// panel xem số lượng mua của 1 sản phẩm
        quantityPn.setBackground(Color.WHITE);
        JLabel proQuantity = LabelConfig.createLabel("x" + customerOrderDTO.customerOrderDTO().getQuantity(), Style.FONT_BOLD_18, Color.BLACK, SwingConstants.RIGHT);
        quantityPn.add(proQuantity);

        bottomPn.add(pricePn);
        bottomPn.add(quantityPn);
        proDetails.add(bottomPn, gbc);

        // Thêm panel chứa thông tin sản phẩm vào mainPanel
        mainPanel.add(proDetails, BorderLayout.CENTER);

        return mainPanel;
    }
    private void upLoadOrderHistory(){
        ProductController productController = new ProductController();
        var k = customerController.getCustomerOrderDetail(CurrentUser.CURRENT_CUSTOMER.getId());
        Map<KeyOrderDTO, ArrayList<CustomerOrderDTO>> mapOrder = new BillConfig(bills).convertDataToBills();
        OrderHistoryConfig orderHistoryConfig = new OrderHistoryConfig(k);
        for (Map.Entry<Integer, List<CustomerOrderDetailDTO>> data : orderHistoryConfig.get().entrySet()) {
            try {
                addOrderToContainer(createOrderPn(data.getKey(),(ArrayList < CustomerOrderDetailDTO >)data.getValue()));
            } catch (Exception ee) {

            }
        }
    }

}
