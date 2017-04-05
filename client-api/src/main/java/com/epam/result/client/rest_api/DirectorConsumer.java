package com.epam.result.client.rest_api;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;

import java.util.List;

/**
 * @author  Bogdan Viktor
 */
public interface DirectorConsumer{
        /**
         * Get all directors list.
         * @return all directors list
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
