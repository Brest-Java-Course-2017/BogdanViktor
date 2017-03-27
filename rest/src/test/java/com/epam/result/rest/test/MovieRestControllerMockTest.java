package com.epam.result.rest.test;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import com.epam.result.rest.controllers.MovieRestController;
import com.epam.result.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-controller-rest-mock.xml"})

public class MovieRestControllerMockTest {

    @Resource
    private MovieRestController movieRestController;


    @Autowired
    private MovieService movieService;

    private MockMvc mockMvc;

    private static final Movie TEST_MOVIE = new Movie(2,"testMovie", new Date(100, 2, 3), 4.0, 3);
    private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(movieRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown(){
        verify(movieService);
        reset(movieService);
    }

    @Test
    public void test_get_all_moviesDTO() throws Exception{
        LOGGER.debug("get_all_moviesDTO_test()");
        expect(movieService.getAllMovieDTO())
                .andReturn(Arrays.asList(new MovieDTO(), new MovieDTO()));
        replay(movieService);

        mockMvc.perform(get("/movies")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_movie_by_id() throws Exception {
        LOGGER.debug("test_get_movie_by_id()");

        expect(movieService.getMovieById(2)).andReturn(TEST_MOVIE);
        replay(movieService);

        String movie = new ObjectMapper().writeValueAsString(TEST_MOVIE);

        mockMvc.perform(get("/movie/2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().string(movie));
    }

    @Test
    public void test_add_movie() throws Exception {
        LOGGER.debug("add_movie_test()");

        expect(movieService.addMovie(anyObject(Movie.class))).andReturn(1);
        replay(movieService);

        String movie = new ObjectMapper().writeValueAsString(new Movie("testMovie", new Date(100, 2, 3), 4.0, 3));

        mockMvc.perform(
                post("/movie")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movie))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("1"));
    }

    @Test
    public void test_update_movie() throws Exception {
        LOGGER.debug("update_movie_test()");

        movieService.updateMovie(anyObject(Movie.class));
        expectLastCall().once();
        replay(movieService);

        String movie = new ObjectMapper().writeValueAsString(TEST_MOVIE);

        mockMvc.perform(
                put("/movie")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movie))
        .andDo(print())
        .andExpect(status().isAccepted());
    }

    @Test
    public void test_delete_movie() throws Exception {
        LOGGER.debug("delete_movie_test()");
        movieService.deleteMovie(3);
        expectLastCall().once();
        replay(movieService);

        mockMvc.perform(
                delete("/movie/3")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }

}

