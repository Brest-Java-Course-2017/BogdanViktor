package com.epam.result.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code DirectorDaoImpl} - is an implementation of interface "DirectorDao"
 * that provides access to a database.
 * @author  Bogdan Viktor
 */
public class DirectorDaoImpl implements DirectorDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER =LogManager.getLogger();

    private static final String DIRECTOR_ID = "director_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String AVERAGE_RATING = "average";

    @Value("${select_all_directors}")
    String selectAllDirectorsSQL;

    @Value("${select_all_directors_with_movie_rating}")
    String selectAllDirectorsWithMovieRatingSQL;

    @Value("${select_director_by_id}")
    String selectDirectorByIdSQL;

    @Value("${insert_director}")
    String insertDirectorSQL;

    @Value("${update_director}")
    String updateDirectorSQL;

    @Value("${delete_director}")
    String deleteDirectorSQL;

    @Value("${select_director_by_first_and_last_name}")
    String selectDirectorByFirstAndLastNameSQL;


    public DirectorDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate =new  NamedParameterJdbcTemplate(dataSource);
    }



    @Override
    public List<Director> getAllDirectors() {
        LOGGER.debug("getAllDirectors()");
        return jdbcTemplate.query(selectAllDirectorsSQL, new DirectorRowMapper());
    }

    @Override
    public List<DirectorDTO> getAllDirectorDTO() {
        LOGGER.debug("getAllDirectorDTO()");
        return jdbcTemplate.query(selectAllDirectorsWithMovieRatingSQL, new DirectorDTORowMapper());
    }

    @Override
    public Director getDirectorById(Integer directorID){
        LOGGER.debug("getDirectorById({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put(DIRECTOR_ID, directorID);
        return namedParameterJdbcTemplate.queryForObject(selectDirectorByIdSQL,
                params, new DirectorRowMapper());
    }

    @Override
    public Director getDirectorByFirstAndLastName(String firstName, String lastName){
        LOGGER.debug("getDirectorByFirstNameAndLastName({})", firstName+", "+lastName);
        Map<String, Object> params = new HashMap<>();
        params.put(FIRST_NAME, firstName);
        params.put(LAST_NAME, lastName);
        return namedParameterJdbcTemplate.queryForObject(selectDirectorByFirstAndLastNameSQL,
                params, new DirectorRowMapper());
    }

    @Override
    public int addDirector(Director director) {
        LOGGER.debug("addDirector({})", director.getFirstName()+" "+director.getLastName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(DIRECTOR_ID, director.getDirectorId());
        parameterSource.addValue(FIRST_NAME, director.getFirstName());
        parameterSource.addValue(LAST_NAME, director.getLastName());
        namedParameterJdbcTemplate.update(insertDirectorSQL, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateDirector(Director director) {
        LOGGER.debug("updateDirector({})", director.getFirstName()+" "+director.getLastName());
        Map<String, Object> params = new HashMap<>();
        params.put(DIRECTOR_ID, director.getDirectorId());
        params.put(FIRST_NAME, director.getFirstName());
        params.put(LAST_NAME, director.getLastName());
        return namedParameterJdbcTemplate.update(updateDirectorSQL, params);
    }

    @Override
    public int deleteDirector(Integer directorID) {
        LOGGER.debug("deleteDirector({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put(DIRECTOR_ID, directorID);
        return namedParameterJdbcTemplate.update(deleteDirectorSQL, params);
    }



    private class DirectorRowMapper implements RowMapper<Director>{
        @Override
        public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Director(
                    rs.getInt(DIRECTOR_ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME));
        }
    }

    private class DirectorDTORowMapper implements RowMapper<DirectorDTO> {
        @Override
        public DirectorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new DirectorDTO(
                    rs.getInt(DIRECTOR_ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getDouble(AVERAGE_RATING));
        }
    }
}
