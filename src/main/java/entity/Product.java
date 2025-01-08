package  entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static view.ManagerMainPanel.formatCurrency;

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


    @Override
    public boolean equals(Object obj) {

            if (obj==null || !(obj instanceof entity.Product))
                return false;
            else {
                entity.Product that= (entity.Product) obj;
                return this.id == that.id &&
                        this.name.equals(that.name) &&
                        this.supplier.equals(that.supplier) &&
                        this.quantity == that.quantity &&
                        this.price == that.price &&
                        this.type.equals(that.type) &&
                        this.brand.equals(that.brand) &&
                        this.operatingSystem.equals(that.operatingSystem) &&
                        this.cpu.equals(that.cpu) &&
                        this.memory.equals(that.memory) &&
                        this.ram.equals(that.ram) &&
                        this.madeIn.equals(that.madeIn) &&
                        this.disk.equals(that.disk) &&
                        this.monitor.equals(that.monitor) &&
                        this.weight ==that.weight &&
                        this.card.equals(that.card) &&
                        this.images.equals(that.images) &&
                        this.status.equals(that.status) &&
                        this.isActive == that.isActive;
            }
    }

    public boolean sameName(String name) {
        return this.name.equals(name);
    }

    private String[] convertToArray(int serial) {
        String[] result = {
                String.valueOf(serial),
                String.valueOf(id),
                name,
                String.valueOf(quantity),
                formatCurrency.format(price),
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

    public boolean sameSupplier(Supplier supplier) {
        return this.supplier.equals(supplier);
    }
    public boolean sameEmailSupplier(Supplier supplier){
        return this.supplier.sameEmail(supplier);
    }
    public void changeSupplier(Supplier supplier){
        this.supplier =supplier;
    }
}
