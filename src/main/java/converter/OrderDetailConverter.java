package converter;

import config.ProductOrderConfig;
import entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailConverter {

    private Set<ProductOrderConfig> productOrderConfigs;

    public List<OrderDetail> toOrderDetails(){
        return this.productOrderConfigs.stream()
                .map(productOrderConfig -> OrderDetail.builder().product(productOrderConfig.getProduct())
                        .quantity(productOrderConfig.getQuantity()).build() ).collect(Collectors.toList());
    }
}
