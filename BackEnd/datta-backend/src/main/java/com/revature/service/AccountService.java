package com.revature.service;

import java.lang.reflect.Field;
import java.util.*;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;

public class AccountService implements AccountServiceInterface, ServiceGenerics{

    private LoginCredsRepo LCrepo;
    private AccountsRepo Accrepo;

    public AccountService(LoginCredsRepo LCrepo, AccountsRepo Accrepo){
        this.LCrepo = LCrepo;
        this.Accrepo = Accrepo;
    }



    /**
     * Attempts to log in a user with the provided email and password by checking the corresponding
     * hashed password in the database. If the login attempt is successful, generates a JWT token and
     * returns it along with the user's account information.
     *
     * @param jsonLogin the JSON string containing the user's email and password
     * @return a map containing the generated JWT token and the user's account information, or an
     *         exception if the login attempt fails
     */
    @Override
    public Map<String,Object> loginUser(String jsonLogin){
        // Log that a user is being logged in
        System.out.println("We're logging in a user");

        // Parse the JSON string into a LoginCred object
        System.out.println(jsonLogin);
        LoginCred newLogin = convertToObject(jsonLogin, LoginCred.class);
        // Extract the email and password from the LoginCred object
        String email  = newLogin.getEmail();
        System.out.println(email);
        String password = newLogin.getPassword();

        // Check if the user exists in the database
        if (LCrepo.checkLogin(email)){
            System.out.println("Account exists woohoo");
            // Generate a JWT token for the user
            String token = LCrepo.hashLogin(email,password);
            // Get the user's account information from the database
            Account user = LCrepo.getAccountByEmail(email);
            // Create a response map containing the token and user information
            Map<String,Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user",user);
            return response;

        //This NEEDS to check password too, java gets mad if you have the right email but wrong password - ab
            // LCRepo checks password with hashed password
        } else {
            // Throw an exception if the login attempt fails
            throw new NoSuchElementException("Login or Password is incorrect");
        }

    }

    /**
     * Registers a new user by creating a new LoginCred instance and hashing the password.
     *
     * @param jsonUser the JSON string containing the user's account and login credential information
     * in the format specified by the API
     * @throws RuntimeException if the account with the given email already exists
     */
    @Override
    public Map<String, Object> registerUser(String jsonUser) {

        // Convert JSON string to LoginCred object
        LoginCred newLogin = convertToObject(jsonUser, LoginCred.class);
        String email  = newLogin.getEmail();

        // Check if account with the given email already exists
        if(!LCrepo.checkLogin(email)){
            String password = newLogin.getPassword();
            // If account does not exist, register user
            String token = LCrepo.hashRegister(email, password);

            Account user = LCrepo.getAccountByEmail(email);
            // Create a response map containing the token and user information
            Map<String,Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user",user);
            return response;
        } else {
            // If account already exists, throw a runtime exception
            throw new RuntimeException("unable to register account, account with this login already exists");
        }
    }

    @Override
    public void changeAccountInfo(String jsonAccount){
        /* TODO: Change Account Info: -TS
         * 
         * 1. Check to make sure the user is logged in
         * 
         * 2. Create an Account from the jsonAccount
         * 
         * 3. Send the Account to the AccountRepo to update accounts
         * 
         * 4. If the Password is being changed, instead of Account use LoginCred
         */
        Account newAccount = convertToObject(jsonAccount, Account.class);
        Accrepo.changeAccountInfo(newAccount);
    }
    ///Maybe a convert from string method here, or several as needed
    
    //TODO: Return List of Users from UserService.searchUsers(searchJson) -TS
    @Override
    public List<Account> searchUsers(String jsonSearch) {
        throw new UnsupportedOperationException("Unimplemented method 'searchUsers'");
    }

    //Not sure on the syntax here (Looks like it works -TS)
    //need a better way to do this, rather than brute forcing
    //wish object mapper would just ignore irrelevant fields while making an object- ab
    @Override
    public <T> T convertToObject(String json, Class <T> returnType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] fields = returnType.getDeclaredFields();
            T newObject = returnType.newInstance();

            map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});

            for (Map.Entry<String, Object> me : map.entrySet()){
                for(Field f : fields){
                    if(f.getName().equals(me.getKey())){
                        f.setAccessible(true);
                        f.set(newObject, me.getValue());
                    }
                }
            }
            return newObject;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}