package com.ruzzyfer.twitterlike.entity;

import com.ruzzyfer.twitterlike.enums.TweetCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    private String text;

    @Column(name = "video_url")
    private String videoUrl;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TweetCategory category;

}
