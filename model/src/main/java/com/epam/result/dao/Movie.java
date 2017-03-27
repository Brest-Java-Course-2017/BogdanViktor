package com.epam.result.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * The {@code Movie} - this is one of the entities in project.
 * Have some simple methods.
 * @author  Bogdan Viktor
 */
public class Movie {
    /** The value is used for identification in data base. */
    private Integer movieId;
    /** This is movie title. */
    private String movieTitle;

    /** This is movie release date. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    /** This is movie release rating. */
    private Double rating;
    /** The value is used for identification in data base
     * director of this movie.
     */
    private Integer movieDirectorId;

    /** The value is used to format date to "yyyy-MM-dd". */
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Initializes a newly created {@code Movie} object.
     */
    public Movie() {
    }

    /**
     * Initializes a newly created {@code Movie} object
     * and the appropriate fields are set.
     * @param  movieTitle is movie title.
     * @param  releaseDate is movie release date.
     * @param  rating is movie rating.
     * @param  movieDirectorId is director's ID of this movie.
     *         A {@code Movie}
     */
    public Movie(String movieTitle, Date releaseDate, Double rating, Integer movieDirectorId) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.movieDirectorId = movieDirectorId;
    }

    /**
     * Initializes a newly created {@code Movie} object
     * and the appropriate fields are set.
     * @param  movieId is movie ID.
     * @param  movieTitle is movie title.
     * @param  releaseDate is movie release date.
     * @param  rating is movie rating.
     * @param  movieDirectorId is director's ID of this movie.
     *         A {@code Movie}
     */
    public Movie(Integer movieId, String movieTitle, Date releaseDate, Double rating, Integer movieDirectorId) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.movieDirectorId = movieDirectorId;
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
     * @return  Returns the movie release date in format "yyyy-MM-dd".
     */
    public String getReleaseDateAsString() {
        return FORMATTER.format(releaseDate);
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
     * @return  Returns the director's id of this movie.
     */
    public Integer getMovieDirectorId() {
        return movieDirectorId;
    }

    /**
     * Sets the director's id of this movie to the <code>movieDirectorId</code>.
     * @param      movieDirectorId   the director's id of this movie.
     */
    public void setMovieDirectorId(Integer movieDirectorId) {
        this.movieDirectorId = movieDirectorId;
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
        Movie movie = (Movie) o;
        return Objects.equals(movieId, movie.movieId) &&
                Objects.equals(movieTitle, movie.movieTitle) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(rating, movie.rating) &&
                Objects.equals(movieDirectorId, movie.movieDirectorId);
    }

    /**
     * Returns a hash code value for the movie. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * @return  a hash code value for this object.d
     */
    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieTitle, releaseDate, rating, movieDirectorId);
    }

    /**
     * @return  a string representation of the movie.
     */
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
