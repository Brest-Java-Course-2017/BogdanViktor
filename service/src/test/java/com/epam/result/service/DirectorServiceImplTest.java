package com.epam.result.service;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The {@code DirectorServiceImplTest} - is a test class for DirectorServiceImpl.
 * @author  Bogdan Viktor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class DirectorServiceImplTest {
    private static final Logger LOGGER = LogManager.getLogger();


    @Autowired
    DirectorService directorService;

    @Test
    public void test_get_all_directors() throws Exception {
        LOGGER.debug("test: getAllDirectors()");
        List<Director> list= directorService.getAllDirectors();
        assertEquals(4, list.size());
    }

    @Test
    public void test_get_all_directorDTO() throws Exception {
        LOGGER.debug("test: getAllDirectorDTO()");
        List<DirectorDTO> list= directorService.getAllDirectorDTO();
        assertEquals(4, list.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_add_director_with_null() throws Exception{
        LOGGER.debug("test: addDirector(). When director is null");
        directorService.addDirector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_director_when_he_already_exists_in_DB() throws Exception{
        LOGGER.debug("test: addDirector(). When director already exists in data base");
        Director testDirector = new Director("StEveN", "SpieLBerg");
        directorService.addDirector(testDirector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_director_when_first_name_longer_30_chars() throws Exception{
        LOGGER.debug("test: addDirector(). When director's first name longer than 30 chars");
        Director testDirector = new Director("SpieLsdfsdfsahfghfdhfdsdfsfsdfBerg", "StEveN");
        directorService.addDirector(testDirector);
    }

    @Test
    public void test_add_director() throws Exception{
        LOGGER.debug("test: addDirector()");
        int initialSize = directorService.getAllDirectors().size();
        Director testDirector = new Director("testFirstName" , "testLastName");
        assertEquals(directorService.addDirector(testDirector), 5);
        assertEquals(initialSize+1, directorService.getAllDirectors().size());
    }

    @Test
    public void test_get_director_by_id() throws Exception{
        LOGGER.debug("test: getDirectorById()");
        Director newDirector = new Director(1, "Steven", "Spielberg");
        assertEquals(newDirector, directorService.getDirectorById(1));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_director_by_id_when_id_does_not_exist_in_DB() throws Exception{
        LOGGER.debug("test: getDirectorById(), when id does not exist in data base");
        directorService.getDirectorById(7);
    }

    @Test
    public void test_update_director() throws Exception{
        LOGGER.debug("test: updateDirector()");
        Director newDirector = new Director(1, "testValue", "testValue2");
        directorService.updateDirector(newDirector);
        assertEquals(newDirector, directorService.getDirectorById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_director_when_id_does_not_exist_in_DB() throws Exception{
        LOGGER.debug("test: updateDirector(), when id does not exist in DB");
        Director newDirector = new Director(10, "testValue", "testValue2");
        directorService.updateDirector(newDirector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_director_with_null() throws Exception{
        LOGGER.debug("test: updateDirector(), when director is null");
        directorService.updateDirector(null);
    }

    @Test
    public void test_delete_director() throws Exception{
        LOGGER.debug("test: deleteDirector()");
        Director testDirector = new Director("testFirstName" , "testLastName");
        int id = directorService.addDirector(testDirector);
        int size = directorService.getAllDirectors().size();
        directorService.deleteDirector(id);
        assertEquals(size-1, directorService.getAllDirectors().size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_delete_director_when_id_does_not_exist_in_DB() throws Exception{
        LOGGER.debug("test: deleteDirector(), when id does not exist in DB");
        directorService.deleteDirector(30);
    }


}