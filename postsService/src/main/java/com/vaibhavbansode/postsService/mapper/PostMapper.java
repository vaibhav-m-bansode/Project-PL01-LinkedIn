package com.vaibhavbansode.postsService.mapper;

import com.vaibhavbansode.postsService.dto.PostCreateRequestDto;
import com.vaibhavbansode.postsService.dto.PostDto;
import com.vaibhavbansode.postsService.entity.Post;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toPostDto(Post Post);

    Post toPost(PostCreateRequestDto postDto);
    Post toPost(PostDto postDto);

}
