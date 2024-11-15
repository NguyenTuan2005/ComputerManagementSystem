package controller;

import Model.Product;
import dao.ProductDAO;

import java.util.ArrayList;

public class ProductController implements ModelController<Product> {

    private ProductDAO productDAO ;


    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    @Override
    public ArrayList<Product> find(String name){
        return  productDAO.findByName(name);
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

    @Deprecated
    @Override
    public void save(Product product) {

    }

    @Override
    public ArrayList<Product> sortByColumn(String column) {
        return productDAO.sortByColumn(column);
    }

    public static void main(String[] args) {
        ProductController p = new ProductController();
        System.out.println(p.getAll());
    }
}
