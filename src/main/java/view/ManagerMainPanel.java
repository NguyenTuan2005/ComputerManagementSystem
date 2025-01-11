package view;

import static view.CustomerMainPanel.createImageForProduct;
import com.toedter.calendar.JCalendar;
import config.*;
import entity.*;
import enums.DisplayProductType;
import enums.OrderType;
import enums.TableStatus;
import static enums.TableStatus.*;
import static view.CustomerMainPanel.formatCurrency;

import lombok.SneakyThrows;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import verifier.*;
import view.otherComponent.*;
import view.overrideComponent.CircularImage;
import view.overrideComponent.CustomButton;
import view.overrideComponent.RoundedBorder;
import view.overrideComponent.ToastNotification;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.Image;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerMainPanel extends JPanel {
  private CardLayout cardLayout = new CardLayout();
  private DashBoardPanel dashBoardPanel;
  private ProductPanel productPanel = new ProductPanel();
  private SupplierPanel supplierPanel = new SupplierPanel();
  private CustomerPanel customerPanel = new CustomerPanel();
  private OrderPanel orderPanel = new OrderPanel();
  private InventoryPanel inventoryPanel = new InventoryPanel();
  private ManagerPanel managerPanel = new ManagerPanel();
  private NotificationPanel notificationPanel = new NotificationPanel();
  private ChangeInformationPanel changeInformationPanel = new ChangeInformationPanel();
  private JPanel notificationContainer;
  //constraint
  public static final String DASHBOARD_CONSTRAINT = "dashboard";
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
  private static  String [] columStatisticsProduct ={
          "Serial Number",
          "Product Name",
          "Sold Quantity",
          "Quantity In Stock"
  };
  private static final String[] columnNamesSUPPLIER = {
    "Serial Number",
    "Supplier Name",
    "Email",
    "Phone number",
    "Address",
    "Contract Start Date"
  };
  private static final String[] columnNamesQuantityOfSupplier ={"Serial Number","Company name :", "Quantity :"};
  private static final String[] columnNamesCUSTOMER = {
    "Customer ID:", "Customer Name:", "Phone Number:", "Email:", "Address:", "Date of Birth:"
  };

  public static DecimalFormat currencyFormatter = new DecimalFormat("#,###");
  public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public ManagerMainPanel() {

    dashBoardPanel = new DashBoardPanel();
    setLayout(cardLayout);
    add(dashBoardPanel, DASHBOARD_CONSTRAINT);
    add(productPanel, PRODUCT_CONSTRAINT);
    add(supplierPanel, SUPPLIER_CONSTRAINT);
    add(customerPanel, CUSTOMER_CONSTRAINT);
    add(orderPanel, ORDER_CONSTRAINT);
    add(inventoryPanel, INVENTORY_CONSTRAINT);
    add(managerPanel, ACC_MANAGEMENT_CONSTRAINT);
    add(notificationPanel, NOTIFICATION_CONSTRAINT);
    add(changeInformationPanel, CHANGE_INFORMATION_CONSTRAINT);
    cardLayout.show(this, DASHBOARD_CONSTRAINT);
  }

  public void showPanel(String panelName) {
    cardLayout.show(this, panelName);
  }

  private class DashBoardPanel extends JPanel {
    private CustomButton productsBt, customersBt, suppliersBt, ordersBt, managersBt;
    private CenterPanel centerPanel = new CenterPanel();
    public DashBoardPanel() {
      setLayout(new BorderLayout());
      setBackground(Color.WHITE);

      JPanel statisticsBarPn = new JPanel(new FlowLayout(FlowLayout.CENTER));
      statisticsBarPn.setBackground(Color.WHITE);

      productsBt = ButtonConfig.createCustomButton("<html><div style='text-align: center;'>" +
              LoginFrame.COMPUTER_SHOP.getTotalProduct() +
              "<br>Products</div></html>", Style.FONT_BOLD_30,Color.white,Style.BUTTON_GREEN_DASHBOARD,Style.LIGHT_GREEN,20,SwingConstants.CENTER,new Dimension(250,100));
      productsBt.addActionListener(e -> {
          showPanel(ManagerMainPanel.PRODUCT_CONSTRAINT);

      });
      statisticsBarPn.add(productsBt);
      customersBt = ButtonConfig.createCustomButton("<html><div style='text-align: center;'>" +
              LoginFrame.COMPUTER_SHOP.getTotalCustomer() +
              "<br>Customers</div></html>", Style.FONT_BOLD_30,Color.white,Style.BUTTON_YELLOW_DASHBOARD,Style.LIGHT_YELLOW,20,SwingConstants.CENTER,new Dimension(250,100));
      customersBt.addActionListener(e -> {
          showPanel(ManagerMainPanel.CUSTOMER_CONSTRAINT);
        });
      statisticsBarPn.add(customersBt);
      suppliersBt = ButtonConfig.createCustomButton("<html><div style='text-align: center;'>" +
              LoginFrame.COMPUTER_SHOP.getTotalSupplier() +
              "<br>Suppliers</div></html>", Style.FONT_BOLD_30,Color.white,Style.MENU_BUTTON_COLOR,Style.LIGHT_BlUE,20,SwingConstants.CENTER,new Dimension(250,100));
      suppliersBt.addActionListener(e -> {
          showPanel(ManagerMainPanel.SUPPLIER_CONSTRAINT);

      });
      statisticsBarPn.add(suppliersBt);

      managersBt = ButtonConfig.createCustomButton("<html><div style='text-align: center;'>" +
                LoginFrame.COMPUTER_SHOP.getTotalManager() +
                "<br>Managers</div></html>", Style.FONT_BOLD_30,Color.white,Style.DELETE_BUTTON_COLOR_RED,Style.LIGHT_RED,20,SwingConstants.CENTER,new Dimension(250,100));
      managersBt.addActionListener(e -> {
            showPanel(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
        });
      statisticsBarPn.add(managersBt);

      add(statisticsBarPn, BorderLayout.NORTH);
      add(centerPanel,BorderLayout.CENTER);
    }

    public class CenterPanel extends JPanel{
        private JLabel welcomeLabel;
        private CardLayout dashBCardLayout = new CardLayout();
        private ChartContainerPanel chartContainerPanel = new ChartContainerPanel();
        private RevenueChartPanel revenueChartPanel;
        CenterPanel(){
            setLayout(dashBCardLayout);

            Timer timer = new Timer(2500, e -> dashBCardLayout.show(CenterPanel.this, "statisticsTable"));
            timer.setRepeats(false);

            JPanel welcomePn = new JPanel(new BorderLayout());
            welcomePn.setBackground(Color.WHITE);
            ImageIcon welcomeImg = new ImageIcon("src/main/java/img/welcomeImage.png");
            welcomeLabel = new JLabel();
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
            welcomeLabel.setIcon(welcomeImg);
            welcomePn.add(welcomeLabel, BorderLayout.CENTER);
            welcomePn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    timer.stop();
                    dashBCardLayout.show(CenterPanel.this, "statisticsTable");
                }
            });
            add(welcomePn, "welcome");
            add(chartContainerPanel,"statisticsTable");
            dashBCardLayout.show(this, "welcome");

            timer.start();
        }
        class ChartContainerPanel extends JPanel{
            ChartContainerPanel(){
                setLayout(new BorderLayout());

                revenueChartPanel = new RevenueChartPanel();
                add(revenueChartPanel, BorderLayout.CENTER);
                add(new RightPn(), BorderLayout.EAST);
            }
        }
        // Revenue Chart
        class RevenueChartPanel extends JPanel {
            private DefaultCategoryDataset revenueData = new DefaultCategoryDataset();
            private JFreeChart revenueChart;
            private CategoryPlot revenuePlot;
            private ChartPanel revenueChartPanel;
            private JComboBox<Integer> yearComboBox;
            private ArrayList<Integer> years = new ArrayList<>();
            private Map<String, Double> data = LoginFrame.COMPUTER_SHOP.analyzeRevenueByMonth(LocalDate.now().getYear());

            RevenueChartPanel() {
                setLayout(new BorderLayout());

                JPanel sortBarPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                sortBarPn.setBackground(Color.WHITE);
                JLabel yearLabel = LabelConfig.createLabel("Year", Style.FONT_BOLD_16, Color.BLACK, SwingConstants.CENTER);
                years.add(2025);
                years.add(2024);
                years.add(2023);

                yearComboBox = new JComboBox<>();
                yearComboBox.setFocusable(false);
                yearComboBox.setBorder(BorderFactory.createLineBorder(Style.MENU_BUTTON_COLOR,2));
                updateYearComboBox();
                yearComboBox.addActionListener(e -> {
                    Integer selectedYear = (Integer) yearComboBox.getSelectedItem();
                    if (selectedYear != null) {
                        updateData(selectedYear);
                        revenueChartPanel.repaint();
                    }
                });

                updateData(LocalDate.now().getYear());

                revenueChart = createChart(revenueData);
                revenueChartPanel = new ChartPanel(revenueChart);

                add(revenueChartPanel, BorderLayout.CENTER);
                sortBarPn.add(yearLabel);
                sortBarPn.add(yearComboBox);
                add(sortBarPn, BorderLayout.NORTH);
            }

            private void updateData(int year) {
                revenueData.clear();

                data = LoginFrame.COMPUTER_SHOP.analyzeRevenueByMonth(year);

                for (Map.Entry<String, Double> entry : data.entrySet()) {
                    revenueData.addValue(entry.getValue(), "Revenue", entry.getKey());
                }

                revenueChart = createChart(revenueData);

                revenueChartPanel = new ChartPanel(revenueChart);
            }

            private JFreeChart createChart(DefaultCategoryDataset dataset) {
                JFreeChart chart = ChartFactory.createLineChart(
                        "Monthly revenue chart",
                        "Month",
                        "Revenue (VND)",
                        dataset
                );
                chart.setBackgroundPaint(Color.white);

                NumberAxis yAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
                yAxis.setNumberFormatOverride(formatCurrency); // Format currency to VND

                revenuePlot = chart.getCategoryPlot();
                revenuePlot.setBackgroundPaint(Style.CHART_BACKGROUND_COLOR);
                LineAndShapeRenderer renderer = new LineAndShapeRenderer();
                renderer.setSeriesPaint(0, new Color(27, 199, 27));
                renderer.setSeriesStroke(0, new BasicStroke(4.0f));
                renderer.setDefaultItemLabelsVisible(true);
                renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
                revenuePlot.setRenderer(renderer);

                return chart;
            }

            private void updateYearComboBox() {
                yearComboBox.removeAllItems();
                for (Integer year : years) {
                    yearComboBox.addItem(year);
                }
            }
        }

        class RightPn extends JPanel{
            RightPn(){
                setLayout(new GridLayout(2,1,0,10));
                setBackground(Color.WHITE);
                OrderStatisticsPanel orderStatisticsPanel= new OrderStatisticsPanel();
                BestSellingProductsPanel bestSellingProductsPanel = new BestSellingProductsPanel();
                add(orderStatisticsPanel);
                add(bestSellingProductsPanel);
            }
        }

        class OrderStatisticsPanel extends JPanel{
            private Map<String, Integer> orderData =LoginFrame.COMPUTER_SHOP.analyzeOrderStatus();

            OrderStatisticsPanel(){
                setLayout(new BorderLayout());

                DefaultPieDataset dataset = new DefaultPieDataset();
                for (Map.Entry<String, Integer> entry : orderData.entrySet()) {
                    dataset.setValue(entry.getKey(), entry.getValue());
                }
                JFreeChart pieChart = ChartFactory.createPieChart(
                        "Order status statistics chart",
                        dataset,
                        true,
                        true,
                        true
                );

                PiePlot ordersPlot = (PiePlot) pieChart.getPlot();
                ordersPlot.setBackgroundPaint(Style.CHART_BACKGROUND_COLOR);
                ordersPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                        "{0}: {2}",
                        new DecimalFormat("0"),
                        new DecimalFormat("0.00%")
                ));

                ChartPanel chartPanel = new ChartPanel(pieChart);
                chartPanel.setPreferredSize(new Dimension(500, 300));
                add(chartPanel);
            }
        }

        class BestSellingProductsPanel extends JPanel{
            private Map<String, Integer> productData = LoginFrame.COMPUTER_SHOP.bestSellingProductStatistics( 4);

            BestSellingProductsPanel(){
                setLayout(new BorderLayout());

                DefaultCategoryDataset productDataset = new DefaultCategoryDataset();
                for (Map.Entry<String, Integer> entry : productData.entrySet()) {
                    productDataset.addValue(entry.getValue(),entry.getKey(), entry.getKey());
                }

                JFreeChart barChart = ChartFactory.createBarChart(
                        "Best-selling product statistics chart",
                        "Product name",
                        "Quantity sold (Items)",
                        productDataset
                );

                CategoryPlot plot = barChart.getCategoryPlot();
                plot.setBackgroundPaint(Style.CHART_BACKGROUND_COLOR);
                BarRenderer renderer = (BarRenderer) plot.getRenderer();

                renderer.setBarPainter(new StandardBarPainter());
                renderer.setSeriesPaint(0, Style.CHART_BAR_COLOR_ORANGE);
                renderer.setSeriesPaint(1, Style.CHART_BAR_COLOR_YELLOW);
                renderer.setSeriesPaint(2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                renderer.setSeriesPaint(3, Style.CONFIRM_BUTTON_COLOR_GREEN);
                renderer.setDrawBarOutline(false);

                ChartPanel chartPanel = new ChartPanel(barChart);
                chartPanel.setPreferredSize(new Dimension(500, 300));
                add(chartPanel);
            }
        }
    }
  }

  private class ProductPanel extends JPanel {
      private ToolPanel toolPanel = new ToolPanel();
      private JButton addBt,
              modifyBt,
              deleteBt,
              sortBt,
              exportExcelBt,
              statisticsBt,
              searchBt,
              reloadBt;
      private CustomButton allBt, gaming,
              office,
              pcCase,
              cheapest,
              luxury,
              selectedButton;
      private JTextField findText;
      private TablePanel tablePanel = new TablePanel();
      private JTable tableProduct, tableStatisticsProduct;
      private DefaultTableModel modelProductTable, modelStatisticsProductTable;
      private JScrollPane scrollPaneProductTable;
      private JTabbedPane tabbedPaneProductTable;
      private JPanel sortPanel;
      private JLabel sortLabel;
      private JComboBox<String> sortComboBox;
      private JFreeChart chartPanel;
      private DefaultCategoryDataset barDataset;
      private ChartPanel barChartPanel;
      private JPanel statisticsProductPn;

      JPanel searchPanel, applicationPanel, mainPanel;

      private static List<Product> productsAll = reloadProduct();

      private static List<Product> reloadProduct() {
          return LoginFrame.COMPUTER_SHOP.getAllProduct();
      }

      private void upDataProductsStatistics(Map<Product, Long> stringLongMap, DefaultTableModel modelStatisticsProductTable) {
          removeProductStatistics(modelStatisticsProductTable);
          int i = 0, totalSold = 0, totaInStock = 0;
          for (Map.Entry<Product, Long> data : stringLongMap.entrySet()) {
              modelStatisticsProductTable.addRow(new Object[]{i++, data.getKey().getName(), data.getValue(), data.getKey().getQuantity()});
              totalSold += data.getValue();
              totaInStock += data.getKey().getQuantity();
          }
          modelStatisticsProductTable.addRow(new Object[]{"", "Total of " + LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), totalSold, totaInStock});
      }

      private void updateDataForChart(Map<Product, Long> data) {
          for (Map.Entry<Product, Long> value : data.entrySet()) {
              barDataset.addValue(value.getValue(), value.getKey().getName(), value.getKey().getName());
          }
      }

      private void upDataProductsStatistics(Map<Product, Long> productLongMap, String text, DefaultTableModel modelStatisticsProductTable) {
          removeProductStatistics(modelStatisticsProductTable);
          int i = 0, tatolSold = 0, tatolInStock = 0;
          for (Map.Entry<Product, Long> data : productLongMap.entrySet()) {
              System.out.println(data.getKey());

              if (data.getKey().getName().toLowerCase().contains(text)) {
                  modelStatisticsProductTable.addRow(new Object[]{i++, data.getKey().getName(), data.getValue(), data.getKey().getQuantity()});
                  tatolSold += data.getValue();
                  tatolInStock += data.getKey().getQuantity();
              }
          }
          modelStatisticsProductTable.addRow(new Object[]{"", "Total of " + LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), tatolSold, tatolInStock});
      }

      private void removeProductStatistics(DefaultTableModel modelStatisticsProductTable) {
          modelStatisticsProductTable.setRowCount(0);
      }

      // ok
      public static void upDataProducts(List<Product> products, DefaultTableModel modelProductTable) {
          String[][] rowData = Product.getDateOnTable(products);
          TablePanel.removeDataTable(modelProductTable);
          for (int i = 0; i < rowData.length; i++) {
              modelProductTable.addRow(rowData[i]);
          }
      }

      public static void deletedProduct(Product product) {
          productsAll.remove(product);
      }

      public static boolean changeStatus(Product product, String status) {
          return product.updateStatus(status);
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
                      e -> new ProductInputForm(() -> {
                          productsAll = reloadProduct();
                          upDataProducts(productsAll, modelProductTable);
                      }));

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
                                          new ProductModifyForm(productsAll.get(selectedRow), () -> {
                                              productsAll = reloadProduct();
                                              upDataProducts(productsAll, modelProductTable);
                                          }).setVisible(true);
                                      });
                          } else {
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
                      e -> {
                          int selectedRow = tableProduct.getSelectedRow();
                          int columnIndex = 0;
                          if (selectedRow != -1) {
                              Object value = tableProduct.getValueAt(selectedRow, columnIndex);

                              int index = Integer.parseInt(value.toString());
                              System.out.println(index);
                              LoginFrame.COMPUTER_SHOP.removeProductByIndex(index);
                              //                  modelProductTable.removeRow(selectedRow);
                              productsAll = reloadProduct();
                              upDataProducts(productsAll, modelProductTable);

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
                      e -> {
                          String fileName =
                                  JOptionPane.showInputDialog(
                                          null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                          if (fileName != null && !fileName.trim().isEmpty()) {

                              if (!fileName.toLowerCase().endsWith(".xlsx")) {
                                  fileName += ".xlsx";
                              }

                              productsAll = reloadProduct();
                              ExcelConfig.exportToExcel(productsAll, fileName, columnNamesPRODUCT);
                              if (productsAll.isEmpty())
                                  JOptionPane.showMessageDialog(
                                          null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                              JOptionPane.showMessageDialog(
                                      null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);
                          }
//                JOptionPane.showMessageDialog(
//                    null, "Are you sure ", "Exit", JOptionPane.ERROR_MESSAGE);
//              }
                      });

              statisticsBt = new JButton("Statistics");
              ButtonConfig.addButtonHoverEffect(
                      statisticsBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
              ButtonConfig.setStyleButton(
                      statisticsBt,
                      Style.FONT_PLAIN_13,
                      Style.WORD_COLOR_BLACK,
                      Style.WORD_COLOR_WHITE,
                      SwingConstants.CENTER,
                      new Dimension(80, 80));
              ButtonConfig.setButtonIcon(
                      "src/main/java/Icon/icons8-export-excel-50.png", statisticsBt, 35);
              KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.importExcelKey, statisticsBt);
              statisticsBt.addActionListener(e -> {
                  upDataProductsStatistics(LoginFrame.COMPUTER_SHOP.productOrderStatistics(), modelStatisticsProductTable);
              });

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
                                  case ("PRICE"): {
                                      productsAll = productsAll.stream().sorted((p1, p2) -> {
                                          return (int) (p2.getPrice() - p1.getPrice());
                                      }).collect(Collectors.toList());
                                      break;
                                  }

                                  case ("MEMORY"): {
                                      productsAll = productsAll.stream()
                                              .sorted((p1, p2) -> p2.getMemory().compareTo(p1.getMemory()))
                                              .collect(Collectors.toList());
                                      break;
                                  }
                                  case ("NAME"): {
                                      productsAll = productsAll.stream()
                                              .sorted((p1, p2) -> p2.getName().compareTo(p1.getName()))
                                              .collect(Collectors.toList());
                                      break;
                                  }
                                  case ("RAM"): {
                                      productsAll = productsAll.stream()
                                              .sorted((p1, p2) -> p2.getRam().compareTo(p1.getRam()))
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

              applicationPanel.add(statisticsBt);
              applicationPanel.add(ButtonConfig.createVerticalSeparator());
              applicationPanel.add(reloadBt);
              applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

              mainPanel = new JPanel(new GridBagLayout());
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

                              if (productsAll.isEmpty()) {
                                  JOptionPane.showMessageDialog(tablePanel, "Product not found in the List!");
                                  return;
                              }
                              upDataProductsStatistics(LoginFrame.COMPUTER_SHOP.productOrderStatistics(), findText.getText(), modelStatisticsProductTable);
                              upDataProducts(productsAll, modelProductTable);
                          }
                      });

              reloadBt.addActionListener(
                      new ActionListener() {
                          @Override
                          public void actionPerformed(ActionEvent e) {
                              productsAll = reloadProduct();

                              upDataProducts(productsAll, modelProductTable);
                              findText.setText("");

                              upDataProductsStatistics(LoginFrame.COMPUTER_SHOP.productOrderStatistics(), modelStatisticsProductTable);
                              updateDataForChart(LoginFrame.COMPUTER_SHOP.productOrderStatistics());
                          }
                      });
          }
      }

      private class TablePanel extends JPanel {
          public TablePanel() {
              setLayout(new BorderLayout());
              setBackground(Style.WORD_COLOR_WHITE);

              JPanel sortBar = new JPanel();
              sortBar.setLayout(new FlowLayout(FlowLayout.LEFT));
              sortBar.setBackground(Color.WHITE);
              allBt = ButtonConfig.createCustomButton(
                      "All",
                      Style.FONT_BOLD_15,
                      Color.BLACK,
                      Style.MENU_BUTTON_COLOR,
                      Style.LIGHT_BlUE,
                      Style.MENU_BUTTON_COLOR,
                      2,
                      25,
                      SwingConstants.CENTER,
                      new Dimension(120, 25));
              allBt.addActionListener(
                      e -> {
                          updateSelectedButtonColor(allBt);
                          filterProduct(DisplayProductType.ALL);
                      });
              gaming =
                      ButtonConfig.createCustomButton(
                              "Laptop Gaming",
                              Style.FONT_BOLD_15,
                              Color.BLACK,
                              Color.white,
                              Style.LIGHT_BlUE,
                              Style.MENU_BUTTON_COLOR,
                              2,
                              25,
                              SwingConstants.CENTER,
                              new Dimension(150, 25));
              gaming.addActionListener(
                      e -> {
                          updateSelectedButtonColor(gaming);
                          filterProduct(DisplayProductType.LAPTOP_GAMING);

                      });
              office =
                      ButtonConfig.createCustomButton(
                              "Laptop Office",
                              Style.FONT_BOLD_15,
                              Color.BLACK,
                              Color.white,
                              Style.LIGHT_BlUE,
                              Style.MENU_BUTTON_COLOR,
                              2,
                              25,
                              SwingConstants.CENTER,
                              new Dimension(150, 25));
              office.addActionListener(
                      e -> {
                          updateSelectedButtonColor(office);
                          filterProduct(DisplayProductType.LAPTOP_OFFICE);

                      });
              pcCase =
                      ButtonConfig.createCustomButton(
                              "PC Case",
                              Style.FONT_BOLD_15,
                              Color.BLACK,
                              Color.white,
                              Style.LIGHT_BlUE,
                              Style.MENU_BUTTON_COLOR,
                              2,
                              25,
                              SwingConstants.CENTER,
                              new Dimension(120, 25));
              pcCase.addActionListener(
                      e -> {
                          updateSelectedButtonColor(pcCase);
                          filterProduct(DisplayProductType.PC_CASE);

                      });
              cheapest =
                      ButtonConfig.createCustomButton(
                              "10m to 20m",
                              Style.FONT_BOLD_15,
                              Color.BLACK,
                              Color.white,
                              Style.LIGHT_BlUE,
                              Style.MENU_BUTTON_COLOR,
                              2,
                              25,
                              SwingConstants.CENTER,
                              new Dimension(120, 25));
              cheapest.addActionListener(
                      e -> {
                          updateSelectedButtonColor(cheapest);
                          filterProduct(DisplayProductType.PRICE_IN_AMOUNT_10M_20M);

                      });

              luxury =
                      ButtonConfig.createCustomButton(
                              "20m to 30m",
                              Style.FONT_BOLD_15,
                              Color.BLACK,
                              Color.white,
                              Style.LIGHT_BlUE,
                              Style.MENU_BUTTON_COLOR,
                              2,
                              25,
                              SwingConstants.CENTER,
                              new Dimension(120, 25));
              luxury.addActionListener(
                      e -> {
                          updateSelectedButtonColor(luxury);
                          filterProduct(DisplayProductType.PRICE_IN_AMOUNT_20M_30M);
                      });

              sortBar.add(allBt);
              sortBar.add(gaming);
              sortBar.add(office);
              sortBar.add(pcCase);
              sortBar.add(luxury);
              sortBar.add(cheapest);
              add(sortBar, BorderLayout.NORTH);


              tableProduct = createTable(modelProductTable, columnNamesPRODUCT);
              tableStatisticsProduct = createTable(modelStatisticsProductTable, columStatisticsProduct);
              tableProduct.setRowHeight(30);
              tableStatisticsProduct.setRowHeight(30);

              resizeColumnWidth(tableProduct, 150);
              resizeColumnWidth(tableStatisticsProduct, 150);

              modelProductTable = (DefaultTableModel) tableProduct.getModel();
              modelStatisticsProductTable = (DefaultTableModel) tableStatisticsProduct.getModel();

//        List<entity.Product> productsDemo = LoginFrame.COMPUTER_SHOP.getAllProduct();

              upDataProducts(productsAll, modelProductTable);
              upDataProductsStatistics(LoginFrame.COMPUTER_SHOP.productOrderStatistics(), modelStatisticsProductTable);

              scrollPaneProductTable = new JScrollPane(tableProduct);
              JScrollPane scrollPaneProductStatisticsTable = new JScrollPane(tableStatisticsProduct);

              statisticsProductPn = new JPanel(new GridLayout(1, 2));

              statisticsProductPn.add(scrollPaneProductStatisticsTable);
              //chart panel

              {
                  barDataset = new DefaultCategoryDataset();

                  String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                  chartPanel = ChartFactory.createBarChart(
                          "Chart of the business rating column of " + currentMonth,
                          "Products",
                          "Sold",
                          barDataset,
                          PlotOrientation.VERTICAL,
                          true, true, false);

                  CategoryPlot plot = chartPanel.getCategoryPlot();

                  NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
                  yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

              }
              updateDataForChart(LoginFrame.COMPUTER_SHOP.productOrderStatistics());

              barChartPanel = new ChartPanel(chartPanel);
              statisticsProductPn.add(barChartPanel);


              tabbedPaneProductTable =
                      createTabbedPane(scrollPaneProductTable, "Product for Sales", Style.FONT_BOLD_16);
              tabbedPaneProductTable.add("Product statistics", statisticsProductPn);
              tabbedPaneProductTable.setBorder(BorderFactory.createTitledBorder(""));

              add(tabbedPaneProductTable, BorderLayout.CENTER);
          }

          private void filterProduct(DisplayProductType type) {
              removeDataTable(modelProductTable);
              productsAll = LoginFrame.COMPUTER_SHOP.filterBy(type);
              upDataProducts(productsAll, modelProductTable);
          }


          public static void removeDataTable(DefaultTableModel modelProductTable) {
              modelProductTable.setRowCount(0);
          }
      }


      private void updateSelectedButtonColor(CustomButton button) {
          Color defaultColor = Color.WHITE;
          Color selectedColor = Style.MENU_BUTTON_COLOR;

          if (selectedButton == null) {
              allBt.setBackgroundColor(defaultColor);
              allBt.setHoverColor(Style.LIGHT_BlUE);
          }

          if (selectedButton != null) {
              selectedButton.setBackgroundColor(defaultColor);
              selectedButton.setHoverColor(Style.LIGHT_BlUE);
          }

          button.setBackgroundColor(selectedColor);
          button.setHoverColor(selectedColor);
          selectedButton = button;
      }
  }

  private class SupplierPanel extends JPanel {
    private JButton addBt, modifyBt, deleteBt, exportExcelBt, reloadBt, searchBt, analysisBt, sumItemBt;
    private JTextField findText;
    private JTable tableSupplier,tableQuantity;
    private DefaultTableModel modelSupplier ,modelQuantity;

    private ToolPanel toolPanel = new ToolPanel();
    private TablePanel tablePanel = new TablePanel();
    //data

    private static List<Supplier> suppliers = reloadData();
    private static  Map<String,Long> analyzeSalesVolume= LoginFrame.COMPUTER_SHOP.quantitativeAnalysis();

    private static List<Supplier> reloadData() {
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
          sumItemBt = new JButton("Imported");
          ButtonConfig.setStyleButton(
                  sumItemBt,
                  Style.FONT_PLAIN_13,
                  Style.WORD_COLOR_BLACK,
                  Style.WORD_COLOR_WHITE,
                  SwingConstants.CENTER,
                  new Dimension(80, 80));
          ButtonConfig.addButtonHoverEffect(
                  sumItemBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
          ButtonConfig.setButtonIcon("src/main/java/Icon/database-add-icon.png", sumItemBt, 35);
          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.addKey, sumItemBt);
          sumItemBt.addActionListener(
                  e -> {
                      analyzeSalesVolume = LoginFrame.COMPUTER_SHOP.analyzeQuantityOfImportedGoods();
                    System.out.println(LoginFrame.COMPUTER_SHOP.analyzeQuantityOfImportedGoods());
                    System.out.println(LoginFrame.COMPUTER_SHOP.getAllProduct());
                      modelQuantity.setRowCount(0);
                      upDataTable(modelQuantity);
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
          analysisBt = new JButton("Quantitative analysis");
          ButtonConfig.setStyleButton(
              analysisBt,
              Style.FONT_PLAIN_13,
              Style.WORD_COLOR_BLACK,
              Style.WORD_COLOR_WHITE,
              SwingConstants.CENTER,
              new Dimension(80, 80));
          ButtonConfig.setButtonIcon(
              "src/main/java/Icon/icons8-export-excel-50.png", analysisBt, 35);
          ButtonConfig.addButtonHoverEffect(
              analysisBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);

          KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.importExcelKey, analysisBt);
          analysisBt.addActionListener( e ->{
             LoginFrame.COMPUTER_SHOP.quantitativeAnalysis();
             modelQuantity.setRowCount(0);
             analyzeSalesVolume = LoginFrame.COMPUTER_SHOP.quantitativeAnalysis();
            upDataTable(modelQuantity);
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
          reloadBt.addActionListener(
              e -> {
                suppliers = reloadData();
                updateSuppliers(selectedOption);
                findText.setText("");

//                analyzeSalesVolume = LoginFrame.COMPUTER_SHOP.quantitativeAnalysis();
                modelQuantity.setRowCount(0);
//                upDataTable(modelQuantity);
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
        applicationPanel.add(analysisBt);
        applicationPanel.add(sumItemBt);
        applicationPanel.add(reloadBt);
        applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
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
//          int supplierId = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 1));
          int columnCount = tableSupplier.getColumnCount(); // Số lượng cột
          String[] rowData = new String[columnCount];

          for (int i = 0; i < columnCount; i++) {
            rowData[i] = (String) tableSupplier.getValueAt(selectedRow, i);
          }
          ModifySupplierFrame modifySupplierFrame =
              new ModifySupplierFrame(() -> updateSuppliers(selectedOption), Supplier.builder().companyName(rowData[1]).email(rowData[2]).phoneNumber(rowData[3]).address(rowData[4]).contractDate(LocalDate.parse(rowData[5])).build() );
          modifySupplierFrame.showFrame();
          ToastNotification.showToast(" modify.", 3000, 50, -1, -1);
        } else {
          ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
        }
      }

      private void deleteHandle() {
        int selectedRow = tableSupplier.getSelectedRow();

        if (selectedRow != -1) {
          int index = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 0)) - 1;

          LoginFrame.COMPUTER_SHOP.removeSupplierByIndex(index);
          suppliers = reloadData();
          updateSuppliers(selectedOption);

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
        tableQuantity = createTable(modelQuantity, columnNamesQuantityOfSupplier);

        tableSupplier.setRowHeight(30);
        tableQuantity.setRowHeight(30);

        resizeColumnWidth(tableSupplier, 300);
        resizeColumnWidth(tableQuantity, 300);


        tableSupplier
            .getColumnModel()
            .getColumn(tableSupplier.getColumnCount() - 1)
            .setPreferredWidth(400);


        tableQuantity
                .getColumnModel()
                .getColumn(tableQuantity.getColumnCount() - 1)
                .setPreferredWidth(400);

        JScrollPane scrollPaneSupplier = new JScrollPane(tableSupplier);
        JScrollPane scrollPaneSupplierQuantity = new JScrollPane(tableQuantity);

        modelSupplier = (DefaultTableModel) tableSupplier.getModel();
        modelQuantity = (DefaultTableModel) tableQuantity.getModel();

        suppliers = LoginFrame.COMPUTER_SHOP.getAllSupplier();

        upDataTable(suppliers, modelSupplier);


        JTabbedPane tabbedPaneSupplier =
            createTabbedPane(scrollPaneSupplier, "Supplier List", Style.FONT_BOLD_16);
        tabbedPaneSupplier.add("Supply Statistics",scrollPaneSupplierQuantity);

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

      modelQuantity.setRowCount(0);

      Iterator<String> iterator =analyzeSalesVolume.keySet().iterator();
      while (iterator.hasNext()) {
        String key = iterator.next();
        if(!key.toLowerCase().contains(text.toLowerCase())) {
          iterator.remove();
        }
      }
      upDataTable(modelQuantity);
    }

    public static void upDataTable(List<entity.Supplier> suppliers, DefaultTableModel modelSupplier) {
      String[][] rowData = Supplier.getData(suppliers);
      for (String[] strings : rowData) {
//        System.out.println(strings);
        modelSupplier.addRow(strings);
      }
    }
    public static void upDataTable( DefaultTableModel modelQuantity) {
      int index =0;
      for(Map.Entry<String, Long> data: analyzeSalesVolume.entrySet()) {
        modelQuantity.addRow(new Object[]{index++ +" ",data.getKey(),data.getValue()});
      }
    }
  }

  private class CustomerPanel extends JPanel {
    private final String[] customerColumnNames = {
      "Serial number", "Customer ID", "Customer Name", "Email", "Address", "Password", "Avata"
    };
      final String[] statisticsColumnNames = { "Serial number","Customer Name","Total Purchase"};

      private JTable tableCustomer, customerStatisticsTable;
      private DefaultTableModel modelCustomer, customerStatisticsModel;
      private JScrollPane scrollPaneCustomer, customerStatisticsScrollPane;
      private JTabbedPane tabbedPaneCustomer;
      private ToolPanel toolPanel = new ToolPanel();
      private TableCustomerPanel tableCustomerPanel = new TableCustomerPanel();

      private  DefaultCategoryDataset barDatasetCustomer;
      private JFreeChart barChartCustomer;

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

                        new CustomerInfoFrame(()-> {reload();});

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
                        ModifyCustomerFrame modifyCustomerFrame =
                            new ModifyCustomerFrame(customers.get(customerId - 1),()->{   reload(); });
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

                    LoginFrame.COMPUTER_SHOP.unBlockCustomerById(customerId);
                    tableCustomer.setValueAt( customername.replace(" *",""),selectedRow,2);

                    JOptionPane.showMessageDialog(
                        null,
                        "Customer"
                            + tableCustomer.getValueAt(selectedRow, fullName)
                            + "Unblocked! ");
                  } else {
                    JOptionPane.showMessageDialog(null, "Block customerId : " + customerId);

                    LoginFrame.COMPUTER_SHOP.blockCustomerById(customerId);
                    tableCustomer.setValueAt( customername+" *",selectedRow,2);

                    JOptionPane.showMessageDialog(
                        null,
                        "Customer"
                            + tableCustomer.getValueAt(selectedRow, fullName)
                            + "is blocked! ");
                  }
//                  reload();
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
                      customers = LoginFrame.COMPUTER_SHOP.findCustomerByName(findCustomerField.getText());
                      if (customers.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            tableCustomerPanel, "No customer found as requested!");
                        return;
                      }else{

                      }
                      upDataTable(  customers , modelCustomer, tableCustomer);
                      break;
                    }
                  case TAB_BILL:
                    {
                      if (findCustomerField.getText().trim().isEmpty()) return;
                      try {
                        int customerId = Integer.parseInt(findCustomerField.getText());

                        showOrderContainer.removeAll();

//bug oi
//                        showOrderContainer.add(new ShowOrder());

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

        showOrderContainer = new CustomerBill();
//        showOrderContainer.add(new Label("You should continue to find the customer Id!!!"));
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

      upDataCustomerStatisticsTable(LoginFrame.COMPUTER_SHOP.customerOrderStatistics());
//      showOrderContainer.add(new Label("You should continue to find the customer Id!!!"));
//      billTextDisplayPanal.setText("You should continue to find the customer Id!!!");
    }

    public int getIndexSelectedTab() {
      return tabbedPaneCustomer.getSelectedIndex();
    }
    private class CustomerBill extends JPanel{
        private JLabel email;
        private ButtonConfig showBt;
        private JPanel tool, view;
        public CustomerBill(){
            setLayout(new BorderLayout());
            this.tool = new JPanel();
            email = new JLabel("email of customer");
            tool.add(email);

            this.view = new JPanel();
            tool.setBackground(Color.GREEN);
            view.setBackground(Color.PINK);

            add(tool,BorderLayout.WEST);
            add(view,BorderLayout.CENTER);

        }
    }
      private class Schemas extends JPanel {
          JPanel chartPn;

          public Schemas() {
              setLayout(new GridLayout(1, 2));
              customerStatisticsTable = createTable(customerStatisticsModel, statisticsColumnNames);
              customerStatisticsTable.setRowHeight(40);// độ rộng của hàng:)
              resizeColumnWidth(customerStatisticsTable, 200);
              customerStatisticsModel = (DefaultTableModel) customerStatisticsTable.getModel();
              customerStatisticsScrollPane = new JScrollPane(customerStatisticsTable);


              chartPn = new JPanel(new BorderLayout());// panel chứa biểu đồ
              chartPn.setBackground(Color.GRAY);
//              chartPn.add();

                  barDatasetCustomer = new DefaultCategoryDataset();
                  String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                   barChartCustomer = ChartFactory.createBarChart(
                          "Chart of the business rating column of "+currentMonth,
                          "Customer",
                          "Purchased",
                          barDatasetCustomer,
                          PlotOrientation.VERTICAL,
                          true, true, false);

                  CategoryPlot plot = barChartCustomer.getCategoryPlot();

                  NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
                  yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                chartPn.add(new ChartPanel(barChartCustomer));
              upDataCustomerStatisticsTable(LoginFrame.COMPUTER_SHOP.customerOrderStatistics());

              add(customerStatisticsScrollPane);
              add(chartPn);
          }

      }
      public void upDataCustomerStatisticsTable(Map<Customer,Long> data){
          ProductPanel.TablePanel.removeDataTable(customerStatisticsModel);
        int i=0, total=0;
        for(Map.Entry<Customer,Long> value : data.entrySet()){
            customerStatisticsModel.addRow(new Object[]{i++,value.getKey().getFullName(),value.getValue()});
            barDatasetCustomer.addValue(value.getValue(),value.getKey().getFullName(),value.getKey());
            total+= value.getValue();
        }
          customerStatisticsModel.addRow(new Object[]{"","Total : ",total});
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
      "Customer Name",
      "Ship address",
      "Order Date",
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

    private String[][] rowData;
    private Map<Manager, List<Order>> managerListMap;
    private List<Manager> managers;
    private Manager manager;
    private Order order;

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
                  List<Order> orders =
                      managerListMap.values().stream()
                          .flatMap(Collection::stream)
                          .collect(Collectors.toList());
                  exportExcel(orders, new String[]{"Order ID",
                          "Customer Name",
                          "Ship address",
                          "Order Date",
                          "Status Item",
                          "Total Price",
                          "Total Quantity"});
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
                "", Style.FONT_PLAIN_18, Color.GRAY, new Dimension(300, 50));
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
            reloadOrders(null, searchOrderField.getText());
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
          String statusMessage = table.getValueAt(selectRow, 4).toString();

          switch (statusMessage) {
            case OrderType.ACTIVE_MESSAGE -> {
              manager = managerListMap.keySet().stream()
                              .filter(manager1 -> manager1.sameID(Integer.parseInt(rowData[selectRow][6])))
                              .findAny()
                              .orElse(null);
              order = managerListMap.get(manager).stream().filter(order1 -> order1.sameID(Integer.parseInt(rowData[selectRow][0])))
                              .findAny()
                              .orElse(null);
              orderTabbedPane.setSelectedIndex(2);
              exportPanel.loadOrders(manager, order);
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

        private void addProductPanel(List<OrderDetail> orderDetails) {
          productDetailsPanel.addProductToOrders(productDetailsPanel.createProduct(orderDetails));
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
        List<OrderDetail> orderDetails;

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
            setColorScrollPane(scrollpane, Style.MENU_BUTTON_COLOR, Color.WHITE);
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

        public JPanel createProduct(List<OrderDetail> orderDetails) {
          this.orderDetails = orderDetails;
          JPanel wrapperPn = new JPanel();
          wrapperPn.setLayout(new BoxLayout(wrapperPn, BoxLayout.Y_AXIS));
          wrapperPn.setBackground(Color.WHITE);

          for (OrderDetail orderDetail : this.orderDetails) {
            Product product = orderDetail.getProduct();
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
                    currencyFormatter.format(product.getPrice()) + "₫",
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
              this.orderDetails.stream()
                      .map(OrderDetail::getProduct)
                      .filter(p -> p.getId() == id).findFirst().orElse(null);
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
          setColorScrollPane(scrollPane, Style.MENU_BUTTON_COLOR, Style.LIGHT_BlUE);
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

          String price = currencyFormatter.format(1000000); // bỏ tổng giá của order vào đây
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
            manager.updateStatus(order, OrderType.DISPATCHED_MESSAGE);
            updateOrders();
            orderTabbedPane.setSelectedIndex(1);
            ToastNotification.showToast("Order exported successfully!", 3000, 50, -1, -1);
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

      private void loadOrders(Manager manager, Order order) {
        setText(
            String.valueOf(order.getCustomer().getId()),
            String.valueOf(order.getOrderedAt()),
            order.getShipAddress(),
            order.getStatus(),
            manager.getFullName(),
            String.valueOf(manager.getId()),
            currencyFormatter.format(order.totalCost()),
            String.valueOf(order.totalQuantity()));
        this.detailsPanel.addProductPanel(order.getOrderDetails());
      }

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
      managers = LoginFrame.COMPUTER_SHOP.getAllManager();
      managerListMap = managers.stream()
              .collect(Collectors.toMap(
                      manager -> manager,
                      manager -> manager.filter(status, searchText),
                      (exist, replace) -> exist,
                      TreeMap::new
              ));
      rowData = Order.getData(managerListMap);
    }

    private void upDataOrders(DefaultTableModel tableModel, String status, String searchText) {
      reloadOrders(status, searchText);
      tableModel.setRowCount(0);
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
    private List<Product> products;

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
            String productName = (String) selectedTable.getValueAt(row, 2);
            Product product1 = products.stream().filter(product -> product.sameName(productName)).findAny().orElse(null);
            if (changeStatus(product1, status)) {
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
                new ProductModifyForm(products.get(selectedRow), InventoryPanel.this::updateProduct)
                    .setVisible(true);
              });
        } else {
          ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
        }
      }

      private void deletedHandle() {
        JTable selectedTable = getSelectedTable();

        if (selectedTable != null && selectedTable.getSelectedRow() != -1) {
          int selectedRow = selectedTable.getSelectedRow();
          String productName = (String) selectedTable.getValueAt(selectedRow, 2);
          Product product1 = products.stream().filter(product -> product.sameName(productName)).findAny().orElse(null);
          deletedProduct(product1);
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

    private void deletedProduct(Product product) {
      ProductPanel.deletedProduct(product);
    }

    private boolean changeStatus(Product product, String status) {
      return ProductPanel.changeStatus(product, status);
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
      products = ProductPanel.reloadProduct().stream()
              .filter(product -> product.filter(status, searchText))
              .toList();
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

  private class ManagerPanel extends JPanel {
        private final String[] accountColumnNames = {
                "Serial Number",
                "Manager ID",
                "Full name",
                "Email",
                "Address",
                "Phone Number",
                "Birth Day",
                "Account creation date",
                "Avatar"
        };
        private JTable managerTable;
        private DefaultTableModel modelAccManager;
        private JScrollPane scrollPaneAccManager;
        private JTabbedPane tabbedPaneAccManager;
        private ToolPanel toolPanel = new ToolPanel();
        private ManagerTablePanel managerTablePanel = new ManagerTablePanel();

        private JButton addAccBt,
                removeBt,
                modifyAccBt,
                exportAccExcelBt,
                searchAccBt,
                reloadAccBt,
                blockManagerBt;
        private JTextField accManagerField;

        private JPanel searchPanel, applicationPanel, mainPanel;
        private JTextField fullNameField, addressField, birthdayField, phoneNumberField,usernameField, emailField;
        private JPasswordField passwordField;
        private Date selectedDate;
        private JLabel label;
        private String contextPath = "";
        private int modifyIndex = -1;
        private static TableStatus tableStatus = ADD;
        private static ArrayList<entity.Manager> managers = (ArrayList<Manager>) LoginFrame.COMPUTER_SHOP.getAllManager();
        private static Manager mutableManager;

    public ManagerPanel() {
      setLayout(new BorderLayout());
      toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
      add(toolPanel, BorderLayout.NORTH);
      add(managerTablePanel, BorderLayout.CENTER);
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
                        e -> {
                            setVisiblePanel(3);
                            tableStatus = ADD;
                            clearField();
                        });

                removeBt = new JButton("Remove");
                ButtonConfig.addButtonHoverEffect(
                        removeBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                ButtonConfig.setStyleButton(
                        removeBt,
                      Style.FONT_PLAIN_13,
                      Style.WORD_COLOR_BLACK,
                      Style.WORD_COLOR_WHITE,
                      SwingConstants.CENTER,
                      new Dimension(80, 80));
                ButtonConfig.setButtonIcon("src/main/java/Icon/removeAcc.png", removeBt, 35);
                KeyStrokeConfig.addKeyBindingButton(this, KeyStrokeConfig.addKey, removeBt);
                removeBt.addActionListener(
                      e -> {
                        if( JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure you want to remove this manager from the system?",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        ) == JOptionPane.YES_OPTION){
                          managers.remove(managerTable.getSelectedRow());
                          reloadManagerData();
                          clearField();
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
                        e -> {
                            int selectedRow = managerTable.getSelectedRow();
                            if (selectedRow != -1) {

                                mutableManager = managers.get(selectedRow);
                                if(!mutableManager.isBlock()){
                                    tableStatus = MODIFY;
                                    setDataToModify(managers.get(selectedRow));
                                    setVisiblePanel(3);
                                    addAccBt.setEnabled(false);
                                }else ToastNotification.showToast("Unlock this manager before starting to modify!",3000,50,-1,-1);

                            } else {
                                ToastNotification.showToast(
                                        "Please select row to change information!", 2500, 50, -1, -1);
                                addAccBt.setEnabled(true);
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
                        e -> {
                            int selectedRow = managerTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String managerName = (String) managerTable.getValueAt(selectedRow, 2);
                                boolean isBlocked = managerName.contains("*");
                                Manager manWillBeBLock = managers.get(selectedRow);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Do you want to " + (isBlocked ? "Unblock" : "Block") + " the manager: " + managerName + "?",
                                        "Confirmation",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    if (isBlocked && !manWillBeBLock.isActive()) {
                                        // Unblock
                                        manWillBeBLock.setActive(true);
                                        manWillBeBLock.setFullName(managerName.replace("*", ""));
                                    } else {
                                        // Block
                                        manWillBeBLock.setActive(false);
                                        manWillBeBLock.setFullName(managerName + "*");
                                    }
                                    ToastNotification.showToast(
                                            managerName + (isBlocked ? " is unblocked !!! " : " is blocked !!!"), 3000, 50, -1, -1);
                                    reloadManagerData();
                                }
                            }else{
                                ToastNotification.showToast("Please select 1 manager to block!", 3000, 50, -1, -1);
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
                        e -> {
                            String fileName =
                                    JOptionPane.showInputDialog(
                                            null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                            if (fileName != null && !fileName.trim().isEmpty()) {
                                reloadManagerData();
                                ExcelConfig.writeManagersToExcel(managers, fileName);
                                ToastNotification.showToast(fileName + " is created!", 2500, 50, -1, -1);
                            } else {
                                ToastNotification.showToast("Failed to export Excel file!", 2500, 50, -1, -1);
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
                        e -> {
                            reloadManagerData();
                            String name = accManagerField.getText().toLowerCase().trim();
                            List<Manager> managerFound =LoginFrame.COMPUTER_SHOP.findManagerByName(name);
                            modelAccManager.setRowCount(0);

                            int count=1;
                            for( Manager manager : managerFound){
                                Object[] rowData = {
                                        count++,
                                        manager.getId(),
                                        manager.getFullName(),
                                        manager.getEmail(),
                                        manager.getAddress(),
                                        manager.getPhone(),
                                        manager.getDob().format(dateFormatter),
                                        manager.getCreatedAt(),
                                        createImageForProduct(manager.getAvatarImg(),100,100),
                                };
                                modelAccManager.addRow(rowData);
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
                            reloadManagerData();
                            accManagerField.setText("Search by Name");
                            accManagerField.setForeground(Color.GRAY);
                        });
                searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(accManagerField);
                searchPanel.add(searchAccBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addAccBt);
                applicationPanel.add(removeBt);

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

        private class ManagerTablePanel extends JPanel {
            private AddManager addManager;
            private OrderStatisticsTable orderStatisticsTable;
            private StatisticsChartPanel statisticsChartPanel;
            public ManagerTablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                managerTable = createTable(modelAccManager, accountColumnNames);

                managerTable
                        .getColumnModel()
                        .getColumn(accountColumnNames.length - 1)
                        .setCellRenderer(new ImageInJTable.ImageRenderer());
                managerTable.setRowHeight(100);
                resizeColumnWidth(managerTable, 220);
                modelAccManager = (DefaultTableModel) managerTable.getModel();

                reloadManagerData();

                scrollPaneAccManager = new JScrollPane(managerTable);
                tabbedPaneAccManager =
                        createTabbedPane(scrollPaneAccManager, "Manager Information", Style.FONT_BOLD_16);
                orderStatisticsTable = new OrderStatisticsTable(); // Statistics manager by order
                tabbedPaneAccManager.add("Manager Statistics by Orders", orderStatisticsTable);
                statisticsChartPanel = new StatisticsChartPanel();
                tabbedPaneAccManager.add("Statistics Chart", statisticsChartPanel);
                addManager = new AddManager(); // edit manager info tab
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
                        fullNameField =
                                TextFieldConfig.createTextField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, Color.white,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(285, 35),true);
                        fullNameField.setInputVerifier(new NotEmptyVerifier());

                        JLabel lblAddress =
                                LabelConfig.createLabel(
                                        "Address", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
                        addressField =
                                TextFieldConfig.createTextField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, Color.white,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(285, 35),true);

                        JLabel lblBirthday =
                                LabelConfig.createLabel(
                                        "Birthday", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
                        birthdayField =
                                TextFieldConfig.createTextField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, Color.white,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(250, 35),true);
                        birthdayField.setInputVerifier(new BirthDayVerifier());
                        birthdayField.setEditable(false);
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
                                e -> {
                                    selectedDate = new Date(calendar.getDate().getTime());

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    birthdayField.setText(dateFormat.format(selectedDate));
                                    calendarDialog.setVisible(false);
                                });

                        JLabel lblPhoneNumber =
                                LabelConfig.createLabel(
                                        "Phone Number", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT);
                        phoneNumberField =
                                TextFieldConfig.createTextField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, Color.white,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(285, 35),true);
                        phoneNumberField.setInputVerifier(new PhoneNumberVerifier());

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.anchor = GridBagConstraints.WEST;
                        add(lblFullName, gbc);

                        gbc.gridx = 1;
                        add(fullNameField, gbc);

                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        add(lblAddress, gbc);

                        gbc.gridx = 1;
                        add(addressField, gbc);

                        gbc.gridx = 0;
                        gbc.gridy = 2;
                        add(lblBirthday, gbc);

                        JPanel birthdayPanel = new JPanel(new BorderLayout());
                        birthdayPanel.setBackground(Color.WHITE);
                        birthdayPanel.add(birthdayField, BorderLayout.CENTER);
                        birthdayPanel.add(btnCalendar, BorderLayout.EAST);

                        gbc.gridx = 1;
                        add(birthdayPanel, gbc);

                        gbc.gridx = 0;
                        gbc.gridy = 3;
                        add(lblPhoneNumber, gbc);

                        gbc.gridx = 1;
                        add(phoneNumberField, gbc);
                    }
                }

                private class RightPn extends JPanel {

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
                        add(LabelConfig.createLabel(
                                        "User Name", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                                gbc);

                        gbc.gridy = 1;
                        add(LabelConfig.createLabel(
                                        "Password", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                                gbc);

                        gbc.gridy = 2;
                        add(LabelConfig.createLabel(
                                        "Email", Style.FONT_BOLD_15, Color.BLACK, SwingConstants.LEFT),
                                gbc);

                        gbc.gridx = 1;
                        gbc.gridy = 0;
                        usernameField =
                                TextFieldConfig.createTextField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, Color.white,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(285, 35),true);
                        usernameField.setInputVerifier(new NotEmptyVerifier());
                        add(usernameField, gbc);

                        gbc.gridy = 1;
                        JPanel passwdPanel = new JPanel(new BorderLayout());
                        passwordField =
                                PasswordFieldConfig.createPasswordField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, new Dimension(250, 35));
                        JButton togglePasswordButton = ButtonConfig.createShowPasswdButton(passwordField);
                        togglePasswordButton.setPreferredSize(new Dimension(45, 35));
                        passwdPanel.setBackground(Color.WHITE);
                        passwdPanel.add(passwordField, BorderLayout.CENTER);
                        passwdPanel.add(togglePasswordButton, BorderLayout.EAST);
                        add(passwdPanel, gbc);

                        gbc.gridy = 2;
                        emailField =
                                TextFieldConfig.createTextField("",
                                        Style.FONT_PLAIN_16, Color.BLACK, Color.white,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Dimension(285, 35),true);
                        emailField.setInputVerifier(new EmailVerifier());
                        add(emailField, gbc);
                    }
                }
            }

            private class Avatar extends JPanel {
                CustomButton confirmBt, undoBt, cancelBt;

                Avatar() {
                    setLayout(new BorderLayout());
                    setBackground(Color.WHITE);
                    label = new JLabel("Drop your image here", SwingConstants.CENTER);
                    label.setBackground(Color.WHITE);
                    Border dashedBorder =
                            BorderFactory.createDashedBorder(Style.CONFIRM_BUTTON_COLOR_GREEN, 2, 10, 20, true);
                    Border margin = BorderFactory.createEmptyBorder(5, 20, 5, 20);
                    Border compoundBorder = BorderFactory.createCompoundBorder(margin, dashedBorder);
                    label.setBorder(compoundBorder);
                    label.setPreferredSize(new Dimension(400, 300));
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Choose your profile picture");

                            fileChooser.setFileFilter(
                                    new FileNameExtensionFilter("Image (JPG, PNG, GIF)", "jpg", "png", "gif"));

                            int result = fileChooser.showOpenDialog(null);
                            if (result == JFileChooser.APPROVE_OPTION) {

                                File selectedFile = fileChooser.getSelectedFile();
                                contextPath = selectedFile.getAbsolutePath();

                                ImageIcon imageIcon = new ImageIcon(contextPath);
                                Image scaledImage =
                                        imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                                label.setText("");
                                label.setIcon(new ImageIcon(scaledImage));
                            }
                        }
                    });

                    JPanel uploadImagePn = new JPanel();
                    uploadImagePn.setBackground(Color.WHITE);
                    label.setTransferHandler(new ImageTransferHandler());
                    label.setInputVerifier(new NotEmptyVerifier());

                    confirmBt = ButtonConfig.createCustomButton("Confirm",Style.FONT_BOLD_16,Color.BLACK,
                            Style.MENU_BUTTON_COLOR_GREEN,Style.LIGHT_GREEN,15,SwingConstants.CENTER,new Dimension(100, 40));

                    confirmBt.addActionListener(e-> {
                        String passwd = new String(passwordField.getPassword());
                        switch (tableStatus) {
                            case ADD -> {
                                if(birthdayField.getText().isEmpty()){
                                    ToastNotification.showToast("Please fill in all the required information!",3000,50,-1,-1);
                                }else {
                                    Manager newManager = Manager.builder()
                                            .id(managers.size() + 1)
                                            .fullName(fullNameField.getText())
                                            .email(emailField.getText())
                                            .address(addressField.getText())
                                            .password(passwd)
                                            .createdAt(LocalDate.now())
                                            .dob(LocalDate.parse(birthdayField.getText(), dateFormatter))
                                            .phone(phoneNumberField.getText())
                                            .avatarImg(contextPath)
                                            .isActive(true)
                                            .orders(new ArrayList<>())
                                            .build();
                                    LoginFrame.COMPUTER_SHOP.addManager(newManager);
                                }
                            }
                            case MODIFY -> {
                                boolean isUpdated = false;

                                if (!fullNameField.getText().equals(mutableManager.getFullName())) {
                                    mutableManager.setFullName(fullNameField.getText().trim());
                                    isUpdated = true;
                                }
                                if (!addressField.getText().equals(mutableManager.getAddress())) {
                                    mutableManager.setAddress(addressField.getText());
                                    isUpdated = true;
                                }
                                if (!birthdayField.getText().equals(mutableManager.getDob().format(dateFormatter))) {
                                    mutableManager.setDob(LocalDate.parse(birthdayField.getText(), dateFormatter));
                                    isUpdated = true;
                                }
                                if (!emailField.getText().equals(mutableManager.getEmail())) {
                                    mutableManager.setEmail(emailField.getText().trim());
                                    isUpdated = true;
                                }
                                if (!phoneNumberField.getText().equals(mutableManager.getPhone())) {
                                    mutableManager.setPhone(phoneNumberField.getText());
                                    isUpdated = true;
                                }

                                if (!passwd.equals(mutableManager.getPassword())) {
                                    mutableManager.setPassword(passwd);
                                    isUpdated = true;
                                }
                                if(!contextPath.equals(mutableManager.getAvatarImg())){
                                    mutableManager.setAvatarImg(contextPath);
                                    isUpdated =true;
                                }

                                if (isUpdated) {
                                    JOptionPane.showMessageDialog(null, "Information updated successfully!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "No changes were made!");
                                }

                            }
                        }

                        tabbedPaneAccManager.setSelectedIndex(0);// refresh table to load new data:)
                        reloadManagerData();
                        clearField();
                    });


                    undoBt = new CustomButton("Undo");
                    undoBt.setPreferredSize(new Dimension(100, 40));
                    undoBt.setDrawBorder(false);
                    undoBt.addActionListener(
                            e -> {
                                if( tableStatus == MODIFY){
                                    setDataToModify(managers.get(managerTable.getSelectedRow()));
                                }else clearField();
                            });

                    cancelBt = ButtonConfig.createCustomButton("Cancel",Style.FONT_BOLD_16,Color.BLACK,
                            Style.LIGHT_RED,Style.LIGHT_RED,15,SwingConstants.CENTER,new Dimension(100, 40));
                    cancelBt.addActionListener(
                            e -> {
                                clearField();
                            });

                    uploadImagePn.add(undoBt);
                    uploadImagePn.add(cancelBt);
                    uploadImagePn.add(confirmBt);
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

        private class OrderStatisticsTable extends JPanel{
            private CustomButton sortByOrderBt, sortByTotalPriceBt, sortProcessedBt, selectedBt;
            private JTable statisticsTable;
            private DefaultTableModel statisticsTableModel;
            private JScrollPane scrollPaneStatisticsTable;
            private static final String[] statisticsColumnNames = {"SN","Manager ID","Manager Name", "Order ID","Total Orders", "Total product quantity","Total Price (VND)","Total Processed Orders"};
            OrderStatisticsTable(){
                setLayout(new BorderLayout());
                JPanel sortBarPn = new JPanel(new FlowLayout(FlowLayout.LEFT));

                sortByOrderBt = ButtonConfig.createCustomButton(
                        "Order Quantity",
                        Style.FONT_BOLD_15,
                        Color.BLACK,
                        Style.MENU_BUTTON_COLOR,
                        Style.LIGHT_BlUE,
                        Style.MENU_BUTTON_COLOR,
                        2,
                        25,
                        SwingConstants.CENTER,
                        new Dimension(180, 25));
                sortByOrderBt.addActionListener(e ->{
                    updateSelectedButtonColor(sortByOrderBt);
                    reloadStatisticsData((ArrayList<Manager>) LoginFrame.COMPUTER_SHOP.sortManagerByOrder());
                });

                sortByTotalPriceBt = ButtonConfig.createCustomButton(
                        "Order value",
                        Style.FONT_BOLD_15,
                        Color.BLACK,
                        Color.white,
                        Style.LIGHT_BlUE,
                        Style.MENU_BUTTON_COLOR,
                        2,
                        25,
                        SwingConstants.CENTER,
                        new Dimension(180, 25));
                sortByTotalPriceBt.addActionListener(e ->{
                    updateSelectedButtonColor(sortByTotalPriceBt);
                    reloadStatisticsData((ArrayList<Manager>) LoginFrame.COMPUTER_SHOP.sortManagerByTotalPrice());
                });

                sortProcessedBt = ButtonConfig.createCustomButton(
                        "Total processed orders",
                        Style.FONT_BOLD_15,
                        Color.BLACK,
                        Color.white,
                        Style.LIGHT_BlUE,
                        Style.MENU_BUTTON_COLOR,
                        2,
                        25,
                        SwingConstants.CENTER,
                        new Dimension(220, 25));
                sortProcessedBt.addActionListener(e ->{
                    updateSelectedButtonColor(sortProcessedBt);
                    reloadStatisticsData((ArrayList<Manager>) LoginFrame.COMPUTER_SHOP.sortManagerByProcessedOrders());
                });


                sortBarPn.add(sortByOrderBt);
                sortBarPn.add(sortByTotalPriceBt);
                sortBarPn.add(sortProcessedBt);
                add(sortBarPn,BorderLayout.NORTH);

                statisticsTable = createTable(statisticsTableModel, statisticsColumnNames);
                statisticsTable.setRowHeight(40);
                resizeColumnWidth(statisticsTable, 180);
                statisticsTableModel = (DefaultTableModel) statisticsTable.getModel();
                statisticsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
                statisticsTable.getColumnModel().getColumn(1).setPreferredWidth(90);


                reloadStatisticsData(managers);
                scrollPaneStatisticsTable = new JScrollPane(statisticsTable);
                add(scrollPaneStatisticsTable, BorderLayout.CENTER);
            }
            private void reloadStatisticsData(ArrayList<Manager> list) {
                statisticsTableModel.setRowCount(0);
                int count =1;
                for (Manager manager : list) {
                    Object[] rowData = {
                            count++,
                            manager.getId(),
                            manager.getFullName(),
                            manager.getAllOrderID(),
                            manager.getTotalOrders(),
                            manager.totalProductQuantity(),
                            currencyFormatter.format(manager.totalProductPrice()),
                            manager.totalOrderCompleted()

                    };
                    statisticsTableModel.addRow(rowData);
                }
            }

            private void updateSelectedButtonColor(CustomButton button) {
                Color defaultColor = Color.WHITE;
                Color selectedColor = Style.MENU_BUTTON_COLOR;

                if(selectedBt == null){
                    sortByOrderBt.setBackgroundColor(defaultColor);
                    sortByOrderBt.setHoverColor(Style.LIGHT_BlUE);
                }

                if (selectedBt != null ) {
                    selectedBt.setBackgroundColor(defaultColor);
                    selectedBt.setHoverColor(Style.LIGHT_BlUE);
                }

                button.setBackgroundColor(selectedColor);
                button.setHoverColor(selectedColor);
                selectedBt = button;
            }

        }

        private class StatisticsChartPanel extends JPanel{
            private JFreeChart performanceChart,kpiChart;

            private Map<Manager,Double> kpiData = LoginFrame.COMPUTER_SHOP.analyzeRevenueByManager();
            private Map<Manager,Integer> ordersDataByManager = LoginFrame.COMPUTER_SHOP.analyzeOrdersByManager();

            StatisticsChartPanel(){
                setLayout(new GridLayout(1,2));

                DefaultCategoryDataset kpiDataset = new DefaultCategoryDataset();
                for (Map.Entry<Manager,Double> entry : kpiData.entrySet()) {
                    kpiDataset.addValue(entry.getValue(),
                            entry.getKey().getFullName(), entry.getKey().getFullName());
                }
                kpiChart = ChartFactory.createBarChart(
                        "Total Revenue by Manager",
                        "Manager",
                        "Revenue (VND)",
                        kpiDataset
                );
                NumberAxis yAxis = (NumberAxis) kpiChart.getCategoryPlot().getRangeAxis();
                yAxis.setNumberFormatOverride(formatCurrency); // Format currency to VND

                CategoryPlot plot = kpiChart.getCategoryPlot();
                plot.setBackgroundPaint(Style.CHART_BACKGROUND_COLOR);
                BarRenderer renderer = (BarRenderer) plot.getRenderer();

                renderer.setBarPainter(new StandardBarPainter());
                renderer.setDrawBarOutline(false);

                ChartPanel kpiChartPanel = new ChartPanel(kpiChart);
                add(kpiChartPanel);

                DefaultPieDataset performanceDataset = new DefaultPieDataset();
                for (Map.Entry<Manager, Integer> entry : ordersDataByManager.entrySet()) {
                    performanceDataset.setValue(entry.getKey().getFullName(), entry.getValue());
                }
                performanceChart = ChartFactory.createPieChart(
                        "Manager Performance Chart",
                        performanceDataset,
                        true,
                        true,
                        true
                );

                PiePlot ordersPlot = (PiePlot) performanceChart.getPlot();
                ordersPlot.setBackgroundPaint(Style.CHART_BACKGROUND_COLOR);
                ordersPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                        "{0}: {2}",
                        new DecimalFormat("0"),
                        new DecimalFormat("0.00%")
                ));

                ChartPanel chartPanel = new ChartPanel(performanceChart);
                chartPanel.setBorder(new MatteBorder(0,1,0,0,Color.GRAY));
                add(chartPanel);

            }
        }

        private void reloadManagerData() {
            loadDataTable(managers, modelAccManager);
        }

        private void clearField() {
          fullNameField.setText("");
          addressField.setText("");
          birthdayField.setText("");
          phoneNumberField.setText("");
          usernameField.setText("");
          passwordField.setText("");
          emailField.setText("");
          label.setIcon(null);
          label.setText("Drop your image here");
          addAccBt.setEnabled(true);
      }

        private void setVisiblePanel(int panel) {
          tabbedPaneAccManager.setSelectedIndex(panel);
      }

        private void setDataToModify(Manager manager) {
          fullNameField.setText(manager.getFullName());
          addressField.setText(manager.getAddress());
          birthdayField.setText(manager.getDob().format(dateFormatter));
          phoneNumberField.setText(manager.getPhone());

          usernameField.setText(manager.getFullName());
          passwordField.setText(manager.getPassword());
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

        private static void loadDataTable(ArrayList<Manager> managers, DefaultTableModel model){
            model.setRowCount(0);
            int count =1;
            for (Manager manager : managers) {
                Object[] rowData = {
                        count++,
                        manager.getId(),
                        manager.getFullName(),
                        manager.getEmail(),
                        manager.getAddress(),
                        manager.getPhone(),
                        manager.getDob().format(dateFormatter),
                        manager.getCreatedAt().format(dateFormatter),
                        createImageForProduct(manager.getAvatarImg(),100,100),
                };
                model.addRow(rowData);
            }
        }
    }

  private class NotificationPanel extends JPanel {
    private JScrollPane scrollPane;
    private List<Order> orders;
    private Timer timer;

    public NotificationPanel() {
      setLayout(new BorderLayout());

      notificationContainer = new JPanel();
      notificationContainer.setBackground(Color.WHITE);
      notificationContainer.setLayout(new BoxLayout(notificationContainer, BoxLayout.Y_AXIS));

      addAllNotification();

      scrollPane = new JScrollPane(notificationContainer);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      setColorScrollPane(scrollPane, Style.BACKGROUND_COLOR, Style.LIGHT_BlUE);
      add(scrollPane, BorderLayout.CENTER);

      startTimer();
    }

    public void reloadNotification() {
      orders = getAllOrders();
      notificationContainer.removeAll();
      addAllNotification();
      notificationContainer.revalidate();
      notificationContainer.repaint();
    }

    private void startTimer() {
      timer = new Timer(30000, e -> reloadNotification());
      timer.start();
    }

    private void addAllNotification() {
      orders = getAllOrders();

      JPanel main = new JPanel(new GridBagLayout());
      main.setBackground(Color.WHITE);
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 15, 5, 15);
      gbc.anchor = GridBagConstraints.WEST;
      int x = 0, y = 0;
      if (!orders.isEmpty()) {
        for (Order order : orders) {
          Customer customer = order.getCustomer();

          StringBuilder notificationText = new StringBuilder();
          notificationText.append(
              String.format(
                  "New Orders from %s\nOrder ID: %d\nOrder Date: %s\nShipping Address: %s\n-----------------------------\n",
                      customer.getFullName(),
                      order.getOrderId(),
                      order.getOrderedAt().toString(),
                      order.getShipAddress()));
          for (OrderDetail orderDetail : order.getOrderDetails()) {
            notificationText.append(
                String.format(
                    "\nOrder Status: %s\nProduct Name: %s\nQuantity: %d\n-----------------------------\n",
                        order.getStatus(),
                        orderDetail.getProductName(),
                        orderDetail.getQuantity()));
          }
          notificationText.append(
              String.format("\nTotal: %s VNĐ", NumberFormat.getInstance(new Locale("vi", "VN")).format(order.totalCost())));

          CircularImage avatar = new CircularImage(customer.getAvatarImg(), 80, 80, false);
          JLabel timeLabel = new JLabel(String.format("<html>%s</html>", order.getOrderedAt().toString()));

          JTextArea message = createMessageArea(notificationText.toString());
          JScrollPane scrollPane = createScrollPane(message);

          gbc.gridx = x;
          gbc.gridy = y;
          gbc.anchor = GridBagConstraints.WEST;
          main.add(avatar, gbc);

          gbc.gridx = x;
          gbc.gridy = ++y;
          gbc.anchor = GridBagConstraints.EAST;
          main.add(scrollPane, gbc);

          gbc.gridx = x + 1;
          main.add(timeLabel, gbc);
          y++;
        }
      } else {
        JPanel emptyNotificationPn = new JPanel(new BorderLayout());
        emptyNotificationPn.setBackground(Color.WHITE);
        emptyNotificationPn.add(
            new JLabel(
                createImageForProduct("src/main/java/img/no_Notification_Img.png", 500, 500)));
        main.add(emptyNotificationPn, gbc);
      }

      notificationContainer.add(main);
      notificationContainer.revalidate();
      notificationContainer.repaint();
    }

    private JTextArea createMessageArea(String text) {
      JTextArea message = new JTextArea(text);
      message.setBackground(Color.WHITE);
      message.setForeground(Color.BLACK);
      message.setFont(new Font("Arial", Font.PLAIN, 16));
      message.setBorder(
          BorderFactory.createCompoundBorder(
              new RoundedBorder(20, 2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE),
              BorderFactory.createEmptyBorder(3, 3, 3, 8)));
      message.setLineWrap(true);
      message.setWrapStyleWord(true);
      message.setEditable(false);
      message.setOpaque(true);
      message.setPreferredSize(new Dimension(600, message.getPreferredSize().height));
      return message;
    }

    private JScrollPane createScrollPane(JTextArea message) {
      JScrollPane scrollPane = new JScrollPane(message);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      scrollPane.setBorder(BorderFactory.createEmptyBorder());
      return scrollPane;
    }

    private List<Order> getAllOrders() {
      return LoginFrame.COMPUTER_SHOP.getAllOrders().stream()
              .filter(Order::isActive)
              .toList();
    }
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
          emailField, new InputVerifier[] {new NotEmptyVerifier(), new EmailVerifier()});
      fieldVerifierMap.put(
          fullNameField,
          new InputVerifier[] {new NotEmptyVerifier()});
      fieldVerifierMap.put(addressField, new InputVerifier[] {new NotEmptyVerifier()});
      fieldVerifierMap.put(
          phoneNumField, new InputVerifier[] {new NotEmptyVerifier(), new PhoneNumberVerifier()});
      fieldVerifierMap.put(
          dateOfBirthField, new InputVerifier[] {new NotEmptyVerifier(), new BirthDayVerifier()});
      fieldVerifierMap.put(createDateField, new InputVerifier[] {new NotEmptyVerifier()});

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
        User user = LoginFrame.COMPUTER_SHOP.findManagerByEmail(CurrentUser.CURRENT_MANAGER_V2.getEmail());
        String[] data = {avatar.getImagePath(),
                        emailField.getText().trim(),
                        createDateField.getText().trim(),
                        fullNameField.getText().trim(),
                        addressField.getText().trim(),
                        dateOfBirthField.getText().trim(),
                        phoneNumField.getText().trim()
        };
        user.update(data);
      ToastNotification.showToast(
          "Your information has been successfully updated.", 2500, 50, -1, -1);
    }
  }

  private <M> void exportExcel(List<M> dataList, String[] headers) {
    String fileName = JOptionPane.showInputDialog("Enter the name of the Excel file:");
    if (fileName != null && !fileName.trim().isEmpty()) {
      fileName = fileName.trim().endsWith(".xlsx") ? fileName.trim() : fileName.trim() + ".xlsx";
      ExcelConfig.exportToExcel(dataList, fileName, headers);
      JOptionPane.showMessageDialog(null, "Exported to " + fileName);
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
      table.getColumnModel().getColumn(0).setPreferredWidth(120);
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

  public void setProductButtonListener(){

  }


}
