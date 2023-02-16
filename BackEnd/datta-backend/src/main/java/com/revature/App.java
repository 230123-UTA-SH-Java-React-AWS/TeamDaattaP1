package com.revature;

import com.revature.controller.UserController;

// import java.net.InetSocketAddress;

// import com.revature.controller.PostController;
// import com.sun.net.httpserver.HttpServer;

import io.javalin.Javalin;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception{

        Javalin app = Javalin.create(/*config*/)
            .get("/", ctx -> ctx.result("Hello World"))
            .start(8000);

        UserController loginController = new UserController();
        loginController.mapEndpoints(app);
    }
}
