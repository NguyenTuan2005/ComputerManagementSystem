package dao;

import Config.DatabaseConfig;
import Model.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements Repository<Customer> {
    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public CustomerDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAccountParameter(PreparedStatement preparedStatement, Customer customer ) throws SQLException {
        preparedStatement.setString(1, customer.getFullName());
        preparedStatement.setString(2, customer.getEmail());
        preparedStatement.setString(3, customer.getAddress());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setString(5, customer.getAvataImg());
    }

    private Customer resultCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("password"),
                rs.getString("avata_img")
        );
    }

    @Override
    public Customer save(Customer customer) {
        try {
            String sql = "INSERT INTO customer (fullname, email, address, password,avata_img) VALUES (?, ?, ?, ?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setAccountParameter(preparedStatement,customer);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                customer.setId(rs.getInt(1));
            }
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer findById(int id) {
        try {
            String sql = "SELECT * FROM customer WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return resultCustomer(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAll()  {
        try {
            String sql = "SELECT * FROM customer";
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer customer = resultCustomer(rs);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Customer> findByName(String name) {
        try {
            String sql = "SELECT * FROM customer WHERE fullname LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(resultCustomer(rs));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer findOneByName(String name) {
        try {
            String sql = "SELECT * FROM customer WHERE fullname LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                 return resultCustomer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer findByEmail(String email ) {
        try {
            String sql = "SELECT * FROM customer WHERE email LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + email + "%");
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return resultCustomer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        try {
            String sql = "UPDATE customer SET fullname = ?, email = ?, address = ?, password = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            setAccountParameter(preparedStatement,customer);
            preparedStatement.setInt(5, customer.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return customer;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            String sql = "DELETE FROM customer WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Customer> getByColumn(String column) {
        return null;
    }

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        System.out.println(customerDAO.getAll());
//        System.out.println(customerDAO.save(new Customer("nguyen huu duy","duynguyenavg@gmail.com","tien giang , chau thành diem hy","123")));
    }
}
