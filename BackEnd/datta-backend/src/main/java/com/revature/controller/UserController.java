package com.revature.controller;

import javax.servlet.http.HttpSession;

import com.revature.model.Account;
import com.revature.service.Service;

import io.javalin.Javalin;

public class UserController{
    // service layer
    private Service userService = new Service();

    // UserController - handles user login, logout, and register HTTP requests
    public void mapEndpoints(Javalin app) {
        app.post("/register", (ctx) ->{
            String userJson = ctx.body();

            // Try creating the new Account
            try{
                userService.registerUser(userJson);

                ctx.result("Account successfully created.");
                ctx.status(201);    // 2xx success - 201 Created
            } catch (Exception e){
                ctx.result(e.getMessage()); // print exception message
                ctx.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });

        // login HTTP request - to enter an user account
        app.post("/login", (ctx) ->{

            // System.out.println(credentials);

            try{
                Account user = new Account(); // TODO: Dummy User Value - loginUser return Account
                //Account user = userService.loginUser(ctx.body());

                // set the user object into an HTTPSession object
                HttpSession session = ctx.req.getSession(); // get the HTTPSession (there is a cookie utilized by the client)
                // to identify the httpSession object associated with the client
                session.setAttribute("user", user);

                ctx.result("Welcome " + user.getFirstName() + " " + user.getLastName());
                ctx.status(200);
            }
            catch (Exception e){
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });

        // logout HTTP request - to exit logged-in user account
        app.post("/logout", (ctx) ->{
            // invalidate an active HTTPSession
            ctx.req.getSession().invalidate();
            ctx.result("Logged out account.");
            ctx.status(200);
        });
    }
}
