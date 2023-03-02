package com.revature.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.revature.model.Post;
import com.revature.repositories.PostsRepo;

public class PostService implements PostServiceInterface, ServiceGenerics{

    private PostsRepo postsRepo;
    private final Set<String> bannedWords = new HashSet<>();

    public PostService(PostsRepo postsRepo){
        this.postsRepo = postsRepo;
    }

    //Communicates with the repo to add a new post to the database
    @Override
    public void createNewPost(String jsonPost){
        //read bad words from file
        InputStream inputStream = getClass().getResourceAsStream("/badwords.txt");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] words = line.split(",");
            bannedWords.addAll(Arrays.asList(words));
        }
        scanner.close();

        Post newPost = convertToObject(jsonPost, Post.class);
        // get the post content
        String postContent = newPost.getContent().toLowerCase();
        // check for bad words
        for (String bannedWord : bannedWords) {
            // if profanity is present, throw exception, else add the post
            if (postContent.contains(bannedWord.toLowerCase())) {
                throw new RuntimeException("Post contains profanity");
            }
        }
        postsRepo.addPost(newPost);

        
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
