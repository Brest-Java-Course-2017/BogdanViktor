/**
 * Created by sw0rd on 05.03.17.
 */

package com.epam.result.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * DAO interface.
 */
public interface DirectorDAO {

    /**
     * Get all directors list.
     *
     * @return all directors list
     */
    List<Director> getAllDirectors() throws DataAccessException;

    List<DirectorDTO> getAllDirectorsWithMovieRating() throws DataAccessException;

    Director getDirectorByFirstAndLastName(String firstName, String lastName) throws DataAccessException;

    int addDirector(Director director) throws DataAccessException;

    Director getDirectorById(Integer id);

    int updateDirector(Director director) throws DataAccessException;

    int deleteDirector(Integer directorID) throws DataAccessException;


}
