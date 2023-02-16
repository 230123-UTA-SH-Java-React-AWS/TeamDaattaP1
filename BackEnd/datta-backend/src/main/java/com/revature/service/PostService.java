package com.revature.service;

public class PostService {
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

    public void createNewComment(String jsonComment){
        //Skeleton
    }
}
