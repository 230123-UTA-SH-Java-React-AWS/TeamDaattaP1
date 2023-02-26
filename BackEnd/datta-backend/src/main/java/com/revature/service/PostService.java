package com.revature.service;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class PostService implements PostServiceInterface, ServiceGenerics{
    ///Communicates with the repo to add a new post to the database
    //Might need a return type later, for now only void
    @Override
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

    @Override
    public void createNewComment(String jsonComment){
        //Skeleton
    }

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
