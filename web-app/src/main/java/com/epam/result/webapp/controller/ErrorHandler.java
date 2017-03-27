package com.epam.result.webapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author  Bogdan Viktor
 */

@ControllerAdvice
public class ErrorHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(Exception ex) {
        LOGGER.debug(ex.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("errorPage");
        return mav;
    }
}
