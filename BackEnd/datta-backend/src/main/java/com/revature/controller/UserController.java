package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpSession;

import io.javalin.Javalin;

public class UserController{
    
    //private UserService = new Service();

    private void mapEndpoints(Javalin app){
        app.post("/login", (ctx) ->{
            // take the JSON in the request body and place it into the user Object
            AppUserAccount credentials = ctx.bodyStreamAsClass(AppUserAccount.class);

            // System.out.println(credentials);

            try{
                AppUserAccount user = appAuthService.login(credentials.getUsername(), credentials.getPassword());

                // set the user object into an HTTPSession object
                HttpSession session = ctx.req.getSession(); // get the HTTPSession (there is a cookie utilized by the client)
                // to identify the httpSession object associated with the client
                session.setAttribute("user", user);

                ctx.result("Welcome " + user.getFirstName() + " " + user.getLastName());
                ctx.status(200);
            }
            catch (InvalidAccountLoginException e){
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });
    }
}
