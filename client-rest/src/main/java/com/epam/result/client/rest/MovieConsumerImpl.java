package com.epam.result.client.rest;

import com.epam.result.client.rest_api.MovieConsumer;
import com.epam.result.dao.Movie;
import com.epam.result.dao.MovieDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author  Bogdan Viktor
 */
public class MovieConsumerImpl implements MovieConsumer {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${protocol}://${host}:${port}${prefix}")
    private String url;

    @Value("${point.movie}")
    private String urlMovie;

    @Value("${point.movies}")
    private String urlMovies;

    ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);


    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    @Override
    public List<Movie> getAllMoviesCreatedByDirector(Integer directorID) {
        LOGGER.debug("getAllMoviesCreatedByDirector()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url+urlMovies+"/createdBy/{directorId}",
                Object.class, directorID);
        List<Movie> movies = (List<Movie>)responseEntity.getBody();
        return movies;
    }


    @Override
    public Movie getMovieById(Integer movieId) {
        LOGGER.debug("getMovieById()");
        ResponseEntity<Movie> responseEntity = restTemplate
                .getForEntity(url + urlMovie+"/{movieId}", Movie.class, movieId);
        return responseEntity.getBody();
    }

    @Override
    public List<MovieDTO> getAllMovieDTO() {
        LOGGER.debug("getAllMovieDTO()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url+urlMovies, Object.class);
        List<MovieDTO> moviesDTO = (List<MovieDTO>)responseEntity.getBody();
        return moviesDTO;
    }

    @Override
    public List<MovieDTO> getAllMovieDTOWithDateFilter(Date startDate, Date endDate) {
        LOGGER.debug("getAllMovieDTOWithDateFilter()");
        String startDateString = startDate==null?"":FORMATTER.format(startDate);
        String endDateString = endDate==null?"":FORMATTER.format(endDate);

        ResponseEntity responseEntity = restTemplate.getForEntity(url+urlMovies+"/DateFilter?startDate=" +
                        "{startDateString}&endDate={endDateString}",
                Object.class, startDateString, endDateString);

        List<MovieDTO> moviesDTO = (List<MovieDTO>)responseEntity.getBody();
        return moviesDTO;
    }


    @Override
    public MovieDTO getMovieDTOById(Integer movieId) {
        LOGGER.debug("getMovieDTOById()");
        ResponseEntity<MovieDTO> responseEntity = restTemplate
                .getForEntity(url +"/movieDTO/{movieId}", MovieDTO.class, movieId);
        return responseEntity.getBody();

    }

    @Override
    public int addMovie(Movie movie) {
        LOGGER.debug("addMovie()");
        return restTemplate.postForObject(url + urlMovie, movie, Integer.class);
    }

    @Override
    public void updateMovie(Movie movie) {
        LOGGER.debug("updateMovie()");
        restTemplate.put(url+urlMovie, movie);
    }

    @Override
    public void deleteMovie(Integer movieID) {
        LOGGER.debug("deleteMovie()");
        restTemplate.delete(url+urlMovie+"/{movieID}", movieID);
    }

}
