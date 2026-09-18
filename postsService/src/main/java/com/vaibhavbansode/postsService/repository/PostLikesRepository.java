package com.vaibhavbansode.postsService.repository;

import com.vaibhavbansode.postsService.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}