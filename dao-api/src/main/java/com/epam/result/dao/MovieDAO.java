/**
 * Created by sw0rd on 05.03.17.
 */

package com.epam.result.dao;

import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
 * DAO interface.
 */
public interface MovieDAO {

    /**
     * Get all directors list.
     *
     * @return all directors list
     */
    List<Movie> getAllMoviesCreatedByDirector(Integer directorID) throws DataAccessException;

    Movie getMovieByTitleAndReleaseDate(String movieTitle, Date date) throws DataAccessException;

    List<Movie> getAllMovies() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDateFilter(Date from, Date to) throws DataAccessException;

    Movie getMovieById(Integer movieID) throws DataAccessException;

    int addMovie(Movie movie) throws DataAccessException;

    int updateMovie(Movie movie) throws DataAccessException;

    int deleteMovie(Integer movieID) throws DataAccessException;

}
