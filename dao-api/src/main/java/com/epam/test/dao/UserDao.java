package com.epam.test.dao;

import com.epam.test.model.User;

import java.util.List;

/**
 * Created by sw0rd on 15.02.17.
 */
public interface UserDao {

        public List<User> getAllUsers();

        public User getUserById(Integer userId);

        public Integer addUser(User user);

        public void updateUser(User user);

        public void deleteUser(Integer userId);

}
