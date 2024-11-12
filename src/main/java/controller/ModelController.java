package controller;

import Model.Product;

import java.util.ArrayList;

public interface ModelController<M> {
    ArrayList<M> find(String name);

    ArrayList<M> getAll();

    ArrayList<M> reloadData();

    void setDeleteRow(int id , boolean status);

    void saves(ArrayList<M> products);
    void save(Product product);

    ArrayList<M> getByColumn(String column);
}
