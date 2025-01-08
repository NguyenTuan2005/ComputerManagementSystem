package data;

import entity.*;
import enums.OrderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerData {

    List<Product> products;
    List<Supplier> suppliers;
    List<Customer> customers;
    List<Manager> managers;
    List<OrderDetail> orderDetails;
    public static int orderId = 0;

    //IMAGE
    @Getter(AccessLevel.NONE)
    List<Image> imagesMacM1 = List.of(
            Image.builder().id(1).url("macbook-air-2020-m1-v1.png").build(),
            Image.builder().id(2).url("macbook-air-2020-m1-v2.png").build(),
            Image.builder().id(3).url("macbook-air-2020-m1-v3.png").build()
    );

    @Getter(AccessLevel.NONE)
    List<Image> imagesMacM2 = List.of(
            Image.builder().id(1).url("src/main/java/img/macbook_air_m2_1.png").build(),
            Image.builder().id(2).url("src/main/java/img/macbook_air_m2_2.png").build(),
            Image.builder().id(3).url("src/main/java/img/macbook_air_m2_2.png").build()
    );

    @Getter(AccessLevel.NONE)
    List<Image> imagesDellXPS13 = List.of(
            Image.builder().id(4).url("src/main/java/img/Dell_XPS_13.jpg").build()
    );

    @Getter(AccessLevel.NONE)
    List<Image> imagesDellXPS15 = List.of(
            Image.builder().id(4).url("src/main/java/img/Dell_XPS_15.jpg").build()
    );

    @Getter(AccessLevel.NONE)
    List<Image> imagesSurfaceLaptop5 = List.of(
            Image.builder().id(4).url("src/main/java/img/lap_surface-v1.png").build(),
            Image.builder().id(4).url("src/main/java/img/lap_surface-v2.png").build(),
            Image.builder().id(4).url("src/main/java/img/lap_surface-v3.png").build()
    );
    @Getter(AccessLevel.NONE)
    List<Image> imagesAsusROGZephyrusG14 = List.of(
            Image.builder().id(4).url("src/main/java/img/laptop-asus-gaming-rog-zephyrus-g15-ga503qc-hn074t-v3.png").build()
    );
    @Getter(AccessLevel.NONE)
    List<Image> imagesThinkPadC = List.of(
            Image.builder().id(4).url("src/main/java/img/laptop-lenovo-thinkpad-x1-carbon-gen-7-v1.png").build(),
            Image.builder().id(4).url("src/main/java/img/laptop-lenovo-thinkpad-x1-carbon-gen-7-v2.png").build(),
            Image.builder().id(4).url("src/main/java/img/laptop-lenovo-thinkpad-x1-carbon-gen-7-v3.png").build()
    );

    @Getter(AccessLevel.NONE)
    List<Image> imagesHpX360 = List.of(
            Image.builder().id(4).url("src/main/java/img/HP_Spectre_X360.jpg").build()
    );


    @Getter(AccessLevel.NONE)
    List<Image> imagesLgGram17 = List.of(
            Image.builder().id(4).url("src/main/java/img/LG_Gram_17.jpg").build()
    );
    @Getter(AccessLevel.NONE)
    List<Image> imagesMacBookPro16M2Pro = List.of(
            Image.builder().id(4).url("src/main/java/img/macbook-pro-14-inch-2021-8cpu-14gpu-16gb-96w-v2.png").build(),
            Image.builder().id(4).url("src/main/java/img/macbook-pro-14-inch-2021-8cpu-14gpu-16gb-96w-v1.png").build(),
            Image.builder().id(4).url("src/main/java/img/macbook-pro-14-inch-2021-8cpu-14gpu-16gb-96w-v3.png").build()
    );


    //SUPPLIER
    @Getter(AccessLevel.NONE)
    Supplier phongVuComputer = Supplier.builder().id(1).companyName("Phong Vũ Computer").email("duynguyen@gamil.com").phoneNumber("18006865").address("Tầng 2, số 2A Trần Đại Nghĩa, Hai Bà Trưng, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

    @Getter(AccessLevel.NONE)
    Supplier gearVN = Supplier.builder().id(2).companyName("GearVN").email("gearvn@gmail.com").phoneNumber("18006173").address("78-80 Hoàng Hoa Thám, P.12, Q.Tân Bình, TP.HCM").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

    @Getter(AccessLevel.NONE)
    Supplier hanoicomputer = Supplier.builder().id(3).companyName("Hanoicomputer").email("hnc@hanoicomputer.com").phoneNumber("19001903").address("131 Lê Thanh Nghị, Hai Bà Trưng, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

    @Getter(AccessLevel.NONE)
    Supplier benComputer = Supplier.builder().id(4).companyName("Ben Computer").email("bencomputer@gmail.com").phoneNumber("0899179993").address("74 Nguyễn Khánh Toàn, Cầu Giấy, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

    //PRODUCT
    @Getter(AccessLevel.NONE)
    Product macBookAirM1 = Product.builder().id(1).supplier(phongVuComputer).name("MacBook Air M1 2020").quantity(123).price(18190000).type("Laptop").brand("Apple").operatingSystem("macOS").cpu("Apple M1").memory("256GB SSD").ram("8GB").disk("None").monitor("13.3-inch Retina").weight(1.29f).card("Integrated Apple GPU").madeIn("USA").status("In Stock").isActive(true).images(imagesMacM1).build();

    @Getter(AccessLevel.NONE)
    Product macBookAirM2 = Product.builder().id(2).supplier(gearVN).name("MacBook Air M2 2023").quantity(500).price(27690000).type("Laptop").brand("Apple").operatingSystem("macOS").cpu("Apple M2").memory("512GB SSD").ram("8GB").disk("None").monitor("13.6-inch Liquid Retina").weight(1.24f).card("Integrated Apple GPU").madeIn("USA").status("In Stock").isActive(true).images(imagesMacM2).build();

    @Getter(AccessLevel.NONE)
    Product dellXPS13 = Product.builder().id(3).supplier(phongVuComputer).name("Dell XPS 13").quantity(200).price(30000000).type("Ultrabook").brand("Dell").operatingSystem("Windows 11").cpu("Intel Core i7").memory("512GB SSD").ram("16GB").disk("None").monitor("13.4-inch FHD+").weight(1.2f).card("Integrated Intel Iris Xe").madeIn("USA").status("In Stock").isActive(true).images(imagesDellXPS13).build();

    @Getter(AccessLevel.NONE)
    Product dellXPS15 = Product.builder().id(4).supplier(hanoicomputer).name("Dell XPS 15").quantity(234).price(35000000).type("Ultrabook").brand("Dell").operatingSystem("Windows 11").cpu("Intel Core i7").memory("1TB SSD").ram("16GB").disk("None").monitor("15.6-inch 4K UHD+").weight(1.83f).card("NVIDIA GeForce GTX 1650 Ti").madeIn("USA").status("In Stock").isActive(true).images(imagesDellXPS15).build();

    @Getter(AccessLevel.NONE)
    Product surfaceLaptop5 = Product.builder().id(5).supplier(gearVN).name("Surface Laptop 5").quantity(567).price(24000000).type("Ultrabook").brand("Microsoft").operatingSystem("Windows 11").cpu("Intel Core i5").memory("256GB SSD").ram("8GB").disk("None").monitor("13.5-inch PixelSense").weight(1.27f).card("Integrated Intel Iris Xe").madeIn("USA").status("In Stock").isActive(true).images(imagesSurfaceLaptop5).build();

    @Getter(AccessLevel.NONE)
    Product asusROGZephyrusG15 = Product.builder().id(6).supplier(benComputer).name("Asus ROG Zephyrus G15").quantity(456).price(40000000).type("Gaming Laptop").brand("Asus").operatingSystem("Windows 11").cpu("AMD Ryzen 9").memory("1TB SSD").ram("16GB").disk("None").monitor("15.6-inch QHD").weight(1.9f).card("NVIDIA GeForce RTX 3070").madeIn("Taiwan").status("In Stock").isActive(true).images(imagesAsusROGZephyrusG14).build();

    @Getter(AccessLevel.NONE)
    Product lenovoThinkPadX1CarbonGen7 = Product.builder().id(7).supplier(hanoicomputer).name("Lenovo ThinkPad X1 Carbon Gen 7").quantity(464).price(30000000).type("Business Laptop").brand("Lenovo").operatingSystem("Windows 11").cpu("Intel Core i7").memory("512GB SSD").ram("16GB").disk("None").monitor("14-inch FHD").weight(1.09f).card("Integrated Intel UHD Graphics").madeIn("China").status("In Stock").isActive(true).images(imagesThinkPadC).build();

    @Getter(AccessLevel.NONE)
    Product hpSpectreX360 = Product.builder().id(8).supplier(phongVuComputer).name("HP Spectre X360").quantity(234).price(27000000).type("Ultrabook").brand("HP").operatingSystem("Windows 11").cpu("Intel Core i7").memory("512GB SSD").ram("16GB").disk("None").monitor("13.3-inch FHD").weight(1.32f).card("Integrated Intel Iris Xe").madeIn("USA").status("In Stock").isActive(true).images(imagesHpX360).build();

    @Getter(AccessLevel.NONE)
    Product lgGram17 = Product.builder().id(9).supplier(benComputer).name("LG Gram 17").quantity(412).price(35000000).type("Ultrabook").brand("LG").operatingSystem("Windows 11").cpu("Intel Core i7").memory("1TB SSD").ram("16GB").disk("None").monitor("17-inch WQXGA").weight(1.35f).card("Integrated Intel Iris Xe").madeIn("South Korea").status("In Stock").isActive(true).images(imagesLgGram17).build();

    @Getter(AccessLevel.NONE)
    Product macBookPro16M2Pro = Product.builder().id(10).supplier(phongVuComputer).name("MacBook Pro 16 M2 Pro").quantity(123).price(60000000).type("Laptop").brand("Apple").operatingSystem("macOS").cpu("Apple M2 Pro").memory("1TB SSD").ram("16GB").disk("None").monitor("16.2-inch Liquid Retina XDR").weight(2.15f).card("Integrated Apple GPU").madeIn("USA").status("In Stock").isActive(true).images(imagesMacBookPro16M2Pro).build();

    @Getter(AccessLevel.NONE)
    Customer huyen = Customer.builder()
            .id(1)
            .fullName("Nguyen Thi Ngoc Huyen")
            .email("23130075@st.hcmuaf.edu.vn")
            .address("Tien Giang, Chau Thanh, Duong Diem")
            .phone("0123456789")
            .dob(LocalDate.of(2000, 5, 15))
            .avatarImg("src/main/java/img/cus_huyen.jpg")
            .password("$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO")
            .createdAt(LocalDate.now())
            .isActive(true)
            .build();
    @Getter(AccessLevel.NONE)
    Customer thanh = Customer.builder()
            .id(2)
            .fullName("Nguyen Thi Kim Thanh")
            .email("abc@st.hcmuaf.edu.vn")
            .address("TPHCM, Q9, ABC")
            .phone("0987654321")
            .dob(LocalDate.of(1998, 8, 20))
            .avatarImg("src/main/java/img/dom.jpg")
            .password("$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO")
            .createdAt(LocalDate.of(2024, 12, 2))
            .isActive(true)
            .build();
    @Getter(AccessLevel.NONE)
    Customer hieu = Customer.builder()
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
    @Getter(AccessLevel.NONE)
    Customer nguyenVanChay = Customer.builder()
            .id(4)
            .fullName("Nguyen Van Chay")
            .email("abceww@st.hcmuaf.edu.vn")
            .address("Tien Giang, Chau Thanh, Nhi Quy, 234")
            .phone("0976543210")
            .dob(LocalDate.of(2001, 3, 25))
            .avatarImg("src/main/java/img/chay.png")
            .password("$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO")
            .createdAt(LocalDate.of(2024, 12, 2))
            .isActive(true)
            .build();

    //OrderDetail
    @Getter(AccessLevel.NONE)
    OrderDetail od1 = OrderDetail.builder().product(macBookAirM1).quantity(3).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od2 = OrderDetail.builder().product(dellXPS13).quantity(10).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od3 = OrderDetail.builder().product(dellXPS15).quantity(9).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od4 = OrderDetail.builder().product(surfaceLaptop5).quantity(2).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od5 = OrderDetail.builder().product(asusROGZephyrusG15).quantity(7).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od6 = OrderDetail.builder().product(lenovoThinkPadX1CarbonGen7).quantity(1).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od7 = OrderDetail.builder().product(hpSpectreX360).quantity(3).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od8 = OrderDetail.builder().product(lgGram17).quantity(8).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od9 = OrderDetail.builder().product(lenovoThinkPadX1CarbonGen7).quantity(6).build();
    @Getter(AccessLevel.NONE)
    OrderDetail od10 = OrderDetail.builder().product(macBookPro16M2Pro).quantity(2).build();

    //Order
    @Getter(AccessLevel.NONE)
    Order order1 = Order.builder().orderId(1).customer(huyen).shipAddress("Cu Xa B, Dai hoc Nong Lam").orderedAt(LocalDate.of(2025, 1, 1)).status(OrderType.ACTIVE_MESSAGE).orderDetails(new ArrayList<>(Arrays.asList(od2, od3, od5))).build();
    @Getter(AccessLevel.NONE)
    Order order2 = Order.builder().orderId(2).customer(hieu).shipAddress("Cu Xa C, Dai hoc Nong Lam").orderedAt(LocalDate.of(2025, 1, 1)).status(OrderType.ACTIVE_MESSAGE).orderDetails(new ArrayList<>(Arrays.asList(od9, od10))).build();
    @Getter(AccessLevel.NONE)
    Order order3 = Order.builder().orderId(3).customer(thanh).shipAddress("Cu Xa E, Dai hoc Nong Lam").orderedAt(LocalDate.of(2025, 1, 1)).status(OrderType.ACTIVE_MESSAGE).orderDetails(new ArrayList<>(Arrays.asList(od8, od7, od10, od9))).build();

    @Getter(AccessLevel.NONE)
    List<Order> orders1 = new ArrayList<>(Arrays.asList(order1));
    @Getter(AccessLevel.NONE)
    List<Order> orders2 = new ArrayList<>(Arrays.asList(order2));
    @Getter(AccessLevel.NONE)
    List<Order> orders3 = new ArrayList<>(Arrays.asList(order3));

    @Getter(AccessLevel.NONE)
    Manager DUY = Manager.builder()
            .id(1)
            .fullName("Nguyen Huu Duy")
            .email("v")
            .address("Tien Giang Chau Thanh Diem Hy, 125/p3/2")
            .password("$2a$10$jXau3IWFWbArMhmH6GhAY.HUrFLNB3McHuPQmhuZzW3YRJpDTvBhG") // pass 123
            .createdAt(LocalDate.of(1980, 3, 25))
            .dob(LocalDate.of(1950, 3, 25))
            .phone("987654321")
            .avatarImg("src/main/java/img/anhDuyVipPro.png")
            .isActive(true)
            .orders(new ArrayList<>())
            .build();
    @Getter(AccessLevel.NONE)
    Manager TUAN = Manager.builder()
            .id(2)
            .fullName("Tuan Nguyen")
            .email("23130370@st.hcmuaf.edu.vn")
            .address("Tien Giang Chau Thanh Diem Hy, 125/p3/2")
            .password("$2a$10$jXau3IWFWbArMhmH6GhAY.HUrFLNB3McHuPQmhuZzW3YRJpDTvBhG") // pass 123
            .createdAt(LocalDate.of(1980, 3, 25))
            .dob(LocalDate.of(1950, 3, 25))
            .phone("987654321")
            .avatarImg("src/main/java/img/tuan_avata.jpg")
            .isActive(true)
            .orders(new ArrayList<>())
            .build();
    @Getter(AccessLevel.NONE)
    Manager HOANG = Manager.builder()
            .id(3)
            .fullName("Huy Hoang")
            .email("23130075@st.hcmuaf.edu.vn")
            .address("Tien Giang Chau Thanh Diem Hy, 125/p3/2")
            .password("$2a$10$jXau3IWFWbArMhmH6GhAY.HUrFLNB3McHuPQmhuZzW3YRJpDTvBhG") // pass 123
            .createdAt(LocalDate.of(1980, 3, 25))
            .dob(LocalDate.of(1950, 3, 25))
            .phone("987654321").avatarImg("src/main/java/img/hoangPb_Avatar.jpg")
            .isActive(true)
            .orders(new ArrayList<>())
            .build();
    public ManagerData() {

        suppliers = new ArrayList<>(Arrays.asList(
                phongVuComputer,
                gearVN,
                hanoicomputer,
                benComputer
        ));


        products = new ArrayList<>(Arrays.asList(
                macBookAirM1,
                macBookAirM2,
                dellXPS13,
                dellXPS15,
                surfaceLaptop5,
                asusROGZephyrusG15,
                lenovoThinkPadX1CarbonGen7,
                hpSpectreX360,
                lgGram17,
                macBookPro16M2Pro
        ));

        customers = new ArrayList<>(Arrays.asList(huyen, thanh, hieu, nguyenVanChay));

        orderDetails = new ArrayList<>(Arrays.asList(od1, od2, od3, od4, od5, od6, od7, od8, od9, od10));
        DUY.addOrder(orders1);
        HOANG.addOrder(orders2);
        TUAN.addOrder(orders3);
        orderId = 3;

        managers = new ArrayList<>(Arrays.asList(DUY, HOANG, TUAN));
    }
}
