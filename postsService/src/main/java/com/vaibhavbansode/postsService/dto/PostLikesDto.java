package com.vaibhavbansode.postsService.dto;

import com.vaibhavbansode.postsService.entity.PostLike;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link PostLike}
 */
public record PostLikesDto(Long id, Long userId, Long postId, LocalDateTime createdDate) implements Serializable {
}