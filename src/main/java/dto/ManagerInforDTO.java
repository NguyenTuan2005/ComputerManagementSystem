package dto;


import jakarta.validation.constraints.*;
import lombok.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

import static view.OtherComponent.ImageInJTable.resizeImageIcon;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerInforDTO {

    private int  managerId;

    private String fullName;

    private String address;

    private Date birthDay;

    private String phoneNumber;

    private int accountId;

    private String username;

    private String password;

    private String email;

    private Date createDate;

    private String avataImg;

    public Object[]  convertToObj(int i){
        ImageIcon icon = resizeImageIcon(this.avataImg, 150, 150); // Kích thước 50x50
        return new Object[]{
                i,
            this.managerId,
            this.fullName,
            this.address,
            this.birthDay,
            this.phoneNumber,
            this.accountId,
            this.username,
            this.password,
            this.email,
            this.createDate,
            icon
        };
    }

    public static Object[][] getDataOnTable(ArrayList<ManagerInforDTO> managerInforDTOS){
        Object[][] results = new Object[managerInforDTOS.size()][];
        for (int i = 0; i < results.length; i++) {
            results[i] = managerInforDTOS.get(i).convertToObj(i+1);
        }
        return results;
    }



}
