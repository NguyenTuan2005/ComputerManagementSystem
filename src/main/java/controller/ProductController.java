package controller;

import Model.Product;
import dao.ImageDAO;
import dao.ProductDAO;
import java.util.ArrayList;
import security.PasswordSecurity;

public class ProductController implements ModelController<Product> {

  private ProductDAO productDAO;

  private PasswordSecurity passwordSecurity;

  private ImageDAO imageDAO;

  public ProductController() {
    this.productDAO = new ProductDAO();
    this.imageDAO = new ImageDAO();
  }

  @Override
  public ArrayList<Product> find(String name) {
    return productDAO.findByName(name);
  }

  @Override
  public Product findById(int id) {
    return productDAO.findById(id);
  }

  @Override
  public ArrayList<Product> getAll() {
    return productDAO.getAll();
  }

  @Override
  public ArrayList<Product> reloadData() {
    return null;
  }

  @Override
  public void setDeleteRow(int id, boolean status) {
    productDAO.setDeleteRow(id, status);
  }

  public boolean setStatus(int id, String status) {
    Product product = findById(id);
    if (status.equals(product.getStatus())) return false;
    product.setStatus(status);
    update(product);
    return true;
  }

  @Override
  public void saves(ArrayList<Product> products) {
    for (Product product : products) {
      productDAO.save(product);
    }
  }

  @Override
  public void save(Product product) {}

  @Override
  public void update(Product product) {
    productDAO.update(product);
  }

  @Override
  public ArrayList<Product> sortByColumn(String column) {
    return productDAO.sortByColumn(column);
  }

  public ArrayList<Product> getEagerProducts() {
    ArrayList<Product> products = productDAO.getEager();

    for (Product product : products) {
      int id = product.getId();
      product.setImages(imageDAO.findByProductId(id));
    }
    return products;
  }
}
