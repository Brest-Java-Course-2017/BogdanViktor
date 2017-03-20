package com.epam.result.dao;

import java.util.List;

/**
 * The {@code DirectorDAO} - is an interface that provides access to a database.
 * @author  Bogdan Viktor
 */
public interface DirectorDAO {

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
     * Get director by first and last name.
     * @param firstName is director's first name.
     * @param lastName is director's last name.
     * @return director.
     */
    Director getDirectorByFirstAndLastName(String firstName, String lastName);

    /**
     * Adds the director to the database and returns
     * the ID that the database assigned to him.
     * @param director director.
     * @return director's ID.
     */
    int addDirector(Director director);

    /**
     * Get director by ID.
     * @param id  is director's ID.
     * @return director.
     */
    Director getDirectorById(Integer id);

    /**
     * Updates the director in the database and returns
     * the number of rows affected in data base.
     * @param director director.
     * @return the number of rows affected in data base.
     */
    int updateDirector(Director director);

    /**
     * Deletes the director in the database and returns
     * the number of rows affected in data base.
     * @param directorID  is director's ID.
     * @return the number of rows affected in data base.
     */
    int deleteDirector(Integer directorID);


}
