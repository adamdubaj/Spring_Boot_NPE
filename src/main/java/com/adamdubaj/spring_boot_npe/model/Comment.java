package com.adamdubaj.spring_boot_npe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    private long id;
    private long post_id;
    private String content;
    private LocalDateTime created;
}
