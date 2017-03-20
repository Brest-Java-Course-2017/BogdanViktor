package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
public interface MovieService {

    List<Movie> getAllMoviesCreatedByDirector(Integer directorID) throws DataAccessException;

    List<Movie> getAllMovies() throws DataAccessException;

    Movie getMovieById(Integer movieId) throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDateFilter(Date fromDate, Date toDate) throws DataAccessException;

    MovieDTO getMovieByIdWithDirectorName(Integer movieId) throws DataAccessException;

    int addMovie(Movie movie) throws DataAccessException;

    void updateMovie(Movie movie) throws DataAccessException;

    void deleteMovie(Integer movieID) throws DataAccessException;
}
