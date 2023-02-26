package com.revature.repositories;

import com.revature.model.Post;
import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PostsRepo implements PostsInterface {

    @Override
    public void addPost(Post post) {
        String sql = "INSERT INTO posts (userid, postcontent, timeofpost) VALUES (?, ?, ?)";

        try(Connection connection = ConnectionUtil.getConnection()){
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, post.getUserID());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setTimestamp(3, post.gettStamp());

            preparedStatement.execute();

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error inserting post into db from PostsRepo");
        }
    }

    @Override
    public List<Post> getAllPosts() {
        ArrayList<Post> postList = new ArrayList<Post>();
        String sql = "SELECT postid, userid, postcontent, timeofpost FROM posts";

        try (Connection con = ConnectionUtil.getConnection()) {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Post newPost = new Post();
                newPost.setID(rs.getInt("postid"));
                newPost.setUserID(rs.getInt("userid"));
                newPost.setContent(rs.getString("postcontent"));
                newPost.settStamp(rs.getTimestamp("timeofpost"));

                postList.add(newPost);
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error receiving and processing posts from database in PostsRepo");
        }

        return postList;
    }
    

}
