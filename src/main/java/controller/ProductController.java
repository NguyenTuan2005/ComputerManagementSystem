package controller;

import Model.Customer;
import Model.Product;
import dao.ProductDAO;
import security.PasswordSecurity;

import java.util.ArrayList;

public class ProductController implements ModelController<Product> {

    private ProductDAO productDAO ;

    private PasswordSecurity passwordSecurity;


    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    @Override
    public ArrayList<Product> find(String name){
        return  productDAO.findByName(name);
    }

    @Override
    public Product findById(int id) {
        return null;
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
    public void setDeleteRow(int id , boolean status){
        productDAO.setDeleteRow(id,status);
    }

    @Override
    public void saves(ArrayList<Product> products) {
        for(Product product: products) {
            productDAO.save(product);
        }
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public ArrayList<Product> getByColumn(String column) {
        return null;
    }

    public static void main(String[] args) {
        ProductController p = new ProductController();
        System.out.println(p.getAll());
    }
}
