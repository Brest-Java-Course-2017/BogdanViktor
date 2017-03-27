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
 * @author  Bogdan Viktor
 */
@Controller
public class DirectorController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    MovieService movieService;

    @Autowired
    DirectorService directorService;



    @RequestMapping(value = "/directors")
    public String directors(Model model) {
        LOGGER.debug(" /directors page.");
        List directorList = directorService.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        return "directorsPage";
    }

    @RequestMapping(value = "/director/edit", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public String directorEdit(
            @RequestParam("directorId") Integer directorId,
            Model model) {
        LOGGER.debug("director edit page, directorID={}", directorId);
        Director director = directorService.getDirectorById(directorId);
        model.addAttribute("director", director);
        return "directorEdit";
    }


    @RequestMapping(value = "/director", method = RequestMethod.POST)
    public String saveDirector(
            @ModelAttribute Director director,
            Model model) {
        LOGGER.debug("update director");
            directorService.updateDirector(director);
            List directorList = directorService.getAllDirectorDTO();
            model.addAttribute("directorList", directorList);
            return "directorsPage";
    }


    @RequestMapping(value = "/director/add", method = RequestMethod.GET)
    public String directorAdd(){
        LOGGER.debug("add new director page");
        return "directorAdd";
    }

    @RequestMapping(value = "/director/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String addDirector(@ModelAttribute Director director, Model model) {
        LOGGER.debug("add director");
            directorService.addDirector(director);
            List directorList = directorService.getAllDirectorDTO();
            model.addAttribute("directorList", directorList);
            return "directorsPage";
    }


    @RequestMapping(value = "/director/details", method = RequestMethod.GET)
    public String directorDetails(
            @RequestParam("directorId") Integer directorId,
            Model model) {
        LOGGER.debug("director details page, directorId={}", directorId);
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
        LOGGER.debug("director delete page, directorID={}", directorId);
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
        LOGGER.debug("director delete");
        directorService.deleteDirector(directorId);
        List directorList = directorService.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        return "directorsPage";
    }


}
