package dao;

import Config.DatabaseConfig;
import Model.Customer;
import dto.CustomerOrderDTO;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements Repository<Customer> {
  private DatabaseConfig databaseConfig;

  private Connection connection;

  private Statement statement;

  private PreparedStatement preparedStatement;

  @SneakyThrows
  public CustomerDAO() {

    this.databaseConfig = new DatabaseConfig();
    this.connection = databaseConfig.getConnection();
  }

  private void setAccountParameter(PreparedStatement preparedStatement, Customer customer)
      throws SQLException {
    preparedStatement.setString(1, customer.getFullName());
    preparedStatement.setString(2, customer.getEmail());
    preparedStatement.setString(3, customer.getAddress());
    preparedStatement.setString(4, customer.getPassword());
    preparedStatement.setString(5, customer.getAvataImg());
    preparedStatement.setInt(6, customer.getNumberOfPurchased());
  }

  private Customer resultCustomer(ResultSet rs) throws SQLException {
    return new Customer(
        rs.getInt("id"),
        rs.getString("fullname"),
        rs.getString("email"),
        rs.getString("address"),
        rs.getString("password"),
        rs.getString("avata_img"),
        rs.getInt("number_of_purchased"),
        rs.getInt("block"));
  }

  @Override
  @SneakyThrows
  public Customer save(Customer customer) {

    String sql =
        "INSERT INTO customer (fullname, email, address, password,avata_img,number_of_purchased) VALUES (?, ?, ?, ?,?,?)";
    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    setAccountParameter(preparedStatement, customer);
    preparedStatement.executeUpdate();
    ResultSet rs = preparedStatement.getGeneratedKeys();
    if (rs.next()) {
      customer.setId(rs.getInt(1));
    }
    return customer;
  }

  @Override
  @SneakyThrows
  public Customer findById(int id) {

    String sql = "SELECT * FROM customer WHERE id = ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, id);
    ResultSet rs = preparedStatement.executeQuery();
    if (rs.next()) {
      return resultCustomer(rs);
    }
    return null;
  }

  @Override
  @SneakyThrows
  public ArrayList<Customer> getAll() {

    String sql = "SELECT * FROM customer";
    statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    ArrayList<Customer> customers = new ArrayList<>();
    while (rs.next()) {
      Customer customer = resultCustomer(rs);
      customers.add(customer);
    }
    return customers;
  }

  @Override
  @SneakyThrows
  public ArrayList<Customer> findByName(String name) {

    String sql = "SELECT * FROM customer WHERE LOWER(fullname) LIKE lower(?)";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, "%" + name + "%");
    ResultSet rs = preparedStatement.executeQuery();
    ArrayList<Customer> customers = new ArrayList<>();
    while (rs.next()) {
      customers.add(resultCustomer(rs));
    }
    return customers;
  }

  @Override
  @SneakyThrows
  public Customer findOneByName(String name) {

    String sql = "SELECT * FROM customer WHERE fullname LIKE ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, "%" + name + "%");
    ResultSet rs = preparedStatement.executeQuery();

    if (rs.next()) {
      return resultCustomer(rs);
    }

    return null;
  }

  @SneakyThrows
  public Customer findByEmail(String email) {

    String sql = "SELECT * FROM customer WHERE email LIKE ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, "%" + email + "%");
    ResultSet rs = preparedStatement.executeQuery();

    if (rs.next()) {
      return resultCustomer(rs);
    }
    return null;
  }

  @Override
  @SneakyThrows
  public Customer update(Customer customer) {

    String sql =
        "UPDATE customer SET fullname = ?, email = ?, address = ?, password = ?,avata_img=? ,number_of_purchased =? WHERE id = ?";
    preparedStatement = connection.prepareStatement(sql);
    setAccountParameter(preparedStatement, customer);
    preparedStatement.setInt(7, customer.getId());
    int rowsAffected = preparedStatement.executeUpdate();
    if (rowsAffected > 0) {
      return customer;
    }
    return null;
  }

  @Override
  @SneakyThrows
  public boolean remove(int id) {

    String sql = "DELETE FROM customer WHERE id = ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, id);
    int rowsAffected = preparedStatement.executeUpdate();
    return rowsAffected > 0;
  }

  @SneakyThrows
  public List<CustomerOrderDTO> getAllCustomerOrder() {
    List<CustomerOrderDTO> orders = new ArrayList<>();
    String query = "SELECT * FROM customer_order_view";
    statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(query);
    while (rs.next()) {
      CustomerOrderDTO orderDTO = resultCustomerOrder(rs);
      orders.add(orderDTO);
    }
    return orders;
  }

  @SneakyThrows
  public ArrayList<CustomerOrderDTO> getDataCustomerOrderById(int customerId) {
    ArrayList<CustomerOrderDTO> orders = new ArrayList<>();
    String query =
        "SELECT DISTINCT  *  FROM customer_order_view  AS c  where c.customer_id = ? order by  c.order_date";

    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, customerId);
    ResultSet rs = preparedStatement.executeQuery();
    while (rs.next()) {
      CustomerOrderDTO order = resultCustomerOrder(rs);
      orders.add(order);
    }

    return orders;
  }

  private CustomerOrderDTO resultCustomerOrder(ResultSet rs) throws SQLException {
    CustomerOrderDTO order = new CustomerOrderDTO();
    order.setOrderId(rs.getInt("order_id"));
    order.setCustomerId(rs.getInt("customer_id"));
    order.setOrderDate(rs.getDate("order_date"));
    order.setShipAddress(rs.getString("ship_address"));
    order.setStatusItem(rs.getString("status_item"));
    order.setSaler(rs.getString("saler"));
    order.setSalerId(rs.getInt("saler_id"));
    order.setUnitPrice(rs.getDouble("unit_price"));
    order.setQuantity(rs.getInt("quantity"));
    order.setProductId(rs.getInt("product_id"));
    order.setProductName(rs.getString("product_name"));
    order.setProductGenre(rs.getString("product_genre"));
    order.setProductBrand(rs.getString("product_brand"));
    order.setOperatingSystem(rs.getString("operating_system"));
    order.setCpu(rs.getString("cpu"));
    order.setMemory(rs.getString("memory"));
    order.setRam(rs.getString("ram"));
    order.setMadeIn(rs.getString("made_in"));
    order.setDisk(rs.getString("disk"));
    order.setWeight(rs.getString("weight"));
    order.setMonitor(rs.getString("monitor"));
    order.setCard(rs.getString("card"));
    order.convertToEnum(rs.getString("status_item"));
    return order;
  }

  /***
   * If isBlock = true then block column update = 1 (Customer is blocked)
   * If isBlock = false then block column update = 0
   * @param isBlock
   * @param id
   */
  @SneakyThrows
  public void updateBlock(boolean isBlock, int id) {

    String sql = "UPDATE customer SET block = ? WHERE id = ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, isBlock ? 1 : 0);
    preparedStatement.setInt(2, id);
    preparedStatement.executeUpdate();
  }

  @SneakyThrows
  public void updateNumberOfPurchased(int id, int quantity) {

    String sql = "UPDATE customer SET number_of_purchased = number_of_purchased+ ?  WHERE id = ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, quantity);
    preparedStatement.setInt(2, id);
    preparedStatement.executeUpdate();
  }

  @Override
  public ArrayList<Customer> sortByColumn(String column) {
    return null;
  }

  @SneakyThrows
  public void updatePassword(String s, int id) {

    String sql = "UPDATE customer SET password = ? WHERE id = ?";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, s);
    preparedStatement.setInt(2, id);
    preparedStatement.executeUpdate();
  }
}
