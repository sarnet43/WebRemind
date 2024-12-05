package com.example.webremind;

public class Post {
    private int id;
    private String title;
    private String userName;
    private String postDate;

    public Post(int id, String title, String userName, String postDate) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.postDate = postDate;
    }

    public int getId() {
        return id;
    }

    public String getDisplayText() {
        return title + "\t\t" + userName + "\t\t" + postDate;
    }

}
