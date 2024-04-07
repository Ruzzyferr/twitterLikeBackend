package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.enums.TweetCategory;
import lombok.Data;

@Data
public class TweetSaveRequestDto {

    private int id;

    private User user;

    private String text;

    private String videoUrl;

    private TweetCategory category;

}
