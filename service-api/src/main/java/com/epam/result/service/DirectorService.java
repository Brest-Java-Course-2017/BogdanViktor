package com.epam.result.service;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;

import java.util.List;

/**
 * The {@code DirectorService} - is a service layer of application.
 * @author  Bogdan Viktor
 */
public interface DirectorService {

    /**
     * Get all directors list.
     * @return all directors list
     */
    List<Director> getAllDirectors();

    /**
     * Get all directors list with average rating of their movies.
     * @return all directors list with average rating of their movies.
     */
    List<DirectorDTO> getAllDirectorDTO();

    /**
     * Adds the director to the database and returns
     * the ID that the database assigned to him.
     * @param director director.
     * @return director's ID.
     */
    int addDirector(Director director);

    /**
     * Get director by ID.
     * @param id  is director's identifier.
     * @return director.
     */
    Director getDirectorById(Integer id);

    /**
     * Updates the director in the database and returns
     * the number of rows affected in data base.
     * @param director director.
     */
    void updateDirector(Director director);

    /**
     * Deletes the director in the database and returns
     * the number of rows affected in data base.
     * @param directorID  is director's ID.
     */
    void deleteDirector(Integer directorID);

}
