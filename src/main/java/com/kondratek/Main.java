package com.kondratek;

import com.kondratek.forum.ForumManager;
import com.kondratek.forum.UserDistanceManager;
import com.kondratek.forum.db.UserNotFoundException;
import com.kondratek.forum.db.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, UserNotFoundException {

        ForumManager forumManager = new ForumManager();

        // reading users
        forumManager.fetchUsersFromURL("https://jsonplaceholder.typicode.com/users");

        // reading posts
        forumManager.fetchUsersPostsFromUrl("https://jsonplaceholder.typicode.com/posts");

        // list of users with their post count
        List<String> usersWithPostsCount = forumManager.getUsersPostsCount();
        usersWithPostsCount.forEach(System.out::println);

        // duplicated posts titles
        Set<String> nonUniqueTitles = forumManager.getNonUniqueTitles();
        if (!nonUniqueTitles.isEmpty()) {
            System.out.println("\nPost titles with duplicates: ");
            nonUniqueTitles.forEach(System.out::println);
            System.out.println();
        } else {
            System.out.println("\nNo duplicate posts titles detected\n");
        }

        // finding nearest user for each user
        Map<User, User> userUserMap = UserDistanceManager.computeUserNearestUserMap(forumManager.getUsers());
        userUserMap.entrySet().stream()
                .map(e -> userToUserGeoSting(e.getKey()) + " | " + userToUserGeoSting(e.getValue()))
                .forEach(System.out::println);
    }

    private static String userToUserGeoSting(User user) {
        return String.format("(id = %d, username = %s, lat = %f, lng = %f)",
                user.getId(), user.getUserName(), user.getLat(), user.getLng());
    }
}

