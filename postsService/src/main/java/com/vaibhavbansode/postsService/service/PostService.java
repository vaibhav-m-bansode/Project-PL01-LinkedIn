package com.vaibhavbansode.postsService.service;

import com.vaibhavbansode.postsService.auth.AuthContextHolder;
import com.vaibhavbansode.postsService.client.ConnectionClient;
import com.vaibhavbansode.postsService.client.UploaderClient;
import com.vaibhavbansode.postsService.dto.PostCreateRequestDto;
import com.vaibhavbansode.postsService.dto.PostDto;
import com.vaibhavbansode.postsService.entity.Post;
import com.vaibhavbansode.postsService.event.PostCreated;
import com.vaibhavbansode.postsService.exception.ResourceNotFoundException;
import com.vaibhavbansode.postsService.mapper.PostMapper;
import com.vaibhavbansode.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;
    private final ConnectionClient connectionClient;
    private final UploaderClient uploaderClient;

    public PostDto createPost(PostCreateRequestDto request, List<MultipartFile> files) {

        Long userId = AuthContextHolder.getCurrentUserId();

        log.info("Creating post for user {}", userId);


        Post post = postMapper.toPost(request);
        post.setUserId(userId);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {

                if (file.isEmpty()) {
                    continue;
                }

                ResponseEntity<String> response =
                        uploaderClient.uploadfile(file);

                post.getFilesUrls().add(response.getBody());
            }
        }
        Post createdPost = postRepository.save(post);

        // Get first-degree connections
        List<Long> connections =
                connectionClient.getFirstDegreeConnection(userId);

        // Create Kafka event
        PostCreated event = new PostCreated();

        event.setPostId(createdPost.getPostId());
        event.setUserId(userId);
        event.setContent(createdPost.getContent());
        event.setConnections(connections);

        // Publish asynchronously
        postCreatedKafkaTemplate.send(
                "post_created_event",
                event
        );

        log.info(
                "Post {} created by user {} and event published for {} connections",
                createdPost.getPostId(),
                userId,
                connections.size()
        );

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
