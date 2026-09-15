package com.vaibhavbansode.postsService.service;

import com.vaibhavbansode.postsService.auth.AuthContextHolder;
import com.vaibhavbansode.postsService.dto.PostCreateRequestDto;
import com.vaibhavbansode.postsService.dto.PostDto;
import com.vaibhavbansode.postsService.entity.Post;
import com.vaibhavbansode.postsService.exception.ResourceNotFoundException;
import com.vaibhavbansode.postsService.mapper.PostMapper;
import com.vaibhavbansode.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Creating post for the user {}", userId);
        Post post = postMapper.toPost(postCreateRequestDto);
        post.setUserId(userId);
        log.info("Creating post of the user {}",post.getUserId());

        Post createdPost = postRepository.save(post);
        log.info(createdPost.toString());
        return postMapper.toPostDto(createdPost);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all posts of user {}", userId);
        List<Post> posts = postRepository.getAllByUserId(userId);
        return posts.stream().map(postMapper::toPostDto).collect(Collectors.toList());
    }

    public PostDto getPostByPostId(Long postID) {
        log.info("Getting post by post id {}", postID);
       Post post = postRepository.findByPostId(postID).orElseThrow( ()-> new ResourceNotFoundException("Post Not available !!!"));
        return postMapper.toPostDto(post);
    }
}
