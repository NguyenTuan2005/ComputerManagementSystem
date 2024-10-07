package Model;

import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String userName;
    private String password;
    private String role;
    private String email;
    private String address;
    public static final String MANAGER_ROLE = "Manager";
    public static final String User_ROLE = "User";

    public boolean sameName(String name){
        return this.userName.equals(name);
    }
    public boolean samePassword(String passwd){
        return this.password.equals(passwd);
    }
    public boolean sameRole(String role){
        return this.role.equals(role);
    }
}
