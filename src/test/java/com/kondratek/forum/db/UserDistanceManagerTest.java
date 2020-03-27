package com.kondratek.forum.db;

import com.kondratek.forum.UserDistanceManager;
import com.kondratek.forum.db.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserDistanceManagerTest {

    private static int id = 0;

    private User getUser(int lat, int lng) {
        return new User(id++, "", lat, lng);
    }

    @Test
    public void zeroUser() {
        // given
        List<User> users = Collections.emptyList();

        // when
        Map<User, User> userUserMap = UserDistanceManager.computeUserNearestUserMap(users);

        // then
        Assert.assertTrue(userUserMap.isEmpty());
    }

    @Test
    public void oneUser() {
        // given
        User user = getUser(0, 0);
        List<User> users = Arrays.asList(user);

        // when
        Map<User, User> userUserMap = UserDistanceManager.computeUserNearestUserMap(users);

        // then
        Assert.assertTrue(userUserMap.isEmpty());
    }

    @Test
    public void twoUsers() {
        // given
        User user1 = getUser(0, 0);
        User user2 = getUser(1, 1);
        List<User> users = Arrays.asList(user1, user2);


        // when
        Map<User, User> userUserMap = UserDistanceManager.computeUserNearestUserMap(users);

        // then
        Assert.assertEquals(user2, userUserMap.get(user1));
        Assert.assertEquals(user1, userUserMap.get(user2));
    }

    @Test
    public void threeUsersInLine() {
        // given
        User user1 = getUser(0, 0);
        User user2 = getUser(2, 2);
        User user3 = getUser(3, 3);

        List<User> users = Arrays.asList(user1, user2, user3);


        // when
        Map<User, User> userUserMap = UserDistanceManager.computeUserNearestUserMap(users);

        // then
        Assert.assertEquals(user2, userUserMap.get(user1));
        Assert.assertEquals(user3, userUserMap.get(user2));
        Assert.assertEquals(user2, userUserMap.get(user3));
    }
}
