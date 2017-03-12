package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.joda.time.LocalDate;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
public interface MovieService {

    List<Movie> getAllMoviesCreatedByDirector(Integer directorID) throws DataAccessException;

    List<Movie> getAllMovies() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException;

    List<MovieDTO> getAllMoviesWithDateFilter(LocalDate from, LocalDate to) throws DataAccessException;

    int addMovie(Movie movie) throws DataAccessException;

    void updateMovie(Movie movie) throws DataAccessException;

    void deleteMovie(Integer movieID) throws DataAccessException;
}
