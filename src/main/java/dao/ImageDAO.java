package dao;

import Config.DatabaseConfig;
import Model.Image;
import java.sql.*;
import java.util.ArrayList;
import lombok.SneakyThrows;

public class ImageDAO implements Repository<Image> {

  private DatabaseConfig databaseConfig;

  private Connection connection;

  private Statement statement;

  private PreparedStatement preparedStatement;

  @SneakyThrows
  public ImageDAO() {

    this.databaseConfig = new DatabaseConfig();
    this.connection = databaseConfig.getConnection();
  }

  private void setImageParameter(PreparedStatement preparedStatement, Image image)
      throws SQLException {
    preparedStatement.setInt(1, image.getProductId());
    preparedStatement.setString(2, image.getAlt());
    preparedStatement.setString(3, image.getUrl());
  }

  private void setImageParameter(ResultSet rs, Image img) throws SQLException {
    img.setId(rs.getInt("id"));
    img.setUrl(rs.getString("url"));
    img.setAlt(rs.getString("alt"));
    img.setProductId(rs.getInt("product_id"));
  }

  @Override
  @SneakyThrows
  public Image save(Image image) {
    String sql = "INSERT INTO image ( product_id, alt, url) VALUES (?,?,?)";

    preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    setImageParameter(preparedStatement, image);
    int affectedRows = preparedStatement.executeUpdate();
    if (affectedRows > 0) {
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        image.setId(generatedKeys.getInt(1));
      }
    }

    return image;
  }

  @Override
  public Image findById(int id) {
    return null;
  }

  @Override
  @SneakyThrows
  public ArrayList<Image> getAll() {
    String sql = " SELECT *FROM image ";
    ArrayList<Image> images = new ArrayList<>();

    statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    while (rs.next()) {
      Image img = new Image();
      setImageParameter(rs, img);
      images.add(img);
    }

    return images;
  }

  @Deprecated
  @Override
  public ArrayList<Image> findByName(String name) {
    return null;
  }

  @Deprecated
  @Override
  public Image findOneByName(String name) {

    return null;
  }

  @Deprecated
  @Override
  public Image update(Image image) {
    return null;
  }

  @SneakyThrows
  public ArrayList<Image> findByProductId(int productId) {

    String sql = " SELECT *FROM image where product_id =?";
    ArrayList<Image> images = new ArrayList<>();

    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, productId);
    ResultSet rs = preparedStatement.executeQuery();
    while (rs.next()) {
      Image img = new Image();
      setImageParameter(rs, img);
      images.add(img);
    }

    return images;
  }

  @Deprecated
  @Override
  public boolean remove(int id) {
    return false;
  }

  @Override
  public ArrayList<Image> sortByColumn(String column) {
    return null;
  }
}
