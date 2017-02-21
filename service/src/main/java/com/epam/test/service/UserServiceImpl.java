package com.epam.test.service;

import com.epam.test.dao.UserDao;
import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by mentor on 20.2.17.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        LOGGER.debug("getAllUsers()");

        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Integer userId) throws DataAccessException {
        LOGGER.debug("getUserById(): user_id = {} ", userId);
        Assert.notNull(userId, "user_id should not be null.");
        Assert.isTrue(userId>0, "user_id should be positive");
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByLogin(String login) throws DataAccessException {
        LOGGER.debug("getUserByLogin(): user login = {} ", login);
        Assert.hasText(login, "User login should not be null.");
        return userDao.getUserByLogin(login);
    }

    @Override
    public Integer addUser(User user) throws DataAccessException {
        Assert.notNull(user, "User should not be null.");
        LOGGER.debug("addUser(): user login = {} ", user.getLogin());
        Assert.isNull(user.getUserId(), "User Id should be null.");
        Assert.hasText(user.getLogin(), "User login should not be null.");
        Assert.hasText(user.getPassword(), "User password should not be null.");
        try {
            getUserByLogin(user.getLogin());
        } catch (Exception e){
            return userDao.addUser(user);
        }
        throw new IllegalArgumentException("Login is already exists");
    }

    @Override
    public int updateUser(User user) throws DataAccessException {
        Assert.notNull(user, "User should not be null.");
        LOGGER.debug("updateUser(): user login = {} ", user.getLogin());
        Assert.notNull(user.getUserId(), "User Id should not be null.");
        Assert.hasText(user.getLogin(), "User login should not be null.");
        Assert.hasText(user.getPassword(), "User password should not be null.");
        try {
            getUserByLogin(user.getLogin());
        } catch (Exception e){
            return userDao.updateUser(user);
//            if(i==0) throw new IllegalArgumentException("User with this id does not exist");

        }
        throw new IllegalArgumentException("Login is already exists");
    }

    @Override
    public int deleteUser(Integer userId) throws DataAccessException {
        Assert.isTrue(userId>0, "userId should be positive");
        return userDao.deleteUser(userId);
    }
}
