package Config;

import Model.Product;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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



    public boolean  hasProductName(Set<ProductOrderConfig> productOrderConfigs){

        for( var p : productOrderConfigs){
            boolean isDQuaity = p.getQuatity() == 1 && this.quatity !=1;
            boolean isSameName =  p.getProduct().getName().equals(product.getName());
            if ( isDQuaity && isSameName ){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Product product1 = new Product(1,"mmm");
        Product product2 = new Product(1,"mmm");
        ProductOrderConfig productOrderConfig1 = new ProductOrderConfig(product1, 1);
        ProductOrderConfig productOrderConfig2 = new ProductOrderConfig(product2, 1);
        Set<ProductOrderConfig> productOrderConfigSet = new HashSet<>();
        productOrderConfigSet.add(productOrderConfig1);
        productOrderConfigSet.add(productOrderConfig2);
        System.out.println(productOrderConfigSet);
    }
}
