package dao;

import Config.DatabaseConfig;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;

public class AccountDAO implements Repository<Account> {

    private DatabaseConfig databaseConfig;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    public AccountDAO() {
        try {
            this.databaseConfig = new DatabaseConfig();
            this.connection = databaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setAccountParameter(PreparedStatement preparedStatement, Account account) throws SQLException {
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setString(3, account.getEmail());
        preparedStatement.setDate(4, new java.sql.Date(account.getCreateDate().getTime()));
        preparedStatement.setInt(5, account.getManagerId());
    }

    @Override
    public Account save(Account account) {
        if (account.getId() == 0) {
            // Insert new account
            String sql = "INSERT INTO account (username, password, email, create_date, manage_id) VALUES (?, ?, ?, ?, ?)";
            try {
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                setAccountParameter(preparedStatement,account);
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
    public Account findById(int id) {
        String sql = " SELECT * FROM account WHERE id = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Account account = new Account();
                setAccountParameter(rs,account);
                return account;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Account> getAll() {
        String sql = " SELECT *FROM account ";
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Account account = new Account();
                setAccountParameter(rs, account);
                accounts.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;

    }

    @Override
    public ArrayList<Account> findByName(String name) {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = " SELECT * FROM account WHERE username LIKE ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+name+"%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Account account = new Account();
                setAccountParameter(rs,account);
                System.out.println("run here");
                accounts.add(account);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  accounts;
    }

    @Override
    public Account findOneByName(String name) {

        String sql = " SELECT * FROM account WHERE username LIKE ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+name+"%");
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Account account = new Account();
                setAccountParameter(rs,account);
                System.out.println("run here");
             return  account;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.update(new Account("123","root","duynguyenavg@gmail.com"));
    }
    @Override
    public Account update(Account account) {

            String sql = "UPDATE account SET username = ?, password = ? WHERE  email = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
                preparedStatement.setString(3, account.getEmail());
                System.out.println( preparedStatement.executeUpdate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        return account;
    }

    @Override
    public boolean remove(int id) {
        String sql = " DELETE FROM account WHERE id =  ?";
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức để lấy tất cả Accounts theo cột
    @Override
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

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                setAccountParameter(rs, account);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private static void setAccountParameter(ResultSet rs ,Account account ) throws SQLException {
        account.setId(rs.getInt("id"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setEmail(rs.getString("email"));
        account.setCreateDate(rs.getDate("create_date"));
        account.setManagerId(rs.getInt("manage_id"));
    }
}
