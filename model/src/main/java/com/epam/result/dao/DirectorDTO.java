package com.epam.result.dao;

import java.util.Objects;

/**
 * The {@code DirectorDTO} - this is just object "Director"
 * with additional field - average rating of director's movies.
 * @author  Bogdan Viktor
 */
public class DirectorDTO {
    /** The value is used for identification director in data base. */
    private Integer directorId;
    /** First name of director. */
    private String firstName;
    /** First name of director. */
    private String lastName;
    /** average rating of director's movies. */
    private double averageRating;

    /**
     * Initializes a newly created {@code DirectorDTO} object.
     */
    public DirectorDTO() {
    }

    /**
     * Initializes a newly created {@code Director} object
     * and the appropriate fields are set.
     * @param  firstName is director's first name.
     * @param  lastName is director's last name.
     *         A {@code DirectorDTO}
     */
    public DirectorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Initializes a newly created {@code DirectorDTO} object
     * and the appropriate fields are set.
     * @param  directorId is director's ID.
     * @param  firstName is director's first name.
     * @param  lastName is director's last name.
     * @param  averageRating
     *         A {@code DirectorDTO}
     */
    public DirectorDTO(int directorId, String firstName, String lastName, double averageRating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorId = directorId;
        this.averageRating = averageRating;
    }

    /**
     * @return  Returns the first name of director.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the director's first name to the <code>firstName</code>.
     * @param      firstName   the new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return  Returns the last name of director.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the director's last name to the <code>lastName</code>.
     * @param      lastName   the new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return  Returns the director's ID.
     */
    public Integer getDirectorId() {
        return directorId;
    }

    /**
     * Sets the director's ID to the <code>directorId</code>
     * @param      directorId   the new director's ID.
     */
    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    /**
     * @return  Returns the average rating of director's movies.
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Sets the average rating of director's movies to
     * the <code>averageRating</code>
     * @param averageRating   the new average rating of director's movies.
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
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
        DirectorDTO that = (DirectorDTO) o;
        return Double.compare(that.averageRating, averageRating) == 0 &&
                Objects.equals(directorId, that.directorId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    /**
     * Returns a hash code value for the directorDTO. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * @return  a hash code value for this object.d
     */
    @Override
    public int hashCode() {
        return Objects.hash(directorId, firstName, lastName, averageRating);
    }

    /**
     * @return  a string representation of the directorDTO.
     */
    @Override
    public String toString() {
        return "DirectorDTO{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
