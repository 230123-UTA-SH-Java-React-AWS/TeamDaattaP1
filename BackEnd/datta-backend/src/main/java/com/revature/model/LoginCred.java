package com.revature.model;

public class LoginCred {
    private String email;
    private String password;
    //primary and foreign keys, here if we need them, doing nothing if we don't
    private int account_id;
    private int credential_id;

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
    public int getAccount_id() {
        return account_id;
    }
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
    public int getCredential_id() {
        return credential_id;
    }
    public void setCredential_id(int credential_id) {
        this.credential_id = credential_id;
    }

    public LoginCred(){
        this.email = null;
        this.password = null;
    }
}
