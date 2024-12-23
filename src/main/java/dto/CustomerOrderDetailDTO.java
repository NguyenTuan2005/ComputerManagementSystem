package dto;

import Model.Image;
import java.util.List;

public record CustomerOrderDetailDTO(CustomerOrderDTO customerOrderDTO, List<Image> images) {

  public int getOrderId() {
    return customerOrderDTO.getOrderId();
  }
}
