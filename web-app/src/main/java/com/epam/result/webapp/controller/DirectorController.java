package com.epam.result.webapp.controller;

import com.epam.result.dao.Director;
import com.epam.result.service.DirectorService;
import com.epam.result.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sw0rd on 15.03.17.
 */
@Controller
public class DirectorController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    MovieService movieService;

    @Autowired
    DirectorService directorService;



    @GetMapping(value = "/directors")
    public String directors(Model model) {
        LOGGER.debug(" /movies page.");
        List directorList = directorService.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        return "directors";
    }

    @RequestMapping(value = "/director/edit", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public String directorEdit(
            @RequestParam("directorId") Integer directorId,
            Model model) {
        LOGGER.debug("getDirectorById({})", directorId);
        Director director = directorService.getDirectorById(directorId);
        model.addAttribute("director", director);
        return "directorEdit";
    }


    @RequestMapping(value = "/director", method = RequestMethod.POST)
    public String saveDirector(
            @ModelAttribute Director director,
            Model model) {
        LOGGER.debug("saveDirector({})");
            directorService.updateDirector(director);
            List directorList = directorService.getAllDirectorDTO();
            model.addAttribute("directorList", directorList);
            return "directors";
    }


    @RequestMapping(value = "/director/add", method = RequestMethod.GET)
    public String directorAdd(){
        LOGGER.debug("getDirectorById({})");
        return "directorAdd";
    }

    @RequestMapping(value = "/director/add", method = RequestMethod.POST)
    public String addDirector(@ModelAttribute Director director, Model model) {
        LOGGER.debug("addDirector({})");
            directorService.addDirector(director);
            List directorList = directorService.getAllDirectorDTO();
            model.addAttribute("directorList", directorList);
            return "directors";
    }


    @RequestMapping(value = "/director/details", method = RequestMethod.GET)
    public String directorDetails(
            @RequestParam("directorId") Integer directorId,
            Model model) {
        LOGGER.debug("directorDetails({})", directorId);
        Director director = directorService.getDirectorById(directorId);
        List moviesList = movieService.getAllMoviesCreatedByDirector(directorId);
        model.addAttribute("director", director);
        model.addAttribute("moviesList", moviesList);
        return "directorDetails";
    }

    @RequestMapping(value = "/director/delete", method = RequestMethod.GET)
    public String directorDeletePage(
            @RequestParam("directorId") Integer directorId,
            Model model) {
        LOGGER.debug("directorDeletePage({})", directorId);
        Director director = directorService.getDirectorById(directorId);
        model.addAttribute("director", director);
        List moviesList = movieService.getAllMoviesCreatedByDirector(directorId);
        if (moviesList != null && moviesList.size()>0) {
            model.addAttribute("moviesList", moviesList);
            return "directorError";
        }
        return "directorDelete";
    }

    @RequestMapping(value = "/director/delete", method = RequestMethod.POST)
    public String directorDelete(
            @RequestParam("directorId") Integer directorId,
            Model model) {
        LOGGER.debug("directorDelete({})");
        directorService.deleteDirector(directorId);
        List directorList = directorService.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        return "directors";
    }


}
