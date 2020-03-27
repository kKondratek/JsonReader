package com.kondratek.forum.db;

import com.kondratek.forum.db.model.Post;
import com.kondratek.forum.db.model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ForumDb {

    private final List<User> users = new LinkedList<>();

    public List<User> getUsers() {
        return users;
    }

    /**
     * @return user of given id if exists
     * otherwise null
     */
    public User getUserById(int id) {
        Optional<User> userOptional = users.stream().filter(u -> u.getId() == id).findFirst();
        return userOptional.orElse(null);
    }

    /**
     * @throws UserNotFoundException if no with userId specified by a post does not exists in database
     */
    public void assignPostToUser(Post post) throws UserNotFoundException {
        User user = getUserById(post.getUserId());
        if (user == null) {
            throw new UserNotFoundException(post.getUserId());
        }

        user.getPosts().add(post);
    }

    public void addUsers(List<User> newUsers) {
        // todo check user id uniqueness if required
        users.addAll(newUsers);
    }
}
