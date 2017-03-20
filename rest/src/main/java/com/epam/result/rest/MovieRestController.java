package com.epam.result.rest;

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
 * Created by sw0rd on 13.03.17.
 */
@CrossOrigin
@RestController
public class MovieRestController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    MovieService movieService;

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class})
    public String incorrectDataError(){
        return "{  \"response\" : \"Incorrect Data Error\' }";
    }

    //curl -v localhost:8088/movies
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public @ResponseBody
    List<MovieDTO> getAllMoviesWithDirectorName(){
        LOGGER.debug("getAllDirectorsWithMoviesRating()");
        return movieService.getAllMoviesWithDirectorName();
    }

    //curl -v localhost:8088/movies/DateFilter
    @RequestMapping(value = {"/movies/DateFilter",
                        "/movies/DateFilter/{fromDate}/null",
                        "/movies/DateFilter/null/{toDate}",
                        "/movies/DateFilter/{fromDate}/{toDate}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<MovieDTO> getAllMoviesWithDateFilter(
            @PathVariable(value = "fromDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        LOGGER.debug("getAllMoviesWithDateFilter({})",
                (fromDate==null?"null":FORMATTER.format(fromDate))+", "+
                (toDate==null?"null":FORMATTER.format(toDate)));
        return movieService.getAllMoviesWithDateFilter(fromDate, toDate);
    }

    //curl -v localhost:8088/movie/1
    @RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody Movie getMovieById(@PathVariable(value = "movieId") Integer movieId){
        LOGGER.debug("getMovieById({})", movieId);
        return movieService.getMovieById(movieId);
    }

//    curl -H "Content-Type: application/json" -X POST -d '{"movieTitle":"xyz","releaseDate":"2015-12-09","rating":"6.5","movieDirectorId":"2"}' -v localhost:8088/movie
    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Integer addMovie(@RequestBody Movie movie){
        LOGGER.debug("addMovie({})", movie.getMovieTitle()+" "+movie.getReleaseDateAsString());
        return movieService.addMovie(movie);
    }

//    curl -H "Content-Type: application/json" -X PUT -d '{"movieId":"2","movieTitle":"xyz","releaseDate":"2015-12-09","rating":"6.5","movieDirectorId":"2"}' -v localhost:8088/movie
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
