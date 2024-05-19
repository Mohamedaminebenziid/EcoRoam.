package tn.esprit.interfaces;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IIService<T>{
    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    List<T> getAll() throws SQLException;

    boolean delete(int id) throws SQLException;
}
