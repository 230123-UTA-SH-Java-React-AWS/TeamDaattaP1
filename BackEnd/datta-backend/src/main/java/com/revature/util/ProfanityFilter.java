package com.revature.util;

import com.revature.model.Post;
import io.javalin.http.Context;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProfanityFilter {

    private final Set<String> bannedWords = new HashSet<>(Arrays.asList("badword1", "badword2", "badword3"));

    public void filterProfanity(Context ctx) {
        String requestBody = ctx.body();
        Post newPost = convertToObject(requestBody, Post.class);
        String postContent = newPost.getContent();
        for (String bannedWord : bannedWords) {

            if (postContent.toLowerCase().contains(bannedWord.toLowerCase())) {
                ctx.status(403); // Forbidden
                ctx.result("Request contains banned word");
            }
        }

    }

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