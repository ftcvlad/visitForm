/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.models;

import java.sql.Connection;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Vlad
 */
public class VisitTable {
    
    
    
    public boolean saveVisit(String activeUserEmail, Visit visitToSave, Connection conn) throws SQLException{
        
      
      
    
        PreparedStatement stmt0 = conn.prepareStatement("SELECT formpatients.nOfVisits FROM formpatients WHERE clinician=? AND id=? LIMIT 1 ");
        stmt0.setString(1,activeUserEmail );
        stmt0.setInt(2,visitToSave.getId() );
        ResultSet rs0 = stmt0.executeQuery();
        
        System.out.println(activeUserEmail+" "+visitToSave.getId());
        if (rs0.next()){

              
              PreparedStatement stmt1 = conn.prepareStatement("UPDATE formpatients SET nOfVisits=nOfVisits+1 WHERE id=? ;");
              stmt1.setInt(1,visitToSave.getId());
              stmt1.executeUpdate();


              PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO visits (name,date,attendees,appointmentType,weightGrowthOk,bloodPressureOk,reviewIn,"+
                                                                    "commentPlan, parentViewNotes, childViewNotes, inattentionTotal,impulsivityTotal,"+
                                                                    "inattentionMean,deportmentMean,cgas,allMedications,otherSymptoms,patientId)"+
                                             " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

              stmt2.setString(1, visitToSave.getClin().getName());
              stmt2.setString(2,visitToSave.getClin().getVisitDate());
              
              
              stmt2.setString(3, new Gson().toJson(visitToSave.getClin().getAttendees()));
              stmt2.setString(4, visitToSave.getAppointmentType());
              stmt2.setInt(5, visitToSave.getPlan().getWeightGrowthOk());
              stmt2.setInt(6, visitToSave.getPlan().getBloodPressureOk());
              stmt2.setInt(7, visitToSave.getPlan().getReviewIn()); 
              stmt2.setString(8, visitToSave.getPlan().getComment());
              stmt2.setString(9, visitToSave.getProgrProb().getParentViewNotes());
              stmt2.setString(10, visitToSave.getProgrProb().getChildViewNotes());
              stmt2.setInt(11, visitToSave.getTreatScores().getInattentionTot());
              stmt2.setInt(12, visitToSave.getTreatScores().getImpulsivityTotal());
              stmt2.setInt(13, visitToSave.getTreatScores().getInattentionMean());
              stmt2.setInt(14, visitToSave.getTreatScores().getDeportmentMean());
              stmt2.setInt(15, visitToSave.getTreatScores().getCgas());
              stmt2.setString(16, new Gson().toJson(visitToSave.getAllMedications()));
              stmt2.setString(17, new Gson().toJson(visitToSave.getOtherSymptoms()));
              stmt2.setInt(18, visitToSave.getId());




              stmt2.execute();
              return true;
        }
        

        return false;


    }
    
}
