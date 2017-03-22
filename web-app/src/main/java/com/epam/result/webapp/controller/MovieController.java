package com.epam.result.webapp.controller;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import com.epam.result.service.DirectorService;
import com.epam.result.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sw0rd on 15.03.17.
 */
@Controller
public class MovieController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    MovieService movieService;

    @Autowired
    DirectorService directorService;

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:movies";
    }

    @GetMapping(value = "/movies")
    public String movies(Model model) {
        LOGGER.debug(" /movies page.");
        List moviesList = movieService.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "movies";
    }

    @RequestMapping(value = "/movie/add", method = RequestMethod.GET)
    public String addMoviePage(Model model) {
        LOGGER.debug(" addMoviePage()");
        List directorList = directorService.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        return "movieAdd";
    }

    @RequestMapping(value = "/movie/add", method = RequestMethod.POST)
    public String addMovie( @RequestParam(value="movieTitle") String movieTitle,
                            @RequestParam(value = "releaseDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseDate,
                            @RequestParam(value="rating") Double rating,
                            @RequestParam(value = "movieDirectorId") Integer movieDirectorId,
                            Model model) {
        LOGGER.debug("addMovie({})");
        Movie movie = new Movie(movieTitle, releaseDate, rating, movieDirectorId);
        movieService.addMovie(movie);
        List moviesList = movieService.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "movies";
    }

    @RequestMapping(value = "/movie/datefilter", method = RequestMethod.POST)
    public String getMoviesWithDateFilter(
                            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
                            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate,
                            Model model) {
        LOGGER.debug("addMovie({})");
        List moviesList = movieService.getAllMovieDTOWithDateFilter(fromDate, toDate);
        SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        String fromDateMessage = (fromDate==null?"":" from "+FORMATTER.format(fromDate));
        String toDateMessage = (toDate==null?"":" to "+FORMATTER.format(toDate));
        model.addAttribute("moviesList", moviesList);
        model.addAttribute("fromDate", fromDateMessage);
        model.addAttribute("toDate", toDateMessage);
        return "movies";
    }


    @RequestMapping(value = "/movie/edit", method = RequestMethod.GET)
    public String editMoviePage(@RequestParam("movieId") Integer movieId, Model model) {
        LOGGER.debug(" editMoviePage()");
        Movie movie = movieService.getMovieById(movieId);
        List directorList = directorService.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        model.addAttribute("movie", movie);
        return "movieEdit";
    }

    @RequestMapping(value = "/movie/edit", method = RequestMethod.POST)
    public String editMovie(@RequestParam(value="movieId") Integer movieId,
                            @RequestParam(value="movieTitle") String movieTitle,
                            @RequestParam(value = "releaseDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseDate,
                            @RequestParam(value="rating") Double rating,
                            @RequestParam(value = "movieDirectorId") Integer movieDirectorId,
                            Model model) {
        LOGGER.debug(" editMovie()");
        Movie movie = new Movie(movieId, movieTitle, releaseDate, rating, movieDirectorId);
        movieService.updateMovie(movie);
        List moviesList = movieService.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "movies";
    }

    @RequestMapping(value = "/movie/delete", method = RequestMethod.GET)
    public String deleteMoviePage(@RequestParam("movieId") Integer movieId, Model model) {
        LOGGER.debug(" deleteMoviePage()");
        MovieDTO movie = movieService.getMovieDTOById(movieId);
        model.addAttribute("movie", movie);
        return "movieDelete";
    }

    @RequestMapping(value = "/movie/delete", method = RequestMethod.POST)
    public String deleteMovie(@RequestParam("movieId") Integer movieId, Model model) {
        LOGGER.debug(" deleteMovie()");
        movieService.deleteMovie(movieId);
        List moviesList = movieService.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "movies";
    }
    /*


    @RequestMapping(value = "/director", method = RequestMethod.POST)
    public String saveDirector(@ModelAttribute Director director, Model model) {
        LOGGER.debug("saveDirector({})");
            directorService.updateDirector(director);
            List directorList = directorService.getAllDirectorDTO();
            model.addAttribute("directorList", directorList);
            return "directors";
    }
     */

////    @ExceptionHandler(IllegalArgumentException.class)
//    @RequestMapping("error")
////    @ExceptionHandler(DataAccessException.class, IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleDataAccessException(HttpServletRequest req, DataAccessException ex, Model model) {
//        LOGGER.debug("Handling DataAccessException: " + ex);
//        model.addAttribute("exception", ex);
//        model.addAttribute("url", req.getRequestURL());
//        return "errorPage";
//    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
////        logger.error("Request: " + req.getRequestURL() + " raised " + ex);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("errorPage");
//        return mav;
//    }

}
