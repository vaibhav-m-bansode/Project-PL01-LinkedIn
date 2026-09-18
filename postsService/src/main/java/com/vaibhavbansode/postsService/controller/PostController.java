package com.vaibhavbansode.postsService.controller;

import com.vaibhavbansode.postsService.dto.PostCreateRequestDto;
import com.vaibhavbansode.postsService.dto.PostDto;
import com.vaibhavbansode.postsService.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestPart("post") PostCreateRequestDto postCreateRequestDto,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        PostDto postDto = postService.createPost(postCreateRequestDto, files);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> postDtoList = postService.getAllPostsOfUser(userId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/{postID}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Long postID,@RequestHeader("X-User_Id")Long userId) {
        log.info("Getting post by post id {}", postID);
        return new ResponseEntity<>(postService.getPostByPostId(postID),HttpStatus.FOUND);
    }
}
