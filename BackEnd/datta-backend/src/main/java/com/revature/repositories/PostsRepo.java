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
        System.out.print(" --> Post added to repo");
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
        System.out.println("Retrieving Posts from DB");
        ArrayList<Post> postList = new ArrayList<Post>();
        String sql = "SELECT a.firstname, a.lastname, p.postid, p.userid, p.postcontent, p.timeofpost FROM posts p INNER JOIN accounts a ON a.accountid = p.userid";

        try (Connection con = ConnectionUtil.getConnection()) {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Post newPost = new Post();
                newPost.setUserName(rs.getString("firstname") + " " + rs.getString("lastname"));
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
    
    public Post getLastPost() {
        Post lastPost = new Post();
        String sql = "SELECT * FROM posts p ORDER BY p.postid DESC LIMIT 1";

        try (Connection con = ConnectionUtil.getConnection()) {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                lastPost.setID(rs.getInt("postid"));
                lastPost.setUserID(rs.getInt("userid"));
                lastPost.setContent(rs.getString("postcontent"));
                lastPost.settStamp(rs.getTimestamp("timeofpost"));
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error receiving and processing posts from database in PostsRepo");
        }

        return lastPost;
    }
}
