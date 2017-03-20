package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDAO;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
public class MovieServiceImpl implements MovieService{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final Date MIN_DATE = new Date(1000-1900,1,1);
    private static final Date MAX_DATE = new Date(9999-1900,11,31);


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
        LOGGER.debug("getAllMovieDTO()");
        return movieDao.getAllMovieDTO();
    }

    @Override
    public List<MovieDTO> getAllMoviesWithDateFilter(Date fromDate, Date toDate) throws DataAccessException {
        LOGGER.debug("getAllMoviesWithDateFilter({})",
                (fromDate==null?"null":FORMATTER.format(fromDate))+", "+
                (toDate==null?"null":FORMATTER.format(toDate)));
        if(fromDate==null && toDate==null) return movieDao.getAllMovieDTO();
        if(fromDate==null) fromDate = MIN_DATE;
        if(toDate==null) toDate = MAX_DATE;
        Assert.isTrue(toDate.compareTo(fromDate)>0, "The date in the beginning should be greater than at the end");
        return movieDao.getAllMoviesWithDateFilter(fromDate, toDate);
    }

    @Override
    public Movie getMovieById(Integer movieId) throws DataAccessException {
        LOGGER.debug("getMovieById({})", movieId);
        Assert.notNull(movieId, "Movie ID should not be null.");
        return movieDao.getMovieById(movieId);
    }

    @Override
    public MovieDTO getMovieByIdWithDirectorName(Integer movieId) throws DataAccessException{
        LOGGER.debug("getMovieDTOById({})", movieId);
        Assert.notNull(movieId, "Movie ID should not be null.");
        return movieDao.getMovieDTOById(movieId);
    }

    @Override
    public int addMovie(Movie movie) throws DataAccessException {
        Assert.notNull(movie, "Movie should not be null.");
        LOGGER.debug("addMovie({})", movie.getMovieTitle()+", "+movie.getReleaseDateAsString());
        Assert.isNull(movie.getMovieId(), "Movie ID should be null.");
        Assert.hasText(movie.getMovieTitle(), "Movie title should not be null");
        Assert.notNull(movie.getReleaseDate(), "Movie release date should not be null");
        Assert.isTrue(movie.getReleaseDate().compareTo(MIN_DATE)>0, "The release date should be greater than 1000-01-01");
        Assert.isTrue(movie.getReleaseDate().compareTo(new Date())<0, "The release date should not be greater than today's date");
        Assert.notNull(movie.getMovieDirectorId(), "Movie director's ID should not be null");
        Assert.notNull(movie.getRating(), "Movie rating should not be null");
        Assert.isTrue(movie.getRating()>=0 && movie.getRating()<=10, "Movie rating should be from 0 to 10");
        try{
            if(movieDao.getMovieByTitleAndReleaseDate(movie.getMovieTitle(), movie.getReleaseDate())!=null){
                throw new IllegalArgumentException(String.format(
                        "The movie \"%s\", release date - \"%s\", already exists in the database",
                        movie.getMovieTitle(), movie.getReleaseDateAsString()));
            }
        } catch (DataAccessException e){  }
        return movieDao.addMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) throws DataAccessException {
        Assert.notNull(movie, "Movie should not be null.");
        LOGGER.debug("updateMovie({})", movie.getMovieTitle()+", "+movie.getReleaseDateAsString());
        Assert.notNull(movie.getMovieId(), "Movie ID should not be null.");
        Assert.hasText(movie.getMovieTitle(), "Movie title should not be null");
        Assert.notNull(movie.getReleaseDate(), "Movie release date should not be null");
        Assert.notNull(movie.getMovieDirectorId(), "Movie director's ID should not be null");
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
        if(numberOfRowsAffected==0) throw new IllegalArgumentException(String.format(
                "The movie with ID=%d does not exist in the database.", movie.getMovieId()));
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
