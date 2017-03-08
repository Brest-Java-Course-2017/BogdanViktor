package com.epam.result;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Objects;


/**
 * Created by sw0rd on 05.03.17.
 */
public class Movie {

    private Integer movieID;
    private String movieTitle;
    private LocalDate releaseDate;
    private Double rating;
    private Integer id_movie_director;

    public Movie() {
    }

    public Movie(String movieTitle, LocalDate releaseDate, Double rating, Integer id_movie_director) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.id_movie_director = id_movie_director;
    }

    public Movie(int movieID, String movieTitle, LocalDate releaseDate, Double rating, Integer id_movie_director) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.id_movie_director = id_movie_director;
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

    public Integer getId_movie_director() {
        return id_movie_director;
    }

    public void setId_movie_director(Integer id_movie_director) {
        this.id_movie_director = id_movie_director;
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
                Objects.equals(id_movie_director, movie.id_movie_director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, movieTitle, releaseDate, rating, id_movie_director);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", movieTitle='" + movieTitle + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", id_movie_director=" + id_movie_director +
                '}';
    }
}
