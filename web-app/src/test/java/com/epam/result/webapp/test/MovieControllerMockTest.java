package com.epam.result.webapp.test;

import com.epam.result.dao.DirectorDTO;
import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import com.epam.result.service.DirectorService;
import com.epam.result.service.MovieService;
import com.epam.result.webapp.controller.MovieController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-controller-mock.xml"})

public class MovieControllerMockTest {

    @Resource
    private MovieController movieController;


    @Autowired
    private DirectorService directorService;
    @Autowired
    private MovieService movieService;

    private MockMvc mockMvc;

    private static final Movie TEST_MOVIE = new Movie(5, "testMovieTitle", new Date(34, 5, 5), 5.4, 3);
    private static final MovieDTO TEST_MOVIE_DTO = new MovieDTO(5, "testMovieTitle", new Date(34, 5, 5),
                                                        5.4, "testFirstName", "testLastName");
    private static final List<MovieDTO> MOVIE_DTO_LIST = Arrays.asList(
            new MovieDTO(1, "movieTitle1", new Date(50, 3, 4), 7.6, "firstName1", "lastName1"),
            new MovieDTO(5, "movieTitle5", new Date(56, 3, 4), 6.7, "firstName5", "lastName5"));
    private static final List<DirectorDTO> DIRECTOR_DTO_LIST = Arrays.asList(
            new DirectorDTO(1, "firstName1", "lastName1", 6.0),
            new DirectorDTO(2, "firstName2", "lastName2", 5.0));
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .build();
    }


    @Test
    public void test_get_moviesDTO() throws Exception {
        LOGGER.debug("test_get_moviesDTO()");
        expect(movieService.getAllMovieDTO())
                .andReturn(MOVIE_DTO_LIST);
        replay(movieService);

        mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("moviesPage"))
                .andExpect(model().attribute("moviesList", hasSize(2)));

        verify(movieService);
        reset(movieService);
    }

    @Test
    public void test_get_movieAdd_page() throws Exception {
        LOGGER.debug("test_get_movieAdd_page()");
        expect(directorService.getAllDirectorDTO())
            .andReturn(DIRECTOR_DTO_LIST);
        replay(directorService);

        mockMvc.perform(get("/movie/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("movieAdd"))
                .andExpect(model().attribute("directorList", hasSize(2)));

        verify(directorService);
        reset(directorService);
    }


    @Test
    public void test_add_movie() throws Exception {
        LOGGER.debug("test_add_movie()");
        expect(movieService.addMovie(anyObject(Movie.class))).andReturn(5);
        expect(movieService.getAllMovieDTO())
                .andReturn(MOVIE_DTO_LIST);
        replay(movieService);

        mockMvc.perform(
                post("/movie/add?movieTitle=testMovieTitle&" +
                        "releaseDate=1934-05-05&" +
                        "rating=5.4&" +
                        "movieDirectorId=3"))
                .andExpect(status().isCreated())
                .andExpect(view().name("moviesPage"))
                .andExpect(model().attribute("moviesList", hasSize(2)));
        verify(movieService);
        reset(movieService);
    }

    @Test
    public void test_get_moviesDTO_with_date_filter() throws Exception {
        LOGGER.debug("test_get_moviesDTO_with_date_filter()");
        expect(movieService.getAllMovieDTOWithDateFilter(
                        FORMATTER.parse("1934-05-05"),
                        FORMATTER.parse("1999-03-03")))
                .andReturn(MOVIE_DTO_LIST);
        replay(movieService);


        mockMvc.perform(
                post("/movie/datefilter?" +
                        "startDate=1934-05-05&" +
                        "endDate=1999-03-03"))
                .andExpect(status().isOk())
                .andExpect(view().name("moviesPage"))
                .andExpect(model().attribute("moviesList", hasSize(2)));

        verify(movieService);
        reset(movieService);
    }

    @Test
    public void test_get_movieEdit_page() throws Exception {
        LOGGER.debug("test_get_movieEdit_page()");
        expect(movieService.getMovieById(5))
                .andReturn(TEST_MOVIE);
        expect(directorService.getAllDirectorDTO())
                .andReturn(DIRECTOR_DTO_LIST);
        replay(movieService, directorService);

        mockMvc.perform(
                get("/movie/edit?movieId=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("movieEdit"))
                .andExpect(model().attribute("directorList", hasSize(2)))
                .andExpect(model().attribute("movie", is(TEST_MOVIE)));

        verify(movieService, directorService);
        reset(movieService, directorService);
    }


    @Test
    public void test_edit_movie() throws Exception {
        LOGGER.debug("test_edit_movie()");
        movieService.updateMovie(anyObject(Movie.class));
        expectLastCall();
        expect(movieService.getAllMovieDTO())
                .andReturn(MOVIE_DTO_LIST);
        replay(movieService);

        mockMvc.perform(
                post("/movie/edit" +
                        "?movieId=5" +
                        "&movieTitle=testMovieTitle" +
                        "&releaseDate=1934-05-05" +
                        "&rating=5.4" +
                        "&movieDirectorId=3"))
                .andExpect(status().isOk())
                .andExpect(view().name("moviesPage"))
                .andExpect(model().attribute("moviesList", hasSize(2)));
        verify(movieService);
        reset(movieService);
    }


    @Test
    public void test_get_movieDelete_page() throws Exception {
        LOGGER.debug("test_get_movieDelete_page()");
        expect(movieService.getMovieDTOById(5))
                .andReturn(TEST_MOVIE_DTO);
        replay(movieService);

        mockMvc.perform(
                get("/movie/delete?movieId=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("movieDelete"))
                .andExpect(model().attribute("movie", is(TEST_MOVIE_DTO)));

        verify(movieService);
        reset(movieService);
    }

    @Test
    public void test_movie_delete() throws Exception {
        LOGGER.debug("test_movie_delete()");
        movieService.deleteMovie(5);
        expectLastCall();
        expect(movieService.getAllMovieDTO())
                .andReturn(MOVIE_DTO_LIST);
        replay(movieService);

        mockMvc.perform(post("/movie/delete?movieId=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("moviesPage"))
                .andExpect(model().attribute("moviesList", hasSize(2)));

        verify(movieService);
        reset(movieService);

    }

}


