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
    private int id;

    private int managerId;

    private int customerId;

    private String ShipAddress;

    private Date orderDate;

    private Date shipDate;

    private String status;

    public Order(int managerId, int customerId, String shipAddress, Date orderDate, Date shipDate, String status) {
        this.managerId = managerId;
        this.customerId = customerId;
        ShipAddress = shipAddress;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.status = status;
    }
}
