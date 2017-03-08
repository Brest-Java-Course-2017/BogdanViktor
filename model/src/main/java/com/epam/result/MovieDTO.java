package com.epam.result;

import org.joda.time.LocalDate;

import java.util.Objects;


/**
 * Created by sw0rd on 05.03.17.
 */
public class MovieDTO {

    private Integer movieID;
    private String movieTitle;
    private LocalDate releaseDate;
    private Double rating;
    private String directorsFirstName;
    private String directorsLastName;

    public MovieDTO() {
    }

    public MovieDTO(Integer movieID, String movieTitle, LocalDate releaseDate, Double rating, String directorsFirstName, String directorsLastName) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.directorsFirstName = directorsFirstName;
        this.directorsLastName = directorsLastName;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDirectorsFirstName() {
        return directorsFirstName;
    }

    public void setDirectorsFirstName(String directorsFirstName) {
        this.directorsFirstName = directorsFirstName;
    }

    public String getDirectorsLastName() {
        return directorsLastName;
    }

    public void setDirectorsLastName(String directorsLastName) {
        this.directorsLastName = directorsLastName;
    }
}
