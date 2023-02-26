package com.revature.repositories;
import com.revature.util.*;
import com.revature.model.Post;
import java.sql.*;
import java.util.List;
import java.util.LinkedList;
public class PostsRepo {
    private List<Post> AllPosts = new LinkedList<Post>();
    
    public PostsRepo(){
        this.AllPosts = new LinkedList<Post>();
        this.FillRepoFromDatabase();
    }

    public void makeNewPost(Post post){
        this.AddPostToDataBase(post);
        this.AddPostToRepo(post);

    }

    //can be expanded upon depending on how we want posts to be displayed
    public List<Post> GetAllPosts(){
        return AllPosts;//this seems like it might be a bad idea somehow
    } 


//methods to split up database and non-database interactions

    //database
    private void FillRepoFromDatabase(){
        String sql = "select * from posts";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                Post newpost = new Post();
                //keys aren't implemented into the model yet
                //newpost.setPostID(result.getInt(1))
                //newpost.setUserID(result.getInt(2))
                newpost.setContent(result.getString(3));
                newpost.settStamp(result.getTimestamp(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AddPostToDataBase(Post post) {
        String sql = "insert into posts (userid, postcontent, timeofpost ) values(?, ?, ?)";
        try (Connection con = ConnectionUtil.getConnection()){
            PreparedStatement prstmt = con.prepareStatement(sql);
            //keys aren't implemented into the model yet
            //prstmt.setInt(1, post.getuserid); 
            prstmt.setString(2, post.getContent());
            prstmt.setTimestamp(3, post.gettStamp());
            //date or timestamp?

            prstmt.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //repo
    private void AddPostToRepo(Post post) {
        this.AllPosts.add(post);
    }
}
