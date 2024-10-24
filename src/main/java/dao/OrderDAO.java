package dao;

import Config.DatabaseConfig;
import Model.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO implements Repository<Order> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public OrderDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Order resultOrder(ResultSet rs) throws SQLException {
        return  new Order(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("manager_id"),
                rs.getString("ship_address"),
                rs.getDate("orderdata"),
                rs.getString("status")
        );
    }

    @Override
    public Order save(Order order) {
        String query = "INSERT INTO public.order (manager_id, customer_id, orderdata, ship_address, status) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getManagerId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setDate(3, (Date) order.getOrderDate());
            preparedStatement.setString(4, order.getShipAddress());
            preparedStatement.setString(5, order.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                }
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm đơn hàng theo ID
    @Override
    public Order findById(int id) {
        Order order = null;
        String query = "SELECT * FROM public.order WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                order = resultOrder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // Lấy tất cả các đơn hàng
    @Override
    public ArrayList<Order> getAll() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM public.order";
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Order order =  resultOrder(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Tìm đơn hàng theo tên khách hàng (nếu cần tìm qua tên customer_id hoặc mối liên hệ khác)
    @Override
    public ArrayList<Order> findByName(String name) {
        ArrayList<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM public.order WHERE customer_id IN (SELECT id FROM public.customers WHERE name LIKE ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Order order = resultOrder(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Tìm một đơn hàng chính xác theo tên (cũng liên quan đến khách hàng)
    @Override
    public Order findOneByName(String name) {
        Order order = null;
        String query = "SELECT * FROM public.order WHERE customer_id IN (SELECT id FROM public.customers WHERE name = ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                order = resultOrder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // Cập nhật thông tin đơn hàng
    @Override
    public Order update(Order order) {
        String query = "UPDATE public.order SET manager_id = ?, customer_id = ?, orderdata = ?, ship_address = ?, status = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getManagerId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setDate(3, (Date) order.getOrderDate());
            preparedStatement.setString(4, order.getShipAddress());
            preparedStatement.setString(5, order.getStatus());
            preparedStatement.setInt(6, order.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa đơn hàng theo ID
    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM public.order WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
