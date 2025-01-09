package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DisplayProductType  {
  ALL("Get full product"),
  LAPTOP_GAMING("Gaming"),
  LAPTOP_OFFICE("Laptop"),
  PC_CASE("pc"),
  LUXURY(""),
  CHEAP(""),
  PRICE_IN_AMOUNT_10M_20M("10M to 20M"),
  PRICE_IN_AMOUNT_20M_30M("10M to 20M")
  ;
  public static final int TEN_MILION =10000000;
  public static final int TWENTY_MILION =20000000;
  public static final int THIRTY_MILION =30000000;

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
