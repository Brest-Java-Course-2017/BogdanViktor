package com.epam.result.dao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * The {@code MovieDTO} - this is just object "Movie"
 * with additional fields - director's first name
 * and last name of this movie.
 * @author  Bogdan Viktor
 */
public class MovieDTO {
    /** The value is used for identification in data base. */
    private Integer movieId;
    /** This is movie title. */
    private String movieTitle;
    /** This is movie release date. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone="GMT+3", pattern = "yyyy-MM-dd")
    private Date releaseDate;
    /** This is movie release rating. */
    private Double rating;
    /** This is director's first name of this movie. */
    private String directorsFirstName;
    /** This is director's last name of this movie. */
    private String directorsLastName;

    /** The value is used to format date to "yyyy-MM-dd". */
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Initializes a newly created {@code MovieDTO} object.
     */
    public MovieDTO() {
    }

    /**
     * Initializes a newly created {@code MovieDTO} object
     * and the appropriate fields are set.
     * @param  movieId is movie ID.
     * @param  movieTitle is movie title.
     * @param  releaseDate is movie release date.
     * @param  rating is movie rating.
     * @param  directorsFirstName is director's first name of this movie.
     * @param  directorsLastName is director's last name of this movie.
     *         A {@code MovieDTO}
     */
    public MovieDTO(Integer movieId, String movieTitle, Date releaseDate, Double rating,
                    String directorsFirstName, String directorsLastName) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.directorsFirstName = directorsFirstName;
        this.directorsLastName = directorsLastName;
    }

    /**
     * @return  Returns the movie ID.
     */
    public Integer getMovieId() {
        return movieId;
    }

    /**
     * Sets the movie ID to the <code>movieId</code>.
     * @param      movieId   the movie ID.
     */
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    /**
     * @return  Returns the movie title.
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Sets the movie title to the <code>movieTitle</code>.
     * @param      movieTitle   the movie title.
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * @return  Returns the movie release date.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the movie release date to the <code>releaseDate</code>.
     * @param      releaseDate   the movie release date.
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return  Returns the movie rating.
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Sets the movie release rating to the <code>rating</code>.
     * @param      rating   the movie rating.
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * @return  Returns the director's first name of this movie.
     */
    public String getDirectorsFirstName() {
        return directorsFirstName;
    }

    /**
     * Sets the director's first name of this movie to the <code>directorsFirstName</code>.
     * @param      directorsFirstName   the director's first name of this movie.
     */
    public void setDirectorsFirstName(String directorsFirstName) {
        this.directorsFirstName = directorsFirstName;
    }

    /**
     * @return  Returns the director's last name of this movie.
     */
    public String getDirectorsLastName() {
        return directorsLastName;
    }

    /**
     * Sets the director's last name of this movie to the <code>directorsLastName</code>.
     * @param      directorsLastName   the director's last name of this movie.
     */
    public void setDirectorsLastName(String directorsLastName) {
        this.directorsLastName = directorsLastName;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * @param   o   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the obj
     *          argument; {@code false} otherwise.
     */
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

    /**
     * Returns a hash code value for the movieDTO. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * @return  a hash code value for this object.d
     */
    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieTitle, releaseDate, rating, directorsFirstName, directorsLastName);
    }

    /**
     * @return  a string representation of the movieDTO.
     */
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
