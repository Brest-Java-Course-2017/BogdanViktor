package com.epam.result.dao;

import java.util.Date;
import java.util.List;

/**
 * The {@code DirectorDAO} - is an interface that provides access to a database.
 * @author  Bogdan Viktor
 */
public interface MovieDAO {

    /**
     * Get all movie list created by this director.
     * @return all movie list created by this director.
     
     */
    List<Movie> getAllMoviesCreatedByDirector(Integer directorID);

    /**
     * Get movie by title and release date.
     * @param movieTitle is movie title.
     * @param releaseDate is movie release date.
     * @return movie.
     */
    Movie getMovieByTitleAndReleaseDate(String movieTitle, Date releaseDate);

    /**
     * Get movieDTO(movie with director's first and last name) by ID.
     * @param movieId is movie ID.
     * @return movieDTO.
     */
    MovieDTO getMovieDTOById(Integer movieId) ;

    /**
     * Get all movies list.
     * @return all movies list
     */
    List<Movie> getAllMovies() ;

    /**
     * Get list of all movieDTO(movie with director's first and last name).
     * @return list of movieDTO.
     */
    List<MovieDTO> getAllMovieDTO() ;

    /**
     * Get list of all movieDTO(movie with director's first and last name)
     * with a date filter.
     * @param fromDate start date.
     * @param toDate finish date.
     * @return list of movieDTO.
     */
    List<MovieDTO> getAllMovieDTOWithDateFilter(Date fromDate, Date toDate) ;

    /**
     * Get movie by ID.
     * @param movieID is movie ID.
     * @return movie.
     */
    Movie getMovieById(Integer movieID) ;

    /**
     * Adds the movie to the database and returns
     * the ID that the database assigned to it.
     * @param movie movie.
     * @return movie's ID.
     */
    int addMovie(Movie movie) ;

    /**
     * Updates the movie in the database and returns
     * the number of rows affected in data base.
     * @param movie movie.
     * @return the number of rows affected in data base.
     */
    int updateMovie(Movie movie) ;

    /**
     * Deletes the movie in the database and returns
     * the number of rows affected in data base.
     * @param movieID is movie ID.
     * @return the number of rows affected in data base.
     */
    int deleteMovie(Integer movieID) ;

}
