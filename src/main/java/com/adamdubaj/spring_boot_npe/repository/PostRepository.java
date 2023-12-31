package com.adamdubaj.spring_boot_npe.repository;

import com.adamdubaj.spring_boot_npe.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //@Query("Select p from Post p left join fetch p.comment")
    @Query("Select p from Post p")
    List<Post> findAllPosts(Pageable pageable);
}
