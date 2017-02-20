package com.epam.test.dao;

import com.epam.test.model.User;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by sw0rd on 15.02.17.
 */
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            LOGGER.error("File app.properties doesn't exist", e);
            e.printStackTrace();
        }
    }



    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        try{
            return jdbcTemplate.query(properties.getProperty("user.selectAll"), new UserRowMapper());
        }catch (Exception e){
            LOGGER.warn("Exception in method \"getAllUsers\"", e);
            throw e;
        }
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);
        try{
            SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
            User user = namedParameterJdbcTemplate.queryForObject(properties.getProperty("user.selectById"),
                    namedParameters, new UserRowMapper());
            return user;
        }catch (Exception e){
            LOGGER.warn("Exception in method \"getUserById({})\"",userId/*, e*/);
            throw e;
        }

    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser({})", user);

        try {
            Object[] params = new Object[] {user.getLogin(), user.getPassword(), user.getDescription()};
            int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
            jdbcTemplate.update(properties.getProperty("user.addUser"), params, types);
            int id = jdbcTemplate.queryForObject(properties.getProperty("selectMaxId"), Integer.class);
            user.setUserId(id);
            return id;

        }catch(Exception e){
            LOGGER.warn("Exception in method addUser({})", user, e);
            throw e;
        }
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})", user);
        try{
            jdbcTemplate.update(String.format(properties.getProperty("user.update"),
                    user.getLogin(), user.getPassword(),
                    user.getDescription(), user.getUserId()));
        }catch (Exception e) {
            LOGGER.warn("Exception in method updateUser({})", user, e);
            throw e;
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser({})", userId);
        try{
            jdbcTemplate.update(String.format(properties.getProperty("user.delete"), userId));
        }catch (Exception e) {
            LOGGER.warn("Exception in method deleteUser({})", userId, e);
            throw e;
        }
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("description"));
            return user;
        }
    }
}
