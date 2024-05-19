package tn.esprit.services;

import java.sql.SQLException;
import java.util.Set;

public interface Iservice<T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;
    public Set<T> getAll() throws SQLException;
}
