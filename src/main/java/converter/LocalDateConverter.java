package converter;

import java.time.LocalDate;
import java.util.Date;

public class LocalDateConverter {
    public static LocalDate toLocalDate(String date){
        return LocalDate.parse(date);
    }
}
