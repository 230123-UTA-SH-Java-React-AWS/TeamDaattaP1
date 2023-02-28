package com.revature;

import com.revature.controller.PostController;
import com.revature.controller.AccountController;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;
import com.revature.repositories.PostsRepo;
import com.revature.service.AccountService;
import com.revature.service.PostService;

import io.javalin.Javalin;
public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception{

        Javalin app = Javalin.create(config -> {
            config.enableCorsForOrigin("http://localhost:3000");
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
