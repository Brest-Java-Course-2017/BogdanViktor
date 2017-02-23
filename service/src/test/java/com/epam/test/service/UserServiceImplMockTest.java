package com.epam.test.service;

import com.epam.test.dao.User;
import com.epam.test.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by mentor on 20.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test-mock.xml"})
public class UserServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private final User userForTest = new User("userLogin8", "userPassword8");
    private final User existedUserForTest = new User(1, "userLogin1", "userPassword1", "first user");


    @Autowired
    private UserService userService;

    @Autowired
    private UserDao mockUserDao;

    @After
    public void clean() {
        verify(mockUserDao);
        reset(mockUserDao);
    }

    @Test
    public void testAddUser() throws Exception {
        LOGGER.debug("testAddUser with easyMock");
        expect(mockUserDao.addUser(new User("userLogin8", "userPassword8"))).andReturn(5);
        expect(mockUserDao.getUserByLogin("userLogin8")).andThrow(new UnsupportedOperationException());
        replay(mockUserDao);

        Integer id = userService.addUser(userForTest);
        Assert.isTrue(id == 5);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetUserByLoginException() {
        expect(mockUserDao.getUserByLogin(userForTest.getLogin())).andThrow(new UnsupportedOperationException());
        replay(mockUserDao);
        userService.getUserByLogin(userForTest.getLogin());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        LOGGER.debug("testGetAllUsers with easyMock");
        User [] usersArray = {new User(), new User()};
        expect(mockUserDao.getAllUsers()).andReturn(Arrays.asList(usersArray));
        replay(mockUserDao);
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() throws Exception {
        LOGGER.debug("testTetUserById() with easyMock");
        expect(mockUserDao.getUserById(1)).andReturn(existedUserForTest);
        replay(mockUserDao);
        User newUser = userService.getUserById(1);
        assertEquals(existedUserForTest.getLogin(), newUser.getLogin());
        assertEquals(existedUserForTest.getPassword(), newUser.getPassword());
        assertEquals(existedUserForTest.getDescription(), newUser.getDescription());
    }


    @Test
    public void testGetUserByLogin() throws Exception {
        LOGGER.debug("testGetUserByLogin() with easyMock");
        expect(mockUserDao.getUserByLogin(existedUserForTest.getLogin())).andReturn(existedUserForTest);
        replay(mockUserDao);
        User newUser = userService.getUserByLogin(existedUserForTest.getLogin());
        assertEquals(existedUserForTest, newUser);

    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByLoginWhenLoginDoesNotExist() throws Exception {
        LOGGER.debug("testGetUserByLoginWhenLoginDoesNotExist() with easyMock");
        expect(mockUserDao.getUserByLogin("vasia")).andThrow(new IllegalArgumentException());
        replay(mockUserDao);
        userService.getUserByLogin("vasia");
    }



    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNull() throws Exception {
        LOGGER.debug("testAddUserWithNull() with easyMock");
        replay(mockUserDao);
        userService.addUser(null);
    }

    @Test
    public void testUpdateUser() throws Exception {
        LOGGER.debug("testUpdateUser() with easyMock");
        User testUser = new User(1, "testUser", "pass", "some description");
        expect(mockUserDao.updateUser(testUser)).andReturn(1);
        expect(mockUserDao.getUserById(1)).andReturn(testUser);
        expect(mockUserDao.getUserByLogin("testUser")).andThrow(new IllegalArgumentException());
        replay(mockUserDao);
        assertEquals(1,userService.updateUser(testUser));
        assertEquals(testUser, userService.getUserById(testUser.getUserId()));
    }




}
