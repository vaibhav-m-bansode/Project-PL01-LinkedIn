package com.vaibhavbansode.postsService.service;

import com.vaibhavbansode.postsService.auth.AuthContextHolder;
import com.vaibhavbansode.postsService.entity.Post;
import com.vaibhavbansode.postsService.entity.PostLike;
import com.vaibhavbansode.postsService.event.PostLiked;
import com.vaibhavbansode.postsService.exception.BadRequestException;
import com.vaibhavbansode.postsService.exception.ResourceNotFoundException;
import com.vaibhavbansode.postsService.repository.PostLikesRepository;
import com.vaibhavbansode.postsService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final KafkaTemplate<Long, PostLiked> kafkaTemplate;

    @Transactional
    public void LikePost(Long postId){

        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with ID : {} liking the post with ID : {}",userId, postId);

        Post post = postRepository.findByPostId(postId).orElseThrow(
                () -> new ResourceNotFoundException("Invalid Post, You Cannot like post!!!"));

        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId, postId);
        if(hasAlreadyLiked){
            throw new BadRequestException("Post Already Liked");
        }

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikesRepository.save(postLike);

        PostLiked postLikedEvent = new PostLiked();
        postLikedEvent.setPostId(postId);
        postLikedEvent.setLikedByUserId(userId);
        postLikedEvent.setOwnerId(post.getUserId());
        kafkaTemplate.send("post_liked_topic", postLikedEvent);

    }

    @Transactional
    public void UnLikePost(Long postId){
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with ID : {} unliking the post with ID : {}",userId, postId);

        postRepository.findByPostId(postId).orElseThrow(
                () -> new ResourceNotFoundException("Invalid Post, You Cannot like post!!!"));
        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId, postId);
        if(!hasAlreadyLiked){
            throw new BadRequestException("You Cannot unlike post"+postId);
        }
        postLikesRepository.deleteByPostIdAndUserId(postId, userId);
    }
}
