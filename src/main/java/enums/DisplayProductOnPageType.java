package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DisplayProductOnPageType {
  ALL("Get full product"),
  LAPTOP_GAMING("Gaming"),
  LAPTOP_OFFICE("Laptop"),
  PC_CASE("pc"),
  LUXURY(""),
  CHEAP("");

  private final String type;

  public boolean isAll() {
    return this == ALL;
  }

  public boolean isLaptopGaming() {
    return this == LAPTOP_GAMING;
  }

  public boolean isLaptopOffice() {
    return this == LAPTOP_OFFICE;
  }

  public boolean isPcCase() {
    return this == PC_CASE;
  }

  public boolean isLuxury() {
    return this == LUXURY;
  }

  public boolean isCheap() {
    return this == CHEAP;
  }
}
