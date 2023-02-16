package com.revature.controller;

import com.revature.service.Service;


import io.javalin.Javalin;

public class UserController{
    
    private Service userService = new Service();

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
    }
}
