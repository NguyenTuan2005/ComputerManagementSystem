package dao;

import Config.DatabaseConfig;
import Model.Supplier;

import java.sql.*;
import java.util.ArrayList;


public class SupplierDAO implements Repository<Supplier>{

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public SupplierDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Supplier resultSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getInt("id"),
                rs.getString("company_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("address"),
                rs.getDate("contact_date")
        );
    }
    

//select * from public.suppliers
// Phương thức để lưu một supplier mới vào CSDL
    @Override
    public Supplier  save(Supplier supplier) {
        String query = "INSERT INTO public.suppliers (company_name, email, phone_number, address, contact_date) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, supplier.getCompanyName());
            preparedStatement.setString(2, supplier.getEmail());
            preparedStatement.setString(3, supplier.getPhoneNumber());
            preparedStatement.setString(4, supplier.getAddress());
            preparedStatement.setDate(5, (Date) supplier.getContactDate());
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    supplier.setId(generatedKeys.getInt(1));
                }
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Phương thức để tìm Supplier theo ID
    @Override
    public Supplier findById(int id) {
        Supplier supplier = null;
        String query = "SELECT * FROM public.suppliers WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                supplier =resultSupplier( rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    // Phương thức để lấy tất cả Suppliers
    @Override
    public ArrayList<Supplier> getAll() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM public.suppliers";
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Supplier supplier =resultSupplier( rs);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Phương thức để tìm Suppliers theo tên công ty
    @Override
    public ArrayList<Supplier> findByName(String name) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM public.suppliers WHERE company_name LIKE ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Supplier supplier =resultSupplier( rs);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public Supplier findOneByName(String name) {
        Supplier supplier = null;
        String query = "SELECT * FROM public.suppliers WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                supplier =resultSupplier( rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }



    // Phương thức để cập nhật thông tin Supplier
    @Override
    public Supplier update(Supplier supplier) {

        String query = "UPDATE public.suppliers SET company_name = ?, email = ?, phone_number = ?, address = ?, contact_date = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplier.getCompanyName());
            preparedStatement.setString(2, supplier.getEmail());
            preparedStatement.setString(3, supplier.getPhoneNumber());
            preparedStatement.setString(4, supplier.getAddress());
            preparedStatement.setDate(5, (Date) supplier.getContactDate());
            preparedStatement.setInt(6, supplier.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức để xóa Supplier theo ID
    @Override
    public boolean remove(int id) {
        String query = "DELETE FROM public.suppliers WHERE id = ?";
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

    public static void main(String[] args) {
        SupplierDAO supplierDAO = new SupplierDAO();

        for (Supplier supplier : supplierDAO.getAll())
            System.out.println(supplier);

//        System.out.println(supplierDAO.findById(1));
        System.out.println(supplierDAO.findByName("Hanoi"));

    }
}
