package controller;

import Model.Account;
import Model.Customer;
import dao.CustomerDAO;
import security.PasswordSecurity;

public class CustomerController {
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

}
