package com.adamdubaj.spring_boot_npe.controller;

import com.adamdubaj.spring_boot_npe.controller.dto.PostDto;
import com.adamdubaj.spring_boot_npe.model.Post;
import com.adamdubaj.spring_boot_npe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostDto> getPosts(@RequestParam(required = false) int page) {
        int pageNumber = page >= 0 ? page : 0;
        return PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber));
    }



    @GetMapping("/posts/{id}")
    public Post getSinglePost(@PathVariable long id) {
        return postService.getSinglePost(id);
    }
}
