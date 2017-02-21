package com.epam.test.service;

import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.core.Is.is;

/**
 * Created by sw0rd on 21.02.17.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LogManager.getLogger();

    private final User existedUserForTest = new User(1, "userLogin1", "userPassword1", "first user");
    private final User userForTest = new User("userLogin8", "userPassword8");

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testGetAllUsers() throws Exception {
        LOGGER.debug("testGetAllUsers");
        List<User> users = userService.getAllUsers();
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() throws Exception {
        LOGGER.debug("testTetUserById()");
        User newUser = userService.getUserById(1);
        Assert.assertEquals(existedUserForTest.getLogin(), newUser.getLogin());
        Assert.assertEquals(existedUserForTest.getPassword(), newUser.getPassword());
        Assert.assertEquals(existedUserForTest.getDescription(), newUser.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByIdWithNull() throws Exception {
        LOGGER.debug("testGetUserByIdWithNull()");
         userService.getUserById(null);
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        LOGGER.debug("testGetUserByLogin()");
        User newUser = userService.getUserByLogin(existedUserForTest.getLogin());
        Assert.assertEquals(existedUserForTest, newUser);

    }

    @Test
    public void testGetUserByLoginWhenLoginInUpperCase() throws Exception {
        LOGGER.debug("testGetUserByLoginWhenLoginInUpperCase()");
        User newUser = userService.getUserByLogin(existedUserForTest.getLogin().toUpperCase());
        Assert.assertEquals(existedUserForTest, newUser);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testGetUserByLoginWhenLoginDoesNotExist() throws Exception {
        LOGGER.debug("testGetUserByLoginWhenLoginDoesNotExist()");
        userService.getUserByLogin("vasia");
    }

    @Test
    public void testAddUser() throws Exception {
        LOGGER.debug("testAddUser()");
        int initialSize = userService.getAllUsers().size();
        userService.addUser(userForTest);
        Assert.assertThat(initialSize+1, is(userService.getAllUsers().size()));
        User newUser = userService.getUserByLogin(userForTest.getLogin());
        Assert.assertThat(userForTest.getLogin(), is(newUser.getLogin()));
        Assert.assertThat(userForTest.getPassword(), is(newUser.getPassword()));
        Assert.assertThat(userForTest.getDescription(), is(newUser.getDescription()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWhenLoginExists() throws Exception {
        LOGGER.debug("testAddUserWhenLoginExists()");
        User testUser = new User("userLogin1", "pass");
        userService.addUser(testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNull() throws Exception {
        LOGGER.debug("testAddUserWithNull()");
        userService.addUser(null);
    }

    @Test
    public void testUpdateUser() throws Exception {
        LOGGER.debug("testUpdateUser()");
        User testUser = new User(1, "testUser", "pass", "some description");
        Assert.assertEquals(1,userService.updateUser(testUser));
        Assert.assertEquals(testUser, userService.getUserById(testUser.getUserId()));
    }

    @Test
    public void testUpdateUserWhenIdDoesNotExist() throws Exception {
        LOGGER.debug("testUpdateUserWhenIdDoesNotExist()");
        User testUser = new User(5, "testUser", "pass", "some description");
        Assert.assertEquals(0, userService.updateUser(testUser));
    }


    @Test
    public void testDeleteUser() throws Exception {
        LOGGER.debug("testDeleteUser()");
        int initialSize = userService.getAllUsers().size();
        Assert.assertEquals(1, userService.deleteUser(2));
        Assert.assertEquals(initialSize-1, userService.getAllUsers().size());
    }

    @Test
    public void testDeleteUserWhenIdDoesNotExist() throws Exception {
        LOGGER.debug("testDeleteUserWhenIdDoesNotExist()");
        Assert.assertEquals(0, userService.deleteUser(7));
    }

}