package Model;

import lombok.*;

import java.util.ArrayList;
import java.sql.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    private int id;

    private String companyName;

    private String email;

    private String phoneNumber;

    private String  address;

    private Date contractDate;

    private int deleteRow;

    public Supplier(String companyName, String email, String phoneNumber, String address, Date contractDate,int deleteRow) {
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.contractDate = contractDate;
        this.deleteRow= deleteRow;
    }

    public static java.sql.Date checkDate(String dateStr) {
        try {
            return java.sql.Date.valueOf(dateStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong date format. Please enter the date in YYYY-MM-DD format.");
        }
    }

    public static String checkPhoneNum(String phoneNum) {
        //Check if phone number is at least 10 characters
        if (phoneNum.length() != 10)
            throw new IllegalArgumentException("Phone number must be 10 digits");

        //Check if all characters are digits
        if (!phoneNum.chars().allMatch(Character::isDigit))
            throw new IllegalArgumentException("Phone number must contain only digits");
        return phoneNum;
    }

    // Convert data to String
    public static String [][] getData(ArrayList<Supplier> suppliers){
        String [][] data = new String[suppliers.size()][];
        for (int i = 0; i < suppliers.size() ; i++) {
            data[i]= suppliers.get(i).convertToArray(i + 1);
        }
        return data;
    }

    private String[] convertToArray(int serial) {
        String[] result = {String.valueOf(serial),
                            String.valueOf(id),
                            companyName,
                            email,
                            phoneNumber,
                            address,
                            String.valueOf(contractDate)
        };
        return result;
    }
}
