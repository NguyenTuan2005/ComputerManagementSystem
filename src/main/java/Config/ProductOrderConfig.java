package Config;

import Model.Product;
import lombok.*;

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
}
