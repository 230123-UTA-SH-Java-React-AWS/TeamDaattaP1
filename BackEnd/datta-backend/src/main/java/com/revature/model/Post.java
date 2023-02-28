package com.revature.model;

public class Post {
    private String content;
    private java.sql.Timestamp tStamp;
    private boolean isLiked;
    private java.sql.Timestamp likeTStamp;
    private int userID;
    private int ID;
    private String userName;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
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
    public java.sql.Timestamp getLikeTStamp() {
        return likeTStamp;
    }
    public void setLikeTStamp(java.sql.Timestamp likeTStamp) {
        this.likeTStamp = likeTStamp;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public java.sql.Timestamp gettStamp() {
        return tStamp;
    }
    public void settStamp(java.sql.Timestamp tStamp) {
        this.tStamp = tStamp;
    }

    public Post(){
        this.content = null;
        this.tStamp = null;
    }
}
