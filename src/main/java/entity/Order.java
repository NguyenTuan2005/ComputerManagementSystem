package  entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

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

    public static String[][] getData(List<Order> orders) {
        return orders.stream()
                .map(order -> new String[] {
                        String.valueOf(order.orderId),
                        order.getCustomer().getFullName(),
                        order.shipAddress,
                        String.valueOf(order.orderedAt),
                        order.status
//                        String.valueOf(order.totalPrice()),
//                        order.totalQuantity()
                })
                .toArray(String[][]::new);
    }

//    private int totalPrice() {
//        return this.orderDetails.stream()
//                .map(OrderDetail::getProduct)
//                .mapToInt(Product::getPrice)
//                .sum();
//    }

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

}
