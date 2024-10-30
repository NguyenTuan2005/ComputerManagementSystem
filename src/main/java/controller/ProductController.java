package controller;

import Model.Product;
import dao.ProductDAO;

import java.util.ArrayList;

public class ProductController {
    private ProductDAO productDAO ;

    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    public ArrayList<Product> find(String name){
        return  productDAO.findByName(name);
    }

    public ArrayList<Product> getAll() {
        return productDAO.getAll();
    }

    public static void main(String[] args) {
        ProductController p = new ProductController();
        System.out.println(p.getAll());
    }
}
