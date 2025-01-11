package model;

import enums.DisplayProductType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

import static view.ManagerMainPanel.currencyFormatter;

@ToString
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    public static final String IN_STOCK = "in stock";
    public static final String AVAILABLE = "available";
    int id;
    Supplier supplier;
    String name;
    int quantity;
    double price;
    String type;
    String brand;
    String operatingSystem;
    String cpu;
    String memory;
    String ram;
    String madeIn;
    String disk;
    String monitor;
    float weight;
    String card;
    List<Image> images;
    String status;
    boolean isActive;


    public boolean sameName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && quantity == product.quantity && Double.compare(price, product.price) == 0 && Float.compare(weight, product.weight) == 0 && isActive == product.isActive && Objects.equals(supplier, product.supplier) && Objects.equals(name, product.name) && Objects.equals(type, product.type) && Objects.equals(brand, product.brand) && Objects.equals(operatingSystem, product.operatingSystem) && Objects.equals(cpu, product.cpu) && Objects.equals(memory, product.memory) && Objects.equals(ram, product.ram) && Objects.equals(madeIn, product.madeIn) && Objects.equals(disk, product.disk) && Objects.equals(monitor, product.monitor) && Objects.equals(card, product.card) && Objects.equals(images, product.images) && Objects.equals(status, product.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplier, name, quantity, price, type, brand, operatingSystem, cpu, memory, ram, madeIn, disk, monitor, weight, card, images, status, isActive);
    }

    private String[] convertToArray(int serial) {
        String[] result = {
                String.valueOf(serial),
                String.valueOf(id),
                name,
                String.valueOf(quantity),
                currencyFormatter.format(price),
                brand,
                brand,
                operatingSystem,
                cpu,
                memory,
                ram,
                madeIn,
                status,
                disk,
                monitor,
                String.valueOf(weight),
                card
        };
        return result;
    }

    public static String[][] getDateOnTable(List<Product> products) {
        String[][] datas = new String[products.size()][];
        for (int i = 0; i < products.size(); i++) {
            datas[i] = products.get(i).convertToArray(i);
        }
        return datas;
    }

    public boolean like(String name) {
        return this.name.toLowerCase().contains(name.toLowerCase());
    }

    public boolean contains(String searchText) {
        return this.name.contains(searchText);
    }

    public void updateSupplier(Supplier sup){
        this.supplier = sup;
    }

    public boolean filter(String status, String searchText) {
        return (status == null || status.isEmpty() || status.equals(this.status)) &&
                (searchText == null || searchText.isEmpty() || contains(searchText.toLowerCase()));
    }

    public boolean updateStatus(String status) {
        if (this.status.equals(status)) return false;
        this.status = status;
        return true;
    }
    public boolean sameSupplier(Supplier supplier) {
        return this.supplier.equals(supplier);
    }
    public boolean sameEmailSupplier(Supplier supplier){
        return this.supplier.sameEmail(supplier);
    }
    public void changeSupplier(Supplier supplier){
        this.supplier =supplier;
    }

    public boolean sameName(Product p) {
        return this.name.equals(p.name);
    }

    public boolean sameId(Product product) {
        return this.id == product.id;
    }


    public boolean isPc() {
        return this.type.equals(DisplayProductType.PC_CASE.getType() );
    }
    public boolean isLapOffice() {
        return this.type.equals(DisplayProductType.LAPTOP_OFFICE.getType() );
    }
    public boolean isLapGaming() {
        return this.type.equals(DisplayProductType.LAPTOP_GAMING.getType() );
    }


    public boolean priceInAmount(int from, int to) {
        return  (this.price > from) && (this.price<=to);
    }
    public boolean priceSmall(int price){
        return this.price <= price;
    }
}
