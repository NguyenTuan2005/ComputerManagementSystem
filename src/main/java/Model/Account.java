package Model;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {


    private int id;

    private String username;

    private String password;

    private String email;

    private Date createDate;

    private int managerId;

    public Account(String username, String email, String password, Date createDate, int managerId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.managerId = managerId;
    }

}
