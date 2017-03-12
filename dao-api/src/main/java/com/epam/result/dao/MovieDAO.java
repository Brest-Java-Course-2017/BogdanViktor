/**
 * Created by sw0rd on 05.03.17.
 */

package com.epam.result.dao;

import org.joda.time.LocalDate;
import org.springframework.dao.DataAccessException;

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

    Movie getMovieByTitleAndReleaseDate(String movieTitle, LocalDate date) throws DataAccessException;

    List<Movie> getAllMovies() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDateFilter(LocalDate from, LocalDate to) throws DataAccessException;

    int addMovie(Movie movie) throws DataAccessException;

    int updateMovie(Movie movie) throws DataAccessException;

    int deleteMovie(Integer movieID) throws DataAccessException;

}
