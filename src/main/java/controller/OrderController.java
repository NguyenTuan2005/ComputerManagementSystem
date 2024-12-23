package controller;

import Enum.*;
import Model.Order;
import dao.OrderDAO;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderController implements ModelController<Order> {

  private OrderDAO orderDAO;

  public OrderController() {
    this.orderDAO = new OrderDAO();
  }

  @Override
  public ArrayList<Order> find(String name) {
    return null;
  }

  @Override
  public Order findById(int id) {
    return null;
  }

  @Override
  public ArrayList<Order> getAll() {
    return null;
  }

  @Override
  public ArrayList<Order> reloadData() {
    return null;
  }

  @Override
  public void setDeleteRow(int id, boolean status) {}

  @Override
  public void saves(ArrayList<Order> m) {}

  @Override
  public void save(Order order) {
    orderDAO.save(order);
  }

  @Override
  public void update(Order order) {}

  @Override
  public ArrayList<Order> sortByColumn(String column) {
    return null;
  }

  public boolean updateStatusOrder(OrderType type, int orderId) {
    return orderDAO.updateOrderStatus(type, orderId);
  }

  public int save(int customerId, int managerId, String address, String status) {
    LocalDate localDate = LocalDate.now();
    java.sql.Date orderDate = java.sql.Date.valueOf(localDate);
    //        LocalDate updatedLocalDate = localDate.plusDays(5);
    //        java.sql.Date shipDate = java.sql.Date.valueOf(updatedLocalDate);
    Order order = new Order();
    order.setOrderDate(orderDate);
    order.setShipAddress(address);
    order.setStatus(status);
    order.setManagerId(managerId);
    order.setCustomerId(customerId);
    return orderDAO.save(order).getId();
  }
}
