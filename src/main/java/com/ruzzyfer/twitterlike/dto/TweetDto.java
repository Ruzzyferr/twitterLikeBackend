package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.entity.Comment;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.enums.TweetCategory;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TweetDto {

    private int id;

    private User user;

    private String text;

    private String videoUrl;

    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;

    private TweetCategory category;

}
