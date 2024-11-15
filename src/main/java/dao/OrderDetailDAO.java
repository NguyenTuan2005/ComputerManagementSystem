package dao;

import Config.DatabaseConfig;
import Model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailDAO implements Repository<OrderDetail> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public OrderDetailDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private OrderDetail resutlOrderDetail(ResultSet rs) throws SQLException {
         return  new OrderDetail(
                rs.getInt("order_id"),
                rs.getInt("product_id"),
                rs.getInt("unit_price"),
                rs.getInt("quantity")
        );
    }

    // Lưu chi tiết đơn hàng mới vào cơ sở dữ liệu
    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        String query = "INSERT INTO public.order_detail (order_id, product_id, unit_price, quantity) VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderDetail.getOrderId());
            preparedStatement.setInt(2, orderDetail.getProductId());
            preparedStatement.setDouble(3, orderDetail.getUnitPrice());
            preparedStatement.setInt(4, orderDetail.getQuantity());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return orderDetail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm chi tiết đơn hàng theo ID
    @Override
    public OrderDetail findById(int id) {
        OrderDetail orderDetail = null;
        String query = "SELECT * FROM public.order_detail WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                orderDetail = resutlOrderDetail(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }

    // Lấy tất cả các chi tiết đơn hàng
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM public.order_detail";
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = resutlOrderDetail(rs);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    // Tìm chi tiết đơn hàng theo tên sản phẩm (liên kết với bảng sản phẩm nếu cần)
    @Override
    public ArrayList<OrderDetail> findByName(String name) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM public.order_detail WHERE product_id IN (SELECT id FROM public.products WHERE name LIKE ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = resutlOrderDetail(rs);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    // Tìm một chi tiết đơn hàng theo tên sản phẩm
    @Override
    public OrderDetail findOneByName(String name) {
        OrderDetail orderDetail = null;
        String query = "SELECT * FROM public.order_detail WHERE product_id IN (SELECT id FROM public.products WHERE name = ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                orderDetail = resutlOrderDetail(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }

    // Cập nhật thông tin chi tiết đơn hàng
    @Deprecated
    @Override
    public OrderDetail update(OrderDetail orderDetail) {
        String query = "UPDATE public.order_detail SET order_id = ?, product_id = ?, unit_price = ?, quantity = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderDetail.getOrderId());
            preparedStatement.setInt(2, orderDetail.getProductId());
            preparedStatement.setDouble(3, orderDetail.getUnitPrice());
            preparedStatement.setInt(4, orderDetail.getQuantity());


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return orderDetail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa chi tiết đơn hàng theo ID
    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM public.order_detail WHERE id = ?";
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

    @Override
    public ArrayList<OrderDetail> sortByColumn(String column) {
        return null;
    }
}
