package com.revature;

import com.revature.controller.PostController;
import com.revature.controller.AccountController;

// import java.net.InetSocketAddress;

// import com.revature.controller.PostController;
// import com.sun.net.httpserver.HttpServer;

import io.javalin.Javalin;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception{

        Javalin app = Javalin.create();

        AccountController userController = new AccountController();
        userController.mapEndpoints(app);

        PostController postController =  new PostController();
        postController.mapEndpoints(app);

        app.start(8000);
    }
}
