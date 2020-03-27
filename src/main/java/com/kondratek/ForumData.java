package com.kondratek;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class ForumData {

    private Map<Integer,User> users;
    private List<Post> posts;
    Set<String> titles;
    Set<String> nonUniqueTitlesSet;

    public ForumData() {
        users = new HashMap<>();
        posts = new LinkedList<>();
    }

    public void loadUsersFromURL(String url) throws IOException {
        JSONArray jsonArray = JsonReader.readJsonFromUrl(url);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            JSONObject objectGeo = object.getJSONObject("address").getJSONObject("geo");
            User user = new User(object);
            users.put(user.getId(), user);
        }
    }

    public void loadPostsFromUrl(String url) throws IOException {
        JSONArray jsonArray = JsonReader.readJsonFromUrl(url);

        posts = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Post post = new Post(object);

            assignPostWithUser(post);
            checkTitleUniqueness(post.getTitle());
            posts.add(post);
        }
    }

    private void assignPostWithUser(Post post) {
        int userId = post.getUserId();
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            user.addPost(post);
        }
    }

    private void checkTitleUniqueness(String title) {
        if (titles.contains(title)) {
            nonUniqueTitlesSet.add(title);
        } else {
            titles.add(title);
        }
    }

    public List<String> getUsersPostsCount() {
        List<String> countsList = new LinkedList<>();

        for (User user : users.values()) {
            String postsCount = user.getUserName() +
                    " napisał(a) " + user.getPostsCount() +
                    " postów";

            countsList.add(postsCount);
        }
        return countsList;
    }

    public List<String> getNonUniqueTitles() {
        return new LinkedList<>(nonUniqueTitlesSet);
    }

    public Map<Integer, User> getUsers() {
        return users;
    }
}
