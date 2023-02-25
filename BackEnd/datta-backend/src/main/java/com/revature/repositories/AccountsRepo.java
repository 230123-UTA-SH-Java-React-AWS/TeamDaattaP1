package com.revature.repositories;
import java.sql.*;
import java.text.ParseException;

import com.revature.util.*;
import com.revature.model.Account;
import java.util.List;
import java.util.ArrayList;
public class AccountsRepo implements AccountsInterface{
    // hard to decide which datastructure to use here, went for arraylist so that the account_id can be their index in the aray
    private List<Account> AllAccounts;

    public AccountsRepo(){
        this.AllAccounts = new ArrayList<Account>();
        this.AllAccounts.add(null);//index 0 is null so that each account can have their account_id be their index in the array (rather than account_id-1)
        this.FillRepoFromDatabase();
    }

    //for registering a new account(to be used in combination with the Registration method from a LoginCredsRepo)
    public void RegisterAccount(Account newAccount){
        //insert into the database
        try{
        this.RegisterToDatabase(newAccount); //this probably doesn't need to be in a try-catch anymore. validation moved to service layer. -DP
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("unable to register account, account name may already be taken");
            return;//stop here if an exception was thrown, do not add to the repo
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //insert into the repo
        this.RegisterToAccountRepo(newAccount);
    }

    //use for logingin
    @Override
    public Account getAccount(int id){
        return this.AllAccounts.get(id);
    }

    
    //private methods

    private void FillRepoFromDatabase() {
        Account newAcc = new Account();
        String sql = "SELECT * FROM accounts ORDER BY accountid ASC";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();
            while(result.next()){
                newAcc.setAccount_id(result.getInt(1));
                newAcc.setFirstName(result.getString(2));
                newAcc.setLastName(result.getString(3));
                newAcc.setDob(result.getString(4));
                newAcc.setBio(result.getString(5));
                this.AllAccounts.add(newAcc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void RegisterToDatabase(Account newAcc) throws SQLException, ParseException{
        String sql = "insert into accounts (firstname, lastname, dateofbirth, bio) values(?, ?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, newAcc.getFirstName());
            prstmt.setString(2, newAcc.getLastName());
            prstmt.setString(3, newAcc.getDob());
            prstmt.setString(4, newAcc.getBio());
            prstmt.execute();
        }
    }

    private void RegisterToAccountRepo(Account newAcc){
        //get the account_id and put it into the object
        String sql = "SELECT MAX(accountid) FROM accounts";//the highest id, should be the one just inserted into the databse
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();
            newAcc.setAccount_id(result.getInt(1));
        } catch (Exception e) {
           e.printStackTrace();
        }
        //put the object into the repo's datastructure
        this.AllAccounts.add(newAcc);
    }

    @Override
    public void changeAccountInfo(Account newInfo) {
        String sql = "update accounts set firstname = ?, lastname = ?, dateofbirth = ?, bio = ? where accountid = ?";

        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, newInfo.getFirstName());
            prstmt.setString(2, newInfo.getLastName());
            prstmt.setString(3, newInfo.getDob());
            prstmt.setString(4, newInfo.getBio());
            prstmt.setInt(5, newInfo.getAccount_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
