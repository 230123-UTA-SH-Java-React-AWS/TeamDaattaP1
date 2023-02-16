package com.revature.service;

import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.AccountRepo;

public class Service {
    //perhaps make a repo in a constructor? or make a static repo? check on that later

    ///Communicates with the repo to check if inputted credentials are in the database
    //Will almost certainly need a return type later
    public Account loginUser(String jsonLogin){
        ///More shell code
        Account response;
        AccountRepo repo = new AccountRepo();

        ObjectMapper mapper = new ObjectMapper();

        try {
            LoginCred newLogin = mapper.readValue(jsonLogin, LoginCred.class);
            response = repo.Login(newLogin.getEmail(), newLogin.getPassword());
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