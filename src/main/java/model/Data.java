package model;




import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Data {


    public static void main(String[] args) {


//        Supplier supplierq  =  Supplier.builder().build();
        Supplier supplier =
                new Supplier(1, "ABC Supplies", "abc@supplies.com", "123456789", "123 Street", LocalDate.of(2020, 1, 1), true);

        Image image1 = Image.builder().id(12).url("dfsdfsdf").build();
        Image image2 = Image.builder().id(1).url("sdfdsfd").build();
        ArrayList<Image> productImages = new ArrayList<>();
        productImages.add(image1);
        productImages.add(image2);

        // Tạo đối tượng Product

        Product p1 = Product.builder().cpu("I7").brand("a").card("Abc").disk("asd").price(123).ram("16GB").id(1).name("Lennovo 12").type("asad").monitor("asd").quantity(10).status("in").isActive(true).supplier(supplier).images(productImages).build();
        Product p2 = Product.builder().cpu("I7").brand("a").card("Abc").disk("asd").price(123).ram("16GB").id(2).name("Lennovo 12").type("asad").monitor("asd").quantity(10).status("in").isActive(true).supplier(supplier).images(productImages).build();
        Product p3 = Product.builder().cpu("I7").brand("a").card("Abc").disk("asd").price(123).ram("16GB").id(3).name("Lennovo 12").type("asad").monitor("asd").quantity(10).status("in").isActive(true).supplier(supplier).images(productImages).build();
        Product p4 = Product.builder().cpu("I7").brand("a").card("Abc").disk("asd").price(123).ram("16GB").id(4).name("Lennovo 12").type("asad").monitor("asd").quantity(10).status("in").isActive(true).supplier(supplier).images(productImages).build();
        Product p5 = Product.builder().cpu("I7").brand("a").card("Abc").disk("asd").price(123).ram("16GB").id(5).name("Lennovo 12").type("asad").monitor("asd").quantity(10).status("in").isActive(true).supplier(supplier).images(productImages).build();
        Product p6 = Product.builder().cpu("I7").brand("a").card("Abc").disk("asd").price(123).ram("16GB").id(7).name("Lennovo 12").type("asad").monitor("asd").quantity(10).status("in").isActive(true).supplier(supplier).images(productImages).build();

        // Tạo đối tượng OrderDetail
        OrderDetail od1 = new OrderDetail(p1, 2);
        OrderDetail od2 = new OrderDetail(p2, 4);
        OrderDetail od3 = new OrderDetail(p3, 5);
        OrderDetail od4 = new OrderDetail(p5, 9);

        List<OrderDetail> orderDetailsByDuy = new ArrayList<>();
        orderDetailsByDuy.add(od1);
        orderDetailsByDuy.add(od2);
        orderDetailsByDuy.add(od3);
        orderDetailsByDuy.add(od4);

        // Tạo đối tượng Customer
        Customer c1 = Customer.builder().id(1).fullName("A").email("john@example.com").address("123 Main St").password("123").createdAt(LocalDate.of(2000, 2, 2)).dob(LocalDate.of(2000, 2, 2)).phone("987654321").avatarImg("avatar.png").isActive(true).build();
        Customer c2 = Customer.builder().id(1).fullName("B").email("john@example.com").address("123 Main St").password("123").createdAt(LocalDate.of(2000, 2, 2)).dob(LocalDate.of(2000, 2, 2)).phone("987654321").avatarImg("avatar.png").isActive(true).build();
        Customer c3 = Customer.builder().id(1).fullName("C").email("john@example.com").address("123 Main St").password("123").createdAt(LocalDate.of(2000, 2, 2)).dob(LocalDate.of(2000, 2, 2)).phone("987654321").avatarImg("avatar.png").isActive(true).build();
//        System.out.println(c3);
//        Customer c1 = new Customer(1,"nguyen huu A","asdas@ACm","nlu","1232",LocalDate.of(12,2,2),"ul","dfasf",LocalDate.of(231,12,2),true);
//        Customer c2 = new Customer(1,"nguyen huu B","asdas@ACm","nlu","1232",LocalDate.of(12,2,2),"ul","dfasf",LocalDate.of(231,12,2),true);
//        Customer c3 = new Customer(1,"nguyen huu duy","asdas@ACm","nlu","1232",LocalDate.of(12,2,2),"ul","dfasf",LocalDate.of(231,12,2),true);
//        Customer c4 = new Customer(1,"nguyen huu m","asdas@ACm","nlu","1232",LocalDate.of(12,2,2),"ul","dfasf",LocalDate.of(231,12,2),true);
//        System.out.println( new Customer(1,"nguyen huu A","asdas@ACm","nlu","1232",LocalDate.of(12,2,2),"ul","dfasf",LocalDate.of(231,12,2),true));
        // Tạo danh sách OrderDetails

        // Tạo đối tượng Order
        Order order = new Order(1, c1, "456 Another St", LocalDate.now(), "Pending", orderDetailsByDuy);
        Order order1 = new Order(1, c2, "456 Another St", LocalDate.now(), "Pending", orderDetailsByDuy);
        Order order2 = new Order(1, c3, "456 Another St", LocalDate.now(), "Pending", orderDetailsByDuy);
        Order order3 = new Order(1, c1, "456 Another St", LocalDate.now(), "Pending", orderDetailsByDuy);

//         Tạo đối tượng Manager
        Manager manager1 = Manager.builder().id(1).fullName("Jane Smith").email("abc@gmail.com").address("12 St ham").password("123").createdAt( LocalDate.of(1980, 3, 25)).dob( LocalDate.of(1950, 3, 25)).phone("987654321").avatarImg("avatar.png").isActive(true).orders(new ArrayList<>()).build();
        manager1.addOrder(order3);
        manager1.addOrder(order);
        manager1.addOrder(order2);
        manager1.addOrder(order1);
        for (Map.Entry<Customer,List<Order>> data :manager1.userOrderStatistics().entrySet()){
            System.out.print("###"+data.getKey());
            for( var o : data.getValue()){
                System.out.println(o);
            }
            System.out.println("---------------------");
        }
    }
}
