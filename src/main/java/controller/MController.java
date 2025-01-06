package controller;

import entity.*;
import enums.LoginStatus;

import java.util.List;
import java.util.Map;

public interface MController {

    boolean login(String email, String pass, LoginStatus loginStatus);

    List<Product> getAllProduct();
    List<Supplier> getAllSupplier();
    List<Customer> getAllCustomer();




    Map<Product,Integer> productSoldStatistic();


    Map<Customer, List<Order>> userOrderStatistics();
    void addOrder(Customer customer , Order newOrder);

    Map<Manager , List<Order>>  managerOrderStatistics();
    Manager findManagerByEmail(String email);


    List<Product> findProductByName(String name);



    List<Supplier> findSuppliersByName(String name);


    void updateUserInfor(User currentUserV2);

    void updateSupplier(Supplier supplier);
}
