package Model;

import dto.CustomerOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    private String status;

    public Order(int managerId, int customerId, String shipAddress, Date orderDate, String status) {
        this.managerId = managerId;
        this.customerId = customerId;
        ShipAddress = shipAddress;
        this.orderDate = orderDate;
        this.status = status;
    }

    public static String[][] getData(TreeMap<Integer, List<CustomerOrderDTO>> orders) {
        int i = 0;
        String[][] data = new String[orders.size()][];
        for (Map.Entry<Integer, List<CustomerOrderDTO>> entry : orders.entrySet()) {
            CustomerOrderDTO[] orderWrapper = {null};
            List<CustomerOrderDTO> list = entry.getValue();
            list.forEach(orderDTO1 -> {
                if (orderWrapper[0] == null) {
                    orderWrapper[0] = orderDTO1;
                } else {
                    orderWrapper[0].update(orderDTO1.getUnitPrice(), orderDTO1.getQuantity());
                }
            });
            CustomerOrderDTO orderDTO = orderWrapper[0];
            data[i++] = orderDTO.toOrderArray();
        }
        return data;
    }
}
