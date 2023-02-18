package com.revature.service;

import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.LoginCredsRepo;

public class Service {
    //perhaps make a repo in a constructor? or make a static repo? check on that later

    ///Communicates with the repo to check if inputted credentials are in the database
    //Will almost certainly need a return type later
    public Account loginUser(String jsonLogin){
        ///More shell code
        Account response = new Account(); // Dummy Account (TODO: remove constructor once Login() is fixed)
        LoginCredsRepo repo = new LoginCredsRepo();

        ObjectMapper mapper = new ObjectMapper();

        try {
            LoginCred newLoginCred = mapper.readValue(jsonLogin, LoginCred.class);
            // TODO: Receive Account from LoginCredsRepo.Login()
            //response = repo.Login(newLoginCred);
        } catch (Exception e) {
            // TODO: handle exception
        } 

        return response;
    }

    public void registerUser(String jsonUser){
        //Skeleton
    }

    ///Maybe a convert from string method here, or several as needed
}