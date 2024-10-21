package Model;

import lombok.*;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    private int id;

    private String fullName;

    private String address;

    private Date birthDay;

    private String phoneNumber;

    public Manager(String fullName, String address, Date birthDay, String phoneNumber) {
        this.fullName = fullName;
        this.address = address;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
    }
}
