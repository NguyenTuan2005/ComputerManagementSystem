package Model;


import lombok.*;

import javax.swing.*;
import java.util.ArrayList;

import static view.OtherComponent.ImageInJTable.resizeImageIcon;

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

    private String avataImg;

    private int  numberOfPurchased;

    private int block;

    public Customer(String fullName, String email, String address, String password, String avataImg, int numberOfPurchased) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.avataImg = avataImg;
        this.numberOfPurchased = numberOfPurchased;
    }

    public Customer(String fullName, String email, String address, String password) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public Customer(int id, String fullName, String email, String address, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public Customer(String fullName, String email, String address, String password, String avataImg) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.avataImg = avataImg;
    }

    public Customer(String fullName, String email, String address, String password, String avataImg, int numberOfPurchased, int block) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.avataImg = avataImg;
        this.numberOfPurchased = numberOfPurchased;
        this.block = block;
    }

    public boolean sameEmail(String email) {
        return  this.email.equals(email);
    }



    public Object [] convertToObjects(int serial ){
        ImageIcon icon = resizeImageIcon(this.avataImg, 200, 200); // Kích thước 50x50
        return new Object[]{
                serial
                ,String.valueOf(this.id)
                ,this.fullName +(this.isBlock()?" *":" ")
                ,this.email
                ,this.address
                ,"***************"
                ,icon
        };
    }

    public static Object [][] getDataOnTable(ArrayList<Customer> customers){
//        String [][] datas = new String[customers.size()][];
        Object [][] datass = new Object[customers.size()][];
        for (int i = 0; i < customers.size(); i++) {
            datass[i] = customers.get(i).convertToObjects(i+1);
        }
        return datass;
    }

    public boolean isBlock(){
        return this.block == 1;
    }

    public boolean authenticateOldPassword(String password) {
        return this.password.equals(password);
    }
}
