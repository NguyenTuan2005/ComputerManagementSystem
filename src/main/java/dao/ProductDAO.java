package dao;

import Config.DatabaseConfig;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO implements Repository<Product> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public ProductDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product save(Product product) {
        try {
            String sql = "INSERT INTO product (suppliers_id, name, quality, price, genre, brand, operating_system, cpu, memory, ram, made_in, status, delete_row) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setProductParameters(preparedStatement, product);
            preparedStatement.setInt(13, 1);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }


    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = ? AND delete_row = 1";
        Product product = new Product();
        ArrayList<Product> products = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                product = mapResultSetToProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE delete_row = 1";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Product product = mapResultSetToProduct(rs);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public ArrayList<Product> findByName(String name) {
//        LOWER(product_name) LIKE LOWER('%từ_khóa_tìm_kiếm%')
        String sql = "SELECT * FROM  product WHERE LOWER(name) LIKE LOWER(?) AND delete_row = 1";
        ArrayList<Product> products = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = mapResultSetToProduct(rs);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findOneByName(String name) {
        try {
            String sql = "SELECT * FROM product WHERE LOWER(name) LIKE LOWER(?) AND delete_row = 1";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  note Id
     * @param product
     * @return product
     */
    @Override
    public Product update(Product product) {
        try {
            String sql = "UPDATE product SET suppliers_id = ?, name = ?, quality = ?, price = ?, genre = ?, brand = ?, operating_system = ?, cpu = ?, memory = ?, ram = ?, made_in = ?, status = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            setProductParameters(preparedStatement, product);
            preparedStatement.setInt(13,product.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public void setDeleteRow(int id , boolean status){
        //UPDATE product SET delete_row = 0 WHERE id = 3
        String sql = "UPDATE product SET delete_row = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status?1:0);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Deprecated
    @Override
    public boolean remove(int id) {
        return false;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setSuppliersId(rs.getInt("suppliers_id"));
        product.setName(rs.getString("name"));
        product.setQuality(Integer.parseInt(rs.getString("quality")));
        product.setPrice(rs.getInt("price"));
        product.setGenre(rs.getString("genre"));
        product.setBrand(rs.getString("brand"));
        product.setOperatingSystem(rs.getString("operating_system"));
        product.setCpu(rs.getString("cpu"));
        product.setMemory(rs.getString("memory"));
        product.setRam(rs.getString("ram"));
        product.setMadeIn(rs.getString("made_in"));
        product.setStatus(rs.getString("status"));
        product.setDeleteRow(rs.getInt("delete_row"));
        return product;
    }

    private void setProductParameters(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setInt(1, product.getSuppliersId());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setInt(3, product.getQuality());
        preparedStatement.setDouble(4, product.getPrice());
        preparedStatement.setString(5, product.getGenre());
        preparedStatement.setString(6, product.getBrand());
        preparedStatement.setString(7, product.getOperatingSystem());
        preparedStatement.setString(8, product.getCpu());
        preparedStatement.setString(9, product.getMemory());
        preparedStatement.setString(10, product.getRam());
        preparedStatement.setString(11, product.getMadeIn());
        preparedStatement.setString(12, product.getStatus());
    }

    public static void main(String[] args) {
        ProductDAO p = new ProductDAO();
        p.setDeleteRow(3, false);
    }

}
