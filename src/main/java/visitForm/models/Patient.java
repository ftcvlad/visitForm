/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitForm.models;

/**
 *
 * @author Vlad
 */
public class Patient {
    
    String clinician;
    String name;
    String surname;
    String gender;
    String birthDate;
    String firstVisit;
    String comment;
    int nOfVisits;
    int id;

    public void setClinician(String clinician) {
        this.clinician = clinician;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setFirstVisit(String firstVisit) {
        this.firstVisit = firstVisit;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setnOfVisits(int nOfVisits) {
        this.nOfVisits = nOfVisits;
    }

    public void setId(int id) {
        this.id = id;
    }
   

    public String getClinician() {
        return clinician;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getFirstVisit() {
        return firstVisit;
    }

    public String getComment() {
        return comment;
    }

    public int getnOfVisits() {
        return nOfVisits;
    }

    public int getId() {
        return id;
    }
    

}
