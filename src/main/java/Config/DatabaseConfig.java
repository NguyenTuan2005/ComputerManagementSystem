package Config;

import Model.Product;

import java.sql.*;

/**
 * spring.datasource.url=jdbc:postgresql://localhost:5432/db_test
 * spring.datasource.username=postgres
 * spring.datasource.password=1111
 */

public class DatabaseConfig {
    private final  String serverName = "localhost";
    private final String portNumber = "5432";
    private final String dbName = "db_computer_manager";
    private final String userName = "postgres";
    private final String password = "1111";

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://" + serverName + ":" + portNumber + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url,userName, password);
    }
    public void closeConnection(Connection con) throws SQLException {
        if( con != null )
            con.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        System.out.println(databaseConfig.getConnection());
        Connection con = databaseConfig.getConnection();
        String sql = "select * from product";
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery(sql);

        while (resultSet.next()) {
            Product p = new Product();
            System.out.println(
                    resultSet.getString("id")+"    "+
                            resultSet.getString("name")+"    "
//                    resultSet.getString("id")+"    "+
//                    resultSet.getString("id")+"    "+
//                    resultSet.getString("id")+"    "+
            );
        }
        databaseConfig.closeConnection(con);


    }


}
