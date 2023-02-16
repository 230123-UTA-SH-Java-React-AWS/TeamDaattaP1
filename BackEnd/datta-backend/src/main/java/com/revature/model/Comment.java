package com.revature.model;

public class Comment {
    private String content;
    private java.sql.Date tStamp;
    private boolean isLiked;
    private java.sql.Date likeTStamp;
    //private int id
    //private int postid
    //private int userid

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
