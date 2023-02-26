package com.revature.model;

public class Comment {
    private String content;
    private java.sql.Date tStamp;
    private boolean isLiked;
    private java.sql.Date likeTStamp;
    private int ID;
    private int postID;
    private int userID;

    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getPostID() {
        return postID;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public boolean isLiked() {
        return isLiked;
    }
    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
    public java.sql.Date getLikeTStamp() {
        return likeTStamp;
    }
    public void setLikeTStamp(java.sql.Date likeTStamp) {
        this.likeTStamp = likeTStamp;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public java.sql.Date gettStamp() {
        return tStamp;
    }
    public void settStamp(java.sql.Date tStamp) {
        this.tStamp = tStamp;
    }
    
    public Comment(){
        this.content = null;
        this.tStamp = null;
    }
}
