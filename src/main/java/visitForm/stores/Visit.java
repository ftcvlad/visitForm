/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.stores;
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
    private OtherSymptoms otherSymptoms;
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

        public void setWeightGrowthOk(int weightGrowthOk) {
            this.weightGrowthOk = weightGrowthOk;
        }

        public void setBloodPressureOk(int bloodPressureOk) {
            this.bloodPressureOk = bloodPressureOk;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setReviewIn(int reviewIn) {
            this.reviewIn = reviewIn;
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

        public void setParentViewNotes(String parentViewNotes) {
            this.parentViewNotes = parentViewNotes;
        }

        public void setChildViewNotes(String childViewNotes) {
            this.childViewNotes = childViewNotes;
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

        public void setInattentionTot(int inattentionTot) {
            this.inattentionTot = inattentionTot;
        }

        public void setImpulsivityTotal(int impulsivityTotal) {
            this.impulsivityTotal = impulsivityTotal;
        }

        public void setInattentionMean(int inattentionMean) {
            this.inattentionMean = inattentionMean;
        }

        public void setDeportmentMean(int deportmentMean) {
            this.deportmentMean = deportmentMean;
        }

        public void setCgas(int cgas) {
            this.cgas = cgas;
        }
        
    }
    
    
    
    
     public static class OtherSymptoms{
        private int insomnia;
        private int nightmares;
        private int drowsiness;
        private int anorexia;
        private int stomachaches;
        private int headaches;
        private int dizziness;
        private int unhappy;
        private int crying;
        private int irritable;
        private int selfharm;
        private int suicidal;
        private int euphoric;
        private int anxious;
        private int ticks;
        private int zombie;
        private int lesstalk;
        private int lesssociable;

        public int getInsomnia() {
            return insomnia;
        }

        public void setInsomnia(int insomnia) {
            this.insomnia = insomnia;
        }

        public int getNightmares() {
            return nightmares;
        }

        public void setNightmares(int nightmares) {
            this.nightmares = nightmares;
        }

        public int getDrowsiness() {
            return drowsiness;
        }

        public void setDrowsiness(int drowsiness) {
            this.drowsiness = drowsiness;
        }

        public int getAnorexia() {
            return anorexia;
        }

        public void setAnorexia(int anorexia) {
            this.anorexia = anorexia;
        }

        public int getStomachaches() {
            return stomachaches;
        }

        public void setStomachaches(int stomachaches) {
            this.stomachaches = stomachaches;
        }

        public int getHeadaches() {
            return headaches;
        }

        public void setHeadaches(int headaches) {
            this.headaches = headaches;
        }

        public int getDizziness() {
            return dizziness;
        }

        public void setDizziness(int dizziness) {
            this.dizziness = dizziness;
        }

        public int getUnhappy() {
            return unhappy;
        }

        public void setUnhappy(int unhappy) {
            this.unhappy = unhappy;
        }

        public int getCrying() {
            return crying;
        }

        public void setCrying(int crying) {
            this.crying = crying;
        }

        public int getIrritable() {
            return irritable;
        }

        public void setIrritable(int irritable) {
            this.irritable = irritable;
        }

        public int getSelfharm() {
            return selfharm;
        }

        public void setSelfharm(int selfharm) {
            this.selfharm = selfharm;
        }

        public int getSuicidal() {
            return suicidal;
        }

        public void setSuicidal(int suicidal) {
            this.suicidal = suicidal;
        }

        public int getEuphoric() {
            return euphoric;
        }

        public void setEuphoric(int euphoric) {
            this.euphoric = euphoric;
        }

        public int getAnxious() {
            return anxious;
        }

        public void setAnxious(int anxious) {
            this.anxious = anxious;
        }

        public int getTicks() {
            return ticks;
        }

        public void setTicks(int ticks) {
            this.ticks = ticks;
        }

        public int getZombie() {
            return zombie;
        }

        public void setZombie(int zombie) {
            this.zombie = zombie;
        }

        public int getLesstalk() {
            return lesstalk;
        }

        public void setLesstalk(int lesstalk) {
            this.lesstalk = lesstalk;
        }

        public int getLesssociable() {
            return lesssociable;
        }

        public void setLesssociable(int lesssociable) {
            this.lesssociable = lesssociable;
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

    public OtherSymptoms getOtherSymptoms() {
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

    public void setOtherSymptoms(OtherSymptoms otherSymptoms) {
        this.otherSymptoms = otherSymptoms;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
