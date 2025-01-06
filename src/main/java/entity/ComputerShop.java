package entity;

import config.CurrentUser;
import controller.MController;
import data.ManagerData;
import enums.LoginStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerShop implements MController {
    List<Product> products;
    List<Supplier> suppliers;
    List<Manager> managers;
    List<Customer> customers;
    

    public static LoginStatus loginStatusManager = LoginStatus.NOT_FOUND;

    public ComputerShop() {
        ManagerData managerData = new ManagerData();
        this.products = managerData.getProducts();
        this.suppliers = managerData.getSuppliers();
        this.managers = managerData.getManagers();
        this.customers = managerData.getCustomers();

    }

    public static void main(String[] args) {
        ComputerShop c = new ComputerShop();
//        c.findProductByName("dell");
        System.out.println(c.managers.get(0).getOrders());
    }

    @Override
    public  boolean login(String email, String pass, LoginStatus loginStatus) {
        loginStatusManager = LoginStatus.NOT_FOUND;
        switch (loginStatus.getMessager()) {
            case ("Manager"): {
                Manager manager = this.managers.stream().filter(m -> m.sameEmail(email)).findFirst().orElse(null);
                boolean isNotFound =( manager == null);
                if(isNotFound) return false;
                if(manager.isValidPassword(pass)){
                    CurrentUser.CURRENT_USER_V2 = manager;
                    CurrentUser.URL = manager.getAvatarImg();

                    return true;
                }else
                 loginStatusManager = LoginStatus.WRONG_EMAIL;
                break;
            }
            case ("Customer"): {
                Customer customer = this.customers.stream().filter(c -> c.sameEmail(email)).findFirst().orElse(null);
                boolean isNotFound =( customer== null);
                if(isNotFound) return false;
                if(customer.isValidPassword(pass)){
                    System.out.println(customer);
                    CurrentUser.CURRENT_USER_V2 = customer;
                    CurrentUser.URL = customer.getAvatarImg();
                    return true;
                }else
                    loginStatusManager = LoginStatus.WRONG_EMAIL;

                break;
            }
        }
        return false;
    }

    @Override
    public List<Product> getAllProduct() {
        return this.products;
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return this.suppliers;
    }


    @Override
    public List<Customer> getAllCustomer() {
        return this.customers;
    }

    @Override
    public Map<Product, Integer> productSoldStatistic() {
//        return Map.of();
        return this.managers.get(0).productSoldStatistic();
    }

    @Override
    public Map<Customer, List<Order>> userOrderStatistics() {
        return Map.of();
    }

    @Override
    public void addOrder(Customer customer, Order newOrder) {

    }

    @Override
    public Map<Manager, List<Order>> managerOrderStatistics() {
        return this.managers.stream().collect(Collectors.toMap(
                manager -> manager,
                Manager::getOrders,
                (exit, replace) -> exit,
                TreeMap::new
        ));
    }


    @Override
    public Manager findManagerByEmail(String email) {
        return this.managers.stream().filter(m ->m.sameEmail(email)).findAny().orElse(null);
    }

    @Override
    public List<Product> findProductByName(String name) {
        products = this.products.stream()
                .filter(p -> p.like(name))
                .collect(Collectors.toList());
        System.out.println(this.products);
        return this.products;
//        return this.products.stream()
//                .filter(p -> p.like(name))
//                .collect(Collectors.toList());
    }

    @Override
    public List<Supplier> findSuppliersByName(String name) {
        return this.suppliers.stream().filter(s->s.sameCompanyName(name))
                .collect(Collectors.toList());
    }

    public Supplier findSupplier(String name){
        return this.suppliers.stream()
                .filter(s->s.exactlySameCompanyName(name))
                .findFirst().orElse(null);
    }

    public void sortSupplierByColumn(String column, List<Supplier> suppliers) {
        switch (column) {
            case "NAME" -> suppliers.sort(Supplier::compareName);
            case "EMAIL" -> suppliers.sort(Supplier::compareEmail);
            case "PHONE NUMBER" -> suppliers.sort(Supplier::comparePhone);
            case "ADDRESS" -> suppliers.sort(Supplier::compareAddress);
            case "DATE" -> suppliers.sort(Supplier::compareDate);
        };
    }

    public void removeProduct(Product p){
        this.products.remove(p);
    }
    public void addProduct(Product p){
        this.products.add(p);
    }

    @Override
    public void updateUserInfor(User user) {
        if(user.isManager()){
            ListIterator<Manager> iterator = this.managers.listIterator();
            while(iterator.hasNext()) {
                var m = iterator.next();
                if(m.sameEmail(user.getEmail())){
                    iterator.set((Manager) user);
                    break;
                }
            }
            this.managers.forEach(System.out::println);
        } else {
            ListIterator<Customer> iterator = this.customers.listIterator();
            while(iterator.hasNext()) {
                var c = iterator.next();
                if(c.sameEmail(user.getEmail())){
                    iterator.set((Customer) user);
                    break;
                }
            }
            this.customers.forEach(System.out::println);
        }

    }

    @Override
    public void updateSupplier(Supplier supplier) {
        for(var sup : this.suppliers){
            if(sup.sameCompanyName(supplier)){
                sup = supplier;
                return;
            }
        }
        this.suppliers.add(supplier);
    }

    //Customer
    public void removeCustomer(Customer c){
        this.customers.remove(c);
    }
    public void addCustomer(Customer c){
        this.customers.add(c);
    }

    public int getTotalProduct(){
        return this.products.size();
    }

    public void blockManager(Manager m,boolean block){
        for(Manager man : this.managers){
            if (man.equals(m)) {
                m.changeActive(block);
                break;
            }
        }
    }

    public void blockCustomer(Customer c,boolean block){
        for(Customer cus : this.customers){
            if (cus.equals(c)) {
                c.changeActive(block);
                break;
            }
        }
    }

    public List<Customer> findCustomerByName(String customerName){
        return this.customers.stream().filter(customer -> customer.getFullName().toLowerCase().contains(customerName.toLowerCase()))
                .collect(Collectors.toList());
    }


    public List<Supplier> findSupplierByName(String suppilerName){
        return this.suppliers.stream().filter(supp -> supp.getCompanyName().toLowerCase().contains(suppilerName.toLowerCase()))
                .collect(Collectors.toList());
    }


    public List<Order> getAllOrders() {
        return this.managers.get(0).getOrders();
    }

//    public void setAllOrders(List<Order> allOrders) {
//        this.managers. = allOrders;
//    }
}
