package controller;

import Model.Account;
import dao.AccountDAO;

import java.util.ArrayList;

public class AccountController {

    private AccountDAO accountDAO;

    public AccountController() {
        this.accountDAO = new AccountDAO();
    }

    public boolean isValidAccount(String username , String password ){
        Account account = accountDAO.findOneByName(username);

        if ( account == null )
            return false;
        System.out.println(account);
        return account.sameUsernameAndPassword(username,password) ;
    }


}
