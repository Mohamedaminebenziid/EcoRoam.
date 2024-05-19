package tn.esprit.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class MyConnection {

    //DB PARAM
    static final String URL ="jdbc:mysql://localhost:3306/ecoroamm";
    static final String USER ="root";
    static final String PASSWORD ="";

    //var
    private Connection cnx;
    //1
    static MyConnection instance;

    //const
    //2
    private MyConnection(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("  "+ "Connected...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Not Connected...");
        }
    }


    public Connection getCnx() {
        return cnx;
    }

    //3
    public static MyConnection getInstance() {
        if(instance == null)
            instance = new MyConnection();

        return instance;
    }



}
