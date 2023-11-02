package com.adamdubaj.spring_boot_npe.service;

import com.adamdubaj.spring_boot_npe.model.Post;
import com.adamdubaj.spring_boot_npe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public Post getSinglePost(long id) {
        return postRepository.findById(id).orElseThrow();
    }
}
