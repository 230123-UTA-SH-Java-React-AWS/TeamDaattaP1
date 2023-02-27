package com.revature.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.revature.middleware.ProfanityFilterMiddleware;
import com.revature.model.Account;
import com.revature.model.Post;
import com.revature.service.PostService;

import io.javalin.Javalin;

public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    private final Set<String> bannedWords = new HashSet<>(Arrays.asList("badword1", "badword2", "badword3"));
    public void mapEndpoints(Javalin app) {


        /* 
         * ================================================================================
         * ----------------------------------- P O S T S ----------------------------------
         * ================================================================================
         */

        // apply profanity filter to all requests on the /post endpoint
        // currently doesnt stop the post from being created. does filter it tho
//        app.before("/post", new ProfanityFilterMiddleware());


        // ------------------------------ GET ALL POSTS FOR USER ------------------------------

        app.get("/post", (context) -> {
            // get the logged in user
            HttpSession httpSession = context.req.getSession();
            Account user = (Account) httpSession.getAttribute("user");

            //Delete this later and bring back the else
            user = new Account();


            //check if user is logged in
            if(user != null) {
                System.out.println("Getting post feed");
                List<Post> postList = postService.getPostFeed();

                context.json(postList);
                context.status(200);
                
            // } else {
            //     context.result("You are not logged in");
            //     context.status(401); //Error status  
            }
        });



        // ------------------------------ CREATE A POST ------------------------------

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
