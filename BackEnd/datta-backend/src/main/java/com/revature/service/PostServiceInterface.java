package com.revature.service;

import java.util.List;

import com.revature.model.Post;

public interface PostServiceInterface {
    public int createNewPost(String jsonPost);
    public List<Post> getPostFeed();
    //public void createNewComment(String jsonComment);
}
