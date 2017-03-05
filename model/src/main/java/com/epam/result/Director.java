package com.epam.result;

import java.util.Objects;

/**
 * Created by sw0rd on 05.03.17.
 */
public class Director {

    private String firstName;
    private String lastName;
    private int directorID;

    public Director() {
    }

    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Director(String firstName, String lastName, int directorID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorID = directorID;
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

    public int getDirectorID() {
        return directorID;
    }

    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director Director = (Director) o;
        return directorID == Director.directorID &&
                Objects.equals(firstName, Director.firstName) &&
                Objects.equals(lastName, Director.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, directorID);
    }

    @Override
    public String toString() {
        return "Director{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", DirectorID=" + directorID +
                '}';
    }
}
