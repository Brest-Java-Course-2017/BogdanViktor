package com.epam.result.service;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDAO;
import com.epam.result.dao.DirectorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
@Service
@Transactional
public class DirectorServiceImpl implements DirectorService{
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    DirectorDAO directorDao;

    public void setDirectorDao(DirectorDAO directorDao) {
        this.directorDao = directorDao;
    }

    @Override
    public List<Director> getAllDirectors() throws DataAccessException {
        LOGGER.debug("getAllDirectors()");
        return directorDao.getAllDirectors();
    }

    @Override
    public List<DirectorDTO> getAllDirectorsWithMovieRating() throws DataAccessException {
        LOGGER.debug("getAllDirectorsWithMovieRating()");
        return directorDao.getAllDirectorsWithMovieRating();
    }


    @Override
    public int addDirector(Director director) throws DataAccessException {
        Assert.notNull(director, "Director should not be null.");
        LOGGER.debug("addDirector({})", director.getFirstName()+" "+director.getLastName());
        Assert.isNull(director.getDirectorID(), "Director's ID should be null.");
        Assert.hasText(director.getFirstName(), "Director's first name should not be null");
        Assert.hasText(director.getLastName(), "Director's last name should not be null");
        try{
            if(directorDao.getDirectorByFirstAndLastName(director.getFirstName(), director.getLastName())!=null){
                throw new IllegalArgumentException(String.format(
                        "The director %s %s already exists in the database",
                        director.getFirstName(), director.getLastName()));
            }
        } catch (DataAccessException e){  }
        return directorDao.addDirector(director);
    }

    @Override
    public Director getDirectorById(Integer id) {
        Assert.notNull(id, "Director's ID should not be null");
        LOGGER.debug("getDirectorById({})", id);
        return directorDao.getDirectorById(id);
    }

    @Override
    public void updateDirector(Director director) throws DataAccessException {
        Assert.notNull(director, "Director should not be null.");
        LOGGER.debug("updateDirector({})", director.getFirstName()+" "+director.getLastName());
        Assert.notNull(director.getDirectorID(), "Director's ID should not be null.");
        Assert.hasText(director.getFirstName(), "Director's first name should not be null");
        Assert.hasText(director.getLastName(), "Director's last name should not be null");
        try{
            if(directorDao.getDirectorByFirstAndLastName(director.getFirstName(), director.getLastName())!=null){
                throw new IllegalArgumentException(String.format(
                        "The director %s %s already exists in the database.",
                        director.getFirstName(), director.getLastName()));
            }
        } catch (DataAccessException e){  }
        int numberOfRowsAffected = directorDao.updateDirector(director);
        if(numberOfRowsAffected==0) throw new IllegalArgumentException(String.format(
                "The director with ID=%d does not exist in the database.", director.getDirectorID()));
    }

    @Override
    public void deleteDirector(Integer directorID) throws DataAccessException {
        Assert.notNull(directorID, "Director's ID should not be null");
        LOGGER.debug("deleteDirector({})", directorID);
        int numberOfRowsAffected = directorDao.deleteDirector(directorID);
        if(numberOfRowsAffected==0) throw new IllegalArgumentException(String.format(
                "The director with ID=%d does not exist in the database.", directorID));
    }
}
