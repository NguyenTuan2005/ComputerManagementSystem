package dao;

import Config.DatabaseConfig;
import Model.Supplier;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;


public class SupplierDAO implements Repository<Supplier> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    @SneakyThrows
    public SupplierDAO() {

        this.databaseConfig = new DatabaseConfig();
        this.connection = databaseConfig.getConnection();

    }

    private Supplier resultSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getInt("id"),
                rs.getString("company_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("address"),
                rs.getDate("contract_date"),
                rs.getInt("delete_row")
        );
    }


    @Override
    @SneakyThrows
    public Supplier save(Supplier supplier) {
        String query = "INSERT INTO public.suppliers (company_name, email, phone_number, address, contract_date) VALUES (?, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, supplier.getCompanyName());
        preparedStatement.setString(2, supplier.getEmail());
        preparedStatement.setString(3, supplier.getPhoneNumber());
        preparedStatement.setString(4, supplier.getAddress());
        preparedStatement.setDate(5, (Date) supplier.getContractDate());

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                supplier.setId(generatedKeys.getInt(1));
            }
            return supplier;
        }

        return null;
    }


    @Override
    @SneakyThrows
    public Supplier findById(int id) {
        Supplier supplier = null;
        String query = "SELECT * FROM public.suppliers WHERE id = ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            supplier = resultSupplier(rs);
        }

        return supplier;
    }


    @Override
    @SneakyThrows
    public ArrayList<Supplier> getAll() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM public.suppliers where delete_row = 1";

        preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Supplier supplier = resultSupplier(rs);
            suppliers.add(supplier);
        }

        return suppliers;
    }


    @Override
    @SneakyThrows
    public ArrayList<Supplier> findByName(String name) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM public.suppliers WHERE LOWER(company_name) LIKE lOWER(?)";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + name.toLowerCase() + "%");
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Supplier supplier = resultSupplier(rs);
            suppliers.add(supplier);
        }

        return suppliers;
    }

    @Override
    @SneakyThrows
    public Supplier findOneByName(String name) {
        Supplier supplier = null;
        String query = "SELECT * FROM public.suppliers WHERE company_name = ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            supplier = resultSupplier(rs);
        }

        return supplier;
    }


    @Override
    @SneakyThrows
    public Supplier update(Supplier supplier) {

        String query = "UPDATE public.suppliers SET company_name = ?, email = ?, phone_number = ?, address = ?, contract_date = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, supplier.getCompanyName());
        preparedStatement.setString(2, supplier.getEmail());
        preparedStatement.setString(3, supplier.getPhoneNumber());
        preparedStatement.setString(4, supplier.getAddress());
        preparedStatement.setDate(5, (Date) supplier.getContractDate());
        preparedStatement.setInt(6, supplier.getId());

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            return supplier;
        }

        return null;
    }


    @SneakyThrows
    public void setDeleteRow(int id, boolean status) {
        String sql = "UPDATE suppliers SET delete_row = ? WHERE id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, status ? 1 : 0);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();

    }


    @Override
    @SneakyThrows
    public boolean remove(int id) {
        String query = "DELETE FROM public.suppliers WHERE id = ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;

    }


    @Override
    @SneakyThrows
    public ArrayList<Supplier> sortByColumn(String column) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM public.suppliers where delete_row = 1";

        switch (column) {
            case "NAME" -> query += " ORDER BY company_name";
            case "EMAIL" -> query += " ORDER BY email";
            case "PHONE NUMBER" -> query += " ORDER BY phone_number";
            case "ADDRESS" -> query += " ORDER BY address";
            case "DATE" -> query += " ORDER BY contract_date";
            default -> {
                return getAll();
            }
        }


        preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Supplier supplier = resultSupplier(rs);
            suppliers.add(supplier);
        }

        return suppliers;
    }


}
