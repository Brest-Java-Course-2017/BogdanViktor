package com.epam.result.dao;

import java.util.Objects;

/**
 * Created by sw0rd on 05.03.17.
 */
public class DirectorDTO {

    private Integer directorID;
    private String firstName;
    private String lastName;
    private double avarageRating;

    public DirectorDTO() {
    }

    public DirectorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DirectorDTO(int directorID, String firstName, String lastName, double avarageRating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorID = directorID;
        this.avarageRating=avarageRating;
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

    public double getAvarageRating() {
        return avarageRating;
    }

    public void setAvarageRating(double avarageRating) {
        this.avarageRating = avarageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectorDTO that = (DirectorDTO) o;
        return Double.compare(that.avarageRating, avarageRating) == 0 &&
                Objects.equals(directorID, that.directorID) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorID, firstName, lastName, avarageRating);
    }

    @Override
    public String toString() {
        return "DirectorDTO{" +
                "directorID=" + directorID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avarageRating=" + avarageRating +
                '}';
    }
}
