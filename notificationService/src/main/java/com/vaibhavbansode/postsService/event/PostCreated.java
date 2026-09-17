package com.vaibhavbansode.postsService.event;

import lombok.Data;

import java.util.List;

@Data
public class PostCreated {

    private Long PostId;
    private Long userId;
    private String message;
    private List<Long> connections;
}
