package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;

import java.util.Date;
import java.util.List;

/**
 * The {@code MovieService} - is a service layer of application.
 * @author  Bogdan Viktor
 */
public interface MovieService {

    /**
     * Get all movie list created by this director.
     * @return all movie list created by this director.
     */
    List<Movie> getAllMoviesCreatedByDirector(Integer directorID);

    /**
     * Get all movies list.
     * @return all movies list
     */
    List<Movie> getAllMovies();

    /**
     * Get movie by ID.
     * @param movieId is movie identifier.
     * @return movie.
     */
    Movie getMovieById(Integer movieId);
    
    /**
     * Get list of all movieDTO(movie with director's first and last name).
     * @return list of movieDTO.
     */
    List<MovieDTO> getAllMovieDTO();

    /**
     * Get list of all movieDTO(movie with director's first and last name)
     * with a date filter.
     * @param fromDate start date.
     * @param toDate finish date.
     * @return list of movieDTO.
     */
    List<MovieDTO> getAllMovieDTOWithDateFilter(Date fromDate, Date toDate);

    /**
     * Get movieDTO(movie with director's first and last name) by ID.
     * @param movieId is movie ID.
     * @return movieDTO.
     */
    MovieDTO getMovieDTOById(Integer movieId);

    /**
     * Adds the movie to the database and returns
     * the ID that the database assigned to it.
     * @param movie movie.
     * @return movie's ID.
     */
    int addMovie(Movie movie);

    /**
     * Updates the movie in the database and returns
     * the number of rows affected in data base.
     * @param movie movie.
     */
    void updateMovie(Movie movie);

    /**
     * Deletes the movie in the database and returns
     * the number of rows affected in data base.
     * @param movieID is movie ID.
     */
    void deleteMovie(Integer movieID);
}
