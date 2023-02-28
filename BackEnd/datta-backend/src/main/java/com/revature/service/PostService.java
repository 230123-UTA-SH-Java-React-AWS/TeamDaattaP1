package com.revature.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Post;
import com.revature.repositories.PostsRepo;

public class PostService implements PostServiceInterface, ServiceGenerics{

    private PostsRepo postsRepo;

    public PostService(PostsRepo postsRepo){
        this.postsRepo = postsRepo;
    }

    //Communicates with the repo to add a new post to the database
    @Override
    public void createNewPost(String jsonPost){
        System.out.print(" --> Servicing new post");
        Post newPost = convertToObject(jsonPost, Post.class);
        postsRepo.addPost(newPost);
        System.out.print(" --> Post successfully added to database");
    }
    
    @Override
    public List<Post> getPostFeed() {
        // TODO: Filter out all posts that are the current user (userID)
        return postsRepo.getAllPosts();
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
