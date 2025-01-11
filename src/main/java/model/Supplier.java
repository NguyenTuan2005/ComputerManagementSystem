package model;

import java.time.LocalDate;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supplier {

  int id;
  String companyName;
  String email;
  String phoneNumber;
  String address;
  LocalDate contractDate;
  boolean isActive;

  // Convert data to String
  public static String[][] getData(List<model.Supplier> suppliers) {
    String[][] data = new String[suppliers.size()][];
    for (int i = 0; i < suppliers.size(); i++) {
      data[i] = suppliers.get(i).convertToArray(i + 1);
    }
    return data;
  }

  private String[] convertToArray(int serial) {
    String[] result = {
      String.valueOf(serial), companyName, email, phoneNumber, address, String.valueOf(contractDate)
    };
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof model.Supplier)) return false;
    else {
      model.Supplier that = (model.Supplier) obj;
      return this.id == that.id
          && this.companyName.equals(that.companyName)
          && this.email.equals(that.email)
          && this.phoneNumber.equals(that.phoneNumber)
          && this.address.equals(that.address)
          && this.contractDate.isEqual(that.contractDate)
          && this.isActive == that.isActive;
    }
  }

  public boolean sameCompanyName(String name) {
    return this.companyName.toLowerCase().contains(name.toLowerCase());
  }

  public boolean exactlySameCompanyName(String name) {
    return this.companyName.toLowerCase().contains(name.toLowerCase());
  }

  public int compareName(Supplier other) {
    return this.companyName.compareTo(other.companyName);
  }

  public int compareEmail(Supplier other) {
    return this.email.compareTo(other.email);
  }

  public int comparePhone(Supplier other) {
    return this.phoneNumber.compareTo(other.phoneNumber);
  }

  public int compareAddress(Supplier other) {
    return this.address.compareTo(other.address);
  }

  public int compareDate(Supplier other) {
    return this.contractDate.compareTo(other.contractDate);
  }

  public boolean sameCompanyName(Supplier supplier) {
    return this.companyName.equals(supplier.companyName);
  }

  public boolean sameEmail(Supplier supplier) {
    return this.email.equals(supplier.email);
  }
}
