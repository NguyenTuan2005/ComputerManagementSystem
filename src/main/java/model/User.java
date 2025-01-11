package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import security.PasswordSecurity;

import java.time.LocalDate;
import java.util.Map;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class User implements Comparable<User>{
    int id;

    String fullName;

    String email;

    String address;

    String phone;

    LocalDate dob;

    String avatarImg;

    String password;

    LocalDate createdAt;

    boolean isActive;

    @Getter(AccessLevel.NONE)
    PasswordSecurity security;

    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof User))
            return false;
        else {
            User that = (User) obj;
            return this.id == that.id &&
                    this.fullName.equals(that.fullName) &&
                    this.email.equals(that.email) &&
                    this.address.equals(that.address) &&
                    this.phone.equals(that.phone) &&
                    this.dob.isEqual(that.dob) &&
                    this.avatarImg.equals(that.avatarImg) &&
                    this.password.equals(that.password) &&
                    this.createdAt.isEqual(that.createdAt) &&
                    this.isActive == that.isActive;
        }
    }

    public Map<Product, Integer> productSoldStatistic() {
        return null;
    };

    public boolean isValidPassword(String pass) {
        this.security = new PasswordSecurity();
        this.security.setPlainPassword(this.password);
        return  this.security.isValidPassword(this.password,pass);

    }

    public boolean sameEmail(String email){
        return this.email.equals(email);
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changeActive(boolean isBlock){
        this.isActive = isBlock ;
    }

    public void changeAddress(String address){
        this.address = address;
    }

    public void changeFullName(String fullName) {
        this.fullName = fullName;
    };

    public boolean isManager() {
        return false;
    }

    public boolean isBlock() {
        return this.isActive == false;
    }

    public boolean contains(String searchText) {
        return this.email.toLowerCase().contains(searchText) ||
                String.valueOf(this.id).contains(searchText) ||
                this.fullName.toLowerCase().contains(searchText) ||
                this.address.trim().toLowerCase().contains(searchText) ||
                this.phone.trim().contains(searchText) ||
                String.valueOf(this.dob).contains(searchText);
    }

    public void changeAvatarImg(String url){ this.avatarImg = url;}

    @Override
    public int compareTo(User o) {
        int num = this.id - o.id;
        if (num == 0) {
            num = this.fullName.compareTo(o.fullName);
            if (num == 0) {
                num = this.email.compareTo(o.email);
                if (num == 0) return this.address.compareTo(o.address);
            }
        }
        return num;
    }

    public boolean sameID(int id) {
        return this.id == id;
    }


    public boolean isId(int id) {
        return this.id == id;
    }

    public void update(String[] data) {
        this.avatarImg = data[0];
        this.email = data[1];
        this.createdAt = LocalDate.parse(data[2]);
        this.fullName = data[3];
        this.address = data[4];
        this.dob = LocalDate.parse(data[5]);
        this.phone = data[6];
    }

    public boolean authenticateOldPassword(String oldPassword) {
        return this.password.equals(oldPassword);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}


