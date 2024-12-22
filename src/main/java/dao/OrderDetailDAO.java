package dao;

import Config.DatabaseConfig;
import Model.OrderDetail;
import java.sql.*;
import java.util.ArrayList;
import lombok.SneakyThrows;

public class OrderDetailDAO implements Repository<OrderDetail> {

  private DatabaseConfig databaseConfig;

  private Connection connection;

  private Statement statement;

  private PreparedStatement preparedStatement;

  @SneakyThrows
  public OrderDetailDAO() {

    this.databaseConfig = new DatabaseConfig();
    this.connection = databaseConfig.getConnection();
  }

  private OrderDetail resutlOrderDetail(ResultSet rs) throws SQLException {
    return new OrderDetail(
        rs.getInt("order_id"),
        rs.getInt("product_id"),
        rs.getInt("unit_price"),
        rs.getInt("quantity"));
  }

  @Override
  @SneakyThrows
  public OrderDetail save(OrderDetail orderDetail) {
    String query =
        "INSERT INTO public.order_detail (order_id, product_id, unit_price, quantity) VALUES (?, ?, ?, ?)";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, orderDetail.getOrderId());
    preparedStatement.setInt(2, orderDetail.getProductId());
    preparedStatement.setDouble(3, orderDetail.getUnitPrice());
    preparedStatement.setInt(4, orderDetail.getQuantity());

    int rowsAffected = preparedStatement.executeUpdate();
    if (rowsAffected > 0) {
      return orderDetail;
    }

    return null;
  }

  @Override
  @SneakyThrows
  public OrderDetail findById(int id) {
    OrderDetail orderDetail = null;
    String query = "SELECT * FROM public.order_detail WHERE id = ?";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, id);
    ResultSet rs = preparedStatement.executeQuery();

    if (rs.next()) {
      orderDetail = resutlOrderDetail(rs);
    }

    return orderDetail;
  }

  @Override
  @SneakyThrows
  public ArrayList<OrderDetail> getAll() throws SQLException {
    ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    String query = "SELECT * FROM public.order_detail";

    preparedStatement = connection.prepareStatement(query);
    ResultSet rs = preparedStatement.executeQuery();

    while (rs.next()) {
      OrderDetail orderDetail = resutlOrderDetail(rs);
      orderDetails.add(orderDetail);
    }

    return orderDetails;
  }

  @Override
  @SneakyThrows
  public ArrayList<OrderDetail> findByName(String name) {
    ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    String query =
        "SELECT * FROM public.order_detail WHERE product_id IN (SELECT id FROM public.products WHERE name LIKE ?)";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, "%" + name + "%");
    ResultSet rs = preparedStatement.executeQuery();

    while (rs.next()) {
      OrderDetail orderDetail = resutlOrderDetail(rs);
      orderDetails.add(orderDetail);
    }

    return orderDetails;
  }

  @Override
  @SneakyThrows
  public OrderDetail findOneByName(String name) {
    OrderDetail orderDetail = null;
    String query =
        "SELECT * FROM public.order_detail WHERE product_id IN (SELECT id FROM public.products WHERE name = ?)";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, name);
    ResultSet rs = preparedStatement.executeQuery();

    if (rs.next()) {
      orderDetail = resutlOrderDetail(rs);
    }

    return orderDetail;
  }

  @Deprecated
  @Override
  @SneakyThrows
  public OrderDetail update(OrderDetail orderDetail) {
    String query =
        "UPDATE public.order_detail SET order_id = ?, product_id = ?, unit_price = ?, quantity = ? WHERE id = ?";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, orderDetail.getOrderId());
    preparedStatement.setInt(2, orderDetail.getProductId());
    preparedStatement.setDouble(3, orderDetail.getUnitPrice());
    preparedStatement.setInt(4, orderDetail.getQuantity());

    int rowsAffected = preparedStatement.executeUpdate();
    if (rowsAffected > 0) {
      return orderDetail;
    }

    return null;
  }

  @Override
  @SneakyThrows
  public boolean remove(int id) {
    String query = "DELETE FROM public.order_detail WHERE id = ?";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, id);
    int rowsAffected = preparedStatement.executeUpdate();
    return rowsAffected > 0;
  }

  @Override
  public ArrayList<OrderDetail> sortByColumn(String column) {
    return null;
  }
}
