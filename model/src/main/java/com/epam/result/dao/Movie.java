package com.epam.result.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * Created by sw0rd on 05.03.17.
 */
public class Movie {

    private Integer movieId;
    private String movieTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private Double rating;
    private Integer movieDirectorId;

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");


    public Movie() {
    }

    public Movie(String movieTitle, Date releaseDate, Double rating, Integer movieDirectorId) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.movieDirectorId = movieDirectorId;
    }

    public Movie(Integer movieId, String movieTitle, Date releaseDate, Double rating, Integer movieDirectorId) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.movieDirectorId = movieDirectorId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDateAsString() {
        return FORMATTER.format(releaseDate);
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getMovieDirectorId() {
        return movieDirectorId;
    }

    public void setMovieDirectorId(Integer movieDirectorId) {
        this.movieDirectorId = movieDirectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movieId, movie.movieId) &&
                Objects.equals(movieTitle, movie.movieTitle) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(rating, movie.rating) &&
                Objects.equals(movieDirectorId, movie.movieDirectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieTitle, releaseDate, rating, movieDirectorId);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", releaseDate=" + FORMATTER.format(releaseDate) +
                ", rating=" + rating +
                ", movieDirectorId=" + movieDirectorId +
                '}';
    }


}
