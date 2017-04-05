package com.epam.result.webapp.controllers;

import com.epam.result.client.rest_api.DirectorConsumer;
import com.epam.result.client.rest_api.MovieConsumer;
import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author  Bogdan Viktor
 */
@Controller
public class MovieController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    MovieConsumer movieConsumer;

    @Autowired
    DirectorConsumer directorConsumer;

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:movies";
    }

    @GetMapping(value = "/movies")
    public String movies(Model model) {
        LOGGER.debug(" /movies page.");
        List moviesList = movieConsumer.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "moviesPage";
    }

    @RequestMapping(value = "/movie/add", method = RequestMethod.GET)
    public String addMoviePage(Model model) {
        LOGGER.debug(" addMoviePage()");
        List directorList = directorConsumer.getAllDirectorDTO();
        model.addAttribute("directorList", directorList);
        return "movieAdd";
    }

    @RequestMapping(value = "/movie/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String addMovie( @RequestParam(value="movieTitle") String movieTitle,
                            @RequestParam(value = "releaseDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseDate,
                            @RequestParam(value="rating") Double rating,
                            @RequestParam(value = "movieDirectorId") Integer movieDirectorId,
                            Model model) {
        LOGGER.debug("addMovie({})");
        Movie movie = new Movie(movieTitle, releaseDate, rating, movieDirectorId);
        movieConsumer.addMovie(movie);
        List moviesList = movieConsumer.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "moviesPage";
    }

    @RequestMapping(value = "/movie/datefilter", method = RequestMethod.POST)
    public String getMoviesWithDateFilter(
                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                            Model model) {
        LOGGER.debug("get movies with date filter");
        List moviesList = movieConsumer.getAllMovieDTOWithDateFilter(startDate, endDate);
        SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        String startDateMessage = (startDate==null?"":" from "+FORMATTER.format(startDate));
        String endDateMessage = (endDate==null?"":" to "+FORMATTER.format(endDate));
        model.addAttribute("moviesList", moviesList);
        model.addAttribute("startDate", startDateMessage);
        model.addAttribute("endDate", endDateMessage);
        return "moviesPage";
    }


    @RequestMapping(value = "/movie/edit", method = RequestMethod.GET)
    public String editMoviePage(@RequestParam("movieId") Integer movieId, Model model) {
        LOGGER.debug(" movieEdit page()");
        Movie movie = movieConsumer.getMovieById(movieId);
        List directorList = directorConsumer.getAllDirectorDTO();
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
        LOGGER.debug(" edit movie()");
        Movie movie = new Movie(movieId, movieTitle, releaseDate, rating, movieDirectorId);
        movieConsumer.updateMovie(movie);
        List moviesList = movieConsumer.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "moviesPage";
    }

    @RequestMapping(value = "/movie/delete", method = RequestMethod.GET)
    public String deleteMoviePage(@RequestParam("movieId") Integer movieId, Model model) {
        LOGGER.debug(" movieDelete page()");
        MovieDTO movie = movieConsumer.getMovieDTOById(movieId);
        model.addAttribute("movie", movie);
        return "movieDelete";
    }

    @RequestMapping(value = "/movie/delete", method = RequestMethod.POST)
    public String deleteMovie(@RequestParam("movieId") Integer movieId, Model model) {
        LOGGER.debug(" delete movie()");
        movieConsumer.deleteMovie(movieId);
        List moviesList = movieConsumer.getAllMovieDTO();
        model.addAttribute("moviesList", moviesList);
        return "moviesPage";
    }

}
