package com.revature.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import com.revature.model.Account;
import com.revature.service.AccountService;

import io.javalin.Javalin;

public class UserController{
    
    private AccountService userService = new AccountService();

    // --------------- UserController - handles user login, logout, and register HTTP requests ---------------
    public void mapEndpoints(Javalin app) {

        // ------------------------------ REGISTER A NEW USER ------------------------------

        app.post("/register", (context) ->{
            String userJson = context.body();

            // Try creating the new Account
            try{
                userService.registerUser(userJson); // TODO: gives an exception?

                context.result("Account successfully created.");
                context.status(201);    // 2xx success - 201 Created
            } catch (Exception e){
                context.result(e.getMessage()); // print exception message
                context.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });



        // ------------------------------ LOGIN EXISTING USER ------------------------------

        app.post("/login", (context) ->{

            // System.out.println(credentials);

            try{
                Account user = userService.loginUser(context.body());

                // set the user object into an HTTPSession object TODO: Teagan, make this into a JWT Token instead -TS
                HttpSession session = context.req.getSession(); // get the HTTPSession (there is a cookie utilized by the client)
                // to identify the httpSession object associated with the client
                session.setAttribute("user", user);

                context.result("Welcome " + user.getFirstName() + " " + user.getLastName());
                context.json(user);
                context.status(200);
            } catch (NoSuchElementException e){//login or password was incorrect
                context.status(404);
                context.result(e.getMessage());
            }
            catch (Exception e){
                context.status(400);
                context.result(e.getMessage());
            }
        });



        // ------------------------------ LOGOUT USER (IF LOGGED IN) ------------------------------

        app.post("/logout", (context) ->{
            // invalidate an active HTTPSession
            context.req.getSession().invalidate(); // TODO: JWT Token pt.2
            context.result("Logged out account.");
            context.status(200);
        });


        
        // // ------------------------------ SEARCH FOR OTHER PEOPLE ------------------------------

        app.get("/users", (context) ->{
            String searchJson = context.body();

            HttpSession httpSession = context.req.getSession();
            Account user = (Account) httpSession.getAttribute("user");

            //check if user is logged in
            if(user != null) {
                // Try searching for accounts like 'searchJson'
                List<Account> userList = userService.searchUsers(searchJson);

                context.json(userList);
                context.status(200);
            } else {
                context.result("You are not logged in");
                context.status(401); //Error status  
            }
        });
    }
}
