package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
public class UserDaoImplTest{

    private static final Logger LOGGER = LogManager.getLogger();

    static final String USER_LOGIN_1 = "userLogin1";

    // sample user object for tests.
    private static final User user = new User("userLogin3", "userPassword3");

    @Autowired
    UserDao userDao;

    @Test
    public void getAllUsersTest() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() == 2);
    }

    @Test
    public void test_get_user_by_id_if_id_exists() throws Exception {
        LOGGER.debug("test_get_user_by_id_if_id_exists()");
        User user = userDao.getUserById(1);
        assertNotNull(user);
        assertEquals("userLogin1",user.getLogin());
    }

    @Test
    public void getUserByLoginTest() throws Exception {
        LOGGER.debug("test: getUserByLogin()");
        User user = userDao.getUserByLogin(USER_LOGIN_1);
        assertNotNull(user);
        assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void testAddUser() throws Exception {

        LOGGER.debug("test: addUser()");

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        Integer userId = userDao.addUser(user);
        assertNotNull(userId);

        User newUser = userDao.getUserById(userId);
        assertNotNull(newUser);
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertNull(user.getDescription());

        users = userDao.getAllUsers();
        assertEquals(quantityBefore + 1, users.size());
    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_user_by_id_if_id_not_exists() throws Exception {
        LOGGER.debug("test_get_user_by_id_if_id_not_exists()");
        User user = userDao.getUserById(Integer.MAX_VALUE);
        assertNotNull(user);
        assertEquals("userLogin1",user.getLogin());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_user_by_id_if_id_negative() throws Exception {
        LOGGER.debug("test_get_user_by_id_if_id_negative()");

        User user = userDao.getUserById(-5);
        assertNotNull(user);
        assertEquals("userLogin1",user.getLogin());

    }

    @Test
    public void test_size_after_add_user() {
        LOGGER.debug("test_size_after_add_user()");

        List<User> list = userDao.getAllUsers();
        int initialSize = list.size();
        User user = new User(null, "vasia","pass", "simple user");
        userDao.addUser(user);
        list = userDao.getAllUsers();
        assertThat(list.size(), is(initialSize+1));
//        assertEquals((Integer)(initialSize+1),list.size());
    }


    @Test(expected = NullPointerException.class)

    public void test_add_null() {
        LOGGER.debug("test_add_null()");
        userDao.addUser(null);
    }

    @Test
    public void updateUserTest() {
        LOGGER.debug("updateUserTest()");

        User user = new User(1 ,"Petr","pass1", "user");
        userDao.updateUser(user);
        assertEquals(user, userDao.getUserById(user.getUserId()));
    }



    @Test
    public void testUpdateUser() throws Exception {
        LOGGER.debug("test: updateUser()");
        User user = userDao.getUserById(1);
        user.setPassword("updated password");
        user.setDescription("updated description");

        int count = userDao.updateUser(user);
        assertEquals(1, count);

        User updatedUser = userDao.getUserById(user.getUserId());
        assertTrue(user.getLogin().equals(updatedUser.getLogin()));
        assertTrue(user.getPassword().equals(updatedUser.getPassword()));
        assertTrue(user.getDescription().equals(updatedUser.getDescription()));
    }

    @Test
    public void testDeleteUser() throws Exception {

        LOGGER.debug("test: deleteUser()");

        Integer userId = userDao.addUser(user);
        assertNotNull(userId);

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        int count = userDao.deleteUser(userId);
        assertEquals(1, count);


        users = userDao.getAllUsers();
        assertEquals(quantityBefore - 1, users.size());
    }
}