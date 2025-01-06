package view;

import static org.apache.commons.collections4.CollectionUtils.collect;
import static view.CustomerMainPanel.createImageForProduct;

import com.toedter.calendar.JCalendar;
import config.*;

import entity.Supplier;
import enums.OrderType;
import enums.TableStatus;
import java.awt.*;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import lombok.SneakyThrows;
import entity.*;
import verifier.*;
import view.otherComponent.*;
import view.overrideComponent.CircularImage;
import view.overrideComponent.CustomButton;
import view.overrideComponent.ToastNotification;

public class ManagerMainPanel extends JPanel {
  private CardLayout cardLayout = new CardLayout();
  private WelcomePanel welcomePanel;
  private ProductPanel productPanel = new ProductPanel();
  private SupplierPanel supplierPanel = new SupplierPanel();
  private CustomerPanel customerPanel = new CustomerPanel();
  private OrderPanel orderPanel = new OrderPanel();
  private InventoryPanel inventoryPanel = new InventoryPanel();
  private AccManagementPanel accManagePanel = new AccManagementPanel();
  private NotificationPanel notificationPanel = new NotificationPanel();
  private ChangeInformationPanel changeInformationPanel = new ChangeInformationPanel();

  private JPanel notificationContainer;

  static final String WELCOME_CONSTRAINT = "welcome";
  static final String PRODUCT_CONSTRAINT = "product";
  static final String SUPPLIER_CONSTRAINT = "supplier";
  static final String CUSTOMER_CONSTRAINT = "customer";
  static final String ORDER_CONSTRAINT = "order";
  static final String INVENTORY_CONSTRAINT = "inventory";
  static final String ACC_MANAGEMENT_CONSTRAINT = "accManagement";
  static final String NOTIFICATION_CONSTRAINT = "notification";
  static final String CHANGE_INFORMATION_CONSTRAINT = "changeInformation";

  private static final String[] columnNamesPRODUCT = {
    "Serial Number",
    "ProductID",
    "Product Name",
    "Quantity",
    "Unit Price",
    "Type of Device",
    "Brand",
    "Operating System",
    "CPU",
    "Storage",
    "RAM",
    "Made In",
    "Status",
    "Disk",
    "Weight",
    "Monitor",
    "Card"
  };
  private static final String[] columnNamesSUPPLIER = {
    "Serial Number",
    "Supplier ID:",
    "Supplier Name:",
    "Email:",
    "Phone number:",
    "Address:",
    "Contract Start Date:"
  };
  static final String[] columnNamesCUSTOMER = {
    "Customer ID:", "Customer Name:", "Phone Number:", "Email:", "Address:", "Date of Birth:"
  };

  public static DecimalFormat formatCurrency = new DecimalFormat("#,###");

  public ManagerMainPanel() {

    welcomePanel = new WelcomePanel();
    setLayout(cardLayout);
    add(welcomePanel, WELCOME_CONSTRAINT);
    add(productPanel, PRODUCT_CONSTRAINT);
    add(supplierPanel, SUPPLIER_CONSTRAINT);
    add(customerPanel, CUSTOMER_CONSTRAINT);
    add(orderPanel, ORDER_CONSTRAINT);
    add(inventoryPanel, INVENTORY_CONSTRAINT);
    add(accManagePanel, ACC_MANAGEMENT_CONSTRAINT);
    add(notificationPanel, NOTIFICATION_CONSTRAINT);
    add(changeInformationPanel, CHANGE_INFORMATION_CONSTRAINT);

    cardLayout.show(this, WELCOME_CONSTRAINT);
  }

  public void showPanel(String panelName) {
    cardLayout.show(this, panelName);
  }

  private class WelcomePanel extends JPanel {
    JLabel welcomeLabel;

    public WelcomePanel() {
      setLayout(new BorderLayout());
      setBackground(Color.WHITE);
      ImageIcon welcomeImg = new ImageIcon("src/main/java/img/welcomeImage.png");
      welcomeLabel = new JLabel();
      welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
      welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
      welcomeLabel.setIcon(welcomeImg);

      add(welcomeLabel, BorderLayout.CENTER);
    }
  }

  private class ProductPanel extends JPanel {
    private ToolPanel toolPanel = new ToolPanel();
    private JButton addBt,
        modifyBt,
        deleteBt,
        sortBt,
        exportExcelBt,
        importExcelBt,
        searchBt,
        reloadBt;
    private JTextField findText;

    private TablePanel tablePanel = new TablePanel();
    private JTable tableProduct;
    private DefaultTableModel modelProductTable;
    private JScrollPane scrollPaneProductTable;
    private JTabbedPane tabbedPaneProductTable;
    private JPanel sortPanel;
    private JLabel sortLabel;
    private JComboBox<String> sortComboBox;

    JPanel searchPanel, applicationPanel, mainPanel;

    private static List<entity.Product> productsAll = reloadData();

    private static List<entity.Product> reloadData() {
      return LoginFrame.COMPUTER_SHOP.getAllProduct();
    }

    public static List<entity.Product> reloadProducts() {
      return reloadData();
    }
    // ok
    public static void upDataProducts(List<entity.Product> products , DefaultTableModel modelProductTable) {
      String[][] rowData = entity.Product.getDateOnTable(products);
      TablePanel.removeDataTable(modelProductTable);
      for (int i = 0; i < rowData.length; i++) {
        modelProductTable.addRow(rowData[i]);
      }
    }

    public static void deletedProduct(int id){
     ToastNotification.showToast("delete product",3000,50,-1,-1);
    }

    public static boolean changeStatus(int id, String status) {
      ToastNotification.showToast("update staus",3000,50,-1,-1);
      return true;
    }

    public ProductPanel() {
      setLayout(new BorderLayout());
      toolPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
      add(toolPanel, BorderLayout.NORTH);
      add(tablePanel, BorderLayout.CENTER);
    }

    private class ToolPanel extends JPanel {

      public ToolPanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);
        addBt = new JButton("Add");
        ButtonConfig.addButtonHoverEffect(addBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            addBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/database-add-icon.png", addBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.addKey, addBt);
        addBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                new ProductInputForm();
              }
            });

        modifyBt = new JButton("Modify");
        ButtonConfig.addButtonHoverEffect(
            modifyBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            modifyBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/modify.png", modifyBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.modifyKey, modifyBt);
        modifyBt.setHorizontalTextPosition(SwingConstants.CENTER);
        modifyBt.addActionListener(
            e -> {

              int selectedRow = tableProduct.getSelectedRow();
              if (selectedRow != -1) {
                SwingUtilities.invokeLater(
                    () -> {
//                      new ProductModifyForm(productsAll.get(selectedRow)).setVisible(true);
                    });
              }else{
                ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
              }
            });

        deleteBt = new JButton("Delete");
        ButtonConfig.addButtonHoverEffect(
            deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            deleteBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon(
            "src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.deleteKey, deleteBt);
        deleteBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                int selectedRow = tableProduct.getSelectedRow();
                int columnIndex = 1;
                if (selectedRow != -1) {
                  Object value = tableProduct.getValueAt(selectedRow, columnIndex);

                  int productId = Integer.parseInt(value.toString());
//                  productController.setDeleteRow(productId, false);
                  System.out.println("viet hamf timf kiem");
                  modelProductTable.removeRow(selectedRow);
                }
              }
            });

        sortBt = new JButton("Sort");
        ButtonConfig.addButtonHoverEffect(sortBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            sortBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/sort.256x204.png", sortBt, 35);

        exportExcelBt = new JButton("Export");
        ButtonConfig.addButtonHoverEffect(
            exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            exportExcelBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon(
            "src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.exportExcelKey, exportExcelBt);
        exportExcelBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                String fileName =
                    JOptionPane.showInputDialog(
                        null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                if (fileName != null && !fileName.trim().isEmpty()) {

                  if (!fileName.toLowerCase().endsWith(".xlsx")) {
                    fileName += ".xlsx";
                  }

                  productsAll = reloadData();
                  ExcelConfig.exportToExcel(productsAll, fileName, columnNamesPRODUCT);
                  if (productsAll.isEmpty())
                    JOptionPane.showMessageDialog(
                        null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                  JOptionPane.showMessageDialog(
                      null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);
                }
                JOptionPane.showMessageDialog(
                    null, "Are you sure ", "Exit", JOptionPane.ERROR_MESSAGE);
              }
            });

        importExcelBt = new JButton("Import");
        ButtonConfig.addButtonHoverEffect(
            importExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            importExcelBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon(
            "src/main/java/Icon/icons8-export-excel-50.png", importExcelBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.importExcelKey, importExcelBt);


        findText =
            TextFieldConfig.createTextField(
                "Search by Name",
                new Font("Arial", Font.PLAIN, 24),
                Color.GRAY,
                new Dimension(280, 50));
        findText.addActionListener(e -> searchBt.doClick());

        searchBt = new JButton();
        ButtonConfig.addButtonHoverEffect(
            searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            searchBt,
            Style.FONT_PLAIN_13,
            Color.BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(40, 45));
        ButtonConfig.setButtonIcon("src/main/java/Icon/106236_search_icon.png", searchBt, 10);

        reloadBt = new JButton("Reload");
        ButtonConfig.addButtonHoverEffect(
            reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            reloadBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/reload_icon.png", reloadBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.reloadKey, reloadBt);

        searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(findText);
        searchPanel.add(searchBt);
        searchPanel.setBackground(Style.WORD_COLOR_WHITE);

        applicationPanel = new JPanel(new FlowLayout());
        applicationPanel.add(addBt);
        applicationPanel.add(deleteBt);
        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(modifyBt);
        String[] sortOptions = {"NAME", "MEMORY", "PRICE", "RAM"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setPreferredSize(new Dimension(80, 50));
        sortComboBox.setBackground(Style.WORD_COLOR_WHITE);
        sortComboBox.setForeground(Style.WORD_COLOR_BLACK);
        sortComboBox.setFont(Style.FONT_PLAIN_15);
        sortComboBox.setRenderer(
            new DefaultListCellRenderer() {
              @Override
              public Component getListCellRendererComponent(
                  JList<?> list,
                  Object value,
                  int index,
                  boolean isSelected,
                  boolean cellHasFocus) {
                Component c =
                    super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                  c.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                  c.setForeground(Color.WHITE);
                } else {
                  c.setBackground(Color.WHITE);
                  c.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                }
                return c;
              }
            });
        sortComboBox.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                String item = (String) sortComboBox.getSelectedItem();
                switch (item) {
                  case ("PRICE"):
                    {
                      productsAll = productsAll.stream().sorted((p1,p2)-> {return (int) (p2.getPrice() - p1.getPrice());}).collect(Collectors.toList());
                      break;
                    }

                  case ("MEMORY"):
                    {
                      productsAll = productsAll.stream()
                              .sorted((p1,p2)->  p2.getMemory().compareTo(p1.getMemory()))
                              .collect(Collectors.toList());
                      break;
                    }
                  case ("NAME"):
                    {
                      productsAll = productsAll.stream()
                              .sorted((p1,p2)->  p2.getName().compareTo(p1.getName()))
                              .collect(Collectors.toList());
                      break;
                    }
                  case ("RAM"):
                    {
                      productsAll = productsAll.stream()
                              .sorted((p1,p2)->  p2.getRam().compareTo(p1.getRam()))
                              .collect(Collectors.toList());
                      break;
                    }
                }

                upDataProducts(productsAll, modelProductTable);
              }
            });

        sortPanel = new JPanel(new BorderLayout());
        sortLabel = new JLabel("Sort", SwingConstants.CENTER);
        sortLabel.setFont(Style.FONT_PLAIN_13);
        sortPanel.add(sortComboBox, BorderLayout.CENTER);
        sortPanel.add(sortLabel, BorderLayout.SOUTH);
        sortPanel.setBackground(Style.WORD_COLOR_WHITE);

        applicationPanel.add(sortPanel);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(exportExcelBt);

        applicationPanel.add(importExcelBt);
        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(reloadBt);
        applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Style.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(applicationPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(searchPanel, gbc);
        mainPanel.setBackground(Style.WORD_COLOR_WHITE);
        add(mainPanel);

        searchBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                if (findText.getText().trim().isEmpty()) return;
                System.out.println(findText.getText());
                System.out.println(productsAll);
                productsAll = LoginFrame.COMPUTER_SHOP.findProductByName(findText.getText().trim());

//                if (productsAll.isEmpty()) {
//                  JOptionPane.showMessageDialog(tablePanel, "Product not found in the List!");
//                  return;
//                }
                upDataProducts(productsAll, modelProductTable);
              }
            });

        reloadBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                productsAll = reloadData();
                upDataProducts(productsAll, modelProductTable);
                findText.setText("");
              }
            });
      }
    }

    private class TablePanel extends JPanel {
      public TablePanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);


        tableProduct = createTable(modelProductTable, columnNamesPRODUCT);
        tableProduct.setRowHeight(30);

        resizeColumnWidth(tableProduct, 150);

        modelProductTable = (DefaultTableModel) tableProduct.getModel();

        List<entity.Product> productsDemo = LoginFrame.COMPUTER_SHOP.getAllProduct();

        upDataProducts(productsDemo, modelProductTable);

        scrollPaneProductTable = new JScrollPane(tableProduct);
        tabbedPaneProductTable =
            createTabbedPane(scrollPaneProductTable, "Product for Sales", Style.FONT_BOLD_16);

        add(tabbedPaneProductTable, BorderLayout.CENTER);
      }

      public static void removeDataTable(DefaultTableModel modelProductTable) {
        modelProductTable.setRowCount(0);
      }
    }
  }

  private class SupplierPanel extends JPanel {
    private JButton addBt, modifyBt, deleteBt, exportExcelBt, importExcelBt, reloadBt, searchBt;
    private JTextField findText;
    private JTable tableSupplier;
    private DefaultTableModel modelSupplier;

    private ToolPanel toolPanel = new ToolPanel();
    private TablePanel tablePanel = new TablePanel();
    //data

    private static List<entity.Supplier> suppliers = reloadData();

    private static List<entity.Supplier> reloadData() {
      return LoginFrame.COMPUTER_SHOP.getAllSupplier();
    }




    private String selectedOption = "ALL";

    public SupplierPanel() {
      setLayout(new BorderLayout());
      add(toolPanel, BorderLayout.NORTH);
      add(tablePanel, BorderLayout.CENTER);
    }

    private class ToolPanel extends JPanel {
      public ToolPanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);

        {
          addBt = new JButton("Add");
          ButtonConfig.setStyleButton(
              addBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              addBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/database-add-icon.png", addBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.addKey, addBt);
          addBt.addActionListener(
              e -> {
                AddSupplierFrame addSupplierFrame =
                    new AddSupplierFrame(() -> updateSuppliers(selectedOption));
                addSupplierFrame.showFrame();
              });
        }

        {
          modifyBt = new JButton("Modify");
          ButtonConfig.setStyleButton(
              modifyBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(90, 80));
          ButtonConfig.setButtonIcon("src/main/java/Icon/modify.png", modifyBt, 35);
          ButtonConfig.addButtonHoverEffect(
              modifyBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.modifyKey, modifyBt);
          modifyBt.addActionListener(e -> modifyHandle());
        }

        {
          deleteBt = new JButton("Delete");
          ButtonConfig.setStyleButton(
              deleteBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt, 35);
          ButtonConfig.addButtonHoverEffect(
              deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.deleteKey, deleteBt);
          deleteBt.addActionListener(e -> deleteHandle());
        }

        {
          exportExcelBt = new JButton("Export");
          ButtonConfig.setStyleButton(
              exportExcelBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(90, 80));
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt, 35);
          ButtonConfig.addButtonHoverEffect(
              exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.exportExcelKey, exportExcelBt);
          exportExcelBt.addActionListener(e -> exportExcel(suppliers, columnNamesSUPPLIER));
        }

        {
          importExcelBt = new JButton("Import");
          ButtonConfig.setStyleButton(
              importExcelBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/icons8-export-excel-50.png", importExcelBt, 35);
          ButtonConfig.addButtonHoverEffect(
              importExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.importExcelKey, importExcelBt);

        }

        {
          reloadBt = new JButton("Reload");
          ButtonConfig.setStyleButton(
              reloadBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/reload_icon.png", reloadBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.reloadKey, reloadBt);
          reloadBt.addActionListener(
              e -> {
                updateSuppliers(selectedOption);
                findText.setText("");
              });
        }
        findText =
            TextFieldConfig.createTextField(
                "Search by Name",
                new Font("Arial", Font.PLAIN, 24),
                Color.GRAY,
                new Dimension(280, 50));
        findText.addActionListener(e -> searchBt.doClick());

        searchBt = new JButton();
        ButtonConfig.setStyleButton(
            searchBt,
            Style.FONT_PLAIN_13,
            Color.BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(40, 45));
        ButtonConfig.setButtonIcon("src/main/java/Icon/106236_search_icon.png", searchBt, 10);
        ButtonConfig.addButtonHoverEffect(
            searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        searchBt.addActionListener(
            e -> {
              if (!findText.getText().isBlank()) {
                String text = findText.getText().toLowerCase().trim();
                System.out.println(LoginFrame.COMPUTER_SHOP.findSupplierByName(text));

                searchSuppliers(selectedOption, text);
              } else {
                updateSuppliers(selectedOption);
              }
            });

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(findText);
        searchPanel.add(searchBt);
        searchPanel.setBackground(Style.WORD_COLOR_WHITE);

        String[] sortOptions = {"ALL", "NAME", "EMAIL", "PHONE NUMBER", "ADDRESS", "DATE"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setPreferredSize(new Dimension(100, 59));
        sortComboBox.setBackground(Style.WORD_COLOR_WHITE);
        sortComboBox.setForeground(Style.WORD_COLOR_BLACK);
        sortComboBox.setFont(Style.FONT_PLAIN_15);
        sortComboBox.addActionListener(
            e -> {
              selectedOption = (String) sortComboBox.getSelectedItem();
              sortTable(selectedOption);
            });

        JPanel applicationPanel = new JPanel(new FlowLayout());
        applicationPanel.add(addBt);
        applicationPanel.add(deleteBt);
        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(modifyBt);
        applicationPanel.add(sortComboBox);
        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(exportExcelBt);
        applicationPanel.add(importExcelBt);
        applicationPanel.add(reloadBt);
        applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Style.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(applicationPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(searchPanel, gbc);
        mainPanel.setBackground(Style.WORD_COLOR_WHITE);

        add(mainPanel);
      }

      private void sortTable(String selectedOption) {
        updateSuppliers(selectedOption);
      }

      private void modifyHandle() {
        int selectedRow = tableSupplier.getSelectedRow();
        if (selectedRow != -1) {
          int supplierId = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 1));
//          ModifySupplierFrame modifySupplierFrame =
//              new ModifySupplierFrame;
//          modifySupplierFrame.showFrame();
          ToastNotification.showToast(" modify.", 3000, 50, -1, -1);
        } else {
          ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
        }
      }

      private void deleteHandle() {
        int selectedRow = tableSupplier.getSelectedRow();

        if (selectedRow != -1) {
          int supplierId = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 1));


          modelSupplier.removeRow(selectedRow);

          ToastNotification.showToast("Supplier marked as deleted successfully.", 3000, 50, -1, -1);
        } else {
          ToastNotification.showToast("Please select a row to delete.", 3000, 50, -1, -1);
        }
      }
    }

    private class TablePanel extends JPanel {
      public TablePanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);

        tableSupplier = createTable(modelSupplier, columnNamesSUPPLIER);
        tableSupplier.setRowHeight(40);
        resizeColumnWidth(tableSupplier, 300);
        tableSupplier
            .getColumnModel()
            .getColumn(tableSupplier.getColumnCount() - 1)
            .setPreferredWidth(400);
        JScrollPane scrollPaneSupplier = new JScrollPane(tableSupplier);
        modelSupplier = (DefaultTableModel) tableSupplier.getModel();
        suppliers = LoginFrame.COMPUTER_SHOP.getAllSupplier();

        upDataTable(suppliers, modelSupplier);

        JTabbedPane tabbedPaneSupplier =
            createTabbedPane(scrollPaneSupplier, "Supplier List", Style.FONT_BOLD_16);
        add(tabbedPaneSupplier, BorderLayout.CENTER);
      }
    }

    private void updateSuppliers(String column) {
      modelSupplier.setRowCount(0);
      LoginFrame.COMPUTER_SHOP.sortSupplierByColumn(column, suppliers);
      upDataTable(suppliers, modelSupplier);
    }

    private void searchSuppliers(String column, String text) {
      modelSupplier.setRowCount(0);
      suppliers = LoginFrame.COMPUTER_SHOP.findSuppliersByName(text);
      LoginFrame.COMPUTER_SHOP.sortSupplierByColumn(column, suppliers);

      upDataTable(suppliers, modelSupplier);
    }

    public static void upDataTable(List<entity.Supplier> suppliers, DefaultTableModel modelSupplier) {
      String[][] rowData = Supplier.getData(suppliers);
      for (String[] strings : rowData) {
        modelSupplier.addRow(strings);
      }
    }
  }

  private class CustomerPanel extends JPanel {
    final String[] customerColumnNames = {
      "Serial number", "Customer ID", "Customer Name", "Email", "Address", "Password", "Avata"
    };

    private JTable tableCustomer;
    private DefaultTableModel modelCustomer;
    private JScrollPane scrollPaneCustomer;
    private JTabbedPane tabbedPaneCustomer;
    private ToolPanel toolPanel = new ToolPanel();
    private TableCustomerPanel tableCustomerPanel = new TableCustomerPanel();

    private JButton addCustomerBt,
        modifyCustomerBt,
        exportCustomerExcelBt,
        searchCustomerBt,
        reloadCustomerBt,
        blockCustomer,
        writeToFileTXT;
    private JTextField findCustomerField;

    private JPanel showOrderContainer;
    private TextDisplayPanel billTextDisplayPanal;
    private final int TAB_DATA_CUSTOMER = 0;
    private final int TAB_BILL = 2;



    private static List<entity.Customer> customers = new ArrayList<>();

    private JPanel searchPanel, applicationPanel, mainPanel;

    public CustomerPanel() {
      setLayout(new BorderLayout());
      toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
      add(toolPanel, BorderLayout.NORTH);
      add(tableCustomerPanel, BorderLayout.CENTER);
    }

    public class ToolPanel extends JPanel {
      public ToolPanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);
        addCustomerBt = new JButton("Add");
        ButtonConfig.addButtonHoverEffect(
            addCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            addCustomerBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/database-add-icon.png", addCustomerBt, 35);

        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.addKey, addCustomerBt);
        addCustomerBt.addActionListener(
            new ActionListener() {
              @Override
              @SneakyThrows
              public void actionPerformed(ActionEvent e) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.invokeLater(CustomerInfoFrame::new);
                reload();
              }
            });

        modifyCustomerBt = new JButton("Modify");
        ButtonConfig.addButtonHoverEffect(
            modifyCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            modifyCustomerBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/modify.png", modifyCustomerBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.modifyKey, modifyCustomerBt);

        modifyCustomerBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCustomer.getSelectedRow();
                int columnIndex = 0;
                if (selectedRow != -1) {
                  Object value = tableCustomer.getValueAt(selectedRow, columnIndex);

                  int customerId = Integer.parseInt(value.toString());
                  SwingUtilities.invokeLater(
                      () -> {
//                        ModifyCustomerFrame modifyCustomerFrame =
//                            new ModifyCustomerFrame(customers.get(customerId - 1));
                        reload();
                      });
                }
              }
            });

        blockCustomer = new JButton("Block");
        ButtonConfig.addButtonHoverEffect(
            blockCustomer, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            blockCustomer,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/block_User.png", blockCustomer, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.blockUserKey, blockCustomer);
        blockCustomer.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {

                int selectedRow = tableCustomer.getSelectedRow();
                int columnIndex = 1;
                int fullName = 2;
                if (selectedRow != -1) {
                  Object value = tableCustomer.getValueAt(selectedRow, columnIndex);
                  int customerId = Integer.parseInt(value.toString());
                  String customername = (String) tableCustomer.getValueAt(selectedRow, fullName);
                  if (customername.contains("*")) {
                    JOptionPane.showMessageDialog(null, "Unblock customerId : " + customerId);



                    JOptionPane.showMessageDialog(
                        null,
                        "Customer"
                            + tableCustomer.getValueAt(selectedRow, fullName)
                            + "Unblocked! ");
                  } else {
                    JOptionPane.showMessageDialog(null, "Block customerId : " + customerId);

                    JOptionPane.showMessageDialog(
                        null,
                        "Customer"
                            + tableCustomer.getValueAt(selectedRow, fullName)
                            + "is blocked! ");
                  }
                  reload();
                }
              }
            });

        exportCustomerExcelBt = new JButton("Export");
        ButtonConfig.addButtonHoverEffect(
            exportCustomerExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            exportCustomerExcelBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon(
            "src/main/java/Icon/icons8-file-excel-32.png", exportCustomerExcelBt, 35);
        KeyStrokeConfig.addKeyBindingButton(
            this, KeyStrokeConfig.exportExcelKey, exportCustomerExcelBt);
        exportCustomerExcelBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                String fileName =
                    JOptionPane.showInputDialog(
                        null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                if (customers.isEmpty())
                  JOptionPane.showMessageDialog(
                      null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                else {
                  JOptionPane.showMessageDialog(
                      null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);
                  fileName =
                      fileName.trim().endsWith(".xlsx")
                          ? fileName.trim()
                          : fileName.trim() + ".xlsx";
//                  ExcelConfig.exportToExcel(customers, fileName, customerColumnNames);

                  JOptionPane.showMessageDialog(
                      null, "Created!  excel ", "Message", JOptionPane.ERROR_MESSAGE);
                  reload();
                }
              }
            });

        writeToFileTXT = new JButton("View Bill");
        ButtonConfig.addButtonHoverEffect(
            writeToFileTXT, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            writeToFileTXT,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/bill.png", writeToFileTXT, 35);

        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.toBillKey, writeToFileTXT);
        writeToFileTXT.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                if (false)
                  JOptionPane.showMessageDialog(
                      null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                else {
                  String fileName =
                      JOptionPane.showInputDialog(
                          null,
                          "Enter bill file name :",
                          "Input file",
                          JOptionPane.QUESTION_MESSAGE);
                  JOptionPane.showMessageDialog(
                      null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);

//                  CustomerExporter.writeBillToFile(new BillConfig(bills).getNewBill(), fileName);
                  JOptionPane.showMessageDialog(
                      null, "Created !!! ", "Message", JOptionPane.ERROR_MESSAGE);
                  reload();
                }
              }
            });

        findCustomerField =
            TextFieldConfig.createTextField(
                "Search by Name",
                new Font("Arial", Font.PLAIN, 24),
                Color.GRAY,
                new Dimension(280, 50));
        findCustomerField.addActionListener(e -> searchCustomerBt.doClick());

        searchCustomerBt = new JButton();
        ButtonConfig.addButtonHoverEffect(
            searchCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            searchCustomerBt,
            Style.FONT_PLAIN_13,
            Color.BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(40, 45));
        ButtonConfig.setButtonIcon(
            "src/main/java/Icon/106236_search_icon.png", searchCustomerBt, 10);
        searchCustomerBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {

                switch (getIndexSelectedTab()) {
                  case TAB_DATA_CUSTOMER:
                    {
                      if (findCustomerField.getText().trim().isEmpty()) return;
                      ArrayList<entity.Customer> cuss = null;
                      if (cuss.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            tableCustomerPanel, "No customer found as requested!");
                        return;
                      }else{

                      }
                      upDataTable(cuss, modelCustomer, tableCustomer);
                      break;
                    }
                  case TAB_BILL:
                    {
                      if (findCustomerField.getText().trim().isEmpty()) return;
                      try {
                        int customerId = Integer.parseInt(findCustomerField.getText());

                        showOrderContainer.removeAll();


                        showOrderContainer.add(new ShowOrder());

                        showOrderContainer.revalidate();
                        showOrderContainer.repaint();

                      } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "You must enter the ID Customer");
                        findCustomerField.setText("");
                      }
                      break;
                    }
                }
              }
            });

        reloadCustomerBt = new JButton("Reload");
        ButtonConfig.addButtonHoverEffect(
            reloadCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            reloadCustomerBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/reload_icon.png", reloadCustomerBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.reloadKey, reloadCustomerBt);
        reloadCustomerBt.addActionListener(
            e -> {
              reload();

              findCustomerField.setText("");
            });

        searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(findCustomerField);
        searchPanel.add(searchCustomerBt);
        searchPanel.setBackground(Style.WORD_COLOR_WHITE);

        applicationPanel = new JPanel(new FlowLayout());
        applicationPanel.add(addCustomerBt);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(modifyCustomerBt);
        applicationPanel.add(blockCustomer);

        JLabel none = new JLabel("");
        none.setFont(Style.FONT_PLAIN_13);
        none.setHorizontalAlignment(SwingConstants.CENTER);
        none.setVerticalAlignment(SwingConstants.CENTER);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(exportCustomerExcelBt);
        applicationPanel.add(writeToFileTXT);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(reloadCustomerBt);
        applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Style.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(applicationPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(searchPanel, gbc);
        mainPanel.setBackground(Style.WORD_COLOR_WHITE);

        add(mainPanel);
      }
    }

    private class TableCustomerPanel extends JPanel {
      public TableCustomerPanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);

        tableCustomer = createTable(modelCustomer, customerColumnNames);
        tableCustomer
            .getColumnModel()
            .getColumn(customerColumnNames.length - 1)
            .setCellRenderer(new ImageInJTable.ImageRenderer());
        tableCustomer.setRowHeight(100);
        resizeColumnWidth(tableCustomer, 219);
        modelCustomer = (DefaultTableModel) tableCustomer.getModel();

        customers = LoginFrame.COMPUTER_SHOP.getAllCustomer();
        upDataTable(customers, modelCustomer, tableCustomer);

        scrollPaneCustomer = new JScrollPane(tableCustomer);
        tabbedPaneCustomer = createTabbedPane(scrollPaneCustomer, "Customer", Style.FONT_BOLD_16);
        tabbedPaneCustomer.add("Sales Chart", new Schemas());

        showOrderContainer = new JPanel();
        showOrderContainer.add(new Label("You should continue to find the customer Id!!!"));
        JScrollPane scrollShowOrderContainer = new JScrollPane(showOrderContainer);
        tabbedPaneCustomer.add("Customer Bill", scrollShowOrderContainer);
        add(tabbedPaneCustomer, BorderLayout.CENTER);
      }
    }

    private void reload() {
      customers = LoginFrame.COMPUTER_SHOP.getAllCustomer();
      upDataTable((ArrayList<entity.Customer>) customers, modelCustomer, tableCustomer);
      showOrderContainer.removeAll();
      showOrderContainer.revalidate();
      showOrderContainer.repaint();
      showOrderContainer.add(new Label("You should continue to find the customer Id!!!"));
      billTextDisplayPanal.setText("You should continue to find the customer Id!!!");
    }

    public int getIndexSelectedTab() {
      return tabbedPaneCustomer.getSelectedIndex();
    }

    private class Schemas extends JPanel {
      public Schemas() {

//        JFreeChart barChart =
//            ChartFactory.createBarChart(
//                "Purchase Quantity by Customer",
//                "Customer",
//                "Number of purchased",
//                createDataset(),
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false);
//
//        ChartPanel chartPanel = new ChartPanel(barChart);
//        chartPanel.setPreferredSize(new Dimension(800, 600));
//
//        this.setLayout(new BorderLayout());
//        this.add(chartPanel, BorderLayout.CENTER);
      }

//      private CategoryDataset createDataset() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        Map<String, Integer> topCustomers = new HashMap<>();
//
//        consertCustomerToMap(customers, topCustomers);
//
//        for (Map.Entry<String, Integer> entry : topCustomers.entrySet()) {
//          dataset.addValue(entry.getValue(), "Số lượng mua", entry.getKey());
//        }
//
//        return dataset;
//      }

//      private void consertCustomerToMap(ArrayList<entity.Customer> customers, Map<String, Integer> map) {
//        for (var customer : customers) {
//          map.put(customer.getFullName(), customer.getNumberOfPurchased());
//        }
//      }
    }

    public static void upDataTable(
        List<entity.Customer> customers, DefaultTableModel modelCustomerTable, JTable tableCustomer) {
      Object[][] rowData = entity.Customer.getDataOnTable((ArrayList<entity.Customer>) customers);
      ProductPanel.TablePanel.removeDataTable(modelCustomerTable);
      for (int i = 0; i < rowData.length; i++) {
        modelCustomerTable.addRow(rowData[i]);
      }
    }
  }

  private class OrderPanel extends JPanel {
    private JPanel detailsContainer;
    private JPanel orderContainer;

    final String[] orderColumnNames = {
      "Order ID",
      "Customer ID",
      "Order Date",
      "Ship address",
      "Status Item",
      "Saler",
      "Saler ID",
      "Total Price",
      "Total Quantity"
    };

    private JButton exportExcelBt, reloadBt, searchBt, deliveryOrderBt;
    private JTextField searchOrderField;

    private JTable orderTable, dispatchedOrderTable;
    private DefaultTableModel orderModel, dispatchedOrderModel;
    private JScrollPane orderScrollPane, dispatchedOrderScroll;
    private JTabbedPane orderTabbedPane;
    private ToolPanel toolPanel = new ToolPanel();
    private TableOrderPanel tableCustomerPanel = new TableOrderPanel();

    private ExportPanel exportPanel;

    private List<Order> orders;

    public OrderPanel() {
      setLayout(new BorderLayout());
      toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
      toolPanel.setPreferredSize(new Dimension(800, 120));
      add(toolPanel, BorderLayout.NORTH);
      add(tableCustomerPanel, BorderLayout.CENTER);
    }

    private class ToolPanel extends JPanel {

      ToolPanel() {
        setLayout(new GridLayout(1, 2));
        setBackground(Color.WHITE);
        {
          deliveryOrderBt = new JButton("Delivery");
          ButtonConfig.setStyleButton(
              deliveryOrderBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              deliveryOrderBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/deliveryIcon1.png", deliveryOrderBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.deliveryKey, deliveryOrderBt);
          deliveryOrderBt.setHorizontalTextPosition(SwingConstants.CENTER);
          deliveryOrderBt.setVerticalTextPosition(SwingConstants.BOTTOM);
          deliveryOrderBt.addActionListener(e -> dispatchOrder());
        }
        {
          exportExcelBt = new JButton("Export");
          ButtonConfig.setStyleButton(
              exportExcelBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.exportExcelKey, exportExcelBt);
          exportExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
          exportExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
          exportExcelBt.addActionListener(
              e -> {
                JTable table = getSelectedTable();
                if (table != null) {
//                  ArrayList<CustomerOrderDTO> list =
//                      orders.values().stream()
//                          .flatMap(Collection::stream)
//                          .collect(Collectors.toCollection(ArrayList::new));
//                  exportExcel(list, orderColumnNames);
                }
              });
        }
        {
          reloadBt = new JButton("Reload");
          ButtonConfig.setStyleButton(
              reloadBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/reload_icon.png", reloadBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.reloadKey, reloadBt);
          reloadBt.setHorizontalTextPosition(SwingConstants.CENTER);
          reloadBt.setVerticalTextPosition(SwingConstants.BOTTOM);
          reloadBt.addActionListener(
              e -> {
                searchOrderField.setText("");
                updateOrders();
              });
        }

        JPanel toolPn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolPn.setBackground(Color.WHITE);
        toolPn.add(deliveryOrderBt);
        toolPn.add(ButtonConfig.createVerticalSeparator());
        toolPn.add(exportExcelBt);
        toolPn.add(reloadBt);

        // search bar to find order
        searchOrderField =
            TextFieldConfig.createTextField(
                "Search Order", Style.FONT_PLAIN_18, Color.GRAY, new Dimension(300, 50));
        searchOrderField.addActionListener(e -> searchBt.doClick());
        searchOrderField.setAlignmentY(SwingConstants.CENTER);
        searchBt = new JButton();
        ButtonConfig.setStyleButton(
            searchBt,
            Style.FONT_PLAIN_15,
            Color.BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(40, 45));
        ButtonConfig.addButtonHoverEffect(
            searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setButtonIcon("src/main/java/Icon/106236_search_icon.png", searchBt, 10);
        searchBt.addActionListener(e -> searchHandle());

        JPanel searchPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPn.setBackground(Color.WHITE);
        searchPn.add(Box.createVerticalStrut(50));
        searchPn.add(searchOrderField);
        searchPn.add(searchBt);

        add(toolPn);
        add(searchPn);
      }

      private void searchHandle() {
        String searchText = searchOrderField.getText().trim().toLowerCase();
        updateOrders(searchText);
      }

      private JTable getSelectedTable() {
        int index = orderTabbedPane.getSelectedIndex();

        return switch (index) {
          case 0 -> {
            reloadOrders(null, "");
            yield orderTable;
          }
          case 1 -> {
            reloadOrders(OrderType.DISPATCHED_MESSAGE, "");
            yield dispatchedOrderTable;
          }
          default -> null;
        };
      }

      private void dispatchOrder() {
        JTable table = getSelectedTable();

        int selectRow = (table != null) ? table.getSelectedRow() : -1;
        if (selectRow != -1) {
          int orderID = Integer.parseInt(table.getValueAt(selectRow, 0).toString());
          String statusMessage = table.getValueAt(selectRow, 4).toString();

          switch (statusMessage) {
            case OrderType.ACTIVE_MESSAGE -> {
              orderTabbedPane.setSelectedIndex(2);
//              exportPanel.loadOrders(orders.get(orderID));
              JOptionPane.showMessageDialog(
                      null, " switch (statusMessage) {\n" +
                              "            case OrderType.ACTIVE_MESSAGE -> {\n" +
                              "              orderTabbedPane.setSelectedIndex(2);", "", JOptionPane.WARNING_MESSAGE);
            }
            case OrderType.UN_ACTIVE_MESSAGE -> ToastNotification.showToast(
                "This order has been canceled and cannot be dispatched.", 3000, 50, -1, -1);
            case OrderType.DISPATCHED_MESSAGE -> ToastNotification.showToast(
                "This order has already been processed for shipping.", 3000, 50, -1, -1);
            default -> ToastNotification.showToast("Unknown order status!", 3000, 50, -1, -1);
          }
        } else {
          ToastNotification.showToast("Please select an order to dispatch!", 3000, 50, -1, -1);
        }
      }
    }

    private class TableOrderPanel extends JPanel {
      TableOrderPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        // orders table
        orderTable = createTable(orderModel, orderColumnNames);
        orderModel = (DefaultTableModel) orderTable.getModel();
        orderScrollPane = new JScrollPane(orderTable);
        // Dispatched orders table
        dispatchedOrderTable = createTable(dispatchedOrderModel, orderColumnNames);
        dispatchedOrderModel = (DefaultTableModel) dispatchedOrderTable.getModel();
        dispatchedOrderScroll = new JScrollPane(dispatchedOrderTable);

        updateOrders();

        // panel export products for each order
        exportPanel = new ExportPanel();

        orderTabbedPane = createTabbedPane(orderScrollPane, "Customer's Order", Style.FONT_BOLD_16);
        orderTabbedPane.add("Dispatched Orders", dispatchedOrderScroll);
        orderTabbedPane.add("Export Product", exportPanel);
        add(orderTabbedPane, BorderLayout.CENTER);
      }
    }

    private class ExportPanel extends JPanel {
      private JLabel totalPriceLabel, quantityLabel;
      private CustomButton exportBt, cancelBt;
      private OrderDetailsPanel detailsPanel;
      private PaymentPanel paymentPanel;
      private int orderID;

      private JTextField customerIdTF,
          orderDateTF,
          shipAddressTF,
          statusItemTF,
          salerTF,
          salerIdTF,
          productIdTF,
          productNameTF,
          productTypeTF,
          productBrandTF,
          operatingSystemTF,
          cpuTF,
          ramTF,
          madeInTF,
          diskTF,
          weightTF,
          monitorTF,
          cardTF;

      public ExportPanel() {
        setLayout(new BorderLayout());

        detailsPanel = new OrderDetailsPanel();
        add(detailsPanel, BorderLayout.CENTER);

        paymentPanel = new PaymentPanel();
        add(paymentPanel, BorderLayout.SOUTH);
      }

      class OrderDetailsPanel extends JPanel {
        private ProductsMainPanel productDetailsPanel;
        private CustomerDetailsPanel customerDetailsPanel;

        OrderDetailsPanel() {
          setLayout(new BorderLayout());

          customerDetailsPanel = new CustomerDetailsPanel();
          productDetailsPanel = new ProductsMainPanel();
          add(customerDetailsPanel, BorderLayout.WEST);
          add(productDetailsPanel, BorderLayout.CENTER);
        }

        private void addProductPanel(List<Product> list) {
          productDetailsPanel.addProductToOrders(productDetailsPanel.createProduct(list));
        }
      }
      // customer information and details about order
      class CustomerDetailsPanel extends JPanel {
        CustomerDetailsPanel() {
          setLayout(new GridBagLayout());
          setBackground(Color.WHITE);
          Border lineBorder =
              BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2);
          TitledBorder titledBorder =
              BorderFactory.createTitledBorder(
                  lineBorder,
                  "Order Information",
                  TitledBorder.LEFT,
                  TitledBorder.TOP,
                  Style.FONT_BOLD_20,
                  Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
          setBorder(titledBorder);

          GridBagConstraints gbc = new GridBagConstraints();
          gbc.insets = new Insets(5, 5, 5, 5);

          String[] orderFields = {
            "Customer ID", "Order Date", "Ship Address",
            "Status", "Saler", "Saler ID"
          };

          JTextField[] orderTFs =
              new JTextField[] {
                // bỏ dữ liệu vào từng textfield
                customerIdTF =
                    TextFieldConfig.createTextField(
                        "",
                        Style.FONT_PLAIN_18,
                        Color.BLACK,
                        Color.WHITE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        new Dimension(300, 50),
                        false),
                orderDateTF =
                    TextFieldConfig.createTextField(
                        "",
                        Style.FONT_PLAIN_18,
                        Color.BLACK,
                        Color.WHITE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        new Dimension(300, 50),
                        false),
                shipAddressTF =
                    TextFieldConfig.createTextField(
                        "",
                        Style.FONT_PLAIN_18,
                        Color.BLACK,
                        Color.WHITE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        new Dimension(300, 50),
                        false),
                statusItemTF =
                    TextFieldConfig.createTextField(
                        "",
                        Style.FONT_PLAIN_18,
                        Color.BLACK,
                        Color.WHITE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        new Dimension(300, 50),
                        false),
                salerTF =
                    TextFieldConfig.createTextField(
                        "",
                        Style.FONT_PLAIN_18,
                        Color.BLACK,
                        Color.WHITE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        new Dimension(300, 50),
                        false),
                salerIdTF =
                    TextFieldConfig.createTextField(
                        "",
                        Style.FONT_PLAIN_18,
                        Color.BLACK,
                        Color.WHITE,
                        Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                        new Dimension(300, 50),
                        false)
              };

          for (int i = 0; i < orderFields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            add(
                LabelConfig.createLabel(
                    orderFields[i], Style.FONT_BOLD_18, Color.BLACK, SwingConstants.LEFT),
                gbc);
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(orderTFs[i], gbc);
            orderTFs[i].setEditable(false);
          }
        }
      }

      // the list of product ordered by customer
      class ProductsMainPanel extends JPanel {
        ProductsPanel productsPn = new ProductsPanel();
        ProductDetailsPanel productDetailsPn = new ProductDetailsPanel();
        CardLayout cardLayoutOrder = new CardLayout();
        List<Product> productList;

        ProductsMainPanel() {
          setLayout(cardLayoutOrder);
          setBackground(Color.WHITE);
          Border lineBorder =
              BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2);
          TitledBorder titledBorder =
              BorderFactory.createTitledBorder(
                  lineBorder,
                  "Products",
                  TitledBorder.LEFT,
                  TitledBorder.TOP,
                  Style.FONT_BOLD_20,
                  Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
          setBorder(titledBorder);
          addDetailsProduct();
          add(productsPn, "products");
          add(productDetailsPn, "details");

          cardLayoutOrder.show(this, "products");
        }

        public void showPanelOrder(String panel) {
          cardLayoutOrder.show(this, panel);
        }

        // danh sách các sản phẩm trong order của user
        class ProductsPanel extends JPanel {
          JScrollPane scrollpane;

          ProductsPanel() {
            setLayout(new BorderLayout());
            orderContainer = new JPanel();
            orderContainer.setLayout(new BoxLayout(orderContainer, BoxLayout.Y_AXIS));
            orderContainer.setBackground(Color.WHITE);

            scrollpane = new JScrollPane(orderContainer);
            setColorScrollPane(scrollpane, Style.BACKGROUND_COLOR, Color.WHITE);
            add(scrollpane, BorderLayout.CENTER);
          }
        }

        class ProductDetailsPanel extends JPanel {

          ProductDetailsPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JPanel topPn = new JPanel(new BorderLayout());
            topPn.setBackground(Color.WHITE);
            CustomButton back =
                ButtonConfig.createCustomButton(
                    "Back",
                    Style.FONT_BOLD_16,
                    Color.white,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    Style.LIGHT_BlUE,
                    5,
                    SwingConstants.CENTER,
                    new Dimension(70, 25));
            back.addActionListener(e -> showPanelOrder("products"));
            topPn.add(back, BorderLayout.EAST);

            JLabel title =
                LabelConfig.createLabel(
                    "Product details",
                    Style.FONT_BOLD_18,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    SwingConstants.CENTER);
            topPn.add(title, BorderLayout.CENTER);

            add(topPn, BorderLayout.NORTH);

            detailsContainer = new JPanel();
            detailsContainer.setLayout(new BorderLayout());
            add(detailsContainer, BorderLayout.CENTER);
          }
        }

        public JPanel createProduct(List<Product> productList) {
          this.productList = productList;
          JPanel wrapperPn = new JPanel();
          wrapperPn.setLayout(new BoxLayout(wrapperPn, BoxLayout.Y_AXIS));
          wrapperPn.setBackground(Color.WHITE);

          for (Product product : productList) {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setPreferredSize(new Dimension(600, 120));
            mainPanel.setMaximumSize(new Dimension(600, 120)); // Prevent stretching
            Border lineBorder =
                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1);
            Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            mainPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

            // Image panel
            JPanel imgPn = new JPanel();
            imgPn.setBackground(Color.WHITE);
            JLabel proImg =
                new JLabel(
                    createImageForProduct(
                        product.getImages().stream()
                            .findFirst()
                            .orElse( new entity.Image(0,"src/main/java/img/not-found-image.png"))
                            .getUrl(),
                        100,
                        100));
            imgPn.add(proImg);

            // Details panel
            JPanel proDetails = new JPanel(new GridBagLayout());
            proDetails.setBackground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Product Name
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 0.4;
            JLabel proName =
                LabelConfig.createLabel(
                    "Product Name: " + product.getName(),
                    Style.FONT_BOLD_18,
                    Color.BLACK,
                    SwingConstants.LEFT);
            proDetails.add(proName, gbc);

            // Product ID
            gbc.gridy++;
            gbc.weighty = 0.3;
            JLabel proID =
                LabelConfig.createLabel(
                    "Product ID: " + product.getId(),
                    Style.FONT_PLAIN_13,
                    Color.BLACK,
                    SwingConstants.LEFT);
            proDetails.add(proID, gbc);

            // Price and Quantity
            gbc.gridy++;
            JPanel priceAndQuantity = new JPanel(new GridLayout(1, 3));
            priceAndQuantity.setBackground(Color.WHITE);

            JPanel pricePn = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pricePn.setBackground(Color.WHITE);
            JLabel proPrice =
                LabelConfig.createLabel(
                    formatCurrency.format(product.getPrice()) + "₫",
                    Style.FONT_BOLD_15,
                    Color.RED,
                    SwingConstants.LEFT);
            pricePn.add(proPrice);

            JPanel quantityPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            quantityPn.setBackground(Color.WHITE);
            JLabel proQuantity =
                LabelConfig.createLabel(
                    "x" + product.getQuantity(),
                    Style.FONT_BOLD_15,
                    Color.BLACK,
                    SwingConstants.RIGHT);
            quantityPn.add(proQuantity);

            JPanel btPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btPn.setBackground(Color.WHITE);
            CustomButton viewDetail =
                ButtonConfig.createCustomButton(
                    "More Details",
                    Style.FONT_BOLD_13,
                    Color.white,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    Style.LIGHT_BlUE,
                    2,
                    SwingConstants.CENTER,
                    new Dimension(120, 25));
            viewDetail.addActionListener(
                e -> {
                  showDetail(product.getId());
                  showPanelOrder("details");
                });
            btPn.add(viewDetail);

            priceAndQuantity.add(pricePn);
            priceAndQuantity.add(quantityPn);
            priceAndQuantity.add(btPn);
            proDetails.add(priceAndQuantity, gbc);

            mainPanel.add(imgPn, BorderLayout.WEST);
            mainPanel.add(proDetails, BorderLayout.CENTER);

            wrapperPn.add(mainPanel);
            wrapperPn.add(Box.createRigidArea(new Dimension(0, 10)));
          }

          return wrapperPn;
        }

        public void showDetail(int id) {
          Product product =
              this.productList.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
          if (product != null) {
            productIdTF.setText(String.valueOf(product.getId()));
            productNameTF.setText(product.getName());
            productTypeTF.setText(product.getType());
            productBrandTF.setText(product.getBrand());
            operatingSystemTF.setText(product.getOperatingSystem());
            cpuTF.setText(product.getCpu());
            ramTF.setText(product.getRam());
            madeInTF.setText(product.getMadeIn());
            diskTF.setText(product.getDisk());
            weightTF.setText(String.valueOf(product.getWeight()));
            monitorTF.setText(product.getMonitor());
            cardTF.setText(product.getCard());
          }
        }

        public void addProductToOrders(JPanel panel) {
          orderContainer.removeAll();
          orderContainer.add(panel);
          orderContainer.revalidate();
          orderContainer.repaint();
        }

        public void addDetailsProduct() {
          JPanel detailPn = new JPanel(new GridBagLayout());
          detailPn.setBackground(Color.WHITE);
          GridBagConstraints gbc = new GridBagConstraints();
          gbc.insets = new Insets(5, 5, 5, 5);

          String[] productFields = {
            "Product ID", "Product Name", "Type", "Brand",
            "OS", "CPU", "RAM", "Made In",
            "Disk", "Weight", "Monitor", "Card"
          };

          JTextField[] productTFs = {
            productIdTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            productNameTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            productTypeTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            productBrandTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            operatingSystemTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            cpuTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            ramTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            madeInTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            diskTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            weightTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            monitorTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false),
            cardTF =
                TextFieldConfig.createTextField(
                    "",
                    Style.FONT_PLAIN_15,
                    Color.BLACK,
                    Color.WHITE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    new Dimension(300, 40),
                    false)
          };

          for (int i = 0; i < productFields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            detailPn.add(
                LabelConfig.createLabel(
                    productFields[i], Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                gbc);
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            productTFs[i].setEditable(false);
            detailPn.add(productTFs[i], gbc);
          }
          JScrollPane scrollPane = new JScrollPane(detailPn);
          setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Style.LIGHT_BlUE);
          scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
          detailsContainer.add(scrollPane);
          detailsContainer.revalidate();
          detailsContainer.repaint();
        }
      }

      // bottom
      class PaymentPanel extends JPanel {
        PaymentPanel() {
          setLayout(new GridLayout(1, 2));

          JPanel leftPn = new JPanel(new GridLayout(2, 1));
          leftPn.setBackground(Color.WHITE);
          quantityLabel =
              new JLabel(
                  "<html><span style='color: black;'>Quantity:     </span> "
                      + "<span style='color: green;'>"
                      + "0"
                      + "</span> "
                      + "<span style='color: black;'> items</span></html>");
          quantityLabel.setFont(Style.FONT_BOLD_24);
          leftPn.add(quantityLabel);

          String price = formatCurrency.format(1000000); // bỏ tổng giá của order vào đây
          totalPriceLabel =
              new JLabel(
                  "<html><span style='color: black;'>Total Price:   </span> "
                      + "<span style='color: red;'>"
                      + "0"
                      + "₫</span></html>");
          totalPriceLabel.setFont(Style.FONT_BOLD_24);

          leftPn.add(totalPriceLabel);

          JPanel rightPn = new JPanel();
          rightPn.setBackground(Color.WHITE);

          cancelBt =
              ButtonConfig.createCustomButton(
                  "Cancel",
                  Style.FONT_BOLD_24,
                  Color.white,
                  Style.DELETE_BUTTON_COLOR_RED,
                  Style.LIGHT_RED,
                  8,
                  SwingConstants.CENTER,
                  new Dimension(200, 40));
          cancelBt.addActionListener(e -> handleCancel());
          rightPn.add(cancelBt);

          exportBt =
              ButtonConfig.createCustomButton(
                  "Export",
                  Style.FONT_BOLD_24,
                  Color.white,
                  Style.CONFIRM_BUTTON_COLOR_GREEN,
                  Style.LIGHT_GREEN,
                  8,
                  SwingConstants.CENTER,
                  new Dimension(200, 40));
          exportBt.addActionListener(e -> handleExport());
          rightPn.add(exportBt);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.exportExcelKey, exportBt);
          add(leftPn);
          add(rightPn);
        }

        private void handleCancel() {
          if (clearAllOrders()) {
            orderTabbedPane.setSelectedIndex(0);
            ToastNotification.showToast("Order canceled successfully!", 3000, 50, -1, -1);
          } else {
            ToastNotification.showToast("No orders available to cancel.", 3000, 50, -1, -1);
          }
        }

        private void handleExport() {
          if (clearAllOrders()) {

            updateOrders();
            orderTabbedPane.setSelectedIndex(1);
            ToastNotification.showToast("Order exported successfully!  chua code", 3000, 50, -1, -1);
          } else {
            ToastNotification.showToast("No orders available to export.", 3000, 50, -1, -1);
          }
        }

        private boolean clearAllOrders() {
          if (!customerIdTF.getText().isEmpty()) {
            orderContainer.removeAll();
            orderContainer.repaint();
            detailsPanel.productDetailsPanel.showPanelOrder("products");
            setText("", "", "", "", "", "", "", "");
            return true;
          } else return false;
        }
      }

//      private void loadOrders(List<Order> list) {
//        Order order = list.get(0);
//        List<OrderDetail> orderDetails = list.stream().map(Order::getOrderDetails).collect(Collectors.toList());
//
//        double totalCost = list.stream().mapToDouble(CustomerOrderDTO::totalCost).sum();
//        int quantity = orderDetails.stream().mapToInt(Product::getQuantity).sum();
//        orderID = order.getOrderId();
//
//        setText(
//            String.valueOf(order.getCustomerId()),
//            String.valueOf(order.getOrderDate()),
//            order.getShipAddress(),
//            order.getStatusItem(),
//            order.getSaler(),
//            String.valueOf(order.getSalerId()),
//            formatCurrency.format(totalCost),
//            String.valueOf(quantity));
//        this.detailsPanel.addProductPanel(orderDetails);
//      }

      private void setText(
          String orderId,
          String orderDate,
          String shipAddress,
          String statusItem,
          String saler,
          String salerId,
          String totalCost,
          String quantity) {
        this.customerIdTF.setText(orderId);
        this.orderDateTF.setText(orderDate);
        this.shipAddressTF.setText(shipAddress);
        this.statusItemTF.setText(statusItem);
        this.salerTF.setText(saler);
        this.salerIdTF.setText(String.valueOf(salerId));
        totalCost = totalCost.isEmpty() ? "0" : totalCost;
        this.totalPriceLabel.setText(
            "<html><span style='color: black;'>Total Price:   </span> "
                + "<span style='color: red;'>"
                + totalCost
                + "₫</span></html>");
        quantity = quantity.isEmpty() ? "0" : quantity;
        this.quantityLabel.setText(
            "<html><span style='color: black;'>Quantity:     </span> "
                + "<span style='color: green;'>"
                + quantity
                + "</span> "
                + "<span style='color: black;'> items</span></html>");
      }
    }

    private void reloadOrders(String status, String searchText) {
      this.orders = LoginFrame.COMPUTER_SHOP.getAllOrders();
//      if (searchText != null && !searchText.isEmpty()) {
//        orders = orders.stream().filter(order -> order.containText(searchText)).toList();
//      }
//      orders = orders.stream().filter(order -> (status != null) ? order.containText(status) : !order.containText(status)).toList();
    }

    private void upDataOrders(DefaultTableModel tableModel, String status, String searchText) {
      reloadOrders(status, searchText);
      tableModel.setRowCount(0);
      String[][] rowData = Order.getData(orders);
      for (String[] strings : rowData) {
        tableModel.addRow(strings);
      }
    }

    private void updateOrders() {
      upDataOrders(orderModel, null, "");
      upDataOrders(dispatchedOrderModel, OrderType.DISPATCHED_MESSAGE, "");
    }

    private void updateOrders(String searchText) {
      upDataOrders(orderModel, null, searchText);
      upDataOrders(dispatchedOrderModel, OrderType.DISPATCHED_MESSAGE, searchText);
    }
  }

  private class InventoryPanel extends JPanel {
    private JTable tableInventory, tableImport, tableExport;
    private JTabbedPane tabbedPaneMain;
    private ToolsPanel toolsPanel;
    private TablePanel tablePanel;
    private JTextField searchTextField;
    private JButton setForSaleBt, restockBt, deleteBt, modifyBt, exportExcelBt, reloadBt, searchBt;
    private DefaultTableModel modelInventory, modelImport, modelExport;
    private String searchText;
    private ArrayList<entity.Product> products;

    InventoryPanel() {
      setLayout(new BorderLayout(5, 5));
      setBackground(Style.WORD_COLOR_WHITE);

      toolsPanel = new ToolsPanel();
      add(toolsPanel, BorderLayout.NORTH);

      tablePanel = new TablePanel();
      add(tablePanel, BorderLayout.CENTER);
    }

    private class ToolsPanel extends JPanel {
      ToolsPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Tools"));
        setBackground(Style.WORD_COLOR_WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Style.WORD_COLOR_WHITE);

        {
          setForSaleBt = new JButton("Sale");
          ButtonConfig.setStyleButton(
              setForSaleBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              setForSaleBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/product-selling.png", setForSaleBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.setSaleKey, setForSaleBt);

          setForSaleBt.addActionListener(e -> setStatusHandle(Product.AVAILABLE));
          buttonPanel.add(setForSaleBt);
        }

        {
          restockBt = new JButton("Re-stock");
          ButtonConfig.setStyleButton(
              restockBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(90, 80));
          ButtonConfig.addButtonHoverEffect(
              restockBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/product-restock.png", restockBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.restockKey, restockBt);

          restockBt.addActionListener(e -> setStatusHandle(Product.IN_STOCK));
          buttonPanel.add(restockBt);
        }
        buttonPanel.add(ButtonConfig.createVerticalSeparator());

        {
          deleteBt = new JButton("Delete");
          ButtonConfig.setStyleButton(
              deleteBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.deleteKey, deleteBt);
          deleteBt.addActionListener(e -> deletedHandle());
          buttonPanel.add(deleteBt);
        }

        {
          modifyBt = new JButton("Modify");
          ButtonConfig.setStyleButton(
              modifyBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              modifyBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/modify.png", modifyBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.modifyKey, modifyBt);

          modifyBt.addActionListener(e -> modifyHandle());
          buttonPanel.add(modifyBt);
        }

        {
          exportExcelBt = new JButton("Export");
          ButtonConfig.setStyleButton(
              exportExcelBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt, 35);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.exportExcelKey, exportExcelBt);
          exportExcelBt.addActionListener(
              e -> {
                getSelectedTable();
                exportExcel(products, columnNamesPRODUCT);
              });
          buttonPanel.add(exportExcelBt);
        }

        {
          reloadBt = new JButton("Reload");
          ButtonConfig.setStyleButton(
              reloadBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
              reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/reload_icon.png", reloadBt, 35);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.reloadKey, reloadBt);

          reloadBt.addActionListener(
              e -> {
                updateProduct();
                searchTextField.setText("");
              });
          buttonPanel.add(reloadBt);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(buttonPanel, gbc);

        JPanel searchPanel = createSearchPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(searchPanel, gbc);
      }

      private JTable getSelectedTable() {
        int index = tabbedPaneMain.getSelectedIndex();

        return switch (index) {
          case 0 -> {
            reloadProducts(null, searchText);
            yield tableInventory;
          }
          case 1 -> {
            reloadProducts(Product.IN_STOCK, searchText);
            yield tableImport;
          }
          case 2 -> {
            reloadProducts(Product.AVAILABLE, searchText);
            yield tableExport;
          }
          default -> throw new IllegalStateException("Unexpected value: " + index);
        };
      }

      private void setStatusHandle(String status) {
        String[] messages =
            switch (status) {
              case (Product.AVAILABLE) -> new String[] {
                "Available for sale", "add product for sale."
              };
              case (Product.IN_STOCK) -> new String[] {"Re-stocked", "re-stock the product."};
              default -> null;
            };
        JTable selectedTable = getSelectedTable();

        int[] selectedRows = selectedTable.getSelectedRows();
        if (selectedRows.length > 0 && messages != null) {
          int y = -1, duration = 3000;
          for (int row : selectedRows) {
            int productId = Integer.parseInt(selectedTable.getValueAt(row, 1).toString());
            String productName = (String) selectedTable.getValueAt(row, 2);
            if (changeStatus(productId, status)) {
              ToastNotification.showToast(
                  "Successfully set product " + productName + " to " + messages[0],
                  duration,
                  50,
                  -1,
                  y++);
            } else
              ToastNotification.showToast(
                  "Failed to set product " + productName + " to " + messages[0],
                  duration,
                  50,
                  -1,
                  y++);
            duration += 100;
          }
          updateProduct();
        } else {
          ToastNotification.showToast("Please select a row to " + messages[1], 3000, 50, -1, -1);
        }
      }

      private void modifyHandle() {
        JTable selectedTable = getSelectedTable();

        if (selectedTable != null && selectedTable.getSelectedRow() != -1) {
          int selectedRow = selectedTable.getSelectedRow();
          SwingUtilities.invokeLater(
              () -> {
//                new ProductModifyForm(products.get(selectedRow), InventoryPanel.this::updateProduct)
//                    .setVisible(true);
              });
        } else {
          ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
        }
      }

      private void deletedHandle() {
        JTable selectedTable = getSelectedTable();

        if (selectedTable != null && selectedTable.getSelectedRow() != -1) {
          int selectedRow = selectedTable.getSelectedRow();
          int productId = Integer.parseInt(selectedTable.getValueAt(selectedRow, 1).toString());
          String productName = (String) selectedTable.getValueAt(selectedRow, 2);
          deletedProduct(productId);
          updateProduct();
          ToastNotification.showToast(
              "Successfully delete product " + productName, 3000, 50, -1, -1);
        } else {
          ToastNotification.showToast("Please select a row to deleted.", 3000, 50, -1, -1);
        }
      }
    }

    private class TablePanel extends JPanel {
      public TablePanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);

        tableInventory = createTable(modelInventory, columnNamesPRODUCT);
        tableImport = createTable(modelImport, columnNamesPRODUCT);
        tableExport = createTable(modelExport, columnNamesPRODUCT);

        modelInventory = (DefaultTableModel) tableInventory.getModel();
        upDataProducts(modelInventory, null, null);
        modelImport = (DefaultTableModel) tableImport.getModel();
        upDataProducts(modelImport, Product.IN_STOCK, null);
        modelExport = (DefaultTableModel) tableExport.getModel();
        upDataProducts(modelExport, Product.AVAILABLE, null);

        tabbedPaneMain =
            createTabbedPane(new JScrollPane(tableInventory), "Inventory", Style.FONT_BOLD_16);
        tabbedPaneMain.addTab("In Stock", new JScrollPane(tableImport));
        tabbedPaneMain.addTab("Available for Sale", new JScrollPane(tableExport));

        add(tabbedPaneMain, BorderLayout.CENTER);
      }
    }

    private JPanel createSearchPanel() {
      JPanel searchPanel = new JPanel(new GridBagLayout());
      searchPanel.setBackground(Style.WORD_COLOR_WHITE);
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);

      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      searchTextField =
          TextFieldConfig.createTextField(
              "Search by Name",
              new Font("Arial", Font.PLAIN, 24),
              Color.GRAY,
              new Dimension(280, 50));
      searchTextField.addActionListener(e -> searchBt.doClick());
      searchPanel.add(searchTextField, gbc);

      gbc.gridx = 1;
      gbc.weightx = 0;
      searchPanel.add(createSearchButton(), gbc);

      return searchPanel;
    }

    private void deletedProduct(int id) {
      ProductPanel.deletedProduct(id);
    }

    private boolean changeStatus(int id, String status) {
      return ProductPanel.changeStatus(id, status);
    }

    private JButton createSearchButton() {
      searchBt = new JButton();
      ButtonConfig.setStyleButton(
          searchBt,
          Style.FONT_PLAIN_15,
          Color.BLACK,
          Style.WORD_COLOR_WHITE,
          SwingConstants.CENTER,
          new Dimension(40, 45));
      ButtonConfig.addButtonHoverEffect(searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
      ButtonConfig.setButtonIcon("src/main/java/Icon/106236_search_icon.png", searchBt, 10);
      searchBt.addActionListener(e -> searchHandle());
      return searchBt;
    }

    private void searchHandle() {
      if (!searchTextField.getText().isBlank()) {
        searchText = searchTextField.getText().toLowerCase().trim();
        searchProduct();
      } else updateProduct();
    }

    private void reloadProducts(String status, String searchText) {
      products = (ArrayList<entity.Product>) ProductPanel.reloadProducts();
      if (!(status == null || status.isEmpty()))
        products.removeIf(product -> !(status.equals(product.getStatus())));
      if (searchText != null && !searchText.isEmpty()) {
        products.removeIf(
            product -> !product.getName().toLowerCase().contains(searchText.toLowerCase()));
      }
    }

    private void upDataProducts(DefaultTableModel tableModel, String status, String searchText) {
      reloadProducts(status, searchText);
      ProductPanel.upDataProducts(products, tableModel);
    }

    private void updateProduct() {
      searchText = null;
      searchTextField.setText("");
      upDataProducts(modelInventory, null, searchText);
      upDataProducts(modelImport, Product.IN_STOCK, searchText);
      upDataProducts(modelExport, Product.AVAILABLE, searchText);
    }

    private void searchProduct() {
      upDataProducts(modelInventory, null, searchText);
      upDataProducts(modelImport, Product.IN_STOCK, searchText);
      upDataProducts(modelExport, Product.AVAILABLE, searchText);
    }
  }

  private class AccManagementPanel extends JPanel {
    private final String[] accountColumnNames = {
      "Serial Number",
      "ManagerID",
      "Fullname",
      "Address",
      "Birth Day",
      "Phone Number",
      "AccountID",
      " User Name",
      "Password",
      "Email",
      "Account creation date",
      "Avatar"
    };
    private final int informationPanel = 0;
    private final int addOrModify = 1;
    private JTable tableAccManager;
    private DefaultTableModel modelAccManager;
    private JScrollPane scrollPaneAccManager;
    private JTabbedPane tabbedPaneAccManager;
    private ToolPanel toolPanel = new ToolPanel();
    private TableCustomerPanel tableAccManagerPanel = new TableCustomerPanel();

    private JButton addAccBt,
        modifyAccBt,
        exportAccExcelBt,
        searchAccBt,
        reloadAccBt,
        blockManagerBt;
    private JTextField accManagerField;

    private JPanel searchPanel, applicationPanel, mainPanel;
    private JTextField txtFullName, txtAddress, txtBirthday, txtPhoneNumber;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private Date selectedDate;
    private JLabel label;
    private String contextPath = "";
    private int modifyId = -1;
    private static boolean btnModifyStutus = false;
    private static TableStatus tableStatus = TableStatus.NONE;
    private static ArrayList<Manager> managerInfors = new ArrayList<>();

    public AccManagementPanel() {
      setLayout(new BorderLayout());
      toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
      add(toolPanel, BorderLayout.NORTH);
      add(tableAccManagerPanel, BorderLayout.CENTER);
    }

    private Manager getManager() {
      Manager manager = new Manager();
      try {
        manager.setFullName(txtFullName.getText());
        manager.setAddress(txtAddress.getText());

        manager.setPhone(txtPhoneNumber.getText());
        manager.setDob(selectedDate.toLocalDate());

      } catch (NullPointerException nullPointerException) {
        System.out.println(nullPointerException.toString());
      }
      return manager;
    }

    private boolean verifier() {
      return txtFullName.getInputVerifier().verify(txtFullName)
          && txtAddress.getInputVerifier().verify(txtAddress)
          && txtBirthday.getInputVerifier().verify(txtBirthday)
          && txtPhoneNumber.getInputVerifier().verify(txtPhoneNumber)
          && usernameField.getInputVerifier().verify(usernameField)
          && passwordField.getInputVerifier().verify(passwordField)
          && emailField.getInputVerifier().verify(emailField);
    }

    private void removeInfor() {
      txtFullName.setText("");
      txtAddress.setText("");
      txtBirthday.setText("");
      txtPhoneNumber.setText("");
      usernameField.setText("");
      passwordField.setText("");
      emailField.setText("");
      label.setIcon(null);
      label.setText("Drop your image here");
      btnModifyStutus = false;
      addAccBt.setEnabled(true);
    }

    private void setVisiblePanel(int panel) {
      tabbedPaneAccManager.setSelectedIndex(panel);
    }

    private void setDataToModify(Manager manager) {
      txtFullName.setText(manager.getFullName());
      txtAddress.setText(manager.getAddress());
      txtBirthday.setText(manager.getDob().toString());
      txtPhoneNumber.setText(manager.getPhone());

      emailField.setText(manager.getEmail());
      contextPath = manager.getAvatarImg();
      try {
        Path targetPath = Paths.get(contextPath);
        ImageIcon avatarIcon = new ImageIcon(targetPath.toString());
        Image scaledImage = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));
        label.setText("");

      } catch (NullPointerException npe) {
        npe.printStackTrace();
      }
    }

    private class ToolPanel extends JPanel {
      public ToolPanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);
        addAccBt = new JButton("Add");
        ButtonConfig.addButtonHoverEffect(
            addAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            addAccBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/database-add-icon.png", addAccBt, 35);

        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.addKey, addAccBt);

        addAccBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                setVisiblePanel(addOrModify);

                tableStatus = TableStatus.ADD;
                try {
                  if (!verifier()) {
                    ToastNotification.showToast("Verifier False ", 2500, 50, -1, -1);
                    return;
                  }
                  if (true) {
                    Object[] options = {"Push image", "No avata", "Cancel"};
                    int status =
                        JOptionPane.showOptionDialog(
                            null,
                            "You haven't uploaded an image!",
                            "Warning",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            options,
                            options[0]);

                    switch (status) {
                      case (0) -> {
                        return;
                      }

                      case (1) -> {}

                      case (2) -> {
                        return;
                      }
                    }
                  }


                  removeInfor();
                  ToastNotification.showToast("The image has been saved!! chuaw code", 2500, 50, -1, -1);
                } catch (Exception exception) {
                  ToastNotification.showToast("Please, upload your image again!", 2500, 50, -1, -1);
                }
              }
            });

        modifyAccBt = new JButton("Modify");
        ButtonConfig.addButtonHoverEffect(
            modifyAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            modifyAccBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/modify.png", modifyAccBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.modifyKey, modifyAccBt);

        modifyAccBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAccManager.getSelectedRow();

                if (btnModifyStutus == false && selectedRow != -1) {

                  tableStatus = TableStatus.MODIFY;

                  int columnIndex = 0;

                  modifyId = (int) tableAccManager.getValueAt(selectedRow, columnIndex) - 1;
                  setDataToModify(managerInfors.get(modifyId));
                  setVisiblePanel(addOrModify);
                  addAccBt.setEnabled(false);
                  btnModifyStutus = true;
                } else {


                  btnModifyStutus = false;
                  reload();
                  removeInfor();
                  ToastNotification.showToast(
                      "Your information has been updated successfully. chuaw code", 2500, 50, -1, -1);

                  addAccBt.setEnabled(true);
                }
              }
            });

        blockManagerBt = new JButton("Block");
        ButtonConfig.addButtonHoverEffect(
            blockManagerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            blockManagerBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/block_User.png", blockManagerBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.blockUserKey, blockManagerBt);
        blockManagerBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAccManager.getSelectedRow();
                int columnIndex = 6;
                int fullnameIndex = 2;
                if (selectedRow != -1) {
                  Object value = tableAccManager.getValueAt(selectedRow, columnIndex);
                  String name = (String) tableAccManager.getValueAt(selectedRow, fullnameIndex);
                  boolean blocked = !name.contains("*");
                  int id = Integer.parseInt(value.toString());

                  reload();
                  ToastNotification.showToast(
                      name + (blocked ? " is blocked !!! . chuaw code " : " is unblocked !!! chua code"), 2500, 50, -1, -1);
                }
              }
            });

        exportAccExcelBt = new JButton("Export");
        ButtonConfig.addButtonHoverEffect(
            exportAccExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            exportAccExcelBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon(
            "src/main/java/Icon/icons8-file-excel-32.png", exportAccExcelBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.exportExcelKey, exportAccExcelBt);
        exportAccExcelBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                String fileName =
                    JOptionPane.showInputDialog(
                        null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                if (fileName != null && !fileName.trim().isEmpty()) {
                  reload();
                  ExcelConfig.writeManagersToExcel(managerInfors, fileName);
                  ToastNotification.showToast(fileName + " is created!", 2500, 50, -1, -1);
                } else {
                  ToastNotification.showToast("Failed to export Excel file!", 2500, 50, -1, -1);
                }
              }
            });
        accManagerField =
            TextFieldConfig.createTextField(
                "Search by Name",
                new Font("Arial", Font.PLAIN, 24),
                Color.GRAY,
                new Dimension(280, 50));
        accManagerField.addActionListener(e -> searchAccBt.doClick());

        searchAccBt = new JButton();
        ButtonConfig.addButtonHoverEffect(
            searchAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            searchAccBt,
            Style.FONT_PLAIN_13,
            Color.BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(40, 45));
        ButtonConfig.setButtonIcon("src/main/java/Icon/106236_search_icon.png", searchAccBt, 10);
        searchAccBt.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                reload();
                String find = accManagerField.getText().toLowerCase().trim();
                System.out.println(find);
//                ArrayList<ManagerInforDTO> managerInforDTOS =
//                    (ArrayList<ManagerInforDTO>)
//                        managerInfors.stream()
//                            .filter(p -> p.getFullnameLowerCase().contains(find))
//                            .collect(Collectors.toList());
//                upDataTable(managerInforDTOS, modelAccManager, tableAccManager);
              }
            });

        reloadAccBt = new JButton("Reload");
        ButtonConfig.addButtonHoverEffect(
            reloadAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
        ButtonConfig.setStyleButton(
            reloadAccBt,
            Style.FONT_PLAIN_13,
            Style.WORD_COLOR_BLACK,
            Style.WORD_COLOR_WHITE,
            SwingConstants.CENTER,
            new Dimension(80, 80));
        ButtonConfig.setButtonIcon("src/main/java/Icon/reload_icon.png", reloadAccBt, 35);
        KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.reloadKey, reloadAccBt);
        reloadAccBt.addActionListener(
            e -> {
              reload();
              accManagerField.setText("");
            });
        searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(accManagerField);
        searchPanel.add(searchAccBt);
        searchPanel.setBackground(Style.WORD_COLOR_WHITE);

        applicationPanel = new JPanel(new FlowLayout());
        applicationPanel.add(addAccBt);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(modifyAccBt);
        applicationPanel.add(blockManagerBt);

        JLabel none = new JLabel("");
        none.setFont(Style.FONT_PLAIN_13);
        none.setHorizontalAlignment(SwingConstants.CENTER);
        none.setVerticalAlignment(SwingConstants.CENTER);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(exportAccExcelBt);

        applicationPanel.add(ButtonConfig.createVerticalSeparator());
        applicationPanel.add(reloadAccBt);
        applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Style.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(applicationPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(searchPanel, gbc);
        mainPanel.setBackground(Style.WORD_COLOR_WHITE);

        add(mainPanel);
      }
    }

    private class TableCustomerPanel extends JPanel {
      AddManager addManager;

      public TableCustomerPanel() {
        setLayout(new BorderLayout());
        setBackground(Style.WORD_COLOR_WHITE);
        tableAccManager = createTable(modelAccManager, accountColumnNames);

        tableAccManager
            .getColumnModel()
            .getColumn(accountColumnNames.length - 1)
            .setCellRenderer(new ImageInJTable.ImageRenderer());
        tableAccManager.setRowHeight(100);
        resizeColumnWidth(tableAccManager, 219);
        modelAccManager = (DefaultTableModel) tableAccManager.getModel();

        reload();

        scrollPaneAccManager = new JScrollPane(tableAccManager);
        tabbedPaneAccManager =
            createTabbedPane(scrollPaneAccManager, "Information", Style.FONT_BOLD_16);
        addManager = new AddManager();
        tabbedPaneAccManager.add("Modify Manager", addManager);

        add(tabbedPaneAccManager, BorderLayout.CENTER);
      }
    }

    private class AddManager extends JPanel {
      ChangeInfo changeInfo;
      Avatar avatar;

      AddManager() {
        setLayout(new GridLayout(2, 1));
        changeInfo = new ChangeInfo();
        avatar = new Avatar();
        add(changeInfo);
        add(avatar);
      }

      public static void addFocusListenerForTextField(JTextField textField) {
        textField.addFocusListener(
            new FocusListener() {
              @Override
              public void focusGained(FocusEvent e) {
                textField.setBorder(
                    BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
              }

              @Override
              public void focusLost(FocusEvent e) {
                textField.setBorder(
                    BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
              }
            });
      }

      private class ChangeInfo extends JPanel {
        private LeftPn rightPn;
        private RightPn leftPn;

        public ChangeInfo() {
          setLayout(new GridLayout(1, 2));
          rightPn = new LeftPn();
          leftPn = new RightPn();
          add(rightPn);
          add(leftPn);
        }

        private class LeftPn extends JPanel {
          LeftPn() {
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 150));
            Border border =
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3),
                    "Personal Information",
                    TitledBorder.LEFT,
                    TitledBorder.TOP,
                    Style.FONT_BOLD_18,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            setBorder(border);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            JLabel lblFullName =
                LabelConfig.createLabel(
                    "Full Name", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
            txtFullName =
                TextFieldConfig.createStyledTextField(
                    Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(285, 35));
            txtFullName.setInputVerifier(new NotNullVerifier());
            addFocusListenerForTextField(txtFullName);

            JLabel lblAddress =
                LabelConfig.createLabel(
                    "Address", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
            txtAddress =
                TextFieldConfig.createStyledTextField(
                    Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(285, 35));
            txtAddress.setInputVerifier(new NotNullVerifier());
            addFocusListenerForTextField(txtAddress);

            JLabel lblBirthday =
                LabelConfig.createLabel(
                    "Birthday", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
            txtBirthday =
                TextFieldConfig.createStyledTextField(
                    Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(250, 35));
            txtBirthday.setInputVerifier(new BirthDayVerifier());
            addFocusListenerForTextField(txtBirthday);
            txtBirthday.setEditable(false);
            JButton btnCalendar = new JButton();
            btnCalendar.setPreferredSize(new Dimension(35, 35));
            btnCalendar.setFocusable(false);
            btnCalendar.setBackground(Color.WHITE);
            btnCalendar.setIcon(new ImageIcon("src/main/java/Icon/calendarIcon.png"));

            JDialog calendarDialog = new JDialog((Frame) null, "Select Date", true);
            calendarDialog.setSize(400, 400);
            calendarDialog.setLayout(new BorderLayout());
            calendarDialog.setLocation(700, 200);
            JCalendar calendar = new JCalendar();
            calendar.setBackground(Color.WHITE);
            calendar.setFont(Style.FONT_BOLD_15);
            calendar.setMaxSelectableDate(new java.util.Date());
            btnCalendar.addActionListener(e -> calendarDialog.setVisible(true));
            calendarDialog.add(calendar, BorderLayout.CENTER);

            CustomButton selectBt =
                ButtonConfig.createCustomButton(
                    "Select",
                    Style.FONT_BOLD_18,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    Color.white,
                    Style.LIGHT_BlUE,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                    1,
                    5,
                    SwingConstants.CENTER,
                    new Dimension(300, 35));

            calendarDialog.add(selectBt, BorderLayout.SOUTH);

            selectBt.addActionListener(
                new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    selectedDate = new Date(calendar.getDate().getTime());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    txtBirthday.setText(dateFormat.format(selectedDate));
                    calendarDialog.setVisible(false);
                  }
                });

            JLabel lblPhoneNumber =
                LabelConfig.createLabel(
                    "Phone Number", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
            txtPhoneNumber =
                TextFieldConfig.createStyledTextField(
                    Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(285, 35));
            txtPhoneNumber.setInputVerifier(new PhoneNumberVerifier());
            addFocusListenerForTextField(txtPhoneNumber);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(lblFullName, gbc);

            gbc.gridx = 1;
            add(txtFullName, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(lblAddress, gbc);

            gbc.gridx = 1;
            add(txtAddress, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            add(lblBirthday, gbc);

            JPanel birthdayPanel = new JPanel(new BorderLayout());
            birthdayPanel.setBackground(Color.WHITE);
            birthdayPanel.add(txtBirthday, BorderLayout.CENTER);
            birthdayPanel.add(btnCalendar, BorderLayout.EAST);

            gbc.gridx = 1;
            add(birthdayPanel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            add(lblPhoneNumber, gbc);

            gbc.gridx = 1;
            add(txtPhoneNumber, gbc);
          }
        }

        class RightPn extends JPanel {

          RightPn() {
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 150));
            Border border =
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3),
                    "Account Information",
                    TitledBorder.LEFT,
                    TitledBorder.TOP,
                    Style.FONT_BOLD_18,
                    Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            setBorder(border);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(
                LabelConfig.createLabel(
                    "User Name", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                gbc);

            gbc.gridy = 1;
            add(
                LabelConfig.createLabel(
                    "Password", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                gbc);

            gbc.gridy = 2;
            add(
                LabelConfig.createLabel(
                    "Email", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            usernameField =
                TextFieldConfig.createStyledTextField(
                    Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(295, 35));
            usernameField.setInputVerifier(new UserNameAccountVerifier());
            addFocusListenerForTextField(usernameField);
            add(usernameField, gbc);

            gbc.gridy = 1;
            JPanel passwdPanel = new JPanel(new BorderLayout());
            passwordField =
                PasswordFieldConfig.createStyledJPasswordField(
                    Style.FONT_PLAIN_16, Style.MEDIUM_BLUE, new Dimension(250, 35));
            passwordField.setInputVerifier(new NotNullVerifier());
            passwordField.addFocusListener(
                new FocusListener() {
                  @Override
                  public void focusGained(FocusEvent e) {
                    passwordField.setBorder(
                        BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
                  }

                  @Override
                  public void focusLost(FocusEvent e) {
                    passwordField.setBorder(
                        BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                  }
                });

            JButton togglePasswordButton = ButtonConfig.createShowPasswdButton(passwordField);
            togglePasswordButton.setPreferredSize(new Dimension(45, 35));
            passwdPanel.setBackground(Color.WHITE);
            passwdPanel.add(passwordField, BorderLayout.CENTER);
            passwdPanel.add(togglePasswordButton, BorderLayout.EAST);
            add(passwdPanel, gbc);

            gbc.gridy = 2;
            emailField =
                TextFieldConfig.createStyledTextField(
                    Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(295, 35));
            emailField.setInputVerifier(new EmailVerifier());
            addFocusListenerForTextField(emailField);
            add(emailField, gbc);
          }
        }
      }

      private class Avatar extends JPanel {
        CustomButton importImage, undoBt, cancelBt;

        Avatar() {
          setLayout(new BorderLayout());
          setBackground(Color.WHITE);
          label = new JLabel("Drop your image here", SwingConstants.CENTER);
          label.setBackground(Color.WHITE);
          Border dashedBorder =
              BorderFactory.createDashedBorder(Style.CONFIRM_BUTTON_COLOR_GREEN, 2, 10, 20, true);
          Border margin = BorderFactory.createEmptyBorder(5, 10, 5, 10);
          Border compoundBorder = BorderFactory.createCompoundBorder(margin, dashedBorder);
          label.setBorder(compoundBorder);
          label.setPreferredSize(new Dimension(400, 300));

          JPanel uploadImagePn = new JPanel();
          uploadImagePn.setBackground(Color.WHITE);
          importImage = new CustomButton("Upload Image from your computer");
          importImage.setDrawBorder(false);
          importImage.setPreferredSize(new Dimension(300, 40));
          importImage.addActionListener(
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                  JFileChooser fileChooser = new JFileChooser();
                  fileChooser.setDialogTitle("Chọn hình ảnh");

                  fileChooser.setFileFilter(
                      new FileNameExtensionFilter("Hình ảnh (JPG, PNG, GIF)", "jpg", "png", "gif"));

                  int result = fileChooser.showOpenDialog(null);
                  if (result == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = fileChooser.getSelectedFile();
                    contextPath = selectedFile.getAbsolutePath();

                    ImageIcon imageIcon = new ImageIcon(contextPath);
                    Image scaledImage =
                        imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage));
                  }
                }
              });

          label.setTransferHandler(new ImageTransferHandler());
          label.setInputVerifier(new NotNullVerifier());

          undoBt = new CustomButton("Undo");
          undoBt.setPreferredSize(new Dimension(100, 40));
          undoBt.setDrawBorder(false);
          undoBt.addActionListener(
              e -> {
                if (btnModifyStutus == true) setDataToModify(managerInfors.get(modifyId));
              });

          cancelBt = new CustomButton("cancel");
          cancelBt.setPreferredSize(new Dimension(100, 40));
          cancelBt.setDrawBorder(false);
          cancelBt.addActionListener(
              e -> {
                removeInfor();
              });

          uploadImagePn.add(undoBt);
          uploadImagePn.add(importImage);
          uploadImagePn.add(cancelBt);

          add(label, BorderLayout.CENTER);
          add(uploadImagePn, BorderLayout.SOUTH);
        }

        private class ImageTransferHandler extends TransferHandler {
          @Override
          public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
          }

          @Override
          public boolean importData(TransferSupport support) {
            if (!canImport(support)) return false;

            try {
              Transferable transferable = support.getTransferable();
              List<File> files =
                  (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
              if (!files.isEmpty()) {
                File file = files.get(0);
                String fileName = file.getName();
                String nameImg = String.valueOf(files.hashCode());
                contextPath = "src/main/java/img/" + nameImg + fileName;
                Path targetPath = Paths.get(contextPath);

                Files.createDirectories(targetPath.getParent());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                ImageIcon avatarIcon = new ImageIcon(targetPath.toString());
                Image scaledImage =
                    avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                label.setText("");

                return true;
              }
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            return false;
          }
        }
      }
    }

    private void reload() {
//      managerInfors = managerController.getManagerInforDTO();
//      upDataTable(managerInfors, modelAccManager, tableAccManager);
    }

    public static void upDataTable(){
//        ArrayList<ManagerInforDTO> managerInforDTOS,
//        DefaultTableModel modelCustomerTable,
//        JTable tableCustomer) {
//      Object[][] rowData = ManagerInforDTO.getDataOnTable(managerInforDTOS);
//      ProductPanel.TablePanel.removeDataTable(modelCustomerTable);
//      for (int i = 0; i < rowData.length; i++) {
//        modelCustomerTable.addRow(rowData[i]);
//      }
    }
  }

  private class NotificationPanel extends JPanel {
//    private JScrollPane scrollPane;
//    private Map<Customer, List<Order>> customerOrders;
//    private Timer timer;
//
//    public NotificationPanel() {
//      setLayout(new BorderLayout());
//
//      notificationContainer = new JPanel();
//      notificationContainer.setBackground(Color.WHITE);
//      notificationContainer.setLayout(new BoxLayout(notificationContainer, BoxLayout.Y_AXIS));
//
//      addAllNotification();
//
//      scrollPane = new JScrollPane(notificationContainer);
//      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//      setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Style.LIGHT_BlUE);
//      add(scrollPane, BorderLayout.CENTER);
//
//      startTimer();
//    }
//
//    public void reloadNotification() {
//      customerOrders = getAllCustomerOrder();
//      notificationContainer.removeAll();
//      addAllNotification();
//      notificationContainer.revalidate();
//      notificationContainer.repaint();
//    }
//
//    private void startTimer() {
//      timer = new Timer(30000, e -> reloadNotification());
//      timer.start();
//    }
//
//    private void addAllNotification() {
//      customerOrders = getAllCustomerOrder();
//
//      JPanel main = new JPanel(new GridBagLayout());
//      main.setBackground(Color.WHITE);
//      GridBagConstraints gbc = new GridBagConstraints();
//      gbc.insets = new Insets(5, 15, 5, 15);
//      gbc.anchor = GridBagConstraints.WEST;
//      int x = 0, y = 0;
////      if (!customerOrders.isEmpty()) {
////        for (Map.Entry<Customer, List<CustomerOrderDTO>> entry : customerOrders.entrySet()) {
////          Customer customer = entry.getKey();
////
////          // Single grouping loop: group orders by date and ID together
////          Map<Date, Map<Integer, List<CustomerOrderDTO>>> groupedOrders =
////              new TreeMap<>(Collections.reverseOrder());
////          for (CustomerOrderDTO order : entry.getValue()) {
////            groupedOrders
////                .computeIfAbsent(
////                    (Date) order.getOrderDate(), date -> new TreeMap<>(Collections.reverseOrder()))
////                .computeIfAbsent(order.getOrderId(), id -> new ArrayList<>())
////                .add(order);
////          }
////
////          // Process grouped orders directly
////          for (Map.Entry<Date, Map<Integer, List<CustomerOrderDTO>>> dateEntry :
////              groupedOrders.entrySet()) {
////            Date orderDate = dateEntry.getKey();
////
////            for (Map.Entry<Integer, List<CustomerOrderDTO>> orderEntry :
////                dateEntry.getValue().entrySet()) {
////              int orderId = orderEntry.getKey();
////              List<CustomerOrderDTO> orders = orderEntry.getValue();
////
////              double totalCost = orders.stream().mapToDouble(CustomerOrderDTO::totalCost).sum();
////
////              StringBuilder notificationText = new StringBuilder();
////              notificationText.append(
////                  String.format(
//                      "New Orders from %s\nOrder ID: %d", customer.getFullName(), orderId));
////              for (CustomerOrderDTO order : orders) {
////                notificationText.append(
////                    String.format(
////                        "\nOrder Date: %s\nShipping Address: %s\nOrder Status: %s\nProduct Name: %s\nQuantity: %d\n-----------------------------\n",
////                        order.getOrderDate().toString(),
////                        order.getShipAddress(),
////                        order.getStatusItem(),
////                        order.getProductName(),
////                        order.getQuantity()));
////              }
////              notificationText.append(
////                  String.format(
////                      "\nTotal: %s VNĐ",
////                      NumberFormat.getInstance(new Locale("vi", "VN")).format(totalCost)));
////
////              CircularImage avatar = new CircularImage(customer.getAvataImg(), 80, 80, false);
////              JLabel timeLabel = new JLabel(String.format("<html>%s</html>", orderDate.toString()));
////
////              JTextArea message = createMessageArea(notificationText.toString());
////              JScrollPane scrollPane = createScrollPane(message);
//
////              gbc.gridx = x;
////              gbc.gridy = y;
////              gbc.anchor = GridBagConstraints.WEST;
////              main.add(avatar, gbc);
////
////              gbc.gridx = x;
////              gbc.gridy = ++y;
////              gbc.anchor = GridBagConstraints.EAST;
////              main.add(scrollPane, gbc);
////
////              gbc.gridx = x + 1;
////              main.add(timeLabel, gbc);
////              y++;
////            }
////          }
////        }
////      } else {
////        JPanel emptyNotificationPn = new JPanel(new BorderLayout());
////        emptyNotificationPn.setBackground(Color.WHITE);
////        emptyNotificationPn.add(
////            new JLabel(
////                createImageForProduct("src/main/java/img/no_Notification_Img.png", 500, 500)));
////        main.add(emptyNotificationPn, gbc);
////      }
////
////      notificationContainer.add(main);
////      notificationContainer.revalidate();
////      notificationContainer.repaint();
//    }
//
//    private JTextArea createMessageArea(String text) {
//      JTextArea message = new JTextArea(text);
//      message.setBackground(Color.WHITE);
//      message.setForeground(Color.BLACK);
//      message.setFont(new Font("Arial", Font.PLAIN, 16));
//      message.setBorder(
//          BorderFactory.createCompoundBorder(
//              new RoundedBorder(20, 2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE),
//              BorderFactory.createEmptyBorder(3, 3, 3, 8)));
//      message.setLineWrap(true);
//      message.setWrapStyleWord(true);
//      message.setEditable(false);
//      message.setOpaque(true);
//      message.setPreferredSize(new Dimension(600, message.getPreferredSize().height));
//      return message;
//    }
//
//    private JScrollPane createScrollPane(JTextArea message) {
//      JScrollPane scrollPane = new JScrollPane(message);
//      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//      scrollPane.setBorder(BorderFactory.createEmptyBorder());
//      return scrollPane;
//    }
//
//    private Map<Customer, List<Order>> getAllCustomerOrder() {
//      return LoginFrame.COMPUTER_SHOP.userOrderStatistics();
//    }
  }

  private class ChangeInformationPanel extends JPanel {
    JTextField emailField,
        fullNameField,
        addressField,
        phoneNumField,
        dateOfBirthField,
        createDateField;
    CircularImage avatar;
    ChangeAvatarPanel changeAvatarPanel = new ChangeAvatarPanel();
    ChangeInfo changeInfo = new ChangeInfo();
    CustomButton updateBt, cancelBt, changePassBt, changeAvaBt;

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
      changePassBt.addActionListener(e -> new ChangePasswordFrame("Manager").showVisible());

      JPanel updatePn = new JPanel(new FlowLayout(FlowLayout.CENTER));
      updatePn.add(cancelBt);
      updatePn.add(updateBt);
      updatePn.add(changePassBt);
      add(updatePn, BorderLayout.SOUTH);
      reloadData();
    }

    private class ChangeAvatarPanel extends JPanel {

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
        changeAvaBt.addActionListener(
            e -> {
              JFileChooser fileChooser = new JFileChooser();
              fileChooser.setDialogTitle("Select an Image");

              fileChooser.setAcceptAllFileFilterUsed(false);
              fileChooser.addChoosableFileFilter(
                  new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

              int result = fileChooser.showOpenDialog(null);
              if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null && isImageFile(selectedFile)) {
                  avatar.setImage(selectedFile.getAbsolutePath());
                  JOptionPane.showMessageDialog(null, "Avatar updated successfully!");
                } else {
                  JOptionPane.showMessageDialog(
                      null,
                      "Please select a valid image file!",
                      "Invalid File",
                      JOptionPane.WARNING_MESSAGE);
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

    private class ChangeInfo extends JPanel {
      private final String[] labels = {
        "Email", "Name", "Address", "Phone Number", "Date of Birth", "Create Date"
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
        editableFields[3] = phoneNumField = TextFieldConfig.createUneditableTextField(labels[3]);
        editableFields[4] = dateOfBirthField = TextFieldConfig.createUneditableTextField(labels[4]);
        editableFields[5] = createDateField = TextFieldConfig.createUneditableTextField(labels[5]);
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
        emailField.setText(CurrentUser.CURRENT_USER_V2.getEmail());
        fullNameField.setText(CurrentUser.CURRENT_USER_V2.getFullName());
        addressField.setText(CurrentUser.CURRENT_USER_V2.getAddress());
        phoneNumField.setText(CurrentUser.CURRENT_USER_V2.getPhone());
        dateOfBirthField.setText(CurrentUser.CURRENT_USER_V2.getDob().toString());
        createDateField.setText(CurrentUser.CURRENT_USER_V2.getCreatedAt().toString());
        avatar.setImage(CurrentUser.CURRENT_USER_V2.getAvatarImg());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private boolean hasNotChanged() {
      return emailField.getText().trim().equals(CurrentUser.CURRENT_USER_V2.getEmail())
          && fullNameField.getText().trim().equals(CurrentUser.CURRENT_USER_V2.getFullName())
          && addressField.getText().trim().equals(CurrentUser.CURRENT_USER_V2.getAddress())
          && phoneNumField.getText().trim().equals(CurrentUser.CURRENT_USER_V2.getPhone())
          && dateOfBirthField
              .getText()
              .trim()
              .equals(CurrentUser.CURRENT_USER_V2.getDob().toString())
          && createDateField
              .getText()
              .trim()
              .equals(CurrentUser.CURRENT_USER_V2.getCreatedAt().toString())
          && this.avatar.equals(
              new CircularImage(CurrentUser.URL, avatar.getWidth(), avatar.getHeight(), false));
    }

    private void cancelHandle() {
      if (!hasNotChanged()) {
        int response =
            JOptionPane.showConfirmDialog(
                null,
                "You have unsaved changes. Are you sure you want to cancel?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
          reloadData();
          JOptionPane.showMessageDialog(
              null,
              "Changes have been canceled.",
              "Action Canceled",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(
              null,
              "Continue editing your changes.",
              "Action Resumed",
              JOptionPane.INFORMATION_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(
            null, "No changes to cancel.", "Action Canceled", JOptionPane.INFORMATION_MESSAGE);
      }
    }

    private void updateHandle() {
      if (hasNotChanged()) {
        JOptionPane.showMessageDialog(
            this,
            "No changes detected. Please modify your information before updating.",
            "No Updates Made",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      Map<JTextField, InputVerifier[]> fieldVerifierMap = new HashMap<>();
      fieldVerifierMap.put(
          emailField, new InputVerifier[] {new NotNullVerifier(), new EmailVerifier()});
      fieldVerifierMap.put(
          fullNameField,
          new InputVerifier[] {new NotNullVerifier(), new UserNameAccountVerifier()});
      fieldVerifierMap.put(addressField, new InputVerifier[] {new NotNullVerifier()});
      fieldVerifierMap.put(
          phoneNumField, new InputVerifier[] {new NotNullVerifier(), new PhoneNumberVerifier()});
      fieldVerifierMap.put(
          dateOfBirthField, new InputVerifier[] {new NotNullVerifier(), new BirthDayVerifier()});
      fieldVerifierMap.put(createDateField, new InputVerifier[] {new NotNullVerifier()});

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
          JOptionPane.INFORMATION_MESSAGE);

      performUpdate();
    }

    private void performUpdate() {
//      Account account = accountController.findById(CurrentUser.CURRENT_USER_V2.getAccountId());
//      account.setAvataImg(avatar.getImagePath());
//      account.setEmail(emailField.getText().trim());
//      account.setCreateDate(Date.valueOf(createDateField.getText().trim()));
//      Manager manager = managerController.findById(CurrentUser.CURRENT_USER_V2.getManagerId());
//      manager.setFullName(fullNameField.getText().trim());
//      manager.setAddress(addressField.getText().trim());
//      manager.setBirthDay(Date.valueOf(dateOfBirthField.getText().trim()));
//      manager.setPhoneNumber(phoneNumField.getText().trim());

//      accountController.update(account);
//      managerController.update(manager);

      ToastNotification.showToast(
          "Your information has been successfully updated.", 2500, 50, -1, -1);
    }
  }

  private <M> void exportExcel(List<M> dataList, String[] headers) {
    String fileName = JOptionPane.showInputDialog("Enter the name of the Excel file:");
    if (fileName != null && !fileName.trim().isEmpty()) {
      fileName = fileName.trim().endsWith(".xlsx") ? fileName.trim() : fileName.trim() + ".xlsx";
//      ExcelConfig.exportToExcel(dataList, fileName, headers);
      JOptionPane.showMessageDialog(null, "Exported to  bug" + fileName);
    } else {
      JOptionPane.showMessageDialog(
          null, "File name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

//  public void reloadNotification() {
//    this.notificationPanel.reloadNotification();
//  }

  private static void setColorScrollPane(
      JScrollPane scrollPane, Color thumbColor, Color trackColor) {
    setColorScrollBar(scrollPane.getVerticalScrollBar(), thumbColor, trackColor);
    setColorScrollBar(scrollPane.getHorizontalScrollBar(), thumbColor, trackColor);
  }

  private static void setColorScrollBar(
      JScrollBar scrollBar, Color scrollBarColor, Color trackBackGroundColor) {
    scrollBar.setUI(
        new BasicScrollBarUI() {
          @Override
          protected void configureScrollBarColors() {
            this.thumbColor = scrollBarColor;
            this.trackColor = trackBackGroundColor;
          }
        });
  }

  private JTable createTable(DefaultTableModel model, String[] columnNames) {

    model =
        new DefaultTableModel(columnNames, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

    JTable table = new JTable(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setRowHeight(40);
    table.setFont(Style.FONT_PLAIN_16);
    resizeColumnWidth(table, 200);

    JTableHeader header = table.getTableHeader();
    header.setDefaultRenderer(
        new TableCellRenderer() {
          public Component getTableCellRendererComponent(
              JTable table,
              Object value,
              boolean isSelected,
              boolean hasFocus,
              int row,
              int column) {
            JLabel label = new JLabel(value.toString());
            label.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
            label.setForeground(Color.BLACK);
            label.setFont(Style.FONT_BOLD_16);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            label.setOpaque(true);
            return label;
          }
        });
    header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));
    header.setReorderingAllowed(false);
    header.setResizingAllowed(true);

    return table;
  }

  private void resizeColumnWidth(JTable table, int width) {
    for (int i = 0; i < table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setPreferredWidth(width);
      table.getColumnModel().getColumn(0).setPreferredWidth(100);
      table.getColumnModel().getColumn(1).setPreferredWidth(120);
      table.getColumnModel().getColumn(2).setPreferredWidth(300);
    }
  }

  private JTabbedPane createTabbedPane(JScrollPane scrollPane, String title, Font font) {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setUI(
        new BasicTabbedPaneUI() {
          @Override
          protected void paintTabBackground(
              Graphics g,
              int tabPlacement,
              int tabIndex,
              int x,
              int y,
              int w,
              int h,
              boolean isSelected) {
            if (isSelected) {
              g.setColor(Style.MENU_BUTTON_COLOR_GREEN);
            } else {
              g.setColor(new Color(227, 224, 224));
            }
            g.fillRect(x, y, w, h);
          }
        });
    tabbedPane.setFont(font);
    tabbedPane.addTab(title, scrollPane);
    tabbedPane.setFocusable(false);
    return tabbedPane;
  }

}
