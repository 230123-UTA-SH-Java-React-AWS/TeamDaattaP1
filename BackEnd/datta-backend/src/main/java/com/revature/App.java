package com.revature;

import com.revature.controller.PostController;
import com.revature.controller.UserController;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;
import com.revature.service.AccountService;

// import java.net.InetSocketAddress;

// import com.revature.controller.PostController;
// import com.sun.net.httpserver.HttpServer;

import io.javalin.Javalin;
public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception{

        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins(); // add this line to enable CORS for all origins
        });

        AccountService accountService = new AccountService(new LoginCredsRepo(), new AccountsRepo());
        UserController userController = new UserController(accountService);
        userController.mapEndpoints(app);

        PostController postController =  new PostController();
        postController.mapEndpoints(app);

        app.start(8000);
    }

}
