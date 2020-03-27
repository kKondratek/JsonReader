package com.kondratek.forum;

import com.kondratek.forum.db.model.User;

import java.util.*;

public class UserDistanceManager {

    /**
     * Computes nearest user for each user based on theirs geo locations.
     * If only one user is provided the resulting map will be empty.
     */
    public static Map<User, User> computeUserNearestUserMap(List<User> users) {
        if (users.size() < 2) {
            return Collections.emptyMap();
        }

        Map<User, User> userToNearestUser = new HashMap<>();

        for (User user : users) {
            Optional<User> nearestUser = users.stream()
                    .filter(u -> u != user)
                    .min(Comparator.comparingDouble(o -> getSquaredDistance(user, o)));


            assert nearestUser.isPresent();
            userToNearestUser.put(user, nearestUser.get());
        }

        return userToNearestUser;
    }

    /**
     * Returns square of real distance
     */
    private static double getSquaredDistance(User userOne, User userTwo) {
        float a = userOne.getLat() - userTwo.getLat();
        float b = userOne.getLng() - userTwo.getLng();
        return a * a + b * b;
    }
}

