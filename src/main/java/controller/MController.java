package controller;

import entity.*;
import enums.DisplayProductType;
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

    List<Manager> getAllManager();
    Manager findManagerByEmail(String email);


    List<Product> findProductByName(String name);
    Map<Product, Long> productOrderStatistics();
    void removeProduct(Product p);
    void removeProductByIndex(int index);
    void updateProduct(Product product);
    List<Product> filterBy(DisplayProductType type);


    List<Supplier> findSuppliersByName(String name);
    Map<String,Long> quantitativeAnalysis();
    Map<String,Long> analyzeQuantityOfImportedGoods();


    void updateUserInfor(User currentUserV2);

    void updateSupplier(Supplier supplier);

    void addSupplier(Supplier newSupplier);

    void removeSupplierByIndex(int index);

}
