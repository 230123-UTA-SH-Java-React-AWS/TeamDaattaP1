package com.revature.repositories;
import java.sql.*;

import com.revature.model.Account;
import com.revature.util.*;
import com.revature.model.LoginCred;
import java.util.HashMap;
import java.util.Optional;

public class LoginCredsRepo {

    //========HASHED PASSWORD LOGIN AND REGISTER=========
    // at the bottom

    // Changing it to this would mean that we wouldn't be able to access other parts of the LoginCred objects(the forign and primary keys) -DP
    private HashMap<String, LoginCred> AllLoginCreds;

    public LoginCredsRepo() {
        this.AllLoginCreds = new HashMap<String, LoginCred>();
        this.FillRepoFromDatabase();
    }

    //for registering new login credentials
    public void RegisterLogin(LoginCred Cred) {
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
    public HashMap<String, LoginCred> getAll() {
        return this.AllLoginCreds;
    }

    //methods to split up database and non-database interactions
    private void FillRepoFromDatabase() {
        String sql = "SELECT * FROM logincredentials ORDER BY loginid ASC";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();

            while (result.next()) {
                LoginCred newCred = new LoginCred();
                newCred.setCredential_id(result.getInt(1));
                newCred.setEmail(result.getString(2));
                newCred.setPassword(result.getString(3));
                newCred.setAccount_id(result.getInt(4));//need to spend some time considering how account creation hooks in. -DP
                AllLoginCreds.put(newCred.getEmail(), newCred);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Change to version in interface -ab
    private void RegisterToDatabase(LoginCred Cred) throws SQLException {
        String sql = "insert into logincredentials (useremail, userpassword) values(?, ?)";
        try (Connection con = ConnectionUtil.getConnection()) {
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

    private void RegisterToLoginCredsRepo(LoginCred Cred) {
        String sql = "SELECT MAX(loginid) FROM logincredentials";//the highest id, should be the one just inserted into the databse
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();
            Cred.setAccount_id(result.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllLoginCreds.put(Cred.getEmail(), Cred);
    }

    //========HASHED PASSWORD LOGIN AND REGISTER=========

    /**
     * Hashes a user's password and inserts the user's email, hashed password, and account ID into the database.
     * Automatically logs in the user on successful registration.
     *
     * @param email the user's email
     * @param password the user's password
     */
    public void hashRegister(String email, String password) {
        // Hash the password
        String hashedPassword = PasswordHasher.hashPassword(password);

        // Insert the user into the database
        Connection con = ConnectionUtil.getConnection();
        try {
            // Insert a new row in the accounts table with empty values
            PreparedStatement statement = con.prepareStatement("INSERT INTO accounts (firstname,lastname, dateofbirth, bio) VALUES (?, ?, ?, ?)");
            statement.setString(1, "");
            statement.setString(2, "");
            statement.setString(3, "");
            statement.setString(4, "");

            // Get the newly inserted account id
            int rowsaffected = statement.executeUpdate();
            int accountId;
            if (rowsaffected > 0) {
                PreparedStatement statement3  = con.prepareStatement("SELECT * FROM accounts ORDER BY accountid DESC LIMIT 1");
                ResultSet rs = statement3.executeQuery();
                if(rs.next()){
                    accountId = rs.getInt(1);
                    System.out.println(rs.getInt(1));

                    // Insert a new row in the logincredentials table
                    PreparedStatement statement2 = con.prepareStatement("INSERT INTO logincredentials (useremail, userpassword, userid) VALUES (?, ?, ?)");
                    statement2.setString(1, email);
                    statement2.setString(2, hashedPassword);
                    statement2.setInt(3, accountId);
                    statement2.executeUpdate();

                    // Login the user automatically on successful register
                    hashLogin(email,password);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Authenticates a user with a given email and password by checking the
     * corresponding hashed password in the database. If the credentials are
     * correct, generates a JWT token and returns it.
     *
     * @param email     the user's email
     * @param password  the user's password
     * @return          the generated JWT token, or null if authentication fails
     */
    public String hashLogin(String email, String password) {
        // Get a connection to the database
        Connection con = ConnectionUtil.getConnection();

        // Declare a variable to hold the JWT token
        String token = null;

        try {
            // Query the database for the user's hashed password and account info
            String sql = "SELECT lc.useremail, lc.userpassword, a.accountid, a.firstname,a.lastname, a.dateofbirth, a.bio "
                    + "FROM logincredentials lc "
                    + "JOIN accounts a ON lc.userid = a.accountid "
                    + "WHERE lc.useremail = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            // If there is a result, check the password
            if (rs.next()) {
                String hashedPassword = rs.getString("userpassword");
                if (PasswordHasher.checkPassword(password, hashedPassword)) {
                    // Credentials are correct, generate JWT token
                    int accountId = rs.getInt("accountid");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String dob = rs.getString("dateofbirth");
                    String bio = rs.getString("bio");
                    token = JwtUtil.generateToken(accountId, firstName, lastName, dob, bio);
                }
            }
        } catch (SQLException e) {
            // If an error occurs, print it to the console
            System.out.println(e.getMessage());
        }

        // Return the generated JWT token, or null if authentication fails
        return token;
    }

    /**
     Checks if a user with the given email exists in the logincredentials table of the database.
     Returns true if a user with the given email exists, false otherwise.
     @param email the email to check for
     @return true if a user with the given email exists, false otherwise
     */
    public boolean checkLogin(String email){
        // Get a connection to the database
        Connection con = ConnectionUtil.getConnection();
        try{
            // Prepare and execute a SELECT statement to check if a user with the given email exists
            String sql = "SELECT * FROM logincredentials WHERE useremail = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            // If a user with the given email exists, return true
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            // If an error occurs, throw a RuntimeException
            throw new RuntimeException(e);
        }
        // If no user with the given email was found, return false
        return false;
    }

    /**
     * Retrieves an Account object from the database by the given email, which is used to
     * locate the corresponding account id in the logincredentials table, and then
     * retrieves the rest of the account details from the accounts table.
     *
     * @param email the email address of the account to retrieve
     * @return the Account object corresponding to the email address, or null if not found
     */
    public Account getAccountByEmail(String email) {
        // Establish a connection to the database
        Connection con = ConnectionUtil.getConnection();
        // Initialize the Account object to return
        Account account = null;

        try {
            // Build a SQL query to retrieve the account details from the database
            String sql = "SELECT accountid, firstname, lastname, dateofbirth, bio FROM accounts WHERE accountid = (SELECT userid FROM logincredentials WHERE useremail = ?)";

            // Prepare the SQL statement with the query
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);

            // Execute the SQL statement and retrieve the result set
            ResultSet rs = statement.executeQuery();

            // If the result set contains data, retrieve the account details and create a new Account object
            if (rs.next()) {
                int accountId = rs.getInt("accountid");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String dob = rs.getString("dateofbirth");
                String bio = rs.getString("bio");
                account = new Account(accountId, firstName, lastName, dob, bio);
            }
        } catch (SQLException e) {
            // If there is an error executing the SQL statement, print the error message to the console
            System.out.println(e.getMessage());
        }

        // Return the Account object (or null if not found)
        return account;
    }


}