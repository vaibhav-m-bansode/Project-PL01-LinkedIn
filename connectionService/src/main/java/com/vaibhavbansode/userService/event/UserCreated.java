package com.vaibhavbansode.userService.event;

import lombok.Data;

@Data
public class UserCreated {
    private Long userId;
    private String name;
}
