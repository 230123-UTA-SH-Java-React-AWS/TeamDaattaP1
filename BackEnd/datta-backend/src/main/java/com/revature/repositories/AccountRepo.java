package com.revature.repositories;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
public class AccountRepo {

    private Map<String, String> Accounts;

    public AccountRepo(){
        this.Accounts = new HashMap<String, String>();
        this.FillRepoFromDatabase();
    }

    //for registering a new account
    public void RegisterAccount(String email, String password){
        try {
            this.RegisterToDatabase(email, password);
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("unable to register account, account name may already be taken");
            return;//stop here if an exception was thrown, do not add to the repo
        }
        //
        this.RegisterToAccountRepo(email, password);
        
    }

    //returns true if login is seccuessful, otherwise returns false
    //could throw exceptions on failure if that would be perfered
    //could also just return the matching password and let the service handle validation
    public boolean Login(String email, String password){
        boolean result = false;
        if (Accounts.containsKey(email)
             && Accounts.get(email).equals(password)
             ){
            result = true;
        }
        return result;
    }


    //methods to split up database and non-database interactions

    private void FillRepoFromDatabase(){
        //TODO: connect to database
        //put sql prepared statements here
        //select * from ...
    }
    private void RegisterToDatabase(String email, String password) throws SQLException{
        // TODO: connect to database
        //put sql prepared statements here
        //insert into ...
        
    }
    private void RegisterToAccountRepo(String email, String password){
        Accounts.put(email, password);
    }
    
}