package com.sahti.backend.dto;

import com.sahti.backend.entity.User;

import java.time.LocalDateTime;

public class UserResponse {

    private final Long id;
    private final String email;
    private final LocalDateTime dateCreation;

    public UserResponse(Long id, String email, LocalDateTime dateCreation) {
        this.id = id;
        this.email = email;
        this.dateCreation = dateCreation;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getDateCreation());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}
