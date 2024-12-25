package Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateConfig {

  private static LocalDate convertToLocalDate(Date date) {
    return new Date(date.getYear(), date.getMonth(), date.getDate())
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }

  public static boolean cancelOrderLimit(Date orderDate, long limit) {
    var orderDateLocalDate = convertToLocalDate(orderDate);
    var now = LocalDate.now();
    return ChronoUnit.DAYS.between(orderDateLocalDate, now) <= limit;
  }

  public static Date convertStringToDate(String inputDate) {
    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
    try {

      Date parsedDate = inputFormat.parse(inputDate);
      String formattedDate = outputFormat.format(parsedDate);
      Date date = new Date(parsedDate.getMonth(), parsedDate.getDay(), parsedDate.getYear());

      return date;
    } catch (ParseException e) {
      System.err.println("Error parsing date: " + e.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {
    Date date = new Date(2024, 12, 15);
    System.out.println(cancelOrderLimit(date, 2));
  }
}
