package config;


import entity.Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class CurrentUser {
    public static String USER_NAME;
    public static String URL;
    public static entity.User  CURRENT_USER_V2;
    public static Manager CURRENT_MANAGER_V2= Manager.builder()
            .id(2)
            .fullName("Tuan Nguyen")
            .email("23130370@st.hcmuaf.edu.vn")
            .address("Tien Giang Chau Thanh Diem Hy, 125/p3/2")
            .password("$2a$10$jXau3IWFWbArMhmH6GhAY.HUrFLNB3McHuPQmhuZzW3YRJpDTvBhG") // pass 123
            .createdAt(LocalDate.of(1980, 3, 25))
            .dob(LocalDate.of(1950, 3, 25))
            .phone("987654321")
            .avatarImg("src/main/java/img/tuan_avata.jpg")
            .isActive(true)
            .orders(new ArrayList<>())
            .build();

}

