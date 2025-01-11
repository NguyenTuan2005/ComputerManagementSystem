package model;

import enums.OrderType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager extends User {
  List<Order> orders = new ArrayList<>();

  public Map<Customer, List<Order>> userOrderStatistics() {
    Map<Customer, List<Order>> map = new HashMap<>();
    for (var order : this.orders) {
      var v = map.getOrDefault(order.getCustomer(), new ArrayList<>());
      v.add(order);
      map.put(order.getCustomer(), v);
    }
    return map;
  }

  public long getQuantityInOrders(Supplier supplier) {
    long count =
        this.orders.stream()
            .map(Order::getOrderDetails)
            .map(
                orderDetail ->
                    orderDetail.stream().map(OrderDetail::getProduct).collect(Collectors.toList()))
            .flatMap(List::stream)
            .filter(p -> p.sameSupplier(supplier))
            .count();
    return count;
  }

  public int getTotalOrders() {
    return this.orders.size();
  }

  @Override
  public Map<Product, Integer> productSoldStatistic() {
    return this.orders.stream()
        .map(Order::getOrderDetails)
        .flatMap(List::stream)
        .collect(
            Collectors.groupingBy(
                OrderDetail::getProduct, Collectors.summingInt(OrderDetail::getQuantity)));
  }

  public void addOrder(Order order) {
    this.orders.add(order);
  }

  public void addOrder(List<Order> order) {
    this.orders.addAll(order);
  }

  public void removeOrder(Order order) {
    this.orders.remove(order);
  }

  public void updateStatus(Order order, String status) {
    this.orders.get(this.orders.indexOf(order)).updateStatus(status);
  }

  public boolean findName(String name) {
    return this.fullName.toLowerCase().contains(name.trim().toLowerCase());
  }

  @Override
  public boolean isManager() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof model.Manager)) return false;
    else {
      model.Manager that = (model.Manager) o;
      return this.orders.equals(that.orders);
    }
  }

  public static Object[][] getDataOnTable(ArrayList<Customer> customers) {
    Object[][] datass = new Object[customers.size()][];
    for (int i = 0; i < customers.size(); i++) {
      datass[i] = customers.get(i).convertToObjects(i + 1);
    }
    return datass;
  }

  public List<Order> filter(String status, String searchText) {
    return this.orders.stream()
        .filter(
            order -> {
              boolean matchesSearchText =
                  searchText == null
                      || searchText.isEmpty()
                      || order.containText(searchText)
                      || searchText.contains(String.valueOf(this.id))
                      || searchText.contains(this.fullName);

              boolean matchesStatus = false;
              if (status != null) {
                matchesStatus =
                    switch (status) {
                      case OrderType.ACTIVE_MESSAGE -> order.isActive();
                      case OrderType.UN_ACTIVE_MESSAGE -> order.isUnActive();
                      case OrderType.DISPATCHED_MESSAGE -> order.isDispatched();
                      default -> false;
                    };
              } else {
                matchesStatus = true;
              }

              return matchesSearchText && matchesStatus;
            })
        .toList();
  }

  public void updateSupplier(Supplier supplier) {
    for (var order : this.orders) {
      order.updateSupplier(supplier);
    }
  }

  public long getQuantityOfProduct(Product p) {
    return this.orders.stream()
        .filter(order -> order.isCurrentMonth())
        .map(order -> order.getOrderDetails())
        .flatMap(List::stream)
        .map(orderDetail -> orderDetail.getProduct())
        .filter(product -> product.sameName(p))
        .collect(Collectors.counting());
  }

  public long getTotalOrderOfCustomer(Customer customer) {
    return this.orders.stream()
        .filter(order -> order.isCurrentMonth() && order.of(customer))
        .map(order -> order.getOrderDetails())
        .flatMap(List::stream)
        .collect(Collectors.counting());
  }

  public String getAllOrderID() {
    String result = "";
    for (var order : this.orders) {
      result += order.getOrderId() + " | ";
    }
    return result;
  }

  public long totalOrderCompleted() {
    return this.orders.stream().filter(order -> order.isDispatched()).count();
  }
  // calculate total quantity of all product in order list
  public int totalProductQuantity() {
    return this.orders.stream().mapToInt(order -> order.totalQuantity()).sum();
  }
  // calculate total price of all product in order list
  public double totalProductPrice() {
    return this.orders.stream().mapToDouble(order -> order.totalCost()).sum();
  }
}
