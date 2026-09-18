package com.vaibhavbansode.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record SignupRequest(
        @NotEmpty(message = "Name cannot be empty")
        String name,
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email is not valid")
        String email,

        @NotEmpty(message = "Password cannot be empty")
        String password
) {
}
