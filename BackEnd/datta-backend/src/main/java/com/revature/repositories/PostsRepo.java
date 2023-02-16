package com.revature.repositories;
import java.sql.*;
import java.util.List;
import java.util.LinkedList;
public class PostsRepo {
    //replace object with the post model object type
    private List<Object> Posts = new LinkedList<Object>();
    
    public PostsRepo(){
        this.Posts = new LinkedList<Object>();
        this.FillRepoFromDatabase();
    }

    public void makeNewPost(Object post){
        this.AddPostToDataBase(post);
        this.AddPostToRepo(post);

    }

    //can be expanded upon depending on how we want posts to be displayed
    public List<Object> GetAllPosts(){
        return Posts;//this seems like it might be a bad idea somehow
    } 


//methods to split up database and non-database interactions

    //database
    private void FillRepoFromDatabase(){
        //TODO:
        //select * from ...
    }

    private void AddPostToDataBase(Object post) {
        //TODO:
        //insert into ...
    }

    //repo
    private void AddPostToRepo(Object post) {
        this.Posts.add(post);
    }
}
