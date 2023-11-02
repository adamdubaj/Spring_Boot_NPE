package com.adamdubaj.spring_boot_npe.repository;

import com.adamdubaj.spring_boot_npe.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
