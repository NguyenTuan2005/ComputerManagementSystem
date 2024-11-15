package controller;

import Model.Account;
import Model.Customer;
import Model.Product;
import dao.CustomerDAO;
import security.PasswordSecurity;

import java.util.ArrayList;

public class CustomerController implements ModelController<Customer> {
    private CustomerDAO customerDAO;

    private PasswordSecurity passwordSecurity;

    public CustomerController(){
        this.customerDAO = new CustomerDAO();
    }
    public boolean isValidAccount(String email , String password ){
        Customer customer = customerDAO.findByEmail(email);
        if ( customer == null )
            return false;
        passwordSecurity = new PasswordSecurity();
        passwordSecurity.setPlainPassword(password);
        System.out.println(customer);
        return passwordSecurity.isVariablePassword(customer.getPassword());
    }
    public Customer create(Customer customer){
        passwordSecurity = new PasswordSecurity();
        passwordSecurity.setPlainPassword(customer.getPassword());
        customer.setPassword(passwordSecurity.generatePassword());
        return customerDAO.save(customer);
    }

    @Override
    public ArrayList<Customer> find(String name) {
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() {
        return  customerDAO.getAll();
    }

    @Override
    public ArrayList<Customer> reloadData() {
        return customerDAO.getAll();
    }

    @Override
    public void setDeleteRow(int id, boolean status) {

    }

    @Override
    public void saves(ArrayList<Customer> products) {

    }

    @Override
    public void save(Product product) {

    }

    @Override
    public ArrayList<Customer> sortByColumn(String column) {
        return null;
    }

    public static void main(String[] args) {
        CustomerController customerController = new CustomerController();
        System.out.println(customerController.getAll());
    }
}
