package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.model.Account;
import com.revature.model.Post;
import com.revature.service.PostService;
import com.revature.util.JwtUtil;

import io.javalin.Javalin;

public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    public void mapEndpoints(Javalin app) {


        /* 
         * ================================================================================
         * ----------------------------------- P O S T S ----------------------------------
         * ================================================================================
         */


        // ------------------------------ GET ALL POSTS ------------------------------

        app.get("/post", (context) -> {
            System.out.println("Getting post feed");
            List<Post> postList = postService.getPostFeed();
            // System.out.println(postList.get(1).getUserName());

            if(postList.size() > 0){

                context.json(postList);
                context.status(200);
                
            } else {
                context.result("Error loading posts");
                context.status(500); //Error status  
            }
        });



        // ------------------------------ CREATE A POST ------------------------------

        app.post("/post", (context) -> {
            String postJson = context.body();
            System.out.println(postJson);
            System.out.print("postJson Recieved at Post Controller");

            try{
                int newPostID = postService.createNewPost(postJson);

                context.json(newPostID);
                context.status(201);    // 2xx success - 201 Created
            } catch (Exception e){
                context.result(e.getMessage()); // print exception message
                context.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });



        // ------------------------------ LIKE A POST ------------------------------
        app.put("/post", (context) -> {
            //String postJson = context.body();

            try{
                // TODO: Create PostService.likePost(postJson) -TS
                //postService.likePost(postJson);

                context.result("Post successfully liked.");
                context.status(200);
            } catch (Exception e){
                context.result(e.getMessage());
                context.status(500);
            }
        });




        /* 
         * ================================================================================
         * ------------------------------ C O M M E N T S ---------------------------------
         * ================================================================================
         */


        // ------------------------------ CREATE A COMMENT ------------------------------
        // I have not created a get method for comments because I am assuming they will be served with the posts (see GET ALL POSTS^^)

        // app.post("/comment", (context) -> {
        //     String commentJson = context.body();

        //     try{
        //         postService.createNewComment(commentJson);

        //         context.result("Comment successfully created.");
        //         context.status(201);    // 2xx success - 201 Created
        //     } catch (Exception e){
        //         context.result(e.getMessage()); // print exception message
        //         context.status(500); // 5xx server errors - 500 Internal Server Error
        //     }
        // });
    }
}
