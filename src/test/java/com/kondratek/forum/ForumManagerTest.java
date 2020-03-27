package com.kondratek.forum;

import com.kondratek.JsonReader;
import com.kondratek.forum.db.UserNotFoundException;
import com.kondratek.forum.db.model.Post;
import com.kondratek.forum.db.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Set;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonReader.class})
public class ForumManagerTest {
    private final String TEST_URL = "testUrl";

    ForumManager forumManager;

    @Before
    public void initTest() throws IOException {
        forumManager = new ForumManager();
        PowerMockito.mockStatic(JsonReader.class);
        PowerMockito.when(JsonReader.readJsonArrayFromUrl(ArgumentMatchers.anyString())).thenCallRealMethod();
        PowerMockito.when(JsonReader.readJsonObjectFromUrl(ArgumentMatchers.anyString())).thenCallRealMethod();
    }

    @Test
    public void fetchUsersFromURL_singleUser() throws IOException {
        // given
        String userJson = getUserJson(1);
        String userJsonArray = getObjectArrayJson(userJson);
        PowerMockito.when(JsonReader.fetchJsonString(TEST_URL)).thenReturn(userJsonArray);

        // when
        forumManager.fetchUsersFromURL(TEST_URL);

        // then
        Assert.assertNotNull(forumManager.getUserById(1));
    }

    @Test
    public void fetchUsersFromURL_twoUsers() throws IOException {
        // given
        String userJsonOne = getUserJson(1);
        String userJsonTwo = getUserJson(2);
        String userJsonArray = getObjectArrayJson(userJsonOne, userJsonTwo);
        PowerMockito.when(JsonReader.fetchJsonString(TEST_URL)).thenReturn(userJsonArray);

        // when
        forumManager.fetchUsersFromURL(TEST_URL);

        // then
        Assert.assertNotNull(forumManager.getUserById(1));
        Assert.assertNotNull(forumManager.getUserById(2));
    }

    @Test
    public void fetchUsersFromURL_singleUser_withSinglePost() throws IOException, UserNotFoundException {
        // given
        String userJson = getUserJson(1);
        String userJsonArray = getObjectArrayJson(userJson);
        String postJson = getPostJson(1, 0);
        String postJsonArray = getObjectArrayJson(postJson);
        PowerMockito.when(JsonReader.fetchJsonString(TEST_URL)).thenReturn(userJsonArray, postJsonArray);

        // when
        forumManager.fetchUsersFromURL(TEST_URL);
        forumManager.fetchUsersPostsFromUrl(TEST_URL);

        // then
        User userById = forumManager.getUserById(1);
        Assert.assertNotNull(userById);
        Assert.assertEquals(1, userById.getPosts().size());
        Assert.assertEquals(0, userById.getPosts().get(0).getId());
    }

    @Test(expected = UserNotFoundException.class)
    public void fetchUsersFromURL_postWithoutUser() throws IOException, UserNotFoundException {
        // given
        String postJson = getPostJson(1, 0);
        String postJsonArray = getObjectArrayJson(postJson);
        PowerMockito.when(JsonReader.fetchJsonString(TEST_URL)).thenReturn(postJsonArray);

        // when
        forumManager.fetchUsersPostsFromUrl(TEST_URL);

        // exception thrown
    }

    // todo genNonUniqueTitles test


    private String getObjectArrayJson(String... jsonObjects) {
        return "[" + String.join(", ", jsonObjects) + "]";
    }

    private String getUserJson(int id) {
        return String.format("{ \"id\": %d, \"username\": \"John\", \"address\": { \"geo\": { \"lat\": 0, \"lng\": 0 } } }", id);
    }

    private String getPostJson(int userId, int id) {
        return String.format("{ \"userId\": %d, \"id\": %d, \"title\": \"\", \"body\": \"\" }", userId, id);
    }
}
