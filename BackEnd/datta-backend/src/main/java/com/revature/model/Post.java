package com.revature.model;

public class Post {
    private String content;
    private java.sql.Date tStamp;
    //private int userid
    //private int id

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
