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
 * Created by sw0rd on 12.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class DirectorServiceImplTest {
    private static final Logger LOGGER = LogManager.getLogger();


    @Autowired
    DirectorService directorService;

    @Test
    public void testGetAllDirectors() throws Exception {
        LOGGER.debug("test: getAllDirectors()");
        List<Director> list= directorService.getAllDirectors();
        assertEquals(4, list.size());
    }

    @Test
    public void testGetAllDirectorsWithMovieRating() throws Exception {
        LOGGER.debug("test: getAllDirectorDTO()");
        List<DirectorDTO> list= directorService.getAllDirectorsWithMovieRating();
        assertEquals(4, list.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectorWithNull() throws Exception{
        LOGGER.debug("test: addDirector(). When director is null");
        directorService.addDirector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectorWhenHeAlreadyExistsInDB() throws Exception{
        LOGGER.debug("test: addDirector(). When director already exists in data base");
        Director testDirector = new Director("StEveN", "SpieLBerg");
        directorService.addDirector(testDirector);
    }

    @Test
    public void testAddDirector() throws Exception{
        LOGGER.debug("test: addDirector()");
        int initialSize = directorService.getAllDirectors().size();
        Director testDirector = new Director("testFirstName" , "testLastName");
        directorService.addDirector(testDirector);
        assertEquals(initialSize+1, directorService.getAllDirectors().size());
    }

    @Test
    public void testGetDirectorById() throws Exception{
        LOGGER.debug("test: getDirectorById()");
        Director newDirector = new Director(1, "Steven", "Spielberg");
        assertEquals(newDirector, directorService.getDirectorById(1));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testGetDirectorByIdWhenIdDoesNotExistInDB() throws Exception{
        LOGGER.debug("test: getDirectorById(), when id does not exist in data base");
        directorService.getDirectorById(7);
    }

    @Test
    public void testUpdateDirector() throws Exception{
        LOGGER.debug("test: updateDirector()");
        Director newDirector = new Director(1, "testValue", "testValue2");
        directorService.updateDirector(newDirector);
        assertEquals(newDirector, directorService.getDirectorById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateDirectorWithNull() throws Exception{
        LOGGER.debug("test: updateDirector(), when director is null");
        directorService.updateDirector(null);
    }

    @Test
    public void testDeleteDirector() throws Exception{
        LOGGER.debug("test: deleteDirector()");
        Director testDirector = new Director("testFirstName" , "testLastName");
        int id = directorService.addDirector(testDirector);
        int size = directorService.getAllDirectors().size();
        directorService.deleteDirector(id);
        assertEquals(size-1, directorService.getAllDirectors().size());
    }

}