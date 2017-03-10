package com.epam.result;

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
public class MovieAndDirectorDaoImplTest{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");


    private static final Director testDirector = new Director("testFirstName", "testLastName");

    @Autowired
    MovieAndDirectorDAO movieAndDirectorDAO;


    @Test
    public void testGetAllDirectors() {
        LOGGER.debug("test: getAllDirectors()");
        List<Director> list = movieAndDirectorDAO.getAllDirectors();
        System.out.println(list);
        Assert.assertTrue(list.size()>0);
    }


    @Test
    public void testGetAllMovies() throws DataAccessException {
        LOGGER.debug("test: getAllMoviesWithDirectorName()");
        List<MovieDTO> list  = movieAndDirectorDAO.getAllMoviesWithDirectorName();
        Assert.assertTrue(list.size()==13);
    }

    @Test
    public void testGetAllDirectorsWithMovieRating(){
        LOGGER.debug("test: getAllDirectorsWithMovieRating()");

        List<DirectorDTO> list = movieAndDirectorDAO.getAllDirectorsWithMovieRating();
        Assert.assertTrue(list.size()==4);
    }

    @Test
    public void addDirector(){
        LOGGER.debug("test: addDirector()");

        List<Director> list = movieAndDirectorDAO.getAllDirectors();
        int size = list.size();
        movieAndDirectorDAO.addDirector(testDirector);
        list = movieAndDirectorDAO.getAllDirectors();
        Assert.assertEquals(size+1, list.size());
    }

    @Test
    public void updateDirector(){
        LOGGER.debug("test: updateDirector()");
        Director newDirector = new Director(1, "testValue", "testValue2");
        movieAndDirectorDAO.updateDirector(newDirector);
        Assert.assertEquals(newDirector, movieAndDirectorDAO.getDirectorById(1));

    }

    @Test
    public void testGetDirectorById(){
        LOGGER.debug("test: GetDirectorById()");
        Director newDirector = new Director(1, "Steven", "Spielberg");
        Assert.assertEquals(newDirector, movieAndDirectorDAO.getDirectorById(1));
    }

    @Test
    public void testDeleteDirector(){
        LOGGER.debug("test: deleteDirector()");
        int id = movieAndDirectorDAO.addDirector(testDirector);
        int size = movieAndDirectorDAO.getAllDirectors().size();
        movieAndDirectorDAO.deleteDirector(id);
        Assert.assertEquals(size-1, movieAndDirectorDAO.getAllDirectors().size());
    }

    @Test
    public void testGetAllMovieByDirector(){
        LOGGER.debug("getAllMovieByDirector()");
        List<Movie> list = movieAndDirectorDAO.getAllMovieByDirector(1);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testAddMovie(){
        LOGGER.debug("AddMovie()");

        int initialSize =movieAndDirectorDAO.getAllMovies().size();
        Movie newMovie = new Movie(null, "title movie", new LocalDate(), 7.0, 1);
        movieAndDirectorDAO.addMovie(newMovie);
        List<Movie> list = movieAndDirectorDAO.getAllMovies();
        Assert.assertEquals(initialSize+1, list.size());
    }

    @Test
    public void testUpdateMovie(){
        LOGGER.debug("updateMovie()");

        Movie newMovie = new Movie(2, "title movie", new LocalDate(), 7.0, 1);
        movieAndDirectorDAO.updateMovie(newMovie);
        List<Movie> list = movieAndDirectorDAO.getAllMovies();
        Assert.assertEquals(newMovie, movieAndDirectorDAO.getAllMovies().get(1));
    }

    @Test
    public void testDeleteMovie(){
        LOGGER.debug("deleteMovie()");

        List<Movie> list = movieAndDirectorDAO.getAllMovies();
        int initialSize= list.size();
        movieAndDirectorDAO.deleteMovie(2);
        Assert.assertEquals(initialSize-1, movieAndDirectorDAO.getAllMovies().size());
    }

    @Test
    public void testGetAllMoviesWithDateFilter(){
        LOGGER.debug("getAllMoviesWithDateFilter()");

        LocalDate fromDate = FORMATTER.parseLocalDate("2000-06-08");
        LocalDate toDate = new LocalDate();
        List<Movie> list = movieAndDirectorDAO.getAllMoviesWithDateFilter(fromDate, toDate);
        Assert.assertEquals(7, list.size());
    }

    @Test
    public void testGetDirectorByFirstAndLastName(){
        LOGGER.debug("getDirectorByFirstAndLastName()");

        String firstName = "StEveN";
        String lastName = "SpieLBerg";

        Assert.assertEquals(movieAndDirectorDAO.getDirectorById(1),
                movieAndDirectorDAO.getDirectorByFirstAndLastName(firstName, lastName));
    }

    @Test
    public void testGetMovieByTitleAndReleaseDate(){
        LOGGER.debug("getMovieByTitleAndReleaseDate()");
        /*'The Lord of the Rings: The Fellowship of the Ring', '2001-12-19'*/

        String movieTitle = "The Lord of the Rings: The FelloWShip of the Ring";
        LocalDate date = FORMATTER.parseLocalDate("2001-12-19");

        Assert.assertEquals(movieAndDirectorDAO.getAllMovies().get(7),
                movieAndDirectorDAO.getMovieByTitleAndReleaseDate(movieTitle, date));
    }
}
