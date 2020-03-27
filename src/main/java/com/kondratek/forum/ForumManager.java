package com.kondratek.forum;

import com.kondratek.JsonReader;
import com.kondratek.forum.db.ForumDb;
import com.kondratek.forum.db.UserNotFoundException;
import com.kondratek.forum.db.model.Post;
import com.kondratek.forum.db.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ForumManager {

    private final ForumDb forumDb = new ForumDb();

    public void fetchUsersFromURL(String url) throws IOException {
        JSONArray jsonArray = JsonReader.readJsonArrayFromUrl(url);

        List<User> users = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            User user = new User(object);
            users.add(user);
        }

        forumDb.addUsers(users);
    }

    /**
     * Fetches posts from URL and assigns them to existing users.
     */
    public void fetchUsersPostsFromUrl(String url) throws IOException, UserNotFoundException {
        JSONArray jsonArray = JsonReader.readJsonArrayFromUrl(url);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Post post = new Post(object);

            forumDb.assignPostToUser(post);
        }
    }

    public List<String> getUsersPostsCount() {
        return forumDb.getUsers().stream()
                .map(user -> String.format("%s napisał(a) %d postów", user.getUserName(), user.getPosts().size()))
                .collect(Collectors.toList());
    }

    public Set<String> getNonUniqueTitles() {
        List<String> titles = forumDb.getUsers().stream()
                .map(User::getPosts)
                .flatMap(Collection::stream)
                .map(Post::getTitle)
                .collect(Collectors.toList());

        return titles.stream()
                .filter(t -> Collections.frequency(titles, t) > 1)
                .collect(Collectors.toSet());
    }

    /**
     * @return user of given id if exists
     * otherwise null
     */
    public User getUserById(int id) {
        return forumDb.getUserById(id);
    }

    public List<User> getUsers() {
        return forumDb.getUsers();
    }
}
