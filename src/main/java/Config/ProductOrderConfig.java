package Config;

import Model.Product;
import lombok.*;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

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
            boolean isDQuaity = p.getQuatity() != 1 && this.quatity !=1;
            boolean isSameName =  p.getProduct().getName().equals(product.getName());
            if ( isDQuaity && isSameName ){
                return true;
            }
        }
        return false;
    }

    public static Set<ProductOrderConfig> getUnqueProductOrder(Set<ProductOrderConfig> productOrderConfigs){
        ArrayList<ProductOrderConfig> pocs = new ArrayList<>();
        pocs.addAll(productOrderConfigs);
        for (int i = 0; i < pocs.size()-1; i++) {
            var tamp = pocs.get(i);
            for (int j = 0; j < pocs.size(); j++) {
                var find = pocs.get(j);
                boolean leftQuatity =tamp.getQuatity() == 1 && find.getQuatity() != 1;
                boolean rigthQuatity =tamp.getQuatity() != 1 && find.getQuatity() == 1;
                boolean isSameName =tamp.getProduct().getName() .equals(find.getProduct().getName());
                if (isSameName){
                    if (leftQuatity){
                        productOrderConfigs.remove(tamp);
                    } else if(rigthQuatity){
                        productOrderConfigs.remove(find);
                    }
                }
            }
        }
        return productOrderConfigs;
    }

    public static void main(String[] args) {
        Product product1 = new Product(1,"mmm");
        Product product2 = new Product(1,"mm");
        ProductOrderConfig productOrderConfig1 = new ProductOrderConfig(product1, 1);
        ProductOrderConfig productOrderConfig2 = new ProductOrderConfig(product1, 1);

        ProductOrderConfig productOrderConfig3 = new ProductOrderConfig(new Product(1,"mmm"),1);
        Set<ProductOrderConfig> productOrderConfigSet = new HashSet<>();
        productOrderConfigSet.add(productOrderConfig1);
        productOrderConfigSet.add(productOrderConfig2);
        productOrderConfigSet.add(productOrderConfig3);
        System.out.println(">>>>>>Set : ");
        for( var x: productOrderConfigSet) {
            System.out.println(x);
            System.out.println();
        }
        System.out.println();

//        System.out.println( ">>>>> before set > "+productOrderConfigSet.stream().filter(p->p.hasProductName(productOrderConfigSet)).collect(Collectors.toSet()));
        System.out.println(">>>>");
//        for( var x:  getItem(productOrderConfigSet)) {
//            System.out.println(x);
//            System.out.println();
//        }
    }
}
