package com.revature.model;

public class LoginCred {
    private String email;
    private String password;
    //primary and foreign keys, here if we need them, doing nothing if we don't
    //private int account id
    //private int credential id

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LoginCred(){
        this.email = null;
        this.password = null;
    }
}
