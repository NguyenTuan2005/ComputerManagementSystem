package Model;

import dto.CustomerOrderDTO;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
      List<CustomerOrderDTO> list = entry.getValue();
      double totalCost = list.stream().mapToDouble(CustomerOrderDTO::totalCost).sum();
      int quantity = list.stream().mapToInt(CustomerOrderDTO::getQuantity).sum();
      CustomerOrderDTO orderDTO = list.get(0);
      orderDTO.update(totalCost, quantity);
      data[i++] = orderDTO.toOrderArray();
    }
    return data;
  }
}
