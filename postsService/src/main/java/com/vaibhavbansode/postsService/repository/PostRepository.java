package com.vaibhavbansode.postsService.repository;

import com.vaibhavbansode.postsService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getAllByPostId(Long userId);

    Optional<Post> findByPostId(Long postID);

    List<Post> getAllByUserId(Long userId);
}