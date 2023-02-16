package com.revature.controller;

import javax.servlet.http.HttpSession;

import com.revature.model.Account;
import com.revature.service.PostService;

import io.javalin.Javalin;

public class PostController {

    private PostService postService = new PostService();

    public void mapEndpoints(Javalin app) {
        app.get("/post", (context) ->{
            // get the logged in user
            HttpSession httpSession = context.req.getSession();
            Account user = (Account) httpSession.getAttribute("user");

            //check if user is logged in
            if(user != null) {
                //TODO: Return List of Posts from PostService.getAllPosts();
                //List<Post> postList = postService.getAllPosts();

                //context.json(postList);
                context.status(200);
            } else {
                context.result("You are not logged in");
                context.status(401); //Error status
            }
        });

        app.post("/post", (context) -> {
            String postJson = context.body();

            try{
                postService.createNewPost(postJson);

                context.result("Post successfully created.");
                context.status(201);    // 2xx success - 201 Created
            } catch (Exception e){
                context.result(e.getMessage()); // print exception message
                context.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });

        app.post("/comment", (context) -> {
            String commentJson = context.body();

            try{
                postService.createNewComment(commentJson);

                context.result("Comment successfully created.");
                context.status(201);    // 2xx success - 201 Created
            } catch (Exception e){
                context.result(e.getMessage()); // print exception message
                context.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });
    }
}
