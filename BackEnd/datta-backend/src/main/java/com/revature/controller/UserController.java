package com.revature.controller;

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
                userService.registerUser(userJson);

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
                // TODO: Dummy User Value - loginUser return Account
                Account user = new Account(); 
                user.setFirstName("default");
                user.setLastName("user");
                // Account user = userService.loginUser(context.body());

                // set the user object into an HTTPSession object
                HttpSession session = context.req.getSession(); // get the HTTPSession (there is a cookie utilized by the client)
                // to identify the httpSession object associated with the client
                session.setAttribute("user", user);

                context.result("Welcome " + user.getFirstName() + " " + user.getLastName());
                context.status(200);
            }
            catch (Exception e){
                context.status(400);
                context.result(e.getMessage());
            }
        });



        // ------------------------------ LOGOUT USER (IF LOGGED IN) ------------------------------

        app.post("/logout", (context) ->{
            // invalidate an active HTTPSession
            context.req.getSession().invalidate();
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

                //TODO: Return List of Users from UserService.searchUsers(searchJson);
                //List<User> userList = userService.searchUsers(searchJson);

                //context.json(userList);
                context.status(200);
            } else {
                context.result("You are not logged in");
                context.status(401); //Error status  
            }
        });
    }
}
