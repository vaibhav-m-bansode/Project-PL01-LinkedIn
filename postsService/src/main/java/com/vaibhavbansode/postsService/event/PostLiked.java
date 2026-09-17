package com.vaibhavbansode.postsService.event;

import lombok.Data;

@Data
public class PostLiked {

    private Long postId;
    private Long ownerId;
    private Long likedByUserId;
}
