package com.epam.result.dao;

import java.util.Objects;


/**
 * The {@code Director} - this is one of the entities in project.
 * Have some simple methods.
 * @author  Bogdan Viktor
  */
public class Director {
    /** The value is used for identification in data base. */
    private Integer directorId;
    /** First name of director. */
    private String firstName;
    /** First name of director. */
    private String lastName;

    /**
     * Initializes a newly created {@code Director} object.
     */
    public Director() {
    }

    /**
     * Initializes a newly created {@code Director} object
     * and the appropriate fields are set.
     * @param  firstName is director's first name.
     * @param  lastName is director's last name.
     *         A {@code Director}
     */
    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Initializes a newly created {@code Director} object
     * and the appropriate fields are set.
     * @param  directorId is director's ID.
     * @param  firstName  is director's first name.
     * @param  lastName is director's last name.
     *         A {@code Director}
     */
    public Director(Integer directorId, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorId = directorId;
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
        Director Director = (Director) o;
        return directorId == Director.directorId &&
                Objects.equals(firstName, Director.firstName) &&
                Objects.equals(lastName, Director.lastName);
    }

    /**
     * Returns a hash code value for the director. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * @return  a hash code value for this object.d
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, directorId);
    }

    /**
     * @return  a string representation of the director.
     */
    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
