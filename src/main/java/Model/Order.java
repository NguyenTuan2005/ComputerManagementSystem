package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int idOrder;
    private int idCustomer;
    private int quantity;
    private long totalPrice;
    private String address;
    private Date orderDate;
    private Date shipDate;


}
