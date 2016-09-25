package visitForm.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
/**
 *
 * @author Administrator
 */
public class User {
   
    String username;
    public User(){
        
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return username;
    }

    public boolean isValidUser(String username, String password, Connection conn) throws SQLException {

       
        PreparedStatement stmt;
        
      
//            Class.forName("com.mysql.jdbc.Driver");
//           conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/formschema","AppsScript","iamgoogle");

        stmt = conn.prepareStatement("SELECT password from allclinicians where username =?");
        stmt.setString(1,username);
        ResultSet rs = stmt.executeQuery();


        if (rs.isBeforeFirst()){
             while(rs.next()){
                String storedPass = rs.getString("password");
                if (storedPass.compareTo(password) == 0){
                    return true;
                }
            }
            return false;
        }
        return false;
    
    
    }
    
    
    public void registerUser(String username, String password, Connection conn) throws SQLException, SQLIntegrityConstraintViolationException {
       
        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO allclinicians (username, password) VALUES (?,?)");
        stmt.setString(1,username );
        stmt.setString(2,password );
        stmt.executeUpdate();
        return;

    }
    


    
}
