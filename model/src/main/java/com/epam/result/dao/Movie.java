package com.epam.result.dao;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Objects;


/**
 * Created by sw0rd on 05.03.17.
 */
public class Movie {

    private Integer movieID;
    private String movieTitle;
    private LocalDate releaseDate;
    private Double rating;
    private Integer movieDirectorID;

    public Movie() {
    }

    public Movie(String movieTitle, LocalDate releaseDate, Double rating, Integer movieDirectorID) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.movieDirectorID = movieDirectorID;
    }

    public Movie(Integer movieID, String movieTitle, LocalDate releaseDate, Double rating, Integer movieDirectorID) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.movieDirectorID = movieDirectorID;
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

    public String getReleaseDateAsString() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return getReleaseDate().toString(formatter);
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getMovieDirectorID() {
        return movieDirectorID;
    }

    public void setMovieDirectorID(Integer movieDirectorID) {
        this.movieDirectorID = movieDirectorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movieID, movie.movieID) &&
                Objects.equals(movieTitle, movie.movieTitle) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(rating, movie.rating) &&
                Objects.equals(movieDirectorID, movie.movieDirectorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, movieTitle, releaseDate, rating, movieDirectorID);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", movieTitle='" + movieTitle + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", movieDirectorID=" + movieDirectorID +
                '}';
    }


}
