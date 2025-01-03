package Model;

import java.util.ArrayList;
import java.util.Objects;
import lombok.*;

import static view.ManagerMainPanel.formatCurrency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
  private int id;

  private int suppliersId;

  private String name;

  private int quantity;

  private double price;

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

  public Product(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Product(
      int suppliersId,
      String name,
      int quantity,
      double price,
      String genre,
      String brand,
      String operatingSystem,
      String cpu,
      String memory,
      String ram,
      String madeIn,
      String status,
      int deleteRow) {
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
    this.deleteRow = deleteRow;
  }

  public Product(
      int suppliersId,
      String name,
      int quantity,
      double price,
      String genre,
      String brand,
      String operatingSystem,
      String cpu,
      String memory,
      String ram,
      String madeIn,
      String status,
      String disk,
      String monitor,
      String weight,
      String card,
      int deleteRow) {
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

  private String[] convertToArray(int serial) {
    String[] result = {
      String.valueOf(serial),
      String.valueOf(id),
      name,
      String.valueOf(quantity),
      formatCurrency.format(price),
      genre,
      brand,
      operatingSystem,
      cpu,
      memory,
      ram,
      madeIn,
      status,
      disk,
      monitor,
      weight,
      card
    };
    return result;
  }

  public static String[][] getDateOnTable(ArrayList<Product> products) {
    String[][] datas = new String[products.size()][];
    for (int i = 0; i < products.size(); i++) {
      datas[i] = products.get(i).convertToArray(i);
    }
    return datas;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return id == product.id && this.name.equals(product.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        suppliersId,
        name,
        quantity,
        price,
        genre,
        brand,
        operatingSystem,
        cpu,
        memory,
        ram,
        madeIn,
        status,
        disk,
        monitor,
        weight,
        card,
        deleteRow,
        images);
  }
}
