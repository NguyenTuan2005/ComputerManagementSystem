package Config;

import Model.Product;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOrderConfig {
    private Product product;
    private int quatity;

    public ProductOrderConfig(Product product) {
        this.product = product;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrderConfig that = (ProductOrderConfig) o;
        System.out.println( " chay xem cop dungs ko"+this.product.equals(that.product));
        return this.product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quatity);
    }
}
