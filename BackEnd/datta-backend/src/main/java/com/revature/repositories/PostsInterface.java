package com.revature.repositories;

import java.util.List;

import com.revature.model.Account;
import com.revature.model.Post;

public interface PostsInterface {
    public void likePost(Account user, Post post);
    public List<Post> getLikedPosts(Account user);
    //public List<Post> getPostQueue(Account user);    Use if we have to have the timeline based on friends only
}
