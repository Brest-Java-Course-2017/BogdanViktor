package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDAO;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
public class MovieServiceImpl implements MovieService{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final LocalDate MIN_DATE = FORMATTER.parseLocalDate("1000-01-01");
    private static final LocalDate MAX_DATE = FORMATTER.parseLocalDate("9999-12-31");


    @Autowired
    MovieDAO movieDao;

    public void setMovieDao(MovieDAO movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAllMovies() throws DataAccessException {
        LOGGER.debug("getAllMovies()");
        return movieDao.getAllMovies();
    }

    @Override
    public List<Movie> getAllMoviesCreatedByDirector(Integer directorID) throws DataAccessException {
        Assert.notNull(directorID, "Director's ID should be null.");
        LOGGER.debug("getAllMoviesCreatedByDirector({})", directorID);
        return movieDao.getAllMoviesCreatedByDirector(directorID);
    }

    @Override
    public List<MovieDTO> getAllMoviesWithDirectorName() throws DataAccessException {
        LOGGER.debug("getAllMoviesWithDirectorName()");
        return movieDao.getAllMoviesWithDirectorName();
    }

    @Override
    public List<MovieDTO> getAllMoviesWithDateFilter(LocalDate fromDate, LocalDate toDate) throws DataAccessException {
        LOGGER.debug("getAllMoviesWithDateFilter({})");
        if(fromDate==null && toDate==null) return movieDao.getAllMoviesWithDirectorName();
        if(fromDate==null) fromDate = MIN_DATE;
        if(toDate==null) toDate = MAX_DATE;
        return movieDao.getAllMoviesWithDateFilter(fromDate, toDate);
    }

    @Override
    public int addMovie(Movie movie) throws DataAccessException {
        Assert.notNull(movie, "Movie should not be null.");
        LOGGER.debug("addMovie({})", movie.getMovieTitle()+", "+movie.getReleaseDateAsString());
        Assert.isNull(movie.getMovieID(), "Movie ID should be null.");
        Assert.hasText(movie.getMovieTitle(), "Movie title should not be null");
        Assert.notNull(movie.getReleaseDate(), "Movie release date should not be null");
        Assert.notNull(movie.getMovieDirectorID(), "Movie director's ID should not be null");
        Assert.notNull(movie.getRating(), "Movie rating should not be null");
        Assert.isTrue(movie.getRating()>=0 && movie.getRating()<=10, "Movie rating should be from 0 to 10");
        try{
            if(movieDao.getMovieByTitleAndReleaseDate(movie.getMovieTitle(), movie.getReleaseDate())!=null){
                throw new IllegalArgumentException(String.format(
                        "The movie %s, release date - %s, already exists in the database",
                        movie.getMovieTitle(), movie.getReleaseDateAsString()));
            }
        } catch (DataAccessException e){  }
        return movieDao.addMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) throws DataAccessException {
        Assert.notNull(movie, "Movie should not be null.");
        LOGGER.debug("updateMovie({})", movie.getMovieTitle()+", "+movie.getReleaseDateAsString());
        Assert.notNull(movie.getMovieID(), "Movie ID should not be null.");
        Assert.hasText(movie.getMovieTitle(), "Movie title should not be null");
        Assert.notNull(movie.getReleaseDate(), "Movie release date should not be null");
        Assert.notNull(movie.getMovieDirectorID(), "Movie director's ID should not be null");
        try{
            if(movieDao.getMovieByTitleAndReleaseDate(movie.getMovieTitle(), movie.getReleaseDate())!=null){
                throw new IllegalArgumentException(String.format(
                        "The movie %s, release date - %s, already exists in the database",
                        movie.getMovieTitle(), movie.getReleaseDateAsString()));
            }
        } catch (DataAccessException e){  }
        int numberOfRowsAffected = movieDao.updateMovie(movie);
        if(numberOfRowsAffected==0) throw new IllegalArgumentException(String.format(
                "The movie with ID=%d does not exist in the database.", movie.getMovieID()));
    }

    @Override
    public void deleteMovie(Integer movieID) throws DataAccessException {
        Assert.notNull(movieID, "Movie ID should not be null");
        LOGGER.debug("deleteMovie({})", movieID);
        int numberOfRowsAffected = movieDao.deleteMovie(movieID);
        if(numberOfRowsAffected==0) throw new IllegalArgumentException(String.format(
                "The movie with ID=%d does not exist in the database.", movieID));
    }
}
