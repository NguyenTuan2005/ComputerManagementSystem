package controller;

import Model.Customer;
import Model.Product;
import Model.Supplier;
import dao.SupplierDAO;

import java.util.ArrayList;

public class SupplierController implements ModelController<Supplier> {
    private SupplierDAO supplierDAO;

    public SupplierController() {
        this.supplierDAO = new SupplierDAO();
    }

    @Override
    public ArrayList<Supplier> find(String name){
        return  supplierDAO.findByName(name);
    }

    @Override
    public Supplier findById(int id) {
        return null;
    }

    @Override
    public ArrayList<Supplier> getAll() {
        return supplierDAO.getAll();
    }

    @Override
    public ArrayList<Supplier> reloadData() {
        return getAll();
    }

    @Override
    public void setDeleteRow(int id, boolean status) {

    }

    @Override
    public void saves(ArrayList<Supplier> products) {

    }

    @Override
    public void save(Supplier supplier) {

    }

    @Override
    public void update(Supplier supplier) {

    }

}
