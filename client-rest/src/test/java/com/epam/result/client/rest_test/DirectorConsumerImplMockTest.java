package com.epam.result.client.rest_test;

import com.epam.result.client.rest_api.DirectorConsumer;
import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author  Bogdan Viktor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:client-rest-test.xml"})
public class DirectorConsumerImplMockTest {
    private static final Logger LOGGER = LogManager.getLogger();


    @Autowired
    DirectorConsumer directorConsumer;

    @Value("${protocol}://${host}:${port}${prefix}")
    private String url;

    @Value("${point.director}")
    private String urlDirector;

    @Value("${point.directors}")
    private String urlDirectors;

    @Autowired
    private RestTemplate mockRestTemplate;

    private static final DirectorDTO DIRECTOR_DTO_TEST1 = new DirectorDTO(1, "firstName1", "lastName1", 5.6);
    private static final DirectorDTO DIRECTOR_DTO_TEST2 = new DirectorDTO(2, "firstName2", "lastName2", 7.8);
    private static final Director DIRECTOR_TEST = new Director(2, "firstName1", "lastName1");

    @After
    public void tearDown(){
        verify(mockRestTemplate);
        reset(mockRestTemplate);
    }


    @Test
    public void test_get_all_directors_DTO() {
        LOGGER.debug("test: getAllDirectorDTO()");
        List expectedResult = new ArrayList();
        expectedResult.add(DIRECTOR_DTO_TEST1);
        expectedResult.add(DIRECTOR_DTO_TEST2);
        expect(mockRestTemplate.getForEntity(url+urlDirectors, Object.class))
                .andReturn(new ResponseEntity<Object>(expectedResult, HttpStatus.OK));
        replay(mockRestTemplate);

        List<DirectorDTO> directorDTOs = directorConsumer.getAllDirectorDTO();
        assertEquals(2, directorDTOs.size());
    }

    @Test
    public void test_add_director() throws Exception{
        LOGGER.debug("test: addDirector()");
        expect(mockRestTemplate.postForObject(url + urlDirector, DIRECTOR_TEST, Integer.class))
                .andReturn(new Integer(2));
        replay(mockRestTemplate);

        assertEquals(directorConsumer.addDirector(DIRECTOR_TEST), 2);
    }

    @Test
    public void test_get_director_by_id() throws Exception {
        LOGGER.debug("test: getDirectorById()");
        expect(mockRestTemplate.getForEntity(url + urlDirector + "/{directorId}", Director.class, 2))
                .andReturn(new ResponseEntity<Director>(DIRECTOR_TEST, HttpStatus.OK));
        replay(mockRestTemplate);

        assertEquals(DIRECTOR_TEST, directorConsumer.getDirectorById(2));
    }

    @Test
    public void test_update_director() throws Exception{
        LOGGER.debug("test: updateDirector()");
        mockRestTemplate.put(url+urlDirector, DIRECTOR_TEST);
        expectLastCall().once();
        replay(mockRestTemplate);

        directorConsumer.updateDirector(DIRECTOR_TEST);
    }

    @Test
    public void test_delete_director() throws Exception{
        LOGGER.debug("test: deleteDirector()");
        mockRestTemplate.delete(url+urlDirector+"/{directorId}", 3);
        expectLastCall().once();
        replay(mockRestTemplate);
        directorConsumer.deleteDirector(3);

    }
}
