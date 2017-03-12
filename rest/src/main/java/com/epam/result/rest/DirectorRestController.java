package com.epam.result.rest;

import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;
import com.epam.result.service.DirectorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sw0rd on 12.03.17.
 */
@RestController
public class DirectorRestController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DirectorService directorService;

    @RequestMapping(value = "/directors", method = RequestMethod.GET)
    public @ResponseBody List<DirectorDTO> getAllDirectorsWithMoviesRating(){
        LOGGER.debug("getAllDirectorsWithMoviesRating()");
        return directorService.getAllDirectorsWithMovieRating();
    }

    @RequestMapping(value = "/director/{directorID}", method = RequestMethod.GET)
    public @ResponseBody Director getDirectorById(@PathVariable(value = "directorID") Integer directorID){
        LOGGER.debug("getDirectorById({})", directorID);
        return directorService.getDirectorById(directorID);
    }

}
