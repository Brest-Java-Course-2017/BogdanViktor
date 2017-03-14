package com.epam.result.dao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * Created by sw0rd on 05.03.17.
 */
public class MovieDTO {

    private Integer movieId;
    private String movieTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private Double rating;
    private String directorsFirstName;
    private String directorsLastName;

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");


    public MovieDTO() {
    }

    public MovieDTO(Integer movieId, String movieTitle, Date releaseDate, Double rating, String directorsFirstName, String directorsLastName) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.directorsFirstName = directorsFirstName;
        this.directorsLastName = directorsLastName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(movieId, movieDTO.movieId) &&
                Objects.equals(movieTitle, movieDTO.movieTitle) &&
                Objects.equals(releaseDate, movieDTO.releaseDate) &&
                Objects.equals(rating, movieDTO.rating) &&
                Objects.equals(directorsFirstName, movieDTO.directorsFirstName) &&
                Objects.equals(directorsLastName, movieDTO.directorsLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieTitle, releaseDate, rating, directorsFirstName, directorsLastName);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", releaseDate=" + FORMATTER.format(releaseDate) +
                ", rating=" + rating +
                ", directorsFirstName='" + directorsFirstName + '\'' +
                ", directorsLastName='" + directorsLastName + '\'' +
                '}';
    }
}
