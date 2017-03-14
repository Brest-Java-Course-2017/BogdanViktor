package com.epam.result.dao;

import java.util.Objects;

/**
 * Created by sw0rd on 05.03.17.
 */
public class Director {

    private Integer directorId;

    private String firstName;
    private String lastName;

    public Director() {
    }

    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Director(Integer directorId, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorId = directorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director Director = (Director) o;
        return directorId == Director.directorId &&
                Objects.equals(firstName, Director.firstName) &&
                Objects.equals(lastName, Director.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, directorId);
    }


    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
