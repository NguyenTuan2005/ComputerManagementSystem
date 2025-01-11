package controller;

import model.*;
import enums.DisplayProductType;
import enums.LoginStatus;
import enums.UserType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MController {

    boolean login(String email, String pass, LoginStatus loginStatus);


    List<Supplier> getAllSupplier();
    List<Customer> getAllCustomer();
    
    //customer
    Map<Customer, List<Order>> userOrderStatistics();
    void addOrder( Order newOrder);
    void sigUpCustomer(Customer newCustomer);
    int getNextIdOfCustomer();
    List<Customer> findCustomerByName(String name);
    void blockCustomerById(int id);
    void unBlockCustomerById(int id);
    Map<Customer,Long> customerOrderStatistics();
    List<Order> getAllOrderByCustomer(Customer customer);
    List<Order> findOrderByCustomerAndDate(Customer customer, LocalDate orderedAt);
    
    void changePassword(UserType type,  String email, String password);
 


    // manager
    void updateUserInfor(User currentUserV2);
    List<Manager> getAllManager();
    Manager findManagerByEmail(String email);
    int getNextIdOfManage();


    // product
    List<Product> findProductByName(String name);
    Map<Product, Long> productOrderStatistics(); // Monthly Product Sales Statistics
    void removeProduct(Product p);
    void removeProductByIndex(int index);
    void updateProduct(Product product);
    List<Product> filterBy(DisplayProductType type);
    Map<Product,Integer> productSoldStatistic();
    List<Product> getAllProduct();

    //supplier
    List<Supplier> findSuppliersByName(String name);
    Map<String,Long> quantitativeAnalysis();
    Map<String,Long> analyzeQuantityOfImportedGoods();
    void addSupplier(Supplier newSupplier);
    void removeSupplierByIndex(int index);
    void updateSupplier(Supplier supplier);
    Map<Supplier, Long> totalProductStatictics();


    Customer findCustomerByEmail(String email);
}
