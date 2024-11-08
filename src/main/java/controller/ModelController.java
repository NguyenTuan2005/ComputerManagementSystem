package controller;

import Model.Product;

import java.util.ArrayList;

public interface ModelController<M> {
    ArrayList<M> find(String name);

    M findById(int id);

    ArrayList<M> getAll();

    ArrayList<M> reloadData();

    void setDeleteRow(int id , boolean status);

    void saves(ArrayList<M> m);

    void save(M m);
    void update(M m);

}
