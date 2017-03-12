package com.epam.result.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
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
 * Created by sw0rd on 06.03.17.
 */
public class MovieDaoImpl implements MovieDAO{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER =LogManager.getLogger();
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");


    static final String FIRST_NAME = "first_name";
    static final String LAST_NAME = "last_name";
    static final String MOVIE_ID = "movie_id";
    static final String MOVIE_TITLE = "movie_title";
    static final String RELEASE_DATE = "release_date";
    static final String RATING = "rating";
    static final String MOVIE_DIRECTOR_ID = "movie_director_id";
    static final String FROM_DATE = "from_date";
    static final String TO_DATE = "to_date";

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



    public MovieDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate =new  NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Movie getMovieByTitleAndReleaseDate(String movieTitle, LocalDate date) throws DataAccessException{
        LOGGER.debug("getMovieByTitleAndReleaseDate({})", movieTitle+","+date.toString(FORMATTER));
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_TITLE, movieTitle);
        params.put(RELEASE_DATE, date.toString(FORMATTER));
        Movie movie = namedParameterJdbcTemplate.queryForObject(selectMovieByTitleAndReleaseDateSQL,
                params, new MovieRowMapper());
        return movie;
    }

    @Override
    public List<Movie> getAllMovies() throws DataAccessException {
        LOGGER.debug("getAllMovies()");

        List<Movie> list = jdbcTemplate.query(selectAllMoviesSQL, new MovieRowMapper());
        return list;
    }

    @Override
    public List<Movie> getAllMoviesCreatedByDirector(Integer directorID) throws DataAccessException {
        LOGGER.debug("getAllMoviesCreatedByDirector({})", directorID);
        Map<String, Object> params = new HashMap<>();
        params.put( MOVIE_DIRECTOR_ID, directorID);
        List<Movie> list = namedParameterJdbcTemplate.query(selectMoviesCreatedByDirectorSQL, params, new MovieRowMapper());
        return list;
    }

    @Override
    public List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException {
        LOGGER.debug("getAllMoviesWithDirectorName()");

        List<MovieDTO> list = jdbcTemplate.query(selectAllMoviesWithDirectorNameSQL, new MovieDTORowMapper());
        return list;
    }

    @Override
    public List<MovieDTO> getAllMoviesWithDateFilter(LocalDate from, LocalDate to) throws DataAccessException {
        LOGGER.debug("getAllMoviesWithDateFilter({})", from.toString(FORMATTER), to.toString(FORMATTER));

        Map<String, Object> params = new HashMap<>();
        params.put(FROM_DATE, from.toString(FORMATTER));
        params.put(TO_DATE, to.toString(FORMATTER));
        List<MovieDTO> list = namedParameterJdbcTemplate.query(selectMoviesWithDateFilterSQL, params, new MovieDTORowMapper());
        return list;
    }

    @Override
    public int addMovie(Movie movie) throws DataAccessException {
        LOGGER.debug("addMovie({})", movie.getMovieTitle());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(MOVIE_ID, movie.getMovieID());
        parameterSource.addValue(MOVIE_TITLE, movie.getMovieTitle());
        parameterSource.addValue(RELEASE_DATE, movie.getReleaseDateAsString());
        parameterSource.addValue(RATING, movie.getRating());
        parameterSource.addValue(MOVIE_DIRECTOR_ID, movie.getMovieDirectorID());

        namedParameterJdbcTemplate.update(insertMovieSQL, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateMovie(Movie movie) throws DataAccessException {
        LOGGER.debug("updateMovie({})", movie.getMovieTitle()+" "+movie.getReleaseDateAsString());
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_ID, movie.getMovieID());
        params.put(MOVIE_TITLE, movie.getMovieTitle());
        params.put(RELEASE_DATE, movie.getReleaseDateAsString());
        params.put(RATING, movie.getRating());
        params.put(MOVIE_DIRECTOR_ID, movie.getMovieDirectorID());
        return namedParameterJdbcTemplate.update(updateMovieSQL, params);
    }

    @Override
    public int deleteMovie(Integer movieID) throws DataAccessException {
        LOGGER.debug("deleteMovie({})", movieID);
        Map<String, Object> params = new HashMap<>();
        params.put(MOVIE_ID, movieID);
        return namedParameterJdbcTemplate.update(deleteMovieSQL, params);
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
                    rs.getInt( MOVIE_DIRECTOR_ID));
            return movie;
        }
    }
}