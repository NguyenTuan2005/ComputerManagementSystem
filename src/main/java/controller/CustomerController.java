package controller;

import Config.CurrentUser;
import Model.Customer;
import dao.CustomerDAO;
import dto.CustomerOrderDTO;
import security.PasswordSecurity;
import Enum.*;

import java.util.ArrayList;

public class CustomerController implements ModelController<Customer> {
    private CustomerDAO customerDAO;

    public static LoginStatus loginStatus = LoginStatus.NOT_FOUND;

    private PasswordSecurity passwordSecurity;

    public CustomerController(){
        this.customerDAO = new CustomerDAO();
        passwordSecurity = new PasswordSecurity();
    }

    public boolean isValidAccount(String email , String password ){
        Customer customer = customerDAO.findByEmail(email);
        System.out.println(customer);
        if ( customer == null )
            return false;
        if(customer.isBlock()){
            loginStatus = LoginStatus.BLOCKED;
            return false;
        }
        if (customer.sameEmail(email)){
            passwordSecurity = new PasswordSecurity();
            passwordSecurity.setPlainPassword(password);
            if (passwordSecurity.isVariablePassword(customer.getPassword())){
                CurrentUser.CURRENT_CUSTOMER = customer;
                return true;
            }else {
                loginStatus =LoginStatus.WORNG_PASSWORD;
                return false;
            }
        }else{
            loginStatus =LoginStatus.WRONG_EMAIL;
            return false;
        }

    }

    public Customer create(Customer customer){
        passwordSecurity = new PasswordSecurity();
        passwordSecurity.setPlainPassword(customer.getPassword());
        customer.setPassword(passwordSecurity.generatePassword());
        return customerDAO.save(customer);
    }

    @Override
    public ArrayList<Customer> find(String name) {
        return customerDAO.findByName(name);
    }

    @Override
    public Customer findById(int id) {
        return customerDAO.findById(id);
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
    public void saves(ArrayList<Customer> customers) {
        for (Customer customer :customers)
            customerDAO.save(customer);
    }

    @Override
    public void save(Customer customer) {
        passwordSecurity.setPlainPassword(customer.getPassword());
        customer.setPassword(passwordSecurity.generatePassword());
        System.out.println( customerDAO.save(customer));
    }

    @Override
    public void update(Customer customer) {
        // Customer has to have Id!
        customerDAO.update(customer);
    }

    @Override
    public ArrayList<Customer> sortByColumn(String column) {
        return null;
    }

    public ArrayList<CustomerOrderDTO> findCustomerOrderById(int id){
        return customerDAO.getDataCustomerOrderById(id);
    }

    public void block(boolean isBlock, int id){
        customerDAO.updateBlock(isBlock, id);
    }

    public Customer findByEmail(String s) {
        return customerDAO.findByEmail(s);
    }

    public void updatePassword(String newPassword, int id) {
        passwordSecurity = new PasswordSecurity(newPassword);
        customerDAO.updatePassword(passwordSecurity.generatePassword(),id);
    }
}
