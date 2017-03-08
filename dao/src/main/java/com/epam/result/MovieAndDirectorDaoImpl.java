package com.epam.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
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
 * Created by sw0rd on 06.03.17.
 */
public class MovieAndDirectorDaoImpl implements MovieAndDirectorDAO{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER =LogManager.getLogger();

    static final String DIRECTOR_ID = "director_id";
    static final String FIRST_NAME = "first_name";
    static final String LAST_NAME = "last_name";

    static final String MOVIE_ID = "movie_id";
    static final String MOVIE_TITLE = "movie_title";
    static final String RELEASE_DATE = "release_date";
    static final String RATING = "rating";
    static final String ID_MOVIE_DIRECTOR = "id_movie_director";
    static final String AVARAGE_RATING = "avarage";

    @Value("${select_all_directors}")
    String selectAllDirectorsSQL;

    @Value("${select_all_directors_with_movie_rating}")
    String selectAllDirectorsWithMovieRatingSQL;

    @Value("${select_all_movies}")
    String selectAllMoviesSQL;

    @Value("${select_director_by_id}")
    String selectDirectorByIdSQL;

    @Value("${insert_director}")
    String insertDirectorSQL;

    @Value("${update_director}")
    String updateDirectorSQL;

    @Value("${delete_director}")
    String deleteDirectorSQL;

    @Value("${select_movie_by_director}")
    String selectMovieByDirectorSQL;



    public MovieAndDirectorDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate =new  NamedParameterJdbcTemplate(dataSource);
    }



    @Override
    public List<Director> getAllDirectors() throws DataAccessException {
        LOGGER.debug("getAllDirectors()");
        return jdbcTemplate.query(selectAllDirectorsSQL, new DirectorRowMapper());
    }

    @Override
    public List<DirectorDTO> getAllDirectorsWithMovieRating() throws DataAccessException {
        LOGGER.debug("getAllDirectorsWithMovieRating()");
        return jdbcTemplate.query(selectAllDirectorsWithMovieRatingSQL, new DirectorDTORowMapper());
    }

    @Override
    public int addDirector(Director director) throws DataAccessException {
        LOGGER.debug("addDirector({})", director.getFirstName()+" "+director.getLastName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(DIRECTOR_ID, director.getDirectorID());
        parameterSource.addValue(FIRST_NAME, director.getFirstName());
        parameterSource.addValue(LAST_NAME, director.getLastName());
        namedParameterJdbcTemplate.update(insertDirectorSQL, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Movie> getAllMovies() throws DataAccessException {
        return null;
    }

    @Override
    public Director getDirectorById(Integer directorID) throws DataAccessException{
        LOGGER.debug("getDirectorById({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put(DIRECTOR_ID, directorID);
        Director director = namedParameterJdbcTemplate.queryForObject(selectDirectorByIdSQL, params, new DirectorRowMapper());
        return director;
    }

    @Override
    public int updateDirector(Director director) throws DataAccessException {
        LOGGER.debug("updateDirector({})", director.getFirstName()+" "+director.getLastName());
        Map<String, Object> params = new HashMap<>();
        params.put(DIRECTOR_ID, director.getDirectorID());
        params.put(FIRST_NAME, director.getFirstName());
        params.put(LAST_NAME, director.getLastName());
        return namedParameterJdbcTemplate.update(updateDirectorSQL, params);
    }

    @Override
    public int deleteDirector(Integer directorID) throws DataAccessException {
        LOGGER.debug("deleteDirector({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put(DIRECTOR_ID, directorID);
        return namedParameterJdbcTemplate.update(deleteDirectorSQL, params);
    }

    @Override
    public List<Movie> getAllMovieByDirector(Integer directorID) throws DataAccessException {
        LOGGER.debug("getAllMovieByDirector({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put(ID_MOVIE_DIRECTOR, directorID);
        List<Movie> list = namedParameterJdbcTemplate.query(selectMovieByDirectorSQL, params, new MovieRowMapper());
        System.out.println();
        return list;
    }

    @Override
    public List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException {
        LOGGER.debug("getAllMovies()");

        List<MovieDTO> list = jdbcTemplate.query(selectAllMoviesSQL, new MovieDTORowMapper());
        return list;
    }

    @Override
    public List<Movie> getAllMoviesWithDateFilter(DateTime from, DateTime to) throws DataAccessException {


        return null;
    }

    @Override
    public int addMovie(Movie movie) throws DataAccessException {
        return 0;
    }

    @Override
    public int updateMovie(Movie movie) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteMovie(Integer movieID) throws DataAccessException {
        return 0;
    }

    private class DirectorRowMapper implements RowMapper<Director>{
        @Override
        public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
            Director director = new Director(
                    rs.getInt(DIRECTOR_ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME));
            return director;
        }
    }

    private class MovieDTORowMapper implements RowMapper<MovieDTO>{
        @Override
        public MovieDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(rs.getString(RELEASE_DATE), fmt);

            MovieDTO movieDTO = new MovieDTO(
                    rs.getInt(MOVIE_ID),
                    rs.getString(MOVIE_TITLE),
                    date,
                    rs.getDouble(RATING),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME));
            return movieDTO;
        }
    }

    private class DirectorDTORowMapper implements RowMapper<DirectorDTO> {
        @Override
        public DirectorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DirectorDTO directorDTO = new DirectorDTO(
                    rs.getInt(DIRECTOR_ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getDouble(AVARAGE_RATING));
            return directorDTO;
        }
    }

    private class MovieRowMapper implements RowMapper<Movie>{
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(rs.getString(RELEASE_DATE), fmt);

            Movie movie = new Movie(
                    rs.getInt(MOVIE_ID),
                    rs.getString(MOVIE_TITLE),
                    date,
                    rs.getDouble(RATING),
                    rs.getInt(ID_MOVIE_DIRECTOR));
            return movie;
        }
    }
}
