package com.revature.model;

public class Post {
    private String content;
    private java.sql.Date tStamp;
    private boolean isLiked;
    private java.sql.Date likeTStamp;
    //private int userid
    //private int id

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

    public Post(){
        this.content = null;
        this.tStamp = null;
    }
}
