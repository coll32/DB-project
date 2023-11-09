/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author coll3
 */
public class DatabaseConnection {
    public Connection databaseLink;
    
    public Connection getConnection(){
        String databaseName = "Library";
 //       String databaseUser = "Jose Mccoy";
//        String databasePassword = "202";
        String username = "jessie"; // Replace with your SQL Server username
        String password = "2311";
        String databaseURL = "jdbc:sqlserver://localhost:1433;databaseName=Library;encrypt=true;trustServerCertificate=true; ";
        
       try{
           databaseLink = DriverManager.getConnection(databaseURL, username, password);
       } catch(Exception e){
           e.printStackTrace();
           System.out.println("Didnt get DB");
       }
       
        return databaseLink;
    }
    

}
