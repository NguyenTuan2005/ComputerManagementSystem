package Model;

import lombok.*;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class OderDetail {

    private int orderId;

    private int productId;

    private int pricePrice;

    private int quantity;

}
