package com.revature;

import com.revature.controller.PostController;
import com.revature.controller.AccountController;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;
import com.revature.repositories.PostsRepo;
import com.revature.service.AccountService;
import com.revature.service.PostService;

// import java.net.InetSocketAddress;

// import com.revature.controller.PostController;
// import com.sun.net.httpserver.HttpServer;

import io.javalin.Javalin;
public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception{

        Javalin app = Javalin.create(config -> {
//            config.enableCorsForAllOrigins();
//            config.enableCorsForOrigin("http://localhost:3000");
        });

        app.before((ctx) -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept");
        });

        app.options("/*", (ctx) -> {
            ctx.status(204);
            ctx.result("");
        });
        // to allow the jwt token to get to the frontend, some cors thing
        app.before(ctx -> ctx.header("Access-Control-Expose-Headers", "Authorization"));
        AccountService accountService = new AccountService(new LoginCredsRepo(), new AccountsRepo());
        AccountController userController = new AccountController(accountService);
        userController.mapEndpoints(app);

        PostController postController =  new PostController(new PostService(new PostsRepo()));
        postController.mapEndpoints(app);

        app.start(8000);
    }

}
