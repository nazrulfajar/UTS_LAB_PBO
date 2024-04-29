/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utslab.contactmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author VIVOBOOK PRO
 */
public class DBConnector {
    static Connection connection;
    
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/labpbouts";
    static final String user = "root";
    static final String password = "";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, user, password);
    }
    
    public static void initDBConnection(){
        try {
            Class.forName(JDBC_DRIVER);
            
            connection = DriverManager.getConnection(DB_URL, user, password);
            
            if (connection != null ){
            System.out.println("Connection is estalibished");
            }
            
        } 
        
        catch (Exception ex) 
        {
            System.out.println(ex);
            if (connection == null) {
                System.out.println("Connection Gagal");
            }
        }
    }
}
