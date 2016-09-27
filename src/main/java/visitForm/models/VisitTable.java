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
import java.util.ArrayList;
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
    
    
    public ArrayList<Visit> getVisits(String activeUserEmail, int id , Connection conn) throws SQLException{
        
        //could use some join :)
        PreparedStatement stmt0 = conn.prepareStatement("SELECT 1 FROM formpatients WHERE clinician=? AND id=? LIMIT 1 ");
        stmt0.setString(1,activeUserEmail);
        stmt0.setInt(2,id);
        ResultSet rs0 = stmt0.executeQuery();
        
       
        ArrayList<Visit> retrievedVisits = new ArrayList<Visit>();
        if (!rs0.next()){

              return retrievedVisits;
        }
        else{
               PreparedStatement stmt1 = conn.prepareStatement("SELECT name, date, attendees, appointmentType, weightGrowthOk, bloodPressureOk, reviewIn, commentPlan, "+
                                                  "parentViewNotes, childViewNotes, inattentionTotal, impulsivityTotal, inattentionMean,"+
                                                  "deportmentMean, cgas, allMedications, otherSymptoms "+
                                                  "FROM visits WHERE patientId=? ORDER BY date DESC;");
               stmt1.setInt(1,id);
               ResultSet rs1 = stmt1.executeQuery(); 


               while(rs1.next()){
                   
                    Visit nextVisit = new Visit();
                    
                    Visit.Clinician nextClin  = new  Visit.Clinician();
                    nextClin.setName(rs1.getString(1));
                    nextClin.setDate(rs1.getString(2));
                    nextClin.setAttendees(new Gson().fromJson(rs1.getString(3), String[].class));//normalize      
                    nextVisit.setClinician(nextClin);
                    
                    nextVisit.setAppointmentType(rs1.getString(4));
                    
                    Visit.Plan nextPlan = new Visit.Plan();
                    nextPlan.setWeightGrowthOk(rs1.getInt(5));
                    nextPlan.setBloodPressureOk(rs1.getInt(6));
                    nextPlan.setReviewIn(rs1.getInt(7));
                    nextPlan.setComment(rs1.getString(8));
                    nextVisit.setPlan(nextPlan);
                    
                     //var nextVisit = {, ,,treatmentScores:{},id:id};
                   
                    Visit.ProgressProblems nextProgress = new Visit.ProgressProblems();
                    nextProgress.setParentViewNotes(rs1.getString(9));
                    nextProgress.setChildViewNotes(rs1.getString(10));
                    nextVisit.setProgressProblems(nextProgress);
                     
                    Visit.TreatmentScores nextScores = new Visit.TreatmentScores();
                    nextScores.setInattentionTot(rs1.getInt(11));
                    nextScores.setImpulsivityTotal(rs1.getInt(12));
                    nextScores.setInattentionMean(rs1.getInt(13));
                    nextScores.setDeportmentMean(rs1.getInt(14));
                    nextScores.setCgas(rs1.getInt(15));
                    nextVisit.setTreatmentScores(nextScores);
                    
                    nextVisit.setAllMedications(new Gson().fromJson(rs1.getString(16), String[].class));//normalize
                    nextVisit.setOtherSymptoms(new Gson().fromJson(rs1.getString(17), String[].class));//normalize
                     
                    retrievedVisits.add(nextVisit);

               }


                return retrievedVisits;

        }
        
        
    }
    
}
