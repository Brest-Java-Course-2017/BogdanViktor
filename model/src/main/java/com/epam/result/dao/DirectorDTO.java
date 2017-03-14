package com.epam.result.dao;

import java.util.Objects;

/**
 * Created by sw0rd on 05.03.17.
 */
public class DirectorDTO {

    private Integer directorId;
    private String firstName;
    private String lastName;
    private double avarageRating;

    public DirectorDTO() {
    }

    public DirectorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DirectorDTO(int directorId, String firstName, String lastName, double avarageRating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorId = directorId;
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

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
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
                Objects.equals(directorId, that.directorId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorId, firstName, lastName, avarageRating);
    }

    @Override
    public String toString() {
        return "DirectorDTO{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avarageRating=" + avarageRating +
                '}';
    }
}
