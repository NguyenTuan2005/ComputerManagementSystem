package dao;

import Config.DatabaseConfig;
import Model.Manager;
import dto.ManagerInforDTO;

import java.sql.*;
import java.util.ArrayList;

public class ManagerDAO implements Repository<Manager> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public ManagerDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Manager save(Manager manager) {
        String sql = "INSERT INTO manager (fullname, address, birthday, phone_number) VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, manager.getFullName());
            preparedStatement.setString(2, manager.getAddress());
            preparedStatement.setDate(3, (Date) manager.getBirthDay());
            preparedStatement.setString(4, manager.getPhoneNumber());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manager.setId((int) generatedKeys.getLong(1));
            }
            return manager;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Manager findById(int id) {
        String sql = "SELECT * FROM manager WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToManager(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public ArrayList<Manager> getAll() {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM Manager";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                managers.add(mapResultSetToManager(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return managers;
    }


    @Override
    public ArrayList<Manager> findByName(String name) {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM manager WHERE fullname LIKE ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                managers.add(mapResultSetToManager(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }


    @Override
    public Manager findOneByName(String name) {
        String sql = "SELECT * FROM manager WHERE fullname LIKE ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToManager(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Manager update(Manager manager) {
        String sql = "UPDATE Manager SET fullname = ?, address = ?, birthday = ?, phone_number = ? WHERE id = ?";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    @Override
    public boolean remove(int id) {
        String sql = "DELETE FROM Manager WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Manager> getByColumn(String column) {
        return null;
    }

    private ManagerInforDTO mapResultSetToManagerInfor(ResultSet resultSet) {
        ManagerInforDTO managerInfor = new ManagerInforDTO();

        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerInfor;
    }

    public ManagerInforDTO getManagerWithAccountById(String nameOfAccount) {
        String sql = "SELECT m.id AS managerId, m.fullname, m.address, m.birthday, m.phone_number, " +
                "a.id AS accountId, a.username, a.password, a.email, a.create_date , a.avata_img " +
                "FROM Manager AS m " +
                "INNER JOIN Account AS a ON a.manage_id = m.id " +
                "WHERE a.username LIKE ?";
        ManagerInforDTO managerInfor = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameOfAccount);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                managerInfor = mapResultSetToManagerInfor(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerInfor;
    }

    public static void main(String[] args) {
        ManagerDAO managerDAO = new ManagerDAO();
        System.out.println(managerDAO.getManagerInforDTO());
    }


    public ArrayList<ManagerInforDTO> getManagerInforDTO() {

        String sql = "SELECT m.id AS managerId, m.fullname, m.address, m.birthday, m.phone_number, " +
                    "a.id AS accountId, a.username, a.password, a.email, a.create_date , a.avata_img " +
                    "FROM Manager AS m " +
                    "INNER JOIN Account AS a ON a.manage_id = m.id " ;
        ArrayList< ManagerInforDTO> managerInforDTOS = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ManagerInforDTO managerInfor = mapResultSetToManagerInfor(resultSet);
                managerInforDTOS.add(managerInfor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerInforDTOS;
    }


}
