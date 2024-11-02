package Model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

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

    public Supplier(String companyName, String email, String phoneNumber, String address, Date contractDate) {
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.contractDate = contractDate;
    }

    // Convert data to String
    public static String [][] getData(ArrayList<Supplier> suppliers){
        String [][] datas = new String[suppliers.size()][];
        for (int i = 0; i < suppliers.size() ; i++) {
            datas[i]= suppliers.get(i).convertToArray(i + 1);
        }
        return datas;
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
