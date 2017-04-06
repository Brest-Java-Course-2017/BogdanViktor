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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The {@code DirectorDaoImplTest} - is a test class for DirectorDaoImpl.
 * @author  Bogdan Viktor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class DirectorDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Director TEST_DIRECTOR = new Director("testFirstName", "testLastName");
    private static final Director TEST_EXISTING_DIRECTOR = new Director(1,"Steven", "Spielberg");

    @Autowired
    DirectorDAO directorDAO;


    @Test
    public void test_get_all_directors() {
        LOGGER.debug("test: getAllDirectors()");
        List<Director> list = directorDAO.getAllDirectors();
        Assert.assertTrue(list.size()==4);
    }

    @Test
    public void test_get_all_directorDTO(){
        LOGGER.debug("test: getAllDirectorDTO()");
        List<DirectorDTO> list = directorDAO.getAllDirectorDTO();
        Assert.assertTrue(list.size()==4);
    }

    @Test
    public void test_add_director(){
        LOGGER.debug("test: addDirector()");
        List<Director> list = directorDAO.getAllDirectors();
        int initialSize = list.size();
        directorDAO.addDirector(TEST_DIRECTOR);
        list = directorDAO.getAllDirectors();
        assertEquals(initialSize+1, list.size());
    }

    @Test(expected = NullPointerException.class)
    public void test_add_director_with_null(){
        LOGGER.debug("test: addDirector() with null");
        directorDAO.addDirector(null);
    }

    @Test
    public void test_update_director(){
        LOGGER.debug("test: updateDirector()");
        Director newDirector = new Director(1, "testValue", "testValue2");
        assertEquals(1,directorDAO.updateDirector(newDirector));
        assertEquals(newDirector, directorDAO.getDirectorById(1));
    }

    @Test
    public void test_update_director_when_ID_does_not_exist_in_DB(){
        LOGGER.debug("test: updateDirector() with directorID = 8");
        Director newDirector = new Director(8, "testValue", "testValue2");
        assertEquals(0, directorDAO.updateDirector(newDirector));
    }

    @Test
    public void test_get_director_by_id(){
        LOGGER.debug("test: GetDirectorById()");
        assertEquals(TEST_EXISTING_DIRECTOR, directorDAO.getDirectorById(1));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_director_by_id_when_ID_does_not_exist_in_DB(){
        LOGGER.debug("test: GetDirectorById(), when ID does not exists in DB" );
        assertEquals(TEST_EXISTING_DIRECTOR, directorDAO.getDirectorById(9));
    }

    @Test
    public void test_delete_director(){
        LOGGER.debug("test: deleteDirector()");
        int id = directorDAO.addDirector(TEST_DIRECTOR);
        int size = directorDAO.getAllDirectors().size();
        assertEquals(1,directorDAO.deleteDirector(id));
        assertEquals(size-1, directorDAO.getAllDirectors().size());
    }

    @Test
    public void test_get_director_by_first_and_last_name_positive(){
        LOGGER.debug("test: getDirectorByFirstAndLastName()");
        String firstName = "StEveN";
        String lastName = "SpieLBerg";
        assertEquals(directorDAO.getDirectorById(1),
                directorDAO.getDirectorByFirstAndLastName(firstName, lastName));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_get_director_by_first_and_last_name_negative(){
        LOGGER.debug("test: getDirectorByFirstAndLastName(), negative test");
        String firstName = "not exist";
        String lastName = "SpieLBerg";
        directorDAO.getDirectorByFirstAndLastName(firstName, lastName);
    }

}
