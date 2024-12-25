package dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyOrderDTO {
  private int customerId;
  private int orderId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KeyOrderDTO that = (KeyOrderDTO) o;
    return customerId == that.customerId && orderId == that.orderId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, orderId);
  }
}
