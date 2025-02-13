package model;

import enums.OrderType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import view.ManagerMainPanel;

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
        .flatMap(
            entry ->
                entry.getValue().stream()
                    .map(
                        order ->
                            new String[] {
                              String.valueOf(order.orderId),
                              order.getCustomer().getFullName(),
                              order.shipAddress,
                              String.valueOf(order.orderedAt),
                              order.status,
                              entry.getKey().getFullName(),
                              String.valueOf(entry.getKey().getId()),
                              ManagerMainPanel.currencyFormatter.format(order.totalCost()),
                              String.valueOf(order.totalQuantity())
                            }))
        .toArray(String[][]::new);
  }

  public int totalQuantity() {
    return this.orderDetails.stream().mapToInt(OrderDetail::getQuantity).sum();
  }

  public double totalCost() {
    return this.orderDetails.stream().mapToDouble(OrderDetail::totalCost).sum();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof model.Order)) return false;
    else {
      model.Order that = (model.Order) obj;
      return this.orderId == that.orderId
          && this.customer.equals(that.customer)
          && this.shipAddress.equals(that.shipAddress)
          && this.orderedAt.isEqual(that.orderedAt)
          && this.status.equals(that.status)
          && this.orderDetails.equals(that.orderDetails);
    }
  }

  public boolean containText(String searchText) {
    return String.valueOf(orderId).contains(searchText)
        || this.customer.getFullName().contains(searchText)
        || (orderedAt != null && orderedAt.toString().toLowerCase().contains(searchText))
        || (shipAddress != null && shipAddress.toLowerCase().contains(searchText))
        || (status != null && status.toLowerCase().contains(searchText))
        || this.orderDetails.stream()
            .anyMatch(
                o ->
                    (searchText).contains(String.valueOf(o.totalCost()))
                        || searchText.contains(String.valueOf(o.getQuantity())));
  }

  public boolean isDispatched() {
    return this.status.equals(OrderType.DISPATCHED_MESSAGE);
  }

  public boolean sameID(int id) {
    return this.orderId == id;
  }

  public void updateStatus(String status) {
    this.status = status;
  }

  public void updateSupplier(Supplier supplier) {
    for (var orderDetail : this.orderDetails) {
      orderDetail.updateSupplier(supplier);
    }
  }

  public boolean isCurrentMonth() {
    return this.orderedAt.getMonth().equals(LocalDate.now().getMonth());
  }

  public boolean of(Customer customer) {
    return this.customer.equals(customer);
  }

  public boolean isCustomer(Customer customer) {
    return this.customer.equals(customer);
  }

  public void changeOrderedAt(LocalDate localDate) {
    this.orderedAt = localDate;
  }

  public boolean isActive() {
    return this.status.equals(OrderType.ACTIVE_MESSAGE);
  }

  public boolean isUnActive() {
    return this.status.equals(OrderType.UN_ACTIVE_MESSAGE);
  }

  public boolean orderedAt(LocalDate orderedAt) {
    return this.orderedAt.equals(orderedAt);
  }
}
