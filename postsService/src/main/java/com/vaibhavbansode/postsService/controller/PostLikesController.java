package com.vaibhavbansode.postsService.controller;

import com.vaibhavbansode.postsService.entity.PostLike;
import com.vaibhavbansode.postsService.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> LikePost(@PathVariable Long postId) {
        postLikeService.LikePost(postId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> UnLikePost(@PathVariable Long postId) {
        postLikeService.UnLikePost(postId);
        return ResponseEntity.noContent().build();
    }
}
