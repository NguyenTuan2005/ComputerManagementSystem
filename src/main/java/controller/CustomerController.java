package controller;

import Model.Account;
import Model.Customer;
import dao.CustomerDAO;

public class CustomerController {
    private CustomerDAO customerDAO;

    public CustomerController(){
        this.customerDAO = new CustomerDAO();
    }
    public boolean isValidAccount(String email , String password ){
        Customer customer = customerDAO.findByEmail(email);

        if ( customer == null )
            return false;
        System.out.println(customer);
        return customer.samePasswordAndEmail(email,password);
    }




}
