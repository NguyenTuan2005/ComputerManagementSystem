package model;

import config.CurrentUser;
import controller.MController;
import data.ManagerData;
import enums.DisplayProductType;
import enums.LoginStatus;
import enums.OrderType;
import enums.UserType;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import security.PasswordSecurity;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerShop implements MController {
  List<Product> products;
  List<Supplier> suppliers;
  List<Manager> managers;
  List<Customer> customers;
  public DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);
  public static DecimalFormat currencyFormatter = new DecimalFormat("#,###");
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
    Customer hieu =
        Customer.builder()
            .id(3)
            .fullName("Nguyen Van Hieu")
            .email("a")
            .address("TPHCM, Thu Duc, NLU, Duong So 6")
            .phone("0934567890") // Số điện thoại giả định
            .dob(LocalDate.of(1999, 11, 12))
            .avatarImg("src/main/java/img/hieu2.png")
            .password("$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO")
            .createdAt(LocalDate.of(2024, 2, 2))
            .isActive(true)
            .build();

    Order order3 =
        Order.builder()
            .orderId(3)
            .customer(hieu)
            .shipAddress("###########")
            .orderedAt(LocalDate.of(2025, 1, 1))
            .status(OrderType.ACTIVE_MESSAGE)
            .orderDetails(new ArrayList<>())
            .build();

    //        List<Order> orders3 = new ArrayList<>(Arrays.asList(order3));
    c.addOrder(order3);
    c.getAllOrderByCustomer(hieu).forEach(System.out::println);
  }

  @Override
  public boolean login(String email, String pass, LoginStatus loginStatus) {
    loginStatusManager = LoginStatus.NOT_FOUND;
    switch (loginStatus.getMessager()) {
      case ("Manager"):
        {
          Manager manager =
              this.managers.stream().filter(m -> m.sameEmail(email)).findFirst().orElse(null);
          boolean isNotFound = (manager == null);
          if (isNotFound) return false;
          if (manager.isValidPassword(pass)) {
            CurrentUser.CURRENT_USER_V2 = manager;
            CurrentUser.CURRENT_MANAGER_V2 = manager;
            CurrentUser.URL = manager.getAvatarImg();

            return true;
          } else loginStatusManager = LoginStatus.WRONG_EMAIL;
          break;
        }
      case ("Customer"):
        {
          Customer customer =
              this.customers.stream().filter(c -> c.sameEmail(email)).findFirst().orElse(null);
          boolean isNotFound = (customer == null);
          if (isNotFound) return false;
          if (customer.isValidPassword(pass)) {
            CurrentUser.CURRENT_USER_V2 = customer;
            CurrentUser.URL = customer.getAvatarImg();
            return true;
          } else loginStatusManager = LoginStatus.WRONG_EMAIL;

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
  public void addOrder(Order newOrder) {
    for (int i = 0; i < managers.size(); i++) {
      if (managers.get(i).sameEmail(CurrentUser.CURRENT_MANAGER_V2.getEmail())) {
        managers.get(i).addOrder(newOrder);
        System.out.println("add thanh cong");
        break;
      }
    }
  }

  @Override
  public void sigUpCustomer(Customer newCustomer) {
    this.customers.add(newCustomer);
  }

  @Override
  public List<Manager> getAllManager() {
    return this.managers;
  }

  @Override
  public Manager findManagerByEmail(String email) {
    return this.managers.stream().filter(m -> m.sameEmail(email)).findAny().orElse(null);
  }

  @Override
  public int getNextIdOfCustomer() {
    return this.customers.get(customers.size() - 1).getId() + 1;
  }

  @Override
  public int getNextIdOfManage() {
    return this.customers.get(customers.size() - 1).getId() + 1;
  }

  public List<Manager> findManagerByName(String name) {
    return this.managers.stream().filter(m -> m.findName(name)).collect(Collectors.toList());
  }

  @Override
  public List<Product> findProductByName(String name) {
    return this.products.stream().filter(p -> p.like(name)).collect(Collectors.toList());
  }

  @Override
  public Map<Product, Long> productOrderStatistics() {
    Map<Product, Long> quantityOrder = new HashMap<>();

    for (var p : this.products) {
      long sumQuantity = 0;
      for (var manager : this.managers) {
        sumQuantity += manager.getQuantityOfProduct(p);
      }
      quantityOrder.put(p, sumQuantity);
    }

    return quantityOrder;
  }

  @Override
  public List<Supplier> findSuppliersByName(String name) {
    return this.suppliers.stream()
        .filter(s -> s.sameCompanyName(name))
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, Long> quantitativeAnalysis() {
    Map<String, Long> map = new HashMap<>();
    for (var s : this.suppliers) {
      long sumQuantity = 0;
      for (var m : this.managers) sumQuantity = m.getQuantityInOrders(s);

      map.put(s.getCompanyName(), sumQuantity);
    }
    return map;
  }

  @Override
  public Map<String, Long> analyzeQuantityOfImportedGoods() {
    Map<String, Long> map = new HashMap<>();
    for (var s : this.suppliers) {
      long sumQuantity = 0;
      for (var p : this.products) if (p.sameSupplier(s)) sumQuantity++;

      map.put(s.getCompanyName(), sumQuantity);
    }
    return map;
  }

  // statistics by monthly revenue
  public Map<String, Double> analyzeRevenueByMonth(int year) {
    Map<String, Double> revenueByMonth =
        this.managers.stream()
            .flatMap(manager -> manager.getOrders().stream())
            .filter(order -> order.getOrderedAt().getYear() == year)
            .collect(
                Collectors.groupingBy(
                    order -> monthFormatter.format(order.getOrderedAt().getMonth()),
                    Collectors.summingDouble(order -> order.totalCost())));

    List<String> monthsOrder = Arrays.asList(new DateFormatSymbols().getMonths());

    TreeMap result =
        new TreeMap<>(
            (month1, month2) -> {
              int index1 = monthsOrder.indexOf(month1);
              int index2 = monthsOrder.indexOf(month2);
              return Integer.compare(index1, index2);
            });
    result.putAll(revenueByMonth);
    return result;
  }

  // statistics by order status by year
  public Map<String, Integer> analyzeOrderStatusByYear(int year) {
    return this.managers.stream()
        .flatMap(manager -> manager.getOrders().stream())
        .filter(order -> order.getOrderedAt().getYear() == year)
        .collect(
            Collectors.groupingBy(
                order -> order.getStatus(),
                Collectors.collectingAndThen(
                    Collectors.counting(), count -> Math.toIntExact(count))));
  }

  // statistics by order status all the time
  public Map<String, Integer> analyzeOrderStatus() {
    return this.managers.stream()
        .flatMap(manager -> manager.getOrders().stream())
        .collect(
            Collectors.groupingBy(
                order -> order.getStatus(),
                Collectors.collectingAndThen(
                    Collectors.counting(), count -> Math.toIntExact(count))));
  }

  // statistics best-selling product, Product name - quantity sold
  public Map<String, Integer> bestSellingProductStatistics(int limit) {
    Map<String, Integer> productSales =
        managers.stream()
            .flatMap(manager -> manager.getOrders().stream())
            .flatMap(order -> order.getOrderDetails().stream())
            .collect(
                Collectors.toMap(
                    OrderDetail::getProductName, OrderDetail::getQuantity, Integer::sum));
    return productSales.entrySet().stream()
        .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
        .limit(limit)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  // statistics manager kpi
  public Map<Manager, Double> analyzeRevenueByManager() {
    return this.managers.stream()
        .collect(Collectors.toMap(manager -> manager, manager -> manager.totalProductPrice()));
  }

  // statistics manager kpi
  public Map<Manager, Integer> analyzeOrdersByManager() {
    return this.managers.stream()
        .collect(Collectors.toMap(manager -> manager, manager -> manager.getTotalOrders()));
  }

  public Supplier findSupplier(String name) {
    return this.suppliers.stream()
        .filter(s -> s.exactlySameCompanyName(name))
        .findFirst()
        .orElse(null);
  }

  public void sortSupplierByColumn(String column, List<Supplier> suppliers) {
    switch (column) {
      case "NAME" -> suppliers.sort(Supplier::compareName);
      case "EMAIL" -> suppliers.sort(Supplier::compareEmail);
      case "PHONE NUMBER" -> suppliers.sort(Supplier::comparePhone);
      case "ADDRESS" -> suppliers.sort(Supplier::compareAddress);
      case "DATE" -> suppliers.sort(Supplier::compareDate);
    }
    ;
  }

  public void removeProduct(Product p) {
    this.products.remove(p);
  }

  @Override
  public void removeProductByIndex(int index) {
    this.products.remove(index);
  }

  @Override
  public void updateProduct(Product product) {
    ListIterator<Product> iterator = this.products.listIterator();
    while (iterator.hasNext()) {
      var p = iterator.next();
      if (p.sameId(product)) {
        iterator.set(product);
        return;
      }
    }
  }

  @Override
  public List<Product> filterBy(DisplayProductType type) {
    switch (type) {
      case ALL -> {
        return this.getAllProduct();
      }
      case PC_CASE -> {
        return this.products.stream().filter(p -> p.isPc()).collect(Collectors.toList());
      }
      case LAPTOP_GAMING -> {
        return this.products.stream().filter(p -> p.isLapGaming()).collect(Collectors.toList());
      }
      case LAPTOP_OFFICE -> {
        return this.products.stream().filter(p -> p.isLapOffice()).collect(Collectors.toList());
      }
      case PRICE_IN_AMOUNT_10M_20M -> {
        return this.products.stream()
            .filter(
                p ->
                    p.priceInAmount(
                        DisplayProductType.TEN_MILION, DisplayProductType.TWENTY_MILION))
            .collect(Collectors.toList());
      }
      case PRICE_IN_AMOUNT_20M_30M -> {
        return this.products.stream()
            .filter(
                p ->
                    p.priceInAmount(
                        DisplayProductType.TWENTY_MILION, DisplayProductType.THIRTY_MILION))
            .collect(Collectors.toList());
      }
    }
    return List.of();
  }

  public void addProduct(Product p) {
    this.products.add(p);
  }

  @Override
  public void updateUserInfor(User user) {
    if (user.isManager()) {
      ListIterator<Manager> iterator = this.managers.listIterator();
      while (iterator.hasNext()) {
        var m = iterator.next();
        if (m.sameEmail(user.getEmail())) {
          iterator.set((Manager) user);
          break;
        }
      }
    } else {
      ListIterator<Customer> iterator = this.customers.listIterator();
      while (iterator.hasNext()) {
        var c = iterator.next();
        if (c.sameEmail(user.getEmail())) {
          iterator.set((Customer) user);
          break;
        }
      }
    }
  }

  @Override
  public void updateSupplier(Supplier supplier) {
    ListIterator<Supplier> iterator = this.suppliers.listIterator();
    while (iterator.hasNext()) {
      var sup = iterator.next();
      if (sup.sameEmail(supplier)) {
        iterator.set(supplier);
      }
    }
    ListIterator<Manager> managerListIterator = this.managers.listIterator();
    while (managerListIterator.hasNext()) {
      managerListIterator.next().updateSupplier(supplier);
    }
  }

  @Override
  public Map<Supplier, Long> totalProductStatictics() {
    Map<Supplier, Long> map = new HashMap<>();
    for (var sup : this.suppliers) {
      long total =
          this.products.stream()
              .filter(p -> p.sameSupplier(sup))
              .collect(Collectors.summarizingLong(p -> p.getQuantity()))
              .getSum();
      map.put(sup, total);
    }
    return map;
  }

  public Map<Supplier, Long> totalProductsBySupplier() {
    return this.products.stream()
            .collect(Collectors.groupingBy(
                    Product::getSupplier,
                    Collectors.summingLong(Product::getQuantity)
            ));
  }

  @Override
  public Customer findCustomerByEmail(String email) {
    return this.customers.stream()
        .filter(customer -> customer.sameEmail(email))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void addSupplier(Supplier newSupplier) {
    this.suppliers.add(newSupplier);
  }

  @Override
  public void removeSupplierByIndex(int index) {
    this.suppliers.remove(index);
  }

  // Customer
  public void removeCustomer(Customer c) {
    this.customers.remove(c);
  }

  public void addCustomer(Customer c) {
    this.customers.add(c);
  }

  public int getTotalProduct() {
    return this.products.size();
  }

  public int getTotalSupplier() {
    return this.suppliers.size();
  }

  public int getTotalManager() {
    return this.managers.size();
  }

  public int getTotalCustomer() {
    return this.customers.size();
  }

  public int getTotalOrder() {
    return this.managers.stream().mapToInt(manager -> manager.getTotalOrders()).sum();
  }

  public void blockManager(Manager m, boolean block) {
    for (Manager man : this.managers) {
      if (man.equals(m)) {
        m.changeActive(block);
        break;
      }
    }
  }

  public void blockCustomer(Customer c, boolean block) {
    for (Customer cus : this.customers) {
      if (cus.equals(c)) {
        c.changeActive(block);
        break;
      }
    }
  }
  // Manager
  public void removeManager(Manager m) {
    this.managers.remove(m);
  }

  public void addManager(Manager m) {
    this.managers.add(m);
  }

  public List<Customer> findCustomerByName(String customerName) {
    return this.customers.stream()
        .filter(
            customer -> customer.getFullName().toLowerCase().contains(customerName.toLowerCase()))
        .collect(Collectors.toList());
  }

  @Override
  public void blockCustomerById(int id) {
    for (int i = 0; i < this.customers.size(); i++) {
      if (customers.get(i).isId(id)) {
        customers.get(i).changeActive(false);
        System.out.println(customers.get(i));
        break;
      }
    }
  }

  @Override
  public void unBlockCustomerById(int id) {
    for (int i = 0; i < this.customers.size(); i++) {
      if (customers.get(i).isId(id)) {
        customers.get(i).changeActive(true);
        System.out.println(customers.get(i));
        break;
      }
    }
  }

  @Override
  public Map<Customer, Long> customerOrderStatistics() {
    Map<Customer, Long> map = new HashMap<>();
    for (var customer : this.customers) {
      long totalOrder = 0;
      for (var manager : this.managers) {
        totalOrder += manager.getTotalOrderOfCustomer(customer);
      }
      map.put(customer, totalOrder);
    }
    return map;
  }

  @Override
  public List<Order> getAllOrderByCustomer(Customer customer) {
    return this.managers.stream()
        .map(m -> m.getOrders())
        .flatMap(List::stream)
        .filter(order -> order.isCustomer(customer))
        .toList();
  }

  @Override
  public List<Order> findOrderByCustomerAndDate(Customer customer, LocalDate orderedAt) {
    return this.getAllOrderByCustomer(customer).stream()
        .filter(order -> order.orderedAt(orderedAt))
        .toList();
  }

  @Override
  public void changePassword(UserType type, String email, String password) {
    if (type.isCustomer()) {
      for (int i = 0; i < this.customers.size(); i++) {
        if (customers.get(i).sameEmail(email)) {
          customers.get(i).updatePassword(new PasswordSecurity(password).generatePassword());
          return;
        }
      }
    } else if (type.isManager()) {
      for (int i = 0; i < this.managers.size(); i++) {
        if (managers.get(i).sameEmail(email)) {
          managers.get(i).updatePassword(new PasswordSecurity(password).generatePassword());
          return;
        }
      }
    }
  }

  public List<Supplier> findSupplierByName(String suppilerName) {
    return this.suppliers.stream()
        .filter(supp -> supp.getCompanyName().toLowerCase().contains(suppilerName.toLowerCase()))
        .collect(Collectors.toList());
  }

  public List<Order> getAllOrders() {
    return this.managers.stream().map(Manager::getOrders).flatMap(List::stream).toList();
  }

  public int totalProductQuantity() {
    return this.managers.stream()
        .flatMap(manager -> manager.getOrders().stream())
        .mapToInt(order -> order.totalQuantity())
        .sum();
  }

  // sort manager list by quantity of order
  public List<Manager> sortManagerByOrder() {
    return managers.stream()
        .sorted(Comparator.comparingInt(Manager::getTotalOrders).reversed())
        .collect(Collectors.toList());
  }

  public List<Manager> sortManagerByTotalPrice() {
    return managers.stream()
        .sorted(Comparator.comparingDouble(Manager::totalProductPrice).reversed())
        .collect(Collectors.toList());
  }

  public List<Manager> sortManagerByProcessedOrders() {
    return managers.stream()
        .sorted(Comparator.comparingLong(Manager::totalOrderCompleted).reversed())
        .collect(Collectors.toList());
  }

  //    public void setAllOrders(List<Order> allOrders) {
  //        this.managers. = allOrders;
  //    }
}
