package controller;

import java.util.ArrayList;

public interface ModelController<M> {
    ArrayList<M> find(String name);

    ArrayList<M> getAll();

    ArrayList<M> reloadData();

    void setDeleteRow(int id , boolean status);
}
