package dao;

import Config.DatabaseConfig;
import Model.Account;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;

public class AccountDAO implements Repository<Account> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    @SneakyThrows
    public AccountDAO() {

        this.databaseConfig = new DatabaseConfig();
        this.connection = databaseConfig.getConnection();

    }

    private static void setAccountParameter(PreparedStatement preparedStatement, Account account) throws SQLException {
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setString(3, account.getEmail());
        preparedStatement.setDate(4, new java.sql.Date(account.getCreateDate().getTime()));
        preparedStatement.setInt(5, account.getManagerId());
        preparedStatement.setString(6, account.getAvataImg());
    }

    @Override
    public Account save(Account account) {
        if (account.getId() == 0) {
            // Insert new account
            String sql = "INSERT INTO account (username, password, email, create_date, manage_id , avata_img) VALUES (?, ?, ?, ?, ?,?)";
            try {
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                setAccountParameter(preparedStatement, account);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        account.setId(generatedKeys.getInt(1));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return account;
    }

    @Override
    @SneakyThrows
    public Account findById(int id) {
        String sql = " SELECT * FROM account WHERE id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Account account = new Account();
            setAccountParameter(rs, account);
            return account;
        }

        return null;
    }

    @Override
    @SneakyThrows
    public ArrayList<Account> getAll() {
        String sql = " SELECT *FROM account ";
        ArrayList<Account> accounts = new ArrayList<>();

        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Account account = new Account();
            setAccountParameter(rs, account);
            accounts.add(account);
        }

        return accounts;

    }

    @Override
    @SneakyThrows
    public ArrayList<Account> findByName(String name) {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = " SELECT * FROM account WHERE username LIKE ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Account account = new Account();
            setAccountParameter(rs, account);
            System.out.println("run here");
            accounts.add(account);
        }

        return accounts;
    }

    @Override
    @SneakyThrows
    public Account findOneByName(String name) {

        String sql = " SELECT * FROM account WHERE username LIKE ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Account account = new Account();
            setAccountParameter(rs, account);
            System.out.println("run here");
            return account;
        }

        return null;
    }



    @Override
    @SneakyThrows
    public Account update(Account account) {

        String sql = "UPDATE account SET username = ?, password = ?  WHERE  email = ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setString(3, account.getEmail());
        System.out.println(preparedStatement.executeUpdate());

        return account;
    }


    @SneakyThrows
    public Account updateById(Account account) {
        String sql = "UPDATE account SET username = ? , email =? ,create_date =?,avata_img =? WHERE  id = ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getEmail());
        preparedStatement.setDate(3, account.getCreateDate());
        preparedStatement.setString(4, account.getAvataImg());
        preparedStatement.setInt(5, account.getId());
        System.out.println(preparedStatement.executeUpdate());

        return account;
    }

    @Override
    public boolean remove(int id) {
        String sql = " DELETE FROM account WHERE id =  ?";
        return false;
    }


    @Override
    @SneakyThrows
    public ArrayList<Account> sortByColumn(String column) {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM public.account";

        switch (column) {
            case "NAME" -> query += " ORDER BY username";
            case "PASSWORD" -> query += " ORDER BY password";
            case "EMAIL" -> query += " ORDER BY email";
            case "DATE" -> query += " ORDER BY create_date";
            default -> {
                return getAll();
            }
        }

        preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Account account = new Account();
            setAccountParameter(rs, account);
            accounts.add(account);
        }

        return accounts;
    }

    private static void setAccountParameter(ResultSet rs, Account account) throws SQLException {
        account.setId(rs.getInt("id"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setEmail(rs.getString("email"));
        account.setCreateDate(rs.getDate("create_date"));
        account.setManagerId(rs.getInt("manage_id"));
    }

    @SneakyThrows
    public void updateBlock(boolean isBlock, int id) {

        String sql = "UPDATE account SET block = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, isBlock ? 1 : 0);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();

    }

    @SneakyThrows
    public void updatePassword(String pw, int id) {

        String sql = "UPDATE account SET password = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, pw);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();

    }
}
