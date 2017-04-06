package com.epam.result.webapp.test;

import com.epam.result.client.rest_api.DirectorConsumer;
import com.epam.result.client.rest_api.MovieConsumer;
import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;
import com.epam.result.dao.Movie;
import com.epam.result.webapp.controllers.DirectorController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-controller-mock.xml"})

public class DirectorControllerMockTest {

    @Resource
    private DirectorController directorController;


    @Autowired
    private DirectorConsumer directorConsumer;
    @Autowired
    private MovieConsumer movieConsumer;

    private MockMvc mockMvc;

    private static final Director TEST_DIRECTOR = new Director(3, "testFirstName", "testLastName");
    private static final List<Movie> MOVIE_LIST = Arrays.asList(
            new Movie(1, "movieTitle1", new Date(50, 3, 4), 7.6, 3),
            new Movie(5, "movieTitle5", new Date(56, 3, 4), 6.7, 3));
    private static final List<DirectorDTO> DIRECTOR_DTO_LIST = Arrays.asList(
            new DirectorDTO(1, "firstName1", "lastName1", 6.0),
            new DirectorDTO(2, "firstName2", "lastName2", 5.0));
    private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(directorController)
                .build();
    }


    @Test
    public void test_get_directorsDTO() throws Exception {
        LOGGER.debug("test_get_directorsDTO()");
        expect(directorConsumer.getAllDirectorDTO())
                .andReturn(DIRECTOR_DTO_LIST);
        replay(directorConsumer);

        mockMvc.perform(get("/directors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("directorsPage"))
                .andExpect(model().attribute("directorList", hasSize(2)));

        verify(directorConsumer);
        reset(directorConsumer);
    }

    @Test
    public void test_get_directorAdd_page() throws Exception {
        LOGGER.debug("test_get_directorAdd_page()");

        mockMvc.perform(get("/director/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("directorAdd"))
                .andExpect(forwardedUrl("directorAdd"));
    }

    @Test
    public void test_add_director() throws Exception {
        LOGGER.debug("test_add_director()");
        expect(directorConsumer.addDirector(anyObject(Director.class))).andReturn(3);
        expect(directorConsumer.getAllDirectorDTO())
                .andReturn(DIRECTOR_DTO_LIST);
        replay(directorConsumer);

        mockMvc.perform(
                post("/director/add?firstName=testFirstName&lastName=testLastName"))
                .andExpect(status().isCreated())
                .andExpect(view().name("directorsPage"))
                .andExpect(model().attribute("directorList", hasSize(2)));
        verify(directorConsumer);
        reset(directorConsumer);
    }

    @Test
    public void test_get_directorEdit_page() throws Exception {
        LOGGER.debug("test_get_directorEdit_page()");
        expect(directorConsumer.getDirectorById(3))
                .andReturn(TEST_DIRECTOR);
        replay(directorConsumer);

        mockMvc.perform(get("/director/edit?directorId=3"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("directorEdit"))
                .andExpect(model().attribute("director", is(TEST_DIRECTOR)));

        verify(directorConsumer);
        reset(directorConsumer);
    }

    @Test
    public void test_get_directorDetails_page() throws Exception {
        LOGGER.debug("test_get_directorDetails_page()");
        expect(directorConsumer.getDirectorById(3))
                .andReturn(TEST_DIRECTOR);

        expect(movieConsumer.getAllMoviesCreatedByDirector(3))
                .andReturn(MOVIE_LIST);

        replay(directorConsumer, movieConsumer);

        mockMvc.perform(get("/director/details?directorId=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("directorDetails"))
                .andExpect(model().attribute("director", is(TEST_DIRECTOR)))
                .andExpect(model().attribute("moviesList", hasSize(2)));

        verify(directorConsumer, movieConsumer);
        reset(directorConsumer, movieConsumer);
    }

    @Test
    public void test_get_directorDelete_page_when_movieList_is_not_null() throws Exception {
        LOGGER.debug("test_get_directorDelete_page()");
        expect(directorConsumer.getDirectorById(3))
                .andReturn(TEST_DIRECTOR);
        expect(movieConsumer.getAllMoviesCreatedByDirector(3))
                .andReturn(MOVIE_LIST);

        replay(directorConsumer, movieConsumer);

        mockMvc.perform(get("/director/delete?directorId=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("directorError"))
                .andExpect(model().attribute("director", is(TEST_DIRECTOR)))
                .andExpect(model().attribute("moviesList", hasSize(2)));

        verify(directorConsumer, movieConsumer);
        reset(directorConsumer, movieConsumer);
    }

    @Test
    public void test_get_directorDelete_page_when_movieList_is_null() throws Exception {
        LOGGER.debug("test_get_directorDelete_page()");
        expect(directorConsumer.getDirectorById(3))
                .andReturn(TEST_DIRECTOR);
        expect(movieConsumer.getAllMoviesCreatedByDirector(3))
                .andReturn(null);

        replay(directorConsumer, movieConsumer);

        mockMvc.perform(get("/director/delete?directorId=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("directorDelete"))
                .andExpect(model().attribute("director", is(TEST_DIRECTOR)));

        verify(directorConsumer, movieConsumer);
        reset(directorConsumer, movieConsumer);
    }

    @Test
    public void test_directorDelete() throws Exception {
        LOGGER.debug("test_directorDelete()");
        directorConsumer.deleteDirector(3);
        expectLastCall();
        expect(directorConsumer.getAllDirectorDTO())
                .andReturn(DIRECTOR_DTO_LIST);
        replay(directorConsumer);

        mockMvc.perform(post("/director/delete?directorId=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("directorsPage"))
                .andExpect(model().attribute("directorList", hasSize(2)));

        verify(directorConsumer);
        reset(directorConsumer);

    }

    @Test
    public void test_director_edit() throws Exception {
        LOGGER.debug("test_director_edit()");
        directorConsumer.updateDirector(new Director(3, "firstName", "lastName"));
        expectLastCall();
        expect(directorConsumer.getAllDirectorDTO())
                .andReturn(DIRECTOR_DTO_LIST);
        replay(directorConsumer);

        mockMvc.perform(post("/director?directorId=3&firstName=firstName&lastName=lastName"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("directorsPage"))
                .andExpect(model().attribute("directorList", hasSize(2)));

        verify(directorConsumer);
        reset(directorConsumer);

    }
}



