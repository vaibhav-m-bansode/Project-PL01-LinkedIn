package com.vaibhavbansode.userService.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.vaibhavbansode.userService.entity.User}
 */
public record UserDto(Long id, String name, String email) implements Serializable {
}