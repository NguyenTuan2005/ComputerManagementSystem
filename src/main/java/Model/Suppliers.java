package Model;

import lombok.*;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Suppliers {

    private int id;

    private String companyName;

    private String email;

    private String phoneNumber;

    private String  address;

    private Date contactDate;

    public Suppliers(String companyName, String email, String phoneNumber, String address, Date contactDate) {
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.contactDate = contactDate;
    }
}
