package com.epam.result.rest.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * @author  Bogdan Viktor
 */
@CrossOrigin
@ControllerAdvice
public class RestErrorHandler{

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleDataAccessException(DataAccessException ex) {
        LOGGER.debug("Handling DataAccessException: " + ex);
        return "DataAccessException: " + ex.getLocalizedMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.debug("Handling IllegalArgumentException: " + ex);
        return "IllegalArgumentException: " + ex.getLocalizedMessage();
    }
}
