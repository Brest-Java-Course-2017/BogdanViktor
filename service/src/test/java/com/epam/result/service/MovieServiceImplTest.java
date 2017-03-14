package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by sw0rd on 12.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class MovieServiceImplTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    MovieService movieService;


    @Test
    public void testGetAllMoviesCreatedByDirector() throws Exception{
        LOGGER.debug("test: getAllMoviesCreatedByDirector()");
        assertEquals(3, movieService.getAllMoviesCreatedByDirector(1).size());
    }

    @Test
    public void testGetAllMovies() throws Exception{
        LOGGER.debug("test: getAllMovies()");
        assertEquals(13, movieService.getAllMovies().size());
    }

    @Test
    public void testGetAllMoviesWithDirectorName() throws Exception{
        LOGGER.debug("test: getAllMoviesWithDirectorName()");
        assertEquals(13, movieService.getAllMoviesWithDirectorName().size());
    }

    @Test
    public void testGetAllMoviesWithDateFilter() throws Exception{
        LOGGER.debug("test: getAllMoviesWithDateFilter()");
        Date fromDate = FORMATTER.parse("2000-01-12");
        Date toDate = FORMATTER.parse("2005-01-12");
        List<MovieDTO> list = movieService.getAllMoviesWithDateFilter(fromDate, toDate);
        assertEquals(4, list.size());
    }

    @Test
    public void testGetAllMoviesWithDateFilterWithNulls() throws Exception{
        LOGGER.debug("test: getAllMoviesWithDateFilter(). Test with fromDate==null, toDate==null");
        List<MovieDTO> list = movieService.getAllMoviesWithDateFilter(null, null);
        assertEquals(13, list.size());
    }

    @Test
    public void testGetAllMoviesWithDateFilterWithFromDateNull() throws Exception{
        LOGGER.debug("test: getAllMoviesWithDateFilter(). Test with fromDate==null, toDate!=null");
        //    ('Pulp Fiction', '1994-10-14', 8.9, 2),
        Date toDate = FORMATTER.parse("1994-10-14");
        List<MovieDTO> list = movieService.getAllMoviesWithDateFilter(null, toDate);
        assertEquals(5, list.size());
    }

    @Test
    public void testGetAllMoviesWithDateFilterWithToDateNull() throws Exception{
        LOGGER.debug("test: getAllMoviesWithDateFilter(). Test with fromDate==null, toDate!=null");
        Date fromDate = FORMATTER.parse("1994-10-14");
        List<MovieDTO> list = movieService.getAllMoviesWithDateFilter(fromDate, null);
        assertEquals(9, list.size());
    }

    @Test
    public void testAddMovie() throws Exception{
        LOGGER.debug("test: addMovie()");
        int initialSize = movieService.getAllMovies().size();
        Movie newMovie = new Movie(null, "title movie", new Date(), 7.0, 1);
        movieService.addMovie(newMovie);
        List<Movie> list = movieService.getAllMovies();
        assertEquals(initialSize+1, list.size());
    }

    @Test
    public void testUpdateMovie() throws Exception{
        LOGGER.debug("test: updateMovie()");
        Movie newMovie = new Movie(2, "title movie", FORMATTER.parse("1997-05-13"), 7.0, 1);
        movieService.updateMovie(newMovie);
        assertEquals(newMovie, movieService.getAllMovies().get(1));
    }

    @Test
    public void testDeleteMovie() throws Exception{
        LOGGER.debug("test: deleteMovie()");
        List<Movie> list = movieService.getAllMovies();
        int initialSize= list.size();
        movieService.deleteMovie(2);
        assertEquals(initialSize-1, movieService.getAllMovies().size());
    }

}