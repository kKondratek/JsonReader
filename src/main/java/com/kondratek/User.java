package com.kondratek;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class User {

    private int id;
    private String userName;
    private float lat;
    private float lng;
    private User nearestUser;

    private List<Post> userPosts;

    public User(JSONObject jsonObject) {
        parseJson(jsonObject);
        userPosts = new LinkedList<>();
    }

    public void addPost(Post post) {
        userPosts.add(post);
    }

    public void setNearestUser(User user) {

    }

    private void parseJson(JSONObject jsonObject) {
        id = jsonObject.getInt("id");
        userName = jsonObject.getString("username");
        lat = jsonObject.getFloat("lat");
        lng = jsonObject.getFloat("lng");
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public int getPostsCount() {
        return userPosts.size();
    }

}
