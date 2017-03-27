package com.epam.result.rest.test;

/**
 * @author  Bogdan Viktor
 */

        import com.epam.result.dao.Director;
        import com.epam.result.dao.DirectorDTO;
        import com.epam.result.rest.controllers.DirectorRestController;
        import com.epam.result.service.DirectorService;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;
        import org.junit.After;
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
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
        import org.springframework.http.MediaType;
        import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


        import javax.annotation.Resource;
        import java.util.Arrays;

        import static org.easymock.EasyMock.*;

        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-controller-rest-mock.xml"})

public class DirectorRestControllerMockTest {

    @Resource
    private DirectorRestController directorRestController;


    @Autowired
    private DirectorService directorService;

    private MockMvc mockMvc;

    private static final Director TEST_DIRECTOR = new Director(3, "testFirstName", "testLastName");
    private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(directorRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown(){
        verify(directorService);
        reset(directorService);
    }

    @Test
    public void test_get_directorsDTO() throws Exception{
        LOGGER.debug("get_directorsDTO_test()");
        expect(directorService.getAllDirectorDTO())
                .andReturn(Arrays.asList(new DirectorDTO(), new DirectorDTO()));
        replay(directorService);

        mockMvc.perform(get("/directors")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_director_by_id() throws Exception {
        LOGGER.debug("get_director_by_id_test()");
        expect(directorService.getDirectorById(3)).andReturn(TEST_DIRECTOR);
        replay(directorService);

        String director = new ObjectMapper().writeValueAsString(TEST_DIRECTOR);

        mockMvc.perform(get("/director/3")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().string(director));
    }

    @Test
    public void test_add_director() throws Exception {
        LOGGER.debug("add_director_test()");
        expect(directorService.addDirector(anyObject(Director.class))).andReturn(1);
        replay(directorService);

        String director = new ObjectMapper().writeValueAsString(new Director("testFirstName", "testLastName"));

        mockMvc.perform(
                post("/director")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(director))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("1"));
    }

    @Test
    public void test_update_director() throws Exception {
        LOGGER.debug("update_director_test()");
        directorService.updateDirector(anyObject(Director.class));
        expectLastCall().once();
        replay(directorService);

        String director = new ObjectMapper().writeValueAsString(TEST_DIRECTOR);

        mockMvc.perform(
                put("/director")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(director))
        .andDo(print())
        .andExpect(status().isAccepted());
    }

    @Test
    public void test_delete_director() throws Exception {
        LOGGER.debug("delete_director_test()");
        directorService.deleteDirector(3);
        expectLastCall().once();
        replay(directorService);

        mockMvc.perform(
                delete("/director/3")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }

}

