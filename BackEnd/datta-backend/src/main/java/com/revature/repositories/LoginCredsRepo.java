package com.revature.repositories;
import java.sql.*;
import com.revature.util.*;
import com.revature.model.LoginCred;
import com.revature.model.Account;
import java.util.HashMap;
import java.util.Map;
public class LoginCredsRepo {

    //TODO: implement interfaces

    // TODO: Map<String, String> (<email_string, passw_string>) -TS
    // Changing it to this would mean that we wouldn't be able to access other parts of the LoginCred objects(the forign and primary keys) -DP
    private HashMap<String, LoginCred> AllLoginCreds;

    public LoginCredsRepo(){
        this.AllLoginCreds = new HashMap<String, LoginCred>();
        this.FillRepoFromDatabase();
    }

    //for registering new login credentials
    public void RegisterLogin(LoginCred Cred){
        try {
            this.RegisterToDatabase(Cred);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("unable to register account, account name may already be taken");
            return;//stop here if an exception was thrown, do not add to the repo
        }
        this.RegisterToLoginCredsRepo(Cred);
    }

    //used for login
    public HashMap<String, LoginCred> getAll(){
        return this.AllLoginCreds;
    }

    //methods to split up database and non-database interactions
    private void FillRepoFromDatabase(){
        String sql = "SELECT * FROM logincredentials ORDER BY loginid ASC";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                LoginCred newCred = new LoginCred();
                newCred.setCredential_id(result.getInt(1));
                newCred.setEmail(result.getString(2));
                newCred.setPassword(result.getString(3));
                newCred.setAccount_id(result.getInt(4));//need to spend some time considering how account creation hooks in. -DP
                AllLoginCreds.put(newCred.getEmail(), newCred);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //TODO: Change to version in interface -ab
    private void RegisterToDatabase(LoginCred Cred) throws SQLException{
        String sql = "insert into logincredentials (useremail, userpassword) values(?, ?)";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, Cred.getEmail());
            prstmt.setString(2, Cred.getPassword());
            //should account creation be happening here automatically? otherwise the account key needs to be put into the database from elsewhere/at a diffrent time -DP
            //oh, it might also need to be added to the Creds objects at some point? Maybe there should be a new method to insert them in? This is getting more complicated than I thought -DP
            //ideally account_id will always be the same as the cred_id, so maybe its not necesary at all? regardless, I'll deal with it later -DP
            //prstmt.setInt(3, account.getAccount_id);
            prstmt.execute();
        }
    }

    //What is the differenct between these two functions? -ab

    private void RegisterToLoginCredsRepo(LoginCred Cred){
        String sql = "SELECT MAX(loginid) FROM logincredentials";//the highest id, should be the one just inserted into the databse
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();
            Cred.setAccount_id(result.getInt(1));
        } catch (Exception e) {
           e.printStackTrace();
        }
        AllLoginCreds.put(Cred.getEmail(), Cred);
    }

}