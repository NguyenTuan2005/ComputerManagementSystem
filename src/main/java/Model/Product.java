package Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private int id;

    private int suppliersId;

    private String name;

    private int quality;

    private double price;

    private String genre;

    private String brand;

    private String operatingSystem;

    private String cpu;

    private String memory;

    private String ram;

    private String madeIn;

    private String status;

    public Product(int suppliersId, String name, int quality, double price, String genre, String brand, String operatingSystem, String cpu, String memory, String ram, String madeIn, String status) {
        this.suppliersId = suppliersId;
        this.name = name;
        this.quality = quality;
        this.price = price;
        this.genre = genre;
        this.brand = brand;
        this.operatingSystem = operatingSystem;
        this.cpu = cpu;
        this.memory = memory;
        this.ram = ram;
        this.madeIn = madeIn;
        this.status = status;
    }
}
