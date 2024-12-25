package Config;

import dto.CustomerOrderDetailDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderHistoryConfig {
  private ArrayList<CustomerOrderDetailDTO> orderDetailDTOS;

  public Map<Integer, List<CustomerOrderDetailDTO>> get() {
    return this.orderDetailDTOS.stream()
        .collect(
            Collectors.groupingBy(
                CustomerOrderDetailDTO::getOrderId, TreeMap::new, Collectors.toList()))
        .descendingMap();
  }
}
