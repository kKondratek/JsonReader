package com.kondratek.forum.db.model;

import org.json.JSONObject;

public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    public Post(JSONObject jsonObject) {
        userId = jsonObject.getInt("userId");
        id = jsonObject.getInt("id");
        title = jsonObject.getString("title");
        body = jsonObject.getString("body");
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "userId: " + userId +
                "\nid: " + id +
                "\ntitle: " + title +
                "\nbody: " + body;
    }

    public int getId() {
        return id;
    }
}
