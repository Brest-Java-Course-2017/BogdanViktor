/**
 * Created by sw0rd on 05.03.17.
 */

package com.epam.result;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * DAO interface.
 */
public interface MovieAndDirectorDAO {

    /**
     * Get all directors list.
     *
     * @return all directors list
     */
    List<Director> getAllDirectors() throws DataAccessException;

    List<DirectorDTO> getAllDirectorsWithMovieRating() throws DataAccessException;

    Director getDirectorByFirstAndLastName(String firstName, String lastName) throws DataAccessException;

    int addDirector(Director director) throws DataAccessException;

    Director getDirectorById(Integer id);

    int updateDirector(Director director) throws DataAccessException;

    int deleteDirector(Integer directorID) throws DataAccessException;

    List<Movie> getAllMovieByDirector(Integer directorID) throws DataAccessException;

    Movie getMovieByTitleAndReleaseDate(String movieTitle, LocalDate date) throws DataAccessException;

    List<Movie> getAllMovies() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException;

    List<Movie> getAllMoviesWithDateFilter(LocalDate from, LocalDate to) throws DataAccessException;

    int addMovie(Movie movie) throws DataAccessException;

    int updateMovie(Movie movie) throws DataAccessException;

    int deleteMovie(Integer movieID) throws DataAccessException;

}
