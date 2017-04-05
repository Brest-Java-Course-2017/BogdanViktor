package com.epam.result.rest.controllers;

import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import com.epam.result.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author  Bogdan Viktor
 */
@CrossOrigin
@RestController
public class MovieRestController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    MovieService movieService;


    //curl -v localhost:8088/movies
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public @ResponseBody
    List<MovieDTO> getAllMoviesDTO(){
        LOGGER.debug("getAllMoviesDTO()");
        return movieService.getAllMovieDTO();
    }

    @RequestMapping(value = "/movies/createdBy/{directorId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Movie> getAllMoviesCreatedByDirector(@PathVariable(value = "directorId")Integer directorId){
        LOGGER.debug("getAllMoviesCreatedByDirector()");
        return movieService.getAllMoviesCreatedByDirector(directorId);
    }


//    curl -v "localhost:8088/movies/DateFilter?startDate=2016-01-01&endDate=2017-12-31"
    @RequestMapping(value = "/movies/DateFilter", method = RequestMethod.GET)
    public @ResponseBody
    List<MovieDTO> getAllMoviesDTOWithDateFilter(
            @RequestParam(value = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        LOGGER.debug("getAllMovieDTOWithDateFilter({})",
                (startDate==null?"null":FORMATTER.format(startDate))+", "+
                        (endDate==null?"null":FORMATTER.format(endDate)));
        return movieService.getAllMovieDTOWithDateFilter(startDate, endDate);
    }

    //curl -v localhost:8088/movie/1
    @RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody Movie getMovieById(@PathVariable(value = "movieId") Integer movieId){
        LOGGER.debug("getMovieById({})", movieId);
        return movieService.getMovieById(movieId);
    }

    //curl -v localhost:8088/movieDTO/1
    @RequestMapping(value = "/movieDTO/{movieId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody MovieDTO getMovieDTOById(@PathVariable(value = "movieId") Integer movieId){
        LOGGER.debug("getMovieById({})", movieId);
        return movieService.getMovieDTOById(movieId);
    }

//    curl -H "Content-Type: application/json" -X POST -d '{"movieTitle":"test","releaseDate":"2015-12-09","rating":"6.5","movieDirectorId":"2"}' -v localhost:8088/movie
    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Integer addMovie(@RequestBody Movie movie){
        LOGGER.debug("addMovie({})", movie.getMovieTitle()+" "+movie.getReleaseDateAsString());
        return movieService.addMovie(movie);
    }

//    curl -H "Content-Type: application/json" -X PUT -d '{"movieId":"2","movieTitle":"test","releaseDate":"2015-12-09","rating":"6.5","movieDirectorId":"2"}' -v localhost:8088/movie
    @RequestMapping(value = "/movie", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateMovie(@RequestBody Movie movie){
        LOGGER.debug("updateDirector({})", movie.getMovieTitle()+" "+movie.getReleaseDateAsString());
        movieService.updateMovie(movie);
    }


    //curl -X DELETE -v localhost:8088/movie/5
    @RequestMapping(value = "/movie/{movieId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMovieById(@PathVariable(value = "movieId") Integer movieId){
        LOGGER.debug("deleteMovieById({})", movieId);
        movieService.deleteMovie(movieId);
    }
}
