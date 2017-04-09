package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDAO;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The {@code MovieServiceImpl} - is an implementation of interface "MovieService".
 * @author  Bogdan Viktor
 */


@Service
@Transactional
public class MovieServiceImpl implements MovieService{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final Date MIN_DATE = new Date(0,1,1);
    private static final Date MAX_DATE = new Date();


    @Autowired
    MovieDAO movieDao;

    public void setMovieDao(MovieDAO movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAllMovies() {
        LOGGER.debug("getAllMovies()");
        return movieDao.getAllMovies();
    }

    @Override
    public List<Movie> getAllMoviesCreatedByDirector(Integer directorID) {
        Assert.notNull(directorID, "Director's ID should be null.");
        LOGGER.debug("getAllMoviesCreatedByDirector({})", directorID);
        return movieDao.getAllMoviesCreatedByDirector(directorID);
    }

    @Override
    public List<MovieDTO> getAllMovieDTO() {
        LOGGER.debug("getAllMovieDTO()");
        return movieDao.getAllMovieDTO();
    }

    @Override
    public List<MovieDTO> getAllMovieDTOWithDateFilter(Date startDate, Date endDate) {
        LOGGER.debug("getAllMovieDTOWithDateFilter({})",
                (startDate==null?"null":FORMATTER.format(startDate))+", "+
                        (endDate==null?"null":FORMATTER.format(endDate)));

        if(startDate==null && endDate==null) return movieDao.getAllMovieDTO();
        if(startDate==null) startDate = MIN_DATE;
        if(endDate==null) endDate = MAX_DATE;

        Assert.isTrue(endDate.compareTo(startDate)>0,
                "The start date should be greater than at the end");
        return movieDao.getAllMovieDTOWithDateFilter(startDate, endDate);
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        LOGGER.debug("getMovieById({})", movieId);
        Assert.notNull(movieId, "Movie ID should not be null.");
        return movieDao.getMovieById(movieId);
    }

    @Override
    public MovieDTO getMovieDTOById(Integer movieId){
        LOGGER.debug("getMovieDTOById({})", movieId);
        Assert.notNull(movieId, "Movie ID should not be null.");
        return movieDao.getMovieDTOById(movieId);
    }

    @Override
    public int addMovie(Movie movie) {
        Assert.notNull(movie, "Movie should not be null.");
        Assert.notNull(movie.getMovieTitle(), "The movie title should not be null.");
        Assert.notNull(movie.getReleaseDate(), "Movie release date should not be null");
        LOGGER.debug("addMovie({})", movie.getMovieTitle()+", "+movie.getReleaseDateAsString());
        Assert.isNull(movie.getMovieId(), "Movie ID should be null.");
        Assert.hasText(movie.getMovieTitle(), "Movie title should not be empty");
        Assert.isTrue(movie.getMovieTitle().length() <= 60, "The length of movie title " +
                "should be less than 60 characters.");
        Assert.isTrue((movie.getReleaseDate().compareTo(MAX_DATE) <= 0) &&
                        ((movie.getReleaseDate().compareTo(MIN_DATE) >= 0)),
                "The release date should be between 1900-01-01 and today's date.");
        Assert.notNull(movie.getMovieDirectorId(), "Movie director's ID should not be null.");
        Assert.notNull(movie.getRating(), "Movie rating should not be null.");
        Assert.isTrue(movie.getRating()>=0 && movie.getRating()<=10, "Movie rating should be from 0 to 10.");
        try{
            if(movieDao.getMovieByTitleAndReleaseDate(movie.getMovieTitle(), movie.getReleaseDate())!=null){
                throw new IllegalArgumentException(String.format(
                        "The movie \"%s\", release date - \"%s\", already exists in the database.",
                        movie.getMovieTitle(), movie.getReleaseDateAsString()));
            }
        } catch (DataAccessException ignore){  }
        return movieDao.addMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        Assert.notNull(movie, "Movie should not be null.");
        Assert.notNull(movie.getMovieTitle(), "The movie title should not be null.");
        Assert.notNull(movie.getReleaseDate(), "Movie release date should not be null");
        LOGGER.debug("updateMovie({})", movie.getMovieTitle()+", "+movie.getReleaseDateAsString());
        Assert.notNull(movie.getMovieId(), "Movie ID should not be null.");
        Assert.hasText(movie.getMovieTitle(), "Movie title should not be empty");
        Assert.isTrue(movie.getMovieTitle().length() <= 60, "The length of movie title " +
                "should be less than 60 characters.");
        Assert.isTrue((movie.getReleaseDate().compareTo(MAX_DATE) <= 0) &&
                        ((movie.getReleaseDate().compareTo(MIN_DATE) >= 0)),
                "The release date should be between 1900-01-01 and today's date.");
        Assert.notNull(movie.getMovieDirectorId(), "Movie director's ID should not be null.");
        Assert.notNull(movie.getRating(), "Movie rating should not be null.");
        Assert.isTrue(movie.getRating()>=0 && movie.getRating()<=10, "Movie rating should be from 0 to 10.");
        try{
            Movie movieTemp;
            movieTemp = movieDao.getMovieByTitleAndReleaseDate(movie.getMovieTitle(), movie.getReleaseDate());
            if(movieTemp!=null && !movieTemp.getMovieId().equals(movie.getMovieId())){
                throw new IllegalArgumentException(String.format(
                        "The movie \"%s\", release date - \"%s\", already exists in the database",
                        movie.getMovieTitle(), movie.getReleaseDateAsString()));
            }
        } catch (DataAccessException e){  }
        int numberOfRowsAffected = movieDao.updateMovie(movie);
        if(numberOfRowsAffected==0){
            throw new IllegalArgumentException(String.format(
                    "The movie with ID=%d does not exist in the database.", movie.getMovieId()));
        }
    }

    @Override
    public void deleteMovie(Integer movieID) {
        Assert.notNull(movieID, "Movie ID should not be null");
        LOGGER.debug("deleteMovie({})", movieID);
        int numberOfRowsAffected = movieDao.deleteMovie(movieID);
        if(numberOfRowsAffected==0){
            throw new IllegalArgumentException(String.format(
                    "The movie with ID=%d does not exist in the database.", movieID));
        }
    }
}
