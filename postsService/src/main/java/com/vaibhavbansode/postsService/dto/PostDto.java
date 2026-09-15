package com.vaibhavbansode.postsService.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.vaibhavbansode.postsService.entity.Post}
 */
public record PostDto(Long postId, Long userId, String content, LocalDateTime createdAt) implements Serializable {
}