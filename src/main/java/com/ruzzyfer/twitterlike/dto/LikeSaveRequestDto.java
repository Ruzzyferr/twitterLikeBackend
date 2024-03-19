package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.entity.Tweet;
import com.ruzzyfer.twitterlike.entity.User;
import lombok.Data;

@Data
public class LikeSaveRequestDto {

    private int id;

    private User user;

    private Tweet tweet;

}
