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

    private String avataImg;

    public Account(String username, String email, String password, Date createDate, int managerId, String avataImg) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.managerId = managerId;
        this.avataImg= avataImg;
    }

    public Account(int managerId, Date createDate, String email, String password, String username) {
        this.managerId = managerId;
        this.createDate = createDate;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Account(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public boolean sameUsernameAndPassword( String username , String password ){
        return this.username.equals(username) && this.password.equals(password);
    }
}
