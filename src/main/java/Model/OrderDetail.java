package Model;

import lombok.*;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private int orderId;

    private int productId;

    private int unitPrice;

    private int quantity;

}
