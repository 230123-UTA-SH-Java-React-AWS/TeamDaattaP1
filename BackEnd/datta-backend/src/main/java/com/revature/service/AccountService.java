package com.revature.service;

import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.LoginCredsRepo;

public class AccountService {
    //perhaps make a repo in a constructor? or make a static repo? check on that later

    ///Communicates with the repo to check if inputted credentials are in the database
    //Will almost certainly need a return type later
    public Account loginUser(String jsonLogin) throws Exception{ //We can throw an exception to UserController here -TS
        ///More shell code
        Account response = new Account(); // Dummy Account TODO: remove constructor once Login() returns Account -TS
        LoginCredsRepo repo = new LoginCredsRepo();

        ObjectMapper mapper = new ObjectMapper();

    
        LoginCred newLoginCred = mapper.readValue(jsonLogin, LoginCred.class);
        // TODO: Receive Account from LoginCredsRepo.Login() -TS
        //response = repo.Login(newLoginCred);

        return response;
    }

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

    public void changeAccountInfo(String jsonAccount){
        //Skeleton
    }
    ///Maybe a convert from string method here, or several as needed
}