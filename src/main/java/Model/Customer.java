package Model;


import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int id;

    private String fullName;

    private String email;

    private String address;

    private String password;

    public Customer(String fullName, String email, String address, String password) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public boolean samePasswordAndEmail(String email, String password) {
        return  this.email.equals(email) && this.password.equals(password);
    }
}
