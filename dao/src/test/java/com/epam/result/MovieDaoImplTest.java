package com.epam.result;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDAO;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sw0rd on 06.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class MovieDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    MovieDAO movieDAO;


    @Test
    public void testGetAllMovies() throws Exception {
        LOGGER.debug("test: getAllMoviesWithDirectorName()");
        List<MovieDTO> list  = movieDAO.getAllMoviesWithDirectorName();
        Assert.assertTrue(list.size()==13);
    }


    @Test
    public void testGetAllMoviesCreatedByDirector() throws Exception{
        LOGGER.debug("test: getAllMoviesCreatedByDirector()");
        List<Movie> list = movieDAO.getAllMoviesCreatedByDirector(1);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testAddMovie() throws Exception{
        LOGGER.debug("test: AddMovie()");

        int initialSize = movieDAO.getAllMovies().size();
        Movie newMovie = new Movie(null, "title movie", new Date(), 7.0, 1);
        movieDAO.addMovie(newMovie);
        List<Movie> list = movieDAO.getAllMovies();
        Assert.assertEquals(initialSize+1, list.size());
    }

    @Test
    public void testUpdateMovie() throws Exception{
        LOGGER.debug("test: updateMovie()");

        Movie newMovie = new Movie(2, "title movie", FORMATTER.parse("2000-06-08"), 7.0, 1);
        movieDAO.updateMovie(newMovie);
        List<Movie> list = movieDAO.getAllMovies();
        Assert.assertEquals(newMovie, movieDAO.getAllMovies().get(1));
    }

    @Test
    public void testGetMovieById() throws Exception{
        LOGGER.debug("test: getMovieById()");
        Movie newMovie = new Movie(2, "Catch Me If You Can", FORMATTER.parse("2002-12-25"), 8.0, 1);
        Assert.assertEquals(newMovie, movieDAO.getMovieById(2));
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
    public void testGetAllMoviesWithDateFilter() throws Exception{
        LOGGER.debug("test: getAllMoviesWithDateFilter()");

        Date fromDate = FORMATTER.parse("2000-06-08");
        Date toDate = new Date();
        List<MovieDTO> list = movieDAO.getAllMoviesWithDateFilter(fromDate, toDate);
        Assert.assertEquals(7, list.size());
    }


    @Test
    public void testGetMovieByTitleAndReleaseDate() throws Exception {
        LOGGER.debug("test: getMovieByTitleAndReleaseDate()");

        String movieTitle = "The Lord of the Rings: The FelloWShip of the Ring";
        Date date = FORMATTER.parse("2001-12-19");
        Assert.assertEquals(movieDAO.getAllMovies().get(7),
        movieDAO.getMovieByTitleAndReleaseDate(movieTitle, date));
    }
}
