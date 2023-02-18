package com.revature.repositories;
import java.sql.*;
import com.revature.util.*;
import com.revature.model.LoginCred;
import java.util.HashMap;
import java.util.Map;
public class LoginCredsRepo {

    private Map<String, LoginCred> AllLoginCreds;

    public LoginCredsRepo(){
        this.AllLoginCreds = new HashMap<String, LoginCred>();
        this.FillRepoFromDatabase();
    }

    //for registering a new account
    public void RegisterAccount(LoginCred Cred){
        try {
            this.RegisterToDatabase(Cred);
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("unable to register account, account name may already be taken");
            return;//stop here if an exception was thrown, do not add to the repo
        }
        this.RegisterToAccountRepo(Cred);
    }

    // TODO: Can you set this up to return an Account object instead of the LoginCred?
    //returns LoginCred from the database that matches the credentials of the input LoginCreds.
    //still does a bit of validation, but that can still be done elsewhere,  then we can get rid of this horribly ugly if-statement
    public LoginCred Login(LoginCred Cred){
        LoginCred realCreds = null;
        if (AllLoginCreds.containsKey(Cred.getEmail()) //login exists
             && AllLoginCreds.get(Cred.getEmail()).getPassword().equals(Cred.getPassword()) //password matches
             ){
                realCreds = AllLoginCreds.get(Cred.getEmail());
        }
        return realCreds;
    }


    //methods to split up database and non-database interactions
    private void FillRepoFromDatabase(){
        String sql = "select * from logincredentials";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                LoginCred newCred = new LoginCred();
                //keys aren't implemented into the model yet
                //newCred.setLoginID(result.getint(1));
                newCred.setEmail(result.getString(2));
                newCred.setPassword(result.getString(3));
                //newCred.setUserID(result.getint(4));
                AllLoginCreds.put(newCred.getEmail(), newCred);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void RegisterToDatabase(LoginCred Cred) throws SQLException{
        String sql = "insert into logincredentials (useremail, userpassword) values(?, ?)";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, Cred.getEmail());
            prstmt.setString(2, Cred.getPassword());
            prstmt.execute();
        }
    }



    private void RegisterToAccountRepo(LoginCred Cred){
        AllLoginCreds.put(Cred.getEmail(), Cred);
    }

}