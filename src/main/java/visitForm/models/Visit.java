/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.models;

import java.util.ArrayList;

//     var allData = {clinicians:{name:nameOfClinician, date:visitDate, attendees:attendee}, 
//                          appointmentType:appointmentType, 
//                          plan:{weightGrowthOk:weightGrowthOk,bloodPressureOk:bloodPressureOk, comment:commentPlan, reviewIn:reviewIn},
//                          progressProblems:{parentViewNotes:parentViewNotes, childViewNotes:childViewNotes},
//                          treatmentScores:{inattentionTot:inattentionTot,impulsivityTotal:impulsivityTotal,inattentionMean:inattentionMean,deportmentMean:deportmentMean,cgas:cgas },
//                          allMedications:allMedications,[string,string,string...]
//                          otherSymptoms:symptomsArr, [string,string,...]
//                          id: $("#visitFormDiv").data("id")};

public class Visit {

   
    private Clinician clinician;
    private String appointmentType;
    private Plan plan;
    private ProgressProblems progressProblems;
    private TreatmentScores treatmentScores;
    private String[] allMedications;
    private String[] otherSymptoms;
    private int id;
    
    public static class Clinician{
         private String name;
         private String date;
         private String[] attendees;
        
        public String getName() {
            return name;
        }

        public String getVisitDate() {
            return date;
        }

        public String[] getAttendees() {
            return attendees;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAttendees(String[] attendees) {
            this.attendees = attendees;
        }
        
    }
    
    public static class Plan{
        private int  weightGrowthOk;
        private int  bloodPressureOk;
        private String comment;
        private int reviewIn;

        public int getWeightGrowthOk() {
            return weightGrowthOk;
        }

        public int getBloodPressureOk() {
            return bloodPressureOk;
        }

        public String getComment() {
            return comment;
        }

        public int getReviewIn() {
            return reviewIn;
        }

        
        
    }
    
    public static class ProgressProblems{
        private String parentViewNotes;
        private String childViewNotes;

        public String getParentViewNotes() {
            return parentViewNotes;
        }

        public String getChildViewNotes() {
            return childViewNotes;
        }
        
    }
    
    public static class TreatmentScores{
        private int inattentionTot;
        private int impulsivityTotal;
        private int inattentionMean;
        private int deportmentMean;
        private int cgas;

        public int getInattentionTot() {
            return inattentionTot;
        }

        public int getImpulsivityTotal() {
            return impulsivityTotal;
        }

        public int getInattentionMean() {
            return inattentionMean;
        }

        public int getDeportmentMean() {
            return deportmentMean;
        }

        public int getCgas() {
            return cgas;
        }
        
    }
    
    
    public Clinician getClin() {
        return clinician;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public Plan getPlan() {
        return plan;
    }

    public ProgressProblems getProgrProb() {
        return progressProblems;
    }

    public TreatmentScores getTreatScores() {
        return treatmentScores;
    }

    public String[] getAllMedications() {
        return allMedications;
    }

    public String[] getOtherSymptoms() {
        return otherSymptoms;
    }

    public int getId() {
        return id;
    }

    public void setClinician(Clinician clinician) {
        this.clinician = clinician;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setProgressProblems(ProgressProblems progressProblems) {
        this.progressProblems = progressProblems;
    }

    public void setTreatmentScores(TreatmentScores treatmentScores) {
        this.treatmentScores = treatmentScores;
    }

    public void setAllMedications(String[] allMedications) {
        this.allMedications = allMedications;
    }

    public void setOtherSymptoms(String[] otherSymptoms) {
        this.otherSymptoms = otherSymptoms;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
