package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.entity.Comment;
import com.ruzzyfer.twitterlike.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TweetSaveRequestDto {

    private int id;

    private User user;

    private String text;

    private String videoUrl;

}
