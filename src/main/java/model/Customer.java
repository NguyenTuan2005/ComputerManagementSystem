package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.swing.*;
import java.util.ArrayList;

import static view.otherComponent.ImageInJTable.resizeImageIcon;


@Getter
@SuperBuilder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends User{

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                ", avatarImg='" + avatarImg + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                '}';
    }

    public Object[] convertToObjects(int serial) {
        ImageIcon icon = resizeImageIcon(this.avatarImg, 200, 200); // Kích thước 50x50
        return new Object[] {
                serial,
                String.valueOf(this.id),
                this.fullName + (this.isBlock() ? " *" : " "),
                this.email,
                this.address,
                "***************",
                icon
        };
    }

    public static Object[][] getDataOnTable(ArrayList<Customer> customers) {
        Object[][] datass = new Object[customers.size()][];
        for (int i = 0; i < customers.size(); i++) {
            datass[i] = customers.get(i).convertToObjects(i + 1);
        }
        return datass;
    }

    @Override
    public void update(String[] data) {
        this.avatarImg = data[0];
        this.email = data[1];
        this.fullName = data[2];
        this.address = data[3];
    }
}
