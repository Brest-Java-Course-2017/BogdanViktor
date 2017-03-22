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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The {@code MovieDaoImplTest} - is a test class for MovieDaoImpl.
 * @author  Bogdan Viktor
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
    public void test_get_all_movies() throws Exception {
        LOGGER.debug("test: getAllMovieDTO()");
        List<MovieDTO> list  = movieDAO.getAllMovieDTO();
        Assert.assertTrue(list.size()==13);
    }


    @Test
    public void test_get_all_movies_created_by_director() throws Exception{
        LOGGER.debug("test: getAllMoviesCreatedByDirector()");
        List<Movie> list = movieDAO.getAllMoviesCreatedByDirector(1);
        assertEquals(3, list.size());
    }

    @Test
    public void test_get_all_movies_created_by_director_when_he_does_not_exist() throws Exception{
        LOGGER.debug("test: getAllMoviesCreatedByDirector(), when director does not exist");
        List<Movie> list = movieDAO.getAllMoviesCreatedByDirector(14);
        assertEquals(0, list.size());
    }

    @Test
    public void test_add_movie() throws Exception{
        LOGGER.debug("test: AddMovie()");
        int initialSize = movieDAO.getAllMovies().size();
        Movie newMovie = new Movie(null, "title movie", new Date(), 7.0, 1);
        movieDAO.addMovie(newMovie);
        List<Movie> list = movieDAO.getAllMovies();
        assertEquals(initialSize+1, list.size());
    }

    @Test
    public void test_update_movie() throws Exception{
        LOGGER.debug("test: updateMovie()");
        Movie newMovie = new Movie(2, "title movie", FORMATTER.parse("2000-06-08"), 7.0, 1);
        movieDAO.updateMovie(newMovie);
        assertEquals(newMovie, movieDAO.getAllMovies().get(1));
    }

    @Test(expected = NullPointerException.class)
    public void test_update_movie_with_null() throws Exception{
        LOGGER.debug("test: updateMovie(), with null");
        movieDAO.updateMovie(null);
    }

    @Test
    public void test_get_movie_by_id() throws Exception{
        LOGGER.debug("test: getMovieById()");
        Movie newMovie = new Movie(2, "Catch Me If You Can", FORMATTER.parse("2002-12-25"), 8.0, 1);
        assertEquals(newMovie, movieDAO.getMovieById(2));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_movie_by_id_when_it_does_exist_in_DB() throws Exception{
        LOGGER.debug("test: getMovieById()");
        movieDAO.getMovieById(20);
    }

    @Test
    public void test_delete_movie(){
        LOGGER.debug("test: deleteMovie()");
        List<Movie> list = movieDAO.getAllMovies();
        int initialSize= list.size();
        assertEquals(1,movieDAO.deleteMovie(2));
        assertEquals(initialSize-1, movieDAO.getAllMovies().size());
    }

    @Test
    public void test_get_all_movieDTO_with_date_filter() throws Exception{
        LOGGER.debug("test: getAllMovieDTOWithDateFilter()");
        Date fromDate = FORMATTER.parse("2000-06-08");
        Date toDate = FORMATTER.parse("2016-06-08");
        List<MovieDTO> list = movieDAO.getAllMovieDTOWithDateFilter(fromDate, toDate);
        assertEquals(7, list.size());
    }

    @Test
    public void test_get_movie_by_title_and_release_date() throws Exception {
        LOGGER.debug("test: getMovieByTitleAndReleaseDate()");
        String movieTitle = "The Lord of the Rings: The FelloWShip of the Ring";
        Date date = FORMATTER.parse("2001-12-19");
        assertEquals(movieDAO.getAllMovies().get(7),
        movieDAO.getMovieByTitleAndReleaseDate(movieTitle, date));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_movie_by_title_and_release_date_negative() throws Exception {
        LOGGER.debug("test: getMovieByTitleAndReleaseDate()");
        String movieTitle = "The Lord";
        Date date = FORMATTER.parse("2001-12-19");
        movieDAO.getMovieByTitleAndReleaseDate(movieTitle, date);
    }

    @Test
    public void test_get_movieDTO_by_id() throws Exception{
        LOGGER.debug("test: getMovieById()");
        MovieDTO newMovieDTO = new MovieDTO(2, "Catch Me If You Can", FORMATTER.parse("2002-12-25"), 8.0, "Steven", "Spielberg");
        assertEquals(newMovieDTO, movieDAO.getMovieDTOById(2));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_movieDTO_by_id_with_null() throws Exception{
        LOGGER.debug("test: getMovieById() with null");
        movieDAO.getMovieDTOById(null);
    }
}
