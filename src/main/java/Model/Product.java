package Model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private int id;

    private int suppliersId;

    private String name;

    private int quantity;

    private int price;

    private String genre;

    private String brand;

    private String operatingSystem;

    private String cpu;

    private String memory;

    private String ram;

    private String madeIn;

    private String status;

    private String disk;

    private String monitor;

    private String weight;

    private String card;

    private int deleteRow;

    private ArrayList<Image> images;

    public static final String IN_STOCK = "In Stock";

    public static final String AVAILABLE = "Available";

    public Product(int suppliersId, String name, int quantity, int price, String genre, String brand, String operatingSystem, String cpu, String memory, String ram, String madeIn, String status,int deleteRow) {
        this.suppliersId = suppliersId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.genre = genre;
        this.brand = brand;
        this.operatingSystem = operatingSystem;
        this.cpu = cpu;
        this.memory = memory;
        this.ram = ram;
        this.madeIn = madeIn;
        this.status = status;
        this.deleteRow= deleteRow;
    }

    public Product(int suppliersId, String name, int quantity, int price, String genre, String brand, String operatingSystem, String cpu, String memory, String ram, String madeIn, String status, String disk, String monitor, String weight, String card, int deleteRow) {
        this.suppliersId = suppliersId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.genre = genre;
        this.brand = brand;
        this.operatingSystem = operatingSystem;
        this.cpu = cpu;
        this.memory = memory;
        this.ram = ram;
        this.madeIn = madeIn;
        this.status = status;
        this.disk = disk;
        this.monitor = monitor;
        this.weight = weight;
        this.card = card;
        this.deleteRow = deleteRow;
    }

    private  String [] convertToArray(int serial){
        String []  result =  {String.valueOf(serial)
                ,String.valueOf(id)
                , name
                , String.valueOf(quantity)
                , Integer.toString(price)
                , genre
                , brand
                , operatingSystem
                , cpu
                , memory
                , ram
                , madeIn
                , status
                ,disk
                ,monitor
                ,weight
                ,card
        };
        System.out.println(this.status);
//        System.out.println(Double.toString(price));
        return result;
    }

    public static String [][] getDateOnTable(ArrayList<Product> products){
        String [][] datas = new String[products.size()][];
        for (int i = 0; i < products.size() ; i++) {
            datas[i]= products.get(i).convertToArray(i);
        }
        return datas;
    }

    
}
