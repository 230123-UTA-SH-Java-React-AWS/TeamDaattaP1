package com.revature.repositories;
import java.sql.*;
import com.revature.util.*;
import com.revature.model.LoginCred;
import java.util.HashMap;
import java.util.Map;
public class LoginCredsRepo {

    private Map<String, LoginCred> Accounts;

    public LoginCredsRepo(){
        this.Accounts = new HashMap<String, LoginCred>();
        this.FillRepoFromDatabase();
    }

    //for registering a new account
    public void RegisterAccount(LoginCred Cred){
        try {
            this.RegisterToDatabase(Cred.getEmail(), Cred.getPassword());
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("unable to register account, account name may already be taken");
            return;//stop here if an exception was thrown, do not add to the repo
        }
        //
        this.RegisterToAccountRepo(Cred.getEmail(), Cred.getPassword());
    }

    //returns true if login is seccuessful, otherwise returns false
    //could throw exceptions on failure if that would be perfered
    //could also just return the matching password and let the service handle validation
    public LoginCred Login(LoginCred Cred){
        //TODO: change return type to object
        boolean result = false;
        if (Accounts.containsKey(Cred.getEmail())
             && Accounts.get(Cred.getEmail()).equals(Cred.getPassword())
             ){
            result = true;
        }
        return result;
    }
    //methods to split up database and non-database interactions
    private void FillRepoFromDatabase(){
        //TODO: replace accounts map with an LoginCred-object datastructure
        String sql = "select useremail, userpassword from logincredentials";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                //employee employ = new employee(
                Accounts.put(
                                                result.getString(1), 
                                                result.getString(2)
                                                );

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void RegisterToDatabase(String email, String password) throws SQLException{
        // TODO: replace parameters with the LoginCred-object

        String sql = "insert into logincredentials (useremail, userpassword) values(?, ?)";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, email);
            prstmt.setString(2, password);
            prstmt.execute();

        }
        
    }



    private void RegisterToAccountRepo(String email, String password){
        Accounts.put(email, password);
    }
    
}