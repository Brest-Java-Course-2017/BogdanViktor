package com.epam.result;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDAO;
import com.epam.result.dao.DirectorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sw0rd on 06.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class DirectorDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Director testDirector = new Director("testFirstName", "testLastName");

    @Autowired
    DirectorDAO directorDAO;


    @Test
    public void testGetAllDirectors() {
        LOGGER.debug("test: getAllDirectors()");
        List<Director> list = directorDAO.getAllDirectors();
        System.out.println(list);
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void testGetAllDirectorsWithMovieRating(){
        LOGGER.debug("test: getAllDirectorsWithMovieRating()");

        List<DirectorDTO> list = directorDAO.getAllDirectorsWithMovieRating();
        Assert.assertTrue(list.size()==4);
    }

    @Test
    public void testAddDirector(){
        LOGGER.debug("test: addDirector()");

        List<Director> list = directorDAO.getAllDirectors();
        int size = list.size();
        directorDAO.addDirector(testDirector);
        list = directorDAO.getAllDirectors();
        Assert.assertEquals(size+1, list.size());
    }

    @Test
    public void testUpdateDirector(){
        LOGGER.debug("test: updateDirector()");
        Director newDirector = new Director(1, "testValue", "testValue2");
        directorDAO.updateDirector(newDirector);
        Assert.assertEquals(newDirector, directorDAO.getDirectorById(1));

    }

    @Test
    public void testGetDirectorById(){
        LOGGER.debug("test: GetDirectorById()");
        Director newDirector = new Director(1, "Steven", "Spielberg");
        Assert.assertEquals(newDirector, directorDAO.getDirectorById(1));
    }

    @Test
    public void testDeleteDirector(){
        LOGGER.debug("test: deleteDirector()");
        int id = directorDAO.addDirector(testDirector);
        int size = directorDAO.getAllDirectors().size();
        directorDAO.deleteDirector(id);
        Assert.assertEquals(size-1, directorDAO.getAllDirectors().size());
    }


    @Test
    public void testGetDirectorByFirstAndLastName(){
        LOGGER.debug("test: getDirectorByFirstAndLastName()");

        String firstName = "StEveN";
        String lastName = "SpieLBerg";

        Assert.assertEquals(directorDAO.getDirectorById(1),
                directorDAO.getDirectorByFirstAndLastName(firstName, lastName));
    }

}
