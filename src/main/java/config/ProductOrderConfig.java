package config;

import model.Image;
import model.Product;
import model.Supplier;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOrderConfig {
    private Product product;
    private int quantity;

    public ProductOrderConfig(Product product) {
        this.product = product;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrderConfig that = (ProductOrderConfig) o;
        System.out.println(this.product.equals(that.product));
        return this.product.equals(that.product) && this.quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }



    public boolean  hasProductName(Set<ProductOrderConfig> productOrderConfigs){

        for( var p : productOrderConfigs){
            boolean isDQuaity = p.getQuantity() != 1 && this.quantity !=1;
            boolean isSameName =  p.getProduct().getName().equals(product.getName());
            if ( isDQuaity && isSameName ){
                return true;
            }
        }
        return false;
    }

    public static Set<ProductOrderConfig> getUnqueProductOrder(Set<ProductOrderConfig> productOrderConfigs){
        ArrayList<ProductOrderConfig> pocs = new ArrayList<>();
        pocs.addAll(productOrderConfigs);
        for (int i = 0; i < pocs.size()-1; i++) {
            var tamp = pocs.get(i);
            for (int j = 0; j < pocs.size(); j++) {
                var find = pocs.get(j);
                boolean leftQuatity =tamp.getQuantity() == 1 && find.getQuantity() != 1;
                boolean rigthQuatity =tamp.getQuantity() != 1 && find.getQuantity() == 1;
                boolean isSameName =tamp.getProduct().getName() .equals(find.getProduct().getName());
                if (isSameName){
                    if (leftQuatity){
                        productOrderConfigs.remove(tamp);
                    } else if(rigthQuatity){
                        productOrderConfigs.remove(find);
                    }
                }
            }
        }
        return productOrderConfigs;
    }

    public static void main(String[] args) {

        List<Image> imagesMacM1 = List.of(
                Image.builder().id(1).url("src/main/java/img/macbook-air-2020-m1-v1.png").build(),
                Image.builder().id(2).url("src/main/java/img/macbook-air-2020-m1-v2.png").build(),
                Image.builder().id(3).url("src/main/java/img/macbook-air-2020-m1-v3.png").build()
        );


        List<Image> imagesMacM2 = List.of(
                Image.builder().id(1).url("src/main/java/img/macbook_air_m2_1.png").build(),
                Image.builder().id(2).url("src/main/java/img/macbook_air_m2_2.png").build(),
                Image.builder().id(3).url("src/main/java/img/macbook_air_m2_2.png").build()
        );


        List<Image> imagesDellXPS13 = List.of(
                Image.builder().id(4).url("src/main/java/img/Dell_XPS_13.jpg").build()
        );


        List<Image> imagesDellXPS15 = List.of(
                Image.builder().id(4).url("src/main/java/img/Dell_XPS_15.jpg").build()
        );


        List<Image> imagesSurfaceLaptop5 = List.of(
                Image.builder().id(4).url("src/main/java/img/lap_surface-v1.png").build(),
                Image.builder().id(4).url("src/main/java/img/lap_surface-v2.png").build(),
                Image.builder().id(4).url("src/main/java/img/lap_surface-v3.png").build()
        );

        List<Image> imagesAsusROGZephyrusG14 = List.of(
                Image.builder().id(4).url("src/main/java/img/laptop-asus-gaming-rog-zephyrus-g15-ga503qc-hn074t-v3.png").build()
        );

        List<Image> imagesThinkPadC = List.of(
                Image.builder().id(4).url("src/main/java/img/laptop-lenovo-thinkpad-x1-carbon-gen-7-v1.png").build(),
                Image.builder().id(4).url("src/main/java/img/laptop-lenovo-thinkpad-x1-carbon-gen-7-v2.png").build(),
                Image.builder().id(4).url("src/main/java/img/laptop-lenovo-thinkpad-x1-carbon-gen-7-v3.png").build()
        );


        List<Image> imagesHpX360 = List.of(
                Image.builder().id(4).url("src/main/java/img/HP_Spectre_X360.jpg").build()
        );



        List<Image> imagesLgGram17 = List.of(
                Image.builder().id(4).url("src/main/java/img/LG_Gram_17.jpg").build()
        );

        List<Image> imagesMacBookPro16M2Pro = List.of(
                Image.builder().id(4).url("src/main/java/img/macbook-pro-14-inch-2021-8cpu-14gpu-16gb-96w-v2.png").build(),
                Image.builder().id(4).url("src/main/java/img/macbook-pro-14-inch-2021-8cpu-14gpu-16gb-96w-v1.png").build(),
                Image.builder().id(4).url("src/main/java/img/macbook-pro-14-inch-2021-8cpu-14gpu-16gb-96w-v3.png").build()
        );


     
        Supplier phongVuComputer = Supplier.builder().id(1).companyName("Phong Vũ Computer").email("duynguyen@gamil.com").phoneNumber("18006865").address("Tầng 2, số 2A Trần Đại Nghĩa, Hai Bà Trưng, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();
 
        Supplier gearVN = Supplier.builder().id(2).companyName("GearVN").email("gearvn@gmail.com").phoneNumber("18006173").address("78-80 Hoàng Hoa Thám, P.12, Q.Tân Bình, TP.HCM").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();
 
        Supplier hanoicomputer = Supplier.builder().id(3).companyName("Hanoicomputer").email("hnc@hanoicomputer.com").phoneNumber("19001903").address("131 Lê Thanh Nghị, Hai Bà Trưng, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

       
        Supplier benComputer = Supplier.builder().id(4).companyName("Ben Computer").email("bencomputer@gmail.com").phoneNumber("0899179993").address("74 Nguyễn Khánh Toàn, Cầu Giấy, Hà Nội").contractDate(LocalDate.of(2024, 10, 17)).isActive(true).build();

        //PRODUCT
    
        Product macBookAirM1 = Product.builder().id(1).supplier(phongVuComputer).name("MacBook Air M1 2020").quantity(123).price(18190000).type("Laptop_office").brand("Apple").operatingSystem("macOS").cpu("Apple M1").memory("256GB SSD").ram("8GB").disk("None").monitor("13.3-inch Retina").weight(1.29f).card("Integrated Apple GPU").madeIn("USA").status("In Stock").isActive(true).images(imagesMacM1).build();
 
        Product macBookAirM2 = Product.builder().id(2).supplier(gearVN).name("MacBook Air M2 2023").quantity(500).price(27690000).type("Laptop_office").brand("Apple").operatingSystem("macOS").cpu("Apple M2").memory("512GB SSD").ram("8GB").disk("None").monitor("13.6-inch Liquid Retina").weight(1.24f).card("Integrated Apple GPU").madeIn("USA").status("In Stock").isActive(true).images(imagesMacM2).build();

        Product dellXPS13 = Product.builder().id(3).supplier(phongVuComputer).name("Dell XPS 13").quantity(200).price(30000000).type("Laptop_office").brand("Dell").operatingSystem("Windows 11").cpu("Intel Core i7").memory("512GB SSD").ram("16GB").disk("None").monitor("13.4-inch FHD+").weight(1.2f).card("Integrated Intel Iris Xe").madeIn("USA").status("In Stock").isActive(true).images(imagesDellXPS13).build();


        Product dellXPS15 = Product.builder().id(4).supplier(hanoicomputer).name("Dell XPS 15").quantity(234).price(35000000).type("Laptop_office").brand("Dell").operatingSystem("Windows 11").cpu("Intel Core i7").memory("1TB SSD").ram("16GB").disk("None").monitor("15.6-inch 4K UHD+").weight(1.83f).card("NVIDIA GeForce GTX 1650 Ti").madeIn("USA").status("In Stock").isActive(true).images(imagesDellXPS15).build();

        Product surfaceLaptop5 = Product.builder().id(5).supplier(gearVN).name("Surface Laptop 5").quantity(567).price(24000000).type("Laptop_office").brand("Microsoft").operatingSystem("Windows 11").cpu("Intel Core i5").memory("256GB SSD").ram("8GB").disk("None").monitor("13.5-inch PixelSense").weight(1.27f).card("Integrated Intel Iris Xe").madeIn("USA").status("In Stock").isActive(true).images(imagesSurfaceLaptop5).build();


        Product asusROGZephyrusG15 = Product.builder().id(6).supplier(benComputer).name("Asus ROG Zephyrus G15").quantity(456).price(40000000).type("Gaming Laptop").brand("Asus").operatingSystem("Windows 11").cpu("AMD Ryzen 9").memory("1TB SSD").ram("16GB").disk("None").monitor("15.6-inch QHD").weight(1.9f).card("NVIDIA GeForce RTX 3070").madeIn("Taiwan").status("In Stock").isActive(true).images(imagesAsusROGZephyrusG14).build();

        Product lenovoThinkPadX1CarbonGen7 = Product.builder().id(7).supplier(hanoicomputer).name("Lenovo ThinkPad X1 Carbon Gen 7").quantity(464).price(30000000).type("Laptop_office").brand("Lenovo").operatingSystem("Windows 11").cpu("Intel Core i7").memory("512GB SSD").ram("16GB").disk("None").monitor("14-inch FHD").weight(1.09f).card("Integrated Intel UHD Graphics").madeIn("China").status("In Stock").isActive(true).images(imagesThinkPadC).build();

        Product hpSpectreX360 = Product.builder().id(8).supplier(phongVuComputer).name("HP Spectre X360").quantity(234).price(27000000).type("Laptop_gaming").brand("HP").operatingSystem("Windows 11").cpu("Intel Core i7").memory("512GB SSD").ram("16GB").disk("None").monitor("13.3-inch FHD").weight(1.32f).card("Integrated Intel Iris Xe").madeIn("USA").status("In Stock").isActive(true).images(imagesHpX360).build();

        Product lgGram17 = Product.builder().id(9).supplier(benComputer).name("LG Gram 17").quantity(412).price(35000000).type("Laptop_gaming").brand("LG").operatingSystem("Windows 11").cpu("Intel Core i7").memory("1TB SSD").ram("16GB").disk("None").monitor("17-inch WQXGA").weight(1.35f).card("Integrated Intel Iris Xe").madeIn("South Korea").status("In Stock").isActive(true).images(imagesLgGram17).build();


        Product macBookPro16M2Pro = Product.builder().id(10).supplier(phongVuComputer).name("MacBook Pro 16 M2 Pro").quantity(123).price(60000000).type("Laptop").brand("Apple").operatingSystem("macOS").cpu("Apple M2 Pro").memory("1TB SSD").ram("16GB").disk("None").monitor("16.2-inch Liquid Retina XDR").weight(2.15f).card("Integrated Apple GPU").madeIn("USA").status("In Stock").isActive(true).images(imagesMacBookPro16M2Pro).build();


        Set<ProductOrderConfig> demo = new HashSet<>();
        demo.add(new ProductOrderConfig(macBookPro16M2Pro,1));
        demo.add(new ProductOrderConfig(macBookPro16M2Pro,1));
        System.out.println(demo.size());
    }
}
