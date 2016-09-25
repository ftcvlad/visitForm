/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.models;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Vlad
 */
public class PatientsTable {
    

    public ArrayList<Patient> findPatients(String activeUserEmail, String name, Connection conn) throws SQLException{
               
        PreparedStatement stmt;
       
        
        String nameCondition="";
        if (!name.equals("")){
            nameCondition = "AND name=?";
        }

        stmt = conn.prepareStatement("SELECT name, surname, gender, birth, firstVisit, comment, nOfVisits, id"+
                                         " FROM formpatients"+
                                         " WHERE clinician=? "+nameCondition+" ORDER BY name ASC;");
        stmt.setString(1, activeUserEmail);                  
        if (!name.equals("")){
            stmt.setString(2, name);
        }

        ResultSet rs = stmt.executeQuery();
        
        ArrayList<Patient> allPatients = new ArrayList<Patient>();
        while (rs.next()){

            Patient p = new Patient();

            p.setName(rs.getString(1));
            p.setSurname(rs.getString(2));
            p.setGender(rs.getString(3));
            p.setBirthDate(rs.getString(4));
            p.setFirstVisit(rs.getString(5));
            p.setComment(rs.getString(6));
            p.setnOfVisits(rs.getInt(7));
            p.setId(rs.getInt(8));
           
            allPatients.add(p);      

        }
        return allPatients;


    }
    
    
    public void savePatient(Patient patToSave,Connection conn) throws SQLException{
            

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO formpatients( clinician, name, surname, gender, birth, firstVisit, comment, nOfVisits)"+
                                           " VALUES (?,?,?,?,?,?,?,?);");

            
            stmt.setString(1, patToSave.getClinician());
            stmt.setString(2, patToSave.getName());
            stmt.setString(3, patToSave.getSurname());
            stmt.setString(4, patToSave.getGender());
            stmt.setString(5, patToSave.getBirthDate());
            stmt.setString(6, patToSave.getFirstVisit());
            stmt.setString(7, patToSave.getComment()); 
            stmt.setInt(8, patToSave.getnOfVisits());

            stmt.execute();
             
    }
    
     public void updatePatient(Patient patToSave,Connection conn) throws SQLException{
         
          
            PreparedStatement stmt = conn.prepareStatement("UPDATE formpatients"+
                                             " SET name=?, surname=?,gender=?, birth=?, firstVisit=?, comment=?"+
                                             " WHERE clinician=? AND id=? ;"); 

            stmt.setString(1, patToSave.getName());
            stmt.setString(2, patToSave.getSurname());
            stmt.setString(3, patToSave.getGender());
            stmt.setString(4, patToSave.getBirthDate());
            stmt.setString(5, patToSave.getFirstVisit());
            stmt.setString(6, patToSave.getComment()); 
            stmt.setString(7, patToSave.getClinician());
            stmt.setInt(8, patToSave.getId()); 

            int rowsUpdated = stmt.executeUpdate();//if 0, something wrong
     }
    

     
       public void deletePatient(String activeUserEmail,int patientId,Connection conn) throws SQLException{
       

            PreparedStatement stmt = conn.prepareStatement(" delete from formpatients"+ 
                                           " where clinician =? AND id=? limit 1;");

            stmt.setString(1, activeUserEmail); 
            stmt.setInt(2, patientId); 

            int rowCount = stmt.executeUpdate();

       }
     
}