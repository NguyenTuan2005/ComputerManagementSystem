package model;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    Product product;
    int quantity;

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof model.OrderDetail))
            return false;
        else {
            model.OrderDetail that = (model.OrderDetail) obj;
            return this.quantity == that.quantity &&
                    this.product.equals(that.product);
        }
    }

    public String getProductName() {
        return this.product.getName();
    }
    public int getProductId() {
        return this.product.getId();
    }public String getProductBrand() {
        return this.product.getBrand();
    }
    public String getProductCpu() {
        return this.product.getName();
    }public String getProductRam() {
        return this.product.getName();
    }public String getProductMemory() {
        return this.product.getMemory();
    }
    public double getProductUnitPrice() {
        return this.product.getPrice();
    }

    public boolean contains(String searchText) {
        return this.product.contains(searchText)
                || searchText.contains(String.valueOf(this.quantity));
    }

    public double totalCost() {
        return this.quantity * this.product.getPrice();
    }

    public void updateSupplier(Supplier supplier) {
        if(product.sameEmailSupplier(supplier))
            this.product.changeSupplier(supplier);
    }
}
