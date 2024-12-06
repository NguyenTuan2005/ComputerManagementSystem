package controller;

import Config.ProductOrderConfig;
import Model.OrderDetail;
import dao.OrderDetailDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderDetailController implements ModelController<OrderDetail> {
    private OrderDetailDAO orderDetailDAO;

    public OrderDetailController() {
        this.orderDetailDAO = new OrderDetailDAO();
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
    public void setDeleteRow(int id, boolean status) {

    }

    @Override
    public void saves(ArrayList<OrderDetail> m) {

    }

    @Override
    public void save(OrderDetail orderDetail) {
        this.orderDetailDAO.save(orderDetail);
    }

    @Override
    public void update(OrderDetail orderDetail) {

    }

    @Override
    public ArrayList<OrderDetail> sortByColumn(String column) {
        return null;
    }

    public void saves(int orderId, Set<ProductOrderConfig> orderConfig){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        for (ProductOrderConfig po : orderConfig){
            if (po.getQuatity() != 0) {
                orderDetail.setQuantity(po.getQuatity());
                orderDetail.setUnitPrice(po.getProduct().getPrice());
                orderDetail.setProductId(po.getProduct().getId());
                this.save(orderDetail);
            }
        }
    }
}
