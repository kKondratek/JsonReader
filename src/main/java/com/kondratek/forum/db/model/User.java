package com.kondratek.forum.db.model;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class User {

    private final int id;
    private final String userName;
    private final float lat;
    private final float lng;

    private final List<Post> posts = new LinkedList<>();

    public User(JSONObject jsonObject) {
        id = jsonObject.getInt("id");
        userName = jsonObject.getString("username");

        JSONObject objectGeo = jsonObject.getJSONObject("address").getJSONObject("geo");
        lat = objectGeo.getFloat("lat");
        lng = objectGeo.getFloat("lng");

        // todo handle other fields
    }

    public User(int id, String userName, float lat, float lng) {
        this.id = id;
        this.userName = userName;
        this.lat = lat;
        this.lng = lng;
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

    public List<Post> getPosts() {
        return posts;
    }
}
