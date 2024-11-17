package Model;

import lombok.*;

import java.sql.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {


    private int id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @NonNull
    private Date createDate;

    @NonNull
    private int managerId;

    @NonNull
    private String avataImg;


    public Account(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }



    public Account(@NonNull String avataImg, @NonNull Date createDate, @NonNull String email, @NonNull String password, @NonNull String username) {
        this.avataImg = avataImg;
        this.createDate = createDate;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public boolean sameUsernameAndPassword(String username , String password ){
        return this.username.equals(username) && this.password.equals(password);
    }

    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);


    }

    public boolean sameUsername(String username){
        return this.username.equals(username);
    }

    public static Date getCurrentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return  sqlDate; // Returns the current date and time
    }

}
