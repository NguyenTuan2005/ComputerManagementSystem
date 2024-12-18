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
import org.jfree.data.json.JSONUtils;
import view.OtherComponent.ChangePasswordFrame;
import view.OtherComponent.NotFoundEntity;
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
    JPanel cartContainer = new JPanel();
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

    private Set<ProductOrderConfig> productOrders = new LinkedHashSet<>();
    private static ArrayList<CustomerOrderDTO> bills = new ArrayList<>();
    private static OrderController orderController = new OrderController();
    private static OrderDetailController orderDetailController = new OrderDetailController();
    private static CustomerController customerController = new CustomerController();

    private JTextField emailField, nameField, addressField;
    private GridBagConstraints gbc;


    static DecimalFormat formatCurrency = new DecimalFormat("#,###");


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
        ProductCatalogInnerPn productCatalogInnerPn = new ProductCatalogInnerPn();
        CartPanel cartPanel = new CartPanel();
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
                searchBt.addActionListener( e ->{
                    productDetailContainer.removeAll();
                    searchProductByName(searchTextField.getText());

                });


                gbc.gridx = 2;
                gbc.anchor = GridBagConstraints.EAST;
                searchBar.add(searchBt, gbc);

                cartButton = createCustomButtonGradientBorder("Cart", Style.FONT_BOLD_16, Color.WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.MENU_BUTTON_COLOR_GREEN, Style.BACKGROUND_COLOR, 4, 20, new Dimension(80, 80));
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

                allBt = createCustomButton("All", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                allBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(allBt);
                        productDetailContainer.removeAll();
                        displayProductOnPage(DisplayProductOnPageType.ALL);

                    }
                });
                gaming = createCustomButton("Laptop Gaming", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(150, 25));
                gaming.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(gaming);
                        productDetailContainer.removeAll();
                        displayProductOnPage(DisplayProductOnPageType.LAPTOP_GAMING);

                    }
                });
                office = createCustomButton("Laptop Office", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(150, 25));
                office.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(office);
                        displayProductOnPage(DisplayProductOnPageType.LAPTOP_OFFICE);
                    }
                });
                pcCase = createCustomButton("PC Case", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                pcCase.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(pcCase);
                    }
                });
                cheapest = createCustomButton("Cheapest", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                cheapest.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(cheapest);
                        cartContainer.removeAll();
                        cartContainer.add(emptyCartPn);
                        cartContainer.revalidate();
                        cartContainer.repaint();
                        displayProductOnPage(DisplayProductOnPageType.CHEAP);

                    }
                });

                luxury = createCustomButton("Luxury", Style.FONT_BOLD_15, Color.BLACK, Color.white, Style.LIGHT_BlUE, Style.BACKGROUND_COLOR, 2, 25, new Dimension(120, 25));
                luxury.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateSelectedButton(luxury);
                        displayProductOnPage(DisplayProductOnPageType.CHEAP);
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
                    displayProductOnPage(DisplayProductOnPageType.ALL);
                    scrollPane = new JScrollPane(catalogContainer);
                    setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Color.WHITE);
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
                    setLayout(new BorderLayout());
                    add(productDetailContainer, BorderLayout.CENTER);
                }
            }

        }

        class CartPanel extends JPanel {
            CustomButton backBt;
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

                backBt = ButtonConfig.createCustomButton("Back", Style.FONT_BOLD_16, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        Style.MEDIUM_BLUE, 5, SwingConstants.CENTER, new Dimension(110, 30));
                ButtonConfig.setButtonIcon("src/main/java/Icon/back_Icon1.png", backBt,5);
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

                private JLabel totalItem, totalPrice;
                private CustomButton orderBt;

                PaymentPanel() {
                    setLayout(new GridBagLayout());
                    setPreferredSize(new Dimension(320, 300));
                    setBackground(Color.WHITE);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.weightx = 1.0;

                    JLabel title = new JLabel("Delivery Information");
                    title.setFont(Style.FONT_BOLD_24);
                    title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    add(title, gbc);

                    emailField = TextFieldConfig.createTextField("", Style.FONT_PLAIN_18, Color.BLACK, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(300, 45));
                    emailField.setText(CurrentUser.CURRENT_CUSTOMER.getEmail());
                    gbc.gridy = 1;
                    add(emailField, gbc);

                    nameField = TextFieldConfig.createTextField("", Style.FONT_PLAIN_18, Color.BLACK, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(300, 45));
                    nameField.setText(CurrentUser.CURRENT_CUSTOMER.getFullName());
                    gbc.gridy = 2;
                    add(nameField, gbc);


                    addressField = TextFieldConfig.createTextField("", Style.FONT_PLAIN_18, Color.BLACK, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(300, 45));
                    addressField.setText(CurrentUser.CURRENT_CUSTOMER.getAddress());
                    gbc.gridy = 4;
                    add(addressField, gbc);

                    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                    separator.setOrientation(SwingConstants.HORIZONTAL);

                    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
                    gbc.gridy = 5;
                    add(separator, gbc);

                    JLabel cartTotal = new JLabel("Cart Total");
                    cartTotal.setFont(Style.FONT_BOLD_24);
                    cartTotal.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                    gbc.gridy = 6;
                    add(cartTotal, gbc);


//                    totalItem = new JLabel(totalItemCount + " Items in your Cart");
                    totalItem = new JLabel("<html> <font color='green'>" + totalItemCount + "</font> Items in your Cart</html>");
                    totalItem.setFont(Style.FONT_BOLD_20);
                    gbc.gridy = 7;
                    add(totalItem, gbc);


                    totalPrice = new JLabel("<html> <font color='black'>Total:  </font> <font color='red'>$" + totalPriceCount + "</font></html>");
                    totalPrice.setFont(Style.FONT_BOLD_20);
                    gbc.gridy = 8;
                    add(totalPrice, gbc);

                    orderBt = ButtonConfig.createCustomButtonGradientBorder("Order", Style.FONT_BOLD_30, new Color(14, 163, 204), Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN, 2, 15, new Dimension(250, 50));
                    orderBt.setHorizontalAlignment(SwingConstants.CENTER);
                    orderBt.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                            if (saved) {
                                int customerId = CurrentUser.CURRENT_CUSTOMER.getId();
                                int managerId = CurrentUser.CURRENT_MANAGER.getManagerId();
                                String address = addressField.getText();
                                String status = STATUS_ORDER;
                                int orderId = orderController.save(customerId, managerId, address, status);
                                orderDetailController.saves(orderId, ProductOrderConfig.getUnqueProductOrder(productOrders));
                                productOrders.clear();
                                var c = new Customer();
                                c.setAvataImg("src/main/java/img/837020177Screenshot 2024-10-20 134127.png");
                                bills = customerController.findCustomerOrderById(customerId);
                                addCustomerNotification(c, new BillConfig(bills).getBillCurrent());
                                ToastNotification.showToast("Successful purchase !!!", 2500, 50, -1, -1);
                                cartContainer.removeAll();
                                cartContainer.add(emptyCartPn);
                                cartContainer.revalidate();
                                cartContainer.repaint();
                            } else {
                                ToastNotification.showToast("Cancel order !!!", 2500, 50, -1, -1);
                            }
                        }
                    });
                    gbc.gridy = 9;
                    add(orderBt, gbc);
                }
            }


            class ProductsInCartPanel extends JPanel {

                ProductsInCartPanel() {
                    setLayout(new BorderLayout());
                    cartContainer.setLayout(new BoxLayout(cartContainer, BoxLayout.Y_AXIS));

                    cartContainer.add(emptyCartPn);
                    scrollPane = new JScrollPane(cartContainer);
                    scrollPane.setBackground(Color.WHITE);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Style.LIGHT_BlUE);
                    add(scrollPane, BorderLayout.CENTER);

                }
            }

        }
    }

    class OrderHistoryPanel extends JPanel {
        private OrdersPanel ordersPanel;
        private ToolPanel toolPanel;
        private String[] columnNames = {"Serial Number", "Order ID", "Order Date", "Product Name", "Product ID", "Quantity", "Total Price", "Status", "Shipping Address", "Delivery Date"};
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
                setLayout(new FlowLayout());
                setBackground(Color.WHITE);


                feedbackBt = ButtonConfig.createCustomButton("FeedBack", Style.FONT_BOLD_13, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 8, SwingConstants.CENTER, new Dimension(100, 70));
                feedbackBt.setIcon(new ImageIcon("src/main/java/Icon/feedback_Icon.png"));
                feedbackBt.setHorizontalTextPosition(SwingConstants.CENTER);
                feedbackBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                feedbackBt.addActionListener(e->{
                    new OpenEmailConfig();
                });


                calendarBt = ButtonConfig.createCustomButton("", Style.FONT_PLAIN_20, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 0, SwingConstants.CENTER, new Dimension(50, 50));
                ButtonConfig.setButtonIcon("src/main/java/Icon/calendarIcon.png", calendarBt,10);

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
                searchBt = ButtonConfig.createCustomButton("", Style.FONT_PLAIN_20, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, 0, SwingConstants.CENTER, new Dimension(60, 50));
                ButtonConfig.setButtonIcon("src/main/java/Icon/search_Icon.png", searchBt,5);


                add(feedbackBt);
                add(calendarBt);
                add(searchField);
                add(searchBt);
            }

        }

        class OrdersPanel extends JPanel {
            JScrollPane ordersScrollPane;

            OrdersPanel() throws SQLException {
                setLayout(new BorderLayout());

                ordersContainer.setLayout(new BoxLayout(ordersContainer, BoxLayout.Y_AXIS));
              //duy
                displayProductHistoryOnPage();
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
            ButtonConfig.setButtonIcon("src/main/java/Icon/search_Icon.png", searchButton,5);

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

    private static CustomButton createCustomButton(String title, Font font, Color textColor, Color backgroundColor, Color hoverColor, Color borderColor, int thickness, int radius, Dimension size) {
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
    public static ImageIcon createImageForProduct(String filePath, int width, int height) {
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
        for (int i = 0; i < images.length; i++) {
            images[i] = createImageForProduct(urls.get(i).getUrl(), 300, 300);
        }
        ImageIcon defaultImg = null;
        if(images.length ==0) defaultImg = new ImageIcon("src/main/java/img/i-404.png");else defaultImg = images[0]; // fix lai cai hình
        JLabel imageLabel = new JLabel(defaultImg);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(250,250));
        JPanel imagePn = new JPanel();
        imagePn.setBackground(Color.WHITE);
        imagePn.add(imageLabel);
        mainPanel.add(imagePn);

// Nút chuyển ảnh
        CustomButton previousBt = createCustomButton("Previous", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, new Dimension(140, 30));
        CustomButton nextBt = createCustomButton("Next", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, new Dimension(140, 30));
        int[] currentIndex = {0};
        previousBt.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + images.length) % images.length;
            imageLabel.setIcon(images[currentIndex[0]]);
        });
        nextBt.addActionListener(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % images.length;
            imageLabel.setIcon(images[currentIndex[0]]);
        });
        JPanel switchPn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        switchPn.setBackground(Color.WHITE);
        switchPn.setPreferredSize(new Dimension(300, 40));
        switchPn.add(previousBt);
        switchPn.add(nextBt);

        mainPanel.add(switchPn, gbc);

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
        JPanel main = new JPanel();
        main.setBackground(Color.WHITE);
        main.setLayout(new BorderLayout());

        // image panel
        JPanel imagePn = new JPanel(new GridBagLayout());
        imagePn.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        CustomButton backBt = ButtonConfig.createCustomButton("Back", Style.FONT_BOLD_16, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Style.MEDIUM_BLUE, 5, SwingConstants.CENTER, new Dimension(110, 30));
        ButtonConfig.setButtonIcon("src/main/java/Icon/back_Icon1.png", backBt, 5);
        backBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productCatalogPanel.displayProductPanel.showProduct("list");
            }
        });
        JPanel backPn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPn.setBackground(Color.WHITE);
        backPn.add(backBt);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        imagePn.add(backPn, gbc);

        ArrayList<Model.Image> urls = product.getImages();
        ImageIcon[] images = new ImageIcon[urls.size()];
        for (int i = 0; i < images.length; i++) {
            images[i] = createImageForProduct(urls.get(i).getUrl(), 350, 350);
        }
        JLabel imageLabel = new JLabel(images[0]);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        imagePn.add(imageLabel, gbc);

        // 2 button switching between product image
        CustomButton previousBt = createCustomButton("Previous", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, new Dimension(150, 30));
        CustomButton nextBt = createCustomButton("Next", Style.FONT_BOLD_16, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, new Dimension(150, 30));
        int[] currentIndex = {0};
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
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        imagePn.add(switchPn, gbc);

        //  Display more details of product
        JPanel rightPn = new JPanel(new BorderLayout());

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


        main.add(imagePn, BorderLayout.WEST);
        rightPn.add(productDetailsPn, BorderLayout.CENTER);
        rightPn.add(paymentPn, BorderLayout.EAST);
        main.add(rightPn, BorderLayout.CENTER);

// xóa sửa
        productDetailContainer.add(main);
        this.productDetailContainer.revalidate();
        this.productDetailContainer.repaint();
    }

    public void addNewPanelToCartContainer(JPanel panel) {
        panel.setPreferredSize(new Dimension(920, 200));
//        panel.setMaximumSize(new Dimension(900, 220));

        this.cartContainer.setLayout(new BoxLayout(this.cartContainer, BoxLayout.Y_AXIS));

        if (cartContainer.getComponent(0).equals(emptyCartPn)) {
            cartContainer.remove(emptyCartPn);
        }

        this.cartContainer.add(panel);

        this.cartContainer.revalidate();
        this.cartContainer.repaint();
    }

    //-------------------------------------------------------------------------------------------------
    //duy code
    public JPanel createPanelForCart(Product product) {

        ProductOrderConfig productOrderConfig = new ProductOrderConfig(product, 1);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));

        JLabel imageLabel = new JLabel(createImageForProduct(product.getImages().get(0).getUrl(), 200, 200));
        panel.add(imageLabel, BorderLayout.WEST);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setBackground(Color.WHITE);
        JLabel productName = new JLabel("<html>" + product.getName() + "</html>", SwingConstants.LEFT);
        productName.setVerticalAlignment(SwingConstants.CENTER);
        productName.setFont(Style.FONT_BOLD_30);
        top.add(productName);

        JLabel productID = new JLabel("<html>Product ID: " + product.getId() + "</html>", SwingConstants.LEFT);
        productID.setVerticalAlignment(SwingConstants.CENTER);
        productID.setFont(Style.FONT_PLAIN_18);
        top.add(productID);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        bot.setBackground(Color.WHITE);
        JLabel price = new JLabel("$" + product.getPrice());
        price.setFont(Style.FONT_BOLD_30);
        price.setForeground(Style.CONFIRM_BUTTON_COLOR_GREEN);
        bot.add(Box.createHorizontalStrut(10));
        bot.add(price);
        bot.add(Box.createHorizontalStrut(50));
        // Hiển thị số lượng
        JLabel quantity = new JLabel("Quantity: ");
        quantity.setFont(Style.FONT_PLAIN_18);
        bot.add(quantity);

        // Thêm JComboBox cho số lượng
        JComboBox<Integer> quantityComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        quantityComboBox.setPreferredSize(new Dimension(60, 30));
        quantityComboBox.setFont(Style.FONT_BOLD_18);
        setComboBoxScrollBarColor(quantityComboBox, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE);
        quantityComboBox.addActionListener(e -> {

            Integer selectedNumber = (Integer) quantityComboBox.getSelectedItem();
//            System.out.println("Số đã chọn: " + selectedNumber);
            productOrderConfig.setQuatity(selectedNumber);
//            System.out.println(productOrderConfig);
            this.productOrders.add(productOrderConfig);


        });
        bot.add(quantityComboBox);
        bot.add(Box.createHorizontalStrut(50));

        CustomButton remove = ButtonConfig.createCustomButton("", Style.FONT_BOLD_15, Color.red, Color.white, Color.white, 15, SwingConstants.CENTER, new Dimension(80, 50));
        remove.setIcon(new ImageIcon("src/main/java/Icon/bin_Icon.png"));
        remove.addActionListener(e -> {
            this.productOrders.remove(productOrderConfig);
//            productOrderConfig.setQuatity(0);
            this.productOrders.remove(productOrderConfig);
            removePanelFromCartContainer(panel);

        });
        bot.add(remove);

        JPanel Main = new JPanel(new GridLayout(2, 1));
        Main.add(top);
        Main.add(bot);
        panel.add(Main, BorderLayout.CENTER);

        return panel;
    }

    private void showFullBills(Map<Integer, List<CustomerOrderDTO>> map, Customer customer) {
        for (Map.Entry<Integer, List<CustomerOrderDTO>> data : map.entrySet()) {
            addCustomerNotification(customer, BillConfig.generateBill((ArrayList<CustomerOrderDTO>) data.getValue()));
        }
    }

    private JPanel createEmptyPanel() {
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
        CustomButton goShopping = createCustomButton("Browse our products now", Style.FONT_PLAIN_18, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 5, new Dimension(250, 40));
        goShopping.addActionListener(e -> {
            productCatalogPanel.show("catalog");
        });
        textPn.add(goShopping, gbc);

        emptyPn.add(textPn);

        return emptyPn;
    }

    // method delete a panel
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

        notificationContainer.add(main);
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

        JPanel bottomPn = new JPanel(new GridLayout(1, 2));
        bottomPn.setBackground(Color.WHITE);
        bottomPn.setPreferredSize(new Dimension(100, 60));
        MatteBorder botBorder = new MatteBorder(1, 0, 0, 0, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        EmptyBorder margin = new EmptyBorder(5, 0, 0, 0);

        Border combinedBorder = new CompoundBorder(margin, botBorder);
        bottomPn.setBorder(combinedBorder);

        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeft.setBackground(Color.WHITE);
        CustomButton viewBill = createCustomButton("View Bill", Style.FONT_BOLD_15, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 8, new Dimension(150, 40));
        ButtonConfig.setButtonIcon("src/main/java/Icon/bill_Icon.png", viewBill, 15);
        bottomLeft.add(viewBill);




        CustomButton cancelOrder = createCustomButton("Cancel Order", Style.FONT_BOLD_15, Style.DELETE_BUTTON_COLOR_RED, Color.white, Style.LIGHT_BlUE, Style.DELETE_BUTTON_COLOR_RED, 1, 8, new Dimension(180, 40));
        CustomButton buyBackBt = createCustomButton("Mua lai", Style.FONT_BOLD_15, Style.DELETE_BUTTON_COLOR_RED, Color.white, Style.LIGHT_BlUE, Style.DELETE_BUTTON_COLOR_RED, 1, 8, new Dimension(180, 40));
        // check order date
        var date = customerOrderDTOs.get(0).customerOrderDTO().getOrderDate();

        if(DateConfig.cancelOrderLimit(date,3)){
            ButtonConfig.setButtonIcon("src/main/java/Icon/cancelOrder_Icon.png", cancelOrder, 15);
            bottomLeft.add(cancelOrder);
        }
        ButtonConfig.setButtonIcon("src/main/java/Icon/cancelOrder_Icon.png", buyBackBt, 15);
        bottomLeft.add(buyBackBt);


        cancelOrder.addActionListener(e->{
            OrderController controller= new OrderController();
            if ( JOptionPane.showConfirmDialog(null,"Cancel order") == 0 && controller.updateStatusOrder(OrderType.UN_ACTIVE,orderId)){
                ToastNotification.showToast("Cancel order",3000,30,-1,-1);
                cancelOrder.setEnabled(false);
            }
        });

        buyBackBt.addActionListener(e->{
            // mua lai thi phai reload cho hien len
            JOptionPane.showConfirmDialog(null,"chua code anh oi  chua có logic mua hàng");
            addOrderToContainer(createOrderPn(orderId,customerOrderDTOs));


        });






        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRight.setBackground(Color.WHITE);
        JLabel items = LabelConfig.createLabel(customerOrderDTOs.size() + " items:  ", Style.FONT_BOLD_25, Color.BLACK, SwingConstants.RIGHT);
        String priceFormated = formatCurrency.format(totalPrice);
        JLabel totalPriceLabel = LabelConfig.createLabel(priceFormated + "₫", Style.FONT_BOLD_25, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.LEFT);

        bottomRight.add(items);
        bottomRight.add(totalPriceLabel);


        bottomPn.add(bottomLeft);
        bottomPn.add(bottomRight);
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
        JLabel proTechnicalDetail = LabelConfig.createLabel("CPU " + customerOrderDTO.customerOrderDTO().getCpu() +" / " +
                                                            "RAM " + customerOrderDTO.customerOrderDTO().getRam() + " / " +
                                                            "Storage " + customerOrderDTO.customerOrderDTO().getMemory(),
                Style.FONT_PLAIN_13, Color.BLACK, SwingConstants.LEFT);
        proDetails.add(proTechnicalDetail, gbc);

        gbc.gridy++;
        JPanel bottomPn = new JPanel(new GridLayout(1, 2));

        JPanel pricePn = new JPanel(new FlowLayout(FlowLayout.LEFT));// panel xem giá của 1 sản phẩm
        pricePn.setBackground(Color.WHITE);
        String unitPrice = formatCurrency.format(customerOrderDTO.customerOrderDTO().getUnitPrice());
        JLabel proPrice = LabelConfig.createLabel( unitPrice+ "₫",Style.FONT_BOLD_18,Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.LEFT);
        pricePn.add(proPrice);

        JPanel quantityPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));// panel xem số lượng mua của 1 sản phẩm
        quantityPn.setBackground(Color.WHITE);
        JLabel proQuantity = LabelConfig.createLabel("x" + customerOrderDTO.customerOrderDTO().getQuantity(),Style.FONT_BOLD_18,Color.BLACK, SwingConstants.RIGHT);
        quantityPn.add(proQuantity);

        bottomPn.add(pricePn);
        bottomPn.add(quantityPn);
        proDetails.add(bottomPn, gbc);

        // Thêm panel chứa thông tin sản phẩm vào mainPanel
        mainPanel.add(proDetails, BorderLayout.CENTER);

        return mainPanel;
    }

    private void searchProductByName(String name){
        // remove old product
        ProductController productController = new ProductController();
        ArrayList<Product> products = productController.getEagerProducts();
        var pro = products.stream()
                .filter(p ->p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        if (pro.isEmpty()) addNewPanelToCatalogContainer( new NotFoundEntity());
        pro.forEach(p -> {
            JPanel create = createPanelForProductInCatalog(p);
            addNewPanelToCatalogContainer(create);
        });
    }

    private void displayProductHistoryOnPage(){
        ProductController productController = new ProductController();
        var k = customerController.getCustomerOrderDetail(CurrentUser.CURRENT_CUSTOMER.getId());
        Map<KeyOrderDTO, ArrayList<CustomerOrderDTO>> mapOrder = new BillConfig(bills).convertDataToBills();
        OrderHistoryConfig orderHistoryConfig = new OrderHistoryConfig(k);
        for (Map.Entry<Integer, List<CustomerOrderDetailDTO>> data : orderHistoryConfig.get().entrySet()) {
                addOrderToContainer(createOrderPn(data.getKey(), (ArrayList<CustomerOrderDetailDTO>) data.getValue()));
        }
    }

    private void displayProductOnPage(DisplayProductOnPageType type) {
        // aa chua remove item dc
        // remove old product


        ProductController productController = new ProductController();
        ArrayList<Product> products = productController.getEagerProducts();

        switch (type){
            case ALL -> {
                 products.forEach( p-> {
                    JPanel create = createPanelForProductInCatalog(p);
                    addNewPanelToCatalogContainer(create);
                });
            }
            case LUXURY ->{
                var pro = products.stream()
                        .sorted((p1,p2)->{
                           return p2.getPrice() -p1.getPrice();
                        })
                        .collect(Collectors.toList());
                if (pro.isEmpty()) addNewPanelToCatalogContainer( new NotFoundEntity());
                pro.forEach(p -> {
                    JPanel create = createPanelForProductInCatalog(p);
                    addNewPanelToCatalogContainer(create);
                });
            }
            case CHEAP->{
                var pro = products.stream()
                        .sorted((p1,p2)->{
                            return p1.getPrice() -p2.getPrice();
                        })
                        .collect(Collectors.toList());
                if (pro.isEmpty()) addNewPanelToCatalogContainer( new NotFoundEntity());
                pro.forEach(p -> {
                    JPanel create = createPanelForProductInCatalog(p);
                    addNewPanelToCatalogContainer(create);
                });
            }
            default -> {
                var pro = products.stream()
                        .filter(p ->p.getGenre().contains(type.getType()))
                        .collect(Collectors.toList());
                if (pro.isEmpty()) addNewPanelToCatalogContainer( new NotFoundEntity());
                pro.forEach(p -> {
                    JPanel create = createPanelForProductInCatalog(p);
                    addNewPanelToCatalogContainer(create);
                });

            }

        }

    }


}
