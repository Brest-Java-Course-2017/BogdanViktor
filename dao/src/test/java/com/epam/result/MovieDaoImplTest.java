package com.epam.result;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDAO;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sw0rd on 06.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class MovieDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Autowired
    MovieDAO movieDAO;


    @Test
    public void testGetAllMovies() throws DataAccessException {
        LOGGER.debug("test: getAllMoviesWithDirectorName()");
        List<MovieDTO> list  = movieDAO.getAllMoviesWithDirectorName();
        Assert.assertTrue(list.size()==13);
    }


    @Test
    public void testGetAllMoviesCreatedByDirector(){
        LOGGER.debug("test: getAllMoviesCreatedByDirector()");
        List<Movie> list = movieDAO.getAllMoviesCreatedByDirector(1);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testAddMovie(){
        LOGGER.debug("test: AddMovie()");

        int initialSize = movieDAO.getAllMovies().size();
        Movie newMovie = new Movie(null, "title movie", new LocalDate(), 7.0, 1);
        movieDAO.addMovie(newMovie);
        List<Movie> list = movieDAO.getAllMovies();
        Assert.assertEquals(initialSize+1, list.size());
    }

    @Test
    public void testUpdateMovie(){
        LOGGER.debug("test: updateMovie()");

        Movie newMovie = new Movie(2, "title movie", new LocalDate(), 7.0, 1);
        movieDAO.updateMovie(newMovie);
        List<Movie> list = movieDAO.getAllMovies();
        Assert.assertEquals(newMovie, movieDAO.getAllMovies().get(1));
    }

    @Test
    public void testDeleteMovie(){
        LOGGER.debug("test: deleteMovie()");

        List<Movie> list = movieDAO.getAllMovies();
        int initialSize= list.size();
        movieDAO.deleteMovie(2);
        Assert.assertEquals(initialSize-1, movieDAO.getAllMovies().size());
    }

    @Test
    public void testGetAllMoviesWithDateFilter(){
        LOGGER.debug("test: getAllMoviesWithDateFilter()");

        LocalDate fromDate = FORMATTER.parseLocalDate("2000-06-08");
        LocalDate toDate = new LocalDate();
        List<MovieDTO> list = movieDAO.getAllMoviesWithDateFilter(fromDate, toDate);
        Assert.assertEquals(7, list.size());
    }


    @Test
    public void testGetMovieByTitleAndReleaseDate(){
        LOGGER.debug("test: getMovieByTitleAndReleaseDate()");

        String movieTitle = "The Lord of the Rings: The FelloWShip of the Ring";
        LocalDate date = FORMATTER.parseLocalDate("2001-12-19");
        Assert.assertEquals(movieDAO.getAllMovies().get(7),
        movieDAO.getMovieByTitleAndReleaseDate(movieTitle, date));
    }
}
