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

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class})
    public String incorrectDataError(){
        return "{  \"response\" : \"Incorrect Data Error\' }";
    }

    @Autowired
    private DirectorService directorService;

    //curl -v localhost:8088/directors
    @RequestMapping(value = "/directors", method = RequestMethod.GET)
    public @ResponseBody List<DirectorDTO> getAllDirectorsWithMoviesRating(){
        LOGGER.debug("getAllDirectorsWithMoviesRating()");
        return directorService.getAllDirectorDTO();
    }

    //curl -v localhost:8088/director/1
    @RequestMapping(value = "/director/{directorId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody Director getDirectorById(@PathVariable(value = "directorId") Integer directorId){
        LOGGER.debug("getDirectorById({})", directorId);
        return directorService.getDirectorById(directorId);
    }

    /*
    curl -H "Content-Type: application/json" -X POST -d '{"firstName":"xyz","re":"testName"}' -v localhost:8088/director
     */
    @RequestMapping(value = "/director", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Integer addDirector(@RequestBody Director director){
        LOGGER.debug("addDirector({})", director.getFirstName()+" "+director.getLastName());
        return directorService.addDirector(director);
    }

    // curl -H "Content-Type: application/json" -X PUT -d '{"directorId":"2","firstName":"xyz","lastName":"testName"}' -v localhost:8088/director
    @RequestMapping(value = "/director", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateDirector(@RequestBody Director director){
        LOGGER.debug("updateDirector({})", director.getFirstName()+" "+director.getLastName());
        directorService.updateDirector(director);
    }

    //curl -X DELETE -v localhost:8088/director/5
    @RequestMapping(value = "/director/{directorId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDirectorById(@PathVariable(value = "directorId") Integer directorId){
        LOGGER.debug("deleteDirectorById({})", directorId);
        directorService.deleteDirector(directorId);
    }
}
