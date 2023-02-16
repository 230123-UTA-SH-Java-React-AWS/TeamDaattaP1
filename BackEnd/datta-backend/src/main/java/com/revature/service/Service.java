package com.revature.service;

public class Service {
    //perhaps make a repo in a constructor? or make a static repo? check on that later

    ///Communicates with the repo to add a new post to the database
    //Might need a return type later, for now only void
    public void createNewPost(String jsonPost){
        ///Shell code for now, commented out to prevent compiler yelling at us
        //Repository repo = new Repository();

        //ObjectMapper mapper - new ObjectMapper();

        /*try {
            Post newPost = mapper.readValue(jsonString, Post.class);
            repo.savePostMethodName(newPost)     will likely need username/userid as well, to tie to foreign key
        } catch block here
         */
    }

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

    ///Maybe a convert from string method here, or several as needed
}