package Config;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConfig {
    public static Date convertStringToDate(String inputDate) {
        // Define the input date format
        // Define the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        // Define the output date format
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            // Parse the input string into a Date object
            Date parsedDate = inputFormat.parse(inputDate);
            // Format the Date object into the desired string format
            String formattedDate = outputFormat.format(parsedDate);
            // Convert the formatted string back to a Date object

            Date date = new Date(parsedDate.getMonth(),parsedDate.getDay(),parsedDate.getYear());

            return date;
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null; // Return null if parsing fails
        }
    }

    public static void main(String[] args) {
        Date date = new Date(9,9,2005);
        System.out.println(date);
    }
}
