package controller;

import Config.CurrentUser;
import Config.ProductOrderConfig;
import Model.OrderDetail;
import dao.CustomerDAO;
import dao.OrderDetailDAO;
import dto.CustomerOrderDetailDTO;
import java.util.ArrayList;
import java.util.Set;

public class OrderDetailController implements ModelController<OrderDetail> {
  private OrderDetailDAO orderDetailDAO;
  private CustomerDAO customerDAO;

  public OrderDetailController() {
    this.orderDetailDAO = new OrderDetailDAO();
    this.customerDAO = new CustomerDAO();
  }

  @Override
  public ArrayList<OrderDetail> find(String name) {
    return null;
  }

  @Override
  public OrderDetail findById(int id) {
    return null;
  }

  @Override
  public ArrayList<OrderDetail> getAll() {
    return null;
  }

  @Override
  public ArrayList<OrderDetail> reloadData() {
    return null;
  }

  @Override
  public void setDeleteRow(int id, boolean status) {}

  @Override
  public void saves(ArrayList<OrderDetail> m) {}

  @Override
  public void save(OrderDetail orderDetail) {
    this.orderDetailDAO.save(orderDetail);
  }

  @Override
  public void update(OrderDetail orderDetail) {}

  @Override
  public ArrayList<OrderDetail> sortByColumn(String column) {
    return null;
  }

  public void saves(int orderId, Set<ProductOrderConfig> orderConfig) {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setOrderId(orderId);
    for (ProductOrderConfig po : orderConfig) {
      if (po.getQuatity() != 0) {
        orderDetail.setQuantity(po.getQuatity());
        orderDetail.setUnitPrice((int) po.getProduct().getPrice());
        orderDetail.setProductId(po.getProduct().getId());
        customerDAO.updateNumberOfPurchased(CurrentUser.CURRENT_CUSTOMER.getId(), po.getQuatity());
        this.save(orderDetail);
      }
    }
  }

  public void saves(int reOrderId, ArrayList<CustomerOrderDetailDTO> customerOrderDTOs) {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setOrderId(reOrderId);
    for (var po : customerOrderDTOs) {
      orderDetail.setQuantity(po.customerOrderDTO().getQuantity());
      orderDetail.setUnitPrice((int) po.customerOrderDTO().getUnitPrice());
      orderDetail.setProductId(po.getProductId());
      customerDAO.updateNumberOfPurchased(
          CurrentUser.CURRENT_CUSTOMER.getId(), po.customerOrderDTO().getQuantity());
      this.save(orderDetail);
    }
  }
}
