package com.revature.service;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;

public class AccountService implements AccountServiceInterface, ServiceGenerics{
    //perhaps make a repo in a constructor? or make a static repo? check on that later
    //static repos are what makes sense to me -DP
    //I agree on the static repo, just feels better -AB
    private static LoginCredsRepo LCrepo = new LoginCredsRepo();
    private static AccountsRepo Accrepo = new AccountsRepo();

    ///Communicates with the repo to check if inputted credentials are in the database
    //Will almost certainly need a return type later
    @Override
    public Account loginUser(String jsonLogin){ //We can throw an exception to UserController here -TS
        ///More shell code
        Account response = new Account(); // Dummy Account TODO: remove constructor once Login() returns Account -TS
    
        LoginCred newLoginCred = convertToObject(jsonLogin, LoginCred.class);
        // TODO: Receive Account from LoginCredsRepo.Login() -TS
        //In theory it, works like this instead:
        int id = LCrepo.Login(newLoginCred);
        response = Accrepo.getAccount(id);
        //is that's ok? -DP
        return response;
    }

    @Override
    public void registerUser(String jsonUser){
        /* TODO: Register User: -TS
         * 
         * 1. Check to make sure the email is not already registered
         * 
         * 2. Create an Account from the jsonUser
         * 
         * 3. Send the Account to the AccountRepo to be stored in the accounts table
         * 
         * 4. Create a LoginCred fromt he username and password
         * 
         * 5. Send the LoginCred to the LoginCredsRepo to be stored in the logincredentials table
         */
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
    }
    ///Maybe a convert from string method here, or several as needed

    @Override
    public <T> T convertToObject(String json, Class<T> returnType) {
        ObjectMapper mapper = new ObjectMapper();
        T newObject = null;

        try {
            newObject = mapper.readValue(json, returnType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newObject;
    }
}