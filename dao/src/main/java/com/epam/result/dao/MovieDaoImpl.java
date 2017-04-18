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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The {@code MovieDaoImpl} - is an implementation of interface "MovieDao"
 * that provides access to a database.
 * @author  Bogdan Viktor
 */
public class MovieDaoImpl implements MovieDAO{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER =LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");



    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MOVIE_ID = "movie_id";
    private static final String MOVIE_TITLE = "movie_title";
    private static final String RELEASE_DATE = "release_date";
    private static final String RATING = "rating";
    private static final String MOVIE_DIRECTOR_ID = "movie_director_id";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    @Value("${select_all_movies}")
    String selectAllMoviesSQL;

    @Value("${select_all_movies_with_director_name}")
    String selectAllMoviesWithDirectorNameSQL;

    @Value("${update_movie}")
    String updateMovieSQL;

    @Value("${delete_movie}")
    String deleteMovieSQL;

    @Value("${select_movies_created_by_director}")
    String selectMoviesCreatedByDirectorSQL;

    @Value("${insert_movie}")
    String insertMovieSQL;

    @Value("${select_movies_with_date_filter}")
    String selectMoviesWithDateFilterSQL;

    @Value("${select_movie_by_title_and_release_date}")
    String selectMovieByTitleAndReleaseDateSQL;

    @Value("${select_movie_by_id}")
    String selectMovieByIdSQL;

    @Value("${select_movie_by_id_with_director_name}")
    String selectMovieByIdWithDirectorNameSQL;



    public MovieDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate =new  NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Movie getMovieByTitleAndReleaseDate(String movieTitle, Date date){
        LOGGER.debug("getMovieByTitleAndReleaseDate({}, {})", movieTitle, FORMATTER.format(date));
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_TITLE, movieTitle);
        params.put(RELEASE_DATE, FORMATTER.format(date));
        return namedParameterJdbcTemplate.queryForObject(selectMovieByTitleAndReleaseDateSQL,
                params, new MovieRowMapper());
    }

    @Override
    public List<Movie> getAllMovies() {
        LOGGER.debug("getAllMovies()");

        return jdbcTemplate.query(selectAllMoviesSQL, new MovieRowMapper());
    }

    @Override
    public List<Movie> getAllMoviesCreatedByDirector(Integer directorID) {
        LOGGER.debug("getAllMoviesCreatedByDirector({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put( MOVIE_DIRECTOR_ID, directorID);
        return namedParameterJdbcTemplate.query(selectMoviesCreatedByDirectorSQL,
                params, new MovieRowMapper());
    }

    @Override
    public List<MovieDTO> getAllMovieDTO() {
        LOGGER.debug("getAllMovieDTO()");

        return jdbcTemplate.query(selectAllMoviesWithDirectorNameSQL, new MovieDTORowMapper());
    }

    @Override
    public List<MovieDTO> getAllMovieDTOWithDateFilter(Date startDate, Date endDate) {
        LOGGER.debug("getAllMovieDTOWithDateFilter({}, {})", FORMATTER.format(startDate), FORMATTER.format(endDate));

        Map<String, Object> params = new HashMap<>();
        params.put(START_DATE, FORMATTER.format(startDate));
        params.put(END_DATE, FORMATTER.format(endDate));
        return namedParameterJdbcTemplate.query(selectMoviesWithDateFilterSQL,
                params, new MovieDTORowMapper());
    }

    @Override
    public int addMovie(Movie movie) {
        LOGGER.debug("addMovie({}, {})", movie.getMovieTitle(), movie.getReleaseDateAsString());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(MOVIE_ID, movie.getMovieId());
        parameterSource.addValue(MOVIE_TITLE, movie.getMovieTitle());
        parameterSource.addValue(RELEASE_DATE, movie.getReleaseDateAsString());
        parameterSource.addValue(RATING, movie.getRating());
        parameterSource.addValue(MOVIE_DIRECTOR_ID, movie.getMovieDirectorId());

        namedParameterJdbcTemplate.update(insertMovieSQL, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateMovie(Movie movie) {
        LOGGER.debug("updateMovie({}, {})", movie.getMovieTitle(), movie.getReleaseDateAsString());
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_ID, movie.getMovieId());
        params.put(MOVIE_TITLE, movie.getMovieTitle());
        params.put(RELEASE_DATE, movie.getReleaseDateAsString());
        params.put(RATING, movie.getRating());
        params.put(MOVIE_DIRECTOR_ID, movie.getMovieDirectorId());
        return namedParameterJdbcTemplate.update(updateMovieSQL, params);
    }

    @Override
    public int deleteMovie(Integer movieID) {
        LOGGER.debug("deleteMovie({})", movieID);
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_ID, movieID);
        return namedParameterJdbcTemplate.update(deleteMovieSQL, params);
    }

    @Override
    public Movie getMovieById(Integer movieID) {
        LOGGER.debug("getMovieById({})", movieID);
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_ID, movieID);
        return namedParameterJdbcTemplate.queryForObject(selectMovieByIdSQL,
                params, new MovieRowMapper());
    }

    @Override
    public MovieDTO getMovieDTOById(Integer movieId) {
        LOGGER.debug("getMovieDTOById({})", movieId);
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_ID, movieId);
        return namedParameterJdbcTemplate.queryForObject(selectMovieByIdWithDirectorNameSQL,
                params, new MovieDTORowMapper());
    }

    private class MovieDTORowMapper implements RowMapper<MovieDTO>{
        @Override
        public MovieDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return new MovieDTO(
                        rs.getInt(MOVIE_ID),
                        rs.getString(MOVIE_TITLE),
                        FORMATTER.parse(rs.getString(RELEASE_DATE)),
                        rs.getDouble(RATING),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME));
            } catch (ParseException e) {
                LOGGER.debug(e);
                throw new SQLException(e);
            }
        }
    }

    private class MovieRowMapper implements RowMapper<Movie>{
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return new Movie(
                        rs.getInt(MOVIE_ID),
                        rs.getString(MOVIE_TITLE),
                        FORMATTER.parse(rs.getString(RELEASE_DATE)),
                        rs.getDouble(RATING),
                        rs.getInt(MOVIE_DIRECTOR_ID));
            } catch (ParseException e) {
                LOGGER.debug(e);
                throw new SQLException(e);
            }
        }
    }
}
