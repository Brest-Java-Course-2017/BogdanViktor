package com.epam.result.service;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
public interface DirectorService {

    List<Director> getAllDirectors() throws DataAccessException;

    List<DirectorDTO> getAllDirectorsWithMovieRating() throws DataAccessException;

    int addDirector(Director director) throws DataAccessException;

    Director getDirectorById(Integer id);

    void updateDirector(Director director) throws DataAccessException;

    void deleteDirector(Integer directorID) throws DataAccessException;

}
