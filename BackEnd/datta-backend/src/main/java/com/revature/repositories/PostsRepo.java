package com.revature.repositories;
import com.revature.util.*;
import com.revature.model.Post;
import java.sql.*;
import java.util.List;
import java.util.LinkedList;
public class PostsRepo {
    private List<Post> Posts = new LinkedList<Post>();
    
    public PostsRepo(){
        this.Posts = new LinkedList<Post>();
        this.FillRepoFromDatabase();
    }

    public void makeNewPost(Post post){
        this.AddPostToDataBase(post);
        this.AddPostToRepo(post);

    }

    //can be expanded upon depending on how we want posts to be displayed
    public List<Post> GetAllPosts(){
        return Posts;//this seems like it might be a bad idea somehow
    } 


//methods to split up database and non-database interactions

    //database
    private void FillRepoFromDatabase(){
        //TODO:add sql
        //select * from ...
    }

    private void AddPostToDataBase(Post post) {
        //TODO: 
        String sql = "insert into post (userid, postcontent, timeofpost ) values(?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);

            //prstmt.setInt(1, post.getuserid); 
            prstmt.setString(2, post.getContent());
            //prstmt.setTimestamp(3, post.gettStamp());
            //date or timestamp?

            prstmt.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //repo
    private void AddPostToRepo(Post post) {
        this.Posts.add(post);
    }
}
