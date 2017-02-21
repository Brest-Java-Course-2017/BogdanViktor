package com.epam.test.service;

import com.epam.test.dao.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by sw0rd on 21.02.17.
 */
public interface UserService {
    List<User> getAllUsers() throws DataAccessException;

    User getUserById(Integer userId) throws DataAccessException;

    User getUserByLogin(String login) throws DataAccessException;

    Integer addUser(User user) throws DataAccessException;

    int updateUser(User user) throws DataAccessException;

    int deleteUser(Integer userId) throws DataAccessException;
}
