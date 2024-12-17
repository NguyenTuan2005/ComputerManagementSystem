package Config;

import Model.Customer;
import dto.ManagerInforDTO;

import java.util.Date;

public class CurrentUser {
    public static String USER_NAME;
    public static String URL;
    public static Customer CURRENT_CUSTOMER;
    public static ManagerInforDTO CURRENT_MANAGER = new ManagerInforDTO(2, "James Nguyen",
            "nlu", new Date(9, 9, 2005), "0398167244", 7, "james",
            "***", "duynguyenavg@gmail.com", new Date(18, 11, 2024), "imge", 0);
}

