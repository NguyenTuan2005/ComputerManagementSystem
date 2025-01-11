package converter;

import java.time.LocalDate;

public class LocalDateConverter {
  public static LocalDate toLocalDate(String date) {
    return LocalDate.parse(date);
  }
}
