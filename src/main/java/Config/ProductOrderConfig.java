package Config;

import Model.Product;
import java.util.*;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductOrderConfig that = (ProductOrderConfig) o;
    System.out.println(" chay xem cop dungs ko" + this.product.equals(that.product));
    return this.product.equals(that.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, quatity);
  }

  public static Set<ProductOrderConfig> getUnqueProductOrder(
      Set<ProductOrderConfig> productOrderConfigs) {
    ArrayList<ProductOrderConfig> pocs = new ArrayList<>();
    pocs.addAll(productOrderConfigs);
    for (int i = 0; i < pocs.size() - 1; i++) {
      var tamp = pocs.get(i);
      for (int j = 0; j < pocs.size(); j++) {
        var find = pocs.get(j);
        boolean leftQuatity = tamp.getQuatity() == 1 && find.getQuatity() != 1;
        boolean rigthQuatity = tamp.getQuatity() != 1 && find.getQuatity() == 1;
        boolean isSameName = tamp.getProduct().getName().equals(find.getProduct().getName());
        if (isSameName) {
          if (leftQuatity) {
            productOrderConfigs.remove(tamp);
          } else if (rigthQuatity) {
            productOrderConfigs.remove(find);
          }
        }
      }
    }
    return productOrderConfigs;
  }
}
