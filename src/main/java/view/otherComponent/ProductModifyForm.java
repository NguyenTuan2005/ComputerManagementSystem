package view.otherComponent;

import static view.ManagerMainPanel.currencyFormatter;

import config.ButtonConfig;
import config.ProductConfig;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;
import model.Product;
import model.Supplier;
import view.LoginFrame;

public class ProductModifyForm extends JFrame {

  private JComboBox<String> cmbSupplierId;
  private JTextField txtName;
  private JTextField txtQuantity;
  private JTextField txtPrice;
  private JTextField txtGenre;
  private JTextField txtBrand;
  private JTextField txtOS;
  private JTextField txtCPU;
  private JTextField txtMemory;
  private JTextField txtRAM;
  private JTextField txtMadeIn;
  private JComboBox<String> cmbStatus;

  private JTextField txtDisk;
  private JTextField txtMonitor;
  private JTextField txtWeight;
  private JTextField txtCard;

  private JButton btnSave;
  private JButton btnClear;
  private JButton btnExit;

  private int productId;
  private model.Product product;

  private String firstDataOfCompany;
  private String firstDataOfStatus;
  private int mouseX, mouseY;
  private ScrollPane scrollPane;
  private Runnable updateCallback;
  private Map<String, Integer> suppliersMap;
  private static List<Supplier> suppliers = LoginFrame.COMPUTER_SHOP.getAllSupplier();

  private final Color PRIMARY_COLOR = new Color(41, 128, 185);
  private final Color SECONDARY_COLOR = new Color(52, 152, 219);
  private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
  private final Color PANEL_BACKGROUND = Color.WHITE;
  private final Color BUTTON_COLOR = new Color(41, 128, 185);
  private final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);

  public ProductModifyForm(Product product) {
    this.product = product;
    initializeFrame();
  }

  public ProductModifyForm(Product product, Runnable updateCallback) {
    this.product = product;
    this.productId = product.getId();
    this.updateCallback = updateCallback;
    initializeFrame();
  }

  private void initializeFrame() {
    setUndecorated(true);
    setTitle("Add product");
    setSize(900, 750);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);

    getContentPane().setBackground(BACKGROUND_COLOR);
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

    this.addMouseListener(
        new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
          }
        });
    this.addMouseMotionListener(
        new MouseAdapter() {
          public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            setLocation(x - mouseX, y - mouseY);
          }
        });

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {

            JOptionPane.showMessageDialog(
                null, "Use the Save or Reload button to manage the window.");
          }
        });

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(BACKGROUND_COLOR);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel titlePanel = createTitlePanel();

    JPanel contentPanel = createContentPanel();

    JPanel buttonPanel = createButtonPanel();
    scrollPane = new ScrollPane();

    mainPanel.add(titlePanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    scrollPane.add(contentPanel);
    mainPanel.add(scrollPane);
    scrollPane.setPreferredSize(new Dimension(700, 90000));
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(buttonPanel);

    add(mainPanel);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(this);
    } catch (Exception e) {
      System.out.println("bug ne e???");
    }
  }

  private JPanel createTitlePanel() {
    JPanel panel = new JPanel();
    panel.setBackground(BACKGROUND_COLOR);

    JLabel titleLabel = new JLabel("PRODUCT INFORMATION");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(PRIMARY_COLOR);
    panel.add(titleLabel);

    return panel;
  }

  private JPanel createContentPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(PANEL_BACKGROUND);
    panel.setBorder(
        BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1), BorderFactory.createEmptyBorder(20, 20, 20, 20)));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    initializeStyledComponents();

    addStyledComponents(panel, gbc);

    return panel;
  }

  private void initializeStyledComponents() {

    txtName = createStyledTextField();
    txtQuantity = createStyledTextField();
    txtPrice = createStyledTextField();
    txtGenre = createStyledTextField();
    txtBrand = createStyledTextField();
    txtOS = createStyledTextField();
    txtCPU = createStyledTextField();
    txtMemory = createStyledTextField();
    txtRAM = createStyledTextField();
    txtMadeIn = createStyledTextField();
    txtDisk = createStyledTextField();
    txtMonitor = createStyledTextField();
    txtWeight = createStyledTextField();
    txtCard = createStyledTextField();

    suppliersMap = new HashMap<>();
    setMapCompany((ArrayList<Supplier>) suppliers, suppliersMap);
    String[] companyNames = new String[suppliers.size()];
    setCompany((ArrayList<Supplier>) suppliers, companyNames);
    firstDataOfCompany = ProductConfig.getKeyByValue(suppliersMap, product.getSupplier().getId());
    firstDataOfStatus = product.getStatus();

    cmbSupplierId = new JComboBox<>(companyNames);
    cmbSupplierId.setSelectedItem(firstDataOfCompany);
    cmbSupplierId.setFont(new Font("Arial", Font.PLAIN, 14));
    cmbSupplierId.addActionListener(
        e -> {
          suppliersMap.get(cmbSupplierId.getSelectedItem());
        });

    String[] statusOptions = {"In Stock", "Available"};
    cmbStatus = new JComboBox<>(statusOptions);
    cmbStatus.setFont(new Font("Arial", Font.PLAIN, 14));
    cmbStatus.setSelectedItem(firstDataOfStatus);

    btnSave = createStyledButton("UPDATE");
    btnSave.setForeground(Color.BLACK);
    btnClear = createStyledButton("UNDO");
    btnClear.setForeground(Color.BLACK);
    btnExit = createStyledButton("CANCEL");
    btnExit.setForeground(Color.BLACK);

    ButtonConfig.addButtonHoverEffect(btnSave, BUTTON_HOVER_COLOR, BUTTON_COLOR);
    ButtonConfig.addButtonHoverEffect(btnClear, BUTTON_HOVER_COLOR, BUTTON_COLOR);
    ButtonConfig.addButtonHoverEffect(btnExit, BUTTON_HOVER_COLOR, BUTTON_COLOR);

    txtName.setText(product.getName());
    txtQuantity.setText("" + product.getQuantity());
    txtPrice.setText("" + currencyFormatter.format(product.getPrice()).replaceAll(",", ""));
    txtGenre.setText(product.getType());
    txtBrand.setText(product.getBrand());
    txtOS.setText(product.getOperatingSystem());
    txtCPU.setText(product.getCpu());
    txtMemory.setText(product.getMemory());
    txtRAM.setText(product.getRam());
    txtCard.setText(product.getCard());
    txtDisk.setText(product.getDisk());
    txtMonitor.setText(product.getMonitor());
    txtWeight.setText(String.valueOf(product.getWeight()));
    txtMadeIn.setText(product.getMadeIn());
  }

  private JTextField createStyledTextField() {
    JTextField textField = new JTextField(20);
    textField.setFont(new Font("Arial", Font.PLAIN, 14));
    textField.setBorder(
        BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    return textField;
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setForeground(Color.WHITE);
    button.setBackground(BUTTON_COLOR);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
  }

  private static void setCompany(ArrayList<Supplier> suppliers, String[] companyNames) {
    for (int i = 0; i < suppliers.size(); i++) {
      companyNames[i] = suppliers.get(i).getCompanyName();
    }
  }

  private static void setMapCompany(ArrayList<Supplier> suppliers, Map<String, Integer> map) {
    for (int i = 0; i < suppliers.size(); i++) {
      map.put(suppliers.get(i).getCompanyName(), suppliers.get(i).getId());
    }
  }

  private void addStyledComponents(JPanel panel, GridBagConstraints gbc) {
    Object[][] components = {
      {"Suppler name:", cmbSupplierId},
      {"Product name:", txtName},
      {"Quantity:", txtQuantity},
      {"Unit price:", txtPrice},
      {"Genre:", txtGenre},
      {"Brand:", txtBrand},
      {"Operating system :", txtOS},
      {"CPU:", txtCPU},
      {"Memory:", txtMemory},
      {"RAM:", txtRAM},
      {"Made in:", txtMadeIn},
      {"Disk :", txtDisk},
      {"Minotor :", txtMonitor},
      {"Weight :", txtWeight},
      {"Card :", txtCard},
      {"Status:", cmbStatus}
    };

    int gridy = 0;
    for (Object[] comp : components) {
      gbc.gridx = 0;
      gbc.gridy = gridy;
      gbc.weightx = 0.3;
      JLabel label = new JLabel(comp[0].toString());
      label.setFont(new Font("Arial", Font.BOLD, 14));
      label.setForeground(PRIMARY_COLOR);
      panel.add(label, gbc);

      gbc.gridx = 1;
      gbc.weightx = 0.7;
      panel.add((Component) comp[1], gbc);

      gridy++;
    }
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    panel.setPreferredSize(new Dimension(800, 800));
    panel.setBackground(BACKGROUND_COLOR);

    btnSave.addActionListener(e -> saveProduct());
    btnClear.addActionListener(e -> clearForm());
    btnExit.addActionListener(e -> this.setVisible(false));

    panel.add(btnSave);
    panel.add(btnClear);
    panel.add(btnExit);

    return panel;
  }

  private void saveProduct() {
    try {
      Product product =
          Product.builder()
              .id(productId)
              .supplier(
                  LoginFrame.COMPUTER_SHOP.findSupplier((String) cmbSupplierId.getSelectedItem()))
              .name(txtName.getText())
              .quantity(Integer.parseInt(txtQuantity.getText()))
              .price(Integer.parseInt(txtPrice.getText()))
              .type(txtGenre.getText())
              .brand(txtBrand.getText())
              .monitor(txtMonitor.getText())
              .operatingSystem(txtOS.getText())
              .cpu(txtCPU.getText())
              .memory(txtMemory.getText())
              .ram(txtRAM.getText())
              .madeIn(txtMadeIn.getText())
              .weight(Float.parseFloat(txtWeight.getText()))
              .card(txtCard.getText())
              .status(cmbStatus.getSelectedItem().toString())
              .isActive(true)
              .images(new ArrayList<>())
              .build();
      LoginFrame.COMPUTER_SHOP.updateProduct(product);
      dispose();
      if (updateCallback != null) {
        updateCallback.run();
      }

    } catch (NumberFormatException ex) {
      showErrorDialog("Please enter the correct format!");
    }
  }

  private void clearForm() {
    txtName.setText(product.getName());
    txtQuantity.setText("" + product.getQuantity());
    txtPrice.setText("" + currencyFormatter.format(product.getPrice()));
    txtGenre.setText(product.getType());
    txtBrand.setText(product.getBrand());
    txtOS.setText(product.getOperatingSystem());
    txtCPU.setText(product.getCpu());
    txtMemory.setText(product.getMemory());
    txtRAM.setText(product.getRam());
    txtMadeIn.setText(product.getMadeIn());
    cmbSupplierId.setSelectedItem(firstDataOfCompany);
    cmbStatus.setSelectedItem(firstDataOfStatus);
  }

  private void showSuccessDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Successfully", JOptionPane.INFORMATION_MESSAGE);
    this.setVisible(false);
  }

  private void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
