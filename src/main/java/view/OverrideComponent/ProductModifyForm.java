package view.OverrideComponent;

import Config.ButtonConfig;
import Config.ProductConfig;
import Model.Product;
import Model.Supplier;
import dao.ProductDAO;
import dao.SupplierDAO;
import org.springframework.util.ObjectUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductModifyForm extends JFrame {

    private JComboBox<String> cmbSupplierId;
    private JTextField txtName;
    private JTextField txtQuality;
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
    private SupplierDAO supplierDAO;
    private ProductDAO productDAO;
    private int supplierId ;
    private Product product;
    private Map<String,Integer> suppliersMap;
    private String firstDataOfCompany;
    private String firstDataOfStatus;
    private int mouseX, mouseY;

    private ScrollPane scrollPane;


    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color PANEL_BACKGROUND = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(41, 128, 185);
    private final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);

    public ProductModifyForm( Product  product) {
        this.product = product;
        setUndecorated(true);
        setTitle("Add product");
        setSize(900, 750);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().setBackground(BACKGROUND_COLOR);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());
        // Thêm sự kiện mouse listener để di chuyển JFrame
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Custom behavior when trying to close the window
                JOptionPane.showMessageDialog(null, "Use the Save or Reload button to manage the window.");
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
//        mainPanel
        scrollPane.add(contentPanel);
        mainPanel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(700,90000));
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
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        initializeStyledComponents();

        addStyledComponents(panel, gbc);

        return panel;
    }

    private void initializeStyledComponents() {

        txtName = createStyledTextField();
        txtQuality = createStyledTextField();
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
        supplierDAO = new SupplierDAO();
        productDAO = new ProductDAO();



        // Khởi tạo JComboBox cho Supplier ID với các lựa chọn
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        suppliersMap= new HashMap<>();
        setMapCompany(suppliers, suppliersMap);
        String [] companyNames = new String[suppliers.size()];
        setCompany(suppliers, companyNames);
        firstDataOfCompany = ProductConfig.getKeyByValue(suppliersMap, product.getSuppliersId());
        firstDataOfStatus = product.getStatus();
//        System.out.println(firstDataOfCompany+"lkasjdhlaSKJFDHLAKSFDS");

        cmbSupplierId = new JComboBox<>(companyNames);
        cmbSupplierId.setSelectedItem(firstDataOfCompany);
        cmbSupplierId.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbSupplierId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supplierId  = suppliersMap.get(cmbSupplierId.getSelectedItem());
                System.out.println(supplierId);
                product.setSuppliersId(supplierId);
            }
        });

        // Khởi tạo ComboBox trạng thái
        String[] statusOptions = {"In Stock", "Out Stock"};
        cmbStatus = new JComboBox<>(statusOptions);
        cmbStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbStatus.setSelectedItem(firstDataOfStatus);
        System.out.println(firstDataOfStatus+"ljhdslfjkhdslkjghfdslkjghldfskjh");

        // Khởi tạo buttons
        btnSave = createStyledButton("UPDATE");
        btnSave.setForeground(Color.BLACK);
        btnClear = createStyledButton("UNDO");
        btnClear.setForeground(Color.BLACK);
        btnExit = createStyledButton("CANCEL");
        btnExit.setForeground(Color.BLACK);

        // Thêm hiệu ứng hover cho buttons
        ButtonConfig.addButtonHoverEffect(btnSave ,BUTTON_HOVER_COLOR,BUTTON_COLOR);
        ButtonConfig.addButtonHoverEffect(btnClear ,BUTTON_HOVER_COLOR,BUTTON_COLOR);
        ButtonConfig.addButtonHoverEffect(btnExit ,BUTTON_HOVER_COLOR,BUTTON_COLOR);

        // set data
        txtName.setText(product.getName());
        txtQuality.setText(""+product.getQuality());
        txtPrice.setText(""+product.getPrice());
        txtGenre.setText(product.getGenre());
        txtBrand.setText(product.getBrand());
        txtOS.setText(product.getOperatingSystem());
        txtCPU.setText(product.getCpu());
        txtMemory.setText(product.getMemory());
        txtRAM.setText(product.getRam());
        txtCard.setText(product.getCard());
        txtDisk.setText(product.getDisk());
        txtMonitor.setText(product.getMonitor());
        txtWeight.setText(product.getWeight());
        txtMadeIn.setText(product.getMadeIn());
    }


    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
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


    private static void setCompany(ArrayList<Supplier> suppliers, String [] companyNames  ){
        for (int i = 0; i < suppliers.size(); i++) {
            companyNames[i] = suppliers.get( i).getCompanyName();
        }
    }
    private static  void setMapCompany(ArrayList<Supplier> suppliers ,Map<String ,Integer> map){
        for (int i = 0; i < suppliers.size(); i++) {
            map.put(suppliers.get(i).getCompanyName(), suppliers.get(i).getId());
        }
    }



    private void addStyledComponents(JPanel panel, GridBagConstraints gbc) {
        Object[][] components = {
//                {"Mã sản phẩm:", txtId},
                {"Suppler name:", cmbSupplierId}, // Sử dụng JComboBox
                {"Product name:", txtName},
                {"Quality:", txtQuality},
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
        panel.setPreferredSize(new Dimension(800,800));
        panel.setBackground(BACKGROUND_COLOR);

        btnSave.addActionListener(e -> saveProduct());
        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e ->  this.setVisible(false));

        panel.add(btnSave);
        panel.add(btnClear);
        panel.add(btnExit);

        return panel;
    }

    private void saveProduct() {
        try {
            product.setName(txtName.getText());
            product.setQuality(Integer.parseInt(txtQuality.getText()));
            product.setPrice(Integer.parseInt(txtPrice.getText()));
            product.setGenre(txtGenre.getText());
            product.setBrand(txtBrand.getText());
            product.setOperatingSystem(txtOS.getText());
            product.setCpu(txtCPU.getText());
            product.setMemory(txtMemory.getText());
            product.setRam(txtRAM.getText());
            product.setMadeIn(txtMadeIn.getText());
            product.setStatus(cmbStatus.getSelectedItem().toString());
            product.setDisk(txtDisk.getText());
            product.setMonitor(txtMonitor.getText());
            product.setCard(txtCard.getText());
            product.setWeight(txtWeight.getText());
            System.out.println(product);
            productDAO.update(product);
            showSuccessDialog("saved successfully!");
            clearForm();

        } catch (NumberFormatException ex) {
            showErrorDialog("Please enter the correct format!");
        }
    }

    private void clearForm() {
        txtName.setText(product.getName());
        txtQuality.setText(""+product.getQuality());
        txtPrice.setText(""+product.getPrice());
        txtGenre.setText(product.getGenre());
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


    public static void main(String[] args) {
//        Product fakeProduct = new Product(
//                42,
//                4, // suppliersId
//                "Asus Gaming Laptop", // name
//                10, // quality
//                1500, // price
//                "Laptop", // genre
//                "ASUS", // brand
//                "Windows 10", // operatingSystem
//                "Intel Core i7", // cpu
//                "512GB SSD", // memory
//                "16GB", // ram
//                "USA", // madeIn
//                "Out Stock", // status
//                1 // deleteRow
//        );
//
//
//            SwingUtilities.invokeLater(() -> {
//                new ProductModifyForm(fakeProduct).setVisible(true);
//            });

    }
}
