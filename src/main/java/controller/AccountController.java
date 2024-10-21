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
        ArrayList<Account> accounts = accountDAO.findByName(username);
        boolean notFound = accounts.isEmpty();
        if ( notFound )
            return false;
        System.out.println(accounts.get(0));
        return accounts.get(0).sameUsernameAndPassword(username,password) ;
    }


}
