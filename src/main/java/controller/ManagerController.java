package controller;

import Model.Manager;
import dao.AccountDAO;
import dao.ManagerDAO;
import dto.ManagerInforDTO;

import java.util.ArrayList;

public class ManagerController implements ModelController<Manager>{

    private ManagerDAO managerDAO;

    private AccountDAO accountDAO;

    public ManagerController(){
        this.managerDAO = new ManagerDAO();
        this.accountDAO = new AccountDAO();
    }

    @Override
    public ArrayList<Manager> find(String name) {
        return managerDAO.findByName(name);
    }

    @Override
    public Manager findById(int id) {
        return managerDAO.findById(id);
    }

    @Override
    public ArrayList<Manager> getAll() {
        return managerDAO.getAll();
    }

    @Override
    public ArrayList<Manager> reloadData() {
        return null;
    }

    @Override
    public void setDeleteRow(int id, boolean status) {

    }

    @Override
    public void saves(ArrayList<Manager> m) {

    }

    @Override
    public void save(Manager manager) {

        managerDAO.save(manager);
    }

    @Override
    public void update(Manager manager) {
        managerDAO.update(manager);
    }

    @Override
    public ArrayList<Manager> sortByColumn(String column) {
        return null;
    }

    public ArrayList<ManagerInforDTO> getManagerInforDTO(){
        return managerDAO.getManagerInforDTO();
    }



}
