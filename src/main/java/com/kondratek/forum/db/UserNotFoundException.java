package com.kondratek.forum.db;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(int id) {
        super("No user with id: " + id);
    }
}
