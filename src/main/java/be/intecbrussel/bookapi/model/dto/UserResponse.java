package be.intecbrussel.bookapi.model.dto;

import be.intecbrussel.bookapi.model.BorrowedBook;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private String firstName;
    private String lastName;
    private byte[] profilePhoto;

    public UserResponse(String firstName, String lastName, byte[] profilePhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
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

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
