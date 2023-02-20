package com.revature.repositories;
import java.sql.*;
import com.revature.util.*;
import com.revature.model.LoginCred;
import com.revature.model.Account;
import java.util.HashMap;
import java.util.Map;
public class LoginCredsRepo {

    // TODO: Map<String, String> (<email_string, passw_string>) -TS
    // Changing it to this would mean that we wouldn't be able to access other parts of the LoginCred objects(the forign and primary keys) -DP
    private Map<String, LoginCred> AllLoginCreds;

    public LoginCredsRepo(){
        this.AllLoginCreds = new HashMap<String, LoginCred>();
        this.FillRepoFromDatabase();
    }

    //for registering a new login
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

    // Can you do magic to return an Account object instead of a LoginCred? -TS
    // yes, but I should probably be passing the account forign key to give to the accountsRepo so that it doesn't need to query the database directly. Abracadabra -DP
    //returns Account from the database that matches the credentials of the input LoginCreds.
    //still does a bit of validation, but that can still be done elsewhere,  then we can get rid of this horribly ugly if-statement
    public int Login(LoginCred Cred){
        LoginCred realCreds = null;
        if (AllLoginCreds.containsKey(Cred.getEmail()) //login exists
             && AllLoginCreds.get(Cred.getEmail()).getPassword().equals(Cred.getPassword()) //password matches
             ){
                realCreds = AllLoginCreds.get(Cred.getEmail());
        }
        //this is the similar sql used to fill the accountsRepo
        /*
        Account newAcc = new Account();
        String sql = "select * from accounts where accountid = ?";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setInt(1, realCreds.getAccount_id());
            ResultSet result = prstmt.executeQuery();
            while(result.next()){
                
                newAcc.setAccount_id(result.getInt(1));
                newAcc.setFirstName(result.getString(2));
                newAcc.setLastName(result.getString(3));
                newAcc.setDob(result.getDate(4));
                newAcc.setBio(result.getString(5));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        */
        //

        return realCreds.getCredential_id(); //realCreds.account_id is currently always undefined(null)
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