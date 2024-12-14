package Config;

import controller.CustomerController;
import dto.CustomerOrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderHistoryConfig {
    private ArrayList<CustomerOrderDetailDTO> orderDetailDTOS;


    public Map<Integer , List<CustomerOrderDetailDTO>> get(){
        return this.orderDetailDTOS.stream().collect(
                Collectors.groupingBy(CustomerOrderDetailDTO::getOrderId,
                        TreeMap::new,
                        Collectors.toList())).descendingMap();
    }

    public static void main(String[] args) throws SQLException {
        CustomerController customerController = new CustomerController();
        OrderHistoryConfig o = new OrderHistoryConfig(customerController.getCustomerOrderDetail(10));
        for ( Map.Entry<Integer, List<CustomerOrderDetailDTO>> d : o.get().entrySet()){
            System.out.println(d.getKey()+"      "+d.getValue());
            System.out.println();
            System.out.println();
        }

    }

}
