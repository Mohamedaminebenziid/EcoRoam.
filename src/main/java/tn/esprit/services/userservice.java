package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface userservice<T>{

    public void ajouteruser(T t) throws SQLException;

    public  void modifieruser (T t ) throws  SQLException ;

    public void supprimer(T t ) throws  SQLException ;
    public  List<T> afficheruser ()throws  SQLException ;
    public boolean isValidLogin(String email, String password);



}