package com.revature.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import com.revature.model.Account;
import com.revature.service.AccountService;

import io.javalin.Javalin;

public class AccountController{
    
    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    // --------------- UserController - handles user login, logout, and register HTTP requests ---------------
    public void mapEndpoints(Javalin app) {
        // ------------------------------ REGISTER A NEW USER ------------------------------

        app.post("/register", (context) ->{
            String userJson = context.body();

            // Try creating the new Account
            try{
                Map<String, Object> response = accountService.registerUser(userJson); // TODO: gives an exception?

                // Set the user object into an HTTPSession object if needed.
                HttpSession session = context.req.getSession();
                session.setAttribute("user", response.get("user"));
                
                context.header("Authorization","" + response.get("token"));

                // context.result("Account successfully created.");
                context.json(response.get("user"));
                context.status(201);    // 2xx success - 201 Created
            } catch (RuntimeException e){
                context.result(e.getMessage()); // print exception message
                context.status(409); // 4xx client errors - 409 Conflict
            } catch (Exception e){
                context.result(e.getMessage()); // print exception message
                context.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });



        // ------------------------------ LOGIN EXISTING USER ------------------------------

        app.post("/login", (context) -> {

            try {
                // Call the `loginUser` method of the `accountService` object and pass the request body as an argument.
                Map<String, Object> response = accountService.loginUser(context.body());

                // Set the user object into an HTTPSession object if needed.
                HttpSession session = context.req.getSession();
                session.setAttribute("user", response.get("user"));

                // Add the token to the Authorization header of the response.
                context.header("Authorization","" +response.get("token"));

                // Set the response body to the user object and return 200 OK.
                context.json(response.get("user"));
                context.status(200);
            } catch (NoSuchElementException e) {
                // If the `loginUser` method throws a `NoSuchElementException`, return 404 Not Found.
                context.status(404);
                context.result(e.getMessage());
            } catch (Exception e) {
                // If any other exception is thrown, return 400 Bad Request.
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
                List<Account> userList = accountService.searchUsers(searchJson);

                context.json(userList);
                context.status(200);
            } else {
                context.result("You are not logged in");
                context.status(401); //Error status  
            }
        });
    }
}
