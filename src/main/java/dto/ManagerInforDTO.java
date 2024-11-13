package dto;

import lombok.*;

import java.util.Date;

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


}
