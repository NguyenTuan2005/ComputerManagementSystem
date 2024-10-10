package Model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Getter
public class UserManager {
    private ArrayList<User> users;


    public UserManager(){
        User admin = new User(0,"admin","0000",User.MANAGER_ROLE,"hduy09092005@gmail.com", "admin" );
        User user1 = new User(1, "Huy_Hoang", "ohyeah", User.MANAGER_ROLE, "huyhoang@gmail.com", "123 Main St");
        User user2 = new User(2, "huu_duy", "duyvippro", User.MANAGER_ROLE, "jane.smith@example.com", "456 Oak St");
        User user3 = new User(3, "tuan_nguyen", "pro1234", User.MANAGER_ROLE, "mike.jones@example.com", "789 Pine St");
        User user4 = new User(5, "dinh_hieu", "abc123", User.User_ROLE, "hieu.harris@example.com", "654 Maple St");
        User user5 = new User(4, "sara_lee", "password789", User.User_ROLE, "sara.lee@example.com", "321 Cedar St");
        User user6 = new User(6, "emily_clark", "pass1234", User.User_ROLE, "emily.clark@example.com", "987 Birch St");
        User user7 = new User(7, "david_wilson", "mypassword1", User.User_ROLE, "david.wilson@example.com", "159 Elm St");
        User user8 = new User(8, "linda_brown", "linda123", User.User_ROLE, "linda.brown@example.com", "753 Ash St");
        User user9 = new User(9, "kevin_white", "white456", User.User_ROLE, "kevin.white@example.com", "951 Spruce St");
        User user10 = new User(10, "a", "a", User.MANAGER_ROLE, "nancy.green@example.com", "852 Willow St");
        this.users = new ArrayList<>();

        users.add(admin);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);


    }

    public ArrayList<User> findUserByName(String name ){
        ArrayList<User> userFound = (ArrayList<User>) users.stream()
                .filter(user -> user.getUserName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return userFound;
    }

    // using binary search
    public User  findIdUser(int idUser){
        int l = 0;
        int r = this.users.size();
        while ( l < r){
            int m  = (r +l)/2;
            if (users.get(m).getId() == idUser){
                return users.get(m);
            } else if( users.get(m).getId() < idUser ){
                l = m+1;
            } else {
                r = m+1;
            }

        }
        return null;
    }
    //method: check user login information
    public boolean isUserExists(String userRole,String username, String password) {
        for (User user : users) {
            if (user.sameRole(userRole) && user.sameName(username) && user.samePassword(password)) {
                return true;
            }
        }
        return false;
    }

}
