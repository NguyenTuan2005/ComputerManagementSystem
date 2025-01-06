package  entity;

import enums.OrderType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import view.ManagerMainPanel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    int orderId;
    Customer customer;
    String shipAddress;
    LocalDate orderedAt;
    String status;
    List<OrderDetail> orderDetails;

    public static String[][] getData(Map<Manager, List<Order>> orders) {
        return orders.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(
                        order -> new String[] {
                                String.valueOf(order.orderId),
                                order.getCustomer().getFullName(),
                                order.shipAddress,
                                String.valueOf(order.orderedAt),
                                order.status,
                                entry.getKey().getFullName(),
                                String.valueOf(entry.getKey().getId()),
                                ManagerMainPanel.formatCurrency.format(order.totalPrice()),
                                String.valueOf(order.totalQuantity())
                        }
                ))
                .toArray(String[][]::new);
    }

    private int totalQuantity() {
        return this.orderDetails.stream()
                .mapToInt(OrderDetail::getQuantity)
                .sum();
    }

    private double totalPrice() {
        return this.orderDetails.stream()
                .map(OrderDetail::getProduct)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof entity.Order))
            return false;
        else {
            entity.Order that = (entity.Order) obj;
            return this.orderId == that.orderId &&
                    this.customer.equals(that.customer) &&
                    this.shipAddress.equals(that.shipAddress) &&
                    this.orderedAt.isEqual(that.orderedAt) &&
                    this.status.equals(that.status) &&
                    this.orderDetails.equals(that.orderDetails);
        }
    }

    public boolean containText(String searchText) {
        return String.valueOf(orderId).contains(searchText)
                || this.customer.contains(searchText)
                || (orderedAt != null && orderedAt.toString().toLowerCase().contains(searchText))
                || (shipAddress != null && shipAddress.toLowerCase().contains(searchText))
                || (status != null && status.toLowerCase().contains(searchText))
                || this.orderDetails.stream().anyMatch(o -> o.contains(searchText));
    }

    public boolean isDispatched() {
        return this.status.equals(OrderType.DISPATCHED_MESSAGE);
    }

    public boolean sameID(int id) {
        return this.orderId == id;
    }
}
