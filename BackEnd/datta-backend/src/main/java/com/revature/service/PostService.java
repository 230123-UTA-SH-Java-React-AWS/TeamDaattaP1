package com.revature.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Post;
import com.revature.repositories.PostsRepo;

public class PostService implements PostServiceInterface, ServiceGenerics{

    private PostsRepo postsRepo;
    private final Set<String> bannedWords = new HashSet<>(Arrays.asList("badword1", "badword2", "badword3"));

    public PostService(PostsRepo postsRepo){
        this.postsRepo = postsRepo;
    }

    //Communicates with the repo to add a new post to the database
    @Override
    public void createNewPost(String jsonPost){

        Post newPost = convertToObject(jsonPost, Post.class);
        // get the post content
        String postContent = newPost.getContent().toLowerCase();
        // check for bad words
        for (String bannedWord : bannedWords) {
            // if profanity is present, throw exception, else add the post
            if (postContent.contains(bannedWord.toLowerCase())) {
                throw new RuntimeException("Post contains profanity");
            }else{
                postsRepo.addPost(newPost);
            }
        }

        
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
