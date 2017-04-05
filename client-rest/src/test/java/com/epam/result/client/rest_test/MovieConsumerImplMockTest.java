package com.epam.result.client.rest_test;

import com.epam.result.client.rest_api.MovieConsumer;
import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author  Bogdan Viktor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:client-rest-test.xml"})
public class MovieConsumerImplMockTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final MovieDTO MOVIE_DTO_TEST1 = new MovieDTO(2, "testTitle1", new Date(130, 4, 4),
            5.6, "firstName1", "lastName1");
    private static final MovieDTO MOVIE_DTO_TEST2 = new MovieDTO(3, "testTitle2", new Date(34, 1, 4),
            7.8, "firstName2", "lastName2");
    private static final Movie MOVIE_TEST1 = new Movie(3, "testTitle1", new Date(34, 1, 4), 7.8, 1);
    private static final Movie MOVIE_TEST2 = new Movie(1, "testTitle2", new Date(324, 1, 4), 4.6, 3);



    @Value("${protocol}://${host}:${port}${prefix}")
    private String url;

    @Value("${point.movie}")
    private String urlMovie;

    @Value("${point.movies}")
    private String urlMovies;

    @Autowired
    MovieConsumer movieConsumer;

    @Autowired
    private RestTemplate mockRestTemplate;

    @After
    public void tearDown(){
        verify(mockRestTemplate);
        reset(mockRestTemplate);
    }


    @Test
    public void test_get_all_movies_created_by_director() {
        LOGGER.debug("test: getAllMoviesCreatedByDirector()");

        List expectedResult = new ArrayList();
        expectedResult.add(MOVIE_TEST1);
        expectedResult.add(MOVIE_TEST2);
        expect(mockRestTemplate.getForEntity(url+urlMovies+"/createdBy/{directorId}", Object.class, 4))
                .andReturn(new ResponseEntity<Object>(expectedResult, HttpStatus.OK));
        replay(mockRestTemplate);

        assertEquals(movieConsumer.getAllMoviesCreatedByDirector(4).size(), 2);

    }


    @Test
    public void test_get_movie_by_id() throws Exception{
        LOGGER.debug("test: getMovieById()");
        expect(mockRestTemplate.getForEntity(url + urlMovie+"/{movieId}", Movie.class, 3))
                .andReturn(new ResponseEntity<Movie>(MOVIE_TEST1, HttpStatus.OK));
        replay(mockRestTemplate);

        assertEquals(MOVIE_TEST1, movieConsumer.getMovieById(3));
    }

    @Test
    public void test_get_all_movies_DTO() {
        LOGGER.debug("test: getAllMovieDTO()");

        List expectedResult = new ArrayList();
        expectedResult.add(MOVIE_DTO_TEST1);
        expectedResult.add(MOVIE_DTO_TEST2);
        expect(mockRestTemplate.getForEntity(url+urlMovies, Object.class))
                .andReturn(new ResponseEntity<Object>(expectedResult, HttpStatus.OK));
        replay(mockRestTemplate);

        assertEquals(movieConsumer.getAllMovieDTO().size(), 2);

    }


    @Test
    public void test_get_all_movies_DTO_with_date_filter() throws ParseException {
        LOGGER.debug("test: getAllMovieDTOWithDateFilter()");

        List expectedResult = new ArrayList();
        expectedResult.add(MOVIE_DTO_TEST1);
        expectedResult.add(MOVIE_DTO_TEST2);
        String startDateString = "2000-01-12";
        String endDateString = "2005-09-12";
        expect(mockRestTemplate.getForEntity(url+urlMovies+"/DateFilter?startDate=" +
                        "{startDateString}&endDate={endDateString}",
                Object.class, startDateString, endDateString))
                .andReturn(new ResponseEntity<Object>(expectedResult, HttpStatus.OK));
        replay(mockRestTemplate);

        assertEquals(movieConsumer.getAllMovieDTOWithDateFilter(
                FORMATTER.parse(startDateString), FORMATTER.parse(endDateString))
                .size(), 2);

    }

    @Test
    public void test_get_movie_DTO_by_id() throws Exception{
        LOGGER.debug("test: getMovieDTOById()");
        expect(mockRestTemplate.getForEntity(url +"/movieDTO/{movieId}", MovieDTO.class, 3))
                .andReturn(new ResponseEntity<MovieDTO>(MOVIE_DTO_TEST1, HttpStatus.OK));
        replay(mockRestTemplate);

        assertEquals(MOVIE_DTO_TEST1, movieConsumer.getMovieDTOById(3));
    }


    @Test
    public void test_add_movie() throws Exception{
        LOGGER.debug("test: addMovie()");
        expect(mockRestTemplate.postForObject(url + urlMovie, MOVIE_TEST1, Integer.class))
                .andReturn(new Integer(2));
        replay(mockRestTemplate);

        assertEquals(2, movieConsumer.addMovie(MOVIE_TEST1));
    }

    @Test
    public void test_update_movie() throws Exception{
        LOGGER.debug("test: updateMovie()");
        mockRestTemplate.put(url+urlMovie, MOVIE_TEST1);
        expectLastCall().once();
        replay(mockRestTemplate);

        movieConsumer.updateMovie(MOVIE_TEST1);
    }


    @Test
    public void test_delete_movie() throws Exception{
        LOGGER.debug("test: deleteMovie()");
        mockRestTemplate.delete(url+urlMovie+"/{movieID}", 4);
        expectLastCall().once();
        replay(mockRestTemplate);

        movieConsumer.deleteMovie(4);
    }

}
