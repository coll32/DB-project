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
        String databaseName = "BooksDB";
 //       String databaseUser = "Jose Mccoy";
//        String databasePassword = "202";
        String url = "jdbc:sqlite:src/main/resources/com/mycompany/databaseexample/BooksDB.db";
        
       try{
           databaseLink = DriverManager.getConnection(url);
       } catch(Exception e){
           e.printStackTrace();
       }
       
           return databaseLink;
    }
    

}
