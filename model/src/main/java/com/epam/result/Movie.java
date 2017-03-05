package com.epam.result;

import org.joda.time.DateTime;

import java.util.Objects;


/**
 * Created by sw0rd on 05.03.17.
 */
public class Movie {

    private int movieID;
    private String movieTitle;
    private DateTime releaseDate;
    private byte rating;
    private Director director;

    public Movie() {
    }

    public Movie(String movieTitle, DateTime releaseDate, byte rating, Director director) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.director = director;
    }

    public Movie(int movieID, String movieTitle, DateTime releaseDate, byte rating, Director director) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.director = director;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieID == movie.movieID &&
                rating == movie.rating &&
                Objects.equals(movieTitle, movie.movieTitle) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, movieTitle, releaseDate, rating, director);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", movieTitle='" + movieTitle + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", director=" + director +
                '}';
    }
}
