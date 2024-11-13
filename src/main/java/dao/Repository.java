package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Repository<T> {

    T save(T t);

    T findById(int id);

    ArrayList<T> getAll() throws SQLException;

    ArrayList<T> findByName(String name);

    T findOneByName(String name);

    T update(T t);

    boolean remove(int id);

    ArrayList<T> getByColumn(String column);
}
