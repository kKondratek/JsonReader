package com.kondratek;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DistanceManager {

    public static void setNearestUsers(Map<Integer ,User> users) {
        for (User user : users.values()) {
            Set<User> otherUsers = new HashSet<>(users.values());
            otherUsers.remove(user);
            double minDist = -1.0;
            double currentDist;
            User nearest = null;

            for (User other : otherUsers) {
                currentDist = getSquaredDistance(user, other);
                if (minDist < 0) {
                    minDist = currentDist;
                    nearest = other;

                } else {
                    if (minDist > currentDist) {
                        minDist = currentDist;
                        nearest = other;
                    }
                }
            }

            user.setNearestUser(nearest);
        }
    }

    private static double getSquaredDistance(User userOne, User userTwo) {
        float latOne = userOne.getLat();
        float latTwo = userTwo.getLat();
        float lngOne = userOne.getLng();
        float lngTwo = userTwo.getLng();
        return (latOne - latTwo) * (latOne - latTwo)
                + (lngOne - lngTwo) * (lngOne - lngTwo);
    }
}

