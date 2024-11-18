package controller;

import Config.CurrentUser;
import Model.Account;
import dao.AccountDAO;
import dao.ManagerDAO;
import dto.ManagerInforDTO;
import security.PasswordSecurity;
import Enum.*;

import java.util.ArrayList;

public class AccountController implements ModelController<Account> {

    private ManagerDAO managerDAO;

    private AccountDAO accountDAO;

    private PasswordSecurity passwordSecurity;

    public static LoginStatus  loginStatus = LoginStatus.NOT_FOUND;

    public AccountController() {
        this.accountDAO = new AccountDAO();
        this.managerDAO = new ManagerDAO();
    }

    public boolean isValidAccount(String username , String password ){
        loginStatus = LoginStatus.NOT_FOUND;
        Account account = accountDAO.findOneByName(username);
        ManagerInforDTO managerInfor = managerDAO.getManagerWithAccountById(username);
        System.out.println(managerInfor);
        if ( managerInfor == null )
            return false;
        if( managerInfor.isBlocked()){
            loginStatus = LoginStatus.BLOCKED;
            return false;
        }
        if (managerInfor.sameUsername(username)) {
            passwordSecurity = new PasswordSecurity();
            passwordSecurity.setPlainPassword(password);
            System.out.println(account);
            boolean isValdAccount=passwordSecurity.isVariablePassword(managerInfor.getPassword());
            if(isValdAccount) {
                CurrentUser.USER_NAME = managerInfor.getFullName();
                CurrentUser.URL = managerInfor.getAvataImg();
                return  true;
            }else {
                loginStatus = LoginStatus.WORNG_PASSWORD;
//                System.out.println("sai pw");
                return false;
            }

        } else {
            loginStatus = LoginStatus.WRONG_USER_NAME;
            return false;
        }

    }
    public Account create(Account theAcount){
        passwordSecurity = new PasswordSecurity(theAcount.getPassword());
        theAcount.setPassword(passwordSecurity.generatePassword());
        return  accountDAO.save(theAcount);
    }

    public void updateBlock(boolean isBlock , int id){
      accountDAO.updateBlock(isBlock, id);
    }

    public static void main(String[] args) {
        AccountController acc = new AccountController();
        acc.updateBlock(true,7);
    }

    @Override
    public ArrayList<Account> find(String name) {
        return null;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Override
    public ArrayList<Account> getAll() {
        return null;
    }

    @Override
    public ArrayList<Account> reloadData() {
        return null;
    }

    @Override
    public void setDeleteRow(int id, boolean status) {

    }

    @Override
    public void saves(ArrayList<Account> m) {

    }

    @Override
    public void save(Account account) {
        passwordSecurity = new PasswordSecurity(account.getPassword());
        account.setPassword(passwordSecurity.generatePassword());
        accountDAO.save(account);
    }

    @Override
    public void update(Account account) {
        accountDAO.updateById(account);
    }

    @Override
    public ArrayList<Account> sortByColumn(String column) {
        return null;
    }

    public Account findByName(String name ){
        return accountDAO.findOneByName(name);
    }
}
