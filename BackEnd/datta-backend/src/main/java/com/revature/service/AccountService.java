package com.revature.service;

public class AccountService {
    //perhaps make a repo in a constructor? or make a static repo? check on that later

    ///Communicates with the repo to check if inputted credentials are in the database
    //Will almost certainly need a return type later
    public void loginUser(String jsonPost){
        ///More shell code
        //Repository repo = new Repository();

        //ObjectMapper mapper = new ObjectMapper();

        /*try {
            User newLogin = mapper.readValue(jsonString, LoginCred.class);
            repo.loginUser(newLogin);
        } */

    }

    public void registerUser(String jsonUser){
        //Skeleton
    }

    public void changeAccountInfo(String jsonAccount){
        //Skeleton
    }
    ///Maybe a convert from string method here, or several as needed
}