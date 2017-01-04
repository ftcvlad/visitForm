/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.models;

import visitForm.stores.Visit;
import java.sql.Connection;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author Vlad
 */
public class VisitTable {
    
    
    
    public boolean saveVisit(String activeUserEmail, Visit visitToSave, Connection conn) throws SQLException{
        
        PreparedStatement stmt0 = conn.prepareStatement("SELECT form_patients.nOfVisits FROM form_patients WHERE clinician=? AND id=? LIMIT 1 ");
        stmt0.setString(1,activeUserEmail );
        stmt0.setInt(2,visitToSave.getId() );
        ResultSet rs0 = stmt0.executeQuery();
        
      
        if (rs0.next()){

              
            PreparedStatement stmt1 = conn.prepareStatement("UPDATE form_patients SET nOfVisits=nOfVisits+1 WHERE id=? ;");
            stmt1.setInt(1,visitToSave.getId());
            stmt1.executeUpdate();


            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO form_visits (name,date,attendees,appointmentType,weightGrowthOk,bloodPressureOk,reviewIn,"+
                                                                  "commentPlan, parentViewNotes, childViewNotes, inattentionTotal,impulsivityTotal,"+
                                                                  "inattentionMean,deportmentMean,cgas,allMedications,patientId)"+
                                           " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);

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
            stmt2.setInt(17, visitToSave.getId());

            stmt2.execute();

            ResultSet rs = stmt2.getGeneratedKeys();
            int lastInsertId;
            if (rs.next()){
                lastInsertId = rs.getInt(1);
            }
            else{
                return false;
            }
            
            PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO form_other_symptoms (visitId,insomnia,nightmares,drowsiness,anorexia,stomachaches,headaches,"+
                                                                  "dizziness, unhappy, crying, irritable,selfharm,"+
                                                                  "suicidal,euphoric,anxious,ticks,zombie,lesstalk,lesssociable)"+
                                           " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

            stmt3.setInt(1,lastInsertId);
            stmt3.setInt(2, visitToSave.getOtherSymptoms().getInsomnia());
            stmt3.setInt(3, visitToSave.getOtherSymptoms().getNightmares());
            stmt3.setInt(4, visitToSave.getOtherSymptoms().getDrowsiness());
            stmt3.setInt(5, visitToSave.getOtherSymptoms().getAnorexia());
            stmt3.setInt(6, visitToSave.getOtherSymptoms().getStomachaches());
            stmt3.setInt(7, visitToSave.getOtherSymptoms().getHeadaches());
            stmt3.setInt(8, visitToSave.getOtherSymptoms().getDizziness());
            stmt3.setInt(9, visitToSave.getOtherSymptoms().getUnhappy());
            stmt3.setInt(10, visitToSave.getOtherSymptoms().getCrying());
            stmt3.setInt(11, visitToSave.getOtherSymptoms().getIrritable());
            stmt3.setInt(12, visitToSave.getOtherSymptoms().getSelfharm());
            stmt3.setInt(13, visitToSave.getOtherSymptoms().getSuicidal());
            stmt3.setInt(14, visitToSave.getOtherSymptoms().getEuphoric());
            stmt3.setInt(15, visitToSave.getOtherSymptoms().getAnxious());
            stmt3.setInt(16, visitToSave.getOtherSymptoms().getTicks());
            stmt3.setInt(17, visitToSave.getOtherSymptoms().getZombie());
            stmt3.setInt(18, visitToSave.getOtherSymptoms().getLesstalk());
            stmt3.setInt(19, visitToSave.getOtherSymptoms().getLesssociable());
       
            stmt3.execute();
            
            return true;
        }
        

        return false;


    }
    
    
    public ArrayList<Visit> getVisits(String activeUserEmail, int id , Connection conn) throws SQLException{
        
       
        PreparedStatement stmt0 = conn.prepareStatement("SELECT 1 FROM form_patients WHERE clinician=? AND id=? LIMIT 1 ");
        stmt0.setString(1,activeUserEmail);
        stmt0.setInt(2,id);
        ResultSet rs0 = stmt0.executeQuery();
        
       
        ArrayList<Visit> retrievedVisits = new ArrayList<>();
        if (!rs0.next()){

              return retrievedVisits;
        }
        else{
               PreparedStatement stmt1 = conn.prepareStatement("SELECT name, date, attendees, appointmentType, weightGrowthOk, bloodPressureOk, reviewIn, commentPlan, "+
                                                  "parentViewNotes, childViewNotes, inattentionTotal, impulsivityTotal, inattentionMean,"+
                                                  "deportmentMean, cgas, allMedications, "
                                                + "insomnia,nightmares,drowsiness,anorexia,stomachaches,headaches,dizziness,unhappy,crying,"
                                                + "irritable,selfharm,suicidal,euphoric,anxious,ticks,zombie,lesstalk,lesssociable "
                                                + "FROM form_visits JOIN form_other_symptoms "
                                                + "ON form_visits.visitId=form_other_symptoms.visitId "
                                                + "WHERE patientId=? ORDER BY date DESC;");
               
               
  
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
                    
                    Visit.OtherSymptoms otherSymptoms = new Visit.OtherSymptoms();
                    otherSymptoms.setInsomnia(rs1.getInt("insomnia"));
                    otherSymptoms.setNightmares(rs1.getInt("nightmares"));
                    otherSymptoms.setDrowsiness(rs1.getInt("drowsiness"));
                    otherSymptoms.setAnorexia(rs1.getInt("anorexia"));
                    otherSymptoms.setStomachaches(rs1.getInt("stomachaches"));
                    otherSymptoms.setHeadaches(rs1.getInt("headaches"));
                    otherSymptoms.setDizziness(rs1.getInt("dizziness"));
                    otherSymptoms.setUnhappy(rs1.getInt("unhappy"));
                    otherSymptoms.setCrying(rs1.getInt("crying"));
                    otherSymptoms.setIrritable(rs1.getInt("irritable"));
                    otherSymptoms.setSelfharm(rs1.getInt("selfharm"));
                    otherSymptoms.setSuicidal(rs1.getInt("suicidal"));
                    otherSymptoms.setEuphoric(rs1.getInt("euphoric"));
                    otherSymptoms.setAnxious(rs1.getInt("anxious"));
                    otherSymptoms.setTicks(rs1.getInt("ticks"));
                    otherSymptoms.setZombie(rs1.getInt("zombie"));
                    otherSymptoms.setLesstalk(rs1.getInt("lesstalk"));
                    otherSymptoms.setLesssociable(rs1.getInt("lesssociable"));
                   
                    nextVisit.setOtherSymptoms(otherSymptoms);
                    
                    
                    retrievedVisits.add(nextVisit);

               }


                return retrievedVisits;

        }
        
        
    }
    
}
