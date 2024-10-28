package controller;

import Config.CurrentUser;
import Model.Account;
import dao.AccountDAO;
import dao.ManagerDAO;
import dto.ManagerInfor;
import security.PasswordSecurity;

import java.util.ArrayList;

public class AccountController {

    private ManagerDAO managerDAO;

    private AccountDAO accountDAO;

    private PasswordSecurity passwordSecurity;

    public AccountController() {
        this.accountDAO = new AccountDAO();
        this.managerDAO = new ManagerDAO();
    }

    public boolean isValidAccount(String username , String password ){
        Account account = accountDAO.findOneByName(username);
        ManagerInfor managerInfor = managerDAO.getManagerWithAccountById(username);
        if ( managerInfor == null )
            return false;
        passwordSecurity = new PasswordSecurity();
        passwordSecurity.setPlainPassword(password);
        System.out.println(account);
        boolean isValdAccount=passwordSecurity.isVariablePassword(managerInfor.getPassword());
        if(isValdAccount) {
            CurrentUser.USER_NAME = managerInfor.getFullName();
            CurrentUser.URL = managerInfor.getAvataImg();
        }
        return  passwordSecurity.isVariablePassword(account.getPassword());
    }

    public Account create(Account theAcount){
        passwordSecurity = new PasswordSecurity(theAcount.getPassword());
        theAcount.setPassword(passwordSecurity.generatePassword());
        return  accountDAO.save(theAcount);
    }

}
