package dto;

import Config.EmailConfig;
import controller.CustomerController;
import lombok.*;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDTO {
    private int orderId;
//    private String customerName;
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
                .append("For inquiries, contact us at: " + EmailConfig.APP_EMAIL + "\n")
                .append("--------------------------------\n");

        return bill.toString();
    }





    public static void main(String[] args) {
        // Tạo danh sách sản phẩm
        ArrayList<CustomerOrderDTO> orders = new ArrayList<>();
        CustomerController c = new CustomerController();
        orders = c.findCustomerOrderById(3);

    }

    public boolean sameOderId(int id) {
        return  this.orderId == id;
    }
}