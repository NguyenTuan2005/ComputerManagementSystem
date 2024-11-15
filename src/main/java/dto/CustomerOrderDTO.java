package dto;

import Config.EmailConfig;
import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDTO {
    private int customerId;
    private Date orderDate;
    private String shipAddress;
    private String statusItem;
    private String saler;
    private int salerId;
    private double unitPrice;
    private int quantity;
    private int productId;
    private String productName;
    private String productGenre;
    private String productBrand;
    private String operatingSystem;
    private String cpu;
    private String memory;
    private String ram;
    private String madeIn;
    private String disk;
    private String weight;
    private String monitor;
    private String card;


    public String toBillString() {
        StringBuilder bill = new StringBuilder();
        bill.append("----- Customer Order Bill -----\n")
                .append("Customer ID     : ").append(customerId).append("\n")
                .append("Order Date      : ").append(orderDate).append("\n")
                .append("Ship Address    : ").append(shipAddress).append("\n")
                .append("Order Status    : ").append(statusItem).append("\n")
                .append("\n")
                .append("Saler           : ").append(saler).append(" (ID: ").append(salerId).append(")\n")
                .append("\n")
                .append("Product Details:\n")
                .append("    Product ID  : ").append(productId).append("\n")
                .append("    Name        : ").append(productName).append("\n")
                .append("    Genre       : ").append(productGenre).append("\n")
                .append("    Brand       : ").append(productBrand).append("\n")
                .append("    OS          : ").append(operatingSystem).append("\n")
                .append("    CPU         : ").append(cpu).append("\n")
                .append("    Memory      : ").append(memory).append("\n")
                .append("    RAM         : ").append(ram).append("\n")
                .append("    Made In     : ").append(madeIn).append("\n")
                .append("    Disk        : ").append(disk).append("\n")
                .append("    Weight      : ").append(weight).append("\n")
                .append("    Monitor     : ").append(monitor).append("\n")
                .append("    Graphics    : ").append(card).append("\n")
                .append("\n")
                .append("Order Summary:\n")
                .append("    Unit Price  : $").append(String.format("%.2f", unitPrice)).append("\n")
                .append("    Quantity    : ").append(quantity).append("\n")
                .append("    Total Price : $").append(String.format("%.2f", unitPrice * quantity)).append("\n")
                .append("--------------------------------\n")
                .append("Thank you for your purchase!\n")
                .append("For inquiries, contact us at: "+ EmailConfig.APP_EMAIL +"\n")
                .append("--------------------------------\n");

        return bill.toString();
    }

    public static  String toBillsString(ArrayList<CustomerOrderDTO> customerOrderDTOS){
        String bill="";

        for (CustomerOrderDTO c : customerOrderDTOS) {
            bill += c.toBillString();
            bill +="\n";
        }

        return bill;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Tạo dữ liệu mẫu
            String[] columnNames = {"ID", "Name", "Status"};
            Object[][] data = {
                    {1, "Alice", "Active"},
                    {2, "Bob", "Inactive"},
                    {3, "Charlie", "Active"},
            };

            // Tạo mô hình bảng
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

            // Tùy chỉnh renderer cho cột "Status" (cột thứ 2)
            table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Đặt màu chữ (foreground) cho cột
                    if (!isSelected) {
                        c.setForeground(Color.BLUE); // Màu chữ xanh
                    } else {
                        c.setForeground(table.getSelectionForeground()); // Giữ màu chữ khi được chọn
                    }
                    return c;
                }
            });

            // Tạo giao diện
            JFrame frame = new JFrame("Text Color Column Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(table));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}