package view.OtherComponent;

import Config.ButtonConfig;
import Config.TextFieldConfig;
import Model.Product;
import Model.Supplier;
import dao.ProductDAO;
import dao.SupplierDAO;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import view.Style;

public class ProductInputForm extends JFrame {

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
  private JTextField txtDisk;
  private JTextField txtMonitor;
  private JTextField txtWeight;
  private JTextField txtCard;

  private JComboBox<String> cmbStatus;
  private JButton btnSave;
  private JButton btnClear;
  private JButton btnExit;
  private SupplierDAO supplierDAO;
  private ProductDAO productDAO;
  private int supplierId = 1;
  private int mouseX, mouseY;

  private ScrollPane scrollPane;

  private Map<String, Integer> suppliersMap;

  private final Color SECONDARY_COLOR = new Color(52, 152, 219);
  private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
  private final Color PANEL_BACKGROUND = Color.WHITE;
  private final Color BUTTON_COLOR = new Color(41, 128, 185);
  private final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);

  public ProductInputForm() {
    setUndecorated(true);
    setTitle("Add product");
    setSize(900, 750);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);

    getContentPane().setBackground(BACKGROUND_COLOR);
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            JOptionPane.showMessageDialog(
                null, "Use the Save or Reload button to manage the window.");
          }
        });

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
    //        setUndecorated(true);

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
    scrollPane.setPreferredSize(new Dimension(700, 90000));
    mainPanel.add(scrollPane);

    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(buttonPanel);

    add(mainPanel);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private JPanel createTitlePanel() {
    JPanel panel = new JPanel();
    panel.setBackground(BACKGROUND_COLOR);

    JLabel titleLabel = new JLabel("PRODUCT INFORMATION");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(Style.MODEL_PRIMARY_COLOR);
    panel.add(titleLabel);

    return panel;
  }

  private JPanel createContentPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(PANEL_BACKGROUND);
    panel.setBorder(
        BorderFactory.createCompoundBorder(
            new LineBorder(Style.MODEL_PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    initializeStyledComponents();

    addStyledComponents(panel, gbc);

    return panel;
  }

  private void initializeStyledComponents() {

    txtName = TextFieldConfig.createStyledTextField();
    txtQuantity = TextFieldConfig.createStyledTextField();
    txtPrice = TextFieldConfig.createStyledTextField();
    txtGenre = TextFieldConfig.createStyledTextField();
    txtBrand = TextFieldConfig.createStyledTextField();
    txtOS = TextFieldConfig.createStyledTextField();
    txtCPU = TextFieldConfig.createStyledTextField();
    txtMemory = TextFieldConfig.createStyledTextField();
    txtRAM = TextFieldConfig.createStyledTextField();
    txtMadeIn = TextFieldConfig.createStyledTextField();
    txtDisk = TextFieldConfig.createStyledTextField();
    txtMonitor = TextFieldConfig.createStyledTextField();
    txtWeight = TextFieldConfig.createStyledTextField();
    txtCard = TextFieldConfig.createStyledTextField();
    supplierDAO = new SupplierDAO();
    productDAO = new ProductDAO();

    // Khởi tạo JComboBox cho Supplier ID với các lựa chọn
    ArrayList<Supplier> suppliers = supplierDAO.getAll();
    suppliersMap = new HashMap<>();
    setMapCompany(suppliers, suppliersMap);
    String[] companyNames = new String[suppliers.size()];
    setCompany(suppliers, companyNames);

    cmbSupplierId = new JComboBox<>(companyNames);
    cmbSupplierId.setSelectedItem(companyNames[2]);
    cmbSupplierId.setFont(new Font("Arial", Font.PLAIN, 14));
    cmbSupplierId.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            supplierId = suppliersMap.get(cmbSupplierId.getSelectedItem());
            System.out.println(supplierId);
          }
        });

    // Khởi tạo ComboBox trạng thái
    String[] statusOptions = {"In Stock", "Out Stock"};
    cmbStatus = new JComboBox<>(statusOptions);
    cmbStatus.setFont(new Font("Arial", Font.PLAIN, 14));

    // Khởi tạo buttons
    btnSave = ButtonConfig.createStyledButton("SAVE");
    btnSave.setForeground(Color.BLACK);
    btnClear = ButtonConfig.createStyledButton("CLEAN ALL");
    btnClear.setForeground(Color.BLACK);
    btnExit = ButtonConfig.createStyledButton("CANCEL");
    btnExit.setForeground(Color.BLACK);

    // Thêm hiệu ứng hover cho buttons
    ButtonConfig.addButtonHoverEffect(btnSave, BUTTON_HOVER_COLOR, BUTTON_COLOR);
    ButtonConfig.addButtonHoverEffect(btnClear, BUTTON_HOVER_COLOR, BUTTON_COLOR);
    ButtonConfig.addButtonHoverEffect(btnExit, BUTTON_HOVER_COLOR, BUTTON_COLOR);
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
      label.setForeground(Style.MODEL_PRIMARY_COLOR);
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
      Product product = new Product();

      //            product.setId(Integer.parseInt(txtId.getText()));
      product.setSuppliersId(supplierId);
      product.setName(txtName.getText());
      product.setQuantity(Integer.parseInt(txtQuantity.getText()));
      product.setPrice(Integer.parseInt(txtPrice.getText()));
      product.setGenre(txtGenre.getText());
      product.setBrand(txtBrand.getText());
      product.setOperatingSystem(txtOS.getText());
      product.setCpu(txtCPU.getText());
      product.setMemory(txtMemory.getText());
      product.setRam(txtRAM.getText());
      product.setMadeIn(txtMadeIn.getText());
      product.setDisk(txtDisk.getText());
      product.setMonitor(txtMonitor.getText());
      product.setWeight(txtWeight.getText());
      product.setCard(txtCard.getText());
      product.setStatus(cmbStatus.getSelectedItem().toString());
      productDAO.save(product);

      showSuccessDialog("saved successfully!");
      clearForm();

    } catch (NumberFormatException ex) {
      showErrorDialog("Please enter the correct format!");
    }
  }

  private void clearForm() {
    txtName.setText("");
    txtQuantity.setText("");
    txtPrice.setText("");
    txtGenre.setText("");
    txtBrand.setText("");
    txtOS.setText("");
    txtCPU.setText("");
    txtMemory.setText("");
    txtRAM.setText("");
    txtMadeIn.setText("");
    txtCard.setText("");
    txtWeight.setText("");
    txtDisk.setText("");
    txtMonitor.setText("");
  }

  private void showSuccessDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Successfully", JOptionPane.INFORMATION_MESSAGE);
  }

  private void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
