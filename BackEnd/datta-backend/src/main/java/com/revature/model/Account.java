package com.revature.model;

public class Account {
    private String email;
    private String firstName;
    private String lastName;
    private java.sql.Date dob;
    private String bio;
    //private int account id
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public java.sql.Date getDob() {
        return dob;
    }
    public void setDob(java.sql.Date dob) {
        this.dob = dob;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public Account(){
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.dob = null;
        this.bio = null;
    }
}
