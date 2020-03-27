package com.kondratek;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        ForumData data = new ForumData();
        //reading users
        data.loadUsersFromURL("https://jsonplaceholder.typicode.com/users");

        //reading posts
        data.loadPostsFromUrl("https://jsonplaceholder.typicode.com/posts");

        //task 1, list users with posts count
        List<String> usersWithPostsCount = data.getUsersPostsCount();

        //task 2
        List<String> nonUniqueTitles = data.getNonUniqueTitles();

        //task 3
        DistanceManager.setNearestUsers(data.getUsers());
        }

}

