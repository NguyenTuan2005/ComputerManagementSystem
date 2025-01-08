package  entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager extends User{
    List<Order> orders = new ArrayList<>();

    public Map<Customer, List<Order>> userOrderStatistics(){
        Map<Customer, List<Order>> map = new HashMap<>();
        for ( var order : this.orders){
            var v = map.getOrDefault(order.getCustomer(),new ArrayList<>());
            v.add(order);
            map.put(order.getCustomer(), v);
        }
        return map;
    }

    public long getQuantityInOrders(Supplier supplier){
        long count = this.orders.stream()
                .map(Order::getOrderDetails)
                .map(orderDetail ->orderDetail.stream().map(OrderDetail::getProduct).collect(Collectors.toList()))
                .flatMap(List::stream)
                .filter(p ->p.sameSupplier(supplier))
                .count();
        return count;

    }


    @Override
    public Map<Product, Integer> productSoldStatistic() {
        return this.orders.stream()
                .map(Order::getOrderDetails)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(OrderDetail::getProduct, Collectors.summingInt(OrderDetail::getQuantity)));
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void addOrder(List<Order> order) {
        this.orders.addAll(order);
    }
    public void removeOrder(Order order){
        this.orders.remove(order);
    }
    public void updateStatus(Order order,String status){

    }

    @Override
    public boolean isManager() {
        return true;
    };

    @Override
    public boolean equals(Object o) {
        if (o==null || !(o instanceof entity.Manager))
            return false;
        else {
            entity.Manager that = (entity.Manager) o;
            return this.orders.equals(that.orders);
        }
    }

    public void updateSupplier(Supplier supplier){
        for (var order : this.orders){
            order. updateSupplier(supplier);
        }
    }
}
