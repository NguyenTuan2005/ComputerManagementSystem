package dao;

import Config.DatabaseConfig;
import Model.Manager;
import dto.ManagerInforDTO;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;

public class ManagerDAO implements Repository<Manager> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    @SneakyThrows
    public ManagerDAO() {

        this.databaseConfig = new DatabaseConfig();
        this.connection = databaseConfig.getConnection();

    }

    @Override
    @SneakyThrows
    public Manager save(Manager manager) {
        String sql = "INSERT INTO manager (fullname, address, birthday, phone_number) VALUES (?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, manager.getFullName());
        preparedStatement.setString(2, manager.getAddress());
        preparedStatement.setDate(3, manager.getBirthDay());
        preparedStatement.setString(4, manager.getPhoneNumber());
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            manager.setId((int) generatedKeys.getLong(1));
        }
        return manager;


    }

    @Override
    @SneakyThrows
    public Manager findById(int id) {
        String sql = "SELECT * FROM manager WHERE id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return mapResultSetToManager(resultSet);
        }

        return null;
    }

    private Manager mapResultSetToManager(ResultSet resultSet) throws SQLException {
        Manager manager = new Manager();
        manager.setId(resultSet.getInt("id"));
        manager.setFullName(resultSet.getString("fullname"));
        manager.setAddress(resultSet.getString("address"));
        manager.setBirthDay(resultSet.getDate("birthday"));
        manager.setPhoneNumber(resultSet.getString("phone_number"));
        return manager;
    }

    @Override
    @SneakyThrows
    public ArrayList<Manager> getAll() {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM Manager";

        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            managers.add(mapResultSetToManager(resultSet));
        }

        return managers;
    }


    @Override
    @SneakyThrows
    public ArrayList<Manager> findByName(String name) {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM manager WHERE fullname LIKE ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            managers.add(mapResultSetToManager(resultSet));
        }

        return managers;
    }


    @Override
    @SneakyThrows
    public Manager findOneByName(String name) {
        String sql = "SELECT * FROM manager WHERE fullname LIKE ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToManager(resultSet);
        }

        return null;
    }

    @Deprecated
    @Override
    @SneakyThrows
    public Manager update(Manager manager) {
        String sql = "UPDATE Manager SET fullname = ?, address = ?, birthday = ?, phone_number = ? WHERE id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, manager.getFullName());
        preparedStatement.setString(2, manager.getAddress());
        preparedStatement.setDate(3, (Date) manager.getBirthDay());
        preparedStatement.setString(4, manager.getPhoneNumber());
        preparedStatement.setLong(5, manager.getId());
        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows > 0) {
            return manager;
        }

        return null;
    }

    @Deprecated
    @Override
    @SneakyThrows
    public boolean remove(int id) {
        String sql = "DELETE FROM Manager WHERE id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows > 0;

    }


    @Override
    public ArrayList<Manager> sortByColumn(String column) {
        return null;
    }

    @
            SneakyThrows
    private ManagerInforDTO mapResultSetToManagerInfor(ResultSet resultSet) {
        ManagerInforDTO managerInfor = new ManagerInforDTO();

        managerInfor.setManagerId(resultSet.getInt("managerId"));
        managerInfor.setFullName(resultSet.getString("fullname"));
        managerInfor.setAddress(resultSet.getString("address"));
        managerInfor.setBirthDay(resultSet.getDate("birthday"));
        managerInfor.setPhoneNumber(resultSet.getString("phone_number"));
        managerInfor.setAccountId(resultSet.getInt("accountId"));
        managerInfor.setUsername(resultSet.getString("username"));
        managerInfor.setPassword(resultSet.getString("password"));
        managerInfor.setEmail(resultSet.getString("email"));
        managerInfor.setCreateDate(resultSet.getDate("create_date"));
        managerInfor.setAvataImg(resultSet.getString("avata_img"));
        managerInfor.setBlock(resultSet.getInt("block"));

        return managerInfor;
    }

    @SneakyThrows
    public ManagerInforDTO getManagerWithAccountByName(String nameOfAccount) {
        String sql = "SELECT m.id AS managerId, m.fullname, m.address, m.birthday, m.phone_number, " +
                "a.id AS accountId, a.username, a.password, a.email, a.create_date , a.avata_img, a.block " +
                "FROM Manager AS m " +
                "INNER JOIN Account AS a ON a.manage_id = m.id " +
                "WHERE a.username LIKE ?";
        ManagerInforDTO managerInfor = null;
        ResultSet resultSet = null;

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + nameOfAccount + "%");
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            managerInfor = mapResultSetToManagerInfor(resultSet);
        }

        return managerInfor;
    }


    @SneakyThrows
    public ArrayList<ManagerInforDTO> getManagerInforDTO() {
        String sql = "SELECT m.id AS managerId, m.fullname, m.address, m.birthday, m.phone_number, " +
                "a.id AS accountId, a.username, a.password, a.email, a.create_date , a.avata_img  , a.block " +
                "FROM Manager AS m " +
                "INNER JOIN Account AS a ON a.manage_id = m.id ";

        ArrayList<ManagerInforDTO> managerInforDTOS = new ArrayList<>();
        ResultSet resultSet = null;

        preparedStatement = connection.prepareStatement(sql);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            var n = mapResultSetToManagerInfor(resultSet);
            managerInforDTOS.add(n);
        }

        return managerInforDTOS;
    }
}
