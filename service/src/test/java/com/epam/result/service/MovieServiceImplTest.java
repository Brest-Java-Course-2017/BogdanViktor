package com.epam.result.service;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
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
 * The {@code MovieServiceImplTest} - is a test class for MovieServiceImpl.
 * @author  Bogdan Viktor
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
    public void test_get_all_movies_created_by_director() throws Exception{
        LOGGER.debug("test: getAllMoviesCreatedByDirector()");
        assertEquals(3, movieService.getAllMoviesCreatedByDirector(1).size());
    }


    @Test
    public void test_get_all_movies_created_by_director_when_ID_not_exist() throws Exception{
        LOGGER.debug("test: getAllMoviesCreatedByDirector(), when id does not exist in DB");
        assertEquals(0, movieService.getAllMoviesCreatedByDirector(7).size());
    }

    @Test
    public void test_get_all_movies() throws Exception{
        LOGGER.debug("test: getAllMovies()");
        assertEquals(13, movieService.getAllMovies().size());
    }

    @Test
    public void test_get_all_movieDTO() throws Exception{
        LOGGER.debug("test: getAllMovieDTO()");
        assertEquals(13, movieService.getAllMovieDTO().size());
    }

    @Test
    public void test_get_all_movieDTO_with_date_filter() throws Exception{
        LOGGER.debug("test: getAllMovieDTOWithDateFilter()");
        Date startDate = FORMATTER.parse("2000-01-12");
        Date endDate = FORMATTER.parse("2005-01-12");
        List<MovieDTO> list = movieService.getAllMovieDTOWithDateFilter(startDate, endDate);
        assertEquals(4, list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_get_all_movieDTO_with_date_filter_when_start_date_greater_than_end_date() throws Exception{
        LOGGER.debug("test: getAllMovieDTOWithDateFilter(), when start date greater than end date");
        Date startDate = FORMATTER.parse("2009-01-12");
        Date endDate = FORMATTER.parse("2005-01-12");
        movieService.getAllMovieDTOWithDateFilter(startDate, endDate);
    }

    @Test
    public void test_get_all_movieDTO_with_date_filter_with_nulls() throws Exception{
        LOGGER.debug("test: getAllMovieDTOWithDateFilter(). Test with startDate==null, endDate==null");
        List<MovieDTO> list = movieService.getAllMovieDTOWithDateFilter(null, null);
        assertEquals(13, list.size());
    }

    @Test
    public void test_get_all_movieDTO_with_date_filter_with_start_date_null() throws Exception{
        LOGGER.debug("test: getAllMovieDTOWithDateFilter(). Test with fromDate==null, toDate!=null");
        Date toDate = FORMATTER.parse("1994-10-14");
        List<MovieDTO> list = movieService.getAllMovieDTOWithDateFilter(null, toDate);
        assertEquals(5, list.size());
    }

    @Test
    public void test_get_all_movieDTO_with_date_filter_with_end_date_null() throws Exception{
        LOGGER.debug("test: getAllMovieDTOWithDateFilter(). Test with fromDate!=null, toDate==null");
        Date fromDate = FORMATTER.parse("1994-10-14");
        List<MovieDTO> list = movieService.getAllMovieDTOWithDateFilter(fromDate, null);
        assertEquals(9, list.size());
    }

    @Ignore
    @Test
    public void test_get_movie_by_id() throws Exception{
        LOGGER.debug("test: getMovieById()");
        Movie testMovie = new Movie(6, "Prometheus", FORMATTER.parse("2012-06-08"), 7.0, 3);
        assertEquals(testMovie, movieService.getMovieById(6));
    }

    @Test
    public void test_get_movieDTO_by_id() throws Exception{
        LOGGER.debug("test: getMovieDTOById()");
        MovieDTO testMovieDTO = new MovieDTO(6, "Prometheus", FORMATTER.parse("2012-06-08"), 7.0,"Scott", "Ridley");
        assertEquals(testMovieDTO, movieService.getMovieDTOById(6));
    }

    @Test
    public void test_add_movie() throws Exception{
        LOGGER.debug("test: addMovie()");
        int initialSize = movieService.getAllMovies().size();
        Movie newMovie = new Movie(null, "title movie", FORMATTER.parse("2017-03-13"), 7.0, 1);
        movieService.addMovie(newMovie);
        List<Movie> list = movieService.getAllMovies();
        assertEquals(initialSize+1, list.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_movie_when_release_date_from_future() throws Exception{
        LOGGER.debug("test: addMovie(), when release date from future");
        Movie newMovie = new Movie(null, "title movie", FORMATTER.parse("3000-03-13"), 7.0, 1);
        movieService.addMovie(newMovie);
    }

    @Test
    public void test_update_movie() throws Exception{
        LOGGER.debug("test: updateMovie()");
        Movie newMovie = new Movie(2, "title movie", FORMATTER.parse("1997-05-13"), 7.0, 1);
        movieService.updateMovie(newMovie);
        assertEquals(newMovie, movieService.getAllMovies().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_movie_when_rating_greater_than_10() throws Exception{
        LOGGER.debug("test: updateMovie(), when rating greater than 10");
        Movie newMovie = new Movie(2, "title movie", FORMATTER.parse("1997-05-13"), 20.9, 1);
        movieService.updateMovie(newMovie);
    }

    @Test
    public void test_delete_movie() throws Exception{
        LOGGER.debug("test: deleteMovie()");
        List<Movie> list = movieService.getAllMovies();
        int initialSize= list.size();
        movieService.deleteMovie(2);
        assertEquals(initialSize-1, movieService.getAllMovies().size());
    }

}