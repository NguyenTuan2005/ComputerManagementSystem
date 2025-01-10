package entity;

import config.CurrentUser;
import controller.MController;
import data.ManagerData;
import enums.LoginStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.*;
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
        Supplier phongVuComputer = Supplier.builder().id(1).companyName("Phong ").email("duynguyen@gamil.com").phoneNumber("18006865").address("Tầng 2, số 2A Trần Đại Nghĩa, Hai Bà Trưng, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

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
    public List<Manager> getAllManager() {
        return this.managers;
    }

    @Override
    public Manager findManagerByEmail(String email) {
        return this.managers.stream().filter(m ->m.sameEmail(email)).findAny().orElse(null);
    }

    public List<Manager> findManagerByName(String name){
        return this.managers.stream().filter(m ->m.findName(name)).collect(Collectors.toList());
    }

    @Override
    public List<Product> findProductByName(String name) {
        return this.products.stream()
                .filter(p -> p.like(name))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Product,Long> productOrderStatistics() {
        Map<Product, Long> quantityOrder = new HashMap<>();


        for ( var  p : this.products){
            long sumQuantity =0;
            for(var manager : this.managers){
                sumQuantity +=  manager.getQuantityOfProduct(p);
            }
            quantityOrder.put(p,sumQuantity);

        }

        return quantityOrder;

    }

    @Override
    public List<Supplier> findSuppliersByName(String name) {
        return this.suppliers.stream().filter(s->s.sameCompanyName(name))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> quantitativeAnalysis() {
        Map<String, Long> map = new HashMap<>();
        for ( var  s : this.suppliers){
            long sumQuantity =0;
            for(var m : this.managers)
                sumQuantity= m.getQuantityInOrders(s);

            map.put(s.getCompanyName(),sumQuantity);
        }
        return  map;
    }

    @Override
    public Map<String, Long> analyzeQuantityOfImportedGoods() {
        Map<String, Long> map = new HashMap<>();
        for ( var  s : this.suppliers){
            long sumQuantity =0;
            for(var p : this.products)
                if(p.sameSupplier(s))
                    sumQuantity++;

            map.put(s.getCompanyName(),sumQuantity);
        }
        return  map;
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

    @Override
    public void removeProductByIndex(int index) {
        this.products.remove(index);
    }

    @Override
    public void updateProduct(Product product) {
        ListIterator<Product> iterator = this.products.listIterator();
        while (iterator.hasNext()){
           var p = iterator.next();
           if (p.sameId(product)){
               iterator.set(product);
               return;
           }
        }
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
        ListIterator<Supplier> iterator = this.suppliers.listIterator();
        while (iterator.hasNext()){
            var sup = iterator.next();
            if( sup.sameEmail(supplier)){
                iterator.set(supplier);
            }
        }
        ListIterator<Manager> managerListIterator = this.managers.listIterator();
        while( managerListIterator.hasNext()){
             managerListIterator.next().updateSupplier(supplier);
        }
    }

    @Override
    public void addSupplier(Supplier newSupplier) {
        this.suppliers.add(newSupplier);
    }

    @Override
    public void removeSupplierByIndex(int index) {
        this.suppliers.remove(index);
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
    //Manager
    public void removeManager(Manager m){
        this.managers.remove(m);
    }
    public void addManager(Manager m){
        this.managers.add(m);
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
        return this.managers.stream()
                .map(Manager::getOrders)
                .flatMap(List::stream)
                .toList();
    }

//    public void setAllOrders(List<Order> allOrders) {
//        this.managers. = allOrders;
//    }
}
