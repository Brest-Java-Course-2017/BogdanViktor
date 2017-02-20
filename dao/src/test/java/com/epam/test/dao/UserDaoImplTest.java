package com.epam.test.dao;

import com.epam.test.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
public class UserDaoImplTest{

    @Autowired
    UserDao userDao;

    @Ignore
    @Test
    public void getAllUsersTest() throws Exception {
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() == 2);
    }

    @Test
    public void test_get_user_by_id_if_id_exists() throws Exception {
        User user = userDao.getUserById(1);
        assertNotNull(user);
        assertEquals("userLogin1",user.getLogin());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_user_by_id_if_id_not_exists() throws Exception {
        User user = userDao.getUserById(Integer.MAX_VALUE);
        assertNotNull(user);
        assertEquals("userLogin1",user.getLogin());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_user_by_id_if_id_negative() throws Exception {
        User user = userDao.getUserById(-5);
        assertNotNull(user);
        assertEquals("userLogin1",user.getLogin());

    }

    @Test
    public void test_size_after_add_user() {
        List<User> list = userDao.getAllUsers();
        int initialSize = list.size();
        User user = new User("vasia","pass", "simple user");
        userDao.addUser(user);
        list = userDao.getAllUsers();
        assertThat(list.size(), is(initialSize+1));
//        assertEquals((Integer)(initialSize+1),list.size());
    }

    @Test
    public void test_user_same_after_add() {
        User user = new User("user5","pass5", "simpleUser5");
        int id = userDao.addUser(user);
        assertThat(user, is(userDao.getUserById(id)));
//        assertEquals((Integer)(initialSize+1),list.size());
    }

    @Test(expected = NullPointerException.class)
    public void test_add_null() {
        userDao.addUser(null);
    }

    @Test
    public void updateUserTest() {
        User user = new User(1 ,"Petr","pass1", "user");
        userDao.updateUser(user);
        assertEquals(user, userDao.getUserById(user.getUserId()));
    }


    @Test
    public void deleteUser() {
        List<User> list = userDao.getAllUsers();
        int initialSize = list.size();
        userDao.deleteUser(1);
        list = userDao.getAllUsers();
        assertThat(list.size(), is(initialSize-1));
    }
}